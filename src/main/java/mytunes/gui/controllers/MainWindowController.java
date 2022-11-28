package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mytunes.MyTunes;

import java.util.Objects;

public class MainWindowController {

    @FXML
    private ImageView playPause;
    private boolean isPlaying = false;


    public void onPlaylistNewButton(ActionEvent actionEvent) {
        System.out.println("New playlist button clicked");
    }

    public void playPauseMouseDown(MouseEvent mouseEvent) {
        System.out.println("Play/Pause button mouse down");
        if (!isPlaying) {
            playPause.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/pause.png"))));
        } else {
            playPause.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/play.png"))));
        }
        isPlaying = !isPlaying;
    }

    public void forwardMouseDown(MouseEvent mouseEvent) {
    }

    public void forwardMouseUp(MouseEvent mouseEvent) {
    }

    public void rewindMouseDown(MouseEvent mouseEvent) {
    }

    public void rewindMouseUp(MouseEvent mouseEvent) {
    }
}
