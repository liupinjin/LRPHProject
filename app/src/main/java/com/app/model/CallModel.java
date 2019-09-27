package com.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallModel extends PNBaseModel {
//    @SerializedName("data")
//    public Data mData;
//
//    public static class Data {
//        @SerializedName("phones")
//        public List<String> mPhones;
//    }

    @SerializedName("id")
    public int id;

    @SerializedName("devid")
    public String devid;

    @SerializedName("housekeep")
    public String housekeep;

    @SerializedName("orderfood")
    public String orderfood;

    @SerializedName("property")
    public String property;

}
