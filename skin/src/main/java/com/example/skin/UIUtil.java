package com.example.skin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

public class UIUtil {
	
	public static int getBtnBgId(){
		return R.drawable.btn_selector;
	}
	public static int getMyActivityBg(){
		return R.drawable.login_bg;
	}
	public static int getTilteString(){
		return R.string.app_name;
	}
	
	public static Drawable getImageDrawable(Context ctx){
		return ctx.getResources().getDrawable(R.drawable.ic_launcher);
	}
	
	public static View getLayout(Context ctx){
		return LayoutInflater.from(ctx).inflate(R.layout.activity_main, null);
	}
	
	public static int getTextStringId(){
		return R.string.app_name;
	}
	
	public static int getImageDrawableId(){
		return R.drawable.ic_launcher;
	}
	
	public static int getLayoutId(){
		return R.layout.activity_main;
	}

}
