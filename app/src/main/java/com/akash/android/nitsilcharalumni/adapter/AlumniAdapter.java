package com.akash.android.nitsilcharalumni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.R;
import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.ui.MainActivity;
import com.akash.android.nitsilcharalumni.ui.alumni.AlumniFragment;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.akash.android.nitsilcharalumni.utils.imageUtils.PicassoCircleTransformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.akash.android.nitsilcharalumni.ui.alumni.AlumniFragment.LIMIT;


public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder> implements Filterable {

    private Context mContext;
    private OnAlumniClickHandler mHandler;
    private ArrayList<User> mAlumniList;
    private FirebaseFirestore mFirestore;
    private DocumentSnapshot mLastVisibleLocation = null;
    private DocumentSnapshot mLastVisibleClassOf = null;
    private DocumentSnapshot mLastVisibleBoth = null;
    private AlumniFilter mAlumniFilter;
    private AlumniFragment mAlumniFragment;
    private MainActivity mMainActivity;

    public interface OnAlumniClickHandler {
        void onAlumniClicked(String email, View view);
    }


    public AlumniAdapter(Context mContext, OnAlumniClickHandler mHandler, AlumniFragment alumniFragment) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        mAlumniList = new ArrayList<>();
        mFirestore = FirebaseFirestore.getInstance();
        mAlumniFragment = alumniFragment;
        this.mMainActivity= (MainActivity) mAlumniFragment.getActivity();
    }

    @Override
    public AlumniViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alumni,
                parent, false);
        return new AlumniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumniViewHolder holder, int position) {

        User alumni = mAlumniList.get(position);
        if (alumni != null) {
            holder.tvName.setText(alumni.getmName());
            holder.tvOrganisationName.setText(alumni.getmOrganisation());
            holder.tvClassOf.setText(String.format("Class of %s,", alumni.getmClassOf()));
            loadProfileImageWithPicasso(alumni.getmProfileImageUrl(), holder);
        }
    }

    private void loadProfileImageWithPicasso(String imageUrl, AlumniViewHolder holder) {
        if (imageUrl != null) {
            Picasso.with(mContext).load(imageUrl)
                    .transform(new PicassoCircleTransformation())
                    .into(holder.ivAlumniProfileIcon);
        } else {
            Picasso.with(mContext).load(R.drawable.person)
                    .transform(new PicassoCircleTransformation())
                    .into(holder.ivAlumniProfileIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mAlumniList.size();
    }

    public void addAll(List<User> newAlumni) {
        int initialSize = mAlumniList.size();
        mAlumniList.addAll(newAlumni);
        notifyItemRangeInserted(initialSize, newAlumni.size());
    }

    public void replaceAll(List<User> newAlumni){
        //get the current items
        int currentSize = mAlumniList.size();
        //remove the current items
        if(currentSize > 0)
            mAlumniList.clear();
        //add all the new items
        mAlumniList.addAll(newAlumni);
        //tell the recycler view that all the old items are gone
        if(currentSize > 0)
            notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mAlumniList.size());

    }

    public void addAsPerFilterFirstLoad(List<User> newAlumni){
        //get the current items
        int currentSize = mAlumniList.size();
        //remove the current items
        mAlumniList.clear();
        //add all the new items
        mAlumniList.addAll(newAlumni);
        //tell the recycler view that all the old items are gone
        notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mAlumniList.size());
    }

    public void addAsPerSearch(List<User> searchedFeed) {
        //get the current items
        int currentSize = mAlumniList.size();
        //remove the current items
        mAlumniList.clear();
        //add all the new items
        mAlumniList.addAll(searchedFeed);
        //tell the recycler view that all the old items are gone
        notifyItemRangeRemoved(0, currentSize);
        //tell the recycler view how many new items we added
        notifyItemRangeInserted(0, mAlumniList.size());
    }

    public void replaceWithInitialList(User[] originalArray, int currentSize) {
        mAlumniList.clear();
        mAlumniList.addAll(Arrays.asList(originalArray));
        notifyItemRangeRemoved(0, currentSize);
        notifyItemRangeInserted(0, mAlumniList.size());
    }

    public void setEmptyView() {
        int currentSize = mAlumniList.size();
        mAlumniList.clear();
        notifyItemRangeRemoved(0, currentSize);
    }

    class AlumniViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAlumniProfileIcon)
        ImageView ivAlumniProfileIcon;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvClassOf)
        TextView tvClassOf;
        @BindView(R.id.tvOrganisationName)
        TextView tvOrganisationName;

        public AlumniViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = mAlumniList.get(getAdapterPosition()).getmEmail();
                    mHandler.onAlumniClicked(email, view);
                }
            });
        }
    }

    public ArrayList<User> getmAlumniList() {
        return mAlumniList;
    }

    public void setmAlumniList(ArrayList<User> mAlumniList) {
        this.mAlumniList = mAlumniList;
    }

    @Override
    public Filter getFilter() {
        if (mAlumniFilter == null) {
            mAlumniFilter = new AlumniFilter();
        }
        return mAlumniFilter;
    }

    class AlumniFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            //do nothing
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (constraint != null && constraint.length() > 0) {
                final ArrayList<User> filterList = new ArrayList<>();
                if (mMainActivity.getmAlumniLocationConstraint() != null &&
                        mMainActivity.getmAlumniClassOfConstraint() != null) {
                    mAlumniFragment.setLoading(true);
                    String[] constraints = constraint.toString().split(",");
                    Query query;
                    if (mLastVisibleBoth != null) {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .startAfter(mLastVisibleBoth)
                                .limit(LIMIT)
                                .whereEqualTo("mLocation", constraints[0])
                                .whereEqualTo("mClassOf", constraints[1]);
                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleBoth != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleBoth = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    } else {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .limit(LIMIT)
                                .whereEqualTo("mLocation", constraints[0])
                                .whereEqualTo("mClassOf", constraints[1]);

                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleBoth != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleBoth = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                                setEmptyView();
                                                Toast.makeText(mContext, R.string.nothing_to_show, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    }
                } else if (mMainActivity.getmAlumniLocationConstraint() != null) {
                    mAlumniFragment.setLoading(true);
                    Query query;
                    if (mLastVisibleLocation != null) {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .startAfter(mLastVisibleLocation)
                                .limit(LIMIT)
                                .whereEqualTo("mLocation", constraint.toString());
                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleLocation != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleLocation = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    } else {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .limit(LIMIT)
                                .whereEqualTo("mLocation", constraint.toString());
                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleLocation != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleLocation = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                                setEmptyView();
                                                Toast.makeText(mContext, R.string.nothing_to_show, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    }
                } else if (mMainActivity.getmAlumniClassOfConstraint() != null) {
                    mAlumniFragment.setLoading(true);
                    Query query;
                    if (mLastVisibleClassOf != null) {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .startAfter(mLastVisibleClassOf)
                                .limit(LIMIT)
                                .whereEqualTo("mClassOf", constraint.toString());
                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleClassOf != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleClassOf = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    } else {
                        query = mFirestore.collection(Constants.USER_COLLECTION)
                                .whereEqualTo("mTypeOfUser", "Alumni")
                                .orderBy("mEmail")
                                .limit(LIMIT)
                                .whereEqualTo("mClassOf", constraint.toString());

                        query
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(documentSnapshots.size());
                                            }
                                            for (DocumentSnapshot documentSnapshot : documentSnapshots)
                                                filterList.add(documentSnapshot.toObject(User.class));
                                            if (filterList.size() > 0) {
                                                if(mLastVisibleClassOf != null)
                                                    addAll(filterList);
                                                else
                                                    addAsPerFilterFirstLoad(filterList);
                                            }
                                            mLastVisibleClassOf = documentSnapshots.getDocuments()
                                                    .get(documentSnapshots.size() - 1);
                                        } else {
                                            if (mAlumniFragment != null) {
                                                mAlumniFragment.setmLastDocumentSnapshotSize(0);
                                                setEmptyView();
                                                Toast.makeText(mContext, R.string.nothing_to_show, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        mAlumniFragment.setLoading(false);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(mContext, R.string.failed_to_load_data, Toast.LENGTH_SHORT).show();
                                        mAlumniFragment.setLoading(false);
                                    }
                                });
                    }
                }
            }
        }
    }

    public DocumentSnapshot getmLastVisibleLocation() {
        return mLastVisibleLocation;
    }

    public void setmLastVisibleLocation(DocumentSnapshot mLastVisibleLocation) {
        this.mLastVisibleLocation = mLastVisibleLocation;
    }

    public DocumentSnapshot getmLastVisibleClassOf() {
        return mLastVisibleClassOf;
    }

    public void setmLastVisibleClassOf(DocumentSnapshot mLastVisibleClassOf) {
        this.mLastVisibleClassOf = mLastVisibleClassOf;
    }

    public DocumentSnapshot getmLastVisibleBoth() {
        return mLastVisibleBoth;
    }

    public void setmLastVisibleBoth(DocumentSnapshot mLastVisibleBoth) {
        this.mLastVisibleBoth = mLastVisibleBoth;
    }

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }
}
