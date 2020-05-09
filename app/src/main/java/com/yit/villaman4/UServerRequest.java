package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class UServerRequest {

	private static final String TAG = "■■■";

	private UServerRequest() {
	}

	public static WServerRequest with(Context pCon) {
		return new WServerRequest(pCon);
	}

	public static class WServerRequest {
		
		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		private OptionLoadingDialog dialog;

		private WServerRequest(Context pCon) {
			mCon = pCon;
		}

		public WServerRequest forStart(Callback WServerRequest_Callback, HashMap<String, String> detailMap) {
			mCallback = WServerRequest_Callback;
			
			String data  = "";
			
			try {
				data = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("usr_id"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_cd", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_cd"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_id"), "UTF-8") + "&"
						+ URLEncoder.encode("memo", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("memo"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String url = "";
			String strMaCd = detailMap.get("ma_cd");

			if (strMaCd.equals("DEL")) {
				try {
					data = data + "&" + URLEncoder.encode("main_id", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_id"), "UTF-8"); // 매매완료 확인
					data = data + "&" + URLEncoder.encode("cd_code", "UTF-8") + "=" + URLEncoder.encode("" + "CG020CD824", "UTF-8"); // 매매완료 확인
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url = Util.getURL_IT() + "/app/saveLogAppDel.do?YESIT="+ Util.getBase64encode(data).replace("\n",""); // TODO

			} else {
				url = Util.getURL_IT() + "/app/saveLogApp.do?YESIT="+ Util.getBase64encode(data).replace("\n",""); // TODO
			}

			Log.e(TAG,   "♣♣♣ url"+url);

			// dialog = new OptionLoadingDialog(mCon);
			// dialog.showLoading();

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
