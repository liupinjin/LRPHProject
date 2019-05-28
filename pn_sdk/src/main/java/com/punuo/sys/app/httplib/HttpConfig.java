package com.punuo.sys.app.httplib;

/**
 * Created by han.chen.
 * Date on 2019/4/23.
 **/
public class HttpConfig {

    private static boolean isUseHttps = false;
    private static String host = "sip.qinqingonline.com";
    private static int port = 8000;
    private static String userAgent = "punuo";
    private static IHttpConfig sIHttpConfig;

    public static void init(IHttpConfig IHttpConfig) {
        sIHttpConfig = IHttpConfig;
    }

    public static boolean isUseHttps() {
        if (sIHttpConfig != null) {
            return sIHttpConfig.isUseHttps();
        }
        return isUseHttps;
    }

    public static String getHost() {
        if (sIHttpConfig != null) {
            return sIHttpConfig.getHost();
        }
        return host;
    }

    public static int getPort() {
        if (sIHttpConfig != null) {
            return sIHttpConfig.getPort();
        }
        return port;
    }


    public static String getUserAgent() {
        if (sIHttpConfig != null) {
            return sIHttpConfig.getUserAgent();
        }
        return userAgent;
    }
}
