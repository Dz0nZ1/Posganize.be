package com.example.posganize.services.QrCode;

public interface QrCodeService
{
    byte[] generateQrCode(String qrCodeContent, int width, int height);


}