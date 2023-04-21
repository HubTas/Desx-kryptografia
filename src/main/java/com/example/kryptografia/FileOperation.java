package com.example.kryptografia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperation {
    public byte[] readFile(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] tab = Files.readAllBytes(path);
        return tab;

    }

    public String text(byte[] bytes){
        String txt = new String(bytes);
        return txt;
    }

    public void writeFile(FileOutputStream pathfile, byte[] cipher) throws IOException {

        pathfile.write(cipher);
    }
}
