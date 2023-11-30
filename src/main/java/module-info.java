module cloudlab {
    requires javafx.controls;
    requires javafx.fxml;

    opens cloudlab to javafx.fxml;

    exports cloudlab;
}
