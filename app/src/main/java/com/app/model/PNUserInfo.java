package com.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by han.chen.
 * Date on 2019/5/28.
 **/
public class PNUserInfo extends PNBaseModel {

    @SerializedName("user")
    public UserInfo userInfo;

    public static class UserInfo {
        /**
         "id": 410,
         "userid": "321000000000594992",
         "name": "18758256058",
         "password": "sha1$7e219$5684a7116c0d2ba8c7a4127dddf3173449f448cf",
         "avatar": "9f061ee9ac3c66395060498cc65759e0.jpg",
         "nickname": "18758256058",
         "is_login": null,
         "gender": null,
         "city": null,
         "notify": null,
         "devList": []
         */

        @SerializedName("id")
        public String id;
        @SerializedName("userid")
        public String userId;
        @SerializedName("name")
        public String name;
        @SerializedName("password")
        public String password;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("gender")
        public String gender;
        @SerializedName("notify")
        public String isNotify;
    }

}
