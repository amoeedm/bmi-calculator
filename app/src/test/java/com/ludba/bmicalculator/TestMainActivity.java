package com.ludba.bmicalculator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestMainActivity {

    @Test
    public void bmiValueIsCorrectForPositiveIntegerValues() {
        MainActivity m = new MainActivity();
        double bmi = m.getBMIValue(2, 80);
        assertEquals(bmi, 20, 0.001);
    }

    @Test
    public void bmiValueIsCorrectForPositiveDoubleValues() {
        MainActivity m = new MainActivity();
        double bmi = m.getBMIValue(1.6, 60);
        assertEquals(bmi, 23.4375, 0.00001);
    }

    @Test
    public void bmiValueThrowsExceptionForNegativeHeight() {
        MainActivity m = new MainActivity();
        try {
            double bmi = m.getBMIValue(-1.6, 60);
        }
        catch (Exception e) {
            assertThat(e.getMessage(), is("Height must be positive."));
        }
    }

    @Test
    public void bmiValueThrowsExceptionForNegativeWeight() {
        MainActivity m = new MainActivity();
        try {
            double bmi = m.getBMIValue(1.6, -60);
        }
        catch (Exception e) {
            assertThat(e.getMessage(), is("Weight must be positive."));
        }
    }

}