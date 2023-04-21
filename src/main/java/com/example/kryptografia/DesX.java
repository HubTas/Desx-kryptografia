package com.example.kryptografia;


public class DesX {
    public DesX()
    {
    }

    public byte[] DESX(byte[] message, byte [] key1, byte [] key2, byte [] key3, boolean ifE) {
        Des des = new Des();
        byte[] step1;
        KeysGenerator subkeysGen = new KeysGenerator(key2);
        byte[][] subkeys = subkeysGen.getSubkeys();
        byte[] step2;
        byte[] result;

        if (ifE == true) {
            //xorowanie z kluczem I
            step1 = XOR.xorEncode(message, key1);
            //szyfrowanie z kluczem II
            step2 = des.desEncode(step1, subkeys);
            //xorowanie z kluczem II
            result = XOR.xorEncode(step2, key3);
        }
        else
        {
            //xorowanie z kluczem I
            step1 = XOR.xorDecode(message, key1);
            //deszyfrowanie z kluczem II
            step2 = des.desDecode(step1, subkeys);
            //xorowanie z kluczem II
            result = XOR.xorDecode(step2, key3);
        }
        return result;
    }
}