package com.example.beyourownbartender;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapterList extends RecyclerView.Adapter<CommentAdapterList.CommentViewHolder> {
    private List<CommentDisplay> comments;
    ReadRecipeActivity rr;
    SharedPreferences pref;
    ServerInterface server;
    UserDisplay currentUser;

    public CommentAdapterList(List<CommentDisplay> comments, ReadRecipeActivity rr) {
        this.comments = comments;
        this.rr = rr;
    }

    public CommentAdapterList() { }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.read_comment_layout, parent, false);
        server = RetrofitInstance.getInstance().create(ServerInterface.class);
        pref = rr.getSharedPreferences("BYOBPreferences", MODE_PRIVATE);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDisplay comment = comments.get(position);
        holder.cUsername.setText(comment.getAuthorName());
        holder.cContent.setText(comment.getContent());
        long days = ChronoUnit.DAYS.between(comment.creationTime, OffsetDateTime.now());
        long months = 0;
        long years = 0;

        if (days >= 28) {
            months = days / 28;
            if (months >= 12) {
                years = months / 12;
                holder.cTime.setText("il y a " + years + " ans");
            }
            else {
                holder.cTime.setText("il y a " + months + " mois");
            }
        }
        else {
            holder.cTime.setText("il y a " + days + " jour(s)");
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(CommentDisplay commentToDisplay) {
        comments.add(commentToDisplay);
        notifyItemInserted(comments.size() - 1);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView cUsername, cContent, cTime, cCounter;
        Button btHelpful;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            cUsername = itemView.findViewById(R.id.cUsername);
            cContent = itemView.findViewById(R.id.cContent);
            cTime = itemView.findViewById(R.id.cTime);
            cCounter = itemView.findViewById(R.id.cCounter);
            btHelpful = itemView.findViewById(R.id.btHelpful);

            btHelpful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (comments.get(getLayoutPosition()).getAuthorId() != rr.getCurrentUserId()) {
                        if (pref.getBoolean("isAdmin", false) == false) {
                            return false;
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(rr);
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Voulez-vous vraiment supprimer ce commentaire?");
                    builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Call<Void> call = server.deleteCommentById(comments.get(getLayoutPosition()).getId());
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
                    return true;
                }
            });
        }
        private void supprimer(int index) {
            comments.remove(index);
            notifyItemRemoved(index);
        }
    }
}
