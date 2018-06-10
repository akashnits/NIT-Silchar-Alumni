package com.akash.android.nitsilcharalumni.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.akash.android.nitsilcharalumni.model.User;
import com.akash.android.nitsilcharalumni.signup.placeAutoComplete.PlaceAutoCompleteFragment;
import com.akash.android.nitsilcharalumni.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginInteractor  {

    public static final String TAG = LoginInteractor.class.getSimpleName();

    private static final int RC_SIGN_IN = 1565;

    private LoginContract.Presenter mLoginPresenter;

    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;

    private FirebaseFirestore mFirebaseFirestore;

    public LoginInteractor() {
    }

    public LoginInteractor(LoginContract.Presenter mLoginPresenter) {
        this.mLoginPresenter = mLoginPresenter;
        mFirebaseFirestore= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    /*
    Sign In using firebase credentials
     */
    void signInWithEmailAndPassword(final Activity activity, String email, char[] password) {
        mAuth.signInWithEmailAndPassword(email, String.valueOf(password))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            downloadUserData();
                            mLoginPresenter.loadMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            mLoginPresenter.loadErrorMessage();
                        }
                    }
                });
    }

    /*
    Sign in using Google sign in button
     */
    void signInWithGoogleCreds(GoogleSignInClient googleSignInClient, Activity context) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity context) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account, context);
                //show progress
                mLoginPresenter.loadProgressBar();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                mLoginPresenter.loadGoogleSignInErrorMessage();
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*
    Exchanging google account details for firebase credentials
     */

    private void firebaseAuthWithGoogle(GoogleSignInAccount account, final Activity context) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success, now check to see if we have data about this user
                            // if not then ask for it
                            writeUserToFirebase();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mLoginPresenter.loadGoogleSignInErrorMessage();
                            mLoginPresenter.hideProgressBar();
                        }
                    }
                });
    }

    /*
    Sign in using Facebook login button
     */

    public void signInWithFacebookCredentials(LoginButton fbLoginButton, final Activity context) {
        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken(), context);
                mLoginPresenter.loadProgressBar();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                mLoginPresenter.loadFacebookSignInErrorMessage();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token, Activity context) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            writeUserToFirebase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mLoginPresenter.loadFacebookSignInErrorMessage();
                            mLoginPresenter.hideProgressBar();
                        }
                    }
                });
    }

    private void downloadUserData() {
        String authorEmail = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : null;
        if (authorEmail != null) {
            //get user's display name
            mFirebaseFirestore.collection(Constants.USER_COLLECTION)
                    .document(authorEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    User user = document.toObject(User.class);
                                    mLoginPresenter.saveLoggedInUserData(user);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }
    }

    private void writeUserToFirebase(){
        FirebaseUser user = mAuth.getCurrentUser();

        final String email;
        if(user != null){
            email = user.getEmail();
            User u= new User();
            u.setmName(user.getDisplayName());
            mLoginPresenter.saveLoggedInUserData(u);
        }else
            email= null;

        mFirebaseFirestore.collection(Constants.USER_COLLECTION)
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //stop progress
                        mLoginPresenter.hideProgressBar();
                        DocumentSnapshot documentSnapshot= task.getResult();
                        if(!documentSnapshot.exists()){
                            mLoginPresenter.loadSocialLoginForm(email);
                        }else {
                            mLoginPresenter.loadMainActivity();
                        }
                    }
                });
    }
}

