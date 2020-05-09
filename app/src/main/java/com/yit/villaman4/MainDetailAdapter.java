package com.yit.villaman4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

///////복사 2019.10.15 //////////////////////////////////////////
import multi_image_selector.MultiImageSelectorActivity;

public class MainDetailAdapter extends BaseAdapter {

	private MainActivity				activity;
	private LayoutInflater				inflater;
	private ArrayList<RGetRoomEnty>	adapterList;
	private int						inLayout;
	private ViewHolder					holder;
	private int						pressPoi;

	private GpsInfo 					gps;
	private boolean 					isCheck;
	private int 						fromMode; 	//지도 : 0, 목록 : 1

	// private MapView mapView;
	// private WebView mWebView;

	////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final String __TAG__ = "MainDetailAdapter♥♥:";

	private boolean isWebView = false;
	private boolean isMarkerOneClick = false;

	public MainDetailAdapter(MainActivity context, int inLayout, ArrayList<RGetRoomEnty> adapterList, int pressPoi, boolean isMarkerOneClick, boolean isWebView) {
		this.activity = context;
		this.inLayout = inLayout;
		this.adapterList = adapterList;
		this.pressPoi = pressPoi;
		this.isMarkerOneClick = isMarkerOneClick;
		this.isWebView = isWebView;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return adapterList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView == null) {

			convertView = inflater.inflate(inLayout, parent, false);

			holder = new ViewHolder();

			holder.textCon = (TextView) convertView.findViewById(R.id.textCon);
			holder.dataMoney = (TextView) convertView.findViewById(R.id.dataMoney);
			holder.dataYear = (TextView) convertView.findViewById(R.id.dataYear);

			holder.dataDateCnt01 = (TextView) convertView.findViewById(R.id.dataDateCnt01);
			holder.dataDateCnt02 = (TextView) convertView.findViewById(R.id.dataDateCnt02);
			holder.dataDateCnt03 = (TextView) convertView.findViewById(R.id.dataDateCnt03);
			holder.dataDateCnt04 = (TextView) convertView.findViewById(R.id.dataDateCnt04);

			holder.dataTel = (TextView) convertView.findViewById(R.id.dataTel);
			holder.dataTelName = (TextView) convertView.findViewById(R.id.dataTelName);
			holder.dataJeon = (TextView) convertView.findViewById(R.id.dataJeon);
			holder.dataTextLevel = (TextView) convertView.findViewById(R.id.dataTextLevel);
			holder.dataMessage = (TextView) convertView.findViewById(R.id.dataMessage);
			holder.imgRView = (ImageView) convertView.findViewById(R.id.imgRView);
			holder.imgJeonAreaBar = (ImageView) convertView.findViewById(R.id.imgJeonAreaBar);
			holder.imgPortrateBar = (ImageView) convertView.findViewById(R.id.imgPortrateBar);
			holder.dataImgCnt = (TextView) convertView.findViewById(R.id.dataImgCnt);
			holder.dataReplyCnt = (TextView) convertView.findViewById(R.id.dataReplyCnt);
			holder.dataDeleteCnt = (TextView) convertView.findViewById(R.id.dataDeleteCnt);

			holder.btnGoRoad = (Button) convertView.findViewById(R.id.btnGoRoad);
			// holder.btnGoMap = (Button) convertView.findViewById(R.id.btnGoMap);
			holder.btnGoReply = (Button) convertView.findViewById(R.id.btnGoReply);
			holder.btnGoSms = (Button) convertView.findViewById(R.id.btnGoSms);
			// holder.btnGoDel = (Button) convertView.findViewById(R.id.btnGoDel);
			// holder.btnGoPicture = (Button) convertView.findViewById(R.id.btnGoPicture);
//			holder.btnGoFav = (Button) convertView.findViewById(R.id.btnGoFav);
			holder.btnImgAppend = (Button) convertView.findViewById(R.id.btnImgAppend);
			holder.btnImgDelete = (Button) convertView.findViewById(R.id.btnImgDelete);
			holder.btnYoutube = (Button) convertView.findViewById(R.id.btnYoutube);

			holder.webview_list = (WebView)  convertView.findViewById(R.id.webview_list);

			holder.rowDetailLayer = (LinearLayout) convertView.findViewById(R.id.rowDetailLayer);

			convertView.setTag(holder);

			//convertView.setTop(200);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
			//convertView.setTop(200);
		}

