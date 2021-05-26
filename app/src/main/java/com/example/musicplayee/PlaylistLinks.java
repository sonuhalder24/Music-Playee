package com.example.musicplayee;

public class PlaylistLinks {
    String song_link;
    String song_name;

    public PlaylistLinks(){

    }

    public PlaylistLinks(String song_link, String song_name) {
        this.song_link = song_link;
        this.song_name = song_name;
    }

    public String getSong_link() {
        return song_link;
    }

    public void setSong_link(String song_link) {
        this.song_link = song_link;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }
}
