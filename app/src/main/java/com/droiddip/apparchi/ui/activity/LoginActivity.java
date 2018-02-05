package com.droiddip.apparchi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.droiddip.apparchi.utils.DConnectionUtils;
import com.droiddip.apparchi.utils.DLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Dipanjan Chakraborty on 05-02-2018.
 */

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private String facebook_id = "", facebook_name = "", facebook_email = "", facebook_profile = "",
            facebook_cover = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Initialize Facebook SDK
     */
    private void initFB() {
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
    }

    /**
     * Method to initiate FB Login procedure on click of FB login button
     */
    private void doFbLogin() {
        if (!DConnectionUtils.checkConnection(LoginActivity.this)) {
            //Show Network error message
            return;
        }

        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile, email, user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //You can show here a progress bar to parse the result returned from FB Login
                        getFacebookLoginResult(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        //FB Login Cancel
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //FB Login Error
                    }
                });
    }

    /**
     * Get User Details from FB Login Result
     *
     * @param loginResult
     */
    public void getFacebookLoginResult(LoginResult loginResult) {
        final String accessToken = loginResult.getAccessToken().getToken();

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {

                            facebook_id = object.getString("id");

                            facebook_name = object.getString("name");

                            try {
                                facebook_email = object.getString("email");
                            } catch (Exception e) {
                                e.printStackTrace();
                                facebook_email = "";
                            }
                            facebook_profile = "https://graph.facebook.com/" + facebook_id + "/picture?type=large";
                            facebook_cover = "https://graph.facebook.com/" + facebook_id + "?fields=cover&access_token=" + accessToken;

//                            new FbCoverAsync(mContext, facebook_cover, facebook_profile, new FbCoverAsync.FbCoverImgListener() {
//                                @Override
//                                public void onSuccess(File cover_file, File profile_file) {
//                                    if (isViewAttached()) {
//                                        getView().hideProgressLoading();
//                                        getView().onFacebookResult(facebook_name, facebook_email, facebook_id, cover_file, profile_file);
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onFailure() {
//                                    if (isViewAttached()) {
//                                        getView().hideProgressLoading();
//                                        getView().onFacebookResult(facebook_name, facebook_email, facebook_id, null, null);
//                                    }
//
//                                }
//                            }).execute();


                        } catch (Exception e) {
                            e.printStackTrace();
//                            if (isViewAttached()) {
//                                getView().hideProgressLoading();
//                                getView().onFacebookResult(facebook_name, facebook_email, facebook_id, null, null);
//                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
