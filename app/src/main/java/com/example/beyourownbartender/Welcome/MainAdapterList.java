package com.example.beyourownbartender.Welcome;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beyourownbartender.Profile.MyLikesActivity;
import com.example.beyourownbartender.Profile.MyRecipesActivity;
import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RecipeDisplay;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdapterList extends RecyclerView.Adapter<MainAdapterList.MainViewHolder> {
    private ArrayList<RecipeDisplay> recipes;
    private ArrayList<RecipeDisplay> fullList;
    MainActivity main;
    MyRecipesActivity mr;
    MyLikesActivity ml;
    boolean isMain = true;
    boolean isMr, isMl;
    SharedPreferences pref;

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MainActivity main) {
        this.recipes = recipes;
        this.main = main;
        isMain = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
        pref = main.getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
    }

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MyRecipesActivity mr) {
        this.recipes = recipes;
        this.mr = mr;
        isMain = false;
        isMr = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
        pref = mr.getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
    }

    public MainAdapterList(ArrayList<RecipeDisplay> recipes, MyLikesActivity ml) {
        this.recipes = recipes;
        this.ml = ml;
        isMain = false;
        isMl = true;
        fullList = new ArrayList<RecipeDisplay>(recipes);
        pref = ml.getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
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
        else {
            holder.imgMain.setVisibility(View.INVISIBLE);
        }
        if (recipe.getAuthorid() != 1) {
            holder.tvAuthor.setText("TODO");
        }
        else {
            holder.tvAuthor.setText("Choix de BeYourOwnBartender");
        }

        if (recipe.isHasRobotHelp())
            holder.btRobot.setVisibility(View.VISIBLE);
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

    private void supprimer(int index) {
        recipes.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateDisplayedData(ArrayList<RecipeDisplay> listRecipes){
        recipes = listRecipes;
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecipeName, tvAuthor, tvTags;
        ImageView imgMain;
        Button btRobot;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvAuthor = itemView.findViewById(R.id.tvAuteur);
            tvTags = itemView.findViewById(R.id.tvTags);
            imgMain = itemView.findViewById(R.id.imgMain);
            btRobot = itemView.findViewById(R.id.btRobot);

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

            btRobot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    boolean adminCheck = pref.getBoolean("isAdmin", false);
                    if (isMr || (adminCheck && isMain)) {
                        AlertDialog.Builder builder;
                        if (isMain)
                             builder = new AlertDialog.Builder(main);
                        else
                            builder = new AlertDialog.Builder(mr);
                        builder.setCancelable(true);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Voulez-vous vraiment supprimer cette recette?");
                        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                                Call<Void> call = server.deleteRecipeById(recipes.get(getLayoutPosition()).getId());
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        supprimer(getLayoutPosition());
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
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
                    return false;
                }
            });
        }

    }
}
