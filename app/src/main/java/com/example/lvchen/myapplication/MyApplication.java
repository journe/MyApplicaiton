package com.example.lvchen.myapplication;

import android.app.Application;

import cn.idaddy.android.opensdk.lib.IdaddySdk;

/**
 * Created by journey on 2018/8/1.
 */
public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		IdaddySdk.INSTANCE.init(this, false);
	}
}
