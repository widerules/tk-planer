package de.inue.tkplaner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PairChoiceDialog extends DialogFragment {

    String[] names;
    public PairChoiceDialog(String[] selectableNames){
        super();
        this.names = selectableNames;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // put list of possible players into the dialog
        //does not work yet!

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(this.names, null, new DialogInterface.OnMultiChoiceClickListener() {
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
