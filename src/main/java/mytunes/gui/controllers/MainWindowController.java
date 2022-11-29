package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mytunes.MyTunes;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainWindowController {
    @FXML
    private Slider volumeControlSlider;
    @FXML
    private TextField filterTextField;
    @FXML
    private ListView<?> songsInPlaylistListVIew, allSongsListView, playListListVIew;
    @FXML
    private ImageView playPauseButton;
    @FXML
    private Button playlistDeleteButton, songsInPlaylistDeleteButton, songsDeleteButton;


    private boolean isPlaying = false;


    /**
     * This method is called when the user clicks the ImageView representing play/pause button.
     * @param mouseEvent The mouse event that triggered this method.
     */
    public void playPauseMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
        System.out.println("Play/Pause button mouse down");
        if (isPlaying) {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/play.png"))));
            isPlaying = false;
        } else {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/pause.png"))));
            isPlaying = true;
        }
    }
    public void forwardMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    public void rewindMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    /**
     * Changes opacity of the music controls buttons to 0.5 when mouse is pressed down on them
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

    /**
     * Called when the user clicks one of delete buttons
     * @param actionEvent The action event that triggered this method
     */
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

    /**
     * Called when the user clicks the "new" button under playlist section
     * It opens new window for creating new playlist
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void playlistNewButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunes.class.getResource("views/new-playlist-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(MyTunes.class.getResource("css/mainstyle.css").toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/playlist.png"))));
        stage.setTitle("Add playlist");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public void playlistEditButtonAction(ActionEvent actionEvent) {

    }
    public void filterOnKeyTyped(KeyEvent keyEvent) {
    }
    /**
     * Called when the user clicks the "new" button under all songs section
     * It opens new window for adding new song
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void songNewButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunes.class.getResource("views/new-song-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(MyTunes.class.getResource("css/mainstyle.css").toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/record.png"))));
        stage.setTitle("Add song");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void songEditButtonAction(ActionEvent actionEvent) {
    }
}
