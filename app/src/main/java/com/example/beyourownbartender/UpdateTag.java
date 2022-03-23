package com.example.beyourownbartender;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateTag extends AppCompatActivity {

    Button btSubmitUpdate;
    EditText tagTb;
    TextView titleTV;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tag);

        Intent intent = getIntent();
        btSubmitUpdate = findViewById(R.id.btSubmitTagUpdate);
        String pos = intent.getStringExtra("pos");
        int posInt = Integer.parseInt(pos);
        titleTV = findViewById(R.id.tvTitleTagUpdate);
        String titleText = "Etape no : "+Integer.toString(posInt+1);
        titleTV.setText(titleText);
        tagTb = findViewById(R.id.tbNewTag);
        if(intent.getStringExtra("oldTag") != null){
            tagTb.setText(intent.getStringExtra("oldTag"));
        }

        btSubmitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReturn = new Intent();
                tagTb = findViewById(R.id.tbNewTag);
                String tagText = tagTb.getText().toString();
                intentReturn.putExtra("newTag",tagText);
                intentReturn.putExtra("pos", pos);
                setResult(RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}