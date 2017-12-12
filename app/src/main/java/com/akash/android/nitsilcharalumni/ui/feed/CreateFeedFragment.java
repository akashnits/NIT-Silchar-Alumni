package com.akash.android.nitsilcharalumni.ui.feed;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFeedFragment extends Fragment implements FABProgressListener {


    @BindView(R.id.tvFeedDescription)
    TextView tvFeedDescription;
    @BindView(R.id.tvFeedKeywords)
    TextView tvFeedKeywords;
    @BindView(R.id.edit_text_feed_keywords)
    EditText editTextFeedKeywords;
    @BindView(R.id.til_feed_keywords)
    TextInputLayout tilFeedKeywords;
    @BindView(R.id.btSelectImage)
    Button btSelectImage;
    @BindView(R.id.uploadFab)
    FloatingActionButton uploadFab;
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;
    @BindView(R.id.btPost)
    Button btPost;
    @BindView(R.id.edit_text_feed_description)
    EditText editTextFeedDescription;
    @BindView(R.id.til_feed_description)
    TextInputLayout tilFeedDescription;
    Unbinder unbinder;

    public CreateFeedFragment() {
        // Required empty public constructor
    }

    public static CreateFeedFragment newInstance() {
        CreateFeedFragment fragment = new CreateFeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_feed, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabProgressCircle.attachListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.uploadFab)
    public void onViewClicked() {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                fabProgressCircle.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try{
                Thread.sleep(5000);}
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                fabProgressCircle.beginFinalAnimation();
            }
        }.execute();
    }

    @Override
    public void onFABProgressAnimationEnd() {
        Snackbar.make(fabProgressCircle, "Upload Complete", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
}
