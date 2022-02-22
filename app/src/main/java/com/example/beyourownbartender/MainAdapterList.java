package com.example.beyourownbartender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainAdapterList extends RecyclerView.Adapter<MainAdapterList.MainViewHolder> {
    private List<Recipe> recipes;
    MainActivity main;

    public MainAdapterList(List<Recipe> recipes, MainActivity main) {
        this.recipes = recipes;
        this.main = main;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_recipe_layout, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        Picasso.get().load("https://www.mordeo.org/files/uploads/2018/10/Anime-Girl-Fireworks-4K-Ultra-HD-Mobile-Wallpaper.jpg").into(holder.imgMain);
        if (recipe.authorId != 7) {
            holder.tvAuthor.setText("TODO");
        }
        else {
            holder.tvAuthor.setText("Choix de BeYourOwnBartender");
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecipeName, tvAuthor;
        ImageView imgMain;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvAuthor = itemView.findViewById(R.id.tvAuteur);
            imgMain = itemView.findViewById(R.id.imgMain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                }
            });
        }

    }
}
