package com.yit.villaman4;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PPreference {
	public Context pCon;
	public SharedPreferences pSharedPref;

	public PPreference(Context pCon) {
		this.pCon = pCon;
		pSharedPref = PreferenceManager.getDefaultSharedPreferences(pCon);
	}

	public PPreference(Context pCon, String name) {
		this.pCon = pCon;
		pSharedPref = pCon.getSharedPreferences(name, 0);
	}

	public String read(String key) {
		return pSharedPref.getString(key, "");
	}

	public String read(String key, String defaultString) {
		return pSharedPref.getString(key, defaultString);
	}

	public void write(String key, String value) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putString(key, value);
		sharedEditor.commit();
	}

	public void write_list(String key, ArrayList<String> strList) {
		String value = null;
		if (strList != null) {
			value = "";
			for (int i = 0; i < strList.size(); i++) {
				if (i == strList.size() - 1) {
					value = value + strList.get(i);
				} else {
					value = value + strList.get(i) + "####";
				}
			}
		}
		write(key, value);
	}

	public int read(String key, int initInt) {
		return pSharedPref.getInt(key, initInt);
	}

	public void write(String key, int value) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putInt(key, value);
		sharedEditor.commit();
	}

	public boolean read(String key, boolean flag) {
		return pSharedPref.getBoolean(key, flag);
	}

	public void write(String key, boolean flag) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putBoolean(key, flag);
		sharedEditor.commit();
	}

	public float read(String key, float value) {
		return pSharedPref.getFloat(key, value);
	}

	public void write(String key, float value) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putFloat(key, value);
		sharedEditor.commit();
	}

	public double read(String key, double value) {
		return pSharedPref.getFloat(key, (float) value);
	}

	public void write(String key, double value) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putFloat(key, (float) value);
		sharedEditor.commit();
	}

	public long read(String key, long value) {
		return pSharedPref.getLong(key, value);
	}

	public void write(String key, long value) {
		SharedPreferences.Editor sharedEditor = pSharedPref.edit();
		sharedEditor.putLong(key, value);
		sharedEditor.commit();
	}

	public void reverseBoolean(String key, boolean defaultBool) {
		if (read(key, defaultBool)) {
			write(key, false);
		} else {
			write(key, true);
		}
	}
}
