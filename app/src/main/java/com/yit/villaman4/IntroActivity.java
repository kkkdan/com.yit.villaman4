package com.yit.villaman4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class IntroActivity extends AppCompatActivity {

	private static final String TAG = "■■■";
	String phoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// phoneNumber = "010-3758-0862";
		phoneNumber = Util.getPhoneNumber(IntroActivity.this);
		if(phoneNumber == null || "".equals(phoneNumber))
			phoneNumber = "00000000000";

		UServerCheckPhoneNumber.with(this).forStart(new UServerCheckPhoneNumber.WServerCheck_PhoneNumber.Callback() {

			@Override
			public void onResult(JSONArray json) {
				Log.e(TAG,  "json" + json);
				try {

					if (json != null) {
						if (json.getJSONObject(0).get("useyn").equals("Y")) {
							Intent intent = new Intent(IntroActivity.this, MainActivity.class);

							Toast.makeText(IntroActivity.this, json.getJSONObject(0).get("modfy_id").toString(), Toast.LENGTH_SHORT).show();

							startActivity(intent);
						} else {
							Toast.makeText(IntroActivity.this, "인증되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(IntroActivity.this, "3.서버 접속이 원할하지 않습니다. \n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		}, phoneNumber);
	}
}
