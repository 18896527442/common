package com.ll.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class QRCodeUtils {

    private static final String DEFAULT_IMG_FMT = "png";
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    public static String base64(String content) {
        String result= null;
        try {
            result = base64(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
           // throw new BusinessException("生成二维码失败", e);
        }

        return result;
    }

    public static String base64(String content, int width, int height) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_IMG_FMT, out);
            byte[] bytes = out.toByteArray();
            return new StringBuilder("data:image/").append(DEFAULT_IMG_FMT).append(";base64,").append(Base64.getEncoder().encodeToString(bytes)).toString();
        }
    }

//    public static void main(String[] args) throws Exception {
//        System.out.println(base64("1234"));
//        System.out.println(IdUtil.getSnowflake().nextIdStr());
//    }
}
