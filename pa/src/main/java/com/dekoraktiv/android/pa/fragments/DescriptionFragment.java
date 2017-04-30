package com.dekoraktiv.android.pa.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekoraktiv.android.pa.R;
import com.dekoraktiv.android.pa.constants.Extras;
import com.dekoraktiv.android.pa.models.Additive;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionFragment extends Fragment {

    @BindView(R.id.text_view_label_and_name)
    TextView textViewLabelAndName;
    @BindView(R.id.text_view_description)
    TextView textViewDescription;

    public static DescriptionFragment newInstance(@NonNull Additive value) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.INTENT_EXTRA, value);

        final DescriptionFragment fragment = new DescriptionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_description, container, false);

        ButterKnife.bind(this, view);

        final Additive additive =
                (Additive) getArguments().getSerializable(Extras.INTENT_EXTRA);

        //noinspection ConstantConditions
        textViewLabelAndName.setText(Html.fromHtml(additive.toString()));

        // https://code.google.com/p/android/issues/detail?id=35466#c4
        if (Build.VERSION.SDK_INT < 17) {
            textViewDescription.setText(Html.fromHtml(additive.getDescription()).toString());
        } else {
            textViewDescription.setText(Html.fromHtml(additive.getDescription()));
        }

        return view;
    }
}
