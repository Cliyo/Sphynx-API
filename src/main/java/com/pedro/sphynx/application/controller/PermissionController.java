package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.application.dtos.permission.PermissionDataInput;
import org.springframework.http.ResponseEntity;

public class PermissionController implements ControllerIN<PermissionDataInput, PermissionDataComplete> {
    @Override
    public ResponseEntity create(PermissionDataInput data, String language) {
        return null;
    }

    @Override
    public ResponseEntity update(String id, PermissionDataComplete data, String language) {
        return null;
    }

    @Override
    public ResponseEntity delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity get() {
        return null;
    }
}
