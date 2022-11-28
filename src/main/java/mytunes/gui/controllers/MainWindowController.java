package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mytunes.MyTunes;

import java.util.Objects;

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

    public void buttonMouseDown(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setOpacity(0.5);
    }
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
        alert.showAndWait();
    }
}
