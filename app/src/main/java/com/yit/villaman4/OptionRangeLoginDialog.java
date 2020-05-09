package com.yit.villaman4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import multi_image_selector.MultiImageNameCardActivity;

import static com.yit.villaman4.Util.strPayment;

public class OptionRangeLoginDialog extends BaseDialog {

	private MainActivity				activity;

	private Context context;
	private Util util;
	private Button btnLoginConfirm;
	private Button btnLoginCancel;
	private Button btnLoginManager;
	private Button btnmamaeRequest;

	private TextView tvphoneNumber;
	private TextView tvpassWd;

	private TextView tvLoginTitle;
	private TextView tvphoneRequest;
	private TextView tvpassRequest;
	private TextView tvmamaePicture;
	private TextView tvpayTitle;
	private TextView tvpayAmount;
	private TextView tvmamaeCnt;
	private TextView tvpayBank;

	private TextView tvMessage;

	private String strType_ = "";
	private String strAuth_Id_ = "";
	private String strPasswd_ = "";
	private String strMaUseYn_ = "";
	private String strPayAmount_ = "";
	private String strLoginPayAmount_ = "";
	private String strFileName_ = "";
	private String strStandbyCnt_ = "";

	private String strNameCardTitle = "";

	private String phoneNumber;
	private String hdPasswd;

	private HashMap<String, String> addressMap;

	private Callback mCallback;

	private static final String __TAG__ = "OptionRangeLoginD♥♥:";

	public OptionRangeLoginDialog(MainActivity context, int theme) {
		super(context, theme);
		this.context = context;
		this.activity = context;
	}

	public void onResume() { // 재조회를 한다.
		startView();
		startAction();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startView();
		startAction();
	}

	private void startView() {
		btnLoginConfirm = (Button) findViewById(R.id.btnLoginConfirm); // 확인
		btnLoginCancel = (Button) findViewById(R.id.btnLoginCancel);   // 취소
		btnLoginManager = (Button) findViewById(R.id.btnLoginManager);   // 관리자
		btnmamaeRequest = (Button) findViewById(R.id.btnmamaeRequest);   // 매물등록

		tvphoneNumber = (TextView) findViewById(R.id.phoneNumber); // 전화번호
		tvpassWd = (TextView) findViewById(R.id.passWd);  // 인증번호

		tvLoginTitle = (TextView) findViewById(R.id.LoginTitle); // 로그인 타이틀
		tvphoneRequest = (TextView) findViewById(R.id.phoneRequest); // 회원가입
		tvpassRequest = (TextView) findViewById(R.id.passRequest); // 인증번호 요청
		tvmamaePicture = (TextView) findViewById(R.id.mamaePicture); // 명함등록
		tvpayTitle = (TextView) findViewById(R.id.txtPay_Title); //
		tvpayAmount = (TextView) findViewById(R.id.txtPay_Amount); // 함계금액
		tvmamaeCnt = (TextView) findViewById(R.id.txtMamaeCnt); // 매물건수
		tvpayBank = (TextView) findViewById(R.id.txtPay_Bank); // 충전하기

		tvMessage = (TextView) findViewById(R.id.Message); // 메시지

		PackageInfo pi = null;
		String p_versionName = "";

		try {
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		}
		catch (PackageManager.NameNotFoundException e) {
		}

		p_versionName =  pi.versionName;
		tvLoginTitle.setText("로그인 ("+ "v" + p_versionName + ")");

		// 전화번호를 가져온다.
		phoneNumber = Util.getPhoneNumber(context);

		// 전화번호를 보여준다.
		// 전화번호를 읽기전용/클릭 불가능
		tvphoneNumber.setText( phoneNumber);
		tvphoneNumber.setFocusable(false);
		tvphoneNumber.setClickable(false);

		tvpassWd.setText(FProPrefer.with(context).getPassWd()); // 저장된 비밀번호를 가져온다.
		tvpassWd.setFocusable(false);
		tvpassWd.setClickable(false);

		if(phoneNumber == null || "".equals(phoneNumber) || !"010".equals(phoneNumber.substring(0,3))) {
			phoneNumber = "01000000000";
		}

		if (phoneNumber.equals("01000000000")) {

			final String finalP_versionName = p_versionName;

			tvphoneNumber.setText("");
			tvpassWd.setText("");
			tvpassWd.setHint("");

			tvphoneRequest.setVisibility(View.GONE);// 회원가입
			tvpassRequest .setVisibility(View.GONE); // 인증번호 요청
			tvmamaePicture.setVisibility(View.GONE); // 명함 등록
			tvpayTitle.setVisibility(View.GONE); //
			tvpayAmount.setVisibility(View.GONE); // 함계금액
			tvmamaeCnt.setVisibility(View.GONE); // 매매건수
			tvpayBank.setVisibility(View.GONE); // 충전하기
			btnmamaeRequest.setVisibility(View.GONE); // 매물 등록

			tvMessage.setText("◈【최초 실행】...\n　종료 후 다시 실행해 주세요.");

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
			alertDialog.setTitle("휴대폰 번호를 인식하지 못하였습니다.");
			alertDialog.setMessage("원인 :\n"
					+ "1. v" + finalP_versionName + "처음 실행이시나요? \n"
					+ "2. 폰 번호가 없는 기기에 설치 되었나요? \n"
					+ "\n\n다시 실행 바랍니다.");

			alertDialog.setPositiveButton("종료", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					ActivityCompat.finishAffinity(activity);
					System.exit(0);
				}
			});

