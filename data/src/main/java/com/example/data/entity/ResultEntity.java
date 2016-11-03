package com.example.data.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultEntity {

    @SerializedName("id")
    private int repoId;

    @SerializedName("full_name")
    private String reponame;

    @SerializedName("watchers_count")
    private int watchers_count;

    @SerializedName("forks_count")
    private int forks_count;

    @SerializedName("subscribers_count")
    private int subscribers_count;

    @SerializedName("open_issues_count")
    private int open_issues_count;

    @SerializedName("owner")
    private Owner owner;

    public class Owner{

        @SerializedName("login")
        private String username;

        @SerializedName("avatar_url")
        private String avatarUrl;

        @SerializedName("type")
        private String type;

        @SerializedName("site_admin")
        private String site_admin;

        public String getUserName() {
            return username;
        }

        public void setUserName(String username) {
            this.username = username;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getUserType() {
            return type;
        }

        public void setUserType(String type) {
            this.type = type;
        }

        public String getSiteAdmin() {
            return site_admin;
        }

        public void setSiteAdmin(String site_admin) {
            this.site_admin = site_admin;
        }


    }

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String modified;

    @SerializedName("language")
    private String language;

    public ResultEntity() {
        //empty
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return reponame;
    }

    public void setRepoName(String reponame) {
        this.reponame = reponame;
    }

    public int getSubscribers() {
        return subscribers_count;
    }

    public void setSubscribers(int subscribers_count) {
        this.subscribers_count = subscribers_count;
    }

    public int getWatchers() {
        return watchers_count;
    }

    public void setWatchers(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public int getForks() {
        return forks_count;
    }

    public void setForks(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getIssues() {
        return open_issues_count;
    }

    public void setIssues(int open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** ResultEntity Details *****\n");
        stringBuilder.append("repoId=" + this.getRepoId() + "\n");
        stringBuilder.append("reponame=" + this.getRepoName() + "\n");
        stringBuilder.append("username=" + owner.getUserName() + "\n");
        stringBuilder.append("avatarUrl=" + owner.getAvatarUrl() + "\n");
        stringBuilder.append("watchers_count=" + this.getWatchers() + "\n");
        stringBuilder.append("forks_count=" + this.getForks() + "\n");
        stringBuilder.append("open_issues_count=" + this.getIssues() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}


