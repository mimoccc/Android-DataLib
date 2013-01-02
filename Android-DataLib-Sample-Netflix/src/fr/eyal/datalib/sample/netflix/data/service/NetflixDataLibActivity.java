package fr.eyal.datalib.sample.netflix.data.service;

import java.util.ArrayList;

import fr.eyal.lib.data.service.DataManager.OnDataListener;

import android.app.Activity;
import android.os.Bundle;


public abstract class NetflixDataLibActivity extends Activity implements OnDataListener {

	/**
	 * NetflixDataManager of the project
	 */
	protected NetflixDataManager mDataManager;

	/**
	 * List of requests currently running for this activity
	 */
	protected ArrayList<Integer> mRequestIds;

	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		
		mRequestIds = new ArrayList<Integer>();
		mDataManager = NetflixDataManager.getInstance(this);
		
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		// we launch the request's data receiving
		for (final int requestId : mRequestIds) {
			mDataManager.addOnDataListener(requestId, this);
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// we stop the request's data receiving
		for (final int requestId : mRequestIds) {
			mDataManager.removeOnDataListener(requestId, this);
		}
		super.onPause();
	}
	
}
