package com.ludba.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText heightValue;
    EditText weightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        heightValue = (EditText) findViewById(R.id.editTextHeight);
        weightValue = (EditText) findViewById(R.id.editTextWeight);
        BootstrapButton computeBMI = (BootstrapButton) findViewById(R.id.bootstrapSubmitButton);
        final TextView bmiAnswer = (TextView) findViewById(R.id.textViewAnswer);


        computeBMI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setErrorMessage(heightValue, "Height");
                setErrorMessage(weightValue, "Weight");
                closeKeyboard();

                try {
                    bmiAnswer.setText(getBMIValuetoString(getHeightValue(), getWeightValue()));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    protected void setErrorMessage(EditText et, String attributeName){
        if (et.getText().toString().trim().length() < 1) {
            et.requestFocus();
            et.setError(attributeName + " is required.");
        }
    }

    protected double getHeightValue() throws Exception{
        double height = Double.parseDouble(heightValue.getText().toString());
        if (height <= 0)
            throw new Exception("Height must be positive.");
        return height;
    }

    protected double getWeightValue() throws Exception{
        double weight = Double.parseDouble(weightValue.getText().toString());
        if (weight <= 0)
            throw new Exception("Weight must be positive.");
        return weight;
    }

    protected double getBMIValue(double height, double weight){
        return (double) weight/(height*height);
    }

    protected String getBMIValuetoString(double height, double weight) throws Exception {
        return Double.toString(getBMIValue(getHeightValue(), getWeightValue()));
    }

    protected void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
