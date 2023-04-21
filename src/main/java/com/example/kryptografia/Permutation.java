package com.example.kryptografia;

public class Permutation {
    public static byte[] permutation(byte[] block, byte[] pattern) {
        byte[] resultBlock = new byte[pattern.length / 8];
        for (int i = 0; i < pattern.length; i++) {
            BitOperations.setBit(resultBlock, i, BitOperations.getBit(block, pattern[i] - 1));
        }
        return resultBlock;
    }
}
