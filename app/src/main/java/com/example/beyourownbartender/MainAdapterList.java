package com.example.beyourownbartender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainAdapterList extends RecyclerView.Adapter<MainAdapterList.MainViewHolder> {
    private List<Recipe> recipes;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BufferedReader reader;
        String line;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL("http://api.impostor.services/api/recipes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                parse(builder.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_recipe_layout, parent, false);
        return new MainViewHolder(view);
    }

    public static String parse(String response) throws JSONException {
        JSONArray responseArray = new JSONArray(response);
        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject responseObject = responseArray.getJSONObject(i);
            int id = responseObject.getInt("id");
            String name = responseObject.getString("name");
            int rating = responseObject.getInt("rating");
            Log.d("I", "hi " + id + " " + name + " " + " " + rating);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
