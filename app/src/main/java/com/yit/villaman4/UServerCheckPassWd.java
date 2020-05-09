package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UServerCheckPassWd {

	private UServerCheckPassWd() {
	}

	public static WServerCheck_PassWd with(Context pCon) {
		return new WServerCheck_PassWd(pCon);
	}

	public static class WServerCheck_PassWd {
		
		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		private OptionLoadingDialog dialog;

		private WServerCheck_PassWd(Context pCon) {
			mCon = pCon;
		}
		
		public WServerCheck_PassWd forStart(Callback WServerCheck_PassWd_Callback, String phoneNum) {
			mCallback = WServerCheck_PassWd_Callback;
			
			String data  = "";
			
			try {
				data = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + phoneNum, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			data = data + "&usr_ver=2.3";
            String url = Util.getURL_IT() + "/ahh/select_passwd.do?YESIT="+Util.getBase64encode(data).replace("\n",""); // TODO

			dialog = new OptionLoadingDialog(mCon);
			dialog.showLoading();

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
						dialog.hideLoading();
					}
				}else {
					
					mCallback.onResult(null);
					//Toast.makeText(mCon, "서버 접속이 원할하지 않습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
	}
}
