package Fuzzy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FuzzyToken {
    private Map<FuzzyValue, Double> fuzzyValues;

    public FuzzyToken(Double NL, Double NM, Double ZR, Double PM,

                      Double PL) {

        fuzzyValues = new EnumMap<>(FuzzyValue.class);

        fuzzyValues.put(FuzzyValue.NL, NL);

        fuzzyValues.put(FuzzyValue.NM, NM);

        fuzzyValues.put(FuzzyValue.ZR, ZR);

        fuzzyValues.put(FuzzyValue.PM, PM);

        fuzzyValues.put(FuzzyValue.PL, PL);

        normalize();

    }

    // normalizing the values

    public void normalize() {

        double sum = 0.0;

        for (FuzzyValue index : fuzzyValues.keySet()) {

            sum += fuzzyValues.get(index);

        }

        if (sum != 0.0) {

            for (FuzzyValue index : fuzzyValues.keySet()) {

                fuzzyValues.put(index, fuzzyValues.get(index) /

                        sum);

            }

        }

    }

    public void addToFuzzyValue(FuzzyValue fuzzyVal, double val) {

        fuzzyValues.put(fuzzyVal, fuzzyValues.get(fuzzyVal) + val);

    }

    public double getFuzzyValue(FuzzyValue val) {

        return fuzzyValues.get(val);

    }

    // returning the elements in fuzzyset with value different from zero

    public List<FuzzyValue> getNonZeroValues() {

        List<FuzzyValue> toRet = new ArrayList<>();

        for (FuzzyValue index : fuzzyValues.keySet()) {

            if (fuzzyValues.get(index) != 0.0) {

                toRet.add(index);

            }

        }

        return toRet;

    }
}
