package com.dekoraktiv.android.pa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dekoraktiv.android.pa.R;
import com.dekoraktiv.android.pa.models.Additive;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<Additive> mAdditives;
    private RecyclerViewAdapterBase mRecyclerViewAdapterBase;

    public RecyclerViewAdapter(Context context, ArrayList<Additive> additives) {
        this.mAdditives = additives;
        this.mRecyclerViewAdapterBase = ((RecyclerViewAdapterBase) context);
    }

    public void changeDataSet(ArrayList<Additive> additives) {
        mAdditives = new ArrayList<>(additives);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAdditives.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final Context baseContext = holder.itemView.getContext();

        final Additive additive = mAdditives.get(position);

        final int color;

        switch (additive.getRisk()) {
            case 1:
                color = baseContext.getResources().getColor(R.color.risk_low);
                break;
            case 2:
                color = baseContext.getResources().getColor(R.color.risk_moderate);
                break;
            case 3:
                color = baseContext.getResources().getColor(R.color.risk_high);
                break;
            default:
                color = baseContext.getResources().getColor(R.color.primary);
                break;
        }

        holder.imageView.setBackgroundColor(color);
        holder.textViewLabel.setText(additive.getLabel());
        holder.textViewName.setText(additive.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewAdapterBase.showDetails(additive);
            }
        });
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    public interface RecyclerViewAdapterBase {
        void showDetails(Additive additive);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.text_view_title)
        TextView textViewLabel;
        @BindView(R.id.text_view_subtitle)
        TextView textViewName;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
