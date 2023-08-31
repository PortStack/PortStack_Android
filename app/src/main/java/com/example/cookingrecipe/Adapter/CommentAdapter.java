// CommentAdapter.java
package com.example.cookingrecipe.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Domain.DTO.CommentDTO;
import com.example.cookingrecipe.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentDTO> comments;

    public CommentAdapter(List<CommentDTO> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDTO comment = comments.get(position);
        holder.nicknameTextView.setText(comment.getNickname());
        holder.contentTextView.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(CommentDTO comment) {
        comments.add(comment);
        notifyItemInserted(comments.size() - 1);
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView nicknameTextView;
        TextView contentTextView;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            nicknameTextView = itemView.findViewById(R.id.nicknameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }
}