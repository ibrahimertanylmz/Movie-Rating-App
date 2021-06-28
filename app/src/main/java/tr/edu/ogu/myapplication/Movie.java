package tr.edu.ogu.myapplication;

import android.widget.ImageView;

public class Movie {

    private String name;
    private String id;
    private String body;
    private String email;
    private String releaseDate;
    private String translatedTitle;
    private boolean isChecked;
    private Integer realId;
    private Double rating;

    public String getTranslatedTitle() {
        return translatedTitle;
    }

    public void setTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    public Movie(String name, String id, String body, String email, String releaseDate, String translatedTitle, Integer realId, Double rating) {
        this.name = name;
        this.id = id;
        this.body = body;
        this.email = email;
        this.releaseDate = releaseDate;
        this.translatedTitle = translatedTitle;
        this.rating = rating;
        this.realId = realId;
    }

    public Integer getRealId() {
        return realId;
    }

    public void setRealId(Integer realId) {
        this.realId = realId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
