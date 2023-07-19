module com.example.passwordencryptionapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.passwordencryptionapp to javafx.fxml;
    exports com.example.passwordencryptionapp;
}