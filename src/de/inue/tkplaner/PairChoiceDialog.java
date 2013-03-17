package de.inue.tkplaner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PairChoiceDialog extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		// put list of possible players into the dialog
		String[] players = null;
		//does not work yet!
		
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(players, null, null)
        	   .setTitle(R.string.game_add_combination)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       System.out.println("Dialog: OK klicked!");
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
