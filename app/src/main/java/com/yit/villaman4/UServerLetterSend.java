package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class UServerLetterSend {

	private UServerLetterSend() {
	}

	public static WServerLetterSend with(Context pCon) {
		return new WServerLetterSend(pCon); // 1번
	}

	public static class WServerLetterSend {

		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		//private UServreLoadingDialog dialog;

		private WServerLetterSend(Context pCon) {
			mCon = pCon;
		}
		
		public WServerLetterSend forStart(Callback WServerLetterSend_Callback, HashMap<String, String> locMap) {
			mCallback = WServerLetterSend_Callback; // 2번
			
			String data  = "";
			try {
				data = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(mCon), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String url = Util.getURL_IT() + "/ahh/selectLetterSend.do?YESIT="+ Util.getBase64encode(data).replace("\n",""); // TODO

			mServerConnection = new ServerConnection(mCon, "GET", mHandler, url);
			mServerConnection.start();
			return this;
		}
		
		public static interface Callback {
			void onResult(JSONArray json);
		}
		
		public Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {
					try {
						JSONArray json = (JSONArray) msg.obj;
						if (mCallback != null) {
							mCallback.onResult(json);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						//dialog.hideLoading();
					}
				}else {
					
					mCallback.onResult(null);
				}
			}
		};
	}
	
	
	

}
