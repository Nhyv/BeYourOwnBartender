package com.example.beyourownbartender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapterList extends RecyclerView.Adapter<CommentAdapterList.CommentViewHolder> {
    private List<Comment> comments;

    public CommentAdapterList(List<Comment> comments) {
        this.comments = comments;
    }

    public CommentAdapterList() { }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.read_comment_layout, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.cUsername.setText(comment.authorName);
        holder.cContent.setText(comment.content);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        notifyItemInserted(comments.size() - 1);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView cUsername, cContent, cTime, cCounter;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            cUsername = itemView.findViewById(R.id.cUsername);
            cContent = itemView.findViewById(R.id.cContent);
            cTime = itemView.findViewById(R.id.cTime);
            cCounter = itemView.findViewById(R.id.cCounter);
        }
    }
}