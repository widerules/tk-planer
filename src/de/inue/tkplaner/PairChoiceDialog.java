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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PairChoiceDialog extends DialogFragment implements
        OnClickListener, OnCheckedChangeListener {

    /**
	 * 
	 */

    /*
     * The parent activity must implement this interface to receive feedback
     * from this dialog.
     */
    public interface PairDiaLogListener {
        void onPairChosen(boolean[] selectedPlayersLeft,
                boolean[] selectedPlayersRight);
    }

    private LinearLayout choiceListLeft;
    private LinearLayout choiceListRight;
    private View topLevelView;
    private PairDiaLogListener mListener;
    private ArrayList<String> names;
    // private ArrayList<String> combinations;
    private boolean[] selectedPlayersLeft;
    private boolean[] selectedPlayersRight;
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
        this.choiceListLeft = (LinearLayout) topLevelView
                .findViewById(R.id.layout_choice_left);
        this.choiceListRight = (LinearLayout) topLevelView
                .findViewById(R.id.layout_choice_right);

        getDialog().setTitle(R.string.game_add_combination);

        // this.combinations =
        // getArguments().getStringArrayList("combinations");
        this.selectedPlayersLeft = new boolean[this.names.size()];
        this.selectedPlayersRight = new boolean[this.names.size()];
        this.deactivatedPlayers = new boolean[this.names.size()];

        this.choiceListLeft.removeAllViews();
        this.choiceListRight.removeAllViews();
        // this.choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // create an array:
        String[] names_array = new String[this.names.size()];
        CheckBox currentPlayer;
        // CheckedTextView currentPlayer;
        for (int i = 0; i < names_array.length; i++) {
            // names_array[i] = this.names.get(i);
            Log.d("Dialog", "Adding " + this.names.get(i));
            // currentPlayer = new CheckedTextView(getActivity());
            currentPlayer = new CheckBox(getActivity());
            currentPlayer.setText(this.names.get(i));
            currentPlayer.setOnCheckedChangeListener(this);
            this.choiceListLeft.addView(currentPlayer);

            currentPlayer = new CheckBox(getActivity());
            currentPlayer.setText(this.names.get(i));
            currentPlayer.setOnCheckedChangeListener(this);
            this.choiceListRight.addView(currentPlayer);

        }
        Button confirmButton = (Button) topLevelView
                .findViewById(R.id.button_choice);
        confirmButton.setOnClickListener(this);

        this.topLevelView.requestLayout();
        return this.topLevelView;
    }

    // , ArrayList<String> playedCombinations
    public static PairChoiceDialog createPairChoiceDialog(
            ArrayList<Player> selectablePlayers) {

        PairChoiceDialog f = new PairChoiceDialog();
        // System.out.println("Creating PairChoiceDialog. Got players: \n"
        // + selectablePlayers);
        // Supply input as an argument.
        Log.i("Dialog", "Creating PairChoiceDialog. Got players: \n"
                + selectablePlayers);
        ArrayList<String> selectableNames = new ArrayList<String>();
        // copy the names of given players into an arraylist to be shown:
        // TODO: Pass Player objects to prevent repeating groups
        for (int i = 0; i < selectablePlayers.size(); i++) {
            selectableNames.add(((Player) selectablePlayers.get(i)).getName());
        }
        Bundle args = new Bundle();
        args.putStringArrayList("names", selectableNames);
        // args.putStringArrayList("combinations", playedCombinations);
        f.setArguments(args);

        return f;
    }

    // Override the Fragment.onAttach() method to instantiate the
    // PairDiaLogLIstener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the PairDiaLogLIstener so we can send events to the
            // host
            this.mListener = (PairDiaLogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PairDiaLogLIstener");
        }
    }

    /*
     */
    @Override
    public void onClick(View Button) {
        Log.d("Dialog", "onClick: Apply pair");
        int nSelectedPlayersLeft = 0;
        for (int i = 0; i < this.choiceListLeft.getChildCount(); i++) {

            if (((CheckBox) this.choiceListLeft.getChildAt(i)).isChecked()) {
                this.selectedPlayersLeft[i] = true;
                nSelectedPlayersLeft += 1;
            } else {
                this.selectedPlayersLeft[i] = false;
            }
        }
        int nSelectedPlayersRight = 0;
        for (int i = 0; i < this.choiceListRight.getChildCount(); i++) {

            if (((CheckBox) this.choiceListRight.getChildAt(i)).isChecked()) {
                this.selectedPlayersRight[i] = true;
                nSelectedPlayersRight += 1;
            } else {
                this.selectedPlayersRight[i] = false;
            }
        }
        if (nSelectedPlayersLeft == 2 && nSelectedPlayersRight == 2) {
            mListener.onPairChosen(this.selectedPlayersLeft,
                    this.selectedPlayersRight);
            dismiss();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton checkBox, boolean newChecked) {
        LinearLayout boxParent = (LinearLayout) checkBox.getParent();
        LinearLayout horzLayout = (LinearLayout) boxParent.getParent();

        int leftOrRight = ((ViewGroup) horzLayout).indexOfChild(boxParent);
        int index = ((ViewGroup) (boxParent)).indexOfChild(checkBox);

        LinearLayout otherParent = (LinearLayout) horzLayout
                .getChildAt(1 - leftOrRight);

        boolean ownChecked[] = new boolean[boxParent.getChildCount()];
        boolean otherChecked[] = new boolean[otherParent.getChildCount()];

        // get an overview of current situation:
        int alreadyChecked = 0;
        int otherCheckedNo = 0;
        CheckBox currentBox;
        for (int i = 0; i < boxParent.getChildCount(); i++) {
            currentBox = (CheckBox) boxParent.getChildAt(i);
            if (currentBox.isChecked()) {
                alreadyChecked++;
                ownChecked[i] = true;
            } else {
                ownChecked[i] = false;
            }
        }
        Log.d("Dialog", "Checked " + alreadyChecked + " Boxes in this column");
        for (int i = 0; i < otherParent.getChildCount(); i++) {
            currentBox = (CheckBox) otherParent.getChildAt(i);
            if (currentBox.isChecked()) {
                otherCheckedNo++;
                otherChecked[i] = true;
            } else {
                otherChecked[i] = false;
            }
        }
        Log.d("Dialog", "Checked " + otherCheckedNo + " Boxes in other column");

        // make sure that not more than 2 players get in one team:
        if (alreadyChecked == 2) {
            for (int i = 0; i < boxParent.getChildCount(); i++) {
                currentBox = (CheckBox) boxParent.getChildAt(i);
                if (!ownChecked[i]) {
                    Log.d("Dialog", "Disabling Box " + i);
                    currentBox.setEnabled(false);
                }
            }
        } else {
            for (int i = 0; i < boxParent.getChildCount(); i++) {
                currentBox = (CheckBox) boxParent.getChildAt(i);
                if (!ownChecked[i] && !otherChecked[i]) {
                    Log.d("Dialog", "Enabling Box " + i);
                    currentBox.setEnabled(true);
                }
            }
        }

        // Male sure that one player is not in two teams at same time:
        CheckBox otherCheckBox = (CheckBox) otherParent.getChildAt(index);
        if (newChecked && otherCheckBox.isEnabled()) {
            // This player was selectable. Disable!
            otherCheckBox.setEnabled(false);
            Log.d("Dialog", (leftOrRight == 0 ? "Left" : "Right")
                    + " column disabled");
        } else if (!newChecked && (otherCheckedNo != 2)) {
            // This player was not selectable but the other team is not complete
            // => Enable!
            otherCheckBox.setEnabled(true);
            Log.d("Dialog", (leftOrRight == 0 ? "Left" : "Right")
                    + " column enabled");
        }

    }
}
