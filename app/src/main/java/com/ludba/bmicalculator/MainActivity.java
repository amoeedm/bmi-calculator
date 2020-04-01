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

    private enum BMI
    {
        UNDERWEIGHT,
        NORMAL,
        OVERWEIGHT,
        OBESE
    }

    EditText heightValue;
    EditText weightValue;
    TextView bmiExplanation;
    BMI typeClassification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        heightValue = (EditText) findViewById(R.id.editTextHeight);
        weightValue = (EditText) findViewById(R.id.editTextWeight);
        bmiExplanation = (TextView) findViewById(R.id.textViewExplanation);
        final TextView bmiAnswer = (TextView) findViewById(R.id.textViewAnswer);
        BootstrapButton computeBMI = (BootstrapButton) findViewById(R.id.bootstrapSubmitButton);

        computeBMI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setErrorMessageIfEmptyField(heightValue, "Height");
                setErrorMessageIfEmptyField(weightValue, "Weight");
                closeKeyboard();
                setComputationAnswer(bmiAnswer, heightValue, weightValue);
            }
        });

    }

    protected void setComputationAnswer(TextView bmi, EditText height, EditText weight){
        try {
            String bmiValueText = getBMIValuetoString(height, weight);
            bmi.setText(bmiValueText);
            setBMIClassification(Double.valueOf(bmiValueText));
            bmiExplanation.setText("Your nutritional status is " +
                    typeClassification.name().toLowerCase() + " according to the World Health Organization. " +
                    "A normal adult with your height should weigh between " + String.valueOf(lowerValue(height)) +
                    " kg and " + String.valueOf(upperValue((height))) + " kg.");
        }
        catch (Exception e){
            bmi.setText("");
        }
    }

    protected int lowerValue(EditText h) throws Exception{
        double height = getValue(h);
        return (int) Math.round(18.5*getValue(h)*getValue(h));
    }

    protected int upperValue(EditText h) throws Exception {
        double height = getValue(h);
        return (int) Math.round(25*height*height);
    }

    protected void setBMIClassification(double bmiValue){
        if(bmiValue < 18.5)
            typeClassification = BMI.UNDERWEIGHT;
        else if(bmiValue < 25)
            typeClassification = BMI.NORMAL;
        else if(bmiValue < 30)
            typeClassification = BMI.OVERWEIGHT;
        else
            typeClassification = BMI.OBESE;
    }

    protected void setErrorMessageIfEmptyField(EditText et, String attributeName){
        if (et.getText().toString().trim().length() < 1) {
            et.requestFocus();
            et.setError(attributeName + " is required.");
        }
    }

    protected double getValue(EditText et) throws Exception{
        double value = Double.parseDouble(et.getText().toString());
        if (value <= 0)
            throw new Exception("Value must be positive");
        return value;
    }

    protected double getBMIValue(double height, double weight){
        return (double) weight/(height*height);
    }


    protected String getBMIValuetoString(EditText height, EditText weight) throws Exception {
        double roundedValue =  Math.round((getBMIValue(getValue(height), getValue(weight))) * 100.0) / 100.0;
        return Double.toString(roundedValue);
    }

    protected void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
