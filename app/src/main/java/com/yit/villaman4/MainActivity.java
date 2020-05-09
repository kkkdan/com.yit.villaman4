package com.yit.villaman4;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.CurrentLocationEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

// import firebase.FireMain;
import multi_image_selector.MultiImageNameCardActivity;
import permission.Permission;
import permission.PermissionListener;

import static com.yit.villaman4.Util.glVername;
import static com.yit.villaman4.Util.glVersion;
import static com.yit.villaman4.Util.strBoardCnt;
import static com.yit.villaman4.Util.strPayment;


///////복사 2019.10.15 //////////////////////////////////////////
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
////////////////////////////////////////////////////////////////////////////////////////////////////
        , MapView.OpenAPIKeyAuthenticationResultListener
        , MapView.MapViewEventListener
        , CurrentLocationEventListener
        , MapView.POIItemEventListener

////////////////////////////////////////////////////////////////////////////////////////////////////
{
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int CURRENT_POSITION = 1;
    public static final int SEARCH_POSITION = 2;
    public static final int ADDR_POSITION = 3;
    public static final int CHOICE_POSITION = 4;
    public static final int MOVE_POSITION = 5;
    public static final int AREA_POSITION = 6;

    private static final int CURRENT_ON = 0;
    private static final int CURRENT_OFF = 1;

    public MapView mapView;
    public WebView mWebView;
    public WebView mWebView_Desc;
    public LinearLayout mLloBottomBar;
    public String  psPhoneNumber;

    private Context context;
    private MainDetailAdapter.ViewHolder holder;
    private MapPOIItem marker;
    private int poiType;
    // private int poiCurr;

    private LinearLayout llCoverContent;
    private LinearLayout ll_RoomList;

    private ArrayList<RGetRoomEnty> rGetRoomList;
    private ArrayList<RGetRoomEnty> rGetRoomSubList;
    private MapPoint.GeoCoordinate prevMapPointGeo;
    private MapPoint.GeoCoordinate mapPointGeo;
    private double latituce;
    private double longitude;

    private double lat_ituce;
    private double long_itude;

    private Button btnHomeLog;
    private Button btnCurPosition;
    private Button btnVilAll;
    private Button btnVilOld;
    private Button btnVilNew;
    private Button btn_favorite;
    private Button btn_actual_price;
    private Button btn_main_standby;
    private Button btn_main_board;
    private Button btnWebView_Del;

    private Button btnSi;
    private Button btnGu;
    private Button btnDong;
    private Button btnVilArea;

    public Button btnClose;
    public Button btnBack;

    // private Button btnLetterSend;
    private Button btnRefresh;
    private Button btnPay_Refresh;
    private Button btnNavClose;
    private ImageView btnRegister;
    // private ImageView btnSearch;
    private ImageView viewMore;
    private Button viewMoreSub;

    private RelativeLayout relExp;
    private TextView txtExp;
    private TextView tvAddress;
    private TextView txtSubTotal;
    private TextView txtTotal;
    private TextView txtAll;
    private TextView txtPhoneNum;
    private TextView txtPhoneName;
    private TextView txtPay_Title;
    private TextView txtPay_Amount;
    private TextView txtPay_Bank;

    private TextView dataVilArea;

    private LinearLayout llPole;
    private LinearLayout lilaRoomList;
    private LinearLayout ll_bottom_bar;
    private LinearLayout ll_info_bar;

    private ListView listRoomList;
    private ListView listRoomSubList;

    private MainDetailAdapter detailAdapter = null;
    private MainDetailAdapter detailSubAdapter = null;
    // private boolean appStart;

    private int pressPoi;

    private HashMap<String, String> detailStrMap;
    private boolean isExpand;
    private long backKeyPressedTime = 0;
    private Toast toast;

    private String selectSi = "";
    private String selectGu = "";
    private String selectDong = "";
    // private int width;
    // private int height;
    // private boolean isbtnRegister;
    private boolean isViewMore;
    private boolean isAllList = false;
    public boolean isAllVisible = false;
    private boolean isMarkerOneClick = false;

    private WebView webview_list;
    private WebView webview_info;


    // private String strPayment = "";
    // private String phoneNumber = "";
    // private String phoneName = "";

    private GpsInfo gps;
    private MainActivity activity_m;
    private Util util;

    private List<RGetRoomEnty> iitem;
    private HashMap<Integer, RGetRoomEnty> imTagItemMap = new HashMap<Integer, RGetRoomEnty>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // private MapView mMapView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    private static final String __TAG__ = "MainActivity♥♥:";
    private static final String __TAG_MY__ = "TAG_MY...♥♥:";

    String deviceVersion;
    String storeVersion;
    String storeDescription;

    private BackgroundThread mBackgroundThread;
    private ContextCompat activity;

    private MainActivity m_activity;
    private static OptionRangeLoginDialog m_optionLogin;

    private TextView selected_item_textview;

    private int fromMode = 0; //지도에서 상세를 갔을 경우 = 0, 총버튼-> 목록 -> 상세 = 1
    private int fromModeSub = 0; //지도에서 상세를 갔을 경우 = 0, 총버튼-> 목록 -> 상세 = 1

    private int lastListViewPosition = 0; //상세에서 목록으로 갈 경우 다시 최종 위치로 이동하도록 한다.

    public int reqCode = 0;
    // private MapPoint reqMp = null;

    public String gc_lat; // 지도검색에서 가지고 온다.
    public String gc_lon; // 지도검색에서 가지고 온다.

    public void setLastListViewPosition(int pos) {
        this.lastListViewPosition = pos;
    }

    public int getLastListViewPosition() {
        return this.lastListViewPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        Log.e("#####", "FirebaseInstanceId.getInstance().getToken()=" + FirebaseInstanceId.getInstance().getToken());
//        // mapView.setCurrentLocationEventListener(this);
//
//        //푸시메시지
//        if (getIntent().getExtras() != null) {
//            Log.d("#####", "푸시메시지");
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d("#####", "Background Key: " + key);
//                Log.d("#####", "Background Value: " + value);
//            }
//            Log.d("#####", "푸시메시지");
//        }

        // GPS 활성화
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        //개발자용 등록후 주석 처리
        makeKeyHash();

//        // 네트워크 gps 상태를 체크한다.
//        if (checkLocationServicesStatus() == false) {
//            Toast.makeText(MainActivity.this, "네트워크, GPS설정이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
//            showDialogForLocationServiceSetting();
//        }else {
//            // checkRunTimePermission();
//        }


        boolean sb = false;
        sb = Util.isConnectedNetwork(this);
        if (sb == true) {
            // Toast.makeText(MainActivity.this, "network ok.......", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "인터넷에 연결 되지 않았습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 버젼관리
        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View headerLayout = navigationView.getHeaderView(0);

        TextView tvVerssion = headerLayout.findViewById(R.id.txtVersion);

        //////////////////////////////////////////
        // 휴대폰 번호를 가지고 온다.
        psPhoneNumber = Util.getPhoneNumber(MainActivity.this);
        TextView tvPhone = headerLayout.findViewById(R.id.txtPhoneNum);
        tvPhone.setText("ID: " + psPhoneNumber);
        //////////////////////////////////////////


        /////////////////////////////////////////////////
        // 버젼 Version Name
        PackageInfo pi = null;
        try { pi = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        tvVerssion.setText("v" + pi.versionName);
        /////////////////////////////////////////////////

        // DODO 부동산/컨설팅 버전 표시한다.
        ////////////////////////////
        // 이름,결제 금액 필드에 보여준다.
        // ll_bottom_bar = (LinearLayout) findViewById(R.id.ll_bottom_bar);

        final TextView tvName = headerLayout.findViewById(R.id.txtPhoneName);
        final TextView tvPayment = headerLayout.findViewById(R.id.txtPay_Amount);
        final TextView tvPayBank = headerLayout.findViewById(R.id.txtPay_Bank);
        final TextView data_main_board = findViewById(R.id.data_main_board);
        final TextView txt_main_board = findViewById(R.id.txt_main_board);
        final TextView data_main_standby = findViewById(R.id.data_main_standby);

//        ll_info_bar = (LinearLayout) findViewById(R.id.ll_info_bar);
//        Util.setPayment(MainActivity.this, tvName, tvPayment, tvPayBank, data_main_board, txt_main_board, data_main_standby, ll_info_bar, psPhoneNumber );


        // 헨드폰 이름
        // phoneName = tvName.getText().toString();

        /////////////////////////////
        // 이미지 파일 접근 권한 체크
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        //권한요청
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                initView(); // 초기화
                initAction(); // Action 활성화
                // reDataView();  // 데이타 조회

                // 사용자의 결제 정보를 세팅한다.
                ll_info_bar = findViewById(R.id.ll_info_bar);
                Util.setPayment(MainActivity.this, tvName, tvPayment, tvPayBank, data_main_board, txt_main_board, data_main_standby, ll_info_bar, psPhoneNumber );

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                Toast.makeText(MainActivity.this, "권한설정이 완료되지 않았습니다.\n", Toast.LENGTH_SHORT).show();
            }


        };


        Permission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleTitle("▶ [빌라만] 권한요청 중입니다...")
                .setRationaleMessage("위치 및 전화번호, 파일읽기를 사용합니다.\n모두(3) '허용' 해 주세요.")
                .setDeniedTitle("권한없음")
                .setDeniedMessage("권한을 설정하지 않았습니다.\n\n[환경설정] > [권한]에서 권한설정을 하십시오.")
                .setGotoSettingButtonText("　▶ 환경설정")
                .setPermissions(
                        Manifest.permission.INTERNET
                        , Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.CALL_PHONE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_NETWORK_STATE
                )
                .check();

        ///////////////////////////////////////////////////
        // 로그인 화면
        ///////////////////////////////////////////////////
        try {
            OptionRangeLoginDialog optionRangeLoginDialog = new OptionRangeLoginDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
            optionRangeLoginDialog.setContentView(R.layout.range_login_dialog);
            optionRangeLoginDialog.setCanceledOnTouchOutside(false); // 다른 곳을 누르지 못하게 한다.
            optionRangeLoginDialog.setCancelable(false); // 취소 버튼 불가능
            optionRangeLoginDialog.show();

            m_optionLogin = optionRangeLoginDialog; // 화면 재조회를 위해 선언

            optionRangeLoginDialog.forStart(new OptionRangeLoginDialog.Callback() {
                @Override
                public void onResponse(HashMap<String, String> detailMap) {

                    if (detailMap.get("action").equals("no")) {
                        Toast.makeText(MainActivity.this, "다시 실행 바랍니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (detailMap.get("action").equals("error")) {
                        Toast.makeText(MainActivity.this, "사용자가 많습니다. \n잠시후 다시 실행 바랍니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Toast.makeText(MainActivity.this, "빌라만 입니다.", Toast.LENGTH_SHORT).show();
                        reDataView("optionRangeLoginDialog");  // 데이타 조회
                    }

                }
            });
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "로그인 화면 호출 오류입니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }



        /////////////////////////////////////////////////
//        registerForContextMenu( findViewById(R.id.viewMoreSub) );

        //추가한 라인
//        FirebaseMessaging.getInstance().subscribeToTopic("news");
//        FirebaseInstanceId.getInstance().getToken();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_right, menu);

        return true;
    }

    //  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String itemURL = "";

        //////////////////////
        /*
        if (id == R.id.nav_login) {
            //로그인
            itemURL = Util.getURL_VIL() + "/mobile/signin?1=1";
            isViewMore = false;
        } else
        */

        if (id == R.id.nav_members) {

            String data2 = null;
            try {
                //회원가입
                itemURL = Util.getURL_VIL() + "/mobile/signup_app?1=1&usr_id="+URLEncoder.encode("" + psPhoneNumber, "UTF-8"); // TODO 회원가입 (메뉴)
                // isViewMore = false;

                // data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                // data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8") + "&";
                // data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 외부로 웹뷰 띄우기
            // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL));
            startActivity(intent);
            return true;

            //} else if (id == R.id.nav_qna) {
            //질문
            //    itemURL = "http://yes-vil.kr/ask/index?mobile=1";

            //////////////////////
            /// 매물 관리
/*
        } else if (id == R.id.nav_mae_recommend) {
            //추천매물
            itemURL = Util.getURL_VIL() + "/w/vil_mo.php?1=1";
            isViewMore = false;
        } else if (id == R.id.nav_mae_complete) {
            //매매완료
            itemURL = Util.getURL_VIL() + "/w/vil_mae_confirm.php?1=1";
            isViewMore = false;
*/

        } else if (id == R.id.nav_actual_price) {
            //실거래가 검색
            itemURL = Util.getURL_IT() + "/ahh/selectVilla.do?1=1"; // TODO
            // isViewMore = false;

            String data2 = null;
            try {
                data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            // 외부로 웹뷰 띄우기
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
            startActivity(intent);
            return true;

            //////////////////////
            // 매물 등록 요청
            /*
        } else if (id == R.id.nav_main_request) {
            //매수/매도 의뢰
            itemURL = "http://yes-vil.kr/member/enquire?mobile=1";
            isViewMore = false;
            */
        } else if (id == R.id.nav_main_standby) {
            // 관리자 이고, 등급이 2,3 이고 strPayAccount 0보다 크면?
            // if (strType.equals("admin") && (strAuthId.equals("2") || strAuthId.equals("3"))) // && !strPayAccount.equals("0"))
            itemURL = Util.getURL_IT() + "/ahh/viewMainRegist_xy.do?YESIT="; // TODO 매물등록 (메뉴)
            // else
            // isViewMore = false;

            String data2 = null;
            try {
                data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            // 외부로 웹뷰 띄우기
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
            startActivity(intent);
            return true;

            // 매물 등록(요청)
        } else if (id == R.id.nav_letter_send) {

            // 단건 발송
            try {
                nav_letter_send();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return true;

            // 로그 아웃
        } else if (id == R.id.nav_NavClose) {

            AlertDialog.Builder gsDialog = new AlertDialog.Builder(MainActivity.this);
            // gsDialog.setTitle("빌라만");
            gsDialog.setMessage("로그아웃(종료) 하시겠습니까?");
            // gsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255,255,255)));

            gsDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Util.showToast(MainActivity.this, "감사합니다..");
                    FProPrefer.with(context).setPassWd(""); // 인증번호 초기화 한다.

                    finish();
                }
            }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    // return;
                }
            }).show();

            return true;
            // 채팅
        } else if (id == R.id.nav_firebaseChat) {

//            FireMain firemain = new FireMain();
//            firemain.firebase();

            return true;

            // 명함 등록
        } else if (id == R.id.nav_mamaePicture) {

            Intent intent = new Intent(MainActivity.this, MultiImageNameCardActivity.class);
            intent.putExtra(MultiImageNameCardActivity.EXTRA_SHOW_CAMERA, false);
            intent.putExtra(MultiImageNameCardActivity.EXTRA_SELECT_COUNT, 1); // 선택(이미지 업로드 갯수)
            intent.putExtra(MultiImageNameCardActivity.EXTRA_SELECT_MODE, MultiImageNameCardActivity.MODE_MULTI);

            this.startActivityForResult(intent, 9902); // 200
            return true;

        } else if (id == R.id.nav_villaman_info) {
            itemURL = Util.getURL_IT() + "/ahh/villamanInfo.do?1=1"; // TODO
            // itemURL = "http://yes-it.kr/ahh/villamanInfo_temp.do?1=1";]
            // itemURL = "http://yes-vil.kr/w/vil_information.php?1=1";
            // isViewMore = false;

            String data2 = null;
            try {
                data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8") + "&";
                data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 외부로 웹뷰 띄우기
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
            startActivity(intent);
            return true;

            /*
        } else if (id == R.id.nav_map_view) {
            //지도보기
            itemURL = "";
            isViewMore = true;
            */
        } else if (id == R.id.nav_environment) { // 환경설정

            NavEnvironmentDialog navEnvironmentDialog = new NavEnvironmentDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
            navEnvironmentDialog.setContentView(R.layout.nav_environment);
            navEnvironmentDialog.show();
            navEnvironmentDialog.forStart(new NavEnvironmentDialog.Callback() {

                @Override
                public void onResponse(HashMap<String, String> detailMap) {
                    // appStart = false;
                    poiType = SEARCH_POSITION;
                    detailStrMap = detailMap;
                    mapView.setVisibility(View.VISIBLE);
                    mLloBottomBar.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    viewMore.setImageResource(R.drawable.view_more_white);

                    btnVilArea.setText("근처지역 선택");
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                    // reDataView("optionRangeSelectDialog");
                }
            });

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            return true;

        } else if (id == R.id.nav_close) {
            //닫기
            itemURL = "";
            // isViewMore = false;

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            /* 지도를 보여준다. */
            /*
            mapView.setVisibility(View.VISIBLE);
            mLloBottomBar.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            */
            // return true;

            return true;
        }




        /*
        if (id == R.id.nav_camera) {
            //로그인
            itemURL = "http://yes-vil.kr/mobile/signin?1=1";

        } else if (id == R.id.nav_gallery) {
            //회원가입
            itemURL = "http://yes-vil.kr/mobile/signup?1=1";

        } else if (id == R.id.nav_slideshow) {
            //매수/매도 의뢰
            itemURL = "http://yes-vil.kr/member/enquire?1=1";

        } else if (id == R.id.nav_manage) {
            //질문
            itemURL = "http://yes-vil.kr/ask/index?1=1";

        } else if (id == R.id.nav_share) {
            //매물 100선
            itemURL = "http://yes-vil.kr/w/vil_mo.php?1=1";

        } else if (id == R.id.nav_send) {
            //매매완료
            itemURL = "http://yes-vil.kr/w/vil_mae_confirm.php?1=1";

        } else if (id == R.id.nav_rgst) {
            //매매완료
            itemURL = "http://yes-it.kr/ahh/viewMainStandby.do?1=1";
        }
        */
////////////////////////////////////////////////////////////////////////////////////////////////////

        if (isExpand == true) {
            popUpClose();
        }

        if (isViewMore == false) {
            mapView.setVisibility(View.GONE);
            mLloBottomBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            viewMore.setImageResource(R.drawable.view_more_red);
            isViewMore = true;

            /////////////////////////////////
            // url 홈으로 이동하기 위하여 항상 홈으로 이동한다.
            String data = null;
            try {
                data = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                data = data + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // mWebView.setInitialScale(120) ;

            // 이동할 주소가 있으면 페이지로 이동한다.
            if (itemURL != "") {
                mWebView.setPadding(0, 0, 0, 0);
                mWebView.setInitialScale(100);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.setWebViewClient(new WebViewClient());

                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.loadUrl(itemURL + "&" + data);
            }
        } else {
            mapView.setVisibility(View.VISIBLE);
            mLloBottomBar.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            viewMore.setImageResource(R.drawable.view_more_white);
            isViewMore = false;
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        //Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //조건
        if (id == R.id.action_range_select) {

            //if (isExpand == false) {

            if (isExpand == true) {
                popUpClose();
            }
            if (btnSi.getText().equals("") || btnSi.getText().equals("시·도 선택")) {
                Util.showToast(MainActivity.this, "시도는 필수 선택사항입니다.");
                return true;
            }

            HashMap<String, String> addressMap = new HashMap<String, String>();
            if (this.getPackageName().equals("com.yit.villaman5")) {
                addressMap.put("sido", "인천광역시");
            } else {
                addressMap.put("sido", btnSi.getText().toString());
            }
            addressMap.put("gungu", selectGu);
            addressMap.put("dong", selectDong);

            OptionRangeSelectDialog optionRangeSelectDialog = new OptionRangeSelectDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar, addressMap);
            optionRangeSelectDialog.setContentView(R.layout.range_select_dialog);
            optionRangeSelectDialog.show();
            optionRangeSelectDialog.forStart(new OptionRangeSelectDialog.Callback() {

                @Override
                public void onResponse(HashMap<String, String> detailMap) {
                    // appStart = false;
                    poiType = SEARCH_POSITION;
                    detailStrMap = detailMap;
                    mapView.setVisibility(View.VISIBLE);
                    mLloBottomBar.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    viewMore.setImageResource(R.drawable.view_more_white);

                    btnVilArea.setText("근처지역 선택");
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                    reDataView("optionRangeSelectDialog");
                }
            });
            return true;


//            // 검색
//        } else if (id == R.id.action_range_choice) {
//
//            if (isExpand == true) {
//                popUpClose();
//            }
//
//            if (btnSi.getText().equals("") || btnSi.getText().equals("시·도 선택")) {
//                Util.showToast(MainActivity.this, "시도는 필수 선택사항입니다.");
//                return true;
//            }
//
//            HashMap<String, String> addressMap = new HashMap<String, String>();
//            if (this.getPackageName().equals("com.yit.villaman5")) {
//                addressMap.put("sido", "인천광역시");
//            } else {
//                addressMap.put("sido", btnSi.getText().toString());
//            }
//            addressMap.put("gungu", selectGu);
//            addressMap.put("dong", selectDong);
//
//            OptionRangeChoiceDialog optionRangeChoiceDialog = new OptionRangeChoiceDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar, addressMap);
//            optionRangeChoiceDialog.setContentView(R.layout.range_choice_dialog);
//            optionRangeChoiceDialog.show();
//            optionRangeChoiceDialog.forStart(new OptionRangeChoiceDialog.Callback() {
//
//                @Override
//                public void onResponse(HashMap<String, String> detailMap) {
//                    // appStart = false;
//                    poiType = CHOICE_POSITION;
//                    detailStrMap = detailMap;
//                    mapView.setVisibility(View.VISIBLE);
//                    mLloBottomBar.setVisibility(View.VISIBLE);
//                    mWebView.setVisibility(View.GONE);
//                    viewMore.setImageResource(R.drawable.view_more_white);
//
//                    btnVilArea.setText("근처지역 선택");
//                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);
//
//                    reDataView("optionRangeChoiceDialog");
//
//                }
//            });
//
//            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        if (!isExpand) {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();

                /////////////////
                // 지도를 보여주고, webview를 닫는다
                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                viewMore.setImageResource(R.drawable.view_more_white);
                isViewMore = false;
                /////////////////

                // 좌측 네이게이션을 닫는다.
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                Util.showToast(MainActivity.this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.");

                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish();
                // toast.cancel();
            }
        } else {

            if (fromMode == 2) { // fromMode:0 지도에서 보기, 1 리스트, 2 상세보기
                if (fromModeSub == 9) {
                    showVillaSubList(); // 다시 선택 목록으로 이동한다.
                } else {
                    showVillaList(); // 다시 목록으로 이동한다.
                }
            } else {
                Util.collapse(llCoverContent);
                isExpand = false;
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        badgeIntent.putExtra("badge_count_package_name", getComponentName().getPackageName());
        badgeIntent.putExtra("badge_count_class_name", getComponentName().getClassName());
        sendBroadcast(badgeIntent);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initView() {

        try {

            btnHomeLog = findViewById(R.id.btnHomeLog);
            btnCurPosition = findViewById(R.id.btnCurPosition);
            btnVilAll = findViewById(R.id.btnVilAll);
            btnVilOld = findViewById(R.id.btnVilOld);
            btnVilNew = findViewById(R.id.btnVilNew);
            btn_favorite = findViewById(R.id.btn_favorite);
            btn_actual_price = findViewById(R.id.btn_actual_price);
            btn_main_standby = findViewById(R.id.btn_main_standby);
            btn_main_board = findViewById(R.id.btn_main_board);
            btnSi = findViewById(R.id.btnSi);
            btnGu = findViewById(R.id.btnGu);
            btnDong = findViewById(R.id.btnDong);
            btnVilArea = findViewById(R.id.btnVilArea);

            // btnSearch = (ImageView) findViewById(R.id.btnSearch);
            viewMore = findViewById(R.id.viewMore);
            viewMoreSub = findViewById(R.id.viewMoreSub);
            btnClose = findViewById(R.id.btnClose);
            btnBack = findViewById(R.id.btnBack);
            // btnLetterSend = (Button) findViewById(R.id.btnLetterSend);
            btnRefresh = findViewById(R.id.btnRefresh);
            btnPay_Refresh = findViewById(R.id.btnPay_Refresh);
            btnNavClose = findViewById(R.id.btnNavClose);
            relExp = findViewById(R.id.relExp);
            txtExp = findViewById(R.id.txtExp);
            txtSubTotal = findViewById(R.id.txtSubTotal);
            txtTotal = findViewById(R.id.txtTotal);
            tvAddress = findViewById(R.id.tvAddress);
            txtAll = findViewById(R.id.txtAll);
            txtPay_Amount = findViewById(R.id.txtPay_Amount);
            txtPhoneNum = findViewById(R.id.txtPhoneNum);
            txtPhoneName = findViewById(R.id.txtPhoneName);
            txtPay_Bank = findViewById(R.id.txtPay_Bank);

            btnWebView_Del = findViewById(R.id.btnWebView_Del);


            // 광고 web view
            webview_info = findViewById(R.id.webview_info);
            webview_info.getSettings().setJavaScriptEnabled(true);
            webview_info.loadUrl("http://yes-vil.kr/w/info/webview_info.php");


            if (this.getPackageName().equals("com.yit.villaman5")) {
                FProPrefer.with(MainActivity.this).setSi("인천광역시");
                // poiType = ADDR_POSITION;
            } else {
//                if (FProPrefer.with(MainActivity.this).getSi() != null && FProPrefer.with(MainActivity.this).getSi() != "") {
//                    poiType = ADDR_POSITION;
//                } else {
//                    poiType = CURRENT_POSITION;
//                }
            }

            // appStart = false;
            isExpand = false;
            isViewMore = false;
            // poiCurr = CURRENT_OFF;

            Log.d(__TAG__,"◆◆:initView:");
            Log.d("getVilOldNew", "getVilOldNew-" + FProPrefer.with(MainActivity.this).getVilOldNew());
            Log.d("getSi", "getSi-" + FProPrefer.with(MainActivity.this).getSi());
            Log.d("getGu", "getGu-" + FProPrefer.with(MainActivity.this).getGu());
            Log.d("getDong", "getDong-" + FProPrefer.with(MainActivity.this).getDong());

            if (FProPrefer.with(MainActivity.this).getVilOldNew() != null && FProPrefer.with(MainActivity.this).getVilOldNew() != "") {
                if (FProPrefer.with(MainActivity.this).getVilOldNew().equals("전체")) {
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, true);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                    // Util.strVilOldNew = "전체"; // 새로운 버젼
                } else if (FProPrefer.with(MainActivity.this).getVilOldNew().equals("전세")) {
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, true);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                    // Util.strVilOldNew = "구옥"; // 새로운 버젼
                } else if (FProPrefer.with(MainActivity.this).getVilOldNew().equals("월세")) {
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, true);
                    // Util.strVilOldNew = "신축";
                } else {
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, true);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                    FProPrefer.with(MainActivity.this).setVilOldNew("전체");
                    // Util.strVilOldNew = "전체"; // 새로운 버젼
                }
            } else {
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, true);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                FProPrefer.with(MainActivity.this).setVilOldNew("전체");
                // Util.strVilOldNew = "전체";
            }

            // 시도
            if (FProPrefer.with(MainActivity.this).getSi() != null && FProPrefer.with(MainActivity.this).getSi() != "") {
                btnSi.setText(FProPrefer.with(MainActivity.this).getSi());
                Util.setBtnTxtBgColor_VilAllOldNew(btnSi, true);
                // btnSi.setTextColor(Color.parseColor("#0054FF"));
            } else {
            }

            // 시군구
            if (FProPrefer.with(MainActivity.this).getGu() != null && FProPrefer.with(MainActivity.this).getGu() != "") {
                btnGu.setText(FProPrefer.with(MainActivity.this).getGu());
                try {
                    int start = FProPrefer.with(MainActivity.this).getGu().indexOf("(");
                    selectGu = FProPrefer.with(MainActivity.this).getGu().substring(0, start);
                } catch (Exception e) {
                    selectGu = FProPrefer.with(MainActivity.this).getGu();
                }
                Util.setBtnTxtBgColor_VilAllOldNew(btnGu, true);
                // btnGu.setTextColor(Color.parseColor("#0054FF"));
            } else {
                Util.setBtnTxtBgColor_VilAllOldNew(btnGu, false);
                // btnGu.setTextColor(Color.parseColor("#000000"));
            }

            // 읍면동
            if (FProPrefer.with(MainActivity.this).getDong() != null && FProPrefer.with(MainActivity.this).getDong() != "") {
                btnDong.setText(FProPrefer.with(MainActivity.this).getDong());
                try {
                    int start = FProPrefer.with(MainActivity.this).getDong().indexOf("(");
                    selectDong = FProPrefer.with(MainActivity.this).getDong().substring(0, start);
                } catch (Exception e) {
                    selectDong = FProPrefer.with(MainActivity.this).getDong();
                }
                Util.setBtnTxtBgColor_VilAllOldNew(btnDong, true);
            } else {
                Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
            }

//            SpannableString content1 = new SpannableString("초기화 ");
//            // content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
//            txtAll.setText(content1);

            listRoomList = findViewById(R.id.listRoomList);
            listRoomSubList = findViewById(R.id.listRoomList);

            mLloBottomBar = findViewById(R.id.llBottom_bar);

            mWebView = findViewById(R.id.web_view);
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

            });

            String data = null;
            try {
                data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mWebView.loadUrl(Util.getURL_VIL() + "/mobile");
            mWebView.getSettings().setJavaScriptEnabled(true);


            mapView = findViewById(R.id.map_view);
            mapView.setZoomLevel(3, true);
            mapView.zoomIn(true);
            mapView.zoomOut(true);
            mapView.setOpenAPIKeyAuthenticationResultListener(this);
            mapView.setMapViewEventListener(this);
            mapView.setPOIItemEventListener(this);
            mapView.setMapType(MapView.MapType.Standard);

            llCoverContent = findViewById(R.id.llCoverContent);
            llCoverContent.setVisibility(View.GONE);

            Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinInputBo()+":");
            Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinInputBo()+":");
            Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinInputBo()+":");
            Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinInputBo()+":");


            if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                poiType = CHOICE_POSITION;
            } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                poiType = SEARCH_POSITION;
            } else {
                poiType = ADDR_POSITION;
            }

            // 근처지역 거리
            dataVilArea = findViewById(R.id.dataVilArea);
            /////////////////////////////////////////////////////////////
            if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                dataVilArea.setText(FProPrefer.with(context).getEnv_distance()+ "m");
            } else {
                FProPrefer.with(context).setEnv_distance("500");
                dataVilArea.setText("500m");
            }
            // dataVilArea.setPaintFlags(dataVilArea.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            /////////////////////////////////////////////////////////////

            // FProPrefer.with(MainActivity.this).setPoiType(Integer.toString(poiType));

