package com.example.kryptografia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.Random;


public class Controller {

    InfoWindow window=new InfoWindow();
    Random rand = new Random();
    private File inputFile;
    private File outputFile;
    private String fileExtension;
    private String text;
    private String kl1;
    private String kl2;
    private String kl3;
    private String nieWiem;
    private DesX desXControler = new DesX();
    private byte [] key1;
    private byte [] key2;
    private byte [] key3;
    private byte [] wiadomosc;
    private byte [] zaszyfrowanaWiadomosc;
    private byte [] deszyfrowanaWiadomosc;
    private byte [] tresc1;
    private byte [] tresc2;
    private boolean klucze=false;
    private Stage stage;
    private final char [] hexTable = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    @FXML
    private Button deszyfruj;

    @FXML
    private TextArea klucz1;

    @FXML
    private TextArea klucz2;

    @FXML
    private TextArea klucz3;

    @FXML
    private Button szyfruj;

    @FXML
    private TextArea tekst;

    @FXML
    private TextArea wynik;

    @FXML
    void dodajPlik(ActionEvent event) throws Exception {
        if(!klucze){
            window.text("Brak kluczy","Najpierw wygeneruj klucze",Alert.AlertType.ERROR);
        }
        else{
            tekst.clear();
            FileOperation fileOperation = new FileOperation();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik do zaszyfrowania");
            inputFile = fileChooser.showOpenDialog(stage);
            if(inputFile != null) {
                wiadomosc = fileOperation.readFile(inputFile);
                text = fileOperation.text(wiadomosc);
            }
            tekst.setText(text);

            byte [] fileByteArray;
            fileByteArray = new byte[(int) inputFile.length()];
            FileInputStream fileStream = new FileInputStream(inputFile);
            getFileExtension();
            fileStream.read(fileByteArray);
            fileByteArray = desXControler.DESX(fileByteArray, key1, key2, key3, true);
            int pom=inputFile.getName().indexOf(".");
            outputFile = new File( inputFile.getName().substring(0,pom) + "Encrypted." + fileExtension);
            FileOutputStream fileOutStream = new FileOutputStream(outputFile);
            fileOutStream.write(fileByteArray);
            zaszyfrowanaWiadomosc = fileByteArray;
            nieWiem = tabTransformation.bytesToHex(fileByteArray);
            wynik.setText(nieWiem);
        }

    }

    @FXML
    void generujKlucz(ActionEvent event) {
        kl1 = randomKey();
        kl2 = randomKey();
        kl3 = randomKey();
        klucz1.setText(kl1);
        klucz2.setText(kl2);
        klucz3.setText(kl3);
        key1 = tabTransformation.hexToBytes(klucz1.getText());
        key2 = tabTransformation.hexToBytes(klucz2.getText());
        key3 = tabTransformation.hexToBytes(klucz3.getText());
        klucze=true;
    }

    @FXML
    void szyfruj(ActionEvent event) throws UnsupportedEncodingException {
        if(!klucze){
            window.text("Brak kluczy","Najpierw wygeneruj klucze",Alert.AlertType.ERROR);
        }
        else {
            tresc1 = tabTransformation.StringToByteArray(tekst.getText());
            zaszyfrowanaWiadomosc = desXControler.DESX(tresc1, key1, key2, key3, true);
            wynik.setText(tabTransformation.bytesToHex(zaszyfrowanaWiadomosc));
        }
    }

    @FXML
    void deszyfruj(ActionEvent event) throws UnsupportedEncodingException {
        tresc2 = tabTransformation.hexToBytes(wynik.getText());
        deszyfrowanaWiadomosc = desXControler.DESX(tresc2,key3,key2,key1,false);
        tekst.setText(tabTransformation.ByteArrayToString(deszyfrowanaWiadomosc));
    }


    @FXML
    void deszyfrujPlik(ActionEvent event) throws IOException {
        byte [] fileByteArray;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik do zaszyfrowania");
        inputFile = fileChooser.showOpenDialog(stage);
        fileByteArray = new byte[(int) inputFile.length()];
        FileInputStream fileStream = new FileInputStream(inputFile);
        getFileExtension();
        fileStream.read(fileByteArray);
        fileByteArray = desXControler.DESX(fileByteArray, key3, key2, key1, false);
        int pom=inputFile.getName().indexOf("Encrypted");
        outputFile = new File( inputFile.getName().substring(0,pom) + "Decrypted." + fileExtension);
        FileOutputStream fileOutStream = new FileOutputStream(outputFile);
        fileOutStream.write(fileByteArray);
        deszyfrowanaWiadomosc = fileByteArray;
        nieWiem = tabTransformation.ByteArrayToString(fileByteArray);
        tekst.setText(nieWiem);
    }




    public void getFileExtension() {
        String extension = null;
        int index = inputFile.getName().lastIndexOf('.');
        if(index > 0) {
            extension = inputFile.getName().substring(index + 1);
        }

        fileExtension = extension;
    }

    public String randomKey () {
        String key = "";

        for(int i = 0; i < 16; i++){
            key += hexTable[rand.nextInt(16)];
        }
        return key;
    }

}