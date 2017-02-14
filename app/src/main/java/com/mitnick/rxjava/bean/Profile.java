package com.mitnick.rxjava.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Entity mapped to table "PROFILE".
 */
public class Profile {
    // KEEP FIELDS - put your custom fields here
    @SerializedName("id")
    private String profile_id;
    // KEEP FIELDS END
    private String username;
    private String email;
    private int weight;
    private int height;
    private int gender;
    private String birthday;
    private String image_url;
    private String image_sas;
    private String createdat;


    public Profile() {
    }

    public Profile(String profile_id) {
        this.profile_id = profile_id;
    }

    public Profile(String profile_id, String username, String email, int weight, int height, int gender, String birthday, String image_url, String image_sas, String createdat) {
        this.profile_id = profile_id;
        this.username = username;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.birthday = birthday;
        this.image_url = image_url;
        this.image_sas = image_sas;
        this.createdat = createdat;
    }


    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_sas() {
        return image_sas;
    }

    public void setImage_sas(String image_sas) {
        this.image_sas = image_sas;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }


}