//            poiType = Integer.parseInt(FProPrefer.with(MainActivity.this).getPoiType());

            Log.d(__TAG__,"..............poiType:"+poiType);
            Log.d(__TAG__,"..............FProPrefer.with(MainActivity.this).getPoiType():"+FProPrefer.with(MainActivity.this).getPoiType());
            Log.d(__TAG__,"..............FProPrefer.with(context).getSearchAction():"+FProPrefer.with(context).getSearchAction());


//            String expSearch = "";
//            expSearch = this.reSearchData();
//            Log.d(__TAG__,"expSearch:"+expSearch);
//            util.setTxtExpBgcolor(true,txtAll,"초기화",txtExp, expSearch, "최초 실행중 입니다.");

            // FProPrefer.with(MainActivity.this).setSearchAction("");
            // util.setTxtExpBgcolor(false,txtAll,"초기화",txtExp, "", "최초 실행중 입니다.");



            // relExp.setVisibility(View.INVISIBLE);



        } catch (Exception e) {
            // 현재 위치 찾기
            Util.showToast(MainActivity.this, "initView:"+"오류 입니다.");
            e.printStackTrace();
        }

    }




    public void reDataView(String sPath) {

        try {
            Message msg = Message.obtain();
            pHandler.sendMessageDelayed(msg, 50); // 50
        } catch (Exception e) {
            // 현재 위치 찾기
            Util.showToast(MainActivity.this, "reDataView:"+"오류 입니다.");
            e.printStackTrace();
        } finally {
        }
    }


    protected Handler pHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


//            Log.e(__TAG__, "handleMessage:poiType:" + poiType);
//            OptionLoadingDialog dialog = null;
            // 두번 반복이 된다?
//            Log.d(__TAG_MY__,"handleMessage:poiType:" + poiType);

            if (poiType == CURRENT_POSITION) { // 1
            } else if (poiType == SEARCH_POSITION) { // 2
                searchData_AddrSer("handleMessage:SEARCH_POSITION");
            } else if (poiType == ADDR_POSITION) { // 3
                searchData_AddrSer("handleMessage:ADDR_POSITION");
            } else if (poiType == CHOICE_POSITION) {  // 4
                searchData_AddrSer("handleMessage:CHOICE_POSITION");
            } else if (poiType == MOVE_POSITION) { // 5
                searchData_AddrSer("handleMessage:MOVE_POSITION");
            } else if (poiType == AREA_POSITION) { // 6
                searchData_AddrSer("handleMessage:AREA_POSITION");
            }

        }
    };

    private void searchData_AddrSer(String sPath) {

        // 타입을 저장한다.
        FProPrefer.with(MainActivity.this).setPoiType(Integer.toString(poiType));

        Log.d(__TAG_MY__,"MY:handleMessage:sPth:" + sPath);

        try {

            mapView.setZoomLevel(3, true);

            // 화면 건수 버튼
            txtSubTotal.setVisibility(View.INVISIBLE);
            mapView.removeAllPolylines();

            ////////////////////////////////////////////////////////////////////////////////////////
            if (poiType == CURRENT_POSITION) {


                if (latituce != 0 && longitude != 0) {

                    HashMap<String, String> locMap = new HashMap<String, String>();
                    locMap.put("strLat", String.valueOf(latituce));
                    locMap.put("strLon", String.valueOf(longitude));
                    locMap.put("ma_search", "");

                    /////////////////////////////////////////////////////////////
                    // 근처 거리
                    String strDist = "";
                    if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                        strDist = FProPrefer.with(context).getEnv_distance();
                        if (strDist.equals("500")) {locMap.put("strDist", "0.5");}
                        else if (strDist.equals("800")) {locMap.put("strDist", "0.8");}
                        else if (strDist.equals("1,000")) {locMap.put("strDist", "1");}
                        else if (strDist.equals("1,500")) {locMap.put("strDist", "1.5");}
                        else if (strDist.equals("2,000")) {locMap.put("strDist", "2");}
                        else {locMap.put("strDist", "1");}
                    } else {
                        FProPrefer.with(context).setEnv_distance("500");
                        // Env_distance.setText("500");
                        locMap.put("strDist", "0.5");
                    }
                    /////////////////////////////////////////////////////////////

                    /***************************************************************************/
                    locMap.put("viloldnew", FProPrefer.with(MainActivity.this).getVilOldNew());
                    locMap.put("packagename", this.getPackageName());
                    locMap.put("poitype", Integer.toString(poiType));

                    UServer_CurrentPosition.with(MainActivity.this).forStart(new UServer_CurrentPosition.WServerCurrentPosition.Callback() {
                        public void onResult(JSONArray json) {
                            if (json.length() > 0) {

                                ///////////////////////////////////
                                // 조회결과 리스트를 배열에 담는다.
                                getDetailList(json, "현재 위치");
                                ///////////////////////////////////

                                SpannableString content = new SpannableString("총:" + rGetRoomList.size() + "개");
                                txtTotal.setText(content.toString());
                                tvAddress.setText("빌라 목록(" + rGetRoomList.size() + "개)");
                            } else {
                                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude), true);
                                txtTotal.setText("총:0개");
                                rGetRoomList.clear();
                            }

                            // 구글에서 주소를 가져온다.
                            // 동주소를 부정확하여 보여주지 않는다.
//                            String getAddress = findAddress(mapPointGeo.latitude, mapPointGeo.longitude);
//
//                            Log.d(__TAG__,"getAddress:"+getAddress);
//
//                                                    if (getAddress.equals("")) {
//                                                        btnSi.setText("시·도 선택");
//                                                        btnGu.setText("군·구 선택");
//                                                        btnDong.setText("읍·면·동 선택");
//
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnSi, false);
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnGu, false);
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
//
//                                                        selectSi = "";
//                                                        selectGu = "";
//                                                        selectDong = "";
//
//                                                    } else {
//                                                        String[] array;
//                                                        array = getAddress.split(" ");
//                                                        btnSi.setText(array[1]);
//                                                        btnGu.setText(array[2]);
//                                                        btnDong.setText(array[3]);
//
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnSi, true);
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnGu, true);
//                                                        Util.setBtnTxtBgColor_VilAllOldNew(btnDong, true);
//
//                                                        selectSi = array[1];
//                                                        selectGu = array[2];
//                                                        selectDong = array[3];
//
//                                                        FProPrefer.with(MainActivity.this).setSi(selectSi);
//                                                        FProPrefer.with(MainActivity.this).setGu(selectGu);
//                                                        FProPrefer.with(MainActivity.this).setDong(selectDong);
//
//                                                    }

                            // txtExp.setVisibility(View.INVISIBLE);
                            // txtAll.setTextColor(Color.parseColor("#FFFFFF"));

