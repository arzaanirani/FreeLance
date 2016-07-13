package com.example.sayeed.xyzconferenceapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SurveysActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_surveys);
        findViewById(R.id.surveysubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                try {
                    if ((findViewById(((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId())) == null ||
                            (findViewById(((RadioGroup) findViewById(R.id.radioGroup2)).getCheckedRadioButtonId())) == null)
                        return;
                    String output = "";
                    output += ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId())).getText();
                    output += ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup2)).getCheckedRadioButtonId())).getText();

                    FileOutputStream fOut = openFileOutput("survey.txt",
                            MODE_WORLD_READABLE);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);
                    osw.write(output);
                    osw.flush();
                    osw.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }

}