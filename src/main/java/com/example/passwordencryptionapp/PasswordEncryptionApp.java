package com.example.passwordencryptionapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PasswordEncryptionApp extends Application {

    // Główna funkcja, która uruchamia aplikację JavaFX
    public static void main(String[] args) {
        launch(args);
    }

    // Metoda startująca interfejs graficzny
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Szyfrowanie Hasła");

        // Tworzymy interfejs graficzny JavaFX
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Tworzymy etykietę i pole do wprowadzenia hasła
        Label passwordLabel = new Label("Wprowadź hasło:");
        grid.add(passwordLabel, 0, 0);
        TextArea passwordInput = new TextArea();
        grid.add(passwordInput, 1, 0);

        // Tworzymy ComboBox do wyboru metody szyfrowania
        Label encryptionMethodLabel = new Label("Wybierz metodę szyfrowania:");
        grid.add(encryptionMethodLabel, 0, 1);
        ComboBox<String> encryptionMethodComboBox = new ComboBox<>();
        encryptionMethodComboBox.getItems().addAll("Szyfrowanie Cezara", "Szyfr Vigenère'a", "Szyfr podstawieniowy", "Zamień na kod binarny");
        grid.add(encryptionMethodComboBox, 1, 1);

        // Tworzymy etykietę i pole do wyświetlania zaszyfrowanego/odszyfrowanego hasła
        Label resultLabel = new Label("Wynik:");
        grid.add(resultLabel, 0, 2);
        TextArea resultOutput = new TextArea();
        grid.add(resultOutput, 1, 2);

        // Tworzymy przycisk do szyfrowania/odszyfrowania hasła
        Button processButton = new Button("Szyfruj/Odszyfruj");
        grid.add(processButton, 0, 3);
        processButton.setOnAction(e -> {
            String password = passwordInput.getText();
            String encryptionMethod = encryptionMethodComboBox.getValue();
            String result = processPassword(password, encryptionMethod);
            resultOutput.setText(result);
        });

        // Tworzymy scenę i ustawiamy na główne okno
        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metoda szyfrująca/odszyfrowująca hasło w zależności od wybranej metody szyfrowania
    private String processPassword(String password, String encryptionMethod) {
        switch (encryptionMethod) {
            case "Szyfrowanie Cezara":
                return encryptCaesar(password);
            case "Szyfr Vigenère'a":
                return encryptVigenere(password, "KLUCZ"); // Przykładowy klucz szyfrowania Vigenère'a
            case "Szyfr podstawieniowy":
                return encryptSubstitution(password, "QWERTYUIOPASDFGHJKLZXCVBNM"); // Przykładowy klucz szyfrowania podstawieniowego
            case "Zamień na kod binarny":
                return textToBinary(password);
            default:
                return "Metoda szyfrowania nie została wybrana.";
        }
    }

    // Metoda szyfrująca hasło za pomocą Szyfrowania Cezara
    private String encryptCaesar(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (char c : password.toCharArray()) {
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                // Szyfrowanie tylko liter
                char encryptedChar = (char) (c + 2);
                encryptedPassword.append(encryptedChar);
            } else {
                encryptedPassword.append(c);
            }
        }
        return encryptedPassword.toString();
    }

    // Metoda odszyfrowująca hasło za pomocą Szyfrowania Cezara
    private String decryptCaesar(String encryptedPassword) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (char c : encryptedPassword.toCharArray()) {
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                // Odszyfrowanie tylko liter
                char decryptedChar = (char) (c - 2);
                decryptedPassword.append(decryptedChar);
            } else {
                decryptedPassword.append(c);
            }
        }
        return decryptedPassword.toString();
    }

    // Metoda szyfrująca hasło za pomocą Szyfru Vigenère'a
    private String encryptVigenere(String password, String key) {
        StringBuilder encryptedPassword = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                int shift = key.charAt(i % keyLength) - 'A';
                char encryptedChar = (char) ((c - 'A' + shift) % 26 + 'A');
                encryptedPassword.append(encryptedChar);
            } else {
                encryptedPassword.append(c);
            }
        }
        return encryptedPassword.toString();
    }

    // Metoda odszyfrowująca hasło za pomocą Szyfru Vigenère'a
    private String decryptVigenere(String encryptedPassword, String key) {
        StringBuilder decryptedPassword = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < encryptedPassword.length(); i++) {
            char c = encryptedPassword.charAt(i);
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                int shift = key.charAt(i % keyLength) - 'A';
                char decryptedChar = (char) ((c - 'A' - shift + 26) % 26 + 'A');
                decryptedPassword.append(decryptedChar);
            } else {
                decryptedPassword.append(c);
            }
        }
        return decryptedPassword.toString();
    }

    // Metoda szyfrująca hasło za pomocą Szyfru podstawieniowego
    private String encryptSubstitution(String password, String substitutionKey) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (char c : password.toCharArray()) {
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                int index = c - 'A';
                char encryptedChar = substitutionKey.charAt(index);
                encryptedPassword.append(encryptedChar);
            } else {
                encryptedPassword.append(c);
            }
        }
        return encryptedPassword.toString();
    }

    // Metoda odszyfrowująca hasło za pomocą Szyfru podstawieniowego
    private String decryptSubstitution(String encryptedPassword, String substitutionKey) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (char c : encryptedPassword.toCharArray()) {
            // Pomijamy spacje, przecinki itp.
            if (Character.isLetter(c)) {
                int index = substitutionKey.indexOf(c);
                char decryptedChar = (char) (index + 'A');
                decryptedPassword.append(decryptedChar);
            } else {
                decryptedPassword.append(c);
            }
        }
        return decryptedPassword.toString();
    }

    // Metoda zamieniająca tekst na kod binarny
    private String textToBinary(String text) {
        StringBuilder binaryCode = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            binaryCode.append(binaryChar).append(" ");
        }
        return binaryCode.toString();
    }

    // Metoda zamieniająca kod binarny na tekst
    private String binaryToText(String binaryCode) {
        StringBuilder text = new StringBuilder();
        String[] binaryChars = binaryCode.split(" ");
        for (String binaryChar : binaryChars) {
            int decimalValue = Integer.parseInt(binaryChar, 2);
            char character = (char) decimalValue;
            text.append(character);
        }
        return text.toString();
    }
}
