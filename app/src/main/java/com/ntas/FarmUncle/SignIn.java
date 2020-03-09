package com.ntas.FarmUncle;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignIn extends Fragment implements View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 3011;

    private View v;


    //Email Password Authentication Views
    private EditText email_login, password_login;
    private Button login_e;

    //TextView
    private TextView error1, error2;


    //Authentication
    private FirebaseAuth mAuth;

    //Google Sign In Client
    private GoogleSignInClient mGoogleSignInClient;


    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        v = view;
        // Inflate the layout for this fragment

        //Edittexts
        email_login = view.findViewById(R.id.email);
        password_login = view.findViewById(R.id.password);


        //Button
        SignInButton signinButton = view.findViewById(R.id.google_button);
        login_e = view.findViewById(R.id.emailSignInButton);

        //TextViews
        error1 = view.findViewById(R.id.email_error);
        error2 = view.findViewById(R.id.password_error);


        signinButton.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, RC_SIGN_IN);
    }


    //GOOGLE SIGN IN: onactivityresult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null, 0);
                // [END_EXCLUDE]
            }
        }
    }


    //GOOGLE SIGN IN: auth_with_google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, RC_SIGN_IN);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(getView().findViewById(R.id.signin_fragment), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null, 0);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }


    //GOOGLE SIGN IN: signin
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void updateUI(FirebaseUser user, int value) {
        if (user != null) {
            if (value == RC_SIGN_IN) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            } else if (value == 2) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            } else if (value == 0) {
                mainpage();
            }
        } else {
            mainpage();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.google_button) {
            signIn();
        }
    }


    private void signinwithemail(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) return;


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            Log.d(TAG, "User: " + user.getEmail());
                            updateUI(user, 2);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Login Failed: Try Again", Toast.LENGTH_SHORT).show();
                            updateUI(null, 0);
                        }
                    }
                });

    }


    private boolean validateForm() {
        boolean valid = true;

        String email = email_login.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            error1.setText("Required*");
            valid = false;
        } else {
            error1.setText("");
        }

        String password = password_login.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            error2.setText("Required*");
            valid = false;
        } else if (password.length() < 8) {
            error2.setText("Minimum: 8 Characters");
        } else {
            error2.setText("");
        }

        return valid;
    }

    private void mainpage() {

        login_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinwithemail(email_login.getText().toString().trim(), password_login.getText().toString().trim());

            }
        });
    }

}
