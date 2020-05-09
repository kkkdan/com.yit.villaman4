package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class UServer_MoveSearch {

	private static final String TAG = "■■■";

	private UServer_MoveSearch() {
	}

	public static WServerMoveSearch with(Context pCon) {
		return new WServerMoveSearch(pCon);
	}

	public static class WServerMoveSearch {

		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		private OptionLoadingDialog dialog;

		private WServerMoveSearch(Context pCon) {
			mCon = pCon;
		}

		public WServerMoveSearch forStart(Callback WServerMoveSearch_Callback, HashMap<String, String> detailMap) {
			mCallback = WServerMoveSearch_Callback;

			String data  = "";


			try {

				String ma_level2 = null;
				ma_level2 = URLEncoder.encode("" + detailMap.get("ma_level2"), "UTF-8");
				if (ma_level2.equals("7"))
					ma_level2 = "99";

				data = URLEncoder.encode("ma_search", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_search"), "UTF-8") + "&"
						+ URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(mCon), "UTF-8") + "&"
						+ URLEncoder.encode("strLat", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("strLat"), "UTF-8") + "&"
						+ URLEncoder.encode("strLon", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("strLon"), "UTF-8") + "&"
						+ URLEncoder.encode("strDist", "UTF-8") + "=" + URLEncoder.encode(""+ detailMap.get("strDist"), "UTF-8") + "&"

						+ URLEncoder.encode("viloldnew", "UTF-8") + "=" + URLEncoder.encode("" + (detailMap.get("viloldnew").equals("전체")?"전월세":detailMap.get("viloldnew")), "UTF-8")  + "&"
						+ URLEncoder.encode("packagename", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("packagename"), "UTF-8") + "&"
						+ URLEncoder.encode("poitype", "UTF-8") + "=" +URLEncoder.encode("" + detailMap.get("poitype"), "UTF-8");

				Log.e(TAG, "data:WServerMoveSearch:"+data.toString());

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String url = Util.getURL_IT() + "/ahh/selectDetail_N.do?YESIT="+ Util.getBase64encode(data).replace("\n","");

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
				}
			}
		};
	}

}
