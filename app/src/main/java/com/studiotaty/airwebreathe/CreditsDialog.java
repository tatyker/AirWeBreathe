package com.studiotaty.airwebreathe;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.studiotaty.airwebreathe.R;

import static android.view.Window.FEATURE_NO_TITLE;

public class CreditsDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_credits);

        TextView creditsText = dialog.findViewById(R.id.creditsText);

        String creditsLink = "<a href=\"https://waqi.info\">World Air Quality Index Project</a>";
        creditsText.setLinksClickable(true);
        creditsText.setText(Html.fromHtml(creditsLink));
        creditsText.setLinkTextColor(Color.BLACK);
        creditsText.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconText = dialog.findViewById(R.id.iconText);
        iconText.setText("Icon by Taty");

        return dialog;
    }
}
