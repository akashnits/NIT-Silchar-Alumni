package com.akash.android.nitsilcharalumni.di.component;

import com.akash.android.nitsilcharalumni.di.PerFragment;
import com.akash.android.nitsilcharalumni.di.module.AlumniOrStudentSignUpFragmentModule;
import com.akash.android.nitsilcharalumni.signup.alumniOrStudentSignUpFragment.AlumniOrStudentSignUpFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = AlumniOrStudentSignUpFragmentModule.class)
public interface AlumniOrStudentSignUpFragmentComponent {

    void inject(AlumniOrStudentSignUpFragment alumniOrStudentSignUpFragment);

}
