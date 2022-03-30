package com.example.beyourownbartender.Update;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beyourownbartender.Creation.IngredientAdapterList;
import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.example.beyourownbartender.Creation.StepAdapterList;
import com.example.beyourownbartender.Creation.TagAdapterList;
import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RecipeDisplay;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private RecyclerView rvTags;
    private IngredientAdapterList ingredientAdapterList;
    private StepAdapterList stepAdapterList;
    private TagAdapterList tagAdapterList;
    private List<IngredientDisplay> ingredientList;
    private List<IngredientDisplay> allIngredientList;
    private List<String> stepList;
    private List<String> tagList;
    private Button buttonAddIngredient;
    private Button buttonAddStep;
    private Button buttonAddTag;
    private TextView etbName;
    RecipeDisplay recipeDisplay;
    List<IngredientDisplay> ingredientDisplay;

    UpdateActivity ua;
    ImageView ivSelectedImage;
    String imageBase64 = null;
    Boolean imageChange = false;
    String urlFromServer;
    MonPhoneReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Reuse the add activity layout for the update
        setContentView(R.layout.activity_add);
        // Context
        ua = this;
        Intent intent = getIntent();
        // Recreate the recipe from the intent
        int id = intent.getIntExtra("id", 0);
        // Broadcast
        br = new MonPhoneReceiver();
        // Set the image view
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        // Server variable
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);

        // This is used to pull the ingredients data from the DB
        Call<List<IngredientDisplay>> call = server.getIngredients();
        call.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                allIngredientList = response.body();

                Intent intent = new Intent();
                intent.setAction("com.info.broadcast.pullIngredientList");
                intent.putExtra("id",id);
                sendBroadcast(intent);

            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {

            }
        });

        // Call the server to get the recipe
        Call<RecipeDisplay> callRecipeById = server.getRecipeById(id);
        callRecipeById.enqueue(new Callback<RecipeDisplay>() {
            @Override
            public void onResponse(Call<RecipeDisplay> call, Response<RecipeDisplay> response) {
                // Sets the recipeDisplay
                recipeDisplay = response.body();

                //Only happens on response

                /// Creates the empty String list and sets the recyclerview/adapterlist values for steps
                stepList = pullStepList();
                rvSteps = findViewById(R.id.rvSteps);
                rvSteps.setLayoutManager(new LinearLayoutManager(ua));
                stepAdapterList = new StepAdapterList(stepList, ua, rvSteps);
                rvSteps.setAdapter(stepAdapterList);

                /// Creates the empty String list and sets the recyclerview/adapterlist values for tags
                tagList = pullTagList();
                rvTags = findViewById(R.id.rvTags);
                rvTags.setLayoutManager(new LinearLayoutManager(ua));
                tagAdapterList = new TagAdapterList(tagList, ua, rvTags);
                rvTags.setAdapter(tagAdapterList);

                ///  Sets the name of the recipe
                etbName.setText(recipeDisplay.getName());

                /// Sets the serverBase64 and the base64 then set the imageview
                urlFromServer = recipeDisplay.getImageUrl();
                Picasso.get().load(urlFromServer).into(ivSelectedImage);

            }

            @Override
            public void onFailure(Call<RecipeDisplay> call, Throwable t) {

            }
        });

        // Button to change the selected image
        Button buttonChangeSelectedImage;

        // Button to remove the selected image
        Button buttonRemoveImage;

        // Button to reset the image to the server image
        Button buttonResetImage;

        // Button to call the update function
        Button buttonUpdateRecipe;
        buttonUpdateRecipe = findViewById(R.id.btAddRecipe);

        buttonUpdateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calls the function to update
                updateRecipe(id, etbName.getText().toString(), imageBase64, tagList, stepList, ingredientList);
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

        // Creates a onClickListener for the removeImageButton
        buttonRemoveImage = findViewById(R.id.btRemoveImage);
        buttonRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Removes the image
                imageBase64 = "delete";
                ivSelectedImage.setImageBitmap(null);
                // This means we dont have the same image as on the server
                imageChange = true;
            }
        });

        // Creates a onClickListener for resetImageButton
        buttonResetImage = findViewById(R.id.btResetImage);
        buttonResetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Empty the base64 string
                imageBase64 = null;

                // Set the imageChange to false as it is the same as the server image
                imageChange = false;

                // Sets the imageView
                Picasso.get().load(urlFromServer).into(ivSelectedImage);
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

        // Creates a onClickListener for the addTag button
        buttonAddTag = findViewById(R.id.btAddTag);
        buttonAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds an empty Tag
                tagAdapterList.addTag("");
            }
        });

        etbName = findViewById(R.id.etbName);
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
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                tagAdapterList.concludeUpdate(Integer.parseInt(data.getStringExtra("pos")), data.getStringExtra("newTag"));
            }
        }
        else {
            if ((data != null) && requestCode == PICK_PHOTO_CODE) {
                Uri photoUri = data.getData();

                // Load the image located at photoUri into selectedImage
                Bitmap selectedImage = loadFromUri(photoUri);
                imageBase64 = encodeToBase64(selectedImage);


                // Load the selected image into a preview
                ivSelectedImage = findViewById(R.id.ivSelectedImage);
                ivSelectedImage.setImageBitmap(selectedImage);

                // Set the imagechanges to true
                imageChange = true;
            }
        }
    }

    // Converts a bitmap to a base64 String
    public static String encodeToBase64(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] b = baos.toByteArray();
        String output = Base64.getEncoder().encodeToString(b);
        String prefix = "data:image/png;base64,";
        return(prefix+output);
    }

    // Function to update the recipe
    public void updateRecipe(int id, String name, String imageUrl, List<String> tags, List<String> steps, List<IngredientDisplay> ingredients){
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<RecipeDisplay> callPatch = null;

        // Was used to convert the name of ingredient to an id
//        List<IngredientDisplay> idIngredientDisplayToLink = new ArrayList<>();
//        for(int j = 0; j<ingredients.size(); j++){
//            for(int k = 0; k<allIngredientList.size(); k++){
//                if(ingredients.get(j).getName() == allIngredientList.get(k).getName()){
//                    idIngredientDisplayToLink.add(new IngredientDisplay(allIngredientList.get(k).getId(), null));
//                }
//            }
//        }

        if(imageChange){
            // Image has changed
            RecipePatchWithImage recipePatch = new RecipePatchWithImage(name, imageUrl, tags, steps, ingredients);
            callPatch = server.patchRecipeById(recipeDisplay.getId(), recipePatch);
        }
        else if(!imageChange){
            // Image didnt change
            RecipePatchNoImage recipePatch = new RecipePatchNoImage(name, tags, steps, ingredients);
            callPatch = server.patchRecipeById(recipeDisplay.getId(), recipePatch);
        }
        else{
            // Log error (null)
        }

        if(callPatch != null){
            callPatch.enqueue((new Callback<RecipeDisplay>() {
                @Override
                public void onResponse(Call<RecipeDisplay> call, Response<RecipeDisplay> response) {
                    setResult(RESULT_OK);
                    ua.finish();
                }

                @Override
                public void onFailure(Call<RecipeDisplay> call, Throwable t) {

                }
            }));
        }
    }

    // These functions create the lists of Ingredient, Tags and Steps
    public List<IngredientDisplay> pullIngredientList(){
        List<IngredientDisplay> ingredientList = new ArrayList<>();
        for(int i = 0; i < ingredientDisplay.size(); i++){
            IngredientDisplay filledIngredient = new IngredientDisplay(ingredientDisplay.get(i).getId(),ingredientDisplay.get(i).getName(), allIngredientList);
            ingredientList.add(filledIngredient);
        }
        return(ingredientList);
    }

    public List<String> pullStepList(){
        List<String> stepList = new ArrayList<>();
        for(int i = 0; i < recipeDisplay.getSteps().size(); i++){
            stepList.add(recipeDisplay.getSteps().get(i));
        }
        return(stepList);
    }

    public List<String> pullTagList(){
        List<String> tagList = new ArrayList<>();
        for(int i = 0; i < recipeDisplay.getTags().size(); i++){
            tagList.add(recipeDisplay.getTags().get(i));
        }
        return(tagList);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.info.broadcast.pullIngredientList");
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

            ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
            // Call the server to get the ingredients associated with the recipe
            int recipeID = intent.getIntExtra("id", -1);
            Call<List<IngredientDisplay>> callIngredientsById = server.getIngredientsByRecipeId(recipeID);
            callIngredientsById.enqueue(new Callback<List<IngredientDisplay>>() {
                @Override
                public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                    ingredientDisplay = response.body();

                    //Only happens on response

                    // Creates the empty ingredient list and sets the recyclerview/adapterlist values for ingredients
                    ingredientList = pullIngredientList();
                    rvIngredients = findViewById(R.id.rvIngredients);
                    rvIngredients.setLayoutManager(new LinearLayoutManager(ua));
                    ingredientAdapterList = new IngredientAdapterList(ingredientList, ua);
                    rvIngredients.setAdapter(ingredientAdapterList);
                }
                @Override
                public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {
                    //Log error
                }
            });
        }
    }

}