module sk.groupOne.mytunes {
    requires javafx.fxml;
    requires javafx.controls;

    opens mytunes to javafx.fxml;
    exports mytunes;
    exports mytunes.gui.controllers;
    opens mytunes.gui.controllers to javafx.fxml;
}