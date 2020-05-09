package com.yit.villaman4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class UServer_DetailSearch {

	private static final String __TAG__ = "Userver_Detail♥♥";

	private UServer_DetailSearch() {
	}

	public static WServerDetailSearch with(Context pCon) {
		return new WServerDetailSearch(pCon);
	}

	public static class WServerDetailSearch {

		private Callback mCallback;
		private Context mCon;
		private ServerConnection mServerConnection;
		private JSONArray data;
		private OptionLoadingDialog dialog;

		private String sMess;
		private WServerDetailSearch(Context pCon) {
			mCon = pCon;
		}

		public WServerDetailSearch forStart(Callback WServerDetailSearch_Callback, HashMap<String, String> detailMap) {
			mCallback = WServerDetailSearch_Callback;

			String data  = "";


			try {

				String ma_level2 = null;
				ma_level2 = URLEncoder.encode("" + detailMap.get("ma_level2"), "UTF-8");
				if (ma_level2.equals("7"))
					ma_level2 = "99";

				data = URLEncoder.encode("sido", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("sido"), "UTF-8") + "&"
						+ URLEncoder.encode("gungu", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("gungu"), "UTF-8") + "&"
						+ URLEncoder.encode("dong", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("dong"), "UTF-8") + "&"

                        + URLEncoder.encode("ma_bo_ney1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_bo_ney1"), "UTF-8") + "&"
                        + URLEncoder.encode("ma_bo_ney2", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_bo_ney2"), "UTF-8") + "&"
                        + URLEncoder.encode("ma_month_ney1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_month_ney1"), "UTF-8") + "&"
                        + URLEncoder.encode("ma_month_ney2", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_month_ney2"), "UTF-8") + "&"

						+ URLEncoder.encode("ma_jeon_area1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_jeon_area1"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_jeon_area2", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_jeon_area2"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_level1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_level1"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_level2", "UTF-8") + "=" + ma_level2 + "&"
						+ URLEncoder.encode("ma_room1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_room1"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_room2", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_room2"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_jun_year1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_jun_year1"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_jun_year2", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_jun_year2"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_bld_nm", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_bld_nm"), "UTF-8") + "&"
						+ URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(mCon), "UTF-8") + "&"
						+ URLEncoder.encode("strDist", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + "&"

						+ URLEncoder.encode("ma_search", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_search"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_status1", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_status1"), "UTF-8") + "&"
						+ URLEncoder.encode("ma_gallery", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_gallery"), "UTF-8") + "&"
						+ URLEncoder.encode("mynew02", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("mynew02"), "UTF-8") + "&"
						+ URLEncoder.encode("myconfirm02", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("myconfirm02"), "UTF-8") + "&"

						+ URLEncoder.encode("ma_use_yn", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("ma_use_yn"), "UTF-8") + "&"
						+ URLEncoder.encode("viloldnew", "UTF-8") + "=" + URLEncoder.encode("" + (detailMap.get("viloldnew").equals("전체")?"전월세":detailMap.get("viloldnew")), "UTF-8")  + "&"
						+ URLEncoder.encode("packagename", "UTF-8") + "=" + URLEncoder.encode("" + detailMap.get("packagename"), "UTF-8") + "&"
						+ URLEncoder.encode("poitype", "UTF-8") + "=" +URLEncoder.encode("" + detailMap.get("poitype"), "UTF-8");
				if (detailMap.get("poitype").equals("2")) {
					sMess = "조건 검색";
				} else if (detailMap.get("poitype").equals("3")) { // 주소
					if (!detailMap.get("dong").equals("")) {
						sMess = detailMap.get("dong");
					} else {
						if (!detailMap.get("gungu").equals("")) {
							sMess = detailMap.get("gungu");
						} else {
							if (!detailMap.get("sido").equals("")) {
								sMess = detailMap.get("sido");
							} else  {
								sMess = "주소";
							}
						}
					}
				} else if (detailMap.get("poitype").equals("4")) { // 선택

					if (detailMap.get("ma_search").equals("myitem")) {
						sMess = "나의 매물";
					} else if (detailMap.get("ma_search").equals("mynew")) {
						sMess = "최신 매물";
					} else if (detailMap.get("ma_search").equals("myconfirm")) {
						sMess = "확인 매물";
					} else if (detailMap.get("ma_search").equals("myfavorite")) {
						sMess = "즐겨찾기";
					} else if (detailMap.get("ma_search").equals("myblank")) {
						sMess = "공실 매물";
					} else if (detailMap.get("ma_search").equals("myrecommend")) {
						sMess = "추천 매물";
					}

				} else {
					sMess = "";
				}

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
			void onResult(JSONArray json, String sMess);
		}

		public Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {
					try {
						JSONArray json = (JSONArray) msg.obj;
						if (mCallback != null) {
							mCallback.onResult(json, sMess);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						dialog.hideLoading();
					}
				}else {

					mCallback.onResult(null, sMess);
				}
			}
		};
	}

}
