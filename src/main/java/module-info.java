module cloudlab {
    requires javafx.controls;
    requires javafx.fxml;

    opens cloudlab to javafx.fxml;
    opens cloudlab.controllers to javafx.fxml;

    exports cloudlab;
    exports cloudlab.controllers;
}
