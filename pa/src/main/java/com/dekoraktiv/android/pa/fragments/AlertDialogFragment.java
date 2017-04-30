package com.dekoraktiv.android.pa.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dekoraktiv.android.pa.constants.Extras;

public class AlertDialogFragment extends DialogFragment {

    //private AlertDialogListener mAlertDialogListener;

    public static AlertDialogFragment newInstance(@NonNull String message) {
        final Bundle bundle = new Bundle();
        bundle.putString(Extras.INTENT_EXTRA, message);

        final AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setMessage(getArguments().getString(Extras.INTENT_EXTRA))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mAlertDialogListener.onDialogPositiveClick();

                        dialog.cancel();
                    }
                })
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //mAlertDialogListener = (AlertDialogListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //mAlertDialogListener = null;
    }

    //public interface AlertDialogListener {
    //    void onDialogPositiveClick();
    //}
}
