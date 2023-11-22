package Fuzzy;

import java.util.Map;

public class TwoXTwoTable {
    //constructing FLRS table for two inputs and two outputs

    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable;

    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable;

    public TwoXTwoTable(Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>>

                                firstTable,

                        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable) {

        this.firstTable = firstTable;

        this.secondTable = secondTable;

    }

//implementing the FLRS table execution

    public FuzzyToken[] execute(FuzzyToken firstToken, FuzzyToken

            secondToken) {

        if (firstTable == null || secondTable == null) {

            throw new RuntimeException("Tables not setted");

        }

        FuzzyToken toRet1 = new FuzzyToken(0.0, 0.0, 0.0, 0.0, 0.0);

        FuzzyToken toRet2 = new FuzzyToken(0.0, 0.0, 0.0, 0.0, 0.0);

        for (FuzzyValue firstTokenNonZero :

                firstToken.getNonZeroValues()) {

            for (FuzzyValue secondNonZero :

                    secondToken.getNonZeroValues()) {

                FuzzyValue firstConclision =

                        firstTable.get(firstTokenNonZero).get(secondNonZero);

                FuzzyValue secondConclusion =

                        secondTable.get(firstTokenNonZero).get(secondNonZero);

                double valueOfConclision =

                        firstToken.getFuzzyValue(firstTokenNonZero)

                                * secondToken.getFuzzyValue(secondNonZero);

                toRet1.addToFuzzyValue(firstConclision,

                        valueOfConclision);

                toRet2.addToFuzzyValue(secondConclusion,

                        valueOfConclision);

            }

        }

        toRet1.normalize();

        toRet2.normalize();

        return new FuzzyToken[] { toRet1, toRet2 };

    }

}
