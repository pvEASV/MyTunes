package mytunes.gui.models;

import mytunes.bll.LogicManager;

public class SongModel {
    private LogicManager bll = new LogicManager();

    public void createSong(String title, String filepath){
        bll.createSong(title, filepath);
    }

}
