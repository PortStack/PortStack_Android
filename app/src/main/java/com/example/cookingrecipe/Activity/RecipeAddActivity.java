package com.example.cookingrecipe.Activity;

import static com.example.cookingrecipe.Util.ImageUtils.convertImageToByteArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.cookingrecipe.Adapter.CookOrdersAdapter;
import com.example.cookingrecipe.Adapter.IngredientListAdapter;
import com.example.cookingrecipe.Domain.DTO.CookOrdersDTO;
import com.example.cookingrecipe.Domain.Model.ImageItem;
import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.Domain.Model.RecipeOrder;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.API.RetrofitAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeAddActivity extends AppCompatActivity {
    private List<Ingredient> Ingredients;
    private List<RecipeOrder> recipeOrders;
    private List<ImageItem> imageItemList;
    private EditText editTextName;
    private EditText editTextInstructions;
    private Button buttonAdd;
    private Button buttonAddIngredient;
    private Button buttonAddStep;

    private LinearLayout stepListLayout;
    private ImageButton buttonBack;
    private RecyclerView ingredientView;
    private RecyclerView cookOrderView;
    private IngredientListAdapter ingredientAdapter;
    private CookOrdersAdapter cookOrdersAdapter;

    private String getImageFileName(Uri imageUri) {
        String fileName = null;
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};

        try (Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                fileName = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode >= 0 && requestCode < imageItemList.size()) {
            // 이미지가 선택된 경우
            Uri selectedImageUri = data.getData();

            ImageItem imageItem = imageItemList.get(requestCode);
            imageItem.setImageUri(selectedImageUri);

            String fileName = getImageFileName(selectedImageUri);
            System.out.println(fileName);

            RecipeOrder recipeOrderItem = recipeOrders.get(requestCode);
            recipeOrderItem.setOrderImageName(fileName);
            cookOrdersAdapter.notifyItemChanged(requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAddIngredient = findViewById(R.id.addIngredient);
        buttonAddStep = findViewById(R.id.buttonAddStep);
        buttonBack = findViewById(R.id.buttonBack);

        ingredientView = (RecyclerView) findViewById(R.id.ingredientListLayout);
        cookOrderView = (RecyclerView) findViewById(R.id.cookOrdersLayout);


        Ingredients = new ArrayList<>();
        ingredientAdapter = new IngredientListAdapter(Ingredients);

        ingredientView.setAdapter(ingredientAdapter);

        ingredientView.setLayoutManager(new LinearLayoutManager(this));

        List<Ingredient> dataList = ingredientAdapter.getIngredientList();
        dataList.add(new Ingredient());
        ingredientAdapter.notifyItemInserted(dataList.size() -1);

        recipeOrders = new ArrayList<>();
        imageItemList = new ArrayList<>();
        cookOrdersAdapter = new CookOrdersAdapter(this,recipeOrders,imageItemList);

        cookOrderView.setAdapter(cookOrdersAdapter);

        cookOrderView.setLayoutManager(new LinearLayoutManager(this));

        List<RecipeOrder> orderDataS = cookOrdersAdapter.getRecipeOrderList();
        List<ImageItem> imageDataS = cookOrdersAdapter.getImageItemList();
        orderDataS.add(new RecipeOrder());
        imageDataS.add(new ImageItem());
        cookOrdersAdapter.notifyItemInserted(orderDataS.size() -1);


        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient(ingredientAdapter);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back button action here
            }
        });

        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStep(cookOrdersAdapter);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRecipeOrders();
            }
        });

    }

    // Image의 절대경로를 가져오는 메소드
    private String getRealPathFromURI(Uri contentURI) {

        String result;

        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);



        if (cursor == null) { // Source is Dropbox or other similar local file path

            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;

    }

    private void sendRecipeOrders() {
        RecipeAPI retrofitAPI = RetrofitClient.getClient().create(RecipeAPI.class);

        CookOrdersDTO cookOrdersDTO = setCookOrders();

        Gson gson = new Gson();
        String json = gson.toJson(cookOrdersDTO);

        RequestBody jsonRequestBody = RequestBody.create(MediaType.parse("application/json"), json);

        List<MultipartBody.Part> orderImagesParts = new ArrayList<>();
        List<MultipartBody.Part> themNailImageParts = new ArrayList<>();

        for (int i = 0; i < imageItemList.size(); i++) {
            ImageItem imageItem = imageItemList.get(i);
            Uri imageUri = imageItem.getImageUri();
            String imagePath = getRealPathFromURI(imageUri);

            if (imageUri != null) {
                File imageFile = new File(imagePath); // 이미지 MIME 타입 얻기
                System.out.println(imageFile);

                // 이미지 파일을 바이트 배열로 변환
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);


                MultipartBody.Part orderImagePart = MultipartBody.Part.createFormData("orderImage", imageFile.getName(), requestBody);
                MultipartBody.Part themNailImagePart = MultipartBody.Part.createFormData("themNail", imageFile.getName(), requestBody);
                orderImagesParts.add(orderImagePart);
                themNailImageParts.add(themNailImagePart);
            }
        }
        System.out.println("json: " + jsonRequestBody);
        Call<ResponseBody> call = retrofitAPI.setNewRecipes(jsonRequestBody,orderImagesParts,themNailImageParts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 응답 처리
                if (response.isSuccessful()) {
                    System.out.println("ok");
                } else {
                    System.out.println(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    private CookOrdersDTO setCookOrders(){
        CookOrdersDTO cookOrdersDTO = new CookOrdersDTO();

        cookOrdersDTO.setCookOrders(recipeOrders);
        cookOrdersDTO.setRecipeIngredients(Ingredients);
        cookOrdersDTO.setWriter("tester2");
        cookOrdersDTO.setTitle(editTextName.getText().toString());

        return cookOrdersDTO;
    }

    private void addIngredient(IngredientListAdapter ingredientAdapter) {
        List<Ingredient> dataList = ingredientAdapter.getIngredientList();
        dataList.add(new Ingredient());
        ingredientAdapter.notifyItemInserted(dataList.size() -1);
        System.out.println(ingredientAdapter.getIngredientList().size());

    }

    private void addStep(CookOrdersAdapter cookOrdersAdapter){
        List<RecipeOrder> recipeOrders = cookOrdersAdapter.getRecipeOrderList();
        List<ImageItem> Images = cookOrdersAdapter.getImageItemList();
        recipeOrders.add(new RecipeOrder());
        Images.add(new ImageItem());
        cookOrdersAdapter.notifyItemInserted(recipeOrders.size() -1);
    }
}
