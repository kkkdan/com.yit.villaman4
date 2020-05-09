package com.yit.villaman4;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class PictureActivity extends AppCompatActivity {

	private static final String __TAG__ = "PictureActivity♥♥";

	private MainActivity			activity;
	private ArrayList<RGetRoomEnty>	adapterList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);

		findViewById(R.id.viewBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		setView();
		getData();
	}

	private ListView					mListView;
	private List<RGetPictureListObj>	mListViewItems	= new ArrayList<RGetPictureListObj>();
	private ImageAdapter				mListViewAdapter;

	private void setView() {
		mListView = (ListView) findViewById(R.id.listview);
		mListViewAdapter = new ImageAdapter(this, R.layout.activity_picture_item, mListViewItems);
		mListView.setAdapter(mListViewAdapter);
	}

	public void btnListener(View v) {
		switch(v.getId()) {
			case R.id.btn_delete :

				// AlertDialog.Builder alt_bld = new AlertDialog.Builder(activity);

				/*
				alt_bld.setMessage("해당 매물에 대한 전체 이미지를 삭제 하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					getDeleteData();
					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});
				*/

				/*
								alt_bld.setTitle("Title");
								alt_bld.setMessage("Message.....");
				
				
								alt_bld.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
				
											public void onClick(DialogInterface dialog, int which) {
												getDeleteData();
												//Toast.makeText(getApplicationContext(),
												//		"Ok Button is Clicked", 0).show();
				
											}
										});
								alt_bld.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
				
											public void onClick(DialogInterface dialog, int which) {
				
												//Toast.makeText(getApplicationContext(),
												//		"Cancel Button is Clicked", 0).show();
											}
										});
								alt_bld.show();
				*/
				//alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				/*
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Title");
				alertDialog.setMessage("Message");
				//alertDialog.setView(input, 10, 0, 10, 0); // 10 spacing, left and right
				alertDialog.setButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getDeleteData();
						// Clicked
					}
				});
				alertDialog.show();
				*/

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
				alertDialog.setTitle("▶ 알림");
				alertDialog.setMessage("삭제하시겠습니까?");
				alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getDeleteData();
					}
				});
				alertDialog.show();
				break;

			case R.id.btn_close :
				finish();
				break;
		}
	}

	private void getData() {
		mListViewItems.clear();
		UServerPictureList.with(PictureActivity.this).forStart(new UServerPictureList.WServerPictureList.Callback() {
			@Override
			public void onResult(JSONArray json) {
				if(json.length() > 0) {
					for(int i = 0; i < json.length(); i++) {
						try {
							RGetPictureListObj item = new RGetPictureListObj();
							item.setOrg_file_name(json.getJSONObject(i).getString("org_file_name"));
							item.setFile_dir(json.getJSONObject(i).getString("file_dir"));
							item.setFile_path(json.getJSONObject(i).getString("file_path"));
							item.setRgst_id(json.getJSONObject(i).getString("rgst_id"));
							item.setID(json.getJSONObject(i).getString("ID"));
							item.setFile_name(json.getJSONObject(i).getString("file_name"));
							item.setMain_id(json.getJSONObject(i).getString("main_id"));
							item.setRgst_dt(json.getJSONObject(i).getString("rgst_dt"));
							mListViewItems.add(item);
						}
						catch(Exception ex) {}
					}
				}

				for(int i = 0; i < mListViewItems.size(); i++) {
					if(Util.getPhoneNumber(PictureActivity.this).equals(mListViewItems.get(i).getRgst_id())) {
						findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
						break;
					}
				}
				mListViewAdapter.notifyDataSetChanged();
			}
		}, getIntent().getStringExtra("main_id"));
	}

	private void getDeleteData() {
		mListViewItems.clear();
		UServerPictureDelete.with(PictureActivity.this).forStart(new UServerPictureDelete.WServerPictureDelete.Callback() {
			@Override
			public void onResult(JSONArray json) {

				setResult(RESULT_OK, getIntent());
				finish();
			}
		}, getIntent().getStringExtra("main_id"));
	}

	class ImageAdapter extends ArrayAdapter<RGetPictureListObj> {

		public ImageAdapter(Context context, int resource, List<RGetPictureListObj> objects) {
			super(context, resource, objects);
		}

		public View getView(int position, View view, ViewGroup parent) {
			if(view == null) {
				view = LayoutInflater.from(PictureActivity.this).inflate(R.layout.activity_picture_item, null);
			}
			ImageView imgView = (ImageView) view.findViewById(R.id.item_imageview);
			Glide.with(PictureActivity.this).load(Util.getURL_IT() + "/" + mListViewItems.get(position).getFile_path() + "/" + mListViewItems.get(position).getFile_dir() + "/" + mListViewItems.get(position).getFile_name()).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Object, GlideDrawable>() {
				public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
					if(e != null) {
						e.printStackTrace();
					}
					return false;
				}

				public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
					return false;
				}
			}).fitCenter().into(imgView);
			return view;
		}

	}

}