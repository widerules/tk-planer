package de.inue.tkplaner;


import java.util.ArrayList;

import de.inue.tkplaner.PairChoiceDialog.PairDiaLogListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameProcessActivity extends Activity 
                                 implements PairDiaLogListener{
	
	//private ArrayList<String> playedCombinations;
	private ArrayList<Player> allPlayers;
	//private ArrayList<String> selectableNames;
	private int gamesPlayed = 0;
//	private int pairsChosen = 0;
	private boolean criticalGame = false;
	private ArrayList<Player> selectablePlayers;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameprocess);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		ArrayList<String> playersStringList 
				= intent.getStringArrayListExtra(InitActivity.EXTRA_NAME);
		this.allPlayers = new ArrayList<Player>();
		for(int i=0; i<playersStringList.size(); i++){
			this.allPlayers.add(new Player(playersStringList.get(i)));
		}
//		System.out.println("Got " + this.allPlayers.size() + " players");
		Log.d("GameProcess", "Got " + this.allPlayers.size() + " players");
		this.selectablePlayers = (ArrayList<Player>) allPlayers.clone();
		//this.playedCombinations = new ArrayList<String>();
		this.criticalGame = (this.allPlayers.size()==5)?true:false;
		Log.i("GameProcess", 
		        this.criticalGame?"Critical game!":"Noncritical game.");
	}
	
	/**
	 * Callback button to choose a pair of players 
	 */
	public void choosePair(View view){
		FragmentManager fm = getFragmentManager();
        PairChoiceDialog choiceDialog = 
        		PairChoiceDialog.createPairChoiceDialog(selectablePlayers);
        choiceDialog.show(fm, null);
        Log.i("GameProcess", "Dialog shown...");
	}
	
	@SuppressWarnings("unchecked")
	public void onPairChosen(boolean[] checkedPositionsLeft, 
	                         boolean[] checkedPositionsRight){
		Log.d("GameProcess", "Checked " + checkedPositionsLeft.length + " players");
		Player p1 = null, p2 = null;
//		int index1 = -1, index2 = -1;
		for(int i = 0; i < checkedPositionsLeft.length; i++){
			if(checkedPositionsLeft[i]){
				if(p1 == null){
					p1 = this.selectablePlayers.get(i);
					Log.i("GameProcess", "i = " + i + "(left): Checked P1: " + p1);
//					index1 = i;
				}else{
					//p1 already initialized, now p2's turn
					p2 = this.selectablePlayers.get(i);
					Log.i("GameProcess", "i = " + i + "(left): Checked P2: " + p2);
//					index2 = i;
				}
			}
		}
		// make a team of selected players
		p1.newTeam(p2);
		p2.newTeam(p1);
		
		LinearLayout gamesTable = (LinearLayout) findViewById(R.id.gamesTable);
		TextView currentLine;
	    CharSequence currentText;

	    // this will be first entry in the line
    	currentText = p1.getName()+"+"+p2.getName()+":";
    	Log.d("GameProcess", "will display " + currentText);
    	//get correct row:
	
    	if(this.gamesPlayed == 0){
    		// first row is initialized
    		currentLine = (TextView)(gamesTable.getChildAt(this.gamesPlayed));
    		gamesTable.removeViewAt(this.gamesPlayed);
    	}else{
    		// create the object for current row:
    		currentLine = new TextView(this);
    	}

    	
    	p1 = null; p2 = null;
//        index1 = -1; index2 = -1;
        for(int i = 0; i < checkedPositionsRight.length; i++){
            if(checkedPositionsRight[i]){
                if(p1 == null){
                    p1 = this.selectablePlayers.get(i);
                    Log.i("GameProcess", "i = " + i + "(right): Checked P1: " + p1);
//                    index1 = i;
                }else{
                    //p1 already initialized, now p2's turn
                    p2 = this.selectablePlayers.get(i);
                    Log.i("GameProcess", "i = " + i + "(right): Checked P2: " + p2);
//                    index2 = i;
                }
            }
        }
        // make a team of selected players
        p1.newTeam(p2);
        p2.newTeam(p1);
    	
        currentText = currentText + (p1.getName()+"+"+p2.getName());
    	
    	//fill the textview with text:
    	currentLine.setText(currentText);
    	// add textview to the table:
    	gamesTable.addView(currentLine);
    	currentLine.setVisibility(View.VISIBLE);
    	
    	this.gamesPlayed++;
    	System.out.println("Critical Game? " + this.criticalGame);
        if(!this.criticalGame){
            // in the next game every combination can be played again
            this.selectablePlayers = (ArrayList<Player>) allPlayers.clone();
        }	    
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
