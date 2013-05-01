package de.inue.tkplaner;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PairChoiceDialog extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener{

    private ArrayList<String> names;
    private ArrayList<String> combinations;
    private Dialog thisDialog;
    
    public PairChoiceDialog(ArrayList<String> selectableNames, 
    						ArrayList<String> playedCombinations){
        super();
        System.out.println("Creating PairChoiceDialog. Got " + selectableNames + " players");
        this.names = selectableNames;
        this.combinations = playedCombinations;
        
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // put list of possible players into the dialog
        //does not work yet!

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
		if(!checked)
			return;
		
		// disable played combinations (if any available):
		String partner = "";
		for(int i = 0; i < this.combinations.size(); i++){
			
			if(this.combinations.get(i).equals(checkedName)){
				// This player already played, disable this option!
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
				for(int j = 0; i<this.names.size(); i++){
					if(this.names.get(j).equals(partner)){
						System.out.println("This view: " + this.getView());
					}
				}
			}
		}
	}
}
