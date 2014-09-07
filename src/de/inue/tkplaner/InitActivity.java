package de.inue.tkplaner;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

public class InitActivity 
	extends Activity implements RadioGroup.OnCheckedChangeListener{

	public final static String EXTRA_NAME = "de.sanyok.firstapp.PLAYERNAMES";
	
	/* 
	 * Here, no management is needed, so use list and let next activity create
	 * Player objects of it
	 */
	private ArrayList<String> players;
	private CheckBox[] activePlayers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		
		
		// initialize empty list with player names
		this.players = new ArrayList<String>();
		
		// create and fill array with all the checkboxes:
		TableLayout playersTable = (TableLayout)findViewById(R.id.players_table);
		int maxNumberPlayers = playersTable.getChildCount();
		this.activePlayers = new CheckBox[maxNumberPlayers];
		TableRow currentRow = null;
		for(int i=0; i<this.activePlayers.length; i++){
			currentRow = (TableRow)(playersTable.getChildAt(i));
			this.activePlayers[i] = (CheckBox)currentRow.getChildAt(0);
			//System.out.println("PlayerNo_" + i + ": "+ this.activePlayers[i]);
		}
		
		RadioGroup playerset = (RadioGroup) findViewById(R.id.playerset);
		playerset.setOnCheckedChangeListener(this);
		this.fillPlayerNames(playerset);
	}

		
	/** 
	 * Callback function. 
	 * Needed to initiate the game process by pressing the button
	 * */
	public void startGame(View view) {
		this.players.clear();
		// get the active player names:
		TableRow currentRow = null;
		for(int i=0; i<this.activePlayers.length; i++){
			if(this.activePlayers[i].isChecked()){
				currentRow = (TableRow)this.activePlayers[i].getParent();
				//System.out.println(""+);
				this.players.add(((EditText)currentRow.getChildAt(1)).getText().toString());
			}
		}
		if(this.players.size() < 4){
//		    System.out.println("Chosen " + this.players.size() 
//                  + " players. Need at least 4!");
			Log.w("Init", "Chosen " + this.players.size() 
					+ " players. Need at least 4!");

			Resources res = getResources();
			String message = "" + res.getString(R.string.error_not_enough_players) 
					+ " Input: " + this.players.size();

			//showErrorDialog(message);
			this.players.clear();
		}else{
			Intent intent = new Intent(this, GameProcessActivity.class);
			intent.putStringArrayListExtra(EXTRA_NAME, players);
	    	
	    	startActivity(intent);
		}
		
//		this.players;
//		this.activePlayers;
//		Intent intent = new Intent(this, GameProcessActivity.class);
	}

	
	private void fillPlayerNames(View view) {
		// Fill INUE players if layout choses this player set on startup:
		RadioGroup playerset = (RadioGroup)view;
		TableLayout playersTable = (TableLayout)findViewById(R.id.players_table);
		
		if (playerset.getCheckedRadioButtonId() == R.id.radioInue) {
			// INUE player set selected!
			System.out.println("INUE checked!");

			// here, all the constant values are stored.
			Resources res = getResources();
			String[] inuePlayerNames = res.getStringArray(R.array.inue_players);
			
			TableRow currentRow = null;
			EditText currentTextLine = null;
			for (int i = 0; (i < this.activePlayers.length); i++) {
			    currentRow = (TableRow) playersTable.getChildAt(i);
                currentTextLine = (EditText) currentRow.getChildAt(1);
			    if(i < inuePlayerNames.length){
    				
    				// fill in the name:
    				currentTextLine.setText(inuePlayerNames[i]);
			    }else{
			        Log.d("Init", "Empty player line");
			    }
				// disable editing:
				currentTextLine.setEnabled(false);
				// uncheck the box:
				this.activePlayers[i].setChecked(false);
			}
		}else{
			//Not INUE player set: clear all names, enable editing: 
			TableRow currentRow = null;
			EditText currentTextLine = null;
			for(int i = 0; i < this.activePlayers.length; i++){
				currentRow = (TableRow) playersTable.getChildAt(i);
				currentTextLine = (EditText) currentRow.getChildAt(1);
				// fill in the name:
				currentTextLine.setText("");
				// enable editing:
				currentTextLine.setEnabled(true);
				// System.out.println("PlayerNo_" + i + ": "+
				// this.activePlayers[i]);
				
				// uncheck the box:
				this.activePlayers[i].setChecked(false);
			}
			
		}
	}
	
	private void showErrorDialog(CharSequence message) {
		FragmentManager fm = getFragmentManager();
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.show(fm, null);
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        errorDialog.setMessage(message);
        
    }

	
	@Override
	/** Called when the player set radio button changes selection*/
	public void onCheckedChanged(RadioGroup playerset, int arg1) {
		this.fillPlayerNames(playerset);
	}
	
	/*
	 * Didn't change anything in this generated method 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_init, menu);
		return true;
	}
}
