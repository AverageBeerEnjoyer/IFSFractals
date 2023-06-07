module com.example.ifsfractals {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ifsfractals to javafx.fxml;
    exports com.example.ifsfractals;
}