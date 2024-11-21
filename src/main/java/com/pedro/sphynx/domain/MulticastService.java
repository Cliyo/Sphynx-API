package com.pedro.sphynx.domain;

import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MulticastService {
    
    private static final int PORT = 57128;
    private static final int FinderPort = 57127;
    private InetAddress ipAddress;
    public static final String multicastIP = "239.255.255.250";
    
    @Autowired
    private DeviceFinder finder;
    
    @PostConstruct
    public void startListening() {
        new Thread(() -> {
            try {
                
                MulticastSocket socket = createSocket(PORT);

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                System.out.println("Multicast started on " + PORT);
                
                while (true) {
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.print("Received: " + message);
                    System.out.println(" From: " + packet.getAddress() + ":" + packet.getPort());

                    if ("Request Sphynx API Address".equals(message)) {
                        InetAddress senderAddress = packet.getAddress();
                        InetAddress localAddress = getSenderNetwork(senderAddress);
                        if (localAddress != null) {
                            String ipAddress = localAddress.getHostAddress();
                            System.out.println("Sending back IP address: " + ipAddress);

                            byte[] responseMessage = ipAddress.getBytes();
                            DatagramPacket responsePacket = new DatagramPacket(responseMessage, responseMessage.length, packet.getAddress(), packet.getPort());
                            socket.send(responsePacket);
                        } else {
                            System.out.println("network not found for: " + senderAddress);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @PostConstruct
    private void Finder() {
        new Thread(() -> {
            finder.setSocket(createSocket(FinderPort));
            finder.Finder(true);
        }).start();
    }

    public void finderScan() {
        finder.Finder(false);
    }

    // compares the sender address with the local network interfaces to get the correct one
    // this way the sender can get the correct IP address to connect to the API
    private InetAddress getSenderNetwork(InetAddress senderAddress) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address) {
                    byte[] senderBytes = senderAddress.getAddress();
                    byte[] localBytes = address.getAddress();
                    if (senderBytes[0] == localBytes[0] && senderBytes[1] == localBytes[1] && senderBytes[2] == localBytes[2]) {
                        return address;
                    }
                }
            }
        }
        return null;
    }

    private MulticastSocket createSocket(int port) {
        try {
            MulticastSocket socket = new MulticastSocket(port);
                
            InetAddress group = InetAddress.getByName(multicastIP);
    
            // uses the same logic from dnsService.java to get the network interface
            // it should work using 0.0.0.0 as the inetaddress to get all the interfaces, but it doesn't apparently (? need further testing)
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> Addresses = networkInterface.getInetAddresses();
                    while (Addresses.hasMoreElements()){
                        ipAddress = Addresses.nextElement();
    
                        if (ipAddress instanceof Inet4Address){
                            break;
                        }
                    }
                    System.out.println("Entrando no grupo de multicast na interface: " + networkInterface.getName() + " porta: " + port);
                    socket.joinGroup(new InetSocketAddress(group, PORT), networkInterface);
                }
            }
            // socket.joinGroup(new InetSocketAddress(group, PORT), NetworkInterface.getByInetAddress(InetAddress.getByName("0.0.0.0")));
            return socket;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public List<List<String>> getDevices() {
        return finder.getDevices();
    }

}

@Service
class DeviceFinder {
    private MulticastSocket socket;
    private byte[] message = "Sphynx Device Finder".getBytes();
    private static final String multicastIP = "239.255.255.250";
    private DatagramPacket packet;
    private byte[] buffer = new byte[1024];
    private DatagramPacket responsePacket;

    public ArrayList<List<String>> devices = new ArrayList<>();

    @Autowired
    private LocalRepository localRepository;

    public DeviceFinder() {
        try{
            packet = new DatagramPacket(message, message.length, InetAddress.getByName(multicastIP), 57127);
            responsePacket = new DatagramPacket(buffer, buffer.length);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    public List<List<String>> getDevices() {
        return devices;
    }

    public void Finder(boolean auto) {
        try {
            while (true) {
                System.out.println("Starting finder Scan");

                List<Local> locals = localRepository.findAll();

                List<String> localsMacs = locals.stream().map(Local::getMac).toList();

                socket.send(packet);

                socket.receive(responsePacket);
                String responseMessage = new String(responsePacket.getData(), 0, responsePacket.getLength());

                List<String> device = new ArrayList<>();
                try {
                    device = List.of(responseMessage.split(","));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (device.size() > 1 && !devices.contains(device) && !localsMacs.contains(device.get(1))) {
                    devices.add(device);
                }

                System.out.println("Devices found: " + devices);

                if (auto) {
                    Thread.sleep(180000);
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}