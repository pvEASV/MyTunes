package mytunes.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import mytunes.be.Genre;
import mytunes.bll.LogicManager;

public class Model {
    private final ObservableList<Genre> genres;
    private LogicManager bll = new LogicManager();

    public Model(){
        genres = FXCollections.observableArrayList();
    }

    public void createSong(String title, String filepath){
        bll.createSong(title, filepath);
    }

    public void createGenre(String name) {
        //TODO add genre to database, update combobox
        bll.createGenre(name);
        updateGenres();
    }

    private void updateGenres() {
        genres.clear();

    }
}
