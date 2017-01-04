package com.example.administrator.skipchangedemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends BaseActivity {

	/**
	 * ��Ҫ�滻����Ŀؼ� ������о�������TextView,ImageView,LinearLayout
	 */
	private LinearLayout ll_bg;
	private TextView tv_title, tv_skin1, tv_skin2;
	
	private int titleID, btn_bg, activty_bg;   //��ԴID
	
	/**
	 * �������
	 */
	protected DexClassLoader classLoader = null;
	/**
	 * security check
	 */
	private SecurityChecker mSecurityChecker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_skin1 = (TextView) findViewById(R.id.tv_skin1);
		tv_skin2 = (TextView) findViewById(R.id.tv_skin2);
		ll_bg = (LinearLayout) findViewById(R.id.ll_bg);
		mSecurityChecker = new SecurityChecker(mContext);
		tv_skin1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadResources(null);
				loadLocalSkin();
			}
		});
		tv_skin2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String sdPath = Environment.getExternalStorageDirectory().getPath();
				String filePath = sdPath + File.separator +"skinchange"+ File.separator + "SkinChange2Skin.apk";

				if (!mSecurityChecker.verifyApk(new File(filePath))) {// security check fail
					return;
				}
				classLoader = new DexClassLoader(filePath, getDir("dex", 0)
						.getAbsolutePath(), null, getClassLoader());
				loadResources(filePath);
				loadOtherSkin();
				//loadOtherSkin1();
			}
		});
	}
	
	private void loadLocalSkin(){
		titleID = R.string.app_name;
		btn_bg = R.drawable.btn_selector;
		activty_bg = R.drawable.activity_bg;
		showSkin();
	}
	
	@SuppressLint("NewApi") 
	private void showSkin(){
		tv_title.setText(getResources().getString(titleID));
		tv_skin1.setBackground(getResources().getDrawable(btn_bg));
		tv_skin2.setBackground(getResources().getDrawable(btn_bg));
		ll_bg.setBackground(getResources().getDrawable(activty_bg));
	}
	
	@SuppressLint("NewApi") 
	private void loadOtherSkin(){
		try {
			Class clazz = classLoader.loadClass("com.example.skin.UIUtil");
			Method method = clazz.getMethod("getTilteString");
			titleID = (int) method.invoke(null);
			method = clazz.getMethod("getBtnBgId");
			btn_bg = (int) method.invoke(null);
			method = clazz.getMethod("getMyActivityBg");
			activty_bg = (int) method.invoke(null);
			showSkin();
		} catch (Exception e) {
			Log.e(TAG, "��ȡʧ�ܣ�" + Log.getStackTraceString(e));
		}
	}
	
	
	/**
	 * �����һ�ַ�ʽ��ȡ
	 */
	private void loadOtherSkin1() {
		titleID = getTilteString();
		btn_bg = getDrawableId("btn_selector");
		activty_bg = getDrawableId("activity_bg");
		Log.i("Loader", "titleID:" + titleID + ",btn_bg:" + btn_bg
				+ ",activty_bg:" + activty_bg);
		showSkin();
	}

	@SuppressLint("NewApi")
	private int getTilteString() {
		try {
			Class clazz = classLoader.loadClass("com.example.skin.R$string");
			Field field = clazz.getField("app_name");
			int resId = (int) field.get(null);
			return resId;
		} catch (Exception e) {
			Log.i("Loader", "error:" + Log.getStackTraceString(e));
		}
		return 0;
	}

	@SuppressLint("NewApi")
	private int getDrawableId(String name) {
		try {
			Class clazz = classLoader.loadClass("com.example.skin.R$drawable");
			Field field = clazz.getField("ic_launcher");
			int resId = (int) field.get(null);
			return resId;
		} catch (Exception e) {
			Log.i("Loader", "error:" + Log.getStackTraceString(e));
		}
		return 0;
	}

}
