package Fuzzy;

import java.util.EnumMap;
import java.util.Map;

public class FuzzyDefuzzy {
    Map<FuzzyValue, Double> limits;

    public FuzzyDefuzzy(double negativeLimit, double negativeMidleValue, double zero, double positiveMidleValuem,
                        double positiveLimit) {

        limits = new EnumMap<FuzzyValue, Double>(FuzzyValue.class);
        limits.put(FuzzyValue.NL, negativeLimit);
        limits.put(FuzzyValue.NM, negativeMidleValue);
        limits.put(FuzzyValue.ZR, zero);
        limits.put(FuzzyValue.PM, positiveMidleValuem);
        limits.put(FuzzyValue.PL, positiveLimit);

    }

    public FuzzyToken fuzzyfie(double val) {

        FuzzyToken toRet = new FuzzyToken(0.0, 0.0, 0.0, 0.0, 0.0);

        if (val <= limits.get(FuzzyValue.NL)) {

            toRet.addToFuzzyValue(FuzzyValue.NL, 1.0);

        } else if (val > limits.get(FuzzyValue.NL) && val <=

                limits.get(FuzzyValue.NM)) {

            double nm = (limits.get(FuzzyValue.NL) - val) /

                    (limits.get(FuzzyValue.NL) - limits.get(FuzzyValue.NM));

            double nl = 1.0 - nm;

            toRet.addToFuzzyValue(FuzzyValue.NL, nl);

            toRet.addToFuzzyValue(FuzzyValue.NM, nm);

        } else if (val > limits.get(FuzzyValue.NM) && val <=

                limits.get(FuzzyValue.ZR)) {

            double zr = (limits.get(FuzzyValue.NM) - val) /

                    (limits.get(FuzzyValue.NM) - limits.get(FuzzyValue.ZR));

            double nm = 1.0 - zr;

            toRet.addToFuzzyValue(FuzzyValue.NM, nm);

            toRet.addToFuzzyValue(FuzzyValue.ZR, zr);

        } else if (val > limits.get(FuzzyValue.ZR) && val <=

                limits.get(FuzzyValue.PM)) {

            double pm = (limits.get(FuzzyValue.ZR) - val) /

                    (limits.get(FuzzyValue.ZR) - limits.get(FuzzyValue.PM));

            double zr = 1.0 - pm;

            toRet.addToFuzzyValue(FuzzyValue.ZR, zr);

            toRet.addToFuzzyValue(FuzzyValue.PM, pm);

        } else if (val > limits.get(FuzzyValue.PM) && val <=

                limits.get(FuzzyValue.PL)) {

            double pl = (limits.get(FuzzyValue.PM) - val) /

                    (limits.get(FuzzyValue.PM) - limits.get(FuzzyValue.PL));

            double pm = 1.0 - pl;

            toRet.addToFuzzyValue(FuzzyValue.PM, pm);

            toRet.addToFuzzyValue(FuzzyValue.PL, pl);

        } else {

            toRet.addToFuzzyValue(FuzzyValue.PL, 1.0);

        }

        return toRet;

    }

    public double defuzzify(FuzzyToken tk) {

        double weighSum = 0.0;

        double allSum = 0.0;

        for (FuzzyValue fv : tk.getNonZeroValues()) {

            weighSum += limits.get(fv) * tk.getFuzzyValue(fv);

            allSum += tk.getFuzzyValue(fv);

        }

        return weighSum / allSum;

    }
}
