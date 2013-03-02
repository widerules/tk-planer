package de.inue.tkplaner;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PairChoiceDialog extends DialogFragment {

    ArrayList<String> names;
    public PairChoiceDialog(ArrayList<String> selectableNames){
        super();
        System.out.println("Creating PairChoiceDialog. Got " + selectableNames + " players");
        this.names = selectableNames;
        
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
        builder.setMultiChoiceItems(names_array, null, new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialog, int id,
                    boolean isChecked) {

                    System.out.println("Dialog: Some name selected!");
                }
            })
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
        return builder.create();
    }
}
