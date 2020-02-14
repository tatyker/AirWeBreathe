package com.studiotaty.airwebreathe;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import static android.view.Window.FEATURE_NO_TITLE;

public class SearchDialog extends AppCompatDialogFragment {


    private EditText searchText;
    private Button searchButton;
    private Button closeButton;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);

        searchText = dialog.findViewById(R.id.placeText);
        searchButton = dialog.findViewById(R.id.searchButton);
        closeButton = dialog.findViewById(R.id.closeButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedTerm = searchText.getText().toString();
                ((MainActivity) getActivity()).doSearch(searchedTerm);
                dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }
}
