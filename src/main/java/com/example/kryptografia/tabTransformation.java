package com.example.kryptografia;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class tabTransformation {

    private static Charset charset = StandardCharsets.UTF_16BE;


    public static String bytesToHex(byte bytes[])
    {
        byte rawData[] = bytes;
        StringBuilder hexText = new StringBuilder();
        String initialHex = null;
        int initHexLength = 0;

        for (int i = 0; i < rawData.length; i++)
        {
            int positiveValue = rawData[i] & 0x000000FF;
            initialHex = Integer.toHexString(positiveValue);
            initHexLength = initialHex.length();
            while (initHexLength++ < 2)
            {
                hexText.append("0");
            }
            hexText.append(initialHex);
        }
        return hexText.toString();
    }

    //konwertuje ciąg znaków w systemie heksadecymalnym na tablicę bajtów
    public static byte[] hexToBytes(String tekst)
    {
        if (tekst == null) { return null;}
        else if (tekst.length() < 2) { return null;}
        else { if (tekst.length()%2!=0)tekst+='0';
            int dl = tekst.length() / 2;
            byte[] wynik = new byte[dl];
            for (int i = 0; i < dl; i++)
            {
                try{
                    wynik[i] = (byte) Integer.parseInt(tekst.substring(i * 2, i * 2 + 2), 16);
                }catch(NumberFormatException e){
                    System.out.println("ups");
                }
            }
            return wynik;
        }
    }


    public static byte [] StringToByteArray(String text) throws UnsupportedEncodingException {
        byte [] str;
        str = text.getBytes(charset);
        return str;
    }

    public static String ByteArrayToString(byte [] bytes) throws UnsupportedEncodingException {
        String str = new String(bytes,charset);
        return str;
    }

    public static byte [] fillMessage(byte [] message)
    {
        //Obliczanie długości szyfrogramu
        int len;
        if (message.length % 8 != 0)
            len = (message.length / 8 + 1) * 8;
        else
            len = message.length;
        byte[] result = new byte[len];
        System.arraycopy(message, 0, result, 0, message.length);
        return result;
    }

    public static byte [] cutDecrypted(byte [] tmpResult)
    {
        int cnt = 0;
        for (int i = 1; i < 9; i += 1)
        {
            if (tmpResult[tmpResult.length - i] == 0)
                cnt += 1;
            else
                break;
        }
        byte[] result = new byte[tmpResult.length - cnt];
        System.arraycopy(tmpResult, 0, result, 0, tmpResult.length - cnt);
        return result;
    }



}