package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
ReadRecipeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentAdapterList adapterList;
    int authorId;
    List<CommentDisplay> comments;
    List<Integer> likes;
    int recipeId;
    ImageView imgRead;
    RecipeDisplay recipe;
    TextView readTitre, readAuthor, readTags, readIngredients, readSteps, readRating;
    EditText etComment;
    Button btComment;
    Context context;
    CommentDisplay commentToDisplay;
    UserDisplay author;
    FloatingActionButton fabLike;
    SharedPreferences pref;
    LikeDisplay like;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
        context = this;
        Intent intent = getIntent();
        imgRead = findViewById(R.id.imgRead);
        readTitre = findViewById(R.id.readTitre);
        readAuthor = findViewById(R.id.readAuthor);
        readTags = findViewById(R.id.readTags);
        readIngredients = findViewById(R.id.readIngredients);
        readRating = findViewById(R.id.readRating);
        readSteps = findViewById(R.id.readSteps);
        etComment = findViewById(R.id.etComment);
        btComment = findViewById(R.id.btComment);
        fabLike = findViewById(R.id.fabLike);

         recipeId = 0;
        if (intent.hasExtra("recipeId")) {
            Bundle bundle = intent.getExtras();
            recipeId = bundle.getInt("recipeId", 0);
            authorId = bundle.getInt("authorId", 1);
        }
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<RecipeDisplay> call = server.getRecipeById(recipeId);
        call.enqueue(new Callback<RecipeDisplay>() {
            @Override
            public void onResponse(Call<RecipeDisplay> call, Response<RecipeDisplay> response) {
                recipe = response.body();
                String name = recipe.getName();
                List<String> tags = recipe.getTags();
                List<String> steps = recipe.getSteps();
                rating = recipe.getRating();
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
                readRating.setText(rating + " ❤");
            }

            @Override
            public void onFailure(Call<RecipeDisplay> call, Throwable t) { }
        });

        Call<List<IngredientDisplay>> callIngredients = server.getIngredientsByRecipeId(recipeId);
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

        Call<List<CommentDisplay>> callComment = server.getCommentsByRecipeId(recipeId);

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

        Call<UserDisplay> callUser = server.getUserById(authorId);
        callUser.enqueue(new Callback<UserDisplay>() {
            @Override
            public void onResponse(Call<UserDisplay> call, Response<UserDisplay> response) {
                author = response.body();
                readAuthor.setText(author.username);
            }

            @Override
            public void onFailure(Call<UserDisplay> call, Throwable t) {

            }
        });

        int userId = pref.getInt("userId", 1);
        Call<List<Integer>> callLikes = server.getUserLiked(userId);
        // mFab.setBackgroundTintList(ColorStateList.valueOf(your color in int)); for normal state
        // mFab.setRippleColor(your color in int); pressed state
        callLikes.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                likes = response.body();

                if (likes.contains(recipeId)) {
                    fabLike.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(221,74,74)));
                    fabLike.setImageTintList(ColorStateList.valueOf(Color.rgb(161,34,34)));
                }
                else {
                    fabLike.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    fabLike.setImageTintList(ColorStateList.valueOf(Color.DKGRAY));
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

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

                String username = pref.getString("username", "N/A");

                CommentCreate commentToAdd = new CommentCreate(userId, username,
                        etComment.getText().toString(), recipeId);
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

        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = pref.getInt("userId", 1);
                Call<LikeDisplay> callToggleLike = server.toggleLikeRecipe(recipeId, userId);

                callToggleLike.enqueue(new Callback<LikeDisplay>() {
                    @Override
                    public void onResponse(Call<LikeDisplay> call, Response<LikeDisplay> response) {
                        like = response.body();

                        if (like.isLiked()) {
                            fabLike.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(221,74,74)));
                            fabLike.setImageTintList(ColorStateList.valueOf(Color.rgb(161,34,34)));
                            readRating.setText(like.getRatingScore() + " ❤");
                        }
                        else {
                            fabLike.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                            fabLike.setImageTintList(ColorStateList.valueOf(Color.DKGRAY));
                            readRating.setText(like.getRatingScore() + " ❤");
                        }
                    }

                    @Override
                    public void onFailure(Call<LikeDisplay> call, Throwable t) {

                    }
                });
            }
        });

    }
}