package mytunes.be;

import mytunes.dal.dao.PlaylistDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Playlist {
    private int id, totalLength;
    private String name, totalLengthAsAString;

    public Playlist(String name){
        this.name = name;
    }

    public Playlist(int id, String name) {
        this(name);
        this.id = id;
    }

    public Playlist(int id, String name, int totalLength) {
        this(id, name);
        this.totalLength = totalLength;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalLength() {
        return totalLength;
    }

    @Override
    public String toString() {
        return  "id = " + id +
                ", name = '" + name + '\'' +
                ", totalLength = " + totalLength;
    }

    private void convertSecondsIntoString(int duration){
        totalLengthAsAString = "";
        int seconds = duration % 60;
        int minutes = (duration / 60) % 60;
        int hours = (duration / 60) / 60;

        if (hours > 0){
            if (hours < 10)
                totalLengthAsAString += "0";
            totalLengthAsAString += hours + ":";
        }

        if (minutes < 10)
            totalLengthAsAString += "0";
        totalLengthAsAString += minutes + ":";

        if (seconds < 10)
            totalLengthAsAString += "0";
        totalLengthAsAString += seconds;
    }

    public String getTotalLengthAsAString(){  // used by PropertyValueFactory
        convertSecondsIntoString(this.totalLength);
        return totalLengthAsAString;
    }
}