			alertDialog.show();

		} else {

			///////////////////////////////////////////////////////////////
			// 전화번호에 따른 회원 정보를 가져 온다.
			///////////////////////////////////////////////////////////////

			UServerCheckPayment.with(context).forStart(new UServerCheckPayment.WServerCheck_Payment.Callback() {

				public void onResult(JSONArray json) {

					try {

						if (json != null && json.length() > 0) {
							strType_ = json.getJSONObject(0).get("type").toString();
							strAuth_Id_ = json.getJSONObject(0).get("auth_id").toString();
							strPasswd_ = json.getJSONObject(0).get("passwd").toString();
							strMaUseYn_ = json.getJSONObject(0).get("ma_use_yn").toString();
							strPayAmount_ = json.getJSONObject(0).get("pay_amount").toString();
							strLoginPayAmount_ = json.getJSONObject(0).get("login_pay_amount").toString();
							strFileName_ = json.getJSONObject(0).get("filename").toString();
							strStandbyCnt_ = json.getJSONObject(0).get("standby_cnt").toString();
							hdPasswd = strPasswd_;

							tvmamaeCnt.setVisibility(View.VISIBLE); // 매매건수
							tvmamaeCnt.setText("("+ strStandbyCnt_ + ")");

							if (strType_.equals("admin") || strType_.equals("general")) { // 회원 이면
								tvphoneRequest.setVisibility(View.VISIBLE);
								SpannableString content1 = new SpannableString("①등록 번호");
								content1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, content1.length(), 0);
								tvphoneRequest.setText(content1);

								//////////////////////////////////////////////////////
								tvpassRequest.setVisibility(View.VISIBLE);
								SpannableString content2 = new SpannableString("②인증 요청");
								content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
								tvpassRequest.setText(content2);

								SpannableString content21 = new SpannableString("");
								if (strFileName_ == null || strFileName_.equals("null") || strFileName_.equals("") ) {
									strNameCardTitle = "③명함 등록";
									content21 = new SpannableString(strNameCardTitle);
									content21.setSpan(new UnderlineSpan(), 0, content21.length(), 0);
								} else {
									strNameCardTitle = "③명함 수정";
									content21 = new SpannableString(strNameCardTitle);
									content21.setSpan(new ForegroundColorSpan(Color.GRAY), 0, content21.length(), 0);
								}
								tvmamaePicture.setText(content21);

								if (strType_.equals("admin") && (strAuth_Id_.equals("2") || strAuth_Id_.equals("3"))) { // 부동산
									tvMessage.setText("회원님은 빌라만 전문가 입니다.");

									///////////////////////////////////////////////////////////////////////////
									// btnLoginConfirm.setOnClickListener(android.view.View.OnClickListener());
									// 이미 로그인 성공한 적이 있으면
									// 자동으로 로그인 한다.
									HashMap<String, String> detailMap = new HashMap<String, String>();

									String passwd = tvpassWd.getText().toString();

									// 인증번호 일치 하는지 확인한다.
									if (hdPasswd.equals(passwd)) {

										if (mCallback != null ) {  // 2019.10.1 수정
											if (Long.parseLong(strPayAmount_.replace(",","")) < Long.parseLong(strLoginPayAmount_.replace(",",""))) {  // 2019.10.1 수정

												// Toast.makeText(context, "코인이 부족합니다. \n '충전하기'를 통하여 충전할 수 있습니다.", Toast.LENGTH_SHORT).show();

												if (strMaUseYn_.equals("Y")) { // 무료 사용 기간이면 사용 가능하도록 한다.
													detailMap.put("action", "ok");
													FProPrefer.with(context).setPassWd(passwd); // 성공이면 비밀번호를 저장한다.

													mCallback.onResponse(detailMap);
													dismiss();
												} else {
													// from tbl_code where cd_code = 'CG024CD001'
												}

											} else {
												detailMap.put("action", "ok");
												FProPrefer.with(context).setPassWd(passwd); // 성공이면 비밀번호를 저장한다.

												mCallback.onResponse(detailMap);
												dismiss();
											}
										}

									} else {
										Toast.makeText(context, "인증 번호 요청 바랍니다.", Toast.LENGTH_SHORT).show();
									}

									// tvmamaePicture.setVisibility(View.GONE); // 명함등록
									///////////////////////////////////////////////////////////////////////////

								} else if (strType_.equals("general")) { //
									tvMessage.setText("부동산/컨설팅이십니까? \n등업 요청 문자 바랍니다.");

									tvmamaePicture.setVisibility(View.VISIBLE); // 명함등록

								} else { // 기타
									tvMessage.setText("회원님은 회원(" + strAuth_Id_ + ") 분류 중입니다.");

									tvmamaePicture.setVisibility(View.VISIBLE); // 명함등록
								}


								tvpayTitle.setVisibility(View.VISIBLE); //
								tvpayAmount.setText("300 / ￦" + strPayAmount_);
								tvpayAmount.setVisibility(View.VISIBLE); // 충전금액

								SpannableString content11 = new SpannableString("④충전 하기");
								content11.setSpan(new UnderlineSpan(), 0, content11.length(), 0);
								tvpayBank.setText(content11);
								tvpayBank.setVisibility(View.VISIBLE);   // 충전하기


							} else { // 비회원이면
								//////////////////////////////////////////////////////
								tvphoneRequest.setVisibility(View.VISIBLE);
								SpannableString content1 = new SpannableString("①회원 가입");
								content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
								tvphoneRequest.setText(content1);

								tvmamaePicture.setVisibility(View.GONE); // 명함 등록
								tvpassRequest.setVisibility(View.GONE); // 요청

								tvMessage.setText("회원 가입 후 사용 가능합니다.");
								tvpayTitle.setVisibility(View.GONE); //
								tvpayAmount.setVisibility(View.GONE); // 충전금액
								tvpayBank.setVisibility(View.GONE);   // 충전하기

							}


						} else {
							Toast.makeText(context, "1.서버 접속이 원할하지 않습니다. \n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();

							HashMap<String, String> detailMap = new HashMap<String, String>();
							detailMap.put("action", "error");
							mCallback.onResponse(detailMap);
							dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} finally {
					}
				}
			}, phoneNumber);
			///////////////////////////////////////////////////////////////

			//////////////////////////////////////////////////////

			// 관리자 링크
//			if("01037580862".equals(phoneNumber)) {
//				btnLoginManager.setVisibility(View.VISIBLE);
//			}

		}
	}

	private void startAction() {

		// DODO 로그인 확인 버튼
		btnLoginConfirm.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				HashMap<String, String> detailMap = new HashMap<String, String>();

				String passwd = tvpassWd.getText().toString();


				if (strType_.equals("") && strAuth_Id_.equals("0") ) { // 비회원
					Toast.makeText(context, "회원 가입 후 사용 가능합니다.", Toast.LENGTH_SHORT).show();
				} else {
					// 인증번호 일치 하는지 확인한다.
					if (hdPasswd != null && hdPasswd.equals(passwd)) {
// 2019.08.21                        if (strType_.equals("admin") && (strAuth_Id_.equals("2") || strAuth_Id_.equals("3"))) { // 부동산

						if (mCallback != null) {
							// 회원이면서 설정한 값이 부족하면 로그인 할수 없다. 2019.10.16
							if (   (strType_.equals("admin") && strAuth_Id_.equals("2") || strAuth_Id_.equals("3")) &&
									(Long.parseLong(strPayAmount_.replace(",","")) < Long.parseLong(strLoginPayAmount_.replace(",","")))) {  // 2019.10.1 수정

								Toast.makeText(context, "코인이 부족합니다. \n '충전하기'를 통하여 충전할 수 있습니다.", Toast.LENGTH_SHORT).show();

								if (strMaUseYn_.equals("Y")) { // 무료 사용 기간이면 사용 가능하도록 한다.
									detailMap.put("action", "ok");
									FProPrefer.with(context).setPassWd(passwd); // 성공이면 비밀번호를 저장한다.

									mCallback.onResponse(detailMap);
									dismiss();
								} else {
									// from tbl_code where cd_code = 'CG024CD001'
								}


								// return;
							} else {
								detailMap.put("action", "ok");
								FProPrefer.with(context).setPassWd(passwd); // 성공이면 비밀번호를 저장한다.

								mCallback.onResponse(detailMap);
								dismiss();
							}
						}
// 2019.08.21                        } else {
// 2019.08.21                           Toast.makeText(context, "        승인 대기중입니다.\n ☎ 010-8676-0862로 문의 바랍니다.", Toast.LENGTH_SHORT).show();
//  2019.08.21                       }

					} else {
						Toast.makeText(context, "인증 번호 확인 바랍니다.", Toast.LENGTH_SHORT).show();
					}
				}
			}

		});

		// DODO 로그인에서 취소버튼
		//  취소 버튼
		btnLoginCancel.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				HashMap<String, String> detailMap = new HashMap<String, String>();

				if (mCallback != null) {
					detailMap.put("action", "no");
					mCallback.onResponse(detailMap);
				}

				// 어플을 종료한다.
				dismiss();

			}
		});

		//  매물 등록
		btnmamaeRequest.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// 매물등록
				String itemURL = Util.getURL_IT() + "/ahh/viewMainRegist.do?YESIT=	"; // TODO 매물등록

				String data2 = null;

				Toast.makeText(context, "매물등록 으로 이동합니다..", Toast.LENGTH_SHORT).show();

				try {
					data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
					data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + phoneNumber, "UTF-8") + "&";
					data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + phoneNumber, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// 외부로 웹뷰 띄우기
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
				context.startActivity(intent);


			}
		});


		// DODO 로그인에서 관리자버튼
		//  관리자 버튼
		btnLoginManager.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// 매물등록
				String itemURL = Util.getURL_VIL() + "/w/env_index.php?"; // TODO 관리자

				String data2 = null;
				try {
					data2 = "packagename" + "=" + Util.getBase64encode("" + context.getPackageName()) + "&";
					data2 = data2 + "userid"+ "=" + Util.getBase64encode("" + phoneNumber);
					// data2 = URLEncoder.encode("packagename", "UTF-8") + "=" + URLEncoder.encode("" + context.getPackageName(), "UTF-8") + "&";
					// data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + phoneNumber, "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 외부로 웹뷰 띄우기
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
				context.startActivity(intent);

				// 어플을 종료한다.
				// dismiss();

			}
		});

		// DODO 회원 가입 요청
		tvphoneRequest.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (strType_.equals("admin") || strType_.equals("general")) { // 회원 이면
					Toast.makeText(context, "이미 등록된 번호 입니다.", Toast.LENGTH_SHORT).show();
					return;
				}

				String data2 = null;
				String itemURL = "";
				try {
					//회원가입
					itemURL = Util.getURL_VIL() + "/mobile/signup_app?1=1&usr_id=" + URLEncoder.encode("" + phoneNumber, "UTF-8"); // TODO 회원가입 (메뉴)
					// isViewMore = false;

					// data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
					// data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + phoneNumber, "UTF-8") + "&";
					// data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + phoneNumber, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// 외부로 웹뷰 띄우기
				// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL));
				context.startActivity(intent);

				android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료

			}

		});

		// DODO 인증 요청
		tvpassRequest.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(phoneNumber == null || "".equals(phoneNumber))
					phoneNumber = "00000000002";

				UServerCheckPassWd.with(activity).forStart(new UServerCheckPassWd.WServerCheck_PassWd.Callback() {

					@Override
					public void onResult(JSONArray json) {

						try {

							if (json != null) {

								SpannableString content2 = new SpannableString("②인증 완료");
								content2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, content2.length(), 0);
								tvpassRequest.setText(content2);


								Toast.makeText(context, "정상 로그인 가능합니다.", Toast.LENGTH_SHORT).show();

								String passwd = json.getJSONObject(0).get("passwd").toString();
								tvpassWd.setText(passwd);

							} else {
								Toast.makeText(context, "2.서버 접속이 원할하지 않습니다. \n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							// finish();
						}
					}
				}, phoneNumber);

			}

		});

		// DODO 명함 등록
		tvmamaePicture.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Toast.makeText(context, "명함 등록 개발중 입니다. \n 문자(010-8676-0862) 부탁합니다. ", Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(activity, MultiImageNameCardActivity.class);
				intent.putExtra(MultiImageNameCardActivity.EXTRA_SHOW_CAMERA, false);
				intent.putExtra(MultiImageNameCardActivity.EXTRA_SELECT_COUNT, 1); // 선택(이미지 업로드 갯수)
				intent.putExtra(MultiImageNameCardActivity.EXTRA_SELECT_MODE, MultiImageNameCardActivity.MODE_MULTI);
				/*
				intent.putExtra("main_id", Integer.toString(adapterList.get(position).getID()));
				intent.putExtra("gubun", adapterList.get(position).getGubun());

				activity.gc_lat = adapterList.get(position).getMa_lat(); // 사진추가 위도 경도 저장
				activity.gc_lon = adapterList.get(position).getMa_lon();
				*/

				activity.startActivityForResult(intent, 9902); // 200


			}

		});

		// DODO 충전하기
		tvpayBank.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String strURL = null;
				String strURL_SUB = null;

				Toast.makeText(context, "충전하기로 이동합니다..", Toast.LENGTH_SHORT).show();

				try {
					strURL = Util.getURL_IT() + "/ahh/viewPayBank.do?YESIT="; // TODO 충전하기
					strURL_SUB = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&";
					strURL_SUB = strURL_SUB + URLEncoder.encode("usr_name", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				System.out.println(strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));

				Log.d("/ahh/viewPayBank.do",strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));
				// 외부로 웹뷰 띄우기
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + Util.getBase64encode(strURL_SUB).replace("\n","")));
				context.startActivity(intent);

			}

		});

	}
	public OptionRangeLoginDialog forStart(Callback OptionLoginDialog_Callback) {
		mCallback = OptionLoginDialog_Callback;
		return this;
	}

	public static interface Callback {
		void onResponse(HashMap<String, String> detailMap);

	}


	public void restart(){
		Intent i = activity.getPackageManager().
				getLaunchIntentForPackage(activity.getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(i);
	}


}
