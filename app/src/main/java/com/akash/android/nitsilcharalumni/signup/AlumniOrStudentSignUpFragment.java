package com.akash.android.nitsilcharalumni.signup;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.akash.android.nitsilcharalumni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniOrStudentSignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniOrStudentSignUpFragment extends Fragment {


    @BindView(R.id.tvClassof)
    TextView tvClassof;
    @BindView(R.id.spinnerClassOf)
    Spinner spinnerClassOf;
    @BindView(R.id.tvOrganisation)
    TextView tvOrganisation;
    @BindView(R.id.etOrganisation)
    EditText etOrganisation;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.btCreateAccount)
    Button btCreateAccount;
    Unbinder unbinder;

    public AlumniOrStudentSignUpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlumniOrStudentSignUpFragment newInstance(boolean isAlumnus) {
        AlumniOrStudentSignUpFragment fragment = new AlumniOrStudentSignUpFragment();
        Bundle b = new Bundle();
        b.putBoolean("alumnus", isAlumnus);
        fragment.setArguments(b);
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
        View view = inflater.inflate(R.layout.fragment_alumni_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            boolean isAlumnus = getArguments().getBoolean("alumnus");
            if(isAlumnus){
                textInputLayout2.setVisibility(View.VISIBLE);
                tvOrganisation.setVisibility(View.VISIBLE);
            }else{
                textInputLayout2.setVisibility(View.GONE);
                tvOrganisation.setVisibility(View.GONE);
            }
        }
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.classOf, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassOf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView != null && adapterView.getChildCount() != 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerClassOf.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
