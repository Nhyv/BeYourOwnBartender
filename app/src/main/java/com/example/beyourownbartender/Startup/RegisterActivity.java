package com.example.beyourownbartender.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword, etPasswordConfirm;
    Button btInscrire;
    boolean userFilled, emailFilled, pwFilled, pwConfirmFilled;
    Context context;
    TextView tvErrorReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        context = this;
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btInscrire = findViewById(R.id.btInscrire);
        tvErrorReg = findViewById(R.id.tvErreurRegi);
        tvErrorReg.setVisibility(View.INVISIBLE);

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
                    if (emailFilled && userFilled && pwConfirmFilled) {
                        if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                            tvErrorReg.setVisibility(View.INVISIBLE);
                            if (charSequence.length() > 10)
                                btInscrire.setEnabled(true);
                        }
                        else {
                            tvErrorReg.setVisibility(View.VISIBLE);
                        }

                    }
                    if (pwConfirmFilled){
                        if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                            tvErrorReg.setVisibility(View.INVISIBLE);
                        }
                        else {
                            tvErrorReg.setVisibility(View.VISIBLE);
                        }
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
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    if (emailFilled && userFilled && pwFilled) {
                        if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                            btInscrire.setEnabled(true);
                            tvErrorReg.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if (pwFilled) {
                        if (etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                            tvErrorReg.setVisibility(View.INVISIBLE);
                        }
                        else {
                            tvErrorReg.setVisibility(View.VISIBLE);
                        }
                    }
                    pwConfirmFilled = true;
                }
                else {
                    btInscrire.setEnabled(false);
                    pwFilled = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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