package com.yit.villaman4;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialog extends Dialog{

	private boolean isSoftInputMode = false;

	public BaseDialog(Context context, int theme) { 
		super(context, theme);		
		setCancelable(true); 
		setCanceledOnTouchOutside(true);  
	}
	
	public BaseDialog(Context context, int theme, boolean isSoftInputMode) {
		super(context, theme);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.isSoftInputMode = isSoftInputMode;
		setCancelable(true); 
		setCanceledOnTouchOutside(true);  
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initCustom();
		if(isSoftInputMode) {
			initSoftInputMode();
		} 	
	}

	private void initSoftInputMode() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);	
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);	
	}
	
	private void initCustom() {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.38f;	
		//lpWindow.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		//lpWindow.format = PixelFormat.TRANSLUCENT;
		getWindow().setAttributes(lpWindow);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//getWindow().setGravity(Gravity.CENTER);		
	}
	
}
