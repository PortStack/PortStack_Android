package com.example.cookingrecipe.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.OnMinusButtonClickListener;
import com.example.cookingrecipe.R;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder>{
    View rootView;

    Context context;
    List<Ingredient> ingredientList;
    OnMinusButtonClickListener minusButtonClickListener;

    public IngredientListAdapter(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }

    public void setOnMinusButtonClickListener(OnMinusButtonClickListener listener) {
        this.minusButtonClickListener = listener;
    }

    @NonNull
    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_adapter, parent, false);
        return new ViewHolder(inflater);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editIngredientName;
        EditText editIngredientAmount;
        EditText editIngredientUnit;
        Button minusIngredientBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editIngredientName = itemView.findViewById(R.id.editIngredient);
            editIngredientAmount = itemView.findViewById(R.id.editAmount);
            editIngredientUnit = itemView.findViewById(R.id.editUnit);
            minusIngredientBtn = itemView.findViewById(R.id.minusIngredient);

            minusIngredientBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        ingredientList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListAdapter.ViewHolder holder, int position) {
        EditText editIngredientName = holder.editIngredientName;
        EditText editIngredientAmount = holder.editIngredientAmount;
        EditText editIngredientUnit = holder.editIngredientUnit;
        Button minusAddIngredient = holder.minusIngredientBtn;

        editIngredientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientList.get(holder.getAdapterPosition()).setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editIngredientAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientList.get(holder.getAdapterPosition()).setCount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editIngredientUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientList.get(holder.getAdapterPosition()).setUnit(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public List<Ingredient> getIngredientList(){
        return ingredientList;
    }


}
