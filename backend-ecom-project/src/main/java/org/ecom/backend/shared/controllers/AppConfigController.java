package org.ecom.backend.shared.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class AppConfigController {

    @GetMapping("/app-data")
    public ResponseEntity<AppConfigData> getAppConfig() throws UnknownHostException {
        var data = new AppConfigData(InetAddress.getLocalHost().getCanonicalHostName(),
                InetAddress.getLocalHost().getHostAddress());
        return ResponseEntity.ok().body(data);
    }
}

record AppConfigData  (String host, String ip) {

}