//                            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);

                        }




                    }, locMap);
                    // appStart = false;

                }
                ////////////////////////////////////////////////////////////////////////////////////////
            } else if (poiType == MOVE_POSITION) {

                // mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

                HashMap<String, String> locMap = new HashMap<String, String>();
                locMap.put("strLat", String.valueOf(detailStrMap.get("strLat")));
                locMap.put("strLon", String.valueOf(detailStrMap.get("strLon")));


                locMap.put("strDist", String.valueOf(detailStrMap.get("strDist")));


                /////////////////////////////////////////////////////////////
                // 근처 거리
                String strDist = "";
                if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                    strDist = FProPrefer.with(context).getEnv_distance();
                    if (strDist.equals("500")) {locMap.put("strDist", "0.5");}
                    else if (strDist.equals("800")) {locMap.put("strDist", "0.8");}
                    else if (strDist.equals("1,000")) {locMap.put("strDist", "1");}
                    else if (strDist.equals("1,500")) {locMap.put("strDist", "1.5");}
                    else if (strDist.equals("2,000")) {locMap.put("strDist", "2");}
                    else {locMap.put("strDist", "1");}
                } else {
                    FProPrefer.with(context).setEnv_distance("500");
                    // Env_distance.setText("500");
                    locMap.put("strDist", "0.5");
                }
                /////////////////////////////////////////////////////////////



                /***************************************************************************/
                locMap.put("viloldnew", FProPrefer.with(MainActivity.this).getVilOldNew());
                locMap.put("packagename", this.getPackageName());
                locMap.put("poitype", Integer.toString(poiType));
                UServer_MoveSearch.with(MainActivity.this).forStart(new UServer_MoveSearch.WServerMoveSearch.Callback() {
                    public void onResult(JSONArray json) {

                        ///////////////////////////////////
                        // 조회결과 리스트를 배열에 담는다.
                        getDetailList(json, "지도 이동");
                        ///////////////////////////////////

                        // mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude), true);
                        // 현제 위치를 중심으로 표시한다.
                        // mapView.setShowCurrentLocationMarker(true);


                        SpannableString content = new SpannableString("총:" + rGetRoomList.size() + "개");
                        txtTotal.setText(content.toString());
                        tvAddress.setText("빌라 목록(" + rGetRoomList.size() + "개)");

                    }
                }, locMap);

                ////////////////////////////////////////////////////////////////////////////////////////
                // 주소 검색 || 선택,조건 검색
                ////////////////////////////////////////////////////////////////////////////////////////
            }  else if (poiType == ADDR_POSITION || poiType == SEARCH_POSITION || poiType == CHOICE_POSITION) {

                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                final HashMap<String, String> locMap = new HashMap<String, String>();

                if (this.getPackageName().equals("com.yit.villaman5")) {
                    locMap.put("sido", "인천광역시");
                } else {
                    locMap.put("sido", btnSi.getText().toString());
                }
                if (btnGu.getText().toString().equals("") || btnGu.getText().toString().equals("군·구 선택")) {
                    locMap.put("gungu", "");
                } else {
                    locMap.put("gungu", selectGu);
                }
                if (btnDong.getText().toString().equals("") || btnDong.getText().equals("읍·면·동 선택")) {
                    locMap.put("dong", "");
                } else {
                    locMap.put("dong", selectDong);
                }


                //////////////////////////////////////////////////////////////
                // SEARCH_POSITION
                if (FProPrefer.with(context).getSearchAction().equals("Search")) {

                    ////////////////////////////////////////////
                    String strSearch = "";
                    if (FProPrefer.with(MainActivity.this).getSearchText() != null && FProPrefer.with(MainActivity.this).getSearchText() != "") {
                        locMap.put("ma_bld_nm", FProPrefer.with(MainActivity.this).getSearchText());
                    } else {
                        locMap.put("ma_bld_nm", "");
                    }
                    if (locMap.get("ma_bld_nm") != "") {
                        strSearch = strSearch + "검색어:" + locMap.get("ma_bld_nm") + " ";
                    }

                    /////////////////////////////////////////////////////////
                    // 보증금
                    if (FProPrefer.with(MainActivity.this).getMinInputBo() != 0 && FProPrefer.with(MainActivity.this).getMinInputBo() != 0) {
                        locMap.put("ma_bo_ney1", String.valueOf(FProPrefer.with(MainActivity.this).getMinInputBo() * 100));
                    } else {
                        locMap.put("ma_bo_ney1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxInputBo() != 0 && FProPrefer.with(MainActivity.this).getMaxInputBo() != 200) {
                        locMap.put("ma_bo_ney2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxInputBo() * 100));
                    } else {
                        locMap.put("ma_bo_ney2", "");
                    }

                    if (  (locMap.get("ma_bo_ney1").equals("0") && locMap.get("ma_bo_ney2").equals("20000")) || (locMap.get("ma_bo_ney1").equals("") && locMap.get("ma_bo_ney2").equals("")) ) {
                        locMap.put("ma_bo_ney1","");
                        locMap.put("ma_bo_ney2","");
                    } else {
                        strSearch = strSearch + "보증금:" + locMap.get("ma_bo_ney1") + "~" + locMap.get("ma_bo_ney2") + " ";
                    }

                    /////////////////////////////////////////////////////////
                    // 월세
                    if (FProPrefer.with(MainActivity.this).getMinInputMonth() != 0 && FProPrefer.with(MainActivity.this).getMinInputMonth() != 5) {
                        locMap.put("ma_month_ney1", String.valueOf(FProPrefer.with(MainActivity.this).getMinInputMonth() ));
                    } else {
                        locMap.put("ma_month_ney1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxInputMonth() != 0 && FProPrefer.with(MainActivity.this).getMaxInputMonth() != 200) {
                        locMap.put("ma_month_ney2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxInputMonth() ));
                    } else {
                        locMap.put("ma_month_ney2", "");
                    }

                    if (  (locMap.get("ma_month_ney1").equals("5") && locMap.get("ma_month_ney2").equals("200")) || (locMap.get("ma_month_ney1").equals("") && locMap.get("ma_month_ney2").equals("")) ) {
                        locMap.put("ma_month_ney1","");
                        locMap.put("ma_month_ney2","");
                    } else {
                        strSearch = strSearch + "월세:" + locMap.get("ma_month_ney1") + "~" + locMap.get("ma_month_ney2") + " ";
                    }

                    // 전용
                    if (FProPrefer.with(MainActivity.this).getMinArea() != 0 && FProPrefer.with(MainActivity.this).getMinArea() != 5) {
                        locMap.put("ma_jeon_area1", String.valueOf(FProPrefer.with(MainActivity.this).getMinArea()));
                    } else {
                        locMap.put("ma_jeon_area1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxArea() != 0 && FProPrefer.with(MainActivity.this).getMaxArea() != 30) {
                        locMap.put("ma_jeon_area2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxArea()));
                    } else {
                        locMap.put("ma_jeon_area2", "");
                    }
                    if (locMap.get("ma_jeon_area1").equals("5") && locMap.get("ma_jeon_area2").equals("30") || locMap.get("ma_jeon_area1").equals("")) {
                        locMap.put("ma_jeon_area1","");
                        locMap.put("ma_jeon_area2","");
                    } else {
                        strSearch = strSearch + "전용:" + locMap.get("ma_jeon_area1") + "~" + locMap.get("ma_jeon_area2") + " ";
                    }

                    // 층수
                    if (FProPrefer.with(MainActivity.this).getMinFloor() != 0 && FProPrefer.with(MainActivity.this).getMinFloor() != 0) {
                        locMap.put("ma_level1", String.valueOf(FProPrefer.with(MainActivity.this).getMinFloor()));
                    } else {
                        locMap.put("ma_level1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxFloor() != 0 && FProPrefer.with(MainActivity.this).getMaxFloor() != 7) {
                        locMap.put("ma_level2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxFloor()));
                    } else {
                        locMap.put("ma_level2", "");
                    }
                    if ((locMap.get("ma_level1").equals("") || locMap.get("ma_level1").equals("0")) && locMap.get("ma_level2").equals("7") || locMap.get("ma_level1").equals("")) {
                        locMap.put("ma_level1","");
                        locMap.put("ma_level2","");
                    } else {
                        strSearch = strSearch + "층수:" + locMap.get("ma_level1") + "~" + locMap.get("ma_level2") + " ";
                    }

                    // 방수
                    if (FProPrefer.with(MainActivity.this).getMinRoom() != 0 && FProPrefer.with(MainActivity.this).getMinRoom() != 1) {
                        locMap.put("ma_room1", String.valueOf(FProPrefer.with(MainActivity.this).getMinRoom()));
                    } else {
                        locMap.put("ma_room1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxRoom() != 0 && FProPrefer.with(MainActivity.this).getMaxRoom() != 5) {
                        locMap.put("ma_room2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxRoom()));
                    } else {
                        locMap.put("ma_room2", "");
                    }

                    if (locMap.get("ma_room1").equals("1") && locMap.get("ma_room2").equals("5") || locMap.get("ma_room1").equals("")) {
                        locMap.put("ma_room1","");
                        locMap.put("ma_room2","");
                    } else {
                        strSearch = strSearch + "방수:" + locMap.get("ma_room1") + "~" + locMap.get("ma_room2") + " ";
                    }

                    // 년식
                    if (FProPrefer.with(MainActivity.this).getMinYear() != 0 && FProPrefer.with(MainActivity.this).getMinYear() != 1985 ) {
                        locMap.put("ma_jun_year1", String.valueOf(FProPrefer.with(MainActivity.this).getMinYear()));
                    } else {
                        locMap.put("ma_jun_year1", "");
                    }
                    if (FProPrefer.with(MainActivity.this).getMaxYear() != 0 && FProPrefer.with(MainActivity.this).getMaxYear() != Util.getCurYear()) {
                        locMap.put("ma_jun_year2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxYear()));
                    } else {
                        locMap.put("ma_jun_year2", "");
                    }

                    // Calendar calendar = new GregorianCalendar(Locale.KOREA);
                    // String nYear = Integer.toString(calendar.get(Calendar.YEAR));

                    if (locMap.get("ma_jun_year1").equals("1985") && locMap.get("ma_jun_year2").equals(Util.getCurYear()) || locMap.get("ma_jun_year1").equals("") ) {
                        locMap.put("ma_jun_year1","");
                        locMap.put("ma_jun_year2","");
                    } else {
                        strSearch = strSearch + "년식:" + locMap.get("ma_jun_year1") + "~" + locMap.get("ma_jun_year2") + " ";
                    }

                    util.setTxtExpBgcolor(true,txtAll,"초기화",txtExp, strSearch, "Search 실행중 입니다.");

                //////////////////////////////////////////////////////////////
                //
                } else {
                    locMap.put("ma_bld_nm", "");

                    locMap.put("ma_bo_ney1", "");
                    locMap.put("ma_bo_ney2", "");
                    locMap.put("ma_month_ney1", "");
                    locMap.put("ma_month_ney2", "");
                    locMap.put("ma_jeon_area1", "");
                    locMap.put("ma_jeon_area2", "");
                    locMap.put("ma_level1", "");
                    locMap.put("ma_level2", "");
                    locMap.put("ma_room1", "");
                    locMap.put("ma_room2", "");
                    locMap.put("ma_jun_year1", "");
                    locMap.put("ma_jun_year2", "");

                    locMap.put("ma_status1", "");
                    locMap.put("ma_gallery", "");
                    util.setTxtExpBgcolor(false, txtAll, "초기화", txtExp, "","Search:Choice 둘중에 모름입니다.");
                }

                //////////////////////////////////////////////////////////////////////
                //
                //////////////////////////////////////////////////////////////////////
                if (poiType == ADDR_POSITION ) { // 3
                } else if (poiType == SEARCH_POSITION ) { // 2
                    locMap.put("ma_search", "");
                    locMap.put("ma_status1", "");
                    locMap.put("ma_gallery", "");
                    locMap.put("mynew02", "");
                    locMap.put("myconfirm02", "");
                } else if (poiType == CHOICE_POSITION ) { // 4
                    locMap.put("ma_bo_ney1", "");
                    locMap.put("ma_bo_ney2", "");
                    locMap.put("ma_month_ney1", "");
                    locMap.put("ma_month_ney2", "");

                    locMap.put("ma_jeon_area1", "");
                    locMap.put("ma_jeon_area2", "");
                    locMap.put("ma_level1", "");
                    locMap.put("ma_level2", "");
                    locMap.put("ma_room1", "");
                    locMap.put("ma_room2", "");
                    locMap.put("ma_jun_year1", "");
                    locMap.put("ma_jun_year2", "");
                    locMap.put("ma_bld_nm", "");
                    locMap.put("ma_search", FProPrefer.with(context).getChoiceMaSearch());
                }



                /***************************************************************************/
                if (FProPrefer.with(MainActivity.this).getVilOldNew().equals("대기")) {
                    locMap.put("ma_use_yn", "S");
                } else {
                    locMap.put("ma_use_yn", "A");
                }
                locMap.put("viloldnew", FProPrefer.with(MainActivity.this).getVilOldNew());
                locMap.put("packagename", this.getPackageName());
                locMap.put("poitype", Integer.toString(poiType));

                UServer_DetailSearch.with(MainActivity.this).forStart(new UServer_DetailSearch.WServerDetailSearch.Callback() {
                    public void onResult(JSONArray json, String sMess) {

                        ///////////////////////////////////
                        // 조회결과 리스트를 배열에 담는다.
                        getDetailList(json,sMess);
                        ///////////////////////////////////

                        SpannableString content = new SpannableString("총:" + rGetRoomList.size() + "개");
                        txtTotal.setText(content.toString());

                        tvAddress.setText("빌라 목록(" + rGetRoomList.size() + "개)");



                        // 사진 추가 였으면 해당 매물로 이동한다.
                        if (reqCode == 9901) {
                            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(Double.parseDouble(gc_lat), Double.parseDouble(gc_lon)), 0, true);
                            mapView.removeAllCircles();
                            reqCode = 0;
                        }

                    }
                }, locMap);

                ////////////////////////////////////////////////////////////////////////////////////////
                // 근처 지역 선택
            } else if (poiType == AREA_POSITION) {

                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

                HashMap<String, String> locMap = new HashMap<String, String>();

                if (this.getPackageName().equals("com.yit.villaman5")) {
                    locMap.put("sido", "인천광역시");
                } else {
                    locMap.put("sido", btnSi.getText().toString());
                }
//
//                if (btnGu.getText().toString().equals("") || btnGu.getText().toString().equals("군·구 선택")) {
//                    locMap.put("gungu", "");
//                } else {
//                    locMap.put("gungu", selectGu);
//                }
//                if (btnDong.getText().toString().equals("") || btnDong.getText().equals("읍·면·동 선택")) {
//                    locMap.put("dong", "");
//                } else {
//                    locMap.put("dong", selectDong);
//                }

                locMap.put("gungu", "");
                locMap.put("dong", "");




                ////////////////////////////////////////////
                String strSearch = "";
                if (FProPrefer.with(MainActivity.this).getSearchText() != null && FProPrefer.with(MainActivity.this).getSearchText() != "") {
                    locMap.put("ma_bld_nm", FProPrefer.with(MainActivity.this).getSearchText());
                } else {
                    locMap.put("ma_bld_nm", "");
                }
                if (locMap.get("ma_bld_nm") != "") {
                    strSearch = strSearch + "검색어:" + locMap.get("ma_bld_nm") + " ";
                }

                /////////////////////////////////////////////////////////
                // 보증금
                if (FProPrefer.with(MainActivity.this).getMinInputBo() != 0 && FProPrefer.with(MainActivity.this).getMinInputBo() != 0) {
                    locMap.put("ma_bo_ney1", String.valueOf(FProPrefer.with(MainActivity.this).getMinInputBo() * 100));
                } else {
                    locMap.put("ma_bo_ney1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxInputBo() != 0 && FProPrefer.with(MainActivity.this).getMaxInputBo() != 200) {
                    locMap.put("ma_bo_ney2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxInputBo() * 100));
                } else {
                    locMap.put("ma_bo_ney2", "");
                }

                if (  (locMap.get("ma_bo_ney1").equals("0") && locMap.get("ma_bo_ney2").equals("20000")) || (locMap.get("ma_bo_ney1").equals("") && locMap.get("ma_bo_ney2").equals("")) ) {
                    locMap.put("ma_bo_ney1","");
                    locMap.put("ma_bo_ney2","");
                } else {
                    strSearch = strSearch + "보증금:" + locMap.get("ma_bo_ney1") + "~" + locMap.get("ma_bo_ney2") + " ";
                }

                /////////////////////////////////////////////////////////
                // 월세
                if (FProPrefer.with(MainActivity.this).getMinInputMonth() != 0 && FProPrefer.with(MainActivity.this).getMinInputMonth() != 5) {
                    locMap.put("ma_month_ney1", String.valueOf(FProPrefer.with(MainActivity.this).getMinInputMonth() ));
                } else {
                    locMap.put("ma_month_ney1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxInputMonth() != 0 && FProPrefer.with(MainActivity.this).getMaxInputMonth() != 200) {
                    locMap.put("ma_month_ney2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxInputMonth() ));
                } else {
                    locMap.put("ma_month_ney2", "");
                }

                if (  (locMap.get("ma_month_ney1").equals("5") && locMap.get("ma_month_ney2").equals("200")) || (locMap.get("ma_month_ney1").equals("") && locMap.get("ma_month_ney2").equals("")) ) {
                    locMap.put("ma_month_ney1","");
                    locMap.put("ma_month_ney2","");
                } else {
                    strSearch = strSearch + "월세:" + locMap.get("ma_month_ney1") + "~" + locMap.get("ma_month_ney2") + " ";
                }

                // 전용
                if (FProPrefer.with(MainActivity.this).getMinArea() != 0 && FProPrefer.with(MainActivity.this).getMinArea() != 5) {
                    locMap.put("ma_jeon_area1", String.valueOf(FProPrefer.with(MainActivity.this).getMinArea()));
                } else {
                    locMap.put("ma_jeon_area1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxArea() != 0 && FProPrefer.with(MainActivity.this).getMaxArea() != 30) {
                    locMap.put("ma_jeon_area2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxArea()));
                } else {
                    locMap.put("ma_jeon_area2", "");
                }
                if (locMap.get("ma_jeon_area1").equals("5") && locMap.get("ma_jeon_area2").equals("30") || locMap.get("ma_jeon_area1").equals("")) {
                    locMap.put("ma_jeon_area1","");
                    locMap.put("ma_jeon_area2","");
                } else {
                    strSearch = strSearch + "전용:" + locMap.get("ma_jeon_area1") + "~" + locMap.get("ma_jeon_area2") + " ";
                }

                // 층수
                if (FProPrefer.with(MainActivity.this).getMinFloor() != 0 && FProPrefer.with(MainActivity.this).getMinFloor() != 0) {
                    locMap.put("ma_level1", String.valueOf(FProPrefer.with(MainActivity.this).getMinFloor()));
                } else {
                    locMap.put("ma_level1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxFloor() != 0 && FProPrefer.with(MainActivity.this).getMaxFloor() != 7) {
                    locMap.put("ma_level2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxFloor()));
                } else {
                    locMap.put("ma_level2", "");
                }
                if ((locMap.get("ma_level1").equals("") || locMap.get("ma_level1").equals("0")) && locMap.get("ma_level2").equals("7") || locMap.get("ma_level1").equals("")) {
                    locMap.put("ma_level1","");
                    locMap.put("ma_level2","");
                } else {
                    strSearch = strSearch + "층수:" + locMap.get("ma_level1") + "~" + locMap.get("ma_level2") + " ";
                }

                // 방수
                if (FProPrefer.with(MainActivity.this).getMinRoom() != 0 && FProPrefer.with(MainActivity.this).getMinRoom() != 1) {
                    locMap.put("ma_room1", String.valueOf(FProPrefer.with(MainActivity.this).getMinRoom()));
                } else {
                    locMap.put("ma_room1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxRoom() != 0 && FProPrefer.with(MainActivity.this).getMaxRoom() != 5) {
                    locMap.put("ma_room2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxRoom()));
                } else {
                    locMap.put("ma_room2", "");
                }

                if (locMap.get("ma_room1").equals("1") && locMap.get("ma_room2").equals("5") || locMap.get("ma_room1").equals("")) {
                    locMap.put("ma_room1","");
                    locMap.put("ma_room2","");
                } else {
                    strSearch = strSearch + "방수:" + locMap.get("ma_room1") + "~" + locMap.get("ma_room2") + " ";
                }

                // 년식
                if (FProPrefer.with(MainActivity.this).getMinYear() != 0 && FProPrefer.with(MainActivity.this).getMinYear() != 1985 ) {
                    locMap.put("ma_jun_year1", String.valueOf(FProPrefer.with(MainActivity.this).getMinYear()));
                } else {
                    locMap.put("ma_jun_year1", "");
                }
                if (FProPrefer.with(MainActivity.this).getMaxYear() != 0 && FProPrefer.with(MainActivity.this).getMaxYear() != Util.getCurYear()) {
                    locMap.put("ma_jun_year2", String.valueOf(FProPrefer.with(MainActivity.this).getMaxYear()));
                } else {
                    locMap.put("ma_jun_year2", "");
                }

                // Calendar calendar = new GregorianCalendar(Locale.KOREA);
                // String nYear = Integer.toString(calendar.get(Calendar.YEAR));

                if (locMap.get("ma_jun_year1").equals("1985") && locMap.get("ma_jun_year2").equals(Util.getCurYear()) || locMap.get("ma_jun_year1").equals("") ) {
                    locMap.put("ma_jun_year1","");
                    locMap.put("ma_jun_year2","");
                } else {
                    strSearch = strSearch + "년식:" + locMap.get("ma_jun_year1") + "~" + locMap.get("ma_jun_year2") + " ";
                }




                locMap.put("strLat", String.valueOf(lat_ituce));
                locMap.put("strLon", String.valueOf(long_itude));

                /////////////////////////////////////////////////////////////
                // 근처 거리
                String strDist = "";
                if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                    strDist = FProPrefer.with(context).getEnv_distance();
                    if (strDist.equals("500")) {locMap.put("strDist", "0.5");}
                    else if (strDist.equals("800")) {locMap.put("strDist", "0.8");}
                    else if (strDist.equals("1,000")) {locMap.put("strDist", "1");}
                    else if (strDist.equals("1,500")) {locMap.put("strDist", "1.5");}
                    else if (strDist.equals("2,000")) {locMap.put("strDist", "2");}
                    else {locMap.put("strDist", "1");}
                } else {
                    FProPrefer.with(context).setEnv_distance("500");
                    // Env_distance.setText("500");
                    locMap.put("strDist", "0.5");
                }
                /////////////////////////////////////////////////////////////

                /***************************************************************************/
                locMap.put("ma_use_yn", "A");
                locMap.put("viloldnew", FProPrefer.with(MainActivity.this).getVilOldNew());
                locMap.put("packagename", this.getPackageName());
                locMap.put("poitype", Integer.toString(poiType));
                //////////////////////////////////
                // UServer_AreaSearch
                //////////////////////////////////
                UServer_AreaSearch.with(MainActivity.this).forStart(new UServer_AreaSearch.WServerAreaSearch.Callback() {
                    public void onResult(JSONArray json) {

                        ///////////////////////////////////
                        // 조회결과 리스트를 배열에 담는다.
                        getDetailList(json, "'" + btnVilArea.getText() + "' 근처");

                        // 초기화 버튼을 초기화 한다.
                        // txtAll.setTextColor(Color.parseColor("#FFFFFF"));
                        // txtExp.setVisibility(View.VISIBLE);

                        SpannableString content = new SpannableString("총:" + rGetRoomList.size() + "개");
                        txtTotal.setText(content.toString());
                        tvAddress.setText("빌라 목록(" + rGetRoomList.size() + "개)");

                        // txtAll.setTextColor(Color.parseColor("#FFFFFF"));

                    }
                }, locMap);


            } else {
                Util.showToast(MainActivity.this, "poiType:"+"오류 입니다.");
            }


//            if (txtAll.getTextColors() == ColorStateList.valueOf(Color.RED)) {
//                Util.showToast(MainActivity.this, "검색 조건 데이타가 있습니다.");
//            }
            // 전역변수 프린트
            Util.printFProPrefer(MainActivity.this, "searchData_AddrSer:poiType:"+poiType);

        } catch (Exception e) {
            // 현재 위치 찾기
            Util.showToast(MainActivity.this, "searchData_AddrSer:("+poiType+") 오류 입니다.");


            e.printStackTrace();
        }



    }


    ////////////////////////////////////////////////////////////////////////
    private void initAction() {


        /***************************************/
        /* 확대 클릭 */
        /***************************************/
        /* findViewById(R.id.btnzoomout).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mapView.zoomOut(true);
            }
        }); */

        /***************************************/
        /* 축소 클릭 */
        /***************************************/
        /* findViewById(R.id.btnzoomin).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mapView.zoomIn(true);
            }
        }); */

        /***************************************/
        /* 전체빌라 클릭 */
        /***************************************/
        btnVilAll.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, true);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                FProPrefer.with(MainActivity.this).setVilOldNew("전체");

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);

                btnVilArea.setText("근처지역 선택");
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                /////////////////////////////////////////////////////////
                // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                    poiType = CHOICE_POSITION;
                } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                    poiType = SEARCH_POSITION;
                } else {
                    poiType = ADDR_POSITION;
                }
                searchData_AddrSer("btnVilAll.setOnClickListener");
                /////////////////////////////////////////////////////////

            }
        });

        /***************************************/
        /* 구옥빌라 클릭 */
        /***************************************/
        btnVilOld.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, false);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, true);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, false);
                FProPrefer.with(MainActivity.this).setVilOldNew("전세");

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);

                btnVilArea.setText("근처지역 선택");
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                /////////////////////////////////////////////////////////
                // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                    poiType = CHOICE_POSITION;
                } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                    poiType = SEARCH_POSITION;
                } else {
                    poiType = ADDR_POSITION;
                }

                searchData_AddrSer("btnVilOld.setOnClickListener");
                /////////////////////////////////////////////////////////

            }
        });

        /***************************************/
        /* 신축버튼 클릭 */
        /***************************************/
        btnVilNew.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                Util.setBtnTxtBgColor_VilAllOldNew(btnVilAll, false);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilOld, false);
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilNew, true);
                FProPrefer.with(MainActivity.this).setVilOldNew("월세");

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);

                btnVilArea.setText("근처지역 선택");
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                /////////////////////////////////////////////////////////
                // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                    poiType = CHOICE_POSITION;
                } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                    poiType = SEARCH_POSITION;
                } else {
                    poiType = ADDR_POSITION;
                }
                searchData_AddrSer("btnVilNew.setOnClickListener");
                /////////////////////////////////////////////////////////

            }
        });

        /***************************************/
        /* 즐겨찾기 */
        /***************************************/
        btn_favorite.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Util.showToast(MainActivity.this, "즐겨찾기 등록 매물 조회입니다.");

                // HashMap<String, String> detailMap = new HashMap<String, String>();
                detailStrMap = new HashMap<String, String>();

                detailStrMap.put("sido", btnSi.getText().toString());
                detailStrMap.put("gungu", "");
                detailStrMap.put("dong", "");

                detailStrMap.put("ma_bo_ney1", "");
                detailStrMap.put("ma_bo_ney2", "");
                detailStrMap.put("ma_month_ney1", "");
                detailStrMap.put("ma_month_ney2", "");

                detailStrMap.put("ma_jeon_area1",  "");
                detailStrMap.put("ma_jeon_area2",  "");
                detailStrMap.put("ma_level1",  "");
                detailStrMap.put("ma_level2",  "");
                detailStrMap.put("ma_room1",  "");
                detailStrMap.put("ma_room2",  "");
                detailStrMap.put("ma_jun_year1",  "");
                detailStrMap.put("ma_jun_year2",  "");
                detailStrMap.put("ma_bld_nm", "");

                detailStrMap.put("ma_search", "myfavorite");
                detailStrMap.put("viloldnew", "");

                // 선택 조회 상태 저장
                FProPrefer.with(context).setChoiceMaSearch("myfavorite");
                FProPrefer.with(context).setSearchAction("Choice");

                FProPrefer.with(MainActivity.this).setSi(btnSi.getText().toString().replace("⊙ ",""));

