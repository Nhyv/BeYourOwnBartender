package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    boolean filledUsername, filledPassword;
    Button login, register;
    LoggedInUser user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    filledUsername = true;
                    if (filledPassword)
                        login.setEnabled(true);
                }
                else {
                    filledUsername = false;
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    filledPassword = true;
                    if (filledUsername)
                        login.setEnabled(true);
                }
                else {
                    filledPassword = false;
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                Call<LoggedInUser> call =
                        server.getLogin(new Login(username.getText().toString(),
                                password.getText().toString()));

                call.enqueue(new Callback<LoggedInUser>() {
                    @Override
                    public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                        if (response.code() == 200) {
                            SharedPreferences pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            user = response.body();
                            editor.putInt("userId", user.getUserId());
                            editor.putString("username", user.getUsername());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(context,
                                    "Nom d'utilisateur ou mot de passe invalide",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoggedInUser> call, Throwable t) {

                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }
}