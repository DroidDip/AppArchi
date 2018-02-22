package com.droiddip.apparchi.data.network.volley;

import android.graphics.Bitmap;

/**
 * Created by Dipanjan Chakraborty on 06-02-2018.
 */

public interface BitmapDownloadListener {

    void onBitmapDownloaded(Bitmap bitmap);

    void onBitmapDownloadFailed();
}
