package com.rumaan.academapp;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Singleton Class for setting custom Typefaces
 *
 * @author Rumaan
 * @version 1.0
 */

public class CustomFont {
    public static final int Regular = 1;
    public static final int Bold = 3;
    public static final int Medium = 2;
    public static final int Light = 4;
    private static final String rootDir = "fonts/";
    private static final String REGULAR = "regular.otf";
    private static final String BOLD = "bold.otf";
    private static final String MEDIUM = "medium.otf";
    private static final String LIGHT = "light.otf";
    private static CustomFont instance;
    private Context mContext;

    private CustomFont(Context context) {
        // don't allow anyone here
        this.mContext = context;
    }

    /* Return only one instance of this class */
    public static CustomFont getInstance(Context context) {
        if (instance == null) {
            instance = new CustomFont(context);
        }
        return instance;
    }

    /**
     * @param style Defines the style of Font face.
     *              Returns Regular font face if style undefined or specified incorrectly.
     */
    public Typeface getTypeFace(int style) {
        switch (style) {
            case 1:
                return createTypeFace(rootDir + REGULAR);
            case 2:
                return createTypeFace(rootDir + MEDIUM);
            case 3:
                return createTypeFace(rootDir + BOLD);
            case 4:
                return createTypeFace(rootDir + LIGHT);
        }
        return createTypeFace(rootDir + REGULAR);
    }

    /**
     * @param type Type of the Font face required
     */
    private Typeface createTypeFace(String type) {
        return Typeface.createFromAsset(mContext.getAssets(), type);
    }
}
