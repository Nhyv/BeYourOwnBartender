package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadRecipeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentAdapterList adapterList;
    List<CommentDisplay> comments;
    ImageView imgRead;
    RecipeDisplay recipe;
    TextView readTitre, readAuthor, readTags, readIngredients, readSteps;
    EditText etComment;
    Button btComment;
    Context context;
    CommentDisplay commentToDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        context = this;
        Intent intent = getIntent();
        imgRead = findViewById(R.id.imgRead);
        readTitre = findViewById(R.id.readTitre);
        readAuthor = findViewById(R.id.readAuthor);
        readTags = findViewById(R.id.readTags);
        readIngredients = findViewById(R.id.readIngredients);
        readSteps = findViewById(R.id.readSteps);
        etComment = findViewById(R.id.etComment);
        btComment = findViewById(R.id.btComment);

        int id = 0;
        if (intent.hasExtra("recipeId")) {
            Bundle bundle = intent.getExtras();
            id = bundle.getInt("recipeId", 0);
        }
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<RecipeDisplay> call = server.getRecipeById(id);
        call.enqueue(new Callback<RecipeDisplay>() {
            @Override
            public void onResponse(Call<RecipeDisplay> call, Response<RecipeDisplay> response) {
                recipe = response.body();
                String name = recipe.getName();
                int authorId = recipe.getAuthorid();
                List<String> tags = recipe.getTags();
                List<String> steps = recipe.getSteps();
                String toShowTags = "";
                String toShowSteps = "";

                if (recipe.getImageUrl() != null) {
                    Picasso.get().load(recipe.getImageUrl()).into(imgRead);
                }

                for (int i = 0; i < tags.size(); i++) {
                    if (tags.size() == 1)
                        toShowTags = tags.get(i);
                    else
                        toShowTags += tags.get(i) + ", ";
                }

                for (int i = 0; i < steps.size(); i++) {
                    if (steps.size() == 1)
                        toShowSteps = steps.get(i);
                    else
                        toShowSteps += steps.get(i) + System.lineSeparator();
                }

                readTags.setText(toShowTags);
                readSteps.setText(toShowSteps);
                readTitre.setText(name);
                if (authorId == 7) {
                    readAuthor.setText("BeYourOwnBartender");
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<RecipeDisplay> call, Throwable t) { }
        });

        Call<List<IngredientDisplay>> callIngredients = server.getIngredientsByRecipeId(id);
        callIngredients.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                List<IngredientDisplay> ingredients = response.body();
                String toDisplay = "";

                for (int i = 0; i < ingredients.size(); i++) {
                    toDisplay += ingredients.get(i).name + System.lineSeparator();
                }

                readIngredients.setText(toDisplay);
            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) { }
        });

        recyclerView = findViewById(R.id.rvComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<CommentDisplay>> callComment = server.getCommentsByRecipeId(id);

        callComment.enqueue(new Callback<List<CommentDisplay>>() {
            @Override
            public void onResponse(Call<List<CommentDisplay>> call, Response<List<CommentDisplay>> response) {
                comments = response.body();
                adapterList = new CommentAdapterList(comments);
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<CommentDisplay>> call, Throwable t) {

            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                    btComment.setEnabled(true);
                }
                else {
                    btComment.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentCreate commentToAdd = new CommentCreate(1, "BYOB",
                        etComment.getText().toString(), 1);
                Call<CommentDisplay> callAdd = server.addComment(commentToAdd);
                callAdd.enqueue(new Callback<CommentDisplay>() {
                    @Override
                    public void onResponse(Call<CommentDisplay> call, Response<CommentDisplay> response) {
                        if (response.code() == 200)
                            commentToDisplay = response.body();
                            adapterList.addComment(commentToDisplay);
                            adapterList.notifyItemChanged(comments.size() - 1);

                    }

                    @Override
                    public void onFailure(Call<CommentDisplay> call, Throwable t) {
                        String apple;
                    }
                });
            }
        });


    }
}