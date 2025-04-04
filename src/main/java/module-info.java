module com.manager.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.manager.main to javafx.fxml;
    exports com.manager.main;
}