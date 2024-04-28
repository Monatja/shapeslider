module com.example.shape {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.shape to javafx.fxml;
    exports com.example.shape;
}