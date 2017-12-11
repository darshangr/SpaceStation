package com.rootminusone.spacestationpasses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rootminusone.spacestationpasses.R;
import com.rootminusone.spacestationpasses.model.Response;
import com.rootminusone.spacestationpasses.utils.Util;

import java.util.List;

/**
 * Created by gangares on 12/10/17.
 */

public class PassesAdapter extends RecyclerView.Adapter<PassesAdapter.PassesViewHolder> {

    private Context mContext;
    private List<Response> mPassesList;

    public class PassesViewHolder extends RecyclerView.ViewHolder {
        public TextView duration, riseTime;

        public PassesViewHolder(View view) {
            super(view);
            duration = (TextView) view.findViewById(R.id.durationValue);
            riseTime = (TextView) view.findViewById(R.id.riseValue);

        }
    }

    public PassesAdapter(Context mContext, List<Response> passesList) {
        this.mContext = mContext;
        this.mPassesList = passesList;
    }

    @Override
    public PassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passes_card, parent, false);

        return new PassesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PassesViewHolder holder, int position) {
        Response response = mPassesList.get(position);
        String durationInSeconds = Util.convertToSeconds(response.getDuration().toString()) + " seconds";
        holder.duration.setText(durationInSeconds);
        String convertedTime = Util.getDateInCurrentTimeZone(response.getRisetime().toString());
        holder.riseTime.setText(convertedTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show details screen by handling on click event on the card item
            }
        });

    }


    @Override
    public int getItemCount() {
        return mPassesList.size();
    }
}
