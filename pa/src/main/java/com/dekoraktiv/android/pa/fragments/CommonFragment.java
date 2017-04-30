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

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonFragment extends Fragment {

    @BindView(R.id.text_view)
    TextView textView;

    public static CommonFragment newInstance(@NonNull String value) {
        final Bundle bundle = new Bundle();
        bundle.putString(Extras.INTENT_EXTRA, value);

        final CommonFragment fragment = new CommonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_common, container, false);

        ButterKnife.bind(this, view);

        final String text = getArguments().getString(Extras.INTENT_EXTRA);

        // https://code.google.com/p/android/issues/detail?id=35466#c4
        if (Build.VERSION.SDK_INT < 17) {
            textView.setText(Html.fromHtml(text).toString());
        } else {
            textView.setText(Html.fromHtml(text));
        }

        return view;
    }
}
