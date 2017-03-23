package com.rumaan.academapp;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Rumaan on 19-Mar-17.
 */

public class Avatar {
    private Uri uri;
    private Bitmap bitmap;
    private String pathString;
    private int drawableInt;

    public Avatar(Uri uri, Bitmap bitmap, String pathString, int drawableInt) {
        this.uri = uri;
        this.bitmap = bitmap;
        this.pathString = pathString;
        this.drawableInt = drawableInt;
    }

    /**
     * @param drawableInt image in resource directory
     */
    public Avatar(int drawableInt) {
        this.drawableInt = drawableInt;
    }

    /**
     * @param pathString path of the image file
     */
    public Avatar(String pathString) {

        this.pathString = pathString;
    }

    /**
     * @param bitmap raw bitmap of image
     */
    public Avatar(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * @param uri Uri of image file location
     */
    public Avatar(Uri uri) {
        this.uri = uri;
    }

    public Avatar() {
        // default constructor
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPathString() {
        return pathString;
    }

    public void setPathString(String pathString) {
        this.pathString = pathString;
    }

    public int getDrawableInt() {
        return drawableInt;
    }

    public void setDrawableInt(int drawableInt) {
        this.drawableInt = drawableInt;
    }

}
