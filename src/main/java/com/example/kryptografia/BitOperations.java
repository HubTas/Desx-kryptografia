package com.example.kryptografia;

import javax.swing.*;

public class BitOperations {

    //pobiera bit z podanej pozycji w tablicy bajtów
    public static int getBit(byte[] data, int pos)
    {
        return  data[pos / 8] >> (7 - (pos % 8)) & 1;
    }

    //ustawia lub kasuje bit na podanej pozycji w tablicy bajtow
    public static void setBit(byte[] data, int pos, int val)
    {
        byte oldByte = data[pos / 8];
        oldByte = (byte) (((0xFF7F >> (pos % 8)) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val << (7 - (pos % 8))) | oldByte);
        data[pos / 8] = newByte;
    }

    //zwraca tablicę z podaną ilością bitów od podanej pozycji z tablicy bajtów
    public static byte[] selectBits(byte[] in, int pos, int len)
    {
        int numOfBytes = (len - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < len; i++) {
            setBit(out, i, getBit(in, pos + i));
        }
        return out;
    }

    //cykliczne przesunięcie bitów w lewo o zadana ilość pozycji
    public static byte[] shiftLeft(byte[] in, int step)
    {
        byte[] out = new byte [in.length];
        for (int i = 0; i < in.length * 8; i++)
        {
            setBit(out, i, getBit(in, (i + step) % (in.length * 8)));
        }
        return out;
    }

    //łączenie dwóch ciągów bajtów w jeden
    public static byte[] joinTabBytes(byte[] a, byte[] b)
    {
        int numOfBytes = a.length + b.length;
        byte[] out = new byte[numOfBytes];
        int j = 0;
        for (int i = 0; i < a.length * 8; i++)
        {
            setBit(out, j, getBit(a, i));
            j++;
        }
        for (int i = 0; i < b.length * 8; i++)
        {
            setBit(out, j, getBit(b, i));
            j++;
        }
        return out;
    }

    // wchodzi blok 48 bitowy(6 bajtów)
    // zwraca tablicę bajtów, w której na każdej pozycji jest 6 bitów z początkowego bloku danych oraz dwa zera na końcu
    public static  byte[] create6BitData(byte[] data)
    {
        int numOfBytes = (8 * data.length - 1) / 6 + 1;  //64 bity
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < numOfBytes; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                setBit(out, 8 * i + j, getBit(data, 6 * i + j));
            }
        }
        return out;
    }

    //dzielenie na bloki 64 bitowe
    public static byte[] oneBlock(byte[] data, int beginIndex)
    {
        byte[] msg = new byte[8];
        System.arraycopy(data, beginIndex, msg, 0, 8);
        return msg;
    }


}