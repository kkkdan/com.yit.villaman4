package com.yit.villaman4;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;

public class NavEnvironmentDialog extends BaseDialog {

	private MainActivity				activity;
	private static final String __TAG__ = "SelectDialog♥♥:";

	private Context context;
	private Button btnNavEnvironmentConfirm;
	private Button btnNavEnvironmentCancel;

	private TextView Env_distance;
	private TextView dataVilArea;

	private Callback mCallback;


	public NavEnvironmentDialog(MainActivity context, int theme) {
		super(context, theme);
		this.context = context;
		this.activity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nav_environment);

		this.startView_onCreate();
		this.startAction_onCreate();
	}

	private void startView_onCreate() {

		btnNavEnvironmentConfirm = (Button) findViewById(R.id.btnNavEnvironmentConfirm);
		btnNavEnvironmentCancel = (Button) findViewById(R.id.btnNavEnvironmentCancel);

		Env_distance = (TextView) findViewById(R.id.Env_distance);
//		final TextView dataVilArea = (TextView) findViewById(R.id.dataVilArea); // 바탕 화면

		/////////////////////////////////////////////////////////////
		if(FProPrefer.with(context).getEnv_distance() != null && FProPrefer.with(context).getEnv_distance() != ""){
			Env_distance.setText(FProPrefer.with(context).getEnv_distance());
		} else {
			FProPrefer.with(context).setEnv_distance("500");
			Env_distance.setText("500");
		}
		Env_distance.setPaintFlags(Env_distance.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		/////////////////////////////////////////////////////////////

	}

	private void startAction_onCreate() {

		// 근처지역 클릭
		Env_distance.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_singlechoice);

					adapter.add("⊙ " + "500m");
					adapter.add("⊙ " + "800m");
					adapter.add("⊙ " + "1,000m");
					adapter.add("⊙ " + "1,500m");
					adapter.add("⊙ " + "2,000m");

					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setTitle("● 근처지역 `거리`를 선택해 주세요.");
					builder.setIcon(R.drawable.villaman);
					// builder.setIcon(R.drawable.default_check);
					builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Env_distance.setText(adapter.getItem(which).replace("⊙ ","").replace("m",""));
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


		// 확인 버튼
		btnNavEnvironmentConfirm.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Util.showToast(activity,"확인 클릭 ~~~");

				// 환경에 저장한다.
				FProPrefer.with(context).setEnv_distance(Env_distance.getText().toString());

				// 바탕화면에 표시한다.
				dataVilArea = (TextView) findViewById(R.id.dataVilArea); // 바탕 화면
//				dataVilArea.setText(Env_distance.getText().toString());

//				if (mCallback != null) {
//					mCallback.onResponse(detailMap);
//				}

				dismiss();


			}
		});

		// 닫기 버튼
		btnNavEnvironmentCancel.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public NavEnvironmentDialog forStart(Callback OptionSelectDialog_Callback) {
		mCallback = OptionSelectDialog_Callback;
		return this;
	}
	
	public static interface Callback {
		void onResponse(HashMap<String, String> detailMap);
	}


}
