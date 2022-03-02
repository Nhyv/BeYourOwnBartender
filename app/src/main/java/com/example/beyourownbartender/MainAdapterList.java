package com.example.beyourownbartender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MainAdapterList extends RecyclerView.Adapter<MainAdapterList.MainViewHolder> {
    private List<RecipeDisplay> recipes;
    MainActivity main;
    MyRecipesActivity mr;
    MyLikesActivity ml;

    public MainAdapterList(List<RecipeDisplay> recipes, MainActivity main) {
        this.recipes = recipes;
        this.main = main;
    }

    public MainAdapterList(List<RecipeDisplay> recipes, MyRecipesActivity mr) {
        this.recipes = recipes;
        this.mr = mr;
    }

    public MainAdapterList(List<RecipeDisplay> recipes, MyLikesActivity mr) {
        this.recipes = recipes;
        this.ml = ml;
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
        RecipeDisplay recipe = recipes.get(position);
        List<String> tags = recipe.getTags();
        String toShow = "";

        for (int i = 0; i < tags.size(); i++) {
            if (tags.size() == 1)
                toShow = tags.get(i);
            else
            toShow += tags.get(i) + ", ";
        }

        holder.tvTags.setText(toShow);
        holder.tvRecipeName.setText(recipe.getName());
        if (recipe.getImageUrl() != null) {
            Picasso.get().load(recipe.getImageUrl()).into(holder.imgMain);
        }
        if (recipe.authorId != 1) {
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

        TextView tvRecipeName, tvAuthor, tvTags;
        ImageView imgMain;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvAuthor = itemView.findViewById(R.id.tvAuteur);
            tvTags = itemView.findViewById(R.id.tvTags);
            imgMain = itemView.findViewById(R.id.imgMain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        main.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                        mr.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                        ml.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                    }
                    catch (Exception ex) {

                    }
                }
            });
        }

    }
}