//                if (detailStrMap == null) {
//                    detailStrMap = new HashMap<String, String>();

//                }

//                detailStrMap.put("sido",btnSi.getText().toString().replace("⊙ ",""));
//                detailStrMap.put("ma_search","myfavorite");

                poiType = CHOICE_POSITION;
                reDataView("btn_favorite.setOnClickListener");

            }

        });


        /***************************************/
        /* 실거래가 */
        /***************************************/
        btn_actual_price.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                String itemURL = "";

                //실거래가 검색
                itemURL = Util.getURL_IT() + "/ahh/selectVilla.do?1=1"; // TODO
                // isViewMore = false;

                String data2 = null;
                try {
                    data2 = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                    data2 = data2 + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8") + "&";
                    data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                // 외부로 웹뷰 띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n","")));
                startActivity(intent);
                // return true;



            }
        });


        /***************************************/
        /* 매물 등록 */
        /***************************************/
        btn_main_standby.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                /*
                mapView.setVisibility(View.GONE);
                mLloBottomBar.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
                viewMore.setImageResource(R.drawable.view_more_red);
                isViewMore = true;
                */

                /////////////////////////////////
                // url 홈으로 이동하기 위하여 항상 홈으로 이동한다.
                String data = null;

                try {
                    data = URLEncoder.encode("pay_amount", "UTF-8") + "=" + URLEncoder.encode("" + strPayment.replace(",",""), "UTF-8") + "&";
                    data = data + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String itemURL = "";

                // 관리자 이고, 등급이 2,3 이고 strPayAccount 0보다 크면?
                //if (strType.equals("admin") && (strAuthId.equals("2") || strAuthId.equals("3"))) // && !strPayAccount.equals("0"))
                itemURL = Util.getURL_IT() + "/ahh/viewMainRegist_xy.do?YESIT="; // TODO 매물등록 (바탕)
                // else
                // 테스트 String itemURL = Util.getURL_IT() + "/temp/d.jsp?1=1";

                // 외부로 웹뷰 띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data).replace("\n","")));
                startActivity(intent);

                /*
                mWebView.setPadding(0, 0, 0, 0);
                mWebView.setInitialScale(100);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.setWebViewClient(new WebViewClient());

                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                // mWebView.loadUrl(itemURL + "YESIT=" + Util.getBase64encode(data).replace("\n",""));
                mWebView.loadUrl(itemURL + data);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                */

            }
        });

        /***************************************/
        /* 게시판 */
        /***************************************/
        btn_main_board.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }
/*
                mapView.setVisibility(View.GONE);
                mLloBottomBar.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
                viewMore.setImageResource(R.drawable.view_more_red);
                isViewMore = true;
*/
                /////////////////////////////////
                // url 홈으로 이동하기 위하여 항상 홈으로 이동한다.
                String data = null;
                try {
                    data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String itemURL = Util.getURL_IT() + "/BoardList.bo?action=01"; // TODO

                // 외부로 웹뷰 띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + "&" + data));
                startActivity(intent);

                /*
                mWebView.setPadding(0, 0, 0, 0);
                mWebView.setInitialScale(100);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.setWebViewClient(new WebViewClient());

                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.loadUrl(itemURL + "&" + data);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                ////////////////////////////
                // 결제 금액 필드에 보여준다.
                TextView data_main_board = (TextView) findViewById(R.id.data_main_board);
                TextView txt_main_board = (TextView) findViewById(R.id.txt_main_board);
                data_main_board.setText("");
                txt_main_board.setText("");

                ////////////////////////////////////////////////////////////////////
                // DODO 뱃지
                ////////////////////////////////////////////////////////////////////
                // if (strBoardCnt != null) {
                    strBoardCnt = "0";
                    int badgeCount = 0;
                    Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
                    badgeIntent.putExtra("badge_count", badgeCount);
                    badgeIntent.putExtra("badge_count_package_name", getComponentName().getPackageName());
                    badgeIntent.putExtra("badge_count_class_name", getComponentName().getClassName());
                    sendBroadcast(badgeIntent);
                // }

*/


            }
        });

        /***************************************/
        /* 시도 클릭 */
        /***************************************/

        btnSi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (isExpand == true) {

                    popUpClose();

                }

                // 인천광역시 이면 시도를 선택할 필요가 없다.
                if (MainActivity.this.getPackageName().equals("com.yit.villaman5")) {

                    FProPrefer.with(MainActivity.this).setSi("인천광역시");
                    FProPrefer.with(MainActivity.this).setGu("");
                    FProPrefer.with(MainActivity.this).setDong("");
                    btnSi.setText("인천광역시");
                    btnGu.setText("군·구 선택");
                    btnDong.setText("읍·면·동 선택");
                    Util.setBtnTxtBgColor_VilAllOldNew(btnSi, true);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnGu, false);
                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                    btnVilArea.setText("근처지역 선택");
                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                    selectSi = "인천광역시";
                    selectGu = "";
                    selectDong = "";
                    mapView.setVisibility(View.VISIBLE);
                    mLloBottomBar.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    viewMore.setImageResource(R.drawable.view_more_white);
                    /////////////////////////////////////////////////////////
                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                        poiType = CHOICE_POSITION;
                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                        poiType = SEARCH_POSITION;
                    } else {
                        poiType = ADDR_POSITION;
                    }
                    reDataView("UServerGetSi.WServerGetSi");
                    /////////////////////////////////////////////////////////

                    return;
                }

                final UServerGetSi.WServerGetSi sido = UServerGetSi.with(MainActivity.this).forStart(new UServerGetSi.WServerGetSi.Callback() {
                    @Override
                    public void onResult(JSONArray json) {
                        try {

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);

                            for (int i = 0; i < json.length(); i++) {
                                adapter.add("⊙ " + json.getJSONObject(i).get("sido").toString());
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("● '시도'를 선택해 주세요.");
                            builder.setIcon(R.drawable.villaman);
                            // builder.setIcon(R.drawable.default_check);
                            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnSi.setText(adapter.getItem(which).replace("⊙ ",""));

                                    Util.setBtnTxtBgColor_VilAllOldNew(btnSi, true);
                                    // btnSi.setTextColor(Color.parseColor("#0054FF"));

                                    FProPrefer.with(MainActivity.this).setSi(adapter.getItem(which).replace("⊙ ",""));
                                    FProPrefer.with(MainActivity.this).setGu("");
                                    FProPrefer.with(MainActivity.this).setDong("");
                                    btnGu.setText("군·구 선택");
                                    btnDong.setText("읍·면·동 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnGu, false);
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                                    btnVilArea.setText("근처지역 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                                    selectSi = adapter.getItem(which).replace("⊙ ","");
                                    selectGu = "";
                                    selectDong = "";

                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);


                                    /////////////////////////////////////////////////////////
                                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                                        poiType = CHOICE_POSITION;
                                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                                        poiType = SEARCH_POSITION;
                                    } else {
                                        poiType = ADDR_POSITION;
                                    }
                                    reDataView("UServerGetSi.WServerGetSi");
                                    /////////////////////////////////////////////////////////


                                }
                            });

                            builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

                            // Cancle 하면 종료 합니다.
                            builder.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }
                            );


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });






            }
        });

        /***************************************/
        /* 군구 클릭 */
        /***************************************/
        btnGu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSi.getText().toString().equals("") || btnSi.getText().toString().equals("시·도 선택")) {
                    Util.showToast(MainActivity.this, "시·도 선택을 선택해주세요.");

                    return;
                }
                //if (isExpand == false) {
                if (isExpand == true) {

                    popUpClose();

                }
                UServerGetGunGu.with(MainActivity.this).forStart(new UServerGetGunGu.WServerGetGunGu.Callback() {

                    @Override
                    public void onResult(final JSONArray json) {
                        try {
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);
                            // adapter.add("- "+ btnSi.getText().toString() + " 전체 -");
                            for (int i = 0; i < json.length(); i++) {

                                adapter.add("⊙ " + json.getJSONObject(i).get("gungu").toString());
//                                if (json.getJSONObject(i).get("ncnt").equals(null)) {
//                                    adapter.add("⊙ " + json.getJSONObject(i).get("gungu").toString());
//                                } else {
//                                    adapter.add("⊙ " + json.getJSONObject(i).get("gungu").toString() + "(" + json.getJSONObject(i).get("ncnt").toString() + ")");
//                                }

                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("● '군.구'를 선택해 주세요.");
                            builder.setIcon(R.drawable.villaman);
                            // builder.setIcon(R.drawable.default_check);
                            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        selectGu = json.getJSONObject(which).get("gungu").toString();
                                    } catch (JSONException e) {
                                        // Auto-generated
                                        // catch
                                        // block
                                        e.printStackTrace();
                                    }

//                                    if (btnGu.getText().toString().indexOf("전체") > -1) {
//                                        Toast.makeText(MainActivity.this, "군구 선택.", Toast.LENGTH_SHORT).show();
//                                        return;
//                                        // reDataView();
//                                    }

                                    btnGu.setText(adapter.getItem(which).replace("⊙ ",""));
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnGu, true);

                                    FProPrefer.with(MainActivity.this).setGu(adapter.getItem(which).replace("⊙ ",""));
                                    FProPrefer.with(MainActivity.this).setDong("");
                                    btnDong.setText("읍·면·동 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                                    btnVilArea.setText("근처지역 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                                    selectDong = "";

                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);

                                    /////////////////////////////////////////////////////////
                                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                                        poiType = CHOICE_POSITION;
                                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                                        poiType = SEARCH_POSITION;
                                    } else {
                                        poiType = ADDR_POSITION;
                                    }
                                    reDataView("btnGu.setOnClickListener");
                                    /////////////////////////////////////////////////////////

                                }
                            });

                            builder.setNeutralButton("전체", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    btnGu.setText("군·구 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnGu, false);
                                    btnDong.setText("읍·면·동 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                                    btnVilArea.setText("근처지역 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                                    selectGu = "";
                                    selectDong = "";
                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);
                                    /////////////////////////////////////////////////////////
                                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                                        poiType = CHOICE_POSITION;
                                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                                        poiType = SEARCH_POSITION;
                                    } else {
                                        poiType = ADDR_POSITION;
                                    }

                                }
                            });

                            builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, btnSi.getText().toString(), FProPrefer.with(MainActivity.this).getVilOldNew());

                //}
            }
        });

        /***************************************/
        /* 동 클릭 */
        /***************************************/
        btnDong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btnSi.getText().toString().equals("") || btnSi.getText().toString().equals("시·도 선택")) {
                    Util.showToast(MainActivity.this, "시·도 선택을 선택해주세요.");
                    return;
                } else {
                    if (btnGu.getText().toString().equals("") || btnGu.getText().toString().equals("군·구 선택")) {
                        Util.showToast(MainActivity.this, "군·구 선택을 선택해주세요.");
                        return;
                    }
                }
                if (isExpand == true) {

                    popUpClose();

                }
                //if (isExpand == false) {
                HashMap<String, String> dongMap = new HashMap<String, String>();

                if (MainActivity.this.getPackageName().equals("com.yit.villaman5")) {
                    dongMap.put("sido", "인천광역시");
                } else {
                    dongMap.put("sido", btnSi.getText().toString());
                }

                dongMap.put("gungu", selectGu);
                UServerGetDong.with(MainActivity.this).forStart(new UServerGetDong.WServerGetDong.Callback() {

                    @Override
                    public void onResult(final JSONArray json) {
                        try {
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);
                            // adapter.add("- "+ selectGu + " 전체 -");
                            for (int i = 0; i < json.length(); i++) {

                                  adapter.add("⊙ " + json.getJSONObject(i).get("dong").toString());
//                                if (json.getJSONObject(i).get("ncnt").equals(null)) {
//                                    adapter.add("⊙ " + json.getJSONObject(i).get("dong").toString());
//                                } else {
//                                    adapter.add("⊙ " + json.getJSONObject(i).get("dong").toString() + "(" + json.getJSONObject(i).get("ncnt").toString() + ")");
//                                }
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("● '읍.면.동'을 선택해 주세요.");
                            builder.setIcon(R.drawable.villaman);
                            // builder.setIcon(R.drawable.default_check);
                            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        selectDong = json.getJSONObject(which).get("dong").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

//                                    if (btnDong.getText().toString().indexOf("전체") > -1) {
//                                        Toast.makeText(MainActivity.this, "동 선택.", Toast.LENGTH_SHORT).show();
//                                        return;
//                                        // reDataView();
//                                    }

                                    btnDong.setText(adapter.getItem(which).replace("⊙ ",""));
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, true);

                                    FProPrefer.with(MainActivity.this).setDong(adapter.getItem(which).replace("⊙ ",""));

                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);

                                    btnVilArea.setText("근처지역 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                                    /////////////////////////////////////////////////////////
                                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                                        poiType = CHOICE_POSITION;
                                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                                        poiType = SEARCH_POSITION;
                                    } else {
                                        poiType = ADDR_POSITION;
                                    }
                                    reDataView("btnDong.setOnClickListener");
                                    /////////////////////////////////////////////////////////

                                }
                            });

                            builder.setNeutralButton("전체", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    btnDong.setText("읍·면·동 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                                    btnVilArea.setText("근처지역 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                                    selectDong = "";
                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);

                                    /////////////////////////////////////////////////////////
                                    // 데이타를 조회할때 주소인지 아니면 조건/선택 조회인지 검사한다.
                                    if (FProPrefer.with(context).getSearchAction().equals("Choice")) { //CHOICE_POSITION
                                        poiType = CHOICE_POSITION;
                                    } else if (FProPrefer.with(context).getSearchAction().equals("Search")) { //SEARCH_POSITION
                                        poiType = SEARCH_POSITION;
                                    } else {
                                        poiType = ADDR_POSITION;
                                    }

                                }
                            });

                            builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, dongMap);
                //}
            }
        });

        /***************************************/
        /* 현재 위치 클릭 */
        /***************************************/
        btnCurPosition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                // 화면 건수 버튼
                txtSubTotal.setVisibility(View.INVISIBLE);
                mapView.removeAllPolylines();

                // return;

                // mMapView = (MapView) findViewById(R.id.map_view);
                //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
                // mapView.setCurrentLocationEventListener(MapView.CurrentLocationEventListener);



