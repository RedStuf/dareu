package com.phonezilla.dareu;

import com.parse.Parse;

import android.app.Application;
import android.os.Bundle;

public class dareuapp extends Application {

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate();
        Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m", "nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
	}
}
