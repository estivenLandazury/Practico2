package com.example.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth maAuth;
    private EditText edt_password, edt_mail;
    private Button btn_singIg, btn_singUp;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        maAuth = FirebaseAuth.getInstance();

        edt_password = findViewById(R.id.et_password);
        edt_mail = findViewById(R.id.et_email);
        btn_singIg = findViewById(R.id.btn_Login);
        btn_singUp = findViewById(R.id.btn_register);

        btn_singUp.setOnClickListener(this);
        btn_singIg.setOnClickListener(this);




    }


    public void registrarUsuario() {

        String hotmail = edt_mail.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if (TextUtils.isEmpty(hotmail) || TextUtils.isEmpty(password)) {
            mensaje("Ninguno de los campos puede estar vacio");
        }
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Realizando registro espere ...");
        progressDialog.show();

        //crear un usario y registrarlo

        maAuth.createUserWithEmailAndPassword(hotmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mensaje("El registro se realizó correctamente");
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        mensaje("este usuario ya se encuentra registrado");

                    }
//                    mensaje("No se pudo realizar el registrar el registro");
                }

                progressDialog.dismiss();
            }
        });


    }


    private void loguearUsuario(String mail, String password) {


        if (TextUtils.isEmpty(mail.trim()) || TextUtils.isEmpty(password.trim())) {
            mensaje("Ninguno de los campos puede estar vacio");
        }
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Realizando registro espere ...");
        progressDialog.show();

        //loguear usuario

        maAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mensaje("Bienvenido");
                    FirebaseUser currentUser = maAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("user", currentUser.getEmail());
                    startActivity(intent);


                } else {
                    mensaje("No se pudo realizar el acceso");


                }


                progressDialog.dismiss();
            }
        });


    }


    public void mensaje(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register) {
            registrarUsuario();
//            mensaje("hola landa");

        }

        if (v.getId() == R.id.btn_Login) {
            String hotmail = edt_mail.getText().toString().trim();
            String password = edt_password.getText().toString().trim();
            loguearUsuario(hotmail, password);

        }

//        mensaje("hola landa");

    }
}
