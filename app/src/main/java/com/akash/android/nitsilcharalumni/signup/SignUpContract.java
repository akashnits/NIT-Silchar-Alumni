package com.akash.android.nitsilcharalumni.signup;

import com.akash.android.nitsilcharalumni.BasePresenter;
import com.akash.android.nitsilcharalumni.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */

public class SignUpContract {

    public interface View extends BaseView<Presenter>{

    }

    public interface Presenter extends BasePresenter{

    }
}
