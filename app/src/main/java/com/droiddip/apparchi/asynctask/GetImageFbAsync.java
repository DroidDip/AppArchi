//package com.droiddip.apparchi.asynctask;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//
//import com.athlepic.app.R;
//import com.athlepic.app.utils.AthlepicUtils;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//
//import java.io.File;
//
///**
// * Created by Dipanjan Chakraborty on 05-02-2018.
// */
//public class GetImageFbAsync extends AsyncTask<Void, Void, Void> {
//
//    private Context mContext;
//    private String profileImageUrl, coverImageUrl;
//    private GetImageFbListener callback;
//    private String finalCoverPhoto;
//    private File cover_image = null, profile_file = null;
//
//    public GetImageFbAsync(Context context, String url, String profile, GetImageFbListener listner) {
//        this.mContext = context;
//        this.profileImageUrl = url;
//        this.coverImageUrl = profile;
//        this.callback = listner;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//
//        try {
//            callback.onFbImageSuccess(cover_image, profile_file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//
//        try {
//
//            HttpClient hc = new DefaultHttpClient();
//            HttpGet get = new HttpGet(profileImageUrl);
//            HttpResponse rp = hc.execute(get);
//
//            if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                String result = EntityUtils.toString(rp.getEntity());
//
//                JSONObject JODetails = new JSONObject(result);
//
//                if (JODetails.has("cover")) {
//                    String getInitialCover = JODetails.getString("cover");
//
//                    if (getInitialCover.equals("null")) {
//                        finalCoverPhoto = null;
//                    } else {
//                        JSONObject JOCover = JODetails.optJSONObject("cover");
//
//                        if (JOCover.has("source")) {
//                            finalCoverPhoto = JOCover.getString("source");
//
//                            Bitmap bm = AthlepicUtils.getBitmapFromURL(finalCoverPhoto);
//                            cover_image = AthlepicUtils.getFileFromBitmap(mContext, bm, mContext.getResources().getString(R.string.file_fb_cover_img));
//
//                        } else {
//                            finalCoverPhoto = null;
//                        }
//                    }
//                } else {
//                    finalCoverPhoto = null;
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            Bitmap bm = AthlepicUtils.getBitmapFromURL(coverImageUrl);
//            profile_file = AthlepicUtils.getFileFromBitmap(mContext, bm, mContext.getResources().getString(R.string.file_fb_profile_img));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//    public interface GetImageFbListener {
//        void onFbImageSuccess(File cover_file, File profile_file);
//
//        void onFbImageFailed();
//    }
//}
//
//
//
//
