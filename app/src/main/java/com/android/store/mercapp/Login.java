package com.android.store.mercapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView txtB, txtL, txtL1,txtL2;
    private EditText txtEmail, txtPass;
    private Button Bttonlogin;
    private Typeface ProductSans;
    private Typeface ProductSansBold;
    private TextInputLayout txtemail,pass;
    private ProgressDialog progressLogin;
    private LoginButton loginButton;
    private ImageButton FacebookButton;
    private CallbackManager mCallbackManager;
    private  static final String TAG="FaceLog";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ImageView imageProfile;
    private TextView nameProfile,emailProfile;
    private String name,email;
    private Uri photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FacebookButton = (ImageButton) findViewById(R.id.LoginFacebookBtn);
        mCallbackManager = CallbackManager.Factory.create();
        txtB = (TextView) findViewById(R.id.TituloBienvenida);
        txtL = (TextView) findViewById(R.id.txtLogin);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPass = (EditText) findViewById(R.id.Password);
        Bttonlogin = (Button) findViewById(R.id.btnLogin);
        txtemail = (TextInputLayout) findViewById(R.id.emailuser);
        pass = (TextInputLayout) findViewById(R.id.passworduser);
        txtL1 = (TextView) findViewById(R.id.txtLabel1);
        txtL2 = (TextView) findViewById(R.id.txtLabel2);
        Bttonlogin.setOnClickListener( this);
        progressLogin = new ProgressDialog(this);
        String fuente = "fonts/ProductSansRegular.ttf";
        this.ProductSans = Typeface.createFromAsset(getAssets(),fuente);

        String fuenteNegrita = "fonts/ProductSansBold.ttf";
        this.ProductSansBold = Typeface.createFromAsset(getAssets(),fuenteNegrita);


        txtB.setTypeface(ProductSans);
        txtL.setTypeface(ProductSansBold);
        txtEmail.setTypeface(ProductSans);
        txtPass.setTypeface(ProductSans);
        Bttonlogin.setTypeface(ProductSans);
        txtemail.setTypeface(ProductSans);
        pass.setTypeface(ProductSans);
        txtL1.setTypeface(ProductSans);
        txtL2.setTypeface(ProductSans);

        txtL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
            }
        });

        FacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user!= null){
                    goToMainScreen();
                }
            }
        };









    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI(currentUser);
        }*/

        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    private void goToMainScreen() {
            Toast.makeText(Login.this, "Inicio de sesion exitoso", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void LoginUser(){
        String email = txtEmail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        if (email.equals(""))
        {
            Toast.makeText(this, "Debe ingresar su email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.equals(""))
        {
            Toast.makeText(this, "Debe ingresar su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressLogin.setMessage("Iniciando sesion por favor espere");
        progressLogin.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {

                    Toast.makeText(Login.this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();

                }
                progressLogin.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {
        LoginUser();
    }




}
