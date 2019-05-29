package com.punuo.sys.app.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.punuo.sys.app.PnApplication;

public class ToastUtils {
	private static Toast mToast;

	/**
	 * 为解决 http://mobile.umeng.com/apps/161e9078c5b0426594dc3a35/error_types/show?error_type_id=53a3cd4956240b5c8709e161_7212916063408484620_4.7.0<p/>
	 * 增加try...catch...
	 * 单独setText是为了解决新版MIUI会在text前边加上appName问题
	 * 显示Toast
	 *
	 * @param text
	 */
	public static void showToast(CharSequence text) {
		if (TextUtils.isEmpty(text)) return;

		try {
			if (mToast == null) {
				mToast = Toast.makeText(PnApplication.getInstance(), null, Toast.LENGTH_LONG);
				mToast.setGravity(Gravity.CENTER, 0, 0);
				mToast.setText(text);
			} else {
				mToast.setDuration(Toast.LENGTH_LONG);
				mToast.setText(text);
			}
			mToast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为解决 http://mobile.umeng.com/apps/161e9078c5b0426594dc3a35/error_types/show?error_type_id=53a3cd4956240b5c8709e161_7212916063408484620_4.7.0<p/>
	 * 增加try...catch...
	 * 显示Toast
	 *
	 * @param text
	 */
	public static void showToastShort(CharSequence text) {
		if (TextUtils.isEmpty(text)) return;

		try {
			if (mToast == null) {
				mToast = Toast.makeText(PnApplication.getInstance(), null, Toast.LENGTH_SHORT);
				mToast.setGravity(Gravity.CENTER, 0, 0);
				mToast.setText(text);
			} else {
				mToast.setDuration(Toast.LENGTH_SHORT);
				mToast.setText(text);
			}
			mToast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showToast(int resId) {
		showToast(PnApplication.getInstance().getResources().getText(resId));
	}

	public static void closeToast() {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
	}
}
