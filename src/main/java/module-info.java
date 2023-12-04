module cloudlab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens cloudlab to javafx.fxml;
    opens cloudlab.controllers to javafx.fxml;
    opens cloudlab.util to javafx.fxml;

    exports cloudlab;
    exports cloudlab.controllers;
    exports cloudlab.util;
}
