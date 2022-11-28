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
    private ImageView playPauseButton;
    private boolean isPlaying = false;


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
}
