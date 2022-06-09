package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.databinding.ActivityLoginBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityTimeLineBinding;
import com.codepath.apps.restclienttemplate.models.SampleModel;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	SampleModelDao sampleModelDao;
	public ActivityLoginBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLoginBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		final SampleModel sampleModel = new SampleModel();
		sampleModel.setName("CodePath");

		sampleModelDao = ((TwitterApp) getApplicationContext()).getMyDatabase().sampleModelDao();

		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				sampleModelDao.insertModel(sampleModel);
			}
		});
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		Log.i("betram", "log in successful");
		Intent intent = new Intent(this, TimeLineActivity.class);
		startActivity(intent);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		System.out.println("here 2");
		e.printStackTrace();
		Log.i("betramf", "log in FAILED");
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		System.out.println("here");
		getClient().connect();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}
