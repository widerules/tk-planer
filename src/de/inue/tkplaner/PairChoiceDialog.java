package de.inue.tkplaner;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class PairChoiceDialog extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener{

    private ArrayList<String> names;
    private ArrayList<String> combinations;
    private Dialog thisDialog;
    private boolean[] selectedPlayers;
    private boolean[] deactivatedPlayers;
    
    /*
     * Default constructor
     */
    public PairChoiceDialog(){
    	super();
    }
    
    /*
     * Singleton instance using default constructor
     */
    public static PairChoiceDialog createPairChoiceDialog(ArrayList<String> selectableNames, 
			ArrayList<String> playedCombinations){
    	PairChoiceDialog f = new PairChoiceDialog();
    	
    	// Supply input as an argument.
    	System.out.println("Creating PairChoiceDialog. Got " + selectableNames + " players");
        Bundle args = new Bundle();
        args.putStringArrayList("names", selectableNames);
        args.putStringArrayList("combinations", playedCombinations);
        f.setArguments(args);
    	
    	return f;
    }
    
    
//    public PairChoiceDialog(ArrayList<String> selectableNames, 
//    						ArrayList<String> playedCombinations){
//        super();
//        System.out.println("Creating PairChoiceDialog. Got " + selectableNames + " players");
//        this.names = selectableNames;
//        this.combinations = playedCombinations;
//        
//    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	super.onCreateDialog(savedInstanceState);
        // put list of possible players into the dialog
        //does not work yet!
    	
    	
    	this.names = getArguments().getStringArrayList("names");
    	this.combinations = getArguments().getStringArrayList("combinations");
    	this.selectedPlayers = new boolean[this.names.size()];
    	this.deactivatedPlayers = new boolean[this.names.size()];
    	
    	// create an array:
    	String[] names_array = new String[this.names.size()];
    	for(int i=0; i<names_array.length; i++){
    		names_array[i] = this.names.get(i);
    	}
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(names_array, null, this)
        .setTitle(R.string.game_add_combination)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.out.println("Dialog: OK klicked!");
                // get checked items and pass them to the parent activity
                
                ((GameProcessActivity)getActivity()).onPairChosen(
                    ((AlertDialog) dialog).getListView()
                                          .getCheckedItemPositions());
            }
        });
        // Create the AlertDialog object and return it
        this.thisDialog = builder.create();
        return this.thisDialog;
    }

	@Override
	public void onClick(DialogInterface dialog, int which, boolean checked) {
		// TODO Auto-generated method stub
		
		// index "which" points also into this.names:
		String checkedName = this.names.get(which);
		System.out.println("Dialog: Some name selected: index " 
				+ which+ " (" +checkedName+ "), checked="+checked);
		ListView displayedList = (ListView)this.getDialog().getCurrentFocus();
		CheckedTextView currentName = null;
		if(!checked){
			// re-activate previous partners
			for(int i=0; i<displayedList.getChildCount(); i++){
				currentName = (CheckedTextView)displayedList.getChildAt(i);
				currentName.setVisibility(View.VISIBLE);
			}
			this.selectedPlayers[which] = false;
			return;
		}
		this.selectedPlayers[which] = true;
		// disable played combinations (if any available):
		String partner = "";
		for(int i = 0; i < this.combinations.size(); i++){
			
			if(this.combinations.get(i).equals(checkedName)){
				// This player already played, disable hisw partner!
				if((i%2 == 0) && i < (this.combinations.size() - 1)){
					// This player was on the left: combination is at i+1
					partner = this.combinations.get(i+1);
				}else if(i%2 != 0){
					// This player was on the right: combination is at i-1
					partner = this.combinations.get(i-1);
				} else{
					System.out.println("Something is wrong with the list " 
										+ this.combinations);
					throw new NoSuchElementException("Something is wrong with the list");
				}
				System.out.println("Found partner: " + partner);
				System.out.println("This view: " + this.getView());
				System.out.println("This focus: " + this.getDialog().getCurrentFocus());
				System.out.println("This List " + displayedList.getChildCount());
				for(int j = 0; j<displayedList.getChildCount(); j++){
					currentName = (CheckedTextView)displayedList.getChildAt(j);
					System.out.println("child at " + j + ": " +displayedList.getChildAt(j));
					if(currentName.getText().equals(partner)){
						System.out.println("Now do something with " + partner);
						currentName.setVisibility(View.INVISIBLE);
						currentName.setChecked(false);
					} // partner disabled
				} // finished disabling previous partners.
			} // finished handling of selected player who already had a partner
		} // finished looking for partners
	} // end onClick
}
