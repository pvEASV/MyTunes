package mytunes.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.bll.LogicManager;

public class Model {
    private final ObservableList<Genre> genres;
    private final ObservableList<Playlist> playlists;
    private LogicManager bll = new LogicManager();

    public Model(){
        genres = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
    }

    public void createSong(String title, String filepath){
        bll.createSong(title, filepath);
    }

    public void createGenre(String name) {
        //TODO update combobox
        Genre genre = bll.createGenre(name);
        genres.add(genre);
    }

    private void updateGenres() {
        genres.clear();
        genres.addAll(bll.getAllGenres());
    }
}
