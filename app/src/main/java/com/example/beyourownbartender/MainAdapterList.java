package com.example.beyourownbartender;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterList extends RecyclerView.Adapter<MainAdapterList.MainViewHolder> {
    private ArrayList<RecipeDisplay> recipes;
    private ArrayList<RecipeDisplay> fullList;
    MainActivity main;
    MyRecipesActivity mr;
    MyLikesActivity ml;
    boolean isMain = true;
    boolean isMr, isMl;

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MainActivity main) {
        this.recipes = recipes;
        this.main = main;
        isMain = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
    }

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MyRecipesActivity mr) {
        this.recipes = recipes;
        this.mr = mr;
        isMain = false;
        isMr = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
    }

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MyLikesActivity ml) {
        this.recipes = recipes;
        this.ml = ml;
        isMain = false;
        isMl = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
    }

    public Filter getFilter() {
        return Searched_Filter;
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

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<RecipeDisplay> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RecipeDisplay recipe : fullList) {
                    if (recipe.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(recipe);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recipes.clear();
            recipes.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

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
                    if (isMr)
                        mr.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                    if (isMl)
                        ml.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                    if (isMain)
                        main.startReadRecipeActivity(recipes.get(getLayoutPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (isMr) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(main);
                        builder.setCancelable(true);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Voulez-vous vraiment supprimer cette recette?");
                        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                
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
                }
            });
        }

    }
}
