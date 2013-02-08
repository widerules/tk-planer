/**
 * 
 */
package de.inue.tkplaner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * @author Sanyok
 *
 */
public class ErrorDialog extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.error_dummy)
        	   .setTitle(R.string.error_title)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       System.out.println("Dialog: OK klicked!");
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	public void setMessage(CharSequence message){
		
		System.out.println(""+getDialog());
		System.out.println(""+message);
		//((AlertDialog)getDialog()).setMessage(message);
	}
}