		// holder.dataYear.setText("(" + "전: " + adapterList.get(position).getMa_jeon_area() + "평/" + adapterList.get(position).getMa_jeon_area_m2() + "㎡)");
		// holder.dataYear.setText("(" + "전:" + adapterList.get(position).getMa_jeon_area() + "평)");

		//////////////////////////////
		// 매매여부 판단한다. // 년식
		if (adapterList.get(position).getMa_Use_Yn().equals("Y")) {
			if (adapterList.get(position).getMa_yun_ney().equals("융:문의")) {
				holder.dataMoney.setText(adapterList.get(position).getMa_mae_ney()); // 매매금액
			} else {
				holder.dataMoney.setText(adapterList.get(position).getMa_mae_ney() + " /" + adapterList.get(position).getMa_yun_ney()); // 매매금액
			}

			if (adapterList.get(position).getMa_jun_year().equals("")) {
				holder.dataYear.setText(" ");
			} else {
				holder.dataYear.setText(" " + adapterList.get(position).getMa_jun_year() + "년식 ");
			}
			// if (adapterList.get(position).getMa_Use_Yn2().equals("S")) { // 대기매물
			// } else {
			// }
		} else {
			holder.dataYear.setText(" ");
			holder.dataMoney.setText(Html.fromHtml("<FONT color=#000000>매매완료</FONT>"));
		}
		//////////////////////////////

		// holder.dataText3.setText(adapterList.get(position).getModfy_dt().substring(5, 10));
		holder.dataDateCnt01.setText(adapterList.get(position).getModfy_dt().substring(5, 10).replace("-","/"));
		holder.dataDateCnt02.setText(adapterList.get(position).getDtTermCnt());
		holder.dataDateCnt03.setText("(" + adapterList.get(position).getDtTermCnt3() + ")");
		holder.dataDateCnt04.setText(adapterList.get(position).getRgst_dt().substring(5, 10).replace("-","/"));

		/*
		Integer iCnt = 0;
		iCnt = Integer.parseInt(adapterList.get(position).getDtTermCnt3());
		if (iCnt <= 15)
		{
			holder.dataDateCnt03.setTextColor(Color.parseColor("#FF0000"));
			holder.dataDateCnt04.setTextColor(Color.parseColor("#FF0000"));

		}
		*/

		SpannableString managerTel = new SpannableString(adapterList.get(position).getMa_memo7());
		managerTel.setSpan(new UnderlineSpan(), 0, managerTel.length(), 0);

