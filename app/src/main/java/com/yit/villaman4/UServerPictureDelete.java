package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UServerPictureDelete {

	private UServerPictureDelete() {
	}

	public static WServerPictureDelete with(Context pCon) {
		return new WServerPictureDelete(pCon);
	}

	public static class WServerPictureDelete {

		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		//private UServreLoadingDialog dialog;

		private WServerPictureDelete(Context pCon) {
			mCon = pCon;
		}
		
		public WServerPictureDelete forStart(Callback WServerCurrentPosition_Callback, String main_id) {
			mCallback = WServerCurrentPosition_Callback;
			
			String p_usr_id = Util.getPhoneNumber(mCon);
			String p_main_id = main_id;
			String data  = "";
			try {
				data = URLEncoder.encode("rgst_id", "UTF-8") + "=" + URLEncoder.encode(p_usr_id, "UTF-8") + "&"
				      + URLEncoder.encode("main_id", "UTF-8") + "=" + URLEncoder.encode(p_main_id, "UTF-8") + "&"
				      + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(mCon), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

            String url = Util.getURL_IT() + "/vil/delFile.do?YESIT="+ Util.getBase64encode(data).replace("\n",""); // TODO

			//dialog = new UServreLoadingDialog(mCon);
			//dialog.showLoading();
		

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