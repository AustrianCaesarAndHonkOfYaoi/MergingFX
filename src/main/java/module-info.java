module com.example.mergingfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.mergingfx to javafx.fxml;
    exports com.example.mergingfx;
}