		holder.dataTel.setText(managerTel);
		//  holder.dataTel.setText("☏ 매물 관리자");
		holder.dataTel.setPaintFlags(holder.dataTel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

		// 층수
		int intLevel = Integer.parseInt(adapterList.get(position).getMa_level());
		if (intLevel >= 0 && intLevel < 100) {
			holder.dataTextLevel.setText("㉻"); //
		} else	if (intLevel >= 100 && intLevel < 200) {
			holder.dataTextLevel.setText("①"); //
		} else	if (intLevel >= 200 && intLevel < 300) {
			holder.dataTextLevel.setText("②"); //
		} else	if (intLevel >= 300 && intLevel < 400) {
			holder.dataTextLevel.setText("③"); //
		} else	if (intLevel >= 400 && intLevel < 500) {
			holder.dataTextLevel.setText("④"); //
		} else {
			holder.dataTextLevel.setText("△"); //
		}

		// holder.dataJeon.setText(adapterList.get(position).getMa_status1_nm()); // 공실,거주중
		holder.dataJeon.setText(Html.fromHtml("<FONT color=#FF0000><b>" + "전:"+ adapterList.get(position).getMa_jeon_area() +  "평" + "</b></FONT>" + "\n" + adapterList.get(position).getMa_jeon_area_m2()+"㎡")); //


		// if(pressPoi == position) {
		if(String.valueOf(adapterList.get(position).getID()).equals(FProPrefer.with(activity).getSelectID())) {
			// if (activity.isAllVisible == true) { // 리스트 일 경우만 표시
                // holder.rowDetailLayer.setBackgroundResource(R.drawable.row_choice_list_bg);
                holder.rowDetailLayer.setBackgroundResource(R.drawable.row_select_bg);
			// }
		} else {
			holder.rowDetailLayer.setBackgroundResource(R.drawable.row_list_bg);
		}

// 오래된 버전
//		String data1 = adapterList.get(position).getMa_memo1();
//		String data2 = " " + adapterList.get(position).getMa_memo2();
//		String data4 = " " + adapterList.get(position).getMa_memo3(); // 년식
//		String data5 = " " + adapterList.get(position).getMa_memo4();
//		String data6 = " " + adapterList.get(position).getMa_memo5();
//		String tempStr = adapterList.get(position).getMa_memo6();
//		String data7 = " " + tempStr.replace("\r\n", " ");
//
//		//////////////////////////////
//		// 매매여부 판단한다.
//		if (adapterList.get(position).getMa_Use_Yn().equals("Y")) {
//			holder.textCon.setText(Html.fromHtml("<b><FONT color=#007FFF>" + data2 + "</FONT></b>" + "<FONT color=#FF5E00>" + " │" + "</FONT>" // 주소
//					+ "<b>" + data1 + "</b>"  // 빌라명.호실
//					+ "<br>" + data4 + "<FONT color=" + "#FF5E00" + ">" + " │" + "</FONT>" //방,화
//					+ data6 + "<FONT color=" + "#FF5E00" + ">" + " │" + "</FONT>" // 비번
//					+ data5 + "<FONT color=#FF5E00>" + " │" + "</FONT>" + data7));
//		} else {
//			holder.textCon.setText(Html.fromHtml("<b>" + data2 + "</b>" + "<FONT color=#FF5E00>" + " │" + "</FONT>" // 주소
//					+ "<b>" + data1 + "</b>"));  // 빌라명
//		}
//		//////////////////////////////


		// String data1 = adapterList.get(position).getMa_addr3() + " " + adapterList.get(position).getMa_memo1();
		String data1 = adapterList.get(position).getMa_memo1();
		String data2 = " " + adapterList.get(position).getMa_memo2();
		String ma_addr3 = " " + adapterList.get(position).getMa_addr3();

		String data22 = "";

		if (adapterList.get(position).getMa_Use_Yn2().equals("S")) {
			data22 = " " + adapterList.get(position).getMa_memo22().replace("-0","-?").replace("()",""); // 번지

		} else {
			data22 = " " + adapterList.get(position).getMa_memo22(); // 번지
		}

		String data4 = " " + adapterList.get(position).getMa_memo3(); // 년식
		String data5 = " " + adapterList.get(position).getMa_memo4();
		String data6 = " " + adapterList.get(position).getMa_memo5();

		if (adapterList.get(position).getMa_Use_Yn2().equals("S")) { // 대기 매물 이면
			holder.dataTelName.setText(adapterList.get(position).getMa_mine_memo());
		} else {
			holder.dataTelName.setText("");
		}

		String data7 = " " + adapterList.get(position).getMa_memo6().replace("\r\n", " ");

		//////////////////////////////
		// 매매여부 판단한다.
		if (adapterList.get(position).getMa_Use_Yn().equals("Y")) {
			holder.textCon.setText(Html.fromHtml("<b><FONT color=#007FFF>" + data1 + "</FONT></b>" + " " // 빌라명 호실
					+ "<FONT color=#FF0000>" + ma_addr3 + "</FONT>" + " " + data22 + "" // 동면 번지 (도로명)
					+ "<br>" + data4 + "<FONT color=" + "#FF5E00" + ">" + " │" + "</FONT>" //방,화
					+ data6 + "<FONT color=" + "#FF5E00" + ">" + " │" + "</FONT>" // 비번
					+ data5 + "<FONT color=#FF5E00>" + " │" + "</FONT>" + data7));
		} else {
			holder.textCon.setText(Html.fromHtml("<b>" + data2 + "</b>" + "<FONT color=#FF5E00>" + " │" + "</FONT>" // 주소
					+ "<b>" + data1 + "</b>"));  // 빌라명
		}

		//////////////////////////////
		// 매매여부 판단한다.
		if (adapterList.get(position).getMa_Use_Yn().equals("Y") ) {
			holder.dataMessage.setText(adapterList.get(position).getMa_secu_memo()); // 보안메모
		} else {
			holder.dataMessage.setText("최근 매매완료된 매물입니다."); // 보안메모
		}
		//////////////////////////////

		// holder.dataImgCnt.setText("55"); // 이미지 건수

		// Uri uri = Uri.parse("http://yes-vil.kr/uploads/gallery/9985/46e9b43b29cdc156990bc34b47925ec8_thumb.png");
		// holder.imgRView.setImageURI(uri);

		if(isWebView == true) {

			holder.webview_list.setVisibility(View.VISIBLE);
			// TODO webview list
			holder.webview_list.setWebViewClient(new WebViewClient());

			// 웹뷰에서 자바스크립트실행가능
			holder.webview_list.getSettings().setJavaScriptEnabled(true);

			String strGubun = adapterList.get(position).getGubun();
			if (strGubun.equals("main")) { // 전세/월세

				holder.webview_list.loadUrl("http://yes-vil.kr/w/vila_app_list_x.php"
												+ "?main_id=" + adapterList.get(position).getID()
												+ "&usr_id=" + Util.getPhoneNumber(activity)
												);
			} else {
				holder.webview_list.loadUrl("http://yes-vil.kr/w/vila_app_list_y.php"
						+ "?main_id=" + adapterList.get(position).getID()
						+ "&usr_id=" + Util.getPhoneNumber(activity)
				);
			}

			// WebViewClient 지정
			holder.webview_list.setVerticalScrollBarEnabled(true);
		}


		holder.rowDetailLayer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			// 지도 맵에서 왔으면 리스트를 보여 주지 않고 종료한다.
			if (isMarkerOneClick) {
				return;
			}

				int fromSubMode = 0;
				int fromModeSub = 0;
				// 리스트인지 상세인지
				if (activity.isAllVisible == true) { // 리스트

					Log.d(__TAG__,"리스트 클릭");
					activity.setFromMode(2);
					activity.setLastListViewPosition(position);
					// 서브 모드에서 오면
					fromSubMode = activity.getFromModeSub();

					if (fromSubMode == 9) {
						activity.listTextDetailSubClick(position);
					} else {
						activity.listTextDetailClick(position);
					}

					activity.btnBack.setVisibility(View.VISIBLE);
					activity.btnClose.setVisibility(View.VISIBLE);

					// 선택된 ID를 보관한다.
					FProPrefer.with(activity).setSelectID(String.valueOf(adapterList.get(position).getID()));

				} else  { // 상세
					fromModeSub = activity.getFromModeSub();
					Log.d(__TAG__,"상세 클릭");

					if (fromModeSub == 9) {
						activity.showVillaSubList(); // 다시 선택 목록으로 이동한다.
					} else {
						activity.showVillaList(); // 다시 목록으로 이동한다.
					}

					activity.btnBack.setVisibility(View.GONE);
					activity.btnClose.setVisibility(View.VISIBLE);

				}
			}
		});

		holder.dataTel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				HashMap<String, String> detailMap = new HashMap<String, String>();
				detailMap.put("usr_id", Util.getPhoneNumber(activity));
				detailMap.put("ma_cd", "TEL");
				detailMap.put("ma_id", String.valueOf(adapterList.get(position).getID()));
				String clean1 = adapterList.get(position).getMa_memo7().replaceAll("[^0-9]", "");
				detailMap.put("memo", clean1);
				UServerRequest.with(activity).forStart(new UServerRequest.WServerRequest.Callback() {

					@Override
					public void onResult(JSONArray json) {
						Log.e("222222", json.toString());

					}

				}, detailMap);

				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + clean1));
				activity.startActivity(intent);

			}
		});

		holder.btnGoRoad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/////////////////////////////////////////////////////////////
				// 헨드폰번호가 없거나, 회원이 아닐경우는 roadView 사용불가
				if (Util.getPhoneNumber(activity).equals("01000000000") || adapterList.get(position).getMa_bunji2().equals("***"))
				{
					return;
				}
				/////////////////////////////////////////////////////////////

				double longitude = 0;
				double latitude = 0;

				gps = new GpsInfo(activity);
				// GPS 사용유무 가져오기
				if (gps.isGetLocation()) {
					latitude = gps.getLatitude();
					longitude = gps.getLongitude();
				} else {
					// GPS 를 사용할수 없으므로
					gps.showSettingsAlert();
				}

				StringBuffer result = new StringBuffer("daummaps://route?sp=");
				result.append(latitude);
				result.append(",");
				result.append(longitude);
				result.append("&ep=" + adapterList.get(position).getMa_lat());
				result.append(",");
				result.append("" + adapterList.get(position).getMa_lon());
				result.append("&by=CAR");
				//daummaps://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=FOOT

				Uri uri = Uri.parse(result.toString());
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(uri);

				if(intent.resolveActivity(activity.getPackageManager()) != null) {
					HashMap<String, String> detailMap = new HashMap<String, String>();
					detailMap.put("usr_id", Util.getPhoneNumber(activity));
					detailMap.put("ma_cd", "MAP");
					detailMap.put("ma_id", String.valueOf(adapterList.get(position).getID()));
					String clean1 = adapterList.get(position).getMa_memo7().replaceAll("[^0-9]", "");
					detailMap.put("memo", clean1);
					UServerRequest.with(activity).forStart(new UServerRequest.WServerRequest.Callback() {

						@Override
						public void onResult(JSONArray json) {
							Log.e("222222", json.toString());

						}

					}, detailMap);
					activity.startActivity(intent);
				}
				else {
					Toast.makeText(activity, "Daum Map이 설치 되어 있지 않습니다. \nPlay 스토어에서 다음지도 앱을 다운받으세요.", Toast.LENGTH_SHORT).show();
				}

			}
		});

		/*
		holder.btnGoMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				/////////////////////////////////////////////////////////////
				// 헨드폰번호가 없거나, 회원이 아닐경우는 roadView 사용불가
				if (Util.getPhoneNumber(activity).equals("01000000000") || adapterList.get(position).getMa_bunji2().equals("***"))
				{
					return;
				}
				/////////////////////////////////////////////////////////////

				//daummaps://look?p=37.537229,127.005515	지도앱을 실행하고 지정된 좌표 중심으로 지도를 보여주고 마커를 표시합니다.
				//StringBuffer result = new StringBuffer("daummaps://look?p=");
				StringBuffer result = new StringBuffer("daummaps://roadView?p=");
				result.append("" + adapterList.get(position).getMa_lat());
				result.append(",");
				result.append("" + adapterList.get(position).getMa_lon());

				Uri uri = Uri.parse(result.toString());
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(uri);

				if(intent.resolveActivity(activity.getPackageManager()) != null) {
					HashMap<String, String> detailMap = new HashMap<String, String>();
					detailMap.put("usr_id", Util.getPhoneNumber(activity));
					detailMap.put("ma_cd", "MAP");
					detailMap.put("ma_id", String.valueOf(adapterList.get(position).getID()));
					String clean1 = adapterList.get(position).getMa_memo7().replaceAll("[^0-9]", "");
					detailMap.put("memo", clean1);
					UServerRequest.with(activity).forStart(new UServerRequest.WServerRequest.Callback() {

						@Override
						public void onResult(JSONArray json) {
							Log.e("222222", json.toString());

						}

					}, detailMap);
					activity.startActivity(intent);
				}
				else {
					Toast.makeText(activity, "Daum Map이 설치 되어 있지 않습니다. \nPlay 스토어에서 다음지도 앱을 다운받으세요.", Toast.LENGTH_SHORT).show();
				}

			}
		});
		*/

		// 매물 답변 내용
		holder.btnGoReply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(activity, "해당 매물에 대한 답변을 작성합니다.", Toast.LENGTH_SHORT).show();

				String strURL = null;
				String strURL_SUB = null;

				try {
					strURL = Util.getURL_IT() +"/ahh/viewMainReply.do?YESIT="; // TODO
					strURL_SUB = URLEncoder.encode("usr_id","UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(activity),"UTF-8") + "&";
					strURL_SUB += URLEncoder.encode("main_id","UTF-8")+ "=" + URLEncoder.encode(Integer.toString(adapterList.get(position).getID()),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + Util.getBase64encode(strURL_SUB).replace("\n","")));
				// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + strURL_SUB));
				activity.startActivity(intent);

			}
		});

		// SMS 발송
		holder.btnGoSms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String str = "";

				str = adapterList.get(position).getGubun();
				if (str.equals("main")) {
					str = "매매";
				} else {
					str = "분양";
				}

				try {
					String smsTel =  String.valueOf(adapterList.get(position).getMa_memo7().replaceAll("[^0-9]", ""));
					String strAddr = "▣ 빌라만에서 문의 드립니다." + " "
							+ System.getProperty("line.separator")
							+ String.valueOf(adapterList.get(position).getMa_addr3())	+ " " + String.valueOf(adapterList.get(position).getMa_memo1()) + " "
							+ System.getProperty("line.separator")
							+ str + "중 인가요?";

					Uri uri = Uri.parse("smsto:"+ smsTel);
					Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
					intent.putExtra("sms_body", strAddr);
					// intent.setType("vnd.android-dir/mms-sms");
					activity.startActivity(intent);

				} catch (Exception e) {

				}

			}
		});

		/*
		holder.btnGoDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alt_bld = new AlertDialog.Builder(activity);
				alt_bld.setMessage("해당 매물에 대한 매매완료를 요청(승인) 하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HashMap<String, String> detailMap = new HashMap<String, String>();
						detailMap.put("usr_id", Util.getPhoneNumber(activity));
						detailMap.put("ma_cd", "DEL");
						detailMap.put("ma_id", String.valueOf(adapterList.get(position).getID()));
						String clean1 = adapterList.get(position).getMa_memo7().replaceAll("[^0-9]", "");
						detailMap.put("memo", clean1);
						UServerRequest.with(activity).forStart(new UServerRequest.WServerRequest.Callback() {

							@Override
							public void onResult(JSONArray json) {
								Log.e("11111111111", json.toString());

							}

						}, detailMap);

						dialog.cancel();
						Toast.makeText(activity, "담당자에게 매매완료를 요청(승인) 하였습니다. \n이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show();

						// 매물 재조회
						activity.reDataView();

					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});
				AlertDialog alert = alt_bld.create();
				alert.show();

			}
		});
		*/

		// 사진이 있으면 Background이미지를 바꾼다.
		/*
		if(adapterList.get(position).getFcnt().equalsIgnoreCase("Y")) { // 사진이 있으면
			if (adapterList.get(position).getMa_agent_yn().equalsIgnoreCase("Y")) {
				holder.btnGoPicture.setBackgroundResource(R.drawable.ic_gopicture_ay_m);
			} else {
				holder.btnGoPicture.setBackgroundResource(R.drawable.ic_gopicture_y_m);
			}
		}
		else if(adapterList.get(position).getFcnt().equalsIgnoreCase("U")) {
			if (adapterList.get(position).getMa_agent_yn().equalsIgnoreCase("Y")) {
				holder.btnGoPicture.setBackgroundResource(R.drawable.ic_gopicture_au_m);
			} else {
				holder.btnGoPicture.setBackgroundResource(R.drawable.ic_gopicture_u_m);
			}
		}
		else {
			holder.btnGoPicture.setBackgroundResource(R.drawable.ic_gopicture_m);
		}
		*/

		// 사진 버튼을 클릭하면
		// holder.btnGoPicture.setOnClickListener(new OnClickListener() {
		holder.imgRView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				// 사진이 있으면
