package com.yit.villaman4;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yit.villaman4.RangeSeekBar.OnRangeSeekBarChangeListener;

import java.util.HashMap;

public class OptionRangeSelectDialog extends BaseDialog {

	private MainActivity				activity;
	private static final String __TAG__ = "SelectDialog♥♥:";

	private Context context;
	private Button btnDialogClose;
	private Button btnRangeConfirm;
	private Button btnRangeClear;
	private Button btnRangeCancel;

	// private Button btnRangeClear;
	private TextView tvInputSeekBo;
	private LinearLayout inputSeekBo;
	private TextView tvInputSeekMonth;
	private LinearLayout inputSeekMonth;

	private TextView tvJeonCheckBox;
	private LinearLayout areaSeek;
	private TextView tvFloorCheckBox;

	private LinearLayout floorSeek;
	private TextView tvRoomCheckBox;
	private LinearLayout roomSeek;
	private TextView tvYearCheckBox;
	private LinearLayout yearSeek;
	private EditText searchText;

    // private TextView txtExp;

	private int minInputBo;
	private int maxInputBo;
	private int minInputMonth;
	private int maxInputMonth;

	private int minArea;
	private int maxArea;

	private int minFloor;
	private int maxFloor;

	private int minRoom;
	private int maxRoom;

	private int minYear;
	private int maxYear;
	
	private HashMap<String, String> addressMap;
	
	private Callback mCallback;
	

	public OptionRangeSelectDialog(MainActivity context, int theme, HashMap<String, String> addressMap) {
		super(context, theme);
		this.context = context;
		this.addressMap  = addressMap;
		this.activity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startView_onCreate();
		startAction_onCreate();
	}

