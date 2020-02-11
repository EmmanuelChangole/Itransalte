package com.translateit.itransalte;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.translateit.itransalte.data.StaticConfig;
import com.translateit.itransalte.utils.Firebase;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class LoginActivity extends AppCompatActivity {
   private Button butLogin;
   private FloatingActionButton fab;
   private EditText edEmail,edPassword;
   private Context context;
    private LovelyProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                login();

            }
        });


    }

    private void login()
    {
        String email=edEmail.getText().toString();
        String password=edPassword.getText().toString();

        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edEmail.setError(getString(R.string.error_email));
            edEmail.requestFocus();
            return;
        }

        if(password.isEmpty()||password.length()<6)
        {
            edPassword.setError(getString(R.string.error_password));
            edPassword.requestFocus();
            return;
        }

        loginUser(email,password);


    }

    private void loginUser(String email, String password)
    {

        Firebase firebase=new Firebase(context);
        firebase.loginUser(email,password);
    }

    private void getView()
    {
        context=getApplicationContext();
        fab=(FloatingActionButton)findViewById(R.id.fab);
        butLogin=(Button)findViewById(R.id.butLogin);
        edEmail=(EditText)findViewById(R.id.edEmail);
        edPassword=(EditText)findViewById(R.id.edPassword);

    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createAccount(View view)
    {
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivityForResult(new Intent(this, RegisterActivity.class), StaticConfig.REQUEST_CODE_REGISTER, options.toBundle());
        } else {
            startActivityForResult(new Intent(this, RegisterActivity.class), StaticConfig.REQUEST_CODE_REGISTER);
        }

    }
}
