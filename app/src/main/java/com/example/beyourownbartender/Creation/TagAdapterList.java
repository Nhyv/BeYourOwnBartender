package com.example.beyourownbartender.Creation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beyourownbartender.R;

import java.util.List;

public class TagAdapterList extends RecyclerView.Adapter<TagAdapterList.AdapterListViewHolder> {

    List<String> tagList;
    Context context;
    private RecyclerView rvTags;


    public TagAdapterList(List<String> tagList, Context context, RecyclerView rvTags) {
        this.tagList = tagList;
        this.context = context;
        this.rvTags = rvTags;
    }

    public TagAdapterList(List<String> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public AdapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_tag_to_recipe_layout, parent, false);
        return new AdapterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListViewHolder holder, int position) {

        // Sets the card title text
        String titleCardTagString = "Etape No: "+(position+1);
        holder.tvTitleTag.setText(titleCardTagString);

        // Sets innerText
        holder.mtbTagInfo.setText(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    // Adds an empty String to the RV
    public void addTag(String item){
        tagList.add(item);
        notifyItemInserted(tagList.size()-1);
    }

    public void deleteTag(int index){
        tagList.remove(index);
        notifyItemRemoved(index);
    }

    public void startUpdate(String pos, String oldTag){
        Intent intent =  new Intent(context, UpdateTag.class);
        intent.putExtra("pos", pos);
        intent.putExtra("oldTag", oldTag);
        ((Activity)context).startActivityForResult(intent, 2);
    }

    public void concludeUpdate(int pos, String newTag){
        tagList.set(pos, newTag);
        notifyItemChanged(pos);
    }

    public void refreshAll(){
        notifyDataSetChanged();
    }


    public class AdapterListViewHolder extends RecyclerView.ViewHolder {
        public Button btRemoveTag;
        public Button btUpdateTag;
        public TextView tvTitleTag;
        public TextView mtbTagInfo;

        public AdapterListViewHolder(@NonNull View itemView) {
            super(itemView);

            btRemoveTag = itemView.findViewById(R.id.btRemoveTag);
            btUpdateTag = itemView.findViewById(R.id.btUpdateTag);
            tvTitleTag = itemView.findViewById(R.id.tvTitleTag);
            mtbTagInfo = itemView.findViewById(R.id.mtbTagInfo);


            btRemoveTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteTag(getLayoutPosition());
                    refreshAll();
                }
            });

            btUpdateTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    String posStr = Integer.toString(pos);
                    startUpdate(posStr, tagList.get(getLayoutPosition()));
                }
            });
        }
    }
}
