package com.example.domain;

/**
 * Created by Tom on 31.10.2016..
 */

public class Result {

    private final int repoId;

    public Result(int repoId) {
        this.repoId = repoId;
    }

    private String reponame;
    private String username;
    private String avatarUrl;
    private String type;
    private String site_admin;
    private int watchers_count;
    private int forks_count;
    private int open_issues_count;
    private int subscribers_count;
    private String createdAt;
    private String modified;
    private String language;

    public int getRepoId() {
        return repoId;
    }

    public String getRepoName() {
        return reponame;
    }

    public void setRepoName(String reponame) {
        this.reponame = reponame;
    }

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

        stringBuilder.append("***** Result Details *****\n");
        stringBuilder.append("repoId=" + this.getRepoId() + "\n");
        stringBuilder.append("reponame=" + this.getRepoName() + "\n");
        stringBuilder.append("username=" + this.getUserName() + "\n");
        stringBuilder.append("avatarUrl=" + this.getAvatarUrl() + "\n");
        stringBuilder.append("watchers_count=" + this.getWatchers() + "\n");
        stringBuilder.append("forks_count=" + this.getForks() + "\n");
        stringBuilder.append("open_issues_count=" + this.getIssues() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
