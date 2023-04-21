package com.example.kryptografia;

import javafx.scene.control.Alert;

public class InfoWindow {

        public void text(String title, String text, Alert.AlertType type) {
            Alert alert = new Alert(type);
            alert.setContentText(text);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.showAndWait();
    }
}
