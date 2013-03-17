package de.inue.tkplaner;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class GameProcessActivity extends Activity {
	
	private String[] playedCombinations;
	private ArrayList<String> allPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameprocess);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		this.allPlayers = intent.getStringArrayListExtra(InitActivity.EXTRA_NAME);
		System.out.println("Got " + this.allPlayers.size() + "players");
		
	}
	
	/**
	 * Callback button to choose a pair of players 
	 */
	public void choosePair(View view){
		FragmentManager fm = getFragmentManager();
        PairChoiceDialog choiceDialog = new PairChoiceDialog();
        
        choiceDialog.show(fm, null);
	}
	
	/*
	 * Didn't change anything in this generated method 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gameprocess, menu);
		return true;
	}

	/*
	 * Didn't change anything in this generated method 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
