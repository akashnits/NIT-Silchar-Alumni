package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akash.android.nitsilcharalumni.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlumniLocationAdapter extends RecyclerView.Adapter<AlumniLocationAdapter.AlumniLocationViewHolder> {


    private List<String> mAlumniLocationList;
    private Context mContext;

    public AlumniLocationAdapter(Context mContext) {
        this.mContext = mContext;
        this.mAlumniLocationList = Arrays.asList(mContext.getResources().getStringArray(R.array.location));
    }

    @Override
    public AlumniLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alumni_location,
                parent, false);
        return new AlumniLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumniLocationViewHolder holder, int position) {
        holder.ctvAlumniLocation.setText(mAlumniLocationList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlumniLocationList.size();
    }

    class AlumniLocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctvAlumniLocation)
        CheckedTextView ctvAlumniLocation;

        public AlumniLocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
