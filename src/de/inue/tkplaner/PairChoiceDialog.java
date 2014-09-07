/**
 * 
 */
package de.inue.tkplaner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PairChoiceDialog extends DialogFragment {

	/**
	 * 
	 */
    
    /* The parent activity must implement this interface to receive feedback
     * from this dialog. */
    public interface PairDiaLogListener{
        void onPairChosen(SparseBooleanArray checkedPositions);
    }
	
	private LinearLayout choiceList;
	private View topLevelView;
	private PairDiaLogListener mListener;
    private ArrayList<String> names;
    //private ArrayList<String> combinations;
    private Dialog thisDialog;
    private boolean[] selectedPlayers;
    private boolean[] deactivatedPlayers;
	
	public PairChoiceDialog() {
		// Empty constructor required
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    this.names = getArguments().getStringArrayList("names");
        Log.d("Dialog", "Got names as argument: " + this.names);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        this.topLevelView = inflater.inflate(R.layout.fragment_parchoice, 
        									 container, false);
        this.choiceList = (LinearLayout) topLevelView
        									.findViewById(R.id.layout_choice);
        Log.d("Dialog", "My list is " + this.choiceList);
        getDialog().setTitle(R.string.game_add_combination);

        
    	//this.combinations = getArguments().getStringArrayList("combinations");
    	this.selectedPlayers = new boolean[this.names.size()];
    	this.deactivatedPlayers = new boolean[this.names.size()];
        
    	this.choiceList.removeAllViews();
    	//this.choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	// create an array:
    	String[] names_array = new String[this.names.size()];
    	CheckBox currentPlayer;
//    	CheckedTextView currentPlayer;
    	for(int i=0; i<names_array.length; i++){
//    		names_array[i] = this.names.get(i);
    		Log.d("Dialog", "Adding " + this.names.get(i));
//    		currentPlayer = new CheckedTextView(getActivity());
    		currentPlayer = new CheckBox(getActivity());
    		
    		currentPlayer.setText(this.names.get(i));
    		this.choiceList.addView(currentPlayer);
    		
    	}
    	
    	this.topLevelView.requestLayout();
        return this.topLevelView;
    }
	
	// , ArrayList<String> playedCombinations
	public static PairChoiceDialog createPairChoiceDialog(
			ArrayList<Player> selectablePlayers) {
		
		PairChoiceDialog f = new PairChoiceDialog();
//    	System.out.println("Creating PairChoiceDialog. Got players: \n" 
//                + selectablePlayers);
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
	
	// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the PairDiaLogLIstener so we can send events to the host
            this.mListener = (PairDiaLogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PairDiaLogLIstener");
        }
    }

	/* TODO: Register this method for callback. Not working yet
	 * TODO: Extract the players pair and pass to parent activity
	 * TODO: Check that exactly 2 players are selected 
	 * */
	public void applyPair(View view){
	    Log.i("Dialog", "Apply pair");
	    mListener.onPairChosen(null);
	}

}
