package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class DemandeIngredientActivity extends AppCompatActivity {
    EditText etNomIngr;
    Button btAjoutInvent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_ingredient);

        etNomIngr = findViewById(R.id.etNomIngr);
        btAjoutInvent = findViewById(R.id.btAjoutInvent);
        btAjoutInvent.setEnabled(false);

        context = this;

        etNomIngr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 3)
                    btAjoutInvent.setEnabled(false);
                else
                    btAjoutInvent.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btAjoutInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                Call<IngredientDisplay> call = server.addIngredient(new IngredientCreate(etNomIngr.getText().toString()));
                call.enqueue(new Callback<IngredientDisplay>() {
                    @Override
                    public void onResponse(Call<IngredientDisplay> call, Response<IngredientDisplay> response) {
                        if (response.code() == 409) {
                            etNomIngr.setText("");
                            Toast.makeText(context, "Cet ingrédient existe déjà.", Toast.LENGTH_LONG).show();
                        }
                        if (response.code() == 200) {
                            etNomIngr.setText("");
                            Toast.makeText(context, "Ingrédient ajouté à l'inventaire.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<IngredientDisplay> call, Throwable t) {

                    }
                });
            }
        });
    }
}