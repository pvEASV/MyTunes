package mytunes.be;

public class Genre {
    private int id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name){
        this(name);
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }
}
