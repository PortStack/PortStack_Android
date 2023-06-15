package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.cookingrecipe.R;

public class RecipeAddActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextIngredient;
    private EditText editTextInstructions;
    private Button buttonAdd;
    private Button buttonAddIngredient;
    private LinearLayout ingredientLayout;
    private Button buttonAddStep;

    private LinearLayout stepListLayout;

    private LinearLayout ingredientListLayout;
    private ImageButton buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editTextName = findViewById(R.id.editTextName);
        editTextIngredient = findViewById(R.id.editTextIngredient);
        editTextInstructions = findViewById(R.id.editTextInstruction);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAddIngredient = findViewById(R.id.buttonAddIngredient);
        ingredientLayout = findViewById(R.id.ingredientLayout);
        buttonAddStep = findViewById(R.id.buttonAddStep);
        ingredientListLayout = findViewById(R.id.ingredientListLayout);
        buttonBack = findViewById(R.id.buttonBack);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back button action here
            }
        });

    }

    private void addIngredient() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ingredientLayout = new LinearLayout(this);
        ingredientLayout.setLayoutParams(layoutParams);
        ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText newIngredientEditText = new EditText(this);
        newIngredientEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newIngredientEditText.setHint("Ingredients");
        newIngredientEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);

        Button newIngredientButton = new Button(this);
        newIngredientButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newIngredientButton.setText("-");

        newIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientListLayout.removeView(ingredientLayout);
            }
        });

        ingredientLayout.addView(newIngredientEditText);
        ingredientLayout.addView(newIngredientButton);
        ingredientListLayout.addView(ingredientLayout);
    }


    private void addRecipe() {
        String name = editTextName.getText().toString().trim();
        String ingredients = getIngredients();
        String instructions = editTextInstructions.getText().toString().trim();

        // Handle validation and perform necessary operations
    }

    private String getIngredients() {
        StringBuilder ingredientsBuilder = new StringBuilder();
        int count = ingredientLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = ingredientLayout.getChildAt(i);
            if (view instanceof EditText) {
                EditText ingredientEditText = (EditText) view;
                String ingredient = ingredientEditText.getText().toString().trim();
                if (!ingredient.isEmpty()) {
                    ingredientsBuilder.append(ingredient);
                    if (i < count - 1) {
                        ingredientsBuilder.append(", ");
                    }
                }
            }
        }
        return ingredientsBuilder.toString();
    }




}
