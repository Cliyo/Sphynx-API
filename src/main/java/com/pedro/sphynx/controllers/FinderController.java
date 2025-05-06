package com.pedro.sphynx.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.sphynx.services.MulticastService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("deviceFinder")
public class FinderController {

    @Autowired
    private MulticastService multicastService;

    @GetMapping
    public ResponseEntity<List<List<String>>> getDevices() {
        List<List<String>> devices = multicastService.getDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("scan")
    public ResponseEntity<List<List<String>>> scanDevices() {
        multicastService.finderScan();
        List<List<String>> devices = multicastService.getDevices();
        return ResponseEntity.ok(devices);
    }

    @PostMapping("push")
    public void pushDevices(@RequestBody String device) {
        System.out.println(device);
        String[] deviceList = device.split(",");
        List<String> deviceListString = List.of(deviceList);
        multicastService.pushDevice(deviceListString);
    }
}