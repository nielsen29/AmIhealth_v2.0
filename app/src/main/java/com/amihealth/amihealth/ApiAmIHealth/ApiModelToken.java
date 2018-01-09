package com.amihealth.amihealth.ApiAmIHealth;

import com.amihealth.amihealth.BuildConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amihealthmel on 12/03/17.
 */

public class ApiModelToken {

    @SerializedName("client_id")
    private String client_id = "2";

    @SerializedName("client_secret")
    private String client_secret;

    @SerializedName("grant_type")
    private String grant_type = "password";

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public ApiModelToken() {
    }

    public ApiModelToken(String username, String password) {
        this.username = username;
        this.password = password;
        this.client_secret = BuildConfig.AMIHEALTH_APP_ID;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
