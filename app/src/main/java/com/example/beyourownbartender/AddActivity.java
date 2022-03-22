package com.example.beyourownbartender;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private IngredientAdapterList ingredientAdapterList;
    private StepAdapterList stepAdapterList;
    private List<IngredientDisplay> ingredientList;
    private List<IngredientDisplay> allIngredientList;
    private List<String> stepList;
    private Button buttonAddIngredient;
    private Button buttonAddStep;
    private TextView etbName;
    RecipeDisplay recipeToDisplay;

    MonPhoneReceiver br;
    AddActivity aa;
    ImageView ivSelectedImage;
    String imageBase64 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        br = new MonPhoneReceiver();
        aa = this;

        // Creates the empty ingredient list and sets the recyclerview/adapterlist values for ingredients
        ingredientList = createListIngredients();
        rvIngredients = findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapterList = new IngredientAdapterList(ingredientList, this);
        rvIngredients.setAdapter(ingredientAdapterList);

        /// Creates the empty String list and sets the recyclerview/adapterlist values for steps
        stepList = createListSteps();
        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        stepAdapterList = new StepAdapterList(stepList, this, rvSteps);
        rvSteps.setAdapter(stepAdapterList);

        // Button to change the selected image
        Button buttonChangeSelectedImage;

        // Button to push to the DB
        Button buttonAddRecipe;


        // This is used to pull the ingredients data from the DB
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<IngredientDisplay>> call = server.getIngredients();

        call.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                allIngredientList = response.body();
                ingredientAdapterList.addIngredient(new IngredientDisplay(0, "", allIngredientList));
            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {

            }
        });

        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));

        // Creates a onClickListener for the addIngredient button
        buttonAddIngredient = findViewById(R.id.btAddIngredients);
        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds an empty ingredient containing a list of all ingredients
                ingredientAdapterList.addIngredient(new IngredientDisplay(0, "", allIngredientList));
            }
        });

        // Creates a onClickListener for the changeSelectedImageButton
        buttonChangeSelectedImage = findViewById(R.id.btChangeImage);
        buttonChangeSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opens the image gallery for the user to select an image
                onPickPhoto(view);
            }
        });


        // Creates a onClickListener for the addStep button
        buttonAddStep = findViewById(R.id.btAddSteps);
        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds an empty step
                stepAdapterList.addStep("");
            }
        });

        etbName = findViewById(R.id.etbName);
        buttonAddRecipe = findViewById(R.id.btAddRecipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pushToDB(ingredientList, stepList, etbName.getText().toString());
            }
        });
    }

    // Used to push to the DB
    private void pushToDB(List<IngredientDisplay> listIngredients, List<String> listSteps, String title) {
        // Creating the recipe to create
        SharedPreferences pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
        int authorID = pref.getInt("userId", 0);
        List<String> listTags = new ArrayList<>();
        RecipeCreate recipeToCreate = new RecipeCreate(title, authorID, imageBase64, listSteps, listTags); // Image base 64 is null is it wasn't selected
        int recipeID;
        List<Integer> idIngredientDisplayToLink = new ArrayList<>();
        int[] listInInt;

        // Trying to post it to the DB
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<RecipeDisplay> callAdd = server.addRecipe(recipeToCreate);
        callAdd.enqueue(new Callback<RecipeDisplay>() {
            @Override
            public void onResponse(Call<RecipeDisplay> call, Response<RecipeDisplay> response) {
                recipeToDisplay = response.body();
                if (response.code() == 200) {
                    // Sets the recipeToDisplay to the response body
                    recipeToDisplay = response.body();
                    int recipeID = recipeToDisplay.getId();

                    for(int j = 0; j<listIngredients.size(); j++){
                        for(int k = 0; k<allIngredientList.size(); k++){
                            if(listIngredients.get(j).name == allIngredientList.get(k).name){
                                idIngredientDisplayToLink.add(allIngredientList.get(k).id);
                            }
                        }
                    }
                    int[] idsToSend = idIngredientDisplayToLink.stream().mapToInt(i->i).toArray();
                    Intent intent = new Intent();
                    intent.setAction("com.info.broadcast.linkIngredients");
                    intent.putExtra("id",recipeID);
                    intent.putExtra("listIngredient", idsToSend);
                    sendBroadcast(intent);
                }
                else{
                    // Where response code is not 200 A.K.A Api is having a seizure
                }
            }
            @Override
            public void onFailure(Call<RecipeDisplay> call, Throwable t) {
                // Fails
                String err;
            }
        });
    }

    // This is a static integer
    public final static int PICK_PHOTO_CODE = 1046;

    // Function to start the activity to access the gallery
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // It think it can crash if no app to access the gallery is installed on the phone
        startActivityForResult(intent, PICK_PHOTO_CODE);
    }


    // Function to load the image selected
    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Function listens for the result of the end of the activity to select the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                stepAdapterList.concludeUpdate(Integer.parseInt(data.getStringExtra("pos")), data.getStringExtra("newStep"));
            }
        } else {
            if ((data != null) && requestCode == PICK_PHOTO_CODE) {
                Uri photoUri = data.getData();

                // Load the image located at photoUri into selectedImage
                Bitmap selectedImage = loadFromUri(photoUri);
                imageBase64 = encodeToBase64(selectedImage);


                // Load the selected image into a preview
                ivSelectedImage = findViewById(R.id.ivSelectedImage);
                ivSelectedImage.setImageBitmap(selectedImage);
            }
        }
    }

    // Converts a bitmap to a base64 String
    public static String encodeToBase64(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String output = Base64.getEncoder().encodeToString(b);
        Log.e("test", output);
        return(output);
    }


    // Creates the empty ingredient list
    private List<IngredientDisplay> createListIngredients(){
        List<IngredientDisplay> ingredientList = new ArrayList<>();
        return(ingredientList);
    }

    private List<String> createListSteps(){
        List<String> stepList = new ArrayList<>();
        stepList.add("");
        return(stepList);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.info.broadcast.linkIngredients");
        this.registerReceiver(br, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(br);
    }

    public class MonPhoneReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Bundle extras = intent.getExtras();
            if (extras != null) {
                int[]idList = intent.getIntArrayExtra("listIngredient");
                int recipeID = intent.getIntExtra("id", -1);
                List<IngredientDisplay> listIngredientsToLink = new ArrayList<>();
                for(int l=0; l<idList.length; l++){
                    for(int m=0; m<allIngredientList.size(); m++){
                        if(idList[l] == allIngredientList.get(m).id){
                            listIngredientsToLink.add(allIngredientList.get(m));
                        }
                    }
                }
                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                Call<List<IngredientDisplay>> callLinkIngredient = server.addIngredientToRecipe(recipeID, listIngredientsToLink);
                callLinkIngredient.enqueue(new Callback<List<IngredientDisplay>>() {

                    @Override
                    public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                        // Finish this app on success
                        aa.finish();
                    }

                    @Override
                    public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {
                        // Fails
                        String err;
                        // Finish this app on failure
                        aa.finish();
                    }
                });
            }
        }
    }

}