package com.example.musicplayee.general.errors;

/**
 * Invalid assets file exception.
 * Created by Joielechong on 29 November 2016.
 */
public class AudioAssetsInvalidException extends Exception {
    public AudioAssetsInvalidException(String path) {
        super("The file name is not a valid Assets file: " + path);
    }
}