//                // GPS 활성화 상태를 물어 본다.
//                if (!checkLocationServicesStatus()) {
//                    showDialogForLocationServiceSetting();
//                }else {
//
//                    checkRunTimePermission();
//                }

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "권한설정이 완료되지 않았습니다.\n", Toast.LENGTH_SHORT).show();
                    }
                };

                Permission.with(MainActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setRationaleTitle("현재 위치")
                        .setRationaleMessage("위치정보를 사용합니다.")
                        .setDeniedTitle("권한없음")
                        .setDeniedMessage("권한을 설정하지 않았습니다.\n\n[환경설정] > [권한]에서 권한설정을 하십시오.")
                        .setGotoSettingButtonText("환경설정")
                        .setPermissions(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_NETWORK_STATE
                        )
                        .check();

                double cur_longitude = 0;
                double cur_latitude = 0;

                gps = new GpsInfo(MainActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    cur_latitude = gps.getLatitude();
                    cur_longitude = gps.getLongitude();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

//                mapView.removeAllCircles();
//                // 200 m
//                MapCircle circle1 = new MapCircle( MapPoint.mapPointWithGeoCoord(cur_latitude, cur_longitude),100, Color.argb(50, 255, 0, 0),Color.argb(5, 255, 255, 0) );
//                circle1.setTag(100);
//                mapView.addCircle(circle1);
//
//                // 500 m
//                MapCircle circle2 = new MapCircle(MapPoint.mapPointWithGeoCoord(cur_latitude, cur_longitude),300,Color.argb(50, 0, 0, 255),Color.argb(5, 0, 255, 255));
//                circle2.setTag(300);
//                mapView.addCircle(circle2);
//
//                // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
//                MapPointBounds[] mapPointBoundsArray = {circle1.getBound(), circle2.getBound()};
//                MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
//                int padding = 50; // px
//                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
//                // 현제 위치를 중심으로 표시한다.
//                // mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
//                // mapView.setShowCurrentLocationMarker(true);

                latituce = cur_latitude;
                longitude = cur_longitude;

                // kkkdan
                ///////////////////////////////////////////////////////////////////////
                lat_ituce =  cur_latitude;
                long_itude = cur_longitude;

                poiType = AREA_POSITION;

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                viewMore.setImageResource(R.drawable.view_more_white);

                reDataView("btnVilArea.setOnClickListener");

                /////////////////////////////////////////////////////////////
                // 근처지역 거리
                if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                    dataVilArea.setText(FProPrefer.with(context).getEnv_distance()+ "m");
                } else {
                    FProPrefer.with(context).setEnv_distance("500");
                    dataVilArea.setText("500m");
                }
                /////////////////////////////////////////////////////////////

                // 근처지역 중심점 좌표로 이동한다.
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latituce, longitude), true);

                // 현재 위치 찾기
                Util.showToast(MainActivity.this, "현재 위치 기준 반경 "+dataVilArea.getText()+"로 매물 조회 합니다.", true);

                btnVilArea.setText("근처지역 선택");
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);


            }
        });


        /***************************************/
        /* 근처지역 클릭 */
        /***************************************/
        btnVilArea.setOnClickListener(new OnClickListener() { // =========================

            @Override
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }
                //if (isExpand == false) {
                HashMap<String, String> areaMap = new HashMap<String, String>();

                if (MainActivity.this.getPackageName().equals("com.yit.villaman5")) {
                    areaMap.put("sido", "인천광역시");
                } else {
                    areaMap.put("sido", btnSi.getText().toString());
                }

                areaMap.put("gungu", selectGu);
                // Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                UServerGetAreaLatLon.with(MainActivity.this).forStart(new UServerGetAreaLatLon.WServerGetAreaLatLon.Callback() {

                    @Override
                    public void onResult(final JSONArray json) {
                        try {

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);

                            final ArrayAdapter<String> adapter_lat = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);
                            final ArrayAdapter<String> adapter_lon = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);

                            for (int i = 0; i < json.length(); i++) {

                                adapter.add("⊙ " + json.getJSONObject(i).get("cd_name").toString());

                                adapter_lat.add(json.getJSONObject(i).get("ma_lat").toString());
                                adapter_lon.add(json.getJSONObject(i).get("ma_lon").toString());

                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            // ContextThemeWrapper cw = new ContextThemeWrapper( MainActivity.this, R.style.AlertDialogTheme );
                            // AlertDialog.Builder builder = new AlertDialog.Builder(cw);

                            builder.setTitle("● '근처지역'을 선택해 주세요.");
                            builder.setIcon(R.drawable.villaman);

                            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    btnDong.setText("읍·면·동 선택");
                                    Util.setBtnTxtBgColor_VilAllOldNew(btnDong, false);
                                    selectDong = "";

                                    Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, true);
                                    btnVilArea.setText(adapter.getItem(which).replace("⊙ ","")); //  + " " + "근처");

                                    lat_ituce = Double.parseDouble(adapter_lat.getItem(which));
                                    long_itude = Double.parseDouble(adapter_lon.getItem(which));

                                    poiType = AREA_POSITION;

                                    mapView.setVisibility(View.VISIBLE);
                                    mLloBottomBar.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    viewMore.setImageResource(R.drawable.view_more_white);

                                    reDataView("btnVilArea.setOnClickListener");

                                    /////////////////////////////////////////////////////////////
                                    // 근처지역 거리
                                    if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
                                        dataVilArea.setText(FProPrefer.with(context).getEnv_distance()+ "m");
                                    } else {
                                        FProPrefer.with(context).setEnv_distance("500");
                                        dataVilArea.setText("500m");
                                    }
                                    /////////////////////////////////////////////////////////////

                                    // 근처지역 중심점 좌표로 이동한다.
                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latituce, longitude), true);

                                }
                            });

                            builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, areaMap);
                //}
            }
        });

        // 근처거리 클릭
        dataVilArea.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isExpand == true) {
                    popUpClose();
                }

                try {
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_dialog_singlechoice);

                    adapter.add("⊙ " + "500m");
                    adapter.add("⊙ " + "800m");
                    adapter.add("⊙ " + "1,000m");
                    adapter.add("⊙ " + "1,500m");
                    adapter.add("⊙ " + "2,000m");

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("● 근처지역 `거리`를 선택해 주세요.");
                    builder.setIcon(R.drawable.villaman);
                    // builder.setIcon(R.drawable.default_check);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FProPrefer.with(context).setEnv_distance(adapter.getItem(which).replace("⊙ ","").replace("m",""));
                            dataVilArea.setText(adapter.getItem(which).replace("⊙ ",""));

                            // 근처 선택이었으면 바로 조회를 한다.
                            if (btnVilArea.getTextColors().getDefaultColor() == Color.WHITE) {
                                poiType = AREA_POSITION;
                                mapView.setVisibility(View.VISIBLE);
                                mLloBottomBar.setVisibility(View.VISIBLE);
                                mWebView.setVisibility(View.GONE);
                                viewMore.setImageResource(R.drawable.view_more_white);
                                reDataView("btnVilArea.setOnClickListener");

                                // 근처지역 중심점 좌표로 이동한다.
                                // mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latituce, longitude), true);
                            }

                            Util.showToast(MainActivity.this, "근처 거리 " + adapter.getItem(which).replace("⊙ ","") + "로 설정");

                        }

                    });

                    builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

        /***************************************/
        /* 검색 버튼 클릭 */
        /***************************************/
        // DODO 검색버튼 클릭
/*
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //if (isExpand == false) {

                if (isExpand == true) {
                    popUpClose();
                }
                if (btnSi.getText().equals("") || btnSi.getText().equals("시·도 선택")) {
                    util.showToast(MainActivity.this, "시도는 필수 선택사항입니다.");
                    return;
                }

                HashMap<String, String> addressMap = new HashMap<String, String>();

                if (MainActivity.this.getPackageName().equals("com.yit.villaman5")) {
                    addressMap.put("sido", "인천광역시");
                } else {
                    addressMap.put("sido", btnSi.getText().toString());
                }

                addressMap.put("gungu", selectGu);
                addressMap.put("dong", selectDong);

                OptionRangeSelectDialog optionRangeSelectDialog = new OptionRangeSelectDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar, addressMap);
                optionRangeSelectDialog.setContentView(R.layout.range_select_dialog);
                optionRangeSelectDialog.show();
                optionRangeSelectDialog.forStart(new OptionRangeSelectDialog.Callback() {

                    @Override
                    public void onResponse(HashMap<String, String> detailMap) {
                        // appStart = false;
                        poiType = SEARCH_POSITION;
                        detailStrMap = detailMap;
                        mapView.setVisibility(View.VISIBLE);
                        mLloBottomBar.setVisibility(View.VISIBLE);
                        mWebView.setVisibility(View.GONE);
                        viewMore.setImageResource(R.drawable.view_more_white);
                        reDataView();

                    }
                });

                mWebView.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                viewMore.setImageResource(R.drawable.view_more_white);

                //}
            }
        });
*/

        /***************************************/
        /* 더보기 버튼 클릭 */
        /***************************************/
        viewMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isExpand == true) {
                    popUpClose();
                }

                if (isViewMore == false) {
                    mapView.setVisibility(View.GONE);
                    mLloBottomBar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                    viewMore.setImageResource(R.drawable.view_more_red);
                    isViewMore = true;
                } else {
                    mapView.setVisibility(View.VISIBLE);
                    mLloBottomBar.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    viewMore.setImageResource(R.drawable.view_more_white);
                    isViewMore = false;
                }
            }
        });

        /***************************************/
        /* 더보기(리스트) 버튼 클릭 */
        /***************************************/
        viewMoreSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//
//        Log.d(__TAG_MY__,"MY:..................");
//
//                //PopupMenu객체 생성.
//                //생성자함수의 첫번재 파라미터 : Context
//                //생성자함수의 두번째 파라미터 : Popup Menu를 붙일 anchor 뷰
//                PopupMenu popup= new PopupMenu(MainActivity.this, v);//v는 클릭된 뷰를 의미
//
//                //Popup Menu에 들어갈 MenuItem 추가.
//                //이전 포스트의 컨텍스트 메뉴(Context menu)처럼 xml 메뉴 리소스 사용
//                //첫번재 파라미터 : res폴더>>menu폴더>>mainmenu.xml파일 리소스
//                //두번재 파라미터 : Menu 객체->Popup Menu 객체로 부터 Menu 객체 얻어오기
//                getMenuInflater().inflate(R.menu.menu_viewsub, popup.getMenu());
//
//
//                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 객체 생성
//                //import android.widget.PopupMenu.OnMenuItemClickListener 가 되어있어야 합니다.
//                //OnMenuItemClickListener 클래스는 다른 패키지에도 많기 때문에 PopupMenu에 반응하는 패키지를 임포트하셔야 합니다.
//                PopupMenu.OnMenuItemClickListener listener= new PopupMenu.OnMenuItemClickListener() {
//
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        // TODO Auto-generated method stub
//
//                        switch( item.getItemId() ){//눌러진 MenuItem의 Item Id를 얻어와 식별
//                            case R.id.mnusub_letter01:
//                                Toast.makeText(MainActivity.this, "개별 문자 발송 기능 준비중 입니다.", Toast.LENGTH_SHORT).show();
//                                // 단건 발송
////                                try {
////                                    sendSingle();
////                                } catch (UnsupportedEncodingException e) {
////                                    e.printStackTrace();
////                                }
//                                break;
//
//                            case R.id.mnusub_letter02:
//                                Toast.makeText(MainActivity.this, "대량 문자 발송 기능 준비중 입니다.", Toast.LENGTH_SHORT).show();
////                                // 단건 발송
////                                try {
////                                    showReceiveList();
////                                } catch (UnsupportedEncodingException e) {
////                                    e.printStackTrace();
////                                }
//                                break;
//
//                            case R.id.mnusub_email:
//                                Toast.makeText(MainActivity.this, "E-mail 발송 기능 준비중 입니다.", Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.mnusub_excel:
//                                Toast.makeText(MainActivity.this, "Excel 저장 기능 준비중 입니다.", Toast.LENGTH_SHORT).show();
//
//                               // saveExcel();
//
//
//                                break;
//
//                            case R.id.mnusub_exit:
//                                break;
//
//                        }
//
//                        return false;
//                    }
//                };
//
//
//                // Use reflection to invoke setForceShowIcon
//                try {
//                    Field[] fields = popup.getClass().getDeclaredFields();
//                    for (Field field : fields) {
//                        if ("mPopup".equals(field.getName())) {
//                            field.setAccessible(true);
//                            Object menuPopupHelper = field.get(popup);
//                            Class<?> classPopupHelper = Class
//                                    .forName(menuPopupHelper.getClass().getName());
//                            Method setForceIcons = classPopupHelper
//                                    .getMethod("setForceShowIcon", boolean.class);
//                            setForceIcons.invoke(menuPopupHelper, true);
//                            break;
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//
//                // this.OnClickListener
//                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
//                popup.setOnMenuItemClickListener(listener);
//
//                popup.show();//Popup Menu 보이기
//
            }
        });




        /***************************************/
        /* 문자발송 버튼 클릭 */
        // DODO 문자발송 클릭
        /***************************************/
        /*
        btnLetterSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String strMess = "";

                // 단건 발송
                try {
                    sendSingle();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        */
                /*
                if (strLetterSsendYn.equals("Y")) {

                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this).setCancelable(false);
                    dialog1.setTitle("문자 발송 서비스");
                    strMess += "① 하루 발송 최대:500건, \n② 한달 1일 200건이상 11회 부터는 \n　문자 발송 요금이 부과됩니다.";

                    dialog1.setMessage(strMess);
                    dialog1.setPositiveButton("다음 ▶", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // 단건 발송
                            sendSingle();

                            // 수신자 리스트 작성
                            // showReceiveList();

                        }
                    });
                    dialog1.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "메시지 발송이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog1.show();

                } else {
                    AlertDialog.Builder dialog11 = new AlertDialog.Builder(MainActivity.this).setCancelable(false);
                    dialog11.setTitle("알림 !");
                    strMess += "유료 회원에게만 제공되는 서비스 입니다.";

                    dialog11.setMessage(strMess);
                    dialog11.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog11.show();
                }
                */
        /*
            }
        });
        */

        /***************************************/
        /* 재조회 버튼 클릭 */
        // DODO 재조회 클릭
        /***************************************/
        btnRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "매물 재조회 중...", Toast.LENGTH_SHORT).show();
                reDataView("btnRefresh.setOnClickListener");
            }
        });

        /***************************************/
        /* 닫기 버튼 클릭 */
        /***************************************/
        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //지도(marker) 상세 = 0 , 목록(totalBtn) 리스트 =  1, 목록(totalBtn)상세 : 2

                if(fromMode == 0 ) {
                    popUpClose();
                } else if(fromMode == 1) {
                    // util.showToast(MainActivity.this, "빌라 목록을 닫습니다.");
                    popUpClose();
                } else if(fromMode == 2) { //다시 목록으로 가야 함.

//                    if (fromModeSub == 9) {
//                        showVillaSubList(); // 다시 선택 목록으로 이동한다.
//                    } else {
//                        showVillaList(); // 다시 목록으로 이동한다.
//                    }

                    popUpClose();

                }

            }
        });

        /***************************************/
        /* 뒤로(리스트) 버튼 클릭 */
        /***************************************/
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //지도(marker) 상세 = 0 , 목록(totalBtn) 리스트 =  1, 목록(totalBtn)상세 : 2
                if(fromMode == 0 ) {
                    popUpClose();
                } else if(fromMode == 1) {
                    Util.showToast(MainActivity.this, "빌라 목록을 닫습니다.");
                    popUpClose();
                } else if(fromMode == 2) { //다시 목록으로 가야 함.
                    if (fromModeSub == 9) {
                        showVillaSubList(); // 다시 선택 목록으로 이동한다.
                    } else {
                        showVillaList(); // 다시 목록으로 이동한다.
                    }
                }


                btnBack.setVisibility(View.GONE);
                btnClose.setVisibility(View.VISIBLE);


            }
        });

        /***************************************/
        /* 전체 건수 클릭 */
        /***************************************/
        txtTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showVillaList();
            }
        });

        /***************************************/
        /* 화면 건수 클릭 */
        /***************************************/
        txtSubTotal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.showToast(MainActivity.this, "지도에 표시된 리스트입니다.");
                showVillaSubList();
            }
        });

        /***************************************/
        /* 초기화 버튼 클릭 */
        /***************************************/
        txtAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // if (isExpand == false) {

                if (isExpand == true) {
                    popUpClose();
                }
                if (detailStrMap == null) {
                    detailStrMap = new HashMap<String, String>();
                }

                detailStrMap.put("ma_bld_nm", "");

                detailStrMap.put("ma_bo_ney1", "");
                detailStrMap.put("ma_bo_ney2", "");
                FProPrefer.with(MainActivity.this).setMinInputBo(0);
                FProPrefer.with(MainActivity.this).setMaxInputBo(0);
                detailStrMap.put("ma_month_ney1", "");
                detailStrMap.put("ma_month_ney2", "");
                FProPrefer.with(MainActivity.this).setMinInputMonth(0);
                FProPrefer.with(MainActivity.this).setMaxInputMonth(0);

                detailStrMap.put("ma_jeon_area1", "");
                detailStrMap.put("ma_jeon_area2", "");
                FProPrefer.with(MainActivity.this).setMinArea(0);
                FProPrefer.with(MainActivity.this).setMaxArea(0);
                detailStrMap.put("ma_level1", "");
                detailStrMap.put("ma_level2", "");
                FProPrefer.with(MainActivity.this).setMinFloor(0);
                FProPrefer.with(MainActivity.this).setMaxFloor(0);
                detailStrMap.put("ma_room1", "");
                detailStrMap.put("ma_room2", "");
                FProPrefer.with(MainActivity.this).setMinRoom(0);
                FProPrefer.with(MainActivity.this).setMaxRoom(0);
                detailStrMap.put("ma_jun_year1", "");
                detailStrMap.put("ma_jun_year2", "");
                FProPrefer.with(MainActivity.this).setMinYear(0);
                FProPrefer.with(MainActivity.this).setMaxYear(0);

                detailStrMap.put("ma_search", "");
                FProPrefer.with(MainActivity.this).setChoiceMaSearch("");
                FProPrefer.with(MainActivity.this).setSearchAction("");

                FProPrefer.with(MainActivity.this).setSearchText("");

                // 검색조건 초기화
                Util.showToast(MainActivity.this, "검색 조건을 초기화 하고 있습니다.");

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);

                viewMore.setImageResource(R.drawable.view_more_white);

                btnVilArea.setText("근처지역 선택");
                Util.setBtnTxtBgColor_VilAllOldNew(btnVilArea, false);

                poiType = ADDR_POSITION;
                reDataView("txtAll.setOnClickListener");

                util.setTxtExpBgcolor(false,txtAll,"초기화",txtExp, "", "초기화 버튼을 클릭하였습니다.");

            }

        });


        /***************************************/
        /* 조건 선택 */
        /***************************************/
        txtExp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // if (isExpand == false) {

                // util.showToast(MainActivity.this, "검색 조건을 선택 합니다.");

                //if (isExpand == false) {

                if (isExpand == true) {
                    popUpClose();
                }


                HashMap<String, String> addressMap = new HashMap<String, String>();
                if (MainActivity.this.getPackageName().equals("com.yit.villaman5")) {
                    addressMap.put("sido", "인천광역시");
                } else {
                    addressMap.put("sido", btnSi.getText().toString());
                }

                if (btnSi.getText().equals("") || btnSi.getText().equals("시·도 선택")) {
                    addressMap.put("sido", "인천광역시");
                    // return true;
                }
                addressMap.put("gungu", selectGu);
                addressMap.put("dong", selectDong);

                OptionRangeSelectDialog optionRangeSelectDialog = new OptionRangeSelectDialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar, addressMap);
                optionRangeSelectDialog.setContentView(R.layout.range_select_dialog);
                optionRangeSelectDialog.show();
                optionRangeSelectDialog.forStart(new OptionRangeSelectDialog.Callback() {

                    @Override
                    public void onResponse(HashMap<String, String> detailMap) {
                        // appStart = false;
                        poiType = SEARCH_POSITION;
                        detailStrMap = detailMap;
                        mapView.setVisibility(View.VISIBLE);
                        mLloBottomBar.setVisibility(View.VISIBLE);
                        mWebView.setVisibility(View.GONE);
                        viewMore.setImageResource(R.drawable.view_more_white);
                        reDataView("txtExp.setOnClickListener");
                    }
                });
                // return true;

            }

        });


        /***************************************/
        /* Nav Close 클릭 */
        /***************************************/
        btnNavClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder gsDialog = new AlertDialog.Builder(MainActivity.this);
                // gsDialog.setTitle("빌라만");
                gsDialog.setMessage("로그아웃(종료) 하시겠습니까?");
                // gsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255,255,255)));

                gsDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Util.showToast(MainActivity.this, "감사합니다..");
                        FProPrefer.with(context).setPassWd(""); // 인증번호 초기화 한다.

                        finish();
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        // return;
                    }
                }).show();

                /*
                AlertDialog alert = gsDialog.create();
                // 대화창 제목 설정
                alert.setTitle("빌라만 (부동산)");
                // 대화창 아이콘 설정
                // alert.setIcon(R.drawable.villaman);
                // 대화창 배경 색  설정
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,255,255,255)));

                alert.show();
                */

            }

        });


        /***************************************/
        /* 포인트 재조회 클릭 */
        /***************************************/
        btnPay_Refresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Util.showToast(MainActivity.this, "사용 가능한 포인트를 재조회 합니다.");

                NavigationView navigationView = findViewById(R.id.nav_view);
                final View headerLayout = navigationView.getHeaderView(0);

                final TextView tvName = headerLayout.findViewById(R.id.txtPhoneName);
                final TextView tvPayment = headerLayout.findViewById(R.id.txtPay_Amount);
                final TextView tvPayBank = headerLayout.findViewById(R.id.txtPay_Bank);

                ////////////////////////////
                // 결제 금액 필드에 보여준다.
                final TextView data_main_board = findViewById(R.id.data_main_board);
                final TextView txt_main_board = findViewById(R.id.txt_main_board);
                final TextView data_main_standby = findViewById(R.id.data_main_standby);

                Util.setPayment(MainActivity.this, tvName, tvPayment, tvPayBank, data_main_board, txt_main_board, data_main_standby,ll_info_bar, psPhoneNumber);

            }

        });

        /***************************************/
        /* 충전하기 TextView 클릭 */
        /***************************************/
        txtPay_Bank.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Util.showToast(MainActivity.this, "충전하기로 이동합니다.");

                String strURL = null;
                String strURL_SUB = null;

                try {
                    strURL = Util.getURL_IT() + "/ahh/viewPayBank.do?YESIT="; // TODO 충전하기
                    strURL_SUB = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8") + "&";
                    strURL_SUB = strURL_SUB + URLEncoder.encode("usr_name", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8") ;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                System.out.println(strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));

                Log.d("/ahh/viewPayBank.do",strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));
                // 외부로 웹뷰 띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + Util.getBase64encode(strURL_SUB).replace("\n","")));
                startActivity(intent);

/*
                // 내부로 웹뷰 띄우기
                mapView.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);

                mWebView.setPadding(0, 0, 0, 0);
                mWebView.setInitialScale(100);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.setWebViewClient(new WebViewClient());

                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.loadUrl(strURL + Util.getBase64encode(strURL_SUB).replace("\n", ""));

                isViewMore = false;

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                Util.collapse(llCoverContent);
                // popUpClose();
  */
            }

        });


        /***************************************/
        /* 폰번호를 클릭하면 관리자 화면으로 이동한다.
        /***************************************/
        txtPhoneNum.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 관리자가 아니면 기능을 수행하지 않는다.
                if (psPhoneNumber.equals("01037580862")) {

                    // 매물등록
                    String itemURL = Util.getURL_VIL() + "/w/env_index.php?"; // TODO 관리자

                    String data2 = null;
                    try {
//                        data2 = "packagename=" + getPackageName() + "&";
//                        data2 = data2 + "userid=" + psPhoneNumber;
                        // data2 = URLEncoder.encode("packagename", "UTF-8") + "=" + URLEncoder.encode("" + getPackageName(), "UTF-8") + "&";
                        // data2 = data2 + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode("" + psPhoneNumber, "UTF-8");
                        data2 = "packagename" + "=" + Util.getBase64encode("" + getPackageName()) + "&";
                        data2 = data2 + "userid"+ "=" + Util.getBase64encode("" + psPhoneNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 외부로 웹뷰 띄우기
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + data2));
                    // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemURL + Util.getBase64encode(data2).replace("\n", "")));
                    startActivity(intent);

                }
