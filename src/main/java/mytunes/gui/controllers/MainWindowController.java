package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mytunes.MyTunes;

import java.util.Objects;
import java.util.Optional;

public class MainWindowController {

    @FXML
    private ImageView playPauseButton;
    private boolean isPlaying = false;
    @FXML
    private Button playlistDeleteButton, songOnPlaylistDeleteButton, songsDeleteButton;


    public void onPlaylistNewButton(ActionEvent actionEvent) {
        System.out.println("New playlist button clicked");
    }

    public void playPauseMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
        System.out.println("Play/Pause button mouse down");
        if (!isPlaying) {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/pause.png"))));
        } else {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/play.png"))));
        }
        isPlaying = !isPlaying;
    }
    public void forwardMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    public void rewindMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    /**
     * Changes opacity of the control button to 0.5 when mouse is pressed down
     * @param mouseEvent The mouse event that triggered this method
     */
    public void controlsButtonMouseDown(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setOpacity(0.5);
    }

    /**
     * Resets the opacity of the button to 1.0
     * Used when the mouse is released
     * @param mouseEvent The mouse event that triggered this method
     */
    private void resetOpacity(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setOpacity(1);
    }

    public void showAlert(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Node source = (Node) actionEvent.getSource();
        String type = "song";
        if (source.getId().equals("playlistDeleteButton"))
            type = "playlist";
        alert.setTitle("Delete " + type);
        alert.setContentText("Do you really wish to delete this " + type + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()){
            // alert is exited, no button has been pressed.
            System.out.println("No button clicked");
        } else if(result.get() == ButtonType.OK){
            //ok button is pressed
            System.out.println("Ok button clicked");
        } else if(result.get() == ButtonType.CANCEL){
            // cancel button is pressed
            System.out.println("Cancel button clicked");
        }
    }
}
