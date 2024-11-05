package model;

public class Movie {
    private String title;
    private String genre;
    private String language;
    private String director;
    private String release_on;
    private String duration;
    private float rating;
    
    public Movie(String title, String genre, String language, String director, String release, String duration, float rating) {
	       this.title = title;
	       this.genre = genre;
	       this.language = language;
	      this.director = director;
	      this.release_on = release;
	      this.duration = duration;
	      this.rating = rating;
    }
    public String getTitle()
    {
    		return title;
    }
    public String getGenre()
    {
    		return genre;
    }
    public String getLanguage()
    {
    		return language;
    }
    public String getDirector()
    {
    		return director;
    }
    public String getReleaseOn()
    {
    		return release_on;
    }
    public String getDuration()
    {
    		return duration;
    }
    public float getRating()
    {
    		return rating;
    }
}

