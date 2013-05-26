/**
 * 
 */
package de.inue.tkplaner;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PairChoiceDialog extends DialogFragment {

	/**
	 * 
	 */
	
	private LinearLayout choiceList;
	private View topLevelView;
	
    private ArrayList<String> names;
    //private ArrayList<String> combinations;
    private Dialog thisDialog;
    private boolean[] selectedPlayers;
    private boolean[] deactivatedPlayers;
	
	public PairChoiceDialog() {
		// Empty constructor required
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        this.topLevelView = inflater.inflate(R.layout.fragment_parchoice, 
        									 container);
        this.choiceList = (LinearLayout) topLevelView
        									.findViewById(R.id.layout_choice);
        Log.i("Dialog", "this list is " + this.choiceList);
        getDialog().setTitle(R.string.game_add_combination);

        this.names = getArguments().getStringArrayList("names");
    	//this.combinations = getArguments().getStringArrayList("combinations");
    	this.selectedPlayers = new boolean[this.names.size()];
    	this.deactivatedPlayers = new boolean[this.names.size()];
        
    	this.choiceList.removeAllViews();
    	//this.choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	// create an array:
    	String[] names_array = new String[this.names.size()];
    	for(int i=0; i<names_array.length; i++){
    		names_array[i] = this.names.get(i);
    		this.choiceList.addView(
					new CheckedTextView(getDialog().getContext()));
    		
    	}
    	this.topLevelView.requestLayout();
        return this.topLevelView;
    }
	
	// , ArrayList<String> playedCombinations
	public static PairChoiceDialog createPairChoiceDialog(
			ArrayList<Player> selectablePlayers) {
		
		PairChoiceDialog f = new PairChoiceDialog();
    	
    	// Supply input as an argument.
		Log.i("Dialog", "Creating PairChoiceDialog. Got players: \n" 
    						+ selectablePlayers);
    	ArrayList<String> selectableNames = new ArrayList<String>();
    	//copy the names of given players into an arraylist to be shown:
    	for(int i = 0; i<selectablePlayers.size(); i++){
    		selectableNames.add(((Player)selectablePlayers.get(i)).getName());
    	}
        Bundle args = new Bundle();
        args.putStringArrayList("names", selectableNames);
       // args.putStringArrayList("combinations", playedCombinations);
        f.setArguments(args);
    	
    	return f;
	}

}
