package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import mytunes.gui.models.Model;

public class NewGenreViewController {
    @FXML
    private TextField nameTextField;
    private Model model = new Model();

    public void saveButtonAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        String name = "";
        if (!nameTextField.getText().isEmpty() && !nameTextField.getText().equals("Field must not be empty!")){
            name = nameTextField.getText();
            model.createGenre(name);
            node.getScene().getWindow().hide();
        }
        else{
            nameTextField.setText("Field must not be empty!");
        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}