	private void startView_onCreate() {
		btnDialogClose = (Button) findViewById(R.id.btnDialogClose);
		btnRangeConfirm = (Button) findViewById(R.id.btnRangeConfirm);
		btnRangeClear = (Button) findViewById(R.id.btnRangeClear);
		btnRangeCancel = (Button) findViewById(R.id.btnRangeCancel);

		inputSeekBo = (LinearLayout) findViewById(R.id.inputSeekBo);
		tvInputSeekBo = (TextView) findViewById(R.id.tvInputSeekBo);
		inputSeekMonth = (LinearLayout) findViewById(R.id.inputSeekMonth);
		tvInputSeekMonth = (TextView) findViewById(R.id.tvInputSeekMonth);

		searchText  = (EditText) findViewById(R.id.searchText);
        // txtExp = (TextView) findViewById(R.id.txtExp);

		searchText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if(keyCode == KeyEvent.KEYCODE_ENTER) {
						if(searchText.getText().toString().trim().isEmpty()) {
							searchText.setText("");
						}
						btnRangeConfirm.performClick();
						return true;
					}
				}
				return false;
			}
		});
		
		if(FProPrefer.with(context).getSearchText() != null && FProPrefer.with(context).getSearchText() != ""){
			searchText.setText(FProPrefer.with(context).getSearchText());
		}

		/////////////////////// 보증금
		RangeSeekBar<Integer> inputSeekBarBo = new RangeSeekBar<Integer>(0, 200, context);
		inputSeekBo.addView(inputSeekBarBo);
		inputSeekBarBo.setNotifyWhileDragging(true);
		
		if(FProPrefer.with(context).getMinInputBo() != 0)
			inputSeekBarBo.setSelectedMinValue(FProPrefer.with(context).getMinInputBo());
		else
			FProPrefer.with(context).setMinInputBo(0);
			
		if(FProPrefer.with(context).getMaxInputBo()  != 0)
			inputSeekBarBo.setSelectedMaxValue(FProPrefer.with(context).getMaxInputBo());
		else
			FProPrefer.with(context).setMaxInputBo(200);

		tvInputSeekBo.setText("▣ 보증금 : " + (FProPrefer.with(context).getMinInputBo() * 100) + " ~ " + (FProPrefer.with(context).getMaxInputBo() * 100));

		inputSeekBarBo.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
				if (minValue > 0 && maxValue == 200) {
					tvInputSeekBo.setText("▣ 보증금 : " + (minValue * 100) + " ~ 이상");
				} else {
					tvInputSeekBo.setText("▣ 보증금 : " + (minValue * 100) + " ~ " + (maxValue * 100));
				}

				minInputBo = minValue * 100;
				maxInputBo = maxValue * 100;
				FProPrefer.with(context).setMinInputBo(minValue);
				FProPrefer.with(context).setMaxInputBo(maxValue);
			}
		});
		///////////////////////////////////

		/////////////////////// 월세
		RangeSeekBar<Integer> inputSeekBarMonth = new RangeSeekBar<Integer>(5, 200, context);
		inputSeekMonth.addView(inputSeekBarMonth);
		inputSeekBarMonth.setNotifyWhileDragging(true);

		if(FProPrefer.with(context).getMinInputMonth() != 0)
			inputSeekBarMonth.setSelectedMinValue(FProPrefer.with(context).getMinInputMonth());
		else
			FProPrefer.with(context).setMinInputMonth(5);

		if(FProPrefer.with(context).getMaxInputMonth()  != 0)
			inputSeekBarMonth.setSelectedMaxValue(FProPrefer.with(context).getMaxInputMonth());
		else
			FProPrefer.with(context).setMaxInputMonth(200);

		tvInputSeekMonth.setText("▣ 월세 : " + (FProPrefer.with(context).getMinInputMonth() ) + " ~ " + (FProPrefer.with(context).getMaxInputMonth() ));

		inputSeekBarMonth.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

				if (minValue > 5 && maxValue == 200) {
					tvInputSeekMonth.setText("▣ 월세 : " + minValue + " ~ 이상");
				} else {
					tvInputSeekMonth.setText("▣ 월세 : " + minValue + " ~ " + maxValue);
				}

				minInputMonth = minValue;
				maxInputMonth = maxValue;
				FProPrefer.with(context).setMinInputMonth(minInputMonth);
				FProPrefer.with(context).setMaxInputMonth(maxInputMonth);
			}
		});
		///////////////////////////////////

		areaSeek = (LinearLayout) findViewById(R.id.areaSeek);
		tvJeonCheckBox = (TextView) findViewById(R.id.tvJeonCheckBox);
		RangeSeekBar<Integer> areaSeekBar = new RangeSeekBar<Integer>(5, 30, context);
		areaSeek.addView(areaSeekBar);
		areaSeekBar.setNotifyWhileDragging(true);
		
		if(FProPrefer.with(context).getMinArea() != 0)
			areaSeekBar.setSelectedMinValue(FProPrefer.with(context).getMinArea());
		else
			FProPrefer.with(context).setMinArea(5);

		if(FProPrefer.with(context).getMaxArea()  != 0)
			areaSeekBar.setSelectedMaxValue(FProPrefer.with(context).getMaxArea());
		else
			FProPrefer.with(context).setMaxArea(30);
		
		tvJeonCheckBox.setText("▣ 전용 : " + FProPrefer.with(context).getMinArea() + " ~ " + FProPrefer.with(context).getMaxArea());
		
		areaSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
				tvJeonCheckBox.setText("▣ 전용 : " + minValue + " ~ " + maxValue);
				minArea = minValue;
				maxArea = maxValue;
				FProPrefer.with(context).setMinArea(minArea);
				FProPrefer.with(context).setMaxArea(maxArea);
			}
		});



		floorSeek = (LinearLayout) findViewById(R.id.floorSeek);
		tvFloorCheckBox = (TextView) findViewById(R.id.tvFloorCheckBox);
		RangeSeekBar<Integer> floorSeekBar = new RangeSeekBar<Integer>(0, 7, context);
		floorSeek.addView(floorSeekBar);
		floorSeekBar.setNotifyWhileDragging(true);
		
		if(FProPrefer.with(context).getMinFloor() != 0)
			floorSeekBar.setSelectedMinValue(FProPrefer.with(context).getMinFloor());
		else
			FProPrefer.with(context).setMinFloor(0);

		if(FProPrefer.with(context).getMaxFloor() != 0)
			floorSeekBar.setSelectedMaxValue(FProPrefer.with(context).getMaxFloor());
		else
			FProPrefer.with(context).setMaxFloor(7);

		tvFloorCheckBox.setText("▣ 층수 : " + FProPrefer.with(context).getMinFloor() + " ~ " + FProPrefer.with(context).getMaxFloor());
		floorSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

				tvFloorCheckBox.setText("▣ 층수 : " + minValue + " ~ " + maxValue);
				minFloor = minValue;
				maxFloor = maxValue;
				FProPrefer.with(context).setMinFloor(minFloor);
				FProPrefer.with(context).setMaxFloor(maxFloor);
			}
		});

		roomSeek = (LinearLayout) findViewById(R.id.roomSeek);
		tvRoomCheckBox = (TextView) findViewById(R.id.tvRoomCheckBox);
		RangeSeekBar<Integer> roomSeekBar = new RangeSeekBar<Integer>(1, 5, context);
		roomSeek.addView(roomSeekBar);
		roomSeekBar.setNotifyWhileDragging(true);
		
		if(FProPrefer.with(context).getMinRoom() != 0)
			roomSeekBar.setSelectedMinValue(FProPrefer.with(context).getMinRoom());
		else
			FProPrefer.with(context).setMinRoom(1);

		if(FProPrefer.with(context).getMaxRoom() != 0)
			roomSeekBar.setSelectedMaxValue(FProPrefer.with(context).getMaxRoom());
		else
			FProPrefer.with(context).setMaxRoom(5);
		tvRoomCheckBox.setText("▣ 방수 : " + FProPrefer.with(context).getMinRoom() + " ~ " + FProPrefer.with(context).getMaxRoom());
		roomSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

				tvRoomCheckBox.setText("▣ 방수 : " + minValue + " ~ " + maxValue);
				minRoom = minValue;
				maxRoom = maxValue;
				FProPrefer.with(context).setMinRoom(minRoom);
				FProPrefer.with(context).setMaxRoom(maxRoom);
			}
		});

		yearSeek = (LinearLayout) findViewById(R.id.yearSeek);
		tvYearCheckBox = (TextView) findViewById(R.id.tvYearCheckBox);
		RangeSeekBar<Integer> yearSeekBar = new RangeSeekBar<Integer>(1985, Util.getCurYear(), context);
		yearSeek.addView(yearSeekBar);
		yearSeekBar.setNotifyWhileDragging(true);

		if(FProPrefer.with(context).getMinYear() != 0)
			yearSeekBar.setSelectedMinValue(FProPrefer.with(context).getMinYear());
		else
			FProPrefer.with(context).setMinYear(1985);

		if(FProPrefer.with(context).getMaxYear() != 0)
			yearSeekBar.setSelectedMaxValue(FProPrefer.with(context).getMaxYear());
		else
			FProPrefer.with(context).setMaxYear(Util.getCurYear());

		tvYearCheckBox.setText("▣ 년식 : " + FProPrefer.with(context).getMinYear() + " ~ " + FProPrefer.with(context).getMaxYear());
		yearSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

				tvYearCheckBox.setText("▣ 년식 : " + minValue + " ~ " + maxValue);
				minYear = minValue;
				maxYear = maxValue;
				FProPrefer.with(context).setMinYear(minYear);
				FProPrefer.with(context).setMaxYear(maxYear);

			}
		});

	}

	private void startAction_onCreate() {

		// DODO 검색에서 확인 버튼
		btnRangeConfirm.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				HashMap<String, String> detailMap = new HashMap<String, String>();
				detailMap.put("sido", addressMap.get("sido").toString());
				detailMap.put("gungu", addressMap.get("gungu").toString());
				detailMap.put("dong", addressMap.get("dong").toString());

				detailMap.put("ma_bo_ney1", String.valueOf(FProPrefer.with(context).getMinInputBo()*100));
				detailMap.put("ma_bo_ney2", String.valueOf(FProPrefer.with(context).getMaxInputBo()*1000));
				detailMap.put("ma_month_ney1", String.valueOf(FProPrefer.with(context).getMinInputMonth()*100));
				detailMap.put("ma_month_ney2", String.valueOf(FProPrefer.with(context).getMaxInputMonth()*100));

				detailMap.put("ma_jeon_area1", String.valueOf(FProPrefer.with(context).getMinArea()));
				detailMap.put("ma_jeon_area2", String.valueOf(FProPrefer.with(context).getMaxArea()));
				detailMap.put("ma_level1", String.valueOf(FProPrefer.with(context).getMinFloor()));
				detailMap.put("ma_level2", String.valueOf(FProPrefer.with(context).getMaxFloor()));
				detailMap.put("ma_room1", String.valueOf(FProPrefer.with(context).getMinRoom()));
				detailMap.put("ma_room2", String.valueOf(FProPrefer.with(context).getMaxRoom()));
				detailMap.put("ma_jun_year1", String.valueOf(FProPrefer.with(context).getMinYear()));
				detailMap.put("ma_jun_year2", String.valueOf(FProPrefer.with(context).getMaxYear()));

				// DODO 빌라면,메모,전화번호로 검색

				if(searchText.getText().toString().equals("빌라명,메모,전화번호로 검색")){
					detailMap.put("ma_bld_nm", "");
					FProPrefer.with(context).setSearchText("");
				}else{
					detailMap.put("ma_bld_nm", searchText.getText().toString());
					FProPrefer.with(context).setSearchText(searchText.getText().toString());
				}

				// 선택 조회 상태 저장
				FProPrefer.with(context).setChoiceMaSearch("");
				FProPrefer.with(context).setSearchAction("Search");

				if (mCallback != null) {
					mCallback.onResponse(detailMap);
				}

				// 전역변수 프린트
				Util.printFProPrefer(activity, "OptionRangeSelectDialog.class:btnRangeConfirm");

				dismiss();
			}

		});

		// DODO 검색에서 초기화버튼
		btnRangeClear.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				HashMap<String, String> detailMap = new HashMap<String, String>();

				detailMap.put("sido", addressMap.get("sido").toString());
				detailMap.put("gungu", addressMap.get("gungu").toString());
				detailMap.put("dong", addressMap.get("dong").toString());

				FProPrefer.with(activity).setSearchText("");
				detailMap.put("ma_bld_nm", "");

				FProPrefer.with(activity).setMinInputBo(0);
				FProPrefer.with(activity).setMaxInputBo(0);
				detailMap.put("ma_bo_ney1", "");
				detailMap.put("ma_bo_ney2", "");

				FProPrefer.with(activity).setMinInputMonth(0);
				FProPrefer.with(activity).setMaxInputMonth(0);
				detailMap.put("ma_month_ney1", "");
				detailMap.put("ma_month_ney2", "");

				FProPrefer.with(activity).setMinArea(0);
				FProPrefer.with(activity).setMaxArea(0);
				detailMap.put("ma_jeon_area1", "");
				detailMap.put("ma_jeon_area2", "");

				FProPrefer.with(activity).setMinFloor(0);
				FProPrefer.with(activity).setMaxFloor(0);
				detailMap.put("ma_level1", "");
				detailMap.put("ma_level2", "");

				FProPrefer.with(activity).setMinRoom(0);
				FProPrefer.with(activity).setMaxRoom(0);
				detailMap.put("ma_room1", "");
				detailMap.put("ma_room2", "");

				FProPrefer.with(activity).setMinYear(0);
				FProPrefer.with(activity).setMaxYear(0);
				detailMap.put("ma_jun_year1", "");
				detailMap.put("ma_jun_year2", "");

				// 초기화 이면 조회 Actioon을 초기화 한다.
				FProPrefer.with(activity).setSearchAction("");

				if (mCallback != null) {
					mCallback.onResponse(detailMap);
				}

				dismiss();

			}
		});

		// DODO 검색에서 닫기버튼
		btnRangeCancel.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		btnDialogClose.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}
	public OptionRangeSelectDialog forStart(Callback OptionSelectDialog_Callback) {
		mCallback = OptionSelectDialog_Callback;
		return this;
	}
	
	public static interface Callback {
		void onResponse(HashMap<String, String> detailMap);
	}


}
