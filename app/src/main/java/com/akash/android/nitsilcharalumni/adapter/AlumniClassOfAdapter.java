package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.ui.alumni.FilterAlumniFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlumniClassOfAdapter extends RecyclerView.Adapter<AlumniClassOfAdapter.AlumniClassOfViewHolder> {


    private List<String> mAlumniClassOfList;
    private Context mContext;
    private int mPositionLastChecked;
    private RecyclerView mRecyclerView;
    private FilterAlumniFragment mFilterAlumniFragment;

    public AlumniClassOfAdapter(Context mContext, FragmentManager fm, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mAlumniClassOfList = Arrays.asList(mContext.getResources().getStringArray(R.array.alumniClassOf));
        this.mRecyclerView = mRecyclerView;
        this.mFilterAlumniFragment = ((FilterAlumniFragment) fm.findFragmentByTag("FilterAlumni"));
        this.mPositionLastChecked = mFilterAlumniFragment.getmLocationCheckedPosition();
    }

    @Override
    public AlumniClassOfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alumni_classof,
                parent, false);
        return new AlumniClassOfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumniClassOfViewHolder holder, int position) {
        holder.ctvAlumniClassOf.setText(mAlumniClassOfList.get(position));
        if ((mFilterAlumniFragment.isLocationPreferenceChecked() && position == mPositionLastChecked)) {
            holder.ctvAlumniClassOf.setSelected(true);
            holder.ctvAlumniClassOf.setCheckMarkDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_check_box_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return mAlumniClassOfList.size();
    }

    class AlumniClassOfViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ctvAlumniClassOf)
        CheckedTextView ctvAlumniClassOf;

        public AlumniClassOfViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ctvAlumniClassOf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //wherever the click be, set the last checked position false if it's there
                    if(mFilterAlumniFragment.isClassOfPreferenceChecked()) {
                        AlumniClassOfViewHolder viewHolder = (AlumniClassOfViewHolder)
                                mRecyclerView.findViewHolderForAdapterPosition(mPositionLastChecked);
                        if(viewHolder != null) {
                            viewHolder.ctvAlumniClassOf.setSelected(false);
                            viewHolder.ctvAlumniClassOf.setCheckMarkDrawable(mContext.getResources()
                                    .getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
                        }
                    }

                    //Clicked on  somewhere else or no checks
                    if (!mFilterAlumniFragment.isClassOfPreferenceChecked()
                            || getAdapterPosition() != mPositionLastChecked) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.setCheckMarkDrawable(mContext.getResources()
                                .getDrawable(R.drawable.ic_check_box_black_24dp));
                        mPositionLastChecked = getAdapterPosition();
                        mFilterAlumniFragment.setmAlumniClassOfCheckedPoistion(mPositionLastChecked);
                        mFilterAlumniFragment.setClassOfPreferenceChecked(true);
                        ctv.toggle();
                    }else {
                        mFilterAlumniFragment.setClassOfPreferenceChecked(false);
                    }
                }
            });
        }
    }
}
