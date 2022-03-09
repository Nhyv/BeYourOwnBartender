package com.example.beyourownbartender;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapterList adapterList;
    Button btDisconnect;
    Button btAdminMode;
    Button btProfileRecipes;
    Button btProfileLikes;
    Button btProfileRobot;
    Button btDemandeIngredient;
    TextView profileUsername;

    UserDisplay user;
    LoggedInUser loggedInUser;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileUsername = findViewById(R.id.profileUsername);
        btDisconnect = findViewById(R.id.btDisconnect);
        btAdminMode = findViewById(R.id.btAdminMode);
        btAdminMode.setVisibility(View.INVISIBLE);
        btProfileLikes = findViewById(R.id.btProfileLikes);
        btProfileRecipes = findViewById(R.id.btProfileRecipes);
        btProfileRobot = findViewById(R.id.btProfileRobot);
        btDemandeIngredient = findViewById(R.id.btDemandeIngredient);

        pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
        editor = pref.edit();
        context = this;

        String username = pref.getString("username", "N/A");
        int userId = pref.getInt("userId", 0);
        profileUsername.setText(username);
        getSupportActionBar().setTitle("Paramètres");

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<UserDisplay> call = server.getUserById(userId);
        call.enqueue(new Callback<UserDisplay>() {
            @Override
            public void onResponse(Call<UserDisplay> call, Response<UserDisplay> response) {
                user = response.body();
                if (user.isAdmin) {
                    btAdminMode.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserDisplay> call, Throwable t) {

            }
        });

        btDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Déconnexion");
                builder.setMessage("Voulez-vous vous déconnecter?");
                builder.setPositiveButton("Se déconnecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("username", "N/A");
                        editor.commit();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);

                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btProfileRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MyRecipesActivity.class);

                startActivity(intent);
            }
        });
        btProfileLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MyLikesActivity.class);

                startActivity(intent);
            }
        });
        btProfileRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, RobotActivity.class);

                startActivity(intent);
            }
        });

        btDemandeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, DemandeIngredientActivity.class);

                startActivity(intent);
            }
        });

        btAdminMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = pref.getString("username", "N/A");
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Menu Admin - Connexion");
                alert.setMessage("Entrez votre mot de passe: ");
                EditText input = new EditText(context);
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                alert.setView(input);

                alert.setPositiveButton("Accéder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<LoggedInUser> call =
                                server.getLogin(new Login(username,
                                        input.getText().toString()));

                        call.enqueue(new Callback<LoggedInUser>() {
                            @Override
                            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                                if (response.code() == 200) {
                                    Intent intent = new Intent(ProfileActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                            }
                        });
                    }
                });
                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                alert.show();
            }
        });
    }
}