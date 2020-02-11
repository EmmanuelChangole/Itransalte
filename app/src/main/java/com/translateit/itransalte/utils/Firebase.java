package com.translateit.itransalte.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.translateit.itransalte.LoginActivity;
import com.translateit.itransalte.MainActivity;
import com.translateit.itransalte.R;
import com.translateit.itransalte.RegisterActivity;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class Firebase
{

    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private FirebaseUser user;
    private Context context;

                 /*Firebase constructor*/
    public Firebase(Context context)
    {
        this.context=context;
        mDialog=new ProgressDialog(this.context);
        mAuth=FirebaseAuth.getInstance();


    }
                      /*Create account*/
    public void createAccount(String email, String password)
    {


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {    mDialog.dismiss();
                    Toast.makeText(context, "account created", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {    mDialog.dismiss();
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

            }
        });


    }

                    /*Login user using*/
    public void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();
                    Intent login=new Intent(context, MainActivity.class);
                    context.startActivity(login);


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

                    /*initialize the mAuthListener*/
    public void initFirebase() {

        mAuthLitener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                checkerLoggedIn(user);

            }
        };


    }



    private void checkerLoggedIn(FirebaseUser currentUser)
    {
        if (currentUser == null) {
            Intent login = new Intent(context, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(login);

        }

    }

    public void onChangeState()
    {
        mAuth.addAuthStateListener(mAuthLitener);
        checkerLoggedIn(mAuth.getCurrentUser());

    }
}
