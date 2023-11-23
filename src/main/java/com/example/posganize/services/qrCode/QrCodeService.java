package com.example.posganize.services.qrCode;

public interface QrCodeService
{
    byte[] generateQrCode(String qrCodeContent, int width, int height);


}