//				if(Integer.parseInt(adapterList.get(position).getImgCnt()) >= 1) {
//
//					String strURL = null;
//					String strURL_SUB = null;
//
//					try {
//						strURL = Util.getURL_IT() +"/app/viewGallery.do?YESIT="; // TODO
//						strURL_SUB = URLEncoder.encode("usr_id","UTF-8") + "=" + URLEncoder.encode(Util.getPhoneNumber(activity),"UTF-8") + "&";
//						strURL_SUB += URLEncoder.encode("main_id","UTF-8")+ "=" + URLEncoder.encode(Integer.toString(adapterList.get(position).getID()),"UTF-8");
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
//
//					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + Util.getBase64encode(strURL_SUB).replace("\n","")));
//					activity.startActivity(intent);
//
//				} // 사진이 없을 경우 사진을 선택할수 있게 해준다 (tbl_user에 등록된 회원에 한하여).
//				else if(adapterList.get(position).getFcnt().equalsIgnoreCase("U")) {
//					return;
//				}
//				else {
//
//				}
			}
		});

		// 사진추가 버튼을 클릭
		holder.btnImgAppend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
				intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
				intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 20); // 선택(이미지 업로드 갯수)
				intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
				intent.putExtra("main_id", Integer.toString(adapterList.get(position).getID()));
				intent.putExtra("gubun", adapterList.get(position).getGubun());

				activity.gc_lat = adapterList.get(position).getMa_lat(); // 사진추가 위도 경도 저장
				activity.gc_lon = adapterList.get(position).getMa_lon();

				activity.startActivityForResult(intent, 9901); // 200

			}

		});


		// 사진삭제 버튼을 클릭
		holder.btnImgDelete.setOnClickListener(new OnClickListener() {

			private MainActivity			activity_m;
			@Override
			public void onClick(final View v) {

				AlertDialog.Builder alt_bld = new AlertDialog.Builder(activity);
				alt_bld.setMessage("등록된 사진을 삭제 하시겠습니까? \n      '모든' 사진이 삭제 됩니다.").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HashMap<String, String> detailMap = new HashMap<String, String>();
						detailMap.put("usr_id", Util.getPhoneNumber(activity));
						detailMap.put("ma_cd", "DEL");
						detailMap.put("ma_id", String.valueOf(adapterList.get(position).getID()));
						UServerImgDelete.with(activity).forStart(new UServerImgDelete.WServerImgDelete.Callback() {

							@Override
							public void onResult(
									JSONArray json) {
								// Log.e("11111111111", json.toString());
								holder.btnImgDelete.setVisibility(View.GONE); // 삭제버튼을 감춘다.
								holder.btnImgDelete.setVisibility(View.INVISIBLE); // 삭제버튼을 감춘다.
								holder.dataImgCnt.setText("");


								// 데이타를 재 조회한다.
								// activity_m.reData();
							}

						}, detailMap);

						dialog.cancel();
						Toast.makeText(activity, "사진을 삭제 하였습니다. \n재 등록 바랍니다.", Toast.LENGTH_SHORT).show();

						// 매물 재조회
						// activity.reqCode = 901;
						activity.reDataView("btnImgDelete.setOnClickListener");

					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});

				AlertDialog alert = alt_bld.create();
				alert.show();

			}



		});


