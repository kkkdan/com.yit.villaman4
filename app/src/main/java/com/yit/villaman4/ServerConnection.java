package com.yit.villaman4;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.os.Handler;

public class ServerConnection extends Thread {

	private static final String __TAG__ = "ServerConnection♥♥";

	private Context		pCon;
	public Handler		handler;
	private String		url;
	private String		method;
	private JSONArray	jarr;

	private OutputStream	os;
	private InputStream		is;

	private HttpURLConnection	conn;
	private Map<String, String>	headers;

	private int iReadTime = 10000; // 10000;
	private int iConnTime = 15000; // 15000;


	//폰번호로 로그인하고 c9521077#

	public ServerConnection(Context pCon, String method, Handler handler, String url) {
		this.pCon = pCon;
		this.method = method;
		this.handler = handler;
		this.url = url;
	}

	@Override
	public void run() {
		super.run();

		try {
			URL conURL = new URL(url);

			System.setProperty("http.keepAlive", "false");

			conn = (HttpURLConnection) conURL.openConnection();
			conn.setReadTimeout(iReadTime);
			conn.setConnectTimeout(iConnTime);
			conn.setRequestMethod(method);

			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");

			if(method.equals("POST")) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
			}
			else {
				conn.setDoOutput(false);
			}

			conn.connect();
			if(conn.getResponseCode() == 200) {
				String str = new String(readResponse(conn.getInputStream()), "UTF-8");

				if (str == null || str.equals(""))
				{} else {


					JSONArray responseJson = new JSONArray(str);
					handler.sendMessage(handler.obtainMessage(1, responseJson));
				}
			}
			else {
				handler.sendMessage(handler.obtainMessage(-1, null));
			}


		}
		catch(SocketTimeoutException e) {

			e.printStackTrace();
//			Toast.makeText(pCon, "연결 시간이 경과 되었습니다.", Toast.LENGTH_SHORT).show();

			handler.sendMessage(handler.obtainMessage(-1, null));

		}
		catch(Exception e) {

//			Toast.makeText(pCon, "잠시후 다시 실행 바랍니다.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();

			// handler.sendMessage(handler.obtainMessage(-1, null));
		}
		finally {
			if(conn != null) conn.disconnect();
		}
	}

	private byte[] readResponse(InputStream in) {
		byte[] byteData = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] byteBuffer = new byte[1024];

			int nLength = 0;

			while((nLength = in.read(byteBuffer, 0, byteBuffer.length)) != -1) {
				baos.write(byteBuffer, 0, nLength);
			}

			byteData = baos.toByteArray();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return byteData;
	}

}
