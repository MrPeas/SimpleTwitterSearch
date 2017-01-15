package com.example.controller.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Janusz on 12.01.2017.
 */
public class LightTweet {
    private String profileImageUrl;
    private String user;
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    private String lang;
    private Integer retweetCount;

    public LightTweet(String text){
        this.text=text;
    }

    public static LightTweet ofTweet(Tweet tweet){
        LightTweet lightTweet=new LightTweet(tweet.getText());
        Date createAt=tweet.getCreatedAt();
        if (createAt!=null){
            lightTweet.date=LocalDateTime.ofInstant(createAt.toInstant(), ZoneId.systemDefault());
        }
        TwitterProfile profile=tweet.getUser();
        if(profile!=null){
            lightTweet.user=profile.getName();
            lightTweet.profileImageUrl=profile.getProfileImageUrl();
        }
        lightTweet.retweetCount=tweet.getRetweetCount();
        lightTweet.lang=tweet.getLanguageCode();
        return lightTweet;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }
}
