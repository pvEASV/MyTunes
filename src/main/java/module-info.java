module sk.groupOne.mytunes {

    opens sk.groupOne.mytunes to javafx.fxml;
    exports sk.groupOne.mytunes;
    exports sk.groupOne.mytunes.gui.controllers;
    opens sk.groupOne.mytunes.gui.controllers to javafx.fxml;
}