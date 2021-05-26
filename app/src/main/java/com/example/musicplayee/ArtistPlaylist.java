package com.example.musicplayee;

public class ArtistPlaylist {
    private String artist_name;
    private String artist_image;

    public ArtistPlaylist(){

    }
    public ArtistPlaylist(String artist_name, String artist_image) {
        this.artist_name = artist_name;
        this.artist_image = artist_image;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_image() {
        return artist_image;
    }

    public void setArtist_image(String artist_image) {
        this.artist_image = artist_image;
    }
}
