package edu.sdsmt.Singh_Ranjun;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

//class based dialog

/**
 * Construct the lose dialog.
 */
public class LoseDialog extends DialogFragment {
    private String loseReason;
    private static final String LOSEREASON = "loseReason";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        setRetainInstance(true);
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MainActivity activity = (MainActivity)getActivity();
        Game game = activity.getGame();

        if(savedInstanceState != null){
            loseReason = savedInstanceState.getString(LOSEREASON);
        }
        else{
            if(game.isWumpusPresent())
                loseReason = getString(R.string.eaten);
            else if(game.getPitPresent())
                loseReason = getString(R.string.pit);
            else if(game.wumpusAlive())
                loseReason = getString(R.string.unbeatable);
        }

        builder.setTitle("Game Lost!");
        builder.setMessage(loseReason);

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(LOSEREASON, loseReason);
    }


}
