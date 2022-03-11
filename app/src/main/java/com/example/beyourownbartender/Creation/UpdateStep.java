package com.example.beyourownbartender.Creation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beyourownbartender.R;

public class UpdateStep extends AppCompatActivity {

    Button btSubmitUpdate;
    EditText stepTb;
    TextView titleTV;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_step);

        Intent intent = getIntent();
        btSubmitUpdate = findViewById(R.id.btSubmitUpdate);
        String pos = intent.getStringExtra("pos");
        int posInt = Integer.parseInt(pos);
        titleTV = findViewById(R.id.tvTitleStepUpdate);
        String titleText = "Etape no : "+Integer.toString(posInt+1);
        titleTV.setText(titleText);
        stepTb = findViewById(R.id.tbNewStep);
        if(intent.getStringExtra("oldStep") != null){
            stepTb.setText(intent.getStringExtra("oldStep"));
        }

        btSubmitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReturn = new Intent();
                stepTb = findViewById(R.id.tbNewStep);
                String stepText = stepTb.getText().toString();
                intentReturn.putExtra("newStep",stepText);
                intentReturn.putExtra("pos", pos);
                setResult(RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}