////////////////////////////////////////////////////////////////////////////////////////////////////

		// 권한에 따라 사진 업로드가 가능하다.
		if(adapterList.get(position).getImgAppendYn().equalsIgnoreCase("Y") && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) { // 사진 업로드
			holder.btnImgAppend.setVisibility(View.VISIBLE);

		} else {
			holder.btnImgAppend.setVisibility(View.GONE);
		}

		// 유투브 영상
		if(!adapterList.get(position).getMa_youtube().equalsIgnoreCase("") && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) { // 유투브 영상
			holder.btnYoutube.setVisibility(View.VISIBLE);

		} else {
			holder.btnYoutube.setVisibility(View.GONE);
		}

		// 사진 삭제
		if(adapterList.get(position).getImgDeleteYn().equalsIgnoreCase("Y") && Integer.parseInt(adapterList.get(position).getImgCnt()) >= 2 && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) { // 사진 삭제
			holder.btnImgDelete.setVisibility(View.VISIBLE);

		} else {
			holder.btnImgDelete.setVisibility(View.GONE);
		}

		holder.dataImgCnt.bringToFront();
		holder.dataReplyCnt.bringToFront();
		holder.dataDeleteCnt.bringToFront();

//		// 즐겨찾기 로딩
//		if(adapterList.get(position).getFavYn().equalsIgnoreCase("Y") && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) { // 즐겨찾기 체크
//			holder.btnGoFav.setBackgroundResource(R.drawable.ico_fav_view);
//			holder.btnGoFav.setTag("ico_fav_view");
//
//		} else {
//			holder.btnGoFav.setBackgroundResource(R.drawable.ico_fav_add);
//			holder.btnGoFav.setTag("ico_fav_add");
//		}

		// 사진 건수
		if(adapterList.get(position).getFcnt().equalsIgnoreCase("Y") && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) { // 사진이 있으면
			holder.imgRView.setBackgroundResource(R.drawable.ic_gopicture_y_m);
			holder.imgRView.setTag("ic_gopicture_y_m");

		} else {
			if(adapterList.get(position).getMa_status1().equalsIgnoreCase("E") && (
					adapterList.get(position).getMa_status2().equalsIgnoreCase("T") || adapterList.get(position).getMa_status2().equalsIgnoreCase("O") ||
					adapterList.get(position).getMa_status2().equalsIgnoreCase("K") || adapterList.get(position).getMa_status2().equalsIgnoreCase("S"))) { // 공실 올수리,특올수리, 반수리, 신축

				holder.imgRView.setBackgroundResource(R.drawable.ic_gopicture_s_m);
				holder.imgRView.setTag("ic_gopicture_s_m");

			} else {
				holder.imgRView.setBackgroundResource(R.drawable.ic_gopicture_u_m);
				holder.imgRView.setTag("ic_gopicture_u_m");
			}
		}
		// 이미지 건수
		if(Integer.parseInt(adapterList.get(position).getImgCnt()) >= 1 && adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("Y")) {
			holder.dataImgCnt.setText(adapterList.get(position).getImgCnt());
		} else {
			holder.dataImgCnt.setText("");
		}

		// 답변 건수
		if(Integer.parseInt(adapterList.get(position).getReplyCnt()) >= 1) {
			holder.dataReplyCnt.setText(adapterList.get(position).getReplyCnt());
			holder.btnGoReply.setBackgroundResource(R.drawable.ic_goreply_y_m);
		} else {
			holder.dataReplyCnt.setText("");
			holder.btnGoReply.setBackgroundResource(R.drawable.ic_goreply_n_m);
		}

		// 삭제 건수
		if(Integer.parseInt(adapterList.get(position).getDeleteCnt()) >= 1) {
			holder.dataDeleteCnt.setText(adapterList.get(position).getDeleteCnt());
		} else {
			holder.dataDeleteCnt.setText("");
		}

		// DODO 층수/평수 게이지
		if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 1 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 9) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar1);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 10 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 11) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar2);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 12 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 13) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar3);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 14 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 15) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar4);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 146 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 17) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar5);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 15 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 19) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar6);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 16 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 21) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar7);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 18 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 24) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar8);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 20 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 24) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar9);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 23 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 24) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar10);
		} else if(Integer.parseInt(adapterList.get(position).getMa_jeon_area()) >= 25 && Integer.parseInt(adapterList.get(position).getMa_jeon_area()) <= 40) {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar11);
		} else {
			holder.imgJeonAreaBar.setImageResource(R.drawable.ico_jeonarea_bar0);
		}

		// 층수 게이지
		if (adapterList.get(position).getMa_level().length() == 3) {

			if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 0) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar0);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 1) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar1);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 2) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar2);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 3) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar3);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 4) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar4);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) == 5) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar5);
			} else if (Integer.parseInt(adapterList.get(position).getMa_level().toString().substring(0, 1)) >= 6) {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar6);
			} else {
				holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar0);
			}
		} else {
			holder.imgPortrateBar.setImageResource(R.drawable.ico_level_bar6);
		}


