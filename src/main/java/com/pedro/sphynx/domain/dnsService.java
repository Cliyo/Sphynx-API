package com.pedro.sphynx.domain;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.jmdns.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
// only build this class when the app is not built by docker, because we use avahi inside the container
@ConditionalOnProperty(name = "docker.container", havingValue = "false", matchIfMissing = false)
public class dnsService {

    // Getting mdns variables from application.properties

    @Value("${mdns.service.type}")
    private String serviceType;

    @Value("${mdns.service.name}")
    private String serviceName; 

    private JmDNS jmdns;
    private int serviceNumber = 0; // If exists more than one interface on PC it concatenates the servicenumber in the serviceName so it won't conflict
    private InetAddress ipAddress; // IP address that will start the mDNS service

    @PostConstruct
    public void registerService() {
        try {
            // Get all network interfaces available in the host
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement(); // choses an network interface from the list
                
                // If the interface is on and is not loopback (localhost, 127.0.0.1, ::1, etc), continues
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    String nomeServico = serviceName + Integer.toString(serviceNumber); // concatenate the serviceNumber in the serviceName
                    
                    // get all ip addresses for the network interface chosen before
                    Enumeration<InetAddress> Addresses = networkInterface.getInetAddresses();
                    while (Addresses.hasMoreElements()){
                        ipAddress = Addresses.nextElement(); // assign ipAddress with an ip from the list

                        // check if the ip is an ipv4 (must be ipv4, ipv6 won't work), then leaves
                        if (ipAddress instanceof Inet4Address){
                            break;
                        }
                    }

                    // create the mDNS service with the desired ipv4 chosen before
                    jmdns = JmDNS.create(ipAddress, "sphynx-api");

                    // set service parameters (http._tcp, name, port and description)
                    ServiceInfo serviceInfo = ServiceInfo.create(serviceType, nomeServico, 57128, "Sphynx API");
                    jmdns.registerService(serviceInfo);
                    serviceNumber++;
                }
            }
            System.out.println("MDNS SERVICE UP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void unregisterService() {
        // unregister all mDNS services before shutting the api down
        if (jmdns != null) {
            jmdns.unregisterAllServices();
            try {
                jmdns.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
