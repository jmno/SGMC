package info.mobilesgmc.modelo;

/**
 * Created by Nicolau on 06/01/15.
 */
public class Horas {

    private String title;
    private String description;

    public Horas(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
