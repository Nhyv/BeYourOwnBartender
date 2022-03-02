package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword;
    Button btInscrire;
    boolean userFilled, emailFilled, pwFilled;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btInscrire = findViewById(R.id.btInscrire);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    if (emailFilled && pwFilled) {
                        btInscrire.setEnabled(true);
                    }
                    userFilled = true;
                }
                else {
                    btInscrire.setEnabled(false);
                    userFilled = false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    if (userFilled && pwFilled) {
                        btInscrire.setEnabled(true);
                    }
                    emailFilled = true;
                }
                else {
                    btInscrire.setEnabled(false);
                    emailFilled = false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    if (emailFilled && userFilled) {
                        btInscrire.setEnabled(true);
                    }
                    pwFilled = true;
                }
                else {
                    btInscrire.setEnabled(false);
                    pwFilled = false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                Call<Void> call = server.addUser(new Registration(etUsername.getText().toString(),
                        etEmail.getText().toString(), etPassword.getText().toString()));

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(RegisterActivity.this,
                                    LoginActivity.class);

                            startActivity(intent);
                            finish();
                        }
                        else if (response.code() == 500) {
                            Toast.makeText(context, "Nom d'utilisateur non disponible.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(context, "Une erreur est survenue.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}