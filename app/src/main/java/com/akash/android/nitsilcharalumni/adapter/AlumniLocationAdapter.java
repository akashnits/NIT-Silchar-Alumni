package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Filter;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlumniLocationAdapter extends RecyclerView.Adapter<AlumniLocationAdapter.AlumniLocationViewHolder> {


    private List<String> mAlumniLocationList;
    private Context mContext;
    private int mPositionLastChecked;
    private RecyclerView mRecyclerView;
    private FilterAlumniFragment mFilterAlumniFragment;

    public AlumniLocationAdapter(Context mContext, FragmentManager fm, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mAlumniLocationList = Arrays.asList(mContext.getResources().getStringArray(R.array.location));
        this.mRecyclerView= mRecyclerView;
        this.mFilterAlumniFragment= ((FilterAlumniFragment) fm.findFragmentByTag("FilterAlumni"));
        this.mPositionLastChecked= mFilterAlumniFragment.getmLocationCheckedPosition();
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
        if((mFilterAlumniFragment.getmMainActivity().isLocationPreferenceChecked() && position == mPositionLastChecked)) {
            holder.ctvAlumniLocation.setSelected(true);
            holder.ctvAlumniLocation.setCheckMarkDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_check_box_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return mAlumniLocationList.size();
    }

    class AlumniLocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctvAlumniLocation)
        CheckedTextView ctvAlumniLocation;

         AlumniLocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ctvAlumniLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //wherever the click be, set the last checked position false if it's there
                    if(mFilterAlumniFragment.getmMainActivity().isLocationPreferenceChecked()) {
                        AlumniLocationViewHolder viewHolder = (AlumniLocationViewHolder)
                                mRecyclerView.findViewHolderForAdapterPosition(mPositionLastChecked);
                        viewHolder.ctvAlumniLocation.setSelected(false);
                        viewHolder.ctvAlumniLocation.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
                    }

                    //Clicked on  somewhere else or no checks
                    if (!mFilterAlumniFragment.getmMainActivity().isLocationPreferenceChecked()
                            || getAdapterPosition() != mPositionLastChecked) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_black_24dp));
                        mPositionLastChecked = getAdapterPosition();
                        mFilterAlumniFragment.setmLocationCheckedPosition(mPositionLastChecked);
                        mFilterAlumniFragment.getmMainActivity().setLocationPreferenceChecked(true);
                        ctv.toggle();
                    }else {
                        mFilterAlumniFragment.getmMainActivity().setLocationPreferenceChecked(false);
                    }
                }
            });
        }
    }
}
