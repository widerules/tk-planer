/**
 * 
 */
package de.inue.tkplaner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

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
    private ArrayList<Player> players;
    // private ArrayList<String> combinations;
    private boolean[] selectedPlayersLeft;
    private boolean[] selectedPlayersRight;
	private boolean criticalGame;

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

        this.choiceListLeft.removeAllViews();
        this.choiceListRight.removeAllViews();
        // this.choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // create an array:
        String[] names_array = new String[this.names.size()];
        CheckBox currentPlayer;
        // CheckedTextView currentPlayer;
        Log.d("Dialog", "Have players list: " + this.players);
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
            ArrayList<Player> selectablePlayers, boolean thirdOfFive) {

        PairChoiceDialog f = new PairChoiceDialog();
        f.setPlayers(selectablePlayers);
        f.setCriticalGame(thirdOfFive);
        // System.out.println("Creating PairChoiceDialog. Got players: \n"
        // + selectablePlayers);
        // Supply input as an argument.
        Log.i("Dialog", "Creating PairChoiceDialog. Got players: \n"
                + selectablePlayers);
        ArrayList<String> selectableNames = new ArrayList<String>();
        // copy the names of given players into an arraylist to be shown:
        // TODO: Since players are passed via setter method, String list not needed
        for (int i = 0; i < selectablePlayers.size(); i++) {
            selectableNames.add(((Player) selectablePlayers.get(i)).getName());
        }
        Bundle args = new Bundle();
        args.putStringArrayList("names", selectableNames);
        // args.putStringArrayList("combinations", playedCombinations);
        f.setArguments(args);

        return f;
    }
    
    private void setCriticalGame(boolean thirdOfFive) {
		this.criticalGame = thirdOfFive;
	}

	private void setPlayers(ArrayList<Player> players){
    	this.players = players;
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
    /*
     * TODO: Ensure that player does not play twice in same team.
     * 		 Now not yet working: Disabled checkboxes can be reenabled. 
     * TODO: Take care of critical game process here (Disable choice when needed)  
     * (non-Javadoc)
     * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
     */
    public void onCheckedChanged(CompoundButton checkBox, boolean newChecked) {
        LinearLayout boxParent = (LinearLayout) checkBox.getParent();
        LinearLayout horzLayout = (LinearLayout) boxParent.getParent();

        int leftOrRight = ((ViewGroup) horzLayout).indexOfChild(boxParent);

        LinearLayout otherParent = (LinearLayout) horzLayout
                .getChildAt(1 - leftOrRight);

        boolean ownChecked[] = new boolean[boxParent.getChildCount()];
        boolean otherChecked[] = new boolean[otherParent.getChildCount()];

        Log.d("Dialog", "Created ownChecked (" + ownChecked.length 
        		+ "), other (" + otherChecked.length + ")");
        // get an overview of current situation:
        int alreadyChecked = 0;
        int otherCheckedNo = 0;
        CheckBox currentBox, currentOtherBox;
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

        for(int i = 0; i < boxParent.getChildCount(); i++){
        	currentBox = (CheckBox) boxParent.getChildAt(i);
        	currentOtherBox = (CheckBox) otherParent.getChildAt(i);
        	// first, handle own team
        	if (alreadyChecked == 2) {
        		//this team is complete. Disable all other unconditionally
        		if (!ownChecked[i]) {
                    Log.d("Dialog", "Disabling own box " + i + "(" + players.get(i) + ")");
                    currentBox.setEnabled(false);
                }
        	}else{
        		// this team is not complete. Disable those who can't be partner
        		if (!ownChecked[i] && !otherChecked[i]) {
                	// This one is not yet in a team => first enable
        			 Log.d("Dialog", "Enabling own box " + i + "(" + players.get(i) + ")");
                     currentBox.setEnabled(true);
        			// if he already played with another team member 
                     for(int j = 0; j < ownChecked.length; j++){
                    	 if(players.get(i).hasPlayedWith(players.get(j)) && ownChecked[j]){
                    		// => disable again
                    		 Log.d("Dialog", players.get(i) + " has played with " + players.get(j));
                    		 Log.d("Dialog", "Disabling own box " + i + "(" + players.get(i) + ")");
                    		 currentBox.setEnabled(false);
                    		 break;
                    	 }
                    	 if(players.get(i).nGamesAgainst(players.get(j)) == 2 && otherChecked[j] && criticalGame){
             				Log.d("Dialog", players.get(i) + " has already playes 2 times against " + players.get(j));
             				Log.d("Dialog", "Disabling own box " + i + "(" + players.get(i) + ")");
             				currentBox.setEnabled(false);
             				break;
             			}
                     }
                     
        		}else if(otherChecked[i]){
        			// he plays in the other team => disable
        			Log.d("Dialog", players.get(i) + " is in the other team!");
        			currentBox.setEnabled(false);
        		}else {
        			// he plays in this team => enable for unchecking
        			currentBox.setEnabled(true);
        		}
        	} 
        	// Now handle this player in  the other team:
        	if(otherCheckedNo == 2){
        		// Disable all unchecked players 
        		// other team is complete. Disable all other unconditionally
        		if (!otherChecked[i]) {
                    Log.d("Dialog", "Disabling other box " + i + "(" + players.get(i) + ")");
                    currentOtherBox.setEnabled(false);
                }
        	}
        	else if(ownChecked[i]){
        		// Disable if he is in the team of invoked box
        		Log.d("Dialog", players.get(i) + " is in this team! Disabling other.");
    			currentOtherBox.setEnabled(false);
        	} else{
        		// probably he is available
        		Log.d("Dialog", "Enabling other box " + i + "(" + players.get(i) + ")");
				currentOtherBox.setEnabled(true);
        		// unless he already played with other activated player of that team
        		for(int j = 0; j < otherChecked.length; j++){
//        			Log.d("Dialog", "i = " + i + ", other j = " + j);
        			if(players.get(i).hasPlayedWith(players.get(j)) && otherChecked[j]){
        				Log.d("Dialog", players.get(i) + " has played with " + players.get(j));
        				Log.d("Dialog", "Disabling other box " + i + "(" + players.get(i) + ")");
        				currentOtherBox.setEnabled(false);
        				break;
        			}
        			if(players.get(i).nGamesAgainst(players.get(j)) == 2 && ownChecked[j] && criticalGame){
        				Log.d("Dialog", players.get(i) + " has already playes 2 times against " + players.get(j));
        				Log.d("Dialog", "Disabling other box " + i + "(" + players.get(i) + ")");
        				currentOtherBox.setEnabled(false);
        				break;
        			}
        		}
        	}
        	 
        }
        
//        // make sure that not more than 2 players get in one team:
//        if (alreadyChecked == 2) {
//            for (int i = 0; i < boxParent.getChildCount(); i++) {
//                currentBox = (CheckBox) boxParent.getChildAt(i);
//                if (!ownChecked[i]) {
//                    Log.d("Dialog", "Disabling Box " + i);
//                    currentBox.setEnabled(false);
//                }
//            }
//        } else {
//            for (int i = 0; i < boxParent.getChildCount(); i++) {
//            	// this team is not complete. See who can be my partner
//                currentBox = (CheckBox) boxParent.getChildAt(i);
//                if (!ownChecked[i] && !otherChecked[i]) {
//                	// This one is not yet in a team
//                    Log.d("Dialog", "Enabling Box " + i);
//                    currentBox.setEnabled(true);
//                    // did he play with the just selected player?
//                    if(this.players.get(i).hasPlayedWith(this.players.get(index)) && newChecked){
//                    	// he did => disable!
//                    	Log.d("Dialog", players.get(i) + " has played with " + players.get(index));
//                        currentBox.setEnabled(false);
//                    }
//                }
//            }
//        }
//
//        // Male sure that one player is not in two teams at same time:
//        CheckBox otherCheckBox = (CheckBox) otherParent.getChildAt(index);
//        if (newChecked && otherCheckBox.isEnabled()) {
//            // This player was selectable. Disable!
//            otherCheckBox.setEnabled(false);
//            Log.d("Dialog", (leftOrRight == 1 ? "Left" : "Right")
//                    + " column disabled");
//        } else if (!newChecked && (otherCheckedNo != 2)) {
//            // This player was not selectable but the other team is not complete
//            // => Enable!
//        	otherCheckBox.setEnabled(true);
//            Log.d("Dialog", (leftOrRight == 1 ? "Left" : "Right")
//                    + " column enabled");
//        }

    }
}
