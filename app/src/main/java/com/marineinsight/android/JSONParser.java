package com.marineinsight.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krrishnaaaa on Jun 24, 2015
 */
public class JSONParser {

    public List<PostValue> parse(JSONObject jsonObject) {
        List<PostValue> postList = new ArrayList<>();
        PostValue postValue;
        String imageURL;
        try {
            JSONArray postsArray = jsonObject.getJSONArray("posts");

            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject posts = postsArray.getJSONObject(i);
                //JSONObject post = posts.getJSONObject("post");

                postValue = new PostValue();

                String title = posts.getString("title");
                String postUrl = posts.getString("url");
                String content = posts.getString("content");
                //String thumbnail_url = posts.getString("thumbnail");
                JSONObject bigImage = posts.getJSONObject("thumbnail_images");
//                if(i==0) {
                    JSONObject tiMed = bigImage.getJSONObject("medium");
                    imageURL = tiMed.getString("url");
                    //System.out.println("MEDIUM:"+imageURL);
//                }
//                else {
//                    JSONObject tiMed1 = bigImage.getJSONObject("related");
//                    imageURL = tiMed1.getString("url");
//                    System.out.println("RELATED:"+imageURL);
//                }


                postValue.setTitle(title);
                postValue.setGuid(postUrl);
                postValue.setContent(content);
                postValue.setThumbnailUrl(imageURL);
                postList.add(postValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postList;
    }

}
