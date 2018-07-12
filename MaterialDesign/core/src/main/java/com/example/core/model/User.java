package com.example.core.model;

public class User {

    private Long userId;
    private String name;
    private String avatar;
    private Long gender;
    private String channel;
    private String bindAlias;

    public String getBindAlias() {
        return bindAlias;
    }

    public void setBindAlias(String bindAlias) {
        this.bindAlias = bindAlias;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
