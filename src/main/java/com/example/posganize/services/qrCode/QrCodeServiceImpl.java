package com.example.posganize.services.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Service
public class QrCodeServiceImpl implements QrCodeService {
    private final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);
    @Override
    public byte[] generateQrCode(String qrCodeContent, int width, int height) {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

            }
        catch (WriterException e) {
            logger.error(e.getMessage(), e);
                         }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        } return null;
    }
}