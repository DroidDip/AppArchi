/**
 * Copyright 2017 Dipanjan Chakraborty (chakraborty.dipanjan07@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.music.arts.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * DStorageUtils
 *
 * @author Dipanjan Chakraborty
 */

public class DStorageUtils {

    private static String APP_FOLDERNAME = ".MMAapp";
    private static String folderPath;
    public static String APP_CACHE_DIRECTORY_PATH = ".MMAapp/cache/images";


    private DStorageUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Check the SD card
     *
     * @return true if SD card is present
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * Set App Folder Name
     *
     * @param folderName
     */
    public static void setFolderName(String folderName) {
        if (!TextUtils.isEmpty(folderName))
            APP_FOLDERNAME = folderName;
        else
            throw new Error("Folder Name should not be empty");
    }

    /**
     * Get App Folder Path
     *
     * @param context
     * @return
     */
    public static String getFolderPath(Context context) {
        if (checkSDCardAvailable()) {
            try {
                File sdPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_FOLDERNAME);
                if (!sdPath.exists()) {
                    sdPath.mkdirs();
                    folderPath = sdPath.getAbsolutePath();
                } else if (sdPath.exists()) {
                    folderPath = sdPath.getAbsolutePath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            folderPath = Environment.getExternalStorageDirectory().getPath() + File.separator + APP_FOLDERNAME;
        } else {
            try {
                File internalDir = new File(context.getFilesDir(), APP_FOLDERNAME);
                if (!internalDir.exists()) {
                    internalDir.mkdirs();
                    folderPath = internalDir.getAbsolutePath();
                } else if (internalDir.exists()) {
                    folderPath = internalDir.getAbsolutePath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return folderPath;
    }

    /**
     * Create a file under the folder path
     *
     * @param _folderPath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createFile(String _folderPath, String fileName) throws IOException {

        if (TextUtils.isEmpty(_folderPath))
            throw new Error("Folder Path is empty");

        if (TextUtils.isEmpty(fileName))
            throw new Error("File Name is empty");

        File logFile = new File(_folderPath, fileName);
        if (!logFile.exists())
            logFile.createNewFile();

        return logFile;
    }

    /**
     * Override a existing file
     *
     * @param _folderPath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File overWriteFile(String _folderPath, String fileName) throws IOException {

        if (TextUtils.isEmpty(_folderPath))
            throw new Error("Folder Path is empty");

        if (TextUtils.isEmpty(fileName))
            throw new Error("File Name is empty");

        File logFile = new File(_folderPath, fileName);
        logFile.createNewFile();

        return logFile;
    }

    /**
     * Read file from a given folder path
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        byte[] buffer = null;
        try {
            if (checkSDCardAvailable()) {
                String filePaht = filePath + "/" + fileName;
                FileInputStream fin = new FileInputStream(filePaht);
                int length = fin.available();
                buffer = new byte[length];
                fin.read(buffer);
                fin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Delete file from SD Card
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean deleteFilefromSDCard(String filePath, String fileName) {
        File file = new File(filePath + File.separator + fileName);
        if (!file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }

}
