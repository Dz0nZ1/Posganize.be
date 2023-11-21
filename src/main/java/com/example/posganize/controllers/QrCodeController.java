package com.example.posganize.controllers;

import com.example.posganize.services.QrCode.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
class QrCodeController {
    private static final int WIDTH = 500;    private static final int HEIGHT = 500;
    @Autowired
    private QrCodeService qrCodeService;
    @GetMapping("/generate-qr-code")
    public ResponseEntity<byte[]> getQrCode() {
        String contentToGenerateQrCode = "Simple Solution";
        byte[] qrCode = qrCodeService.generateQrCode(contentToGenerateQrCode, WIDTH, HEIGHT);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
    }
}