package de.inue.tkplaner;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameProcessActivity extends Activity {
	
	private ArrayList<String> playedCombinations;
	private ArrayList<String> allPlayers;
	private ArrayList<String> selectableNames;
	private int gamesPlayed = 0;
	private int pairsChosen = 0;
	private boolean criticalGame = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameprocess);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		this.allPlayers = intent.getStringArrayListExtra(InitActivity.EXTRA_NAME);
		System.out.println("Got " + this.allPlayers.size() + " players");
		this.selectableNames = (ArrayList<String>) allPlayers.clone();
		this.playedCombinations = new ArrayList<String>();
		this.criticalGame = (this.allPlayers.size()==5)?true:false;
	}
	
	/**
	 * Callback button to choose a pair of players 
	 */
	public void choosePair(View view){
		FragmentManager fm = getFragmentManager();
        PairChoiceDialog choiceDialog = 
        		PairChoiceDialog.createPairChoiceDialog(selectableNames, 
        												playedCombinations);
        choiceDialog.show(fm, null);
        System.out.println("Dialog shown...");
	}
	
	public void onPairChosen(SparseBooleanArray checkedPositions){
	//TODO: safety check of array length + number of selected items
	    String name1="", name2="";
	    int index1=-1, index2=-1;
	    boolean first_found = false;
	    System.out.println("Checked " + checkedPositions.size() + " players");
	    if(checkedPositions.size() != 2){
	    	System.out.println("Not a pair chosen! Dismissing...");
	    	return;
	    }
	    //i<checkedPositions.size() && 
	    for(int i=0; (i<selectableNames.size()); i++){
	        System.out.println(i+": selected="+checkedPositions.get(i));
	        if(checkedPositions.get(i) && !first_found){
	            name1 = selectableNames.get(i);
                System.out.println("User selected 1: " + name1);
                index1 = i;
                first_found=true;
            }else if(checkedPositions.get(i) && first_found){
                name2 = selectableNames.get(i);
                System.out.println("User selected 2: " + name2);
                index2 = i;
            }
	    }
	    //Resources res = getResources();
	    LinearLayout gamesTable = (LinearLayout) findViewById(R.id.gamesTable);
	    System.out.println("Here the table " + gamesTable + "with " 
    							+ gamesTable.getChildCount() + " rows." );
	    TextView currentLine;
	    CharSequence currentText;
	    if(this.pairsChosen == 0){
	    	// this will be first entry in the line
	    	this.pairsChosen++;
	    	currentText = name1+"+"+name2+":";
	    	System.out.println("will display " + currentText);
	    	//get correct row:
	    	if(this.gamesPlayed == 0){
	    		// first row is initialized
	    		currentLine = (TextView)(gamesTable.getChildAt(this.gamesPlayed));
	    		gamesTable.removeViewAt(this.gamesPlayed);
	    		
	    	}else{
	    		// create the object for current row:
	    		currentLine = new TextView(this);
	    		
	    	}
	    	//fill the textview with text:
	    	currentLine.setText(currentText);
	    	// add textview to the table:
	    	gamesTable.addView(currentLine);
	    	currentLine.setVisibility(View.VISIBLE);
	    	this.playedCombinations.add(name1);
	    	this.playedCombinations.add(name2);
	    	this.selectableNames.remove(index2); //remove higher index first
	    	this.selectableNames.remove(index1);
	    	
	    }else{
	    	// current line already exists and has to be extended.
	    	currentLine = (TextView)(gamesTable.getChildAt(this.gamesPlayed));
	    	currentText = currentLine.getText();
	    	currentText = currentText + name1 + "+" + name2;
	    	currentLine.setText(currentText);
	    	this.playedCombinations.add(name1);
	    	this.playedCombinations.add(name2);
	    	this.gamesPlayed++;
	    	this.pairsChosen = 0;
	    	System.out.println("Critical Game? " + this.criticalGame);
	    	if(!this.criticalGame){
	    		// in the next game every combination can be played again
	    		this.selectableNames = (ArrayList<String>) allPlayers.clone();
	    	}
	    }
	    for(int i = 0; i < this.playedCombinations.size(); i++)
	    {
	    	System.out.println("played combinations (" +i+ "): " 
	    						+ this.playedCombinations.get(i));
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
