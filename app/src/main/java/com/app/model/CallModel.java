package com.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallModel extends PNBaseModel {
    @SerializedName("data")
    public Data mData;

    public static class Data {
        @SerializedName("phones")
        public List<String> mPhones;
    }
}
