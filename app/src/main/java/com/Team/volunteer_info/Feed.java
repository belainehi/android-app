package com.Team.volunteer_info;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Feed extends AppCompatActivity {
    final private String TAG = "Feed";
    private String uName;
    private String description;
    private String image;
    private String postID;
    private String uId;
    private String name;
    private String userImage;



    public Feed(){
    }
    public Feed(String uName,String description,String image,String postID,String uId, String name, String userImage){
        this.userImage = userImage;
        this.uName = uName;
        this.uId = uId;
        this.description = description;
        this.image = image;
        this.postID = postID;
        this.name = name;
    }

    public String getUserImage() {
        Log.d(TAG, "getuimage: "+userImage);
        return userImage;
    }

    public void setUserImage(String userImage) {
//        Log.d(TAG, "getuimage: "+userImage);
        this.userImage = userImage;

    }
    public String getuName() {
        Log.d(TAG, "getuName: "+uName);
        return uName;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {

        Log.d(TAG, "IMAGE: "+image);
        return image;
    }

    public String getPostID() {
        return postID;
    }

    public String getuId() {
        return uId;
    }

    public String getName(){
        return name;
    };
}
