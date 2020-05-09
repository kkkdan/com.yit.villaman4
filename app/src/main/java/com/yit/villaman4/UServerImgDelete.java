package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class UServerImgDelete {

	private UServerImgDelete() {
	}

	public static WServerImgDelete with(Context pCon) {
		return new WServerImgDelete(pCon);
	}

	public static class WServerImgDelete {
		
		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		private OptionLoadingDialog dialog;

		private WServerImgDelete(Context pCon) {
			mCon = pCon;
		}

		public WServerImgDelete forStart(Callback WServerImgDelete_Callback, HashMap<String, String> detailMap) {
			mCallback = WServerImgDelete_Callback;
			
			String data  = "";
			
			try {
				data = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("usr_id"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_cd", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_cd"), "UTF-8") + "&"
						+ URLEncoder.encode("cd_code", "UTF-8") + "=" + URLEncoder.encode("" + "CG020CD422", "UTF-8") + "&" // 사진삭제
						+ URLEncoder.encode("main_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_id"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_id"), "UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String url = Util.getURL_IT() + "/ahh/deleteImgFile.do?YESIT="+Util.getBase64encode(data).replace("\n","");  // TODO 2.0.8 버젼
			// String url = Util.getURL_IT() + "/ahh/deleteImgFile.do?"+data.replace("\n","");

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
						// dialog.hideLoading();
					}
				}else {
					
					mCallback.onResult(null);
					//Toast.makeText(mCon, "서버 접속이 원할하지 않습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
	}
}
