package com.yit.villaman4;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

public class OptionLoadingDialog {

	private Dialog loadingDialog;
	private Context con;
	
	public OptionLoadingDialog(Context con){
		this.con = con;
	}
	
	public void showLoading() {
		if (loadingDialog == null) {
			loadingDialog = new Dialog(con, R.style.TransDialog);
			ProgressBar pb = new ProgressBar(con);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			loadingDialog.addContentView(pb, params);
			loadingDialog.setCancelable(false);
		}

		loadingDialog.show();
	}
	
	public void showLoadingCancel() {
		if (loadingDialog == null) {
			loadingDialog = new Dialog(con, R.style.TransDialog);
			ProgressBar pb = new ProgressBar(con);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			loadingDialog.addContentView(pb, params);
			loadingDialog.setCancelable(false);
		}

		loadingDialog.show();
	}

	public void hideLoading() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}
}
