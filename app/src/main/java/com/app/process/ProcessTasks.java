package com.app.process;

import android.app.Application;

import com.punuo.sys.app.activity.ActivityLifeCycle;
import com.punuo.sys.app.util.DebugCrashHandler;
import com.punuo.sys.app.util.DeviceHelper;

/**
 * Created by han.chen.
 * Date on 2019/4/2.
 **/
public class ProcessTasks {

    public static void commonLaunchTasks(Application app) {
        if (DeviceHelper.isApkInDebug()) {
            DebugCrashHandler.getInstance().init(); //崩溃日志收集
        }
        app.registerActivityLifecycleCallbacks(ActivityLifeCycle.getInstance());
    }
}