/*
                // 내부로 웹뷰 띄우기
                mapView.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);

                mWebView.setPadding(0, 0, 0, 0);
                mWebView.setInitialScale(100);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.setWebViewClient(new WebViewClient());

                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.loadUrl(itemURL + Util.getBase64encode(data2).replace("\n", ""));

                isViewMore = false;

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                Util.collapse(llCoverContent);
                // popUpClose();
*/


            }

        });



        /***************************************/
        /* 광고 삭제 버튼 클릭 */
        /***************************************/
        btnWebView_Del.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Util.showToast(MainActivity.this, "[광고] 삭제는 유료회원만 가능합니다.");

                String strURL = null;
                String strURL_SUB = null;

                try {
                    strURL = Util.getURL_IT() + "/ahh/viewPayBank.do?YESIT="; // TODO 충전하기
                    strURL_SUB = URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8") + "&";
                    strURL_SUB = strURL_SUB + URLEncoder.encode("usr_name", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8") ;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                System.out.println(strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));

                Log.d("/ahh/viewPayBank.do",strURL + Util.getBase64encode(strURL_SUB).replace("\n",""));
                // 외부로 웹뷰 띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL + Util.getBase64encode(strURL_SUB).replace("\n","")));
                startActivity(intent);

            }

        });


    }
    ////////////////////////////////////////////////////////////////////////

    /* 조회한 모든 리스트 */
    public void showVillaList() {
        fromMode = 1; //목록 모드
        fromModeSub = 0;
        isMarkerOneClick = false; // 지도에서 조회를 했는지 여부 판다

        btnBack.setVisibility(View.GONE);
        btnClose.setVisibility(View.VISIBLE);


        if (rGetRoomList == null || rGetRoomList.size() == 0) {
            Toast.makeText(MainActivity.this, "이 지역에 매물이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {

            SpannableString content = new SpannableString("총:" + rGetRoomList.size() + "개");
            txtTotal.setText(content.toString());
            tvAddress.setText("빌라 목록(" + rGetRoomList.size() + "개)");


            if (isExpand == false) {
                isExpand = true;
                isAllList = true;
                isMarkerOneClick = false;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

                mapView.setLayoutParams(params);
                Util.expand(llCoverContent);

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                viewMore.setImageResource(R.drawable.view_more_white);

                // util.showToast(MainActivity.this, "지도에 표시된 빌라 목록입니다.");


                if (!isAllVisible) {
                    isAllVisible = true;
                }

                detailAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomList, pressPoi, isMarkerOneClick, false);
                listRoomList.setAdapter(detailAdapter);

            } else {
                isAllList = true;
                // popUpClose();


                if (!isAllVisible) {
                    isAllVisible = true;

                    detailAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomList, pressPoi, isMarkerOneClick,false);
                    listRoomList.setAdapter(detailAdapter);
                } else {
                    // 리스트가 보이는 상태에서 클릭하면 리스트를 닫는다.
                    popUpClose();
                }
            }
            //
            listRoomList.setSelection(lastListViewPosition);
            // 빌라 리스트의 크기를 원 상태로 보여준다.
            lilaRoomList = findViewById(R.id.lilaRoomList);
            // lilaRoomList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            lastListViewPosition = 0;
        }
    }


    /* 조회한 모든 리스트 */
    public void showVillaSubList() {
        fromMode = 1; //목록 모드
        fromModeSub = 9;
        isMarkerOneClick = false; // 지도에서 조회를 했는지 여부 판다

        btnBack.setVisibility(View.GONE);
        btnClose.setVisibility(View.VISIBLE);

        if (rGetRoomSubList == null || rGetRoomSubList.size() == 0) {
            Toast.makeText(MainActivity.this, "이 지역에 매물이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {

            // SpannableString content = new SpannableString("총:" + rGetRoomSubList.size() + "개");
            // txtTotal.setText(content.toString());
            tvAddress.setText("선택 목록(" + rGetRoomSubList.size() + "개)");


            if (isExpand == false) {
                isExpand = true;
                isAllList = true;
                isMarkerOneClick = false;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

                mapView.setLayoutParams(params);
                Util.expand(llCoverContent);

                mapView.setVisibility(View.VISIBLE);
                mLloBottomBar.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                viewMore.setImageResource(R.drawable.view_more_white);

                // util.showToast(MainActivity.this, "지도에 표시된 빌라 목록입니다.");

                if (!isAllVisible) {
                    isAllVisible = true;
                }

                detailSubAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomSubList, pressPoi,isMarkerOneClick, false);
                listRoomSubList.setAdapter(detailSubAdapter);

            } else {
                isAllList = true;
                // popUpClose();
                if (!isAllVisible) {
                    isAllVisible = true;


                    detailSubAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomSubList, pressPoi,isMarkerOneClick, false);
                    listRoomSubList.setAdapter(detailSubAdapter);
                } else {

                    // 리스트가 보이는 상태에서 클릭하면 리스트를 닫는다.
                    popUpClose();
                }
            }
            //
            listRoomSubList.setSelection(lastListViewPosition);
            // 빌라 리스트의 크기를 원 상태로 보여준다.
            // lilaRoomList = (LinearLayout) findViewById(R.id.lilaRoomList);
            // lilaRoomList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            lastListViewPosition = 0;
        }
    }

    public void btnLetterContent(View v) { // 문자 내용 작성 TODO
        //여기에다 할 일을 적어주세요.
        Toast.makeText(MainActivity.this, "문자 내용을 작성합니다.", Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_sms, null,true);

        final EditText editTextPhoneNo = alertLayout.findViewById(R.id.editTextPhoneNo);
        final EditText editTextSMS = alertLayout.findViewById(R.id.editTextSMS);
        final TextView textViewSMS = alertLayout.findViewById(R.id.textViewSMS);

        String strMess = "";

        String sData = "";

        Log.d("▣letter:", "▣editTextPhoneNo:" + editTextPhoneNo.getText());

        try {
            if (editTextPhoneNo.getText().toString().length() != 11) {
                Toast.makeText(MainActivity.this, ":"+editTextPhoneNo.getText().toString() + ":헨드폰 번호를 확인 바랍니다.", Toast.LENGTH_SHORT).show();
            } else {

                sData = sData + URLEncoder.encode("usr_id", "UTF-8") + "=" + editTextPhoneNo.getText().toString() + "&";
                sData = sData + URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8");
                String URL = Util.getURL_IT() + "/ahh/viewMainRegist_G.do?YESIT=" + Util.getBase64encode(sData).replace("\n", ""); // TODO

                Log.d("▣letter:", "▣sData:" + sData);
                Log.d("▣letter:", "▣URL:" + URL);

                strMess += "빌라매매 전문 사이트\n";
                strMess += "`빌라만`입니다.\n";
                strMess += "\r\n";
                strMess += "아래의 주소에서\n";
                strMess += "10,000원을 `충전하기` 하시고\n";
                strMess += "매물을 등록하실 수 있습니다.\n";
                strMess += "\r\n";
                strMess += URL;  // 본문 내용
                strMess += "\r\n";
                strMess += "\r\n";
                strMess += "등록 후 문자로 회신 바랍니다.\n";
                strMess += "\r\n";
                strMess += "감사합니다.\n";

                Log.d("▣letter:", "▣letter:" + "014");
                // 메시지 내용
                editTextSMS.setText(strMess);

                Log.d("▣letter:", "▣letter:" + "015");

                textViewSMS.setText("크기 : " + strMess.getBytes("KSC5601").length + " bytes");

                if (strMess.getBytes("KSC5601").length > 4000) {
                    Toast.makeText(MainActivity.this, "내용이 4,000 Bytes 이상은 발송할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
        Log.e("getTag", "getTag" + arg1.getTag());
        if (isExpand == false) {
            isAllList = false;
            isExpand = true;
            pressPoi = arg1.getTag();
            setMarkerTouchListView();
        }
    }

    // 지도의 마커를 클릭한다.
    private void setMarkerTouchListView() {
        fromMode = 0;
        lastListViewPosition = 0;
        isMarkerOneClick = true;

        listTextDetailClick(pressPoi);

        btnBack.setVisibility(View.GONE);
        btnClose.setVisibility(View.VISIBLE);

    }

    public void setFromMode(int fromMode) {
        this.fromMode = fromMode;
    }

    public int getFromMode() {
        return fromMode;
    }
    public int getFromModeSub() {
        return fromModeSub;
    }

    public void listTextDetailClick(int position) {
        try {
            double lati = 0;
            double longi = 0;
Log.d(__TAG__, "listTextDetailClick:");
            pressPoi = position;

            if (rGetRoomList.get(pressPoi).getMa_lat().equals(null)) {
                lati = 0;
            } else {
                lati = Double.parseDouble(rGetRoomList.get(pressPoi).getMa_lat());
            }

            if (rGetRoomList.get(pressPoi).getMa_lon().equals(null)) {
                longi = 0;
            } else {
                longi = Double.parseDouble(rGetRoomList.get(pressPoi).getMa_lon());
            }

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

            mapView.setLayoutParams(params);
            // 지도 중심으로 이동한다.
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lati, longi), true);
            Log.d(__TAG__, "앱뷰 중심 이동:");
            mapView.setZoomLevel(1,true);

            Util.expand(llCoverContent);

            ArrayList<RGetRoomEnty> tempRoomList = new ArrayList<RGetRoomEnty>();
            tempRoomList.add(rGetRoomList.get(pressPoi));

            // tvAddress.setText(Util.strVilOldNew + " 빌라 선택");
            tvAddress.setText("빌라("+rGetRoomList.get(pressPoi).getID()+") 정보");

            isAllVisible = false;

            detailAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, tempRoomList, pressPoi, isMarkerOneClick,true);
            listRoomList.setAdapter(detailAdapter);


        } finally {

        }
    }


    public void listTextDetailSubClick(int position) {
        try {
            double lati = 0;
            double longi = 0;

            pressPoi = position;

            if (rGetRoomSubList.get(pressPoi).getMa_lat().equals(null)) {
                lati = 0;
            } else {
                lati = Double.parseDouble(rGetRoomSubList.get(pressPoi).getMa_lat());
            }

            if (rGetRoomSubList.get(pressPoi).getMa_lon().equals(null)) {
                longi = 0;
            } else {
                longi = Double.parseDouble(rGetRoomSubList.get(pressPoi).getMa_lon());
            }

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

            mapView.setLayoutParams(params);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lati, longi), true);
            mapView.setZoomLevel(1,true);

            Util.expand(llCoverContent);

            ArrayList<RGetRoomEnty> tempRoomList = new ArrayList<RGetRoomEnty>();
            tempRoomList.add(rGetRoomSubList.get(pressPoi));

            tvAddress.setText("빌라("+rGetRoomSubList.get(pressPoi).getID()+") 정보");

            isAllVisible = false;

            detailSubAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, tempRoomList, pressPoi, isMarkerOneClick,true);
            listRoomSubList.setAdapter(detailSubAdapter);

        } finally {

        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1, CalloutBalloonButtonType arg2) {
    }

    @Override
    public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1, MapPoint arg2) {
    }

    @Override
    public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView arg0, float arg1) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        // MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        // Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }
    /*
        @Override
        public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
            mapReverseGeoCoder.toString();
            onFinishReverseGeoCoding(s);
        }


    */
    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
