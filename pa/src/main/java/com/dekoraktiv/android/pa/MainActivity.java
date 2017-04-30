package com.dekoraktiv.android.pa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.dekoraktiv.android.pa.adapters.RecyclerViewAdapter;
import com.dekoraktiv.android.pa.constants.Extras;
import com.dekoraktiv.android.pa.fragments.AlertDialogFragment;
import com.dekoraktiv.android.pa.models.Additive;
import com.dekoraktiv.android.pa.models.Stem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements RecyclerViewAdapter.RecyclerViewAdapterBase {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Additive> mAdditives;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setElevation(0);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        (new AsyncTasks()).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search_view_widget).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String string) {
                final ArrayList<Additive> additives = filterRecyclerView(mAdditives, string);

                mRecyclerViewAdapter.changeDataSet(additives);

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String string) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialogFragment alertDialogFragment =
                    AlertDialogFragment.newInstance(getString(R.string.dialog_help_message));
            alertDialogFragment.show(getFragmentManager(), Extras.INTENT_EXTRA);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDetails(Additive additive) {
        final Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Extras.INTENT_EXTRA, additive);

        startActivity(intent);
    }

    private class AsyncTasks extends AsyncTask<String, Void, ArrayList<Additive>> {

        private static final String JSON_ASSET = "additives.json";

        private ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected ArrayList<Additive> doInBackground(String... strings) {
            final ArrayList<Additive> additives = new ArrayList<>();

            try {
                final InputStream inputStream = getAssets().open(JSON_ASSET);

                final BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream));

                final StringBuilder stringBuilder = new StringBuilder();

                String buffer;

                while ((buffer = bufferedReader.readLine()) != null) {
                    stringBuilder.append(buffer);
                }

                final Gson gson = new GsonBuilder().serializeNulls().create();

                final Stem stem = gson.fromJson(stringBuilder.toString(), Stem.class);

                additives.addAll(stem.getAdditives());

                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return additives;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setMessage(getResources().getString(R.string.dialog_progress_message));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Additive> additives) {
            super.onPostExecute(additives);

            mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, additives);

            recyclerView.setAdapter(mRecyclerViewAdapter);

            mAdditives = new ArrayList<>(additives);

            mProgressDialog.dismiss();
        }
    }

    private ArrayList<Additive> filterRecyclerView(ArrayList<Additive> additives, String string) {
        final ArrayList<Additive> result = new ArrayList<>();

        for (final Additive additive : additives) {
            final String labelAndName = additive.toString().toLowerCase();

            if (labelAndName.contains(string.toLowerCase())) {
                result.add(additive);
            }
        }

        return result;
    }
}
