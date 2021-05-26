package com.example.musicplayee;

public class Song {
    private String song_name;
    private String song_link;
    private String song_image;
    private String song_about;
    public Song(){

    }

    public Song(String song_name, String song_link,String song_image,String song_about) {
        this.song_name = song_name;
        this.song_link = song_link;
        this.song_image=song_image;
        this.song_about=song_about;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_link() {
        return song_link;
    }

    public void setSong_link(String song_link) {
        this.song_link = song_link;
    }

    public String getSong_image() {
        return song_image;
    }

    public void setSong_image(String song_image) {
        this.song_image = song_image;
    }

    public String getSong_about() {
        return song_about;
    }

    public void setSong_about(String song_about) {
        this.song_about = song_about;
    }
}
