package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


public class UServerGetDong {

	private UServerGetDong() {
	}

	public static WServerGetDong with(Context pCon) {
		return new WServerGetDong(pCon);
	}
	
	public static class WServerGetDong {
		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;

		private WServerGetDong(Context pCon) {
			mCon = pCon;
		}
		
		public WServerGetDong forStart(Callback WServerGetDong_Callback, HashMap<String, String> dongMap) {
			mCallback = WServerGetDong_Callback;
			
			String data  = "";
			try {

				// data = URLEncoder.encode("sido", "UTF-8") + "=" +  dongMap.get("sido") + "&"
				//		+ URLEncoder.encode("gungu", "UTF-8") + "=" +  dongMap.get("gungu") + "&"
				data = URLEncoder.encode("sido", "UTF-8") + "=" + URLEncoder.encode("" + dongMap.get("sido"), "UTF-8") + "&"
					    + URLEncoder.encode("gungu", "UTF-8") + "=" + URLEncoder.encode("" + dongMap.get("gungu"), "UTF-8")+ "&"
						+ URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(mCon), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String url = Util.getURL_IT() + "/app/selectDong.do?YESIT="+ Util.getBase64encode(data).replace("\n",""); // TODO

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
					}
				}else {
					
					mCallback.onResult(null);
					//Toast.makeText(mCon, "서버 접속이 원할하지 않습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
				}
			}
		};
	}
}
