package com.example.kryptografia;


public class KeysGenerator {

    private byte[] key;
    //56 bitów
    private final byte[] PC1 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    //48 bitów
    private final byte[] PC2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    //liczba przesunięć w lewo
    private final byte[] SHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    public KeysGenerator(byte[] key) {
        this.key = key;
    }

    //generuje 16 podkluczy
    public byte[][] getSubkeys()
    {
        byte[] activeKey = Permutation.permutation(this.key, this.PC1);
        int halfKeySize = this.PC1.length / 2;  //28 bitów
        byte[] c = BitOperations.selectBits(activeKey, 0, halfKeySize);  //pierwsza połowa permutowanego klucza
        byte[] d = BitOperations.selectBits(activeKey, halfKeySize, halfKeySize); //druga połowa permutowanego klucza
        byte[][] subKeysLocal = new byte[16][];  // tablica 16 podkluczy
        for (int k = 0; k < 16; k++)
        {
            c = BitOperations.shiftLeft(c, this.SHIFTS[k]);
            d = BitOperations.shiftLeft(d, this.SHIFTS[k]);
            byte[] cd = BitOperations.joinTabBytes(c, d);
            try {
                subKeysLocal[k] = Permutation.permutation(cd, this.PC2);
            } catch (RuntimeException e)
            {
                System.out.println(e.getCause());
            }

        }
        return subKeysLocal;
    }
}