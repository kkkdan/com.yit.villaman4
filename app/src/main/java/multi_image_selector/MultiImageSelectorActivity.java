package multi_image_selector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yit.villaman4.MainActivity;
import com.yit.villaman4.R;
import com.yit.villaman4.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

	public static final String EXTRA_SELECT_COUNT = "max_select_count";
	public static final String EXTRA_SELECT_MODE = "select_count_mode";
	public static final String EXTRA_SHOW_CAMERA = "show_camera";
	public static final String EXTRA_RESULT = "select_result";
	public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

	public static final int MODE_SINGLE = 0;
	public static final int MODE_MULTI = 1;

	private ArrayList<String> resultList = new ArrayList<String>();
	private Button mSubmitButton;
	private int mDefaultCount;


	private MainActivity activity;
	private static final String __TAG__ = "MultiImageSel♥♥:";

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_default);



		Intent intent = getIntent();
		mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 19);
		int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
		boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
		if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
			resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
		}

		Bundle bundle = new Bundle();
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
		bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
		bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
		bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

		getSupportFragmentManager().beginTransaction().add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle)).commit();

		findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		mSubmitButton = (Button) findViewById(R.id.btn_commit);
		if (resultList == null || resultList.size() <= 0) {
			mSubmitButton.setText("선택");
			mSubmitButton.setEnabled(false);
		} else {
			mSubmitButton.setText("선택(" + resultList.size() + "/" + mDefaultCount + ")");
			mSubmitButton.setEnabled(true);
		}

		mDialog = new ProgressDialog(MultiImageSelectorActivity.this);
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setMessage("업로드 중입니다..");

		// 선택 버튼 클릭
		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {


				if (resultList != null && resultList.size() > 0) {
					if (!mDialog.isShowing()) {
						mDialog.show();
					}
					new Thread(imageThread).start();
				}
			}
		});



		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
	}

	private ProgressDialog mDialog;
	private Handler mHandler = new Handler();

	// 사진 업로드를 시작한다.
	private Runnable imageThread = new Runnable() {
		public void run() {
			File file1 = null, file2 = null, file3 = null, file4 = null, file5 = null, file6 = null, file7 = null, file8 = null, file9 = null, file10 = null;
			File file11 = null, file12 = null, file13 = null, file14 = null, file15 = null, file16 = null, file17 = null, file18 = null, file19 = null, file20 = null;
			String strPath = "";
			String ext = "";

			// strPath = Environment.getExternalStorageDirectory().toString();
			strPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/";


			for (int i = 0; i < resultList.size(); i++) {
				if (resultList.get(i).lastIndexOf(".mp4")>0){
					ext = ".mp4";
				}else {
					ext = ".jpg";
				}
				if (i == 0) {	file1 = new File(strPath, "1"+ext); imageResize(resultList.get(i), file1.getPath());
				} else if (i == 1) {	file2 = new File(strPath, "2"+ext);	imageResize(resultList.get(i), file2.getPath());
				} else if (i == 2) {	file3 = new File(strPath, "3"+ext);	imageResize(resultList.get(i), file3.getPath());
				} else if (i == 3) {	file4 = new File(strPath, "4"+ext);	imageResize(resultList.get(i), file4.getPath());
				} else if (i == 4) {	file5 = new File(strPath, "5"+ext);	imageResize(resultList.get(i), file5.getPath());
				} else if (i == 5) {	file6 = new File(strPath, "6"+ext);	imageResize(resultList.get(i), file6.getPath());
				} else if (i == 6) {	file7 = new File(strPath, "7"+ext);	imageResize(resultList.get(i), file7.getPath());
				} else if (i == 7) {	file8 = new File(strPath, "8"+ext);	imageResize(resultList.get(i), file8.getPath());
				} else if (i == 8) {	file9 = new File(strPath, "9"+ext);	imageResize(resultList.get(i), file9.getPath());
				} else if (i == 9) {	file10 = new File(strPath, "10"+ext);	imageResize(resultList.get(i), file10.getPath());
				} else if (i == 10) {	file11 = new File(strPath, "11"+ext);	imageResize(resultList.get(i), file11.getPath());
				} else if (i == 11) {	file12 = new File(strPath, "12"+ext);	imageResize(resultList.get(i), file12.getPath());
				} else if (i == 12) {	file13 = new File(strPath, "13"+ext);	imageResize(resultList.get(i), file13.getPath());
				} else if (i == 13) {	file14 = new File(strPath, "14"+ext);	imageResize(resultList.get(i), file14.getPath());
				} else if (i == 14) {	file15 = new File(strPath, "15"+ext);	imageResize(resultList.get(i), file15.getPath());
				} else if (i == 15) {	file16 = new File(strPath, "16"+ext);	imageResize(resultList.get(i), file16.getPath());
				} else if (i == 16) {	file17 = new File(strPath, "17"+ext);	imageResize(resultList.get(i), file17.getPath());
				} else if (i == 17) {	file18 = new File(strPath, "18"+ext);	imageResize(resultList.get(i), file18.getPath());
				} else if (i == 18) {	file19 = new File(strPath, "19"+ext);	imageResize(resultList.get(i), file19.getPath());
				} else if (i == 19) {	file20 = new File(strPath, "20"+ext);	imageResize(resultList.get(i), file20.getPath());
				}
			}

			modProfileImgService(Util.getPhoneNumber(MultiImageSelectorActivity.this), getIntent().getStringExtra("main_id"), getIntent().getStringExtra("gubun"),
					file1, file2, file3, file4, file5, file6, file7, file8, file9, file10,
					file11, file12, file13, file14, file15, file16, file17, file18, file19, file20);
		}

		private void modProfileImgService(String usr_id, String main_id, String gubun, File upfile1, File upfile2, File upfile3, File upfile4, File upfile5, File upfile6, File upfile7, File upfile8, File upfile9, File upfile10,
										  File upfile11, File upfile12, File upfile13, File upfile14, File upfile15, File upfile16, File upfile17, File upfile18, File upfile19, File upfile20) {
			String type = "image/jpeg";

			OkHttpClient client = getClient();
			MultipartBody.Builder builder = new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);
			builder.addFormDataPart("usr_id", usr_id);
			builder.addFormDataPart("main_id", main_id);
			builder.addFormDataPart("gubun", gubun);
			builder.addFormDataPart("cd_code", "CG020CD722"); // 결제관리, 사진등록

			if (upfile1 != null) {	builder.addFormDataPart("upfile1",  upfile1.getName(), RequestBody.create(MediaType.parse(type),  upfile1));	}
			if (upfile2 != null) {	builder.addFormDataPart("upfile2",  upfile2.getName(), RequestBody.create(MediaType.parse(type),  upfile2));	}
			if (upfile3 != null) {	builder.addFormDataPart("upfile3",  upfile3.getName(), RequestBody.create(MediaType.parse(type),  upfile3));	}
			if (upfile4 != null) {	builder.addFormDataPart("upfile4",  upfile4.getName(), RequestBody.create(MediaType.parse(type),  upfile4));	}
			if (upfile5 != null) {	builder.addFormDataPart("upfile5",  upfile5.getName(), RequestBody.create(MediaType.parse(type),  upfile5));	}
			if (upfile6 != null) {	builder.addFormDataPart("upfile6",  upfile6.getName(), RequestBody.create(MediaType.parse(type),  upfile6));	}
			if (upfile7 != null) {	builder.addFormDataPart("upfile7",  upfile7.getName(), RequestBody.create(MediaType.parse(type),  upfile7));	}
			if (upfile8 != null) {	builder.addFormDataPart("upfile8",  upfile8.getName(), RequestBody.create(MediaType.parse(type),  upfile8));	}
			if (upfile9 != null) {	builder.addFormDataPart("upfile9",  upfile9.getName(), RequestBody.create(MediaType.parse(type),  upfile9));	}
			if (upfile10 != null) {builder.addFormDataPart("upfile10", upfile10.getName(), RequestBody.create(MediaType.parse(type), upfile10));	}
			if (upfile11 != null) {builder.addFormDataPart("upfile11", upfile11.getName(), RequestBody.create(MediaType.parse(type), upfile11));	}
			if (upfile12 != null) {builder.addFormDataPart("upfile12", upfile12.getName(), RequestBody.create(MediaType.parse(type), upfile12));	}
			if (upfile13 != null) {builder.addFormDataPart("upfile13", upfile13.getName(), RequestBody.create(MediaType.parse(type), upfile13));	}
			if (upfile14 != null) {builder.addFormDataPart("upfile14", upfile14.getName(), RequestBody.create(MediaType.parse(type), upfile14));	}
			if (upfile15 != null) {builder.addFormDataPart("upfile15", upfile15.getName(), RequestBody.create(MediaType.parse(type), upfile15));	}
			if (upfile16 != null) {builder.addFormDataPart("upfile16", upfile16.getName(), RequestBody.create(MediaType.parse(type), upfile16));	}
			if (upfile17 != null) {builder.addFormDataPart("upfile17", upfile17.getName(), RequestBody.create(MediaType.parse(type), upfile17));	}
			if (upfile18 != null) {builder.addFormDataPart("upfile18", upfile18.getName(), RequestBody.create(MediaType.parse(type), upfile18));	}
			if (upfile19 != null) {builder.addFormDataPart("upfile19", upfile19.getName(), RequestBody.create(MediaType.parse(type), upfile19));	}
			if (upfile20 != null) {builder.addFormDataPart("upfile20", upfile20.getName(), RequestBody.create(MediaType.parse(type), upfile20));	}

			RequestBody body = builder.build();
			Request request = new Request.Builder().url(Util.getURL_IT() + "/app/saveGallery.do").post(body).build(); // TODO

			try {

				Response response = client.newCall(request).execute();
				if (response.isSuccessful()) {
					if (upfile1  != null)  {	upfile1.delete(); }
					if (upfile2  != null)  {	upfile2.delete(); }
					if (upfile3  != null)  {	upfile3.delete(); }
					if (upfile4  != null)  {	upfile4.delete(); }
					if (upfile5  != null)  {	upfile5.delete(); }
					if (upfile6  != null)  {	upfile6.delete(); }
					if (upfile7  != null)  {	upfile7.delete(); }
					if (upfile8  != null)  {	upfile8.delete(); }
					if (upfile9  != null)  {	upfile9.delete(); }
					if (upfile10 != null)  {	upfile10.delete();}
					if (upfile11 != null)  {	upfile11.delete(); }
					if (upfile12 != null)  {	upfile12.delete(); }
					if (upfile13 != null)  {	upfile13.delete(); }
					if (upfile14 != null)  {	upfile14.delete(); }
					if (upfile15 != null)  {	upfile15.delete(); }
					if (upfile16 != null)  {	upfile16.delete(); }
					if (upfile17 != null)  {	upfile17.delete(); }
					if (upfile18 != null)  {	upfile18.delete(); }
					if (upfile19 != null)  {	upfile19.delete(); }
					if (upfile20 != null) {	upfile20.delete(); }

					mHandler.post(new Runnable() {
						public void run() {
							mDialog.dismiss();
							Intent intent = getIntent();
							intent.putStringArrayListExtra(EXTRA_RESULT, resultList);
							intent.putExtra("result_msg", "정상적으로 이미지를 저장하였습니다.");
							setResult(RESULT_OK, intent); // 호출한 곳 MainActivity.startActivityForResult에 값을 넘긴다.
							finish();
						}
					});
				} else {
					throw new Exception(response.message());
				}
			} catch (final Exception ex) {
				mHandler.post(new Runnable() {
					public void run() {
						mDialog.dismiss();
						Toast.makeText(MultiImageSelectorActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
		}

		private OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
			final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					X509Certificate[] x509Certificates = new X509Certificate[0];
					return x509Certificates;
				}

				public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
				}

				public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
				}
			}};

			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(null, certs, new SecureRandom());
			} catch (final GeneralSecurityException ex) {
				ex.printStackTrace();
			}
			try {
				final HostnameVerifier hostnameVerifier = new HostnameVerifier() {

					public boolean verify(final String hostname, final SSLSession session) {
						return true;
					}
				};

				builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
			} catch (final Exception e) {
				e.printStackTrace();
			}

			return builder;
		}

		private OkHttpClient getClient() {
			return configureClient(new OkHttpClient().newBuilder()).connectTimeout(10, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
		}


		// 이미지/동영상 처리를 한다.
		private void imageResize(String path, String finalPath) {
			int MAX_IMAGE_SIZE = 500 * 1024;
			Bitmap bmpPic = BitmapFactory.decodeFile(path);

			System.out.println("path=" + path);
			System.out.println("finalPath=" + finalPath);

		    bmpPic = rotateBitmap(bmpPic, exifOrientationToDegrees(finalPath));

			int compressQuality = 102;  // 102
			int streamLength = MAX_IMAGE_SIZE;

			if (bmpPic != null) { // 이미지
				if (bmpPic.getWidth() > 1024 || bmpPic.getHeight() > 1024) {
					BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
					bmpOptions.inSampleSize = 1
					; // 1
					while (bmpPic.getWidth() > 1024 || bmpPic.getHeight() > 1024) {
						bmpOptions.inSampleSize++;
						bmpPic = BitmapFactory.decodeFile(path, bmpOptions);
					}
				}

				while (streamLength >= MAX_IMAGE_SIZE) {
					ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
					compressQuality -= 2; // 2
					bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
					byte[] bmpPicByteArray = bmpStream.toByteArray();
					streamLength = bmpPicByteArray.length;
				}

				try {
					FileOutputStream bmpFile = new FileOutputStream(finalPath);

					bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
					bmpFile.flush();
					bmpFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}


			} else {


				try {

                    File file = new File(path);

                    FileInputStream fin = new FileInputStream(file);
                    byte b[] = new byte[(int)file.length()];
                    fin.read(b);

                    File nf = new File(finalPath);
                    FileOutputStream fw = new FileOutputStream(nf);
                    fw.write(b);
                    fw.flush();
                    fw.close();

				}
				catch(Exception e) {
					System.out.println("Something went wrong! Reason: " + e.getMessage());
				}
			}
		}
	};

	private int exifOrientationToDegrees(String filepath) {
		int degree = 0;
		ExifInterface exif = null;

		try {
			exif = new ExifInterface(filepath);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		if(exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

			if(orientation != -1) {
				switch(orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90 :
						degree = 90;
						break;

					case ExifInterface.ORIENTATION_ROTATE_180 :
						degree = 180;
						break;

					case ExifInterface.ORIENTATION_ROTATE_270 :
						degree = 270;
						break;
				}
			}
		}
		return degree;
	}

	private Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
		if(degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

			try {
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
				if(bitmap != converted) {
					bitmap.recycle();
					bitmap = converted;
				}
			}
			catch(OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void onSingleImageSelected(String path) {
		Intent data = new Intent();
		resultList.add(path);
		data.putStringArrayListExtra(EXTRA_RESULT, resultList);
		setResult(RESULT_OK, data);
		finish();
	}

	public void onImageSelected(String path) {
		if (!resultList.contains(path)) {
			resultList.add(path);
		}
		if (resultList.size() > 0) {
			mSubmitButton.setText("선택(" + resultList.size() + "/" + mDefaultCount + ")");
			if (!mSubmitButton.isEnabled()) {
				mSubmitButton.setEnabled(true);
			}
		}
	}

	public void onImageUnselected(String path) {
		if (resultList.contains(path)) {
			resultList.remove(path);
			mSubmitButton.setText("선택(" + resultList.size() + "/" + mDefaultCount + ")");
		} else {
			mSubmitButton.setText("선택(" + resultList.size() + "/" + mDefaultCount + ")");
		}
		if (resultList.size() == 0) {
			mSubmitButton.setText("선택");
			mSubmitButton.setEnabled(false);
		}
	}

	public void onCameraShot(File imageFile) {
		if (imageFile != null) {
			Intent data = new Intent();
			resultList.add(imageFile.getAbsolutePath());
			data.putStringArrayListExtra(EXTRA_RESULT, resultList);
			setResult(RESULT_OK, data);
			finish();
		}
	}

}
