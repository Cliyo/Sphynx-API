package com.pedro.sphynx.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.sphynx.services.MulticastService;

import java.util.HashMap;
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
    public ResponseEntity<List<HashMap<String, Object>>> getDevices() {
        List<HashMap<String, Object>> devices = multicastService.getDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("scan")
    public ResponseEntity<List<HashMap<String, Object>>> scanDevices() {
        multicastService.finderScan();
        List<HashMap<String, Object>> devices = multicastService.getDevices();
        return ResponseEntity.ok(devices);
    }

    @PostMapping("push")
    public void pushDevices(@RequestBody String device) {
        HashMap<String, Object> deviceObject = new HashMap<>();
        deviceObject.put("ip", device.split(",")[0]);
        deviceObject.put("mac", device.split(",")[1]);
        deviceObject.put("registered", false);
        multicastService.pushDevice(deviceObject);
    }
}