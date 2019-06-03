package com.app.request;

import com.app.model.PNBaseModel;
import com.punuo.sys.app.httplib.BaseRequest;

/**
 * Created by han.chen.
 * Date on 2019-06-03.
 **/
public class RegisterRequest extends BaseRequest<PNBaseModel> {

    public RegisterRequest() {
        setRequestType(RequestType.GET);
        setRequestPath("/register");
    }
}
