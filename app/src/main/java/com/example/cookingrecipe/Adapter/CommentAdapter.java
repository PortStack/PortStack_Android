// CommentAdapter.java
package com.example.cookingrecipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.Domain.DTO.CommentDTO;
import com.example.cookingrecipe.Domain.DTO.UserDTO;
import com.example.cookingrecipe.OnItemClickListener;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.API.RetrofitAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.TokenUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentDTO> comments;
    private Context mContext;
    private int recipeId;

    private OnItemClickListener itemClickListener;
    private String nickName;

    public CommentAdapter(List<CommentDTO> comments, String nickName,Context mContext,int recipeId) {
        this.comments = comments;
        this.nickName = nickName;
        this.mContext = mContext;
        this.recipeId = recipeId;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
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
        holder.createDateView.setText(comment.getCreatedDate());
        if(comment.getNickname().equals(nickName)){
            holder.buttonView.setVisibility(View.VISIBLE);
        }
        holder.deleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment(comment.getId());
            }
        });
//
//        holder.deleteButtonView.setOnClickListener(view -> {
//            deleteComment(comment.getId());
//        });


    }

    public void deleteComment(int commentId){
        RecipeAPI retrofitAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        retrofitAPI.deleteComment(commentId).enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful()){
                    loadComments();
                    Toast.makeText(mContext, "댓글이 삭제되엇습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("Test",TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
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
        TextView createDateView;
        LinearLayout buttonView;
        TextView deleteButtonView;
        TextView correctButtonView;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            nicknameTextView = itemView.findViewById(R.id.nicknameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            createDateView = itemView.findViewById(R.id.createDateView);
            buttonView = itemView.findViewById(R.id.buttonView);
            deleteButtonView = itemView.findViewById(R.id.deleteButton);
            correctButtonView = itemView.findViewById(R.id.correctButton);
        }
    }

    private void loadComments() {
        // 서버로부터 댓글 목록을 가져오는 Retrofit 호출
        RecipeAPI retrofitAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        retrofitAPI.getComments(recipeId).enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                Log.d("CommentDto", String.valueOf(response));
                if (response.isSuccessful()) {
                    List<CommentDTO> newComments = response.body();
                    // comments 리스트를 업데이트하고 RecyclerView를 갱신합니다.
                    comments.clear();
                    comments.addAll(newComments);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CommentDTO>> call, Throwable t) {
                // 실패 시 처리
                t.printStackTrace();
            }
        });
    }
}
