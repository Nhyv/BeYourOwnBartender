package com.example.beyourownbartender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

    public MainAdapterList(List<Recipe> recipes) {
        this.recipes = recipes;
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
        if (recipe.authorid != 0) {
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

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvAuthor = itemView.findViewById(R.id.tvAuteur);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open the recipe
                }
            });
        }

    }
}
