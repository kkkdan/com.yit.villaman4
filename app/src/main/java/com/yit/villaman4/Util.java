package com.yit.villaman4;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class Util {
	private static final String 		 __TAG__ = "Util:■■■";

	public static String getURL_IT()  { return "http://yes-it.kr"; }
	public static String getURL_VIL() { return "http://yes-vil.kr"; }

	public static String strLetterSsendYn;
	public static String strBoardCnt;
	public static String strStandbyCnt;
	public static String strBoardMessage;
	public static String strPayment = "";
	public static String strName = "";
	public static String strPayAccount = "";
	public static String strType = "";
	public static String strAuthId = "";

	public static String glVersion = "";
	public static String glVername = "";

	public static final String WIFI_STATE = "WIFE";
	public static final String MOBILE_STATE = "MOBILE";
	public static final String NONE_STATE = "NONE";

	public static void expand(final View v) {

		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 1;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT : (int) (targtetHeight * (interpolatedTime));
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setDuration(500);
		v.startAnimation(a);
	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setDuration(500);
		v.startAnimation(a);
	}

	public static String getPhoneNumber(Context context){
		TelephonyManager systemService = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		if ( Build.VERSION.SDK_INT >= 23 &&
				ContextCompat.checkSelfPermission( context, android.Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
		} else {

		}
		// 기종에 따라 +82가 붙는 경우가 있어 0으로 바꾼다.

		String PhoneNum = "01000000000";
		try {
			PhoneNum = systemService.getLine1Number();    //폰번호를 가져오기

			// 2020.03.18 추가
			PhoneNum = PhoneNum.substring(PhoneNum.length()-10,PhoneNum.length());
			PhoneNum="0"+PhoneNum;


		}catch (Exception d){

		}

		if(PhoneNum == null) {
			PhoneNum = "01000000000";
		}
//        else {
//        	PhoneNum = PhoneNum.replace("+82","0");
//        }
		return PhoneNum;
	}


	public static void showToast(AppCompatActivity activity, final String text) {
		Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(AppCompatActivity activity, final String text, boolean bLong) {
		Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}


	public static int getCurYear() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
		return Integer.parseInt(CurYearFormat.format(date));
	}


	public static String getHanGul(String str) {
		StringBuffer hangul = new StringBuffer();
		for(int i = 0; i < str.length(); i++) {
			if(checkHan(str.charAt(i))) {
				hangul.append(str.charAt(i));
			}
		}
		return hangul.toString();
	}

	// 들어온 문자열이 한글의 유니코드(0xAC00 ~ 0xD743) 범위에 들어오는지 판단
	public static boolean checkHan(char cValue) {
		if(cValue >= 0xAC00 && cValue <= 0xD743) {
			return true;
		}
		else {
			return false;
		}
	}

	public static String   openHttpURLConnection(String requestURL){
		String result = "";

		URL url =null;
		HttpURLConnection http = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try{
			url = new URL(requestURL);
			http = (HttpURLConnection) url.openConnection();
			http.setConnectTimeout(3*1000);
			http.setReadTimeout(3*1000);

			isr = new InputStreamReader(http.getInputStream());
			br = new BufferedReader(isr);

			String str = null;
			while ((str = br.readLine()) != null) {
				result += str + "\n";
			}

		}catch(Exception e){

		}finally{
			if(http != null){
				try{http.disconnect();}catch(Exception e){}
			}

			if(isr != null){
				try{isr.close();}catch(Exception e){}
			}

			if(br != null){
				try{br.close();}catch(Exception e){}
			}
		}
		return result;
	}


	/**
	 * Base64 인코딩
	 */
	public static String getBase64encode(String content){
		return Base64.encodeToString(content.getBytes(), 0);
	}

	/**
	 * Base64 디코딩
	 */
	public static String getBase64decode(String content){
		return new String(Base64.decode(content, 0));
	}

	/**
	 * getURLEncode
	 */
	public static String getURLEncode(String content){
		try {
//          return URLEncoder.encode(content, "utf-8");   // UTF-8
			return URLEncoder.encode(content, "euc-kr");  // EUC-KR
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getURLDecode
	 */
	public static String getURLDecode(String content){
		try {
//          return URLDecoder.decode(content, "utf-8");   // UTF-8
			return URLDecoder.decode(content, "euc-kr");  // EUC-KR
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bun = msg.getData();
			//String naverHtml = bun.getString("NAVER_HTML");
			//tvNaverHtml.setText(naverHtml);
		}
	};


	/**
	 * Remove 'null' fields from a JSONObject. This method calls itself recursively until all the
	 * fields have been looked at.
	 * DODO(mlamouri): move to some util class?
	 */
	public static void removeNullFields(Object object) throws JSONException {
		if (object instanceof JSONArray) {
			JSONArray array = (JSONArray) object;
			for (int i = 0; i < array.length(); ++i) removeNullFields(array.get(i));
		} else if (object instanceof JSONObject) {
			JSONObject json = (JSONObject) object;
			JSONArray names = json.names();
			if (names == null) return;
			for (int i = 0; i < names.length(); ++i) {
				String key = names.getString(i);
				if (json.isNull(key)) {
					json.remove(key);
				} else {
					removeNullFields(json.get(key));
				}
			}
		}
	}


	public static void setPayment(final MainActivity mActivity, final TextView tvName, final TextView tvPayment,
								  final TextView tvPayBank,
								  final TextView data_main_board,
								  final TextView txt_main_board,
								  final TextView data_main_standby,
								  final LinearLayout ll_info_bar,
								  String sphoneNumber)  {

		// String phoneNumber = Util.getPhoneNumber(mActivity);

		if(sphoneNumber == null || "".equals(sphoneNumber))
			sphoneNumber = "00000000000";



//		boolean sb = false;
//		sb = isConnectedNetwork(mActivity);
//		if (sb == true) {
//			// Toast.makeText(mActivity, "network ok.......", Toast.LENGTH_SHORT).show();
//		} else {
//			Toast.makeText(mActivity, "인터넷에 연결 되지 않았습니다.", Toast.LENGTH_SHORT).show();
//		}

		UServerCheckPayment.with(mActivity).forStart(new UServerCheckPayment.WServerCheck_Payment.Callback() {

			public void onResult(JSONArray json) {

				String strPayment_ = "0";
				String strName_ = "";
				String strLetterSsendYn_ = "N";
				String strBoard_Cnt_ = "0";
				String strStandby_Cnt_ = "";
				String strBoard_Message_ = "";
				String strPay_Account_ = "";
				String strType_ = "";
				String strAuth_Id_ = "";
				try {

					if (json != null && json.length() > 0) {
						strPayment_ = json.getJSONObject(0).get("pay_amount").toString();
						strName_ = json.getJSONObject(0).get("name").toString();
						strLetterSsendYn_ = json.getJSONObject(0).get("letter_send_yn").toString();
						strBoard_Cnt_ = json.getJSONObject(0).get("board_cnt").toString();
						strStandby_Cnt_ = json.getJSONObject(0).get("standby_cnt").toString();
						strBoard_Message_ = json.getJSONObject(0).get("board_message").toString();
						strPay_Account_ = json.getJSONObject(0).get("pay_account").toString();
						strType_ = json.getJSONObject(0).get("type").toString();
						strAuth_Id_ = json.getJSONObject(0).get("auth_id").toString();

					} else {
						strPayment_ = "0";
						strName_ = "(비회원)";
						strLetterSsendYn_ = "N";
						strBoard_Cnt_ = "0";
						strStandby_Cnt_ = "";
						strBoard_Message_ = "";
						strPay_Account_ = "";
						strType_ = "";
						strAuth_Id_ = "";

					}
				} catch (JSONException e) {
					// } catch (Exception e) {
					e.printStackTrace();
				} finally {
					//finish();
					strPayment =  strPayment_;
					strName =  strName_;
					strLetterSsendYn = strLetterSsendYn_;
					strBoardCnt = strBoard_Cnt_;
					strStandbyCnt = strStandby_Cnt_;
					strBoardMessage = strBoard_Message_;
					strPayAccount = strPay_Account_;
					strType = strType_;
					strAuthId = strAuth_Id_;
				}

				tvName.setText(strName);
				tvPayment.setText(strPayment);
				tvPayBank.setText("충전하기");


//				try {
//
//					Integer ii = Integer.parseInt(strPayment.replace(",",""));
//					// 유료.무료 회원에 따라 광고를 보여준다.
//					if (ii > 0 )
//					{
//						ll_info_bar.setVisibility(View.GONE); // 유료 회원은 광고를 보이지 않는다.
//					}else{
//						ll_info_bar.setVisibility(View.VISIBLE); // 무료 회원
//					}
//				} catch  (Exception e) {
//
//					Toast.makeText(mActivity, "잔액 오류 입니다.", Toast.LENGTH_SHORT).show();
//					e.printStackTrace();
//				} finally {
//				}

				strLetterSsendYn = strLetterSsendYn_;

				try {
					if (strBoard_Cnt_.equals("0")) {
						data_main_board.setText(""); // 게시판 조회 건수
						txt_main_board.setText(""); // 게시판 내용
					} else {
						if (Integer.parseInt(strBoard_Cnt_) >= 1 && Integer.parseInt(strBoard_Cnt_) <= 15) {
							strBoard_Cnt_ = strBoard_Cnt_.replace("10","⑩").replace("11","⑪").replace("12","⑫")
									.replace("13","⑬").replace("14","⑭").replace("15","⑮")
									.replace("1","①").replace("2","②").replace("3","③")
									.replace("4","④").replace("5","⑤").replace("6","⑥")
									.replace("7","⑦").replace("8","⑧").replace("9","⑨");

							data_main_board.setText(strBoard_Cnt_); // 게시판 조회 건수
						} else {
							data_main_board.setText(strBoard_Cnt_); // 게시판 조회 건수
						}

						txt_main_board.setText(strBoardMessage); // 게시판 조회 메시지

					}

					// 매물 게시물
					if (strStandby_Cnt_.equals("0.0")) {
						data_main_standby.setText(""); // 게시물 건수
					} else {
						data_main_standby.setText(strStandby_Cnt_); // 게시물 건수
					}

					////////////////////////////////////////////////////////////////////
					// DODO 뱃지
					////////////////////////////////////////////////////////////////////
					int badgeCount = Integer.parseInt(strBoardCnt);
					if (strBoardCnt == null) {
						badgeCount = 0;
					} else {
						badgeCount = Integer.parseInt(strBoardCnt);
					}

					Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
					badgeIntent.putExtra("badge_count", badgeCount);

					if (mActivity.getPackageName().equals("com.yit.villaman5")) {
						badgeIntent.putExtra("badge_count_package_name", "com.yit.villaman5");
						badgeIntent.putExtra("badge_count_class_name", "com.yit.villaman5.MainActivity");
					} else {
						badgeIntent.putExtra("badge_count_package_name", "com.yit.villaman4");
						badgeIntent.putExtra("badge_count_class_name", "com.yit.villaman4.MainActivity");
					}

					mActivity.sendBroadcast(badgeIntent);

				} catch  (Exception e) {

					Toast.makeText(mActivity, "setPayment:"+"오류 입니다.", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}


			}


		}, sphoneNumber);

	}


	public static void getMarketVersion(final MainActivity mActivity) {

		try {

			Document document = null;

			document = Jsoup.connect("http://yes-vil.kr/w/vil_env_villaman4.php").get();

			Element divVersion = document.getElementById("vil_version");
			Element divVername = document.getElementById("vil_description");

			glVersion = divVersion.text().trim();
			glVername = divVername.text().trim();

		} catch (IOException ex) {
			ex.printStackTrace();
			// Toast.makeText(mActivity, "네트워크 연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
		}
	}


	public static String RightString(String sText, int iTextLenth)
	{
		String sConvertText;

		if (sText.length() < iTextLenth)
		{
			iTextLenth= sText.length();
		}

		sConvertText= sText.substring(sText.length() - iTextLenth, iTextLenth);

		return sConvertText;
	}



	/**
	 * baseStr 이 compareStr 보다 큰 문자열인지 여부. (baseStr > compareStr)
	 * 날짜 문자열 비교 등. "20180301", "20180302" ==> false 반환.
	 * @param baseStr
	 * @param compareStr
	 * @return
	 */
	public static boolean isGreat(String baseStr, String compareStr) {
		baseStr = nvl(baseStr);
		compareStr = nvl(compareStr);

		int compare = baseStr.compareTo(compareStr);

		// 같은지 판단
		if ( baseStr.equals(compareStr)) {
			return true;
		} else {
			// 값이 큰지 판단
			if (compare > 0) {
				return true;
			} else {
				return false;
			}
		}

	}

	// 전체,구옥,신축, 버튼 배경색 바꾸기
	public static void setBtnTxtBgColor_VilAllOldNew(final Button btnName, boolean useYN) {

		if (useYN) {
			btnName.setTextColor(Color.parseColor("#FFFFFF"));
			// btnName.setBackgroundResource(R.drawable.input_box_oldnew_y);
			btnName.setBackgroundResource(R.color.vilmainRent);
		} else {
			btnName.setTextColor(Color.parseColor("#000000"));
			// btnName.setBackgroundResource(R.drawable.input_box_off);
			btnName.setBackgroundResource(R.drawable.action_btn_bg_general);

		}
	}

	// 초기화 검색어 변경
	public static void setTxtExpBgcolor(boolean useYN, TextView txtAll, String strAll, TextView txtExp, String strExp, String strDesc) {

		Log.d(__TAG__,"setTxtExpBgcolor:"+strDesc);
		if (useYN) {
			if (!strExp.equals("")) {
				txtAll.setVisibility(View.VISIBLE);
				txtAll.setTextColor(Color.parseColor("#FF0000")); //fdfd67
				txtAll.setText(strAll);
				txtExp.setVisibility(View.VISIBLE);
				txtExp.setTextColor(Color.parseColor("#FF0000")); //fdfd67
				txtExp.setText(strExp);
			} else {
				txtAll.setVisibility(View.INVISIBLE);
				txtExp.setVisibility(View.INVISIBLE);
			}

		} else {
			txtAll.setVisibility(View.INVISIBLE);
			txtExp.setVisibility(View.INVISIBLE);
		}
	}


	public static String nvl(String str) {
		return str==null ? "" : str;
	}


	public static String getWhatKindOfNetwork(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				return WIFI_STATE;
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				return MOBILE_STATE;
			}
		}
		return NONE_STATE;
	}


	public static String getKeyHash(final Context context) {
		PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
		if (packageInfo == null)
			return null;

		for (Signature signature : packageInfo.signatures) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
			} catch (NoSuchAlgorithmException e) {

			}
		}
		return null;
	}


	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;
	public static final int TYPE_NOT_CONNECTED = 3;

	public static int getConnectivityStatus(Context context){ //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if(networkInfo != null){
			int type = networkInfo.getType();
			if(type == ConnectivityManager.TYPE_MOBILE){//쓰리지나 LTE로 연결된것(모바일을 뜻한다.)
				return TYPE_MOBILE;
			}else if(type == ConnectivityManager.TYPE_WIFI){//와이파이 연결된것
				return TYPE_WIFI;
			}
		}
		return TYPE_NOT_CONNECTED;  //연결이 되지않은 상태
	}

	//Network 연결확인
	public static boolean isConnectedNetwork(Context context){
		boolean networkState = false;

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connectivityManager != null) {
			networkInfo = connectivityManager.getActiveNetworkInfo();
		}
		if(networkInfo != null && networkInfo.isConnected()) {
			networkState = true;
			//Log.e(TAG, "네트워크 연결 확인 됨");
		}

		return networkState;
	}

	// 호출된 URL을 Json 데이타로 리턴한다.
	public static String requestURL_to_JSON(String sendUrl, String jsonValue) throws IllegalStateException {

		String inputLine = null;
		StringBuffer content = new StringBuffer();
		try{

			sendUrl = sendUrl + Util.getBase64encode(jsonValue).replace("\n","");

			URL url = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");

			BufferedReader inn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((inputLine = inn.readLine()) != null) {
				content.append(inputLine);
			}
			inn.close();

			conn.disconnect();
		}catch(Exception e){

			e.printStackTrace();
		}
		return content.toString();
	}

	public static void printFProPrefer(Context context, String sDesc) {
		Log.d(__TAG__, "◆◆=============================================================");
		Log.d(__TAG__, "◆◆sDesc:" + sDesc + ":");
		Log.d(__TAG__, "◆◆getMinInputBo:" + FProPrefer.with(context).getMinInputBo() + ":");
		Log.d(__TAG__, "◆◆getMaxInputBo:" + FProPrefer.with(context).getMaxInputBo() + ":");
		Log.d(__TAG__, "◆◆getMinInputMonth:" + FProPrefer.with(context).getMinInputMonth() + ":");
		Log.d(__TAG__, "◆◆getMaxInputMonth:" + FProPrefer.with(context).getMaxInputMonth() + ":");
		Log.d(__TAG__, "◆◆getMinArea:" + FProPrefer.with(context).getMinArea() + ":");
		Log.d(__TAG__, "◆◆getMaxArea:" + FProPrefer.with(context).getMaxArea() + ":");
		Log.d(__TAG__, "◆◆getMinFloor:" + FProPrefer.with(context).getMinFloor() + ":");
		Log.d(__TAG__, "◆◆getMaxFloor:" + FProPrefer.with(context).getMaxFloor() + ":");
		Log.d(__TAG__, "◆◆getMinRoom:" + FProPrefer.with(context).getMinRoom() + ":");
		Log.d(__TAG__, "◆◆getMaxRoom:" + FProPrefer.with(context).getMaxRoom() + ":");
		Log.d(__TAG__, "◆◆getMinYear:" + FProPrefer.with(context).getMinYear() + ":");
		Log.d(__TAG__, "◆◆getMaxYear:" + FProPrefer.with(context).getMaxYear() + ":");

		Log.d(__TAG__, "◆◆getMyNew02:" + FProPrefer.with(context).getMyNew02() + ":");
		Log.d(__TAG__, "◆◆getMyBlankPicyn:" + FProPrefer.with(context).getMyBlankPicyn() + ":");
		Log.d(__TAG__, "◆◆getMyConPicyn:" + FProPrefer.with(context).getMyConPicyn() + ":");
		Log.d(__TAG__, "◆◆getMyConEmpty:" + FProPrefer.with(context).getMyConEmpty() + ":");
		Log.d(__TAG__, "◆◆getMyNewPicyn:" + FProPrefer.with(context).getMyNewPicyn() + ":");
		Log.d(__TAG__, "◆◆getMyNewEmpty:" + FProPrefer.with(context).getMyNewEmpty() + ":");
		Log.d(__TAG__, "◆◆getMyConfirm02:" + FProPrefer.with(context).getMyConfirm02() + ":");
		Log.d(__TAG__, "◆◆getChoiceMaSearch:" + FProPrefer.with(context).getChoiceMaSearch() + ":");
		Log.d(__TAG__, "◆◆getSearchAction:" + FProPrefer.with(context).getSearchAction() + ":");
		Log.d(__TAG__, "◆◆=============================================================");
	}

}
