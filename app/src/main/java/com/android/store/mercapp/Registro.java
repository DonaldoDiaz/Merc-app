package com.android.store.mercapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private TextView tituloR1,tituloR2,labelTc;
    private EditText User,txtEmailR,txtPassR;
    private Button BotonRegistro;
    private Typeface ProductSans;
    private Typeface ProductSansBold;
    private TextInputLayout layoutUserR,layoutEmailR,LayoutPassR;
    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        tituloR1 = (TextView) findViewById(R.id.tituloRegistro1);
        tituloR2 = (TextView) findViewById(R.id.tituloRegistro2);
        labelTc = (TextView) findViewById(R.id.txtTerminosC);
        User = (EditText) findViewById(R.id.usernameRegistro);
        txtEmailR = (EditText) findViewById(R.id.EmailRegistro);
        txtPassR = (EditText) findViewById(R.id.passwordRegistro);
        BotonRegistro = (Button) findViewById(R.id.BtnRegistro);
        layoutUserR = (TextInputLayout) findViewById(R.id.layoutUserName);
        layoutEmailR = (TextInputLayout) findViewById(R.id.LayoutEmail);
        LayoutPassR = (TextInputLayout) findViewById(R.id.LayoutPassword);
        progress = new ProgressDialog(this);
        BotonRegistro.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        String fuente = "fonts/ProductSansRegular.ttf";
        this.ProductSans = Typeface.createFromAsset(getAssets(),fuente);

        String fuenteNegrita = "fonts/ProductSansBold.ttf";
        this.ProductSansBold = Typeface.createFromAsset(getAssets(),fuenteNegrita);

        tituloR1.setTypeface(ProductSans);
        tituloR2.setTypeface(ProductSans);
        labelTc.setTypeface(ProductSans);
        User.setTypeface(ProductSans);
        txtEmailR.setTypeface(ProductSans);
        txtPassR.setTypeface(ProductSans);
        BotonRegistro.setTypeface(ProductSans);
        layoutUserR.setTypeface(ProductSans);
        layoutEmailR.setTypeface(ProductSans);
        LayoutPassR.setTypeface(ProductSans);
    }


    private void RegistrarUsuario(){
        String email = txtEmailR.getText().toString().trim();
        String password = txtPassR.getText().toString().trim();

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

        progress.setMessage("Registrando usuario..");
        progress.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(Registro.this, "Su usuario ha sido registrado con exito", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(Registro.this, "Este usuario ya existe ", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Registro.this, "Su usuario NO ha sido registrado con exito", Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        RegistrarUsuario();
    }
}
