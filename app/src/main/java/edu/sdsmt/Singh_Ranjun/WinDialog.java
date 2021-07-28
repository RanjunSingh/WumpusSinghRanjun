package edu.sdsmt.Singh_Ranjun;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Construct the win dialog.
 */
public class WinDialog extends DialogFragment {
    private int score;
    private static final String WINSCORE = "winScore";
    private AlertDialog.Builder builder;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setRetainInstance(true);

        builder = new AlertDialog.Builder(getActivity());
        MainActivity activity = (MainActivity)getActivity();


        super.onCreateDialog(savedInstanceState);
        if(savedInstanceState != null){
            score = savedInstanceState.getInt(WINSCORE);
        }
        else{
            score = activity.getScore();
        }

        builder.setTitle("Game Won!");
        builder.setMessage("Score: " + score);

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(WINSCORE, score);
    }

}