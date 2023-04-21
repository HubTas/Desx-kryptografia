package com.example.kryptografia;

public class XOR {
    //xoruje dwie tablice bajtów
    public static byte[] XORBytes(byte[] a, byte[] b)
    {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++)
        {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static byte[] xorEncode(byte[] message, byte[] key)
    {
        byte[] result = tabTransformation.fillMessage(message);
        byte[] tempBlock;
        // Pętla tnąca i kodowanie bloków
        for (int i = 0; i < (result.length / 8); i++)
        {
            tempBlock = XORBytes(BitOperations.oneBlock(result, i * 8),key);
            System.arraycopy(tempBlock, 0, result, i * 8, 8);
        }
        return result;
    }

    public static byte[] xorDecode(byte[] encrypted, byte[] key)
    {
        byte[] tmpResult = new byte[encrypted.length];
        byte[] tempBlock;
        //Pętla tnąca i kodowanie bloków
        for (int i = 0; i < (encrypted.length / 8); i++)
        {
            tempBlock = XORBytes(BitOperations.oneBlock(encrypted, i * 8),key);
            System.arraycopy(tempBlock, 0, tmpResult, i * 8, tempBlock.length);
        }
        return tabTransformation.cutDecrypted(tmpResult);
    }
}