/*
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }
*/

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        // if (!appStart) {
        prevMapPointGeo = mapPointGeo;
        mapPointGeo = currentLocation.getMapPointGeoCoord();

        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.e("♣♣♣:MainActivity", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
        // Log.e("♣♣♣:mapPointGeo", "mapPointGeo" + mapPointGeo.latitude + "----" + mapPointGeo.longitude);
        if (mapPointGeo.latitude > 0 && mapPointGeo.longitude > 0) {
            latituce = mapPointGeo.latitude;
            longitude = mapPointGeo.longitude;
            reDataView("onCurrentLocationUpdate");
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        txtSubTotal.setVisibility(View.INVISIBLE);
        txtSubTotal.setText("0");

        int zoomlevel = mapView.getZoomLevel();     // 지도 줌레벨에 따라 디스플레이 크기 1당 위도, 경도 값이 달라지니까 구함.
        int intRadius = 0;
        double dblDistance = 0;

        mapView.removeAllCircles();
        if (        zoomlevel == -2) {
            intRadius = 50;       dblDistance = 0.05;
        } else if (zoomlevel == -1) {
            intRadius = 100;  dblDistance = 0.1;
        } else if (zoomlevel == 0) {//
            intRadius = 150;  dblDistance = 0.15;
        } else if (zoomlevel == 1) { // 정상
            intRadius = 200;  dblDistance = 0.2;
        } else if (zoomlevel == 2) { // 정상
            intRadius = 500;  dblDistance = 0.5;
        } else if (zoomlevel == 3) {
            intRadius = 1200; dblDistance = 1.2;
        } else if (zoomlevel == 4) { //
            intRadius = 1500; dblDistance = 1.5;
        } else {
            return;
        }

        // util.showToast(MainActivity.this, "드레그.........:"+zoomlevel+ ":"+intRadius+ ":"+dblDistance);

        MapPoint mp = mapView.getMapCenterPoint();  // 다음지도 맵뷰의 가운데 값을 구함
        MapPoint.GeoCoordinate gc = mp.getMapPointGeoCoord();  // 맵포인드를 WCONG 평면좌표계의 좌표값으로 변환

        if (zoomlevel >= 1 && zoomlevel <= 4) {
            MapCircle circle2 = new MapCircle(MapPoint.mapPointWithGeoCoord(gc.latitude, gc.longitude),
                    intRadius,
                    Color.argb(128, 200, 0, 0),
                    Color.argb(100, 255, 255, 200));

            circle2.setTag(300);
            mapView.addCircle(circle2);

            // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
            MapPointBounds[] mapPointBoundsArray = {circle2.getBound()};
            MapPointBounds mapPointBounds2 = new MapPointBounds(mapPointBoundsArray);
            int padding2 = 0; // px
            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds2, padding2));
        }

        /* 현재 리스트 건수 */
        getDetailSubList(gc.latitude, gc.longitude, dblDistance);

    }


    private void getDetailList(JSONArray json, String sMess) {

        mapView.removeAllPOIItems();

        rGetRoomList = new ArrayList<RGetRoomEnty>();
        try {
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    RGetRoomEnty rGetRoomEnty = new RGetRoomEnty();
                    rGetRoomEnty.setID(json.getJSONObject(i).getInt("ID"));
                    rGetRoomEnty.setMa_Use_Yn(json.getJSONObject(i).getString("ma_use_yn"));
                    rGetRoomEnty.setMa_Use_Yn2(json.getJSONObject(i).getString("ma_use_yn2"));
                    rGetRoomEnty.setMa_Good_Yn(json.getJSONObject(i).getString("ma_good_yn"));
                    rGetRoomEnty.setGubun(json.getJSONObject(i).getString("ma_gubun"));
                    rGetRoomEnty.setFcnt(json.getJSONObject(i).getString("fcnt"));
                    rGetRoomEnty.setImgCnt(json.getJSONObject(i).getString("imgcnt"));
                    rGetRoomEnty.setReplyCnt(json.getJSONObject(i).getString("replycnt"));
                    rGetRoomEnty.setDeleteCnt(json.getJSONObject(i).getString("deletecnt"));
                    rGetRoomEnty.setImgAppendYn(json.getJSONObject(i).getString("img_append_yn"));
                    rGetRoomEnty.setImgDeleteYn(json.getJSONObject(i).getString("img_delete_yn"));
                    rGetRoomEnty.setFavYn(json.getJSONObject(i).getString("favyn"));
                    rGetRoomEnty.setDtTermCnt(json.getJSONObject(i).getString("dt_term_cnt"));
                    rGetRoomEnty.setDtTermCnt3(json.getJSONObject(i).getString("dt_term_cnt3"));
                    rGetRoomEnty.setMa_agent_yn(json.getJSONObject(i).getString("ma_agent_yn"));
                    rGetRoomEnty.setMa_pass_yn(json.getJSONObject(i).getString("ma_pass_yn"));
                    rGetRoomEnty.setModfy_dt(json.getJSONObject(i).getString("modfy_dt"));
                    rGetRoomEnty.setMa_jeon_area_m2(json.getJSONObject(i).getString("ma_jeon_area_m2"));
                    rGetRoomEnty.setMa_jeon_area(json.getJSONObject(i).getString("ma_jeon_area"));
                    rGetRoomEnty.setMa_bld_nm(json.getJSONObject(i).getString("ma_bld_nm"));
                    rGetRoomEnty.setMa_addr3(json.getJSONObject(i).getString("ma_addr3"));
                    rGetRoomEnty.setMa_yun_ney(json.getJSONObject(i).getString("ma_yun_ney"));
                    rGetRoomEnty.setModfy_id(json.getJSONObject(i).getString("modfy_id"));
                    rGetRoomEnty.setMa_mae_ney(json.getJSONObject(i).getString("ma_mae_ney"));
                    rGetRoomEnty.setMa_bo_ney(json.getJSONObject(i).getString("ma_bo_ney"));
                    rGetRoomEnty.setMa_month_ney(json.getJSONObject(i).getString("ma_month_ney"));
                    rGetRoomEnty.setMa_jun_year(json.getJSONObject(i).getString("ma_jun_year"));
                    rGetRoomEnty.setMa_secu_memo(json.getJSONObject(i).getString("ma_secu_memo"));
                    rGetRoomEnty.setMa_bunji2(json.getJSONObject(i).getString("ma_bunji2"));
                    rGetRoomEnty.setMa_level(json.getJSONObject(i).getString("ma_level"));
                    rGetRoomEnty.setMa_status1(json.getJSONObject(i).getString("ma_status1"));
                    rGetRoomEnty.setMa_status2(json.getJSONObject(i).getString("ma_status2"));
                    rGetRoomEnty.setMa_youtube(json.getJSONObject(i).getString("ma_youtube"));
                    rGetRoomEnty.setRgst_dt(json.getJSONObject(i).getString("rgst_dt"));
                    rGetRoomEnty.setMa_memo1(json.getJSONObject(i).getString("ma_memo1"));
                    rGetRoomEnty.setMa_memo2(json.getJSONObject(i).getString("ma_memo2"));
                    rGetRoomEnty.setMa_memo22(json.getJSONObject(i).getString("ma_memo22"));
                    rGetRoomEnty.setMa_memo3(json.getJSONObject(i).getString("ma_memo3"));
                    rGetRoomEnty.setMa_memo4(json.getJSONObject(i).getString("ma_memo4"));
                    rGetRoomEnty.setMa_memo5(json.getJSONObject(i).getString("ma_memo5"));
                    rGetRoomEnty.setMa_memo6(json.getJSONObject(i).getString("ma_memo6"));
                    rGetRoomEnty.setMa_memo7(json.getJSONObject(i).getString("ma_memo7"));
                    rGetRoomEnty.setMa_mine_memo(json.getJSONObject(i).getString("ma_mine_memo"));
                    if (json.getJSONObject(i).isNull("ma_lon")) {
                        rGetRoomEnty.setMa_lon("0");
                    } else {
                        rGetRoomEnty.setMa_lon(json.getJSONObject(i).getString("ma_lon"));
                    }
                    if (json.getJSONObject(i).isNull("ma_lat")) {
                        rGetRoomEnty.setMa_lat("0");
                    } else {
                        rGetRoomEnty.setMa_lat(json.getJSONObject(i).getString("ma_lat"));
                    }
                    rGetRoomEnty.setDistance(json.getJSONObject(i).getString("distance"));

                    rGetRoomList.add(rGetRoomEnty);
                }

                /////////////////////////////////////////////////
                // 2018.10.06
                // 마지막 빈 공간을 하나 추가한다.
                // RGetRoomEnty rGetRoomEnty = new RGetRoomEnty();
                // rGetRoomList.add(rGetRoomEnty);
                /////////////////////////////////////////////////

            }

            if (isExpand) {
                if (isAllList) {
                    isAllVisible = true;

                    detailAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomList, pressPoi, isMarkerOneClick,false);
                    listRoomList.setAdapter(detailAdapter);
                } else {
                    setMarkerTouchListView();
                }
            }

            if (rGetRoomList.size() > 0) {
//                if (poiType == CHOICE_POSITION) {
//                    MainAction.setMapPointAndMark_Sub(rGetRoomList, psPhoneNumber, mapView, MainActivity.this);
//                } else if (poiType == MOVE_POSITION) {
//                        MainAction.setMapPoint_Move(rGetRoomList, psPhoneNumber, mapView, MainActivity.this);
//                } else {
//                    MainAction.setMapPointAndMark(rGetRoomList, psPhoneNumber, mapView, MainActivity.this);
//                }
//                if (rGetRoomList.size() > 150) {
//                    MainAction.setMapPointAndMark(rGetRoomList, psPhoneNumber, mapView, MainActivity.this);
//                } else {
                    MainAction.setMapPointAndMark_Sub(rGetRoomList, mapView, MainActivity.this, sMess);
//                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }

    }


    private void getDetailSubList(Double dblLat, Double dblLon, Double dblDistance) {

        // mapView.removeAllPOIItems();

        rGetRoomSubList = new ArrayList<RGetRoomEnty>();
        Double dblLat2 = Double.valueOf(0);
        Double dblLon2 = Double.valueOf(0);

        try {
            if (rGetRoomList == null || rGetRoomList.size() == 0) {
                Toast.makeText(MainActivity.this, "이 지역에 매물이 없습니다.", Toast.LENGTH_SHORT).show();
            } else  {

                for (int i = 0; i < rGetRoomList.size(); i++) {
                    RGetRoomEnty rGetRoomSubEnty = new RGetRoomEnty();

                    dblLat2 = Double.parseDouble(rGetRoomList.get(i).getMa_lat());
                    dblLon2 = Double.parseDouble(rGetRoomList.get(i).getMa_lon());

                    // 킬로미터(Kilo Meter) 단위
                    double distanceKiloMeter = distance(dblLat, dblLon, dblLat2, dblLon2, "kilometer");

                    if (dblDistance >= distanceKiloMeter)
                    {
                        rGetRoomSubEnty.setID(rGetRoomList.get(i).getID());
                        rGetRoomSubEnty.setMa_Use_Yn(rGetRoomList.get(i).getMa_Use_Yn());
                        rGetRoomSubEnty.setMa_Use_Yn2(rGetRoomList.get(i).getMa_Use_Yn2());
                        rGetRoomSubEnty.setMa_Good_Yn(rGetRoomList.get(i).getMa_Good_Yn());
                        rGetRoomSubEnty.setGubun(rGetRoomList.get(i).getGubun());
                        rGetRoomSubEnty.setFcnt(rGetRoomList.get(i).getFcnt());
                        rGetRoomSubEnty.setImgCnt(rGetRoomList.get(i).getImgCnt());
                        rGetRoomSubEnty.setReplyCnt(rGetRoomList.get(i).getReplyCnt());
                        rGetRoomSubEnty.setDeleteCnt(rGetRoomList.get(i).getDeleteCnt());
                        rGetRoomSubEnty.setImgAppendYn(rGetRoomList.get(i).getImgAppendYn());
                        rGetRoomSubEnty.setImgDeleteYn(rGetRoomList.get(i).getImgDeleteYn());
                        rGetRoomSubEnty.setFavYn(rGetRoomList.get(i).getFavYn());
                        rGetRoomSubEnty.setDtTermCnt(rGetRoomList.get(i).getDtTermCnt());
                        rGetRoomSubEnty.setDtTermCnt3(rGetRoomList.get(i).getDtTermCnt3());
                        rGetRoomSubEnty.setMa_agent_yn(rGetRoomList.get(i).getMa_agent_yn());
                        rGetRoomSubEnty.setMa_pass_yn(rGetRoomList.get(i).getMa_pass_yn());
                        rGetRoomSubEnty.setModfy_dt(rGetRoomList.get(i).getModfy_dt());
                        rGetRoomSubEnty.setMa_jeon_area_m2(rGetRoomList.get(i).getMa_jeon_area_m2());
                        rGetRoomSubEnty.setMa_jeon_area(rGetRoomList.get(i).getMa_jeon_area());
                        rGetRoomSubEnty.setMa_bld_nm(rGetRoomList.get(i).getMa_bld_nm());
                        rGetRoomSubEnty.setMa_addr3(rGetRoomList.get(i).getMa_addr3());
                        rGetRoomSubEnty.setMa_yun_ney(rGetRoomList.get(i).getMa_yun_ney());
                        rGetRoomSubEnty.setModfy_id(rGetRoomList.get(i).getModfy_id());
                        rGetRoomSubEnty.setMa_mae_ney(rGetRoomList.get(i).getMa_mae_ney());
                        rGetRoomSubEnty.setMa_bo_ney(rGetRoomList.get(i).getMa_bo_ney());
                        rGetRoomSubEnty.setMa_month_ney(rGetRoomList.get(i).getMa_month_ney());
                        rGetRoomSubEnty.setMa_jun_year(rGetRoomList.get(i).getMa_jun_year());
                        rGetRoomSubEnty.setMa_secu_memo(rGetRoomList.get(i).getMa_secu_memo());
                        rGetRoomSubEnty.setMa_bunji2(rGetRoomList.get(i).getMa_bunji2());
                        rGetRoomSubEnty.setMa_level(rGetRoomList.get(i).getMa_level());
                        rGetRoomSubEnty.setMa_status1(rGetRoomList.get(i).getMa_status1());
                        rGetRoomSubEnty.setMa_status2(rGetRoomList.get(i).getMa_status2());
                        rGetRoomSubEnty.setMa_youtube(rGetRoomList.get(i).getMa_youtube());
                        rGetRoomSubEnty.setRgst_dt(rGetRoomList.get(i).getRgst_dt());
                        rGetRoomSubEnty.setMa_memo1(rGetRoomList.get(i).getMa_memo1());
                        rGetRoomSubEnty.setMa_memo2(rGetRoomList.get(i).getMa_memo2());
                        rGetRoomSubEnty.setMa_memo22(rGetRoomList.get(i).getMa_memo22());
                        rGetRoomSubEnty.setMa_memo3(rGetRoomList.get(i).getMa_memo3());
                        rGetRoomSubEnty.setMa_memo4(rGetRoomList.get(i).getMa_memo4());
                        rGetRoomSubEnty.setMa_memo5(rGetRoomList.get(i).getMa_memo5());
                        rGetRoomSubEnty.setMa_memo6(rGetRoomList.get(i).getMa_memo6());
                        rGetRoomSubEnty.setMa_memo7(rGetRoomList.get(i).getMa_memo7());
                        rGetRoomSubEnty.setMa_mine_memo(rGetRoomList.get(i).getMa_mine_memo());

                        rGetRoomSubEnty.setMa_lon(rGetRoomList.get(i).getMa_lon());
                        rGetRoomSubEnty.setMa_lat(rGetRoomList.get(i).getMa_lat());

                        rGetRoomSubList.add(rGetRoomSubEnty);

                        Log.e("♣♣", "dblLat : " + dblLat  + " dblLon : " + dblLon );
                    } else {
                        Log.e("♣", "dblLat : " + dblLat  + " dblLon : " + dblLon );
                    }

                }

            }

            // 건수가 없으면
            if (rGetRoomSubList == null || rGetRoomSubList.size() == 0) {
                txtSubTotal.setVisibility(View.INVISIBLE);
            } else {
                txtSubTotal.setVisibility(View.VISIBLE);
                // 화면에 보이는 겟수
                SpannableString content = new SpannableString("택:" + rGetRoomSubList.size() + "개");
                txtSubTotal.setText(content.toString());
                txtSubTotal.setTextColor(Color.parseColor("#ffff00")); // 노란색 #ffff00
            }

            if (isExpand) {
                if (isAllList) {
                    isAllVisible = true;

                    detailAdapter = new MainDetailAdapter(MainActivity.this, R.layout.row_detail, rGetRoomSubList, pressPoi,isMarkerOneClick, false);
                    listRoomSubList.setAdapter(detailAdapter);
                } else {
                    setMarkerTouchListView();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        reqCode = 0;

        if (resultCode == RESULT_OK) {
            if (requestCode == 100 || requestCode == 200) {
                reDataView("onActivityResult:100:200");
            } else if (requestCode == 9901) { // 사진추가
                reqCode = 9901;
                reDataView("onActivityResult:9001");
            } else if (requestCode == 9902) { // 명함추가
                reqCode = 9902;
                Log.d("@@@", "onActivityResult : 명함 등록 완료 ~~~");
                m_optionLogin.onResume(); // 재조회를 한다.

            }
        }

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mapView.setShowCurrentLocationMarker(false);
    }

    private String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1);
                if (address != null && address.size() > 0) {
                    String currentLocationAddress = address.get(0).getAddressLine(0);
                    bf.append(currentLocationAddress);
                }
            }

        } catch (IOException e) {
            bf.append("");
            e.printStackTrace();
        }
        return bf.toString();
    }

    private boolean chkGpsService() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("▶ 위치 서비스 설정");
            gsDialog.setMessage("GPS사용을 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            }).create().show();
            return false;
        } else {
            return true;
        }

    }

    public void popUpClose() {

        try {
            double lati = 0;
            double longi = 0;
            if (rGetRoomList.get(pressPoi).getMa_lat().equals(null)) {
                lati = 0;
            } else {
                lati = Double.parseDouble(rGetRoomList.get(pressPoi).getMa_lat());
            }
            if (rGetRoomList.get(pressPoi).getMa_lon().equals(null)) {
                longi = 0;
            } else {
                longi = Double.parseDouble(rGetRoomList.get(pressPoi).getMa_lon());
            }

            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();
            // mapView.setLayoutParams(params);
            // mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lati, longi), true);
            Util.collapse(llCoverContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isExpand = false;
        }

    }

    public void popUpClose2() {

        try {
            Util.collapse(llCoverContent);
        } finally {
            isExpand = false;
        }

    }

    public void listClickClose(int position) {

        double lati = 0;
        double longi = 0;

        // 지도에서 마커 툴팁을 클릭
        if (isMarkerOneClick) {

            Util.collapse(llCoverContent);
            isExpand = false;

        } else {


            if (rGetRoomList.get(position).getMa_lat().equals(null)) {
                lati = 0;
            } else {
                lati = Double.parseDouble(rGetRoomList.get(position).getMa_lat());
            }
            if (rGetRoomList.get(position).getMa_lon().equals(null)) {
                longi = 0;
            } else {
                longi = Double.parseDouble(rGetRoomList.get(position).getMa_lon());
            }

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

            /////////////////////////////
            /* 리스트를 닫고 지도 표시 */

            mapView.setLayoutParams(params);
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

            mapView.selectPOIItem(mapView.getPOIItems()[position], true);

            mapView.setZoomLevel(1, true);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lati, longi), true);
            Util.collapse(llCoverContent);
            isExpand = false;
        }

    }

    // 해상도에 따른 화면의 높이를 지정한다.
    public void setResoluation(RelativeLayout.LayoutParams params, String gubun, String sAction) {
        // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mapView.getLayoutParams();

        Log.e("..Action:", sAction);
//        Log.e("..height:", String.valueOf(height));
//        Log.e("..width:", String.valueOf(width));
        Log.e("..mapView.getHeight():", String.valueOf(mapView.getHeight()));

		/*
		width <= 800                  height : 1280
		width > 900 && width < 1440   height : 1920
		width >= 1440                 height : 2560
		 */

        int match_parent= RelativeLayout.LayoutParams.MATCH_PARENT;
        int wrap_content= RelativeLayout.LayoutParams.WRAP_CONTENT;
        /*
        chatView.setBackground(context.getResources().getDrawable(R.drawable.incoming_normal));

        RelativeLayout.LayoutParams parentParams=new RelativeLayout.LayoutParams(match_parent,wrap_content);
        parentParams.setMarginStart(5);
        parent.setLayoutParams(parentParams);
*/

        // params = new RelativeLayout.LayoutParams(match_parent,wrap_content);


        if (gubun.equals("+")) {
/*
            if (width <= 800) {
                params.height = mapView.getHeight() + 500; // 700
            } else if (width > 800 && width < 1440) {
                params.height = mapView.getHeight() + 700; // 900
            } else {
                params.height = mapView.getHeight() + 1900; // 1220
            }
*/

        } else {
/*
            if(width <= 800) {
                params.height = mapView.getHeight() - 700;
            } else	if(width > 800 && width < 1440) {
                if (sAction.equals("Marker")) {
                    params.height = mapView.getHeight() - 1200;
                    // params.topMargin = 1000;
                } else if (sAction.equals("txtTotal")){
                    params.height = mapView.getHeight() - 900;
                } else{
                    params.height = mapView.getHeight() - 900;
                }

            } else {
                params.height = mapView.getHeight() - 1220;
            }
*/
        }

        Log.e("mapView.getHeight:", String.valueOf(mapView.getHeight()));
        Log.e("params.height:", String.valueOf(params.height));
        // mapView.setLayoutParams(params);

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {

    }


    /**
     * small map view 에 지정된 위치에  중심 maker 를 표시합니다.
     *
     */
    public void addCenterMarker(double latitude, double longitude) {

//        MapPOIItem customMarker = new MapPOIItem();
//        customMarker.setItemName("현재위치");
//        customMarker.setTag(1);
//        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//        customMarker.setCustomImageResourceId(R.drawable.marker_current_pink); // 마커 이미지.
//        customMarker.setCustomImageAutoscale(true); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
//        customMarker.setMoveToCenterOnSelect(true);
//        mapView.addPOIItem(customMarker);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);

    }

    public class BackgroundThread extends Thread {
        @Override
        public void run() {

            String sVersion = null;
            String sVerName = null;

            try {
                Thread.sleep(5000);

                if (Util.getWhatKindOfNetwork(getApplicationContext()).equals("NONE")) {
                    // Toast.makeText(MainActivity.this, "인너텟 연결이 원할하지 않습니다.\n\n빌라만을 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                ///////////////////////////////////////////////////
                // 서버에서 설정한 앱 버젼을 가지고 온다.
                Util.getMarketVersion(MainActivity.this);
                if (glVersion == null || glVersion.equals("")) {
                    // Toast.makeText(MainActivity.this, "네트워크 연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                storeVersion = glVersion;
                storeDescription = glVername;
                ///////////////////////////////////////////////////

                //////////////////////////////////////////////////////////////////////////////////////////////
                // 결제 금액 필드에 보여준다.
                NavigationView navigationView = findViewById(R.id.nav_view);
                final View headerLayout = navigationView.getHeaderView(0);

                TextView tvName = headerLayout.findViewById(R.id.txtPhoneName);
                TextView tvPayment = headerLayout.findViewById(R.id.txtPay_Amount);
                TextView tvPayBank = headerLayout.findViewById(R.id.txtPay_Bank);

                TextView data_main_board = findViewById(R.id.data_main_board);
                TextView txt_main_board = findViewById(R.id.txt_main_board);
                // Util.setPayment(MainActivity.this, tvName, tvPayment, tvPayBank, data_main_board, txt_main_board);
                //////////////////////////////////////////////////////////////////////////////////////////////

            } catch (InterruptedException e) {
                Util.showToast(MainActivity.this, "다시 시도 바랍니다.");
                Log.e("error:","The runnable times out.");
            }

            // 패키지 네임 전달
            // storeVersion = MarketVersionChecker.getMarketVersion(getPackageName());
            // storeDescription = MarketVersionChecker.getMarketDescription(getPackageName());

            // storeVersion = MarketVersionChecker.getMarketVersionFast(getPackageName());
            // storeVersion = MarketVersionChecker.getMarketVersionFast("com.zibnawaapp.zibnawa");
            // storeVersion = MarketVersionChecker.getMarketVersion("com.zibnawaapp.zibnawa");


            // 디바이스 버전 가져옴
            try {
                deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
            // 핸들러로 메세지 전달


            /*
           final Button btn_main_board2 = (Button) findViewById(R.id.btn_main_board);
           btn_main_board2.setVisibility(View.INVISIBLE); // TODO 게시판 버튼 보여질지 여부
            */


        }
    }

    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);

    // 핸들러 객체 만들기
    private static class DeviceVersionCheckHandler extends Handler {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        public DeviceVersionCheckHandler(MainActivity mainActivity) {
            mainActivityWeakReference = new WeakReference<MainActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mainActivityWeakReference.get();
            // OptionRangeLoginDialog login = new OptionRangeLoginDialog.OnShowListener();

            if (activity != null) {
                activity.handleMessage(msg);
                // 핸들메세지로 결과값 전달
            }
        }

    }

    private void handleMessage(Message msg) {

        if (storeVersion != null) {

            //핸들러에서 넘어온 값 체크
            if( Util.isGreat(deviceVersion, storeVersion) ) {
                // 업데이트 불필요
            } else {
                // 업데이트 필요
                // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light)); // 오류로 인해 변경처리
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // DODO 새로운 버전이 있습니다.
                alertDialogBuilder.setTitle("▶ 업데이트");
                alertDialogBuilder
                        .setMessage("(" + deviceVersion + "->" + storeVersion + ") 새로운 버전이 있습니다.\n" + storeDescription + "\n" + "업데이트 해 주세요.")
                        .setCancelable(true)  // 뒤로가기 취소 가능 여부
                        .setPositiveButton("업데이트 바로가기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 구글플레이 업데이트 링크
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                try {
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();
                } catch (Exception e) {
                    Util.showToast(MainActivity.this, "새로운 버전("+storeVersion+")이 있습니다. \n구글 앱스토어에서 업데이트 하십시오. ", true);
                    e.printStackTrace();
                }
            }
        }
    }

//    // 검색조건에서 버튼을 클릭하면 메인 화면을 바꾼다.
//    public void ClickBtnSelect(String strDesc) {
//
//        try {
//
//            //  검색조건에 나의 매물을 보여 준다.
//            txtExp.setVisibility(View.VISIBLE);
//            txtExp.setTextColor(Color.parseColor("#ff0000")); // fdfd67
//            txtExp.setText(strDesc);
//
//
//        } finally {
//            isExpand = false;
//        }
//
//    }


    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    // 매물 등록(요청) 문자 //TODO
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void nav_letter_send() throws UnsupportedEncodingException {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_sms, null);

        final EditText editTextPhoneNo = alertLayout.findViewById(R.id.editTextPhoneNo);
        final EditText editTextSMS = alertLayout.findViewById(R.id.editTextSMS);
        final TextView textViewSMS = alertLayout.findViewById(R.id.textViewSMS);

        // 만들기 버튼 보여주기
        final Button button_01 = alertLayout.findViewById(R.id.btn_letterContent);
        button_01.setVisibility(View.VISIBLE);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // Specify the alert dialog title
        String titleText = "   매물 등록(요청) 문자";
        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan( foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        // Set the alert dialog title using spannable string builder
        alert.setTitle(ssBuilder);

        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        /*
        String strMess = new String();

        String sData = "";
        sData = sData + URLEncoder.encode("usr_id", "UTF-8") + "=" + editTextPhoneNo.getText().toString() + "&";
        sData = sData + URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(psPhoneNumber, "UTF-8") ;
        String URL = Util.getURL_IT() + "/ahh/viewMainRegist_G.do?YESIT="+Util.getBase64encode(sData).replace("\n",""); // TODO

        strMess += "빌라매매 전문 사이트\n";
        strMess += "`빌라만`입니다.\n";
        strMess += "\r\n";
        strMess += "아래의 주소에서\n";
        strMess += "10,000원을 `충전하기` 하시고\n";
        strMess += "매물을 등록하실 수 있습니다.\n";
        strMess += "\r\n";
        strMess += URL;  // 본문 내용
        strMess += "\r\n";
        strMess += "\r\n";
        strMess += "등록 후 문자로 회신 바랍니다.\n";
        strMess += "\r\n";
        strMess += "감사합니다.\n";

        // 메시지 내용
        editTextSMS.setText(strMess);

        textViewSMS.setText("크기 : "+ strMess.getBytes("KSC5601").length + " bytes");

        if (strMess.getBytes("KSC5601").length > 4000) {
            Toast.makeText(MainActivity.this, "내용이 4,000 Bytes 이상은 발송할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        */

        // 취소  ______________________________________________________________
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 확인 ______________________________________________________________
        alert.setPositiveButton("발송 ▶", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strPhone = editTextPhoneNo.getText().toString();
                String strSMS = editTextSMS.getText().toString();

                if (strPhone.length() == 11)
                {
                    // sendMessage(0, strPhone, strSMS);
                    sendThread thread = new sendThread(0, strPhone, strSMS);
                    thread.start();
                } else {
                    Util.showToast(MainActivity.this, "[실패] 헨드폰 번호를 확인 바랍니다.");
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

















    //단건 메시지 발송
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendSingle() throws UnsupportedEncodingException {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_sms, null);

        final EditText editTextPhoneNo = alertLayout.findViewById(R.id.editTextPhoneNo);
        final EditText editTextSMS = alertLayout.findViewById(R.id.editTextSMS);
        final TextView textViewSMS = alertLayout.findViewById(R.id.textViewSMS);

        // 만들기 버튼 감추기
        final Button button_01 = alertLayout.findViewById(R.id.btn_letterContent);
        button_01.setVisibility(View.GONE);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // Specify the alert dialog title
        String titleText = "   문자 발송 서비스 입니다.";
        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan( foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        // Set the alert dialog title using spannable string builder
        alert.setTitle(ssBuilder);


        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        String strMess = "";

        strMess = getdMessage();
        // 메시지 내용
        editTextSMS.setText(strMess);
        textViewSMS.setText("크기 : "+ strMess.getBytes("KSC5601").length + " bytes");


        if (strMess.getBytes("KSC5601").length > 4000) {
            Toast.makeText(MainActivity.this, "내용이 4,000 Bytes 이상은 발송할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 취소  ______________________________________________________________
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 확인 ______________________________________________________________
        alert.setPositiveButton("발송 ▶", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strPhone = editTextPhoneNo.getText().toString();
                String strSMS = editTextSMS.getText().toString();

                // String strPhone = "홍길동,유관순,강감찬";
                strPhone = strPhone.replace(" ",",").replace("*",",").replace(".",",").replace("-","");
                String[] arrPhone = strPhone.split(",");

                // List<String> salesTeamList = new ArrayList<>();
                int iCnt = 0;
                for (int i = 0; i < arrPhone.length; i++) {
                    if (arrPhone[i].length() == 11) {
                        iCnt = iCnt + 1;

                        sendThread thread = new sendThread(i, arrPhone[i], strSMS);
                        thread.start();
                    }
                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

        /*
        Button pq = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button nq = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        pq.setBackgroundColor(Color.argb(200,200,200,200));
        nq.setBackgroundColor(Color.argb(200,200,200,200));
        */
    }



    //대량 메시지 발송
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void showReceiveList() throws UnsupportedEncodingException {
    // private void showReceiveList() {
        HashMap<String, String> locMap = new HashMap<String, String>();
        UServerLetterSend.with(MainActivity.this).forStart(new UServerLetterSend.WServerLetterSend.Callback() {
            public void onResult(JSONArray json) {
                final JSONArray jsonList = json;
                final JSONArray mobileList = new JSONArray();


                //서버에서 가져온 건수
                if (json.length() > 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("▶ 수신자를 선택하십시오.");
                    builder.setIcon(R.drawable.villaman);

                    String[] rcvrList = new String[jsonList.length()];
                    final boolean[] checkList = new boolean[jsonList.length()];
                    for (int i = 0; i < checkList.length; i++) {
                        checkList[i] = true;
                    }

                    String rcvr = "";
                    for (int i = 0; i < json.length(); i++) {
                        try {
                            rcvr = json.getJSONObject(i).get("ma_mobile").toString().substring(0,3) + "-"
                                    + json.getJSONObject(i).get("ma_mobile").toString().substring(3,7) + "-"
                                    + json.getJSONObject(i).get("ma_mobile").toString().substring(7,11);
                            rcvr += " [" + json.getJSONObject(i).get("ma_president").toString() + "]";
                            rcvrList[i] = rcvr;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    final List<String> selectedList = Arrays.asList(rcvrList);
                    builder.setMultiChoiceItems(rcvrList, checkList, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkList[which] = isChecked;
                            String currentItem = selectedList.get(which);

                        }
                    });

                    builder.setPositiveButton("다음 ▶", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int chkCnt = 0;
                            for (int i = 0; i < checkList.length; i++) {
                                if (checkList[i] == true) {
                                    chkCnt++;
                                    try {
                                        // 체크한 수신자만 필터
                                        mobileList.put(jsonList.getJSONObject(i));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            //체크박스 선택한 건수
                            if (chkCnt > 0) {
//                                if (chkCnt > 300) {
//                                    Toast.makeText(MainActivity.this, "수신자는 1회 최대 300명입니다.", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }


                                // 메시지 내용을 만든다.
                                makeMessage(mobileList);
                            } else {
                                Util.showToast(MainActivity.this, "대량 메시지 수신자 목록이 없습니다.");
                                return;
                            }
                        }
                    });

                    builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Util.showToast(MainActivity.this, "대량 메시지 수신자 목록이 없습니다.");
                }
            }

        }, locMap);
    }


    private void makeMessage(JSONArray json) {
        final JSONArray mobileList = json;
        String strMess = "";

        if (rGetRoomList.size() > 50) {
            Toast.makeText(MainActivity.this, "매물 50건 이상은 발송할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this).setCancelable(false);
        dialog.setTitle("▶ 메시지 확인");
        strMess = mobileList.length() + " 명에게 " + rGetRoomList.size() + " 건의 물건 정보를 발송합니다.";

        dialog.setMessage(strMess + "\n\n " + getdMessage());
        dialog.setPositiveButton("발송", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mobileNumber = "";
                String strMessage = getdMessage();

                for (int i = 0; i < mobileList.length(); i++) {
                    try {
                        mobileNumber = mobileList.getJSONObject(i).getString("ma_mobile");

                        sendThread thread = new sendThread(i, mobileNumber, strMessage);
                        thread.start();

                        /*
                        Toast.makeText(MainActivity.this, "["+ mobileNumber.substring(0,3) + "-" + mobileNumber.substring(3,7) + "-" + mobileNumber.substring(7,11) + "] 님에게 메시지 발송중...", Toast.LENGTH_SHORT).show();
                        // Thread.sleep(1000);
                        wait(1000);
                        // 메시지 발송
                        sendMessage(mobileNumber, strMessage);
                        */

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Toast.makeText(MainActivity.this, "메시지 발송이 실행되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "메시지 발송이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    private String getdMessage() {
        String dongName = "■";
        String pre = "▶";
        String message = "[광고] 빌라 매물 리스트 입니다.\n\n";

        message += "매매완료 :  \n\n";

        ArrayList<RGetRoomEnty> arrList = new ArrayList<RGetRoomEnty>();
        arrList = (ArrayList) rGetRoomList.clone();

        Collections.sort(arrList, getMa_memo2Comparator);
        String[] parts = null;
        String strPhone  = psPhoneNumber;

        for (int i = 0; i < arrList.size(); i++) {
            parts = arrList.get(i).getMa_memo2().replace("  "," ").split(" ");
            if (!dongName.equals(parts[0])) {
                dongName = parts[0];
                message += "■" + dongName + "■\n";
            }

            message += pre;
            message += parts[1];

            message += " " + arrList.get(i).getMa_memo1();
            message += " " + arrList.get(i).getMa_jun_year() + "년";
            message += " " + arrList.get(i).getMa_mae_ney().substring(0, arrList.get(i).getMa_mae_ney().length() - 2);
            message += " 전:" + arrList.get(i).getMa_jeon_area();
            message += " " + arrList.get(i).getMa_memo3();
            message += " " + arrList.get(i).getMa_memo4();
            message += " " + arrList.get(i).getMa_memo5();
            message += "\r\n";
            message += "\r\n";
        }
        message += "\n [빌라만]을 통하여 발송하는 문자 발송 서비스 입니다.";
        message += "\n 발송 삭제를 원하시면 [" + strPhone.substring(0,3) + "-" + strPhone.substring(3,7) + "-" + strPhone.substring(7,11) + "] 번호로 문자 바랍니다.";
        message += "\r\n";

        return message;
    }

    // 동명으로 sort
    public static Comparator<RGetRoomEnty> getMa_memo2Comparator = new Comparator<RGetRoomEnty>() {
        public int compare(RGetRoomEnty compar1, RGetRoomEnty compar2) {
            String str1 = compar1.getMa_memo2().toUpperCase();
            String str2 = compar2.getMa_memo2().toUpperCase();
            //ascending order
            return str1.compareTo(str2);
        }
    };


    private class sendThread extends Thread {
        private int threadNum = 0;
        private String mobileNumber = "";
        private String message = "";

        public sendThread(int threadNum, String mobileNumber, String message) {
            this.threadNum = threadNum;
            this.mobileNumber = mobileNumber;
            this.message = message;
        }

        public void run() {
            try {

                sendMessage(threadNum, mobileNumber, message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(int smsNo, String smsNumber, String smsText) throws UnsupportedEncodingException {

        final int smsNo2 = smsNo + 1;
        final String smsNum = smsNumber;
        final String smsTxt = smsText;
/*
        final SmsManager smsManager = SmsManager.getDefault();
        if (smsManager == null) {
            return;
        }
*/

        //final PendingIntent sentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT_ACTION"), 0);
        //final PendingIntent deliveredIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> partMessage = smsManager.divideMessage(smsText);

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        Log.d(__TAG_MY__,"MY:psPhoneNumber:"+psPhoneNumber);
        Log.d(__TAG_MY__,"MY:smsNum:"+smsNum);
        Log.d(__TAG_MY__,"MY:String.valueOf(smsNo2):"+String.valueOf(smsNo2));
        Log.d(__TAG_MY__,"MY:smsTxt:"+smsTxt);

        // SMS가 발송될때 실행
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:

                        HashMap<String, String> detailMap = new HashMap<String, String>();
                        detailMap.put("usr_id", psPhoneNumber);
                        detailMap.put("ma_cd", "sms");
                        detailMap.put("ma_id", smsNum);
                        detailMap.put("ma_no", String.valueOf(smsNo2));
                        detailMap.put("ma_memo", smsTxt);

                        UServerLog.with(MainActivity.this).forStart(new UServerLog.WServerLog.Callback() {

                            @Override
                            public void onResult(JSONArray json) {
                                // Log.e("11111111111", json.toString());
                                // 전송 성공
                                Toast.makeText(MainActivity.this, "정상적으로 [" + smsNo2 + "] 발송 하였습니다.", Toast.LENGTH_SHORT).show();

                            }

                        }, detailMap);

                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(getApplicationContext(), "전송 실패 [" + smsNo2 + "] 하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(getApplicationContext(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(getApplicationContext(), "휴대폰이 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(getApplicationContext(), "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Nothing...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        // SMS가 도착했을때 실행
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(getApplicationContext(), "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        //finish();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(getApplicationContext(), "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));


        sentIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT_ACTION"), 0));
        deliveryIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED_ACTION"), 0));

        smsManager.sendMultipartTextMessage(smsNumber, null, partMessage, sentIntents, deliveryIntents);




        //   SmsManager smsManager = SmsManager.getDefault();
        //   ArrayList<String> partMessage = smsManager.divideMessage(smsText);

        /*
        int numParts = partMessage.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < numParts; i++) {
            sentIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT_ACTION"), 0));
            deliveryIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED_ACTION"), 0));
        }

        smsManager.sendMultipartTextMessage(smsNumber,null, parts, sentIntents, deliveryIntents);
        */

        //    smsManager.sendMultipartTextMessage(smsNumber, null, partMessage, sentIntent, deliveredIntent);
        // smsManager.sendMultipartTextMessage(smsNumber, null, partMessage, new Intent("SMS_SENT_ACTION"), new Intent("SMS_DELIVERED_ACTION"));
        // smsManager.sendMultipartTextMessage(smsNumber, null, partMessage, null, null);

        /*
        if (smsText.getBytes("KSC5601").length > 80) {
            ArrayList<String> partMessage = smsManager.divideMessage(smsText);
            smsManager.sendMultipartTextMessage(smsNumber, null, partMessage, sentIntent, deliveredIntent);
        } else {
            smsManager.sendTextMessage(smsNumber, null, smsText, null, null);
        }
        */

        /*
        if (message.length() > 160) {
            smsManager.sendMultipartTextMessage(mobileNumber, null, partMessage, null, null);
        } else {
            smsManager.sendTextMessage(mobileNumber, null, message, null, null);
        }
        */

    }

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            Bundle bun = msg.getData();
//            String naverHtml = bun.getString("HTML_DATA");
//            edtHtml.setText(naverHtml);
//        }
//    };
//
//
//



    //대량 메시지 발송
////////////////////////////////////////////////////////////////////////////////////////////////////

    //개발자용 키해쉬 생성
    private void makeKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            try {
                // 3.  위치 값을 가져올 수 있음
                //  mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    ///////////////////////////////////////////////////
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("▶ 위치 서비스 비활성화");
        builder.setIcon(R.drawable.villaman);
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
    ///////////////////////////////////////////////////



    private String reSearchData()
    {
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinInput()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMaxInput()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinArea()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMaxArea()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinFloor()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMaxFloor()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinRoom()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMaxRoom()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMinYear()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(MainActivity.this).getMaxYear()+":");
//
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyNew02()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyBlankPicyn()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyConPicyn()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyConEmpty()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyNewPicyn()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyNewEmpty()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getMyConfirm02()+":");
//        Log.d(__TAG__,"◆◆:"+FProPrefer.with(context).getChoiceMaSearch()+":");
//        Log.d(__TAG__,"◆◆getSearchAction:"+FProPrefer.with(context).getSearchAction()+":");


        String strSearch = "";
        if (FProPrefer.with(MainActivity.this).getMinInputBo() != 0) {
            strSearch = strSearch + "01,";
        }
        if (FProPrefer.with(MainActivity.this).getMaxInputBo() != 30) {
            strSearch = strSearch + "02,";
        }
        if (FProPrefer.with(MainActivity.this).getMinArea() != 5) {
            strSearch = strSearch + "03,";
        }
        if (FProPrefer.with(MainActivity.this).getMaxArea() != 30) {
            strSearch = strSearch + "04,";
        }
        if (FProPrefer.with(MainActivity.this).getMinFloor() != 0) {
            strSearch = strSearch + "05,";
        }
        if (FProPrefer.with(MainActivity.this).getMaxFloor() != 7) {
            strSearch = strSearch + "06,";
        }
        if (FProPrefer.with(MainActivity.this).getMinRoom() != 1) {
            strSearch = strSearch + "07,";
        }
        if (FProPrefer.with(MainActivity.this).getMaxRoom() != 5) {
            strSearch = strSearch + "08,";
        }
        if (FProPrefer.with(MainActivity.this).getMinYear() != 1985) {
            strSearch = strSearch + "09,";
        }
        if (!Integer.toString(FProPrefer.with(MainActivity.this).getMaxYear()).equals(Util.getCurYear())) {
            strSearch = strSearch + "10,";
        }


        if(FProPrefer.with(context).getMyNew02() != null && FProPrefer.with(context).getMyNew02() != ""){
            strSearch = strSearch + "11,";
        }
        if(FProPrefer.with(context).getMyBlankPicyn() != null && FProPrefer.with(context).getMyBlankPicyn() != ""){
            strSearch = strSearch + "12,";
        }
        if(FProPrefer.with(context).getMyConPicyn() != null && FProPrefer.with(context).getMyConPicyn() != ""){
            strSearch = strSearch + "13,";
        }
        if(FProPrefer.with(context).getMyConEmpty() != null && FProPrefer.with(context).getMyConEmpty() != ""){
            strSearch = strSearch + "14,";
        }
        if(FProPrefer.with(context).getMyNewPicyn() != null && FProPrefer.with(context).getMyNewPicyn() != ""){
            strSearch = strSearch + "15,";
        }
        if(FProPrefer.with(context).getMyNewEmpty() != null && FProPrefer.with(context).getMyNewEmpty() != ""){
            strSearch = strSearch + "16,";
        }
        if(FProPrefer.with(context).getMyConfirm02() != null && FProPrefer.with(context).getMyConfirm02() != ""){
            strSearch = strSearch + "17,";
        }
        if(FProPrefer.with(context).getChoiceMaSearch() != null && FProPrefer.with(context).getChoiceMaSearch() != ""){
            strSearch = strSearch + "18,";
        }
        if(FProPrefer.with(context).getSearchAction() != null && FProPrefer.with(context).getSearchAction() != ""){
            strSearch = strSearch + "19,";
        }


        Log.d(__TAG__,"◆◆strSearch:"+strSearch+":");

        return strSearch;

    }

/*
    private void saveExcel(){
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet(); // 새로운 시트 생성
        Row row = sheet.createRow(0); // 새로운 행 생성
        Cell cell;// 1번 셀 생성과 입력
        cell = row.createCell(0);
        cell.setCellValue("아이디");// 2번 셀에 값 생성과 입력
        cell = row.createCell(1);cell.setCellValue("이름");

        for(int i = 0; i < 10 ; i++){ // 데이터 엑셀에 입력
            row = sheet.createRow(i+1);

            cell = row.createCell(0);
            cell.setCellValue("mItems.get(i).getId()" + i);
            cell = row.createCell(1);
            cell.setCellValue("mItems.get(i).getName()");
        }

        File excelFile = new File(getExternalFilesDir(null),"user.xls");
        try{
            FileOutputStream os = new FileOutputStream(excelFile);
            workbook.write(os);
        }catch (IOException e){
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),excelFile.getAbsolutePath()+"에 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

*/

}
