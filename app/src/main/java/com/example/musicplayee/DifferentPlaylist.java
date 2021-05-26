package com.example.musicplayee;

public class DifferentPlaylist {
    private String diff_image;
    private String diff_caption;
    public DifferentPlaylist(){

    }
    public DifferentPlaylist(String diff_image,String diff_caption){
        this.diff_image=diff_image;
        this.diff_caption=diff_caption;
    }

    public String getDiff_image() {
        return diff_image;
    }

    public void setDiff_image(String diff_image) {
        this.diff_image = diff_image;
    }

    public String getDiff_caption() {
        return diff_caption;
    }

    public void setDiff_caption(String diff_caption) {
        this.diff_caption = diff_caption;
    }
}
