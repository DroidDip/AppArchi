package com.droiddip.apparchi.utils;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;

import com.droiddip.apparchi.application.AppArchi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dipanjan Chakraborty on 06-02-2018.
 */

public class DImageUtils {

    private final static String IMAGE_DIRECTORY_NAME = "AppArchiImages";
    private final static String IMAGE_FILE_NAME = "AppArchi_IMG";

    private DImageUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }


    /**
     * Custom method to save a bitmap into internal storage
     *
     * @param bitmap
     * @return Uri
     */
    public static Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(AppArchi.getInstance());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir(IMAGE_DIRECTORY_NAME, MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, IMAGE_FILE_NAME + "_" + System.currentTimeMillis() + ".jpg");

        try {
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }

    /**
     * Custom method to save a bitmap into internal storage
     *
     * @param bitmap
     * @return File
     */
    public static File saveImageToStorage(Bitmap bitmap) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(AppArchi.getInstance());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir(IMAGE_DIRECTORY_NAME, MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, IMAGE_FILE_NAME + "_" + System.currentTimeMillis() + ".jpg");

        try {
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }


        // Return the saved image file
        return file;
    }
}
