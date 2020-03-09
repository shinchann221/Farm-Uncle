package com.ntas.FarmUncle;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {
    private static final String TAG = "Register";
    EditText fname, lname, mobile, email, password;
    Button submit;
    private FirebaseAuth mauth;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AlertDialog alertDialog;


    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fname = view.findViewById(R.id.fname);
        lname = view.findViewById(R.id.lname);
        mobile = view.findViewById(R.id.mobile);
        email = view.findViewById(R.id.email_register);
        password = view.findViewById(R.id.password_register);


        //Firebase Instance
        mauth = FirebaseAuth.getInstance();

        //ProgressBar
        progressBar = view.findViewById(R.id.progressBar);

        //Button
        submit = view.findViewById(R.id.register);

        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Email Verification")
                .setMessage("Email Verification Link Sent to your Email Address")
                .create();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateform()) {
                    createaccount(email.getText().toString(), password.getText().toString());
                }
            }
        });
        return view;

    }

    public void createaccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        progressBar.setVisibility(View.VISIBLE);
        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = mauth.getCurrentUser();
                            assert user != null;
                            sendEmailVerification(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();

                            updateUI(null, 0);

                        }
                    }
                });
    }


    private void sendEmailVerification(final FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateUI(user, 0);
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getActivity(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User account deleted.");
                                            }
                                        }
                                    });
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }


    private boolean validateform() {
        boolean valid = true;


        //firstname
        String fn = fname.getText().toString().trim();
        if (TextUtils.isEmpty(fn)) {
            fname.setError("Required");
            valid = false;
        } else fname.setError(null);


        //lastname
        String ln = lname.getText().toString().trim();
        if (TextUtils.isEmpty(ln)) {
            lname.setError("Required");
            valid = false;
        } else lname.setError(null);


        //mobile number
        String mob = mobile.getText().toString().trim();
        if (TextUtils.isEmpty(mob) || mob.length() != 10) {
            mobile.setError("Required");
            valid = false;
        } else mobile.setError(null);


        String id = email.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            email.setError("Required");
            valid = false;
        } else email.setError(null);

        String pass = password.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            password.setError("Requried");
            valid = false;
        } else password.setError(null);

        return valid;
    }


    private void uploaddata() {
        final FirebaseUser user = mauth.getCurrentUser();
        String fn = fname.getText().toString();
        String ln = lname.getText().toString();
        String mob = mobile.getText().toString();
        String emailid = email.getText().toString();
        String pass = password.getText().toString();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("first_name", fn);
        user1.put("last_name", ln);
        user1.put("mobile", mob);
        user1.put("Email", emailid);
        user1.put("Password", pass);


// Add a new document with a generated ID
        db.collection("Users(Arat App)").document(emailid)
                .set(user1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "successful", Toast.LENGTH_SHORT).show();
                        updateUI(user, 2);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User account deleted.");
                                        }
                                    }
                                });
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getActivity(), "Unexpected Failure.. Try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        final FirebaseUser user = mauth.getCurrentUser();

        if (user != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    while (!user.isEmailVerified()) {
                        alertDialog.dismiss();
                        new AlertDialog.Builder(getContext())
                                .setTitle("Verify")
                                .setMessage("Please verify your email to continue")
                                .setPositiveButton("Resend", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sendEmailVerification(user);
                                    }
                                })
                                .show();

                        mauth.getCurrentUser().reload();
                    }

                }
            }, 300);


            updateUI(user, 1);
        } else {

        }

    }

    public void updateUI(FirebaseUser user, int a) {

        if (user != null) {
            if (a == 0) {
                alertDialog.show();

            } else if (a == 1) {
                uploaddata();
            } else if (a == 2) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        } else {
            Intent intent = getActivity().getIntent();
            startActivity(intent);
        }
    }
}
