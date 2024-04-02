package com.example.rent_apartment.service.impl;

import java.util.Base64;

public class Base64EncoderDecoder {

    public static String encod(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(bytes);
        return new String(encode);
    }

    public static byte[] decod(String encoderStr) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encoderStr);
        return decode;
    }
}
