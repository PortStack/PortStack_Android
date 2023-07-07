package com.example.cookingrecipe.Adapter;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Domain.Model.ImageItem;
import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.Domain.Model.RecipeOrder;
import com.example.cookingrecipe.OnMinusButtonClickListener;
import com.example.cookingrecipe.R;

import java.util.List;

public class CookOrdersAdapter extends RecyclerView.Adapter<CookOrdersAdapter.ViewHolder>{
    View rootView;

    Context context;
    List<RecipeOrder> recipeOrderList;
    List<ImageItem> imageItemList;
    OnMinusButtonClickListener minusButtonClickListener;

    private static final int REQUEST_CODE_IMAGE = 1;

    public CookOrdersAdapter(Context context,List<RecipeOrder> recipeOrderList, List<ImageItem> imageItemList){
        this.context = context;
        this.recipeOrderList = recipeOrderList;
        this.imageItemList = imageItemList;
    }

    public void setOnMinusButtonClickListener(OnMinusButtonClickListener listener) {
        this.minusButtonClickListener = listener;
    }

    @NonNull
    @Override
    public CookOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_order_adapter, parent, false);
        return new CookOrdersAdapter.ViewHolder(inflater);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImageView;
        TextView sequenceText;
        EditText editRecipeOrder;
        Button deleteOrderBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.recipe_image_view);
            editRecipeOrder = itemView.findViewById(R.id.recipeOrder);
            deleteOrderBtn = itemView.findViewById(R.id.delete_button);
            sequenceText = itemView.findViewById(R.id.sequence);

            deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        recipeOrderList.remove(position);
                        imageItemList.remove(position);
                        updateSequenceNumbers();
                        notifyItemRemoved(position);
                    }
                }
            });
        }

        public void bindImage(Uri imageUri){
            recipeImageView.setImageURI(imageUri);

            recipeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // 갤러리에서 이미지 선택
                        openGallery(position);
                    }
                }
            });
        }

        public void bindEditRecipeOrder(String recipeOrder){
            editRecipeOrder.setText(recipeOrder);
        }

        public void bindSequence(int sequence){
            sequenceText.setText(String.valueOf(sequence));
        }

        private void openGallery(int position) {
            // 갤러리에서 이미지 선택 로직 구현
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity) context).startActivityForResult(intent, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CookOrdersAdapter.ViewHolder holder, int position) {
        ImageItem imageItem = imageItemList.get(position);
        holder.bindImage(imageItem.getImageUri());
        holder.bindSequence(position + 1);
        EditText editIngredientAmount = holder.editRecipeOrder;


        editIngredientAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeOrderList.get(holder.getAdapterPosition()).setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    public int getItemCount() {
        return recipeOrderList.size();
    }

    public List<RecipeOrder> getRecipeOrderList(){
        return recipeOrderList;
    }

    public List<ImageItem> getImageItemList() { return imageItemList; }

    private void updateSequenceNumbers() {
        int size = recipeOrderList.size();
        for (int i = 0; i < size; i++) {
            RecipeOrder recipeOrder = recipeOrderList.get(i);
            recipeOrder.setSequence(i + 1);
        }
        notifyDataSetChanged();
    }

}

