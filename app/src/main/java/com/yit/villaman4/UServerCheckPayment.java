package com.yit.villaman4;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UServerCheckPayment {

    private static final String __TAG__ = "CheckPayment♥♥:";

    private UServerCheckPayment() {
    }

    public static WServerCheck_Payment with(Context pCon) { return new WServerCheck_Payment(pCon); }

    public static class WServerCheck_Payment {

        private Callback mCallback;
        private Context mCon;
        private ServerConnection mServerConnection;
        private JSONArray data;

        private WServerCheck_Payment(Context pCon) {
            mCon = pCon;
        }

        public WServerCheck_Payment forStart(Callback WServerCheck_Payment_Callback, String phoneNum) {
            mCallback = WServerCheck_Payment_Callback;

            String data  = "";
            String version = "";
            String version5 = "";

            PackageInfo packageInfo;

            try {
                packageInfo = mCon.getApplicationContext()
                        .getPackageManager()
                        .getPackageInfo(mCon.getApplicationContext().getPackageName(), 0 );

                if (mCon.getPackageName().equals("com.yit.villaman5")) {
                    version = "";
                    version5 = packageInfo.versionName;
                } else {
                    version = packageInfo.versionName;
                    version5 = "";
                }


            } catch (PackageManager.NameNotFoundException e) {
            }


            try {

                data = URLEncoder.encode("packagename", "UTF-8") + "=" + URLEncoder.encode("" + mCon.getPackageName(), "UTF-8") + "&"
                        + URLEncoder.encode("version", "UTF-8") + "=" + URLEncoder.encode("" + version, "UTF-8") + "&"
                        + URLEncoder.encode("version5", "UTF-8") + "=" + URLEncoder.encode("" + version5, "UTF-8") + "&"
                       + URLEncoder.encode("usr_id", "UTF-8") + "=" + URLEncoder.encode("" + phoneNum, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // data = data + "&usr_ver=2.3";

            String url = Util.getURL_IT() + "/ahh/getPayment.do?YESIT="+Util.getBase64encode(data).replace("\n",""); // TODO

            try {
                mServerConnection = new ServerConnection(mCon, "GET", mHandler, url);
                mServerConnection.start();
            } catch (Exception e) {
                Log.e("UServerCheckPayment:",   "♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣ ");
                e.printStackTrace();
            }
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
                    }
                }else {

                    mCallback.onResult(null);
                    //Toast.makeText(mCon, "서버 접속이 원할하지 않습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }
}
