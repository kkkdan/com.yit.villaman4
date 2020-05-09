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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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


public class MultiImageNameCardActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

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
	private static final String __TAG__ = "MultiImageName♥♥:";

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

		mDialog = new ProgressDialog(MultiImageNameCardActivity.this);
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
			File file1 = null, file2 = null;
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

				if (i == 0) {
					file1 = new File(strPath, "1"+ext);
					imageResize(resultList.get(i), file1.getPath());
				} else if (i == 1) {
					file2 = new File(strPath, "2"+ext);
					imageResize(resultList.get(i), file2.getPath());
				}
			}

			modProfileImgService(Util.getPhoneNumber(MultiImageNameCardActivity.this), file1, file2);
		}

		private void modProfileImgService(String usr_id, File upfile1, File upfile2) {
			String type = "image/jpeg";

			OkHttpClient client = getClient();
			MultipartBody.Builder builder = new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);
			builder.addFormDataPart("usr_id", usr_id);
			// builder.addFormDataPart("main_id", main_id);
			//builder.addFormDataPart("gubun", gubun);
			builder.addFormDataPart("cd_code", "CG023CD001"); // 관리자-명함등록

			if (upfile1 != null) {	builder.addFormDataPart("upfile1",  upfile1.getName(), RequestBody.create(MediaType.parse(type),  upfile1));	}
			if (upfile2 != null) {	builder.addFormDataPart("upfile2",  upfile2.getName(), RequestBody.create(MediaType.parse(type),  upfile2));	}

			RequestBody body = builder.build();
			Request request = new Request.Builder().url(Util.getURL_IT() + "/app/saveNameCard.do").post(body).build(); // TODO

			try {

				Response response = client.newCall(request).execute();
				if (response.isSuccessful()) {
					if (upfile1  != null)  {	upfile1.delete(); }
					if (upfile2  != null)  {	upfile2.delete(); }

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
						Toast.makeText(MultiImageNameCardActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
		}




		// 이미지/동영상 처리를 한다.
		private void imageResize(String path, String finalPath) {
			int MAX_IMAGE_SIZE = 500 * 1024;
			Bitmap bmpPic = BitmapFactory.decodeFile(path);

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


		// 파일이 존제하는지 물어 본다.
		File files = new File(filepath);
		if (files.exists() != true) {

			return 0;
		}

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

}