////////////////////////////////////////////////////////////////////////////////////////////////////

		// 번지가 보이지 않으면 일반인으로 로드뷰 버튼을 보이지 않게 한다.
		if ("***".equals(adapterList.get(position).getMa_bunji2()) || adapterList.get(position).getMa_Use_Yn().equalsIgnoreCase("N"))
		{
			holder.btnGoRoad.setVisibility(View.GONE);
			// holder.btnGoMap.setVisibility(View.GONE);
		} else {
			holder.btnGoRoad.setVisibility(View.VISIBLE);
			// holder.btnGoMap.setVisibility(View.VISIBLE);
		}

		if (adapterList.get(position).getMa_Use_Yn2().equals("S")) { // 대기 매물 이면
			holder.btnImgAppend.setVisibility(View.GONE);
			// holder.btnGoFav.setVisibility(View.GONE);
			// holder.btnGoRoad.setVisibility(View.GONE);
			holder.btnGoSms.setVisibility(View.GONE);
			holder.btnGoReply.setVisibility(View.GONE);
			holder.dataReplyCnt.setVisibility(View.GONE);
		}

		// convertView.setLayoutParams(new LayoutParams(500, 500));

		// convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


		return convertView;

	}

	private String openHttpURLConnection(String requestURL){
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

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bun = msg.getData();
			String result = bun.getString("KEY");
			//tvNaverHtml.setText(result);
		}
	};



	public static class ViewHolder {
		TextView		textCon;
		LinearLayout	rowDetailLayer;
		TextView		dataMoney;
		TextView		dataYear;
		TextView		dataText3;
		TextView		dataTel;
		TextView		dataTelName;
		TextView		dataJeon;
		TextView		dataTextLevel;
		TextView		dataMessage;

		TextView		dataDateCnt01;
		TextView		dataDateCnt02;
		TextView		dataDateCnt03;
		TextView		dataDateCnt04;

		TextView		dataImgCnt;
		TextView		dataReplyCnt;
		TextView		dataDeleteCnt;
		Button			btnGoRoad;
		// Button			btnGoMap;
		Button			btnGoReply;
		Button			btnGoMsg;
		Button			btnGoSms;
		// Button			btnGoDel;
		// Button			btnGoPicture;
		// Button			btnGoFav;
		ImageView		imgRView;
		ImageView		imgJeonAreaBar;
		ImageView		imgPortrateBar;
		Button		    btnImgAppend;
		Button		    btnImgDelete;
		Button			btnYoutube;

		WebView         webview_list;

	}

}