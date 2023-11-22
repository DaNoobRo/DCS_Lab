package Lab3;

import Fuzzy.FuzzyDefuzzy;
import Fuzzy.FuzzyToken;
import Fuzzy.FuzzyValue;
import Fuzzy.TwoXTwoTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Map;

public class Ex1 {
    public static TwoXTwoTable createInversor() {

        // construct tabela1 FLRS for inversor, first output
        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable1 = new EnumMap<>(FuzzyValue.class);

        Map<FuzzyValue, FuzzyValue> nlLine = new EnumMap<>(FuzzyValue.class);
       ruleTable1.put(FuzzyValue.NL, nlLine);
        /*
        nlLine.put(FuzzyValue.NL, FuzzyValue.PL);
        nlLine.put(FuzzyValue.NM, FuzzyValue.PM);
        nlLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        nlLine.put(FuzzyValue.PM, FuzzyValue.NL);
        nlLine.put(FuzzyValue.PL, FuzzyValue.ZR);

        */
        nlLine.put(FuzzyValue.NL, FuzzyValue.NL);
        nlLine.put(FuzzyValue.NM, FuzzyValue.NL);
        nlLine.put(FuzzyValue.ZR, FuzzyValue.NL);
        nlLine.put(FuzzyValue.PM, FuzzyValue.NM);
        nlLine.put(FuzzyValue.PL, FuzzyValue.ZR);


        Map<FuzzyValue, FuzzyValue> nmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.NM, nmLine);
        /*
        nmLine.put(FuzzyValue.NL, FuzzyValue.PL);
        nmLine.put(FuzzyValue.NM, FuzzyValue.NM);
        nmLine.put(FuzzyValue.ZR, FuzzyValue.PL);
        nmLine.put(FuzzyValue.PM, FuzzyValue.PL);
        nmLine.put(FuzzyValue.PL, FuzzyValue.NM);
        */

        nmLine.put(FuzzyValue.NL, FuzzyValue.NL);
        nmLine.put(FuzzyValue.NM, FuzzyValue.NL);
        nmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
        nmLine.put(FuzzyValue.PM, FuzzyValue.ZR);
        nmLine.put(FuzzyValue.PL, FuzzyValue.PM);


        Map<FuzzyValue, FuzzyValue> zrLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.ZR, zrLine);

        /*
        zrLine.put(FuzzyValue.NL, FuzzyValue.NL);
        zrLine.put(FuzzyValue.NM, FuzzyValue.PL);
        zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PM, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PL, FuzzyValue.PL);
        */

        zrLine.put(FuzzyValue.NL, FuzzyValue.NL);
        zrLine.put(FuzzyValue.NM, FuzzyValue.NM);
        zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PM, FuzzyValue.PM);
        zrLine.put(FuzzyValue.PL, FuzzyValue.PL);


        Map<FuzzyValue, FuzzyValue> pmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.PM, pmLine);

        /*
        pmLine.put(FuzzyValue.NL, FuzzyValue.ZR);
        pmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
        pmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
        pmLine.put(FuzzyValue.PM, FuzzyValue.PM);
        pmLine.put(FuzzyValue.PL, FuzzyValue.NL);
        */

        pmLine.put(FuzzyValue.NL, FuzzyValue.NM);
        pmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
        pmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
        pmLine.put(FuzzyValue.PM, FuzzyValue.PL);
        pmLine.put(FuzzyValue.PL, FuzzyValue.PL);


        Map<FuzzyValue, FuzzyValue> plLine = new EnumMap<>(FuzzyValue.class);
        ruleTable1.put(FuzzyValue.PL, plLine);

        /*
        plLine.put(FuzzyValue.NL, FuzzyValue.PM);
        plLine.put(FuzzyValue.NM, FuzzyValue.PM);
        plLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        plLine.put(FuzzyValue.PM, FuzzyValue.NM);
        plLine.put(FuzzyValue.PL, FuzzyValue.PL);
        */

        plLine.put(FuzzyValue.NL, FuzzyValue.ZR);
        plLine.put(FuzzyValue.NM, FuzzyValue.PM);
        plLine.put(FuzzyValue.ZR, FuzzyValue.PL);
        plLine.put(FuzzyValue.PM, FuzzyValue.PL);
        plLine.put(FuzzyValue.PL, FuzzyValue.PL);

        //------------------ TABLE 2 -----------------
        // construct tabela2 FLRS for inversor, the second output

        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2 = new EnumMap<>(FuzzyValue.class);

        Map<FuzzyValue, FuzzyValue> nlLine2 = new EnumMap<>(FuzzyValue.class);
        ruleTable2.put(FuzzyValue.NL, nlLine2);
        /*
        nlLine.put(FuzzyValue.NL, FuzzyValue.PL);
        nlLine.put(FuzzyValue.NM, FuzzyValue.NL);
        nlLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        nlLine.put(FuzzyValue.PM, FuzzyValue.PL);
        nlLine.put(FuzzyValue.PL, FuzzyValue.PL);
        */

        nlLine2.put(FuzzyValue.NL, FuzzyValue.ZR);
        nlLine2.put(FuzzyValue.NM, FuzzyValue.NM);
        nlLine2.put(FuzzyValue.ZR, FuzzyValue.NL);
        nlLine2.put(FuzzyValue.PM, FuzzyValue.NL);
        nlLine2.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> nmLine2 = new EnumMap<>(FuzzyValue.class);
        // nmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable2.put(FuzzyValue.NM, nmLine2);
        /*
        nmLine.put(FuzzyValue.NL, FuzzyValue.PM);
        nmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
        nmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
        nmLine.put(FuzzyValue.PM, FuzzyValue.NM);
        nmLine.put(FuzzyValue.PL, FuzzyValue.PL);
        */

        nmLine2.put(FuzzyValue.NL, FuzzyValue.PM);
        nmLine2.put(FuzzyValue.NM, FuzzyValue.ZR);
        nmLine2.put(FuzzyValue.ZR, FuzzyValue.NM);
        nmLine2.put(FuzzyValue.PM, FuzzyValue.NL);
        nmLine2.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> zrLine2 = new EnumMap<>(FuzzyValue.class);
        //zrLine = new EnumMap<>(FuzzyValue.class);
        ruleTable2.put(FuzzyValue.ZR, zrLine2);

        /*
        zrLine.put(FuzzyValue.NL, FuzzyValue.PM);
        zrLine.put(FuzzyValue.NM, FuzzyValue.NM);
        zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PM, FuzzyValue.NM);
        zrLine.put(FuzzyValue.PL, FuzzyValue.ZR);
        */

        zrLine2.put(FuzzyValue.NL, FuzzyValue.PL);
        zrLine2.put(FuzzyValue.NM, FuzzyValue.PM);
        zrLine2.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine2.put(FuzzyValue.PM, FuzzyValue.NM);
        zrLine2.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> pmLine2 = new EnumMap<>(FuzzyValue.class);
        //pmLine = new EnumMap<>(FuzzyValue.class);
        ruleTable2.put(FuzzyValue.PM, pmLine2);

        /*
        pmLine.put(FuzzyValue.NL, FuzzyValue.PL);
        pmLine.put(FuzzyValue.NM, FuzzyValue.PM);
        pmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
        pmLine.put(FuzzyValue.PM, FuzzyValue.PM);
        pmLine.put(FuzzyValue.PL, FuzzyValue.NL);
        */

        pmLine2.put(FuzzyValue.NL, FuzzyValue.PL);
        pmLine2.put(FuzzyValue.NM, FuzzyValue.PL);
        pmLine2.put(FuzzyValue.ZR, FuzzyValue.PM);
        pmLine2.put(FuzzyValue.PM, FuzzyValue.ZR);
        pmLine2.put(FuzzyValue.PL, FuzzyValue.NM);

        Map<FuzzyValue, FuzzyValue> plLine2 = new EnumMap<>(FuzzyValue.class);
        //plLine = new EnumMap<>(FuzzyValue.class);
        ruleTable2.put(FuzzyValue.PL, plLine2);

        /*
        plLine.put(FuzzyValue.NL, FuzzyValue.ZR);
        plLine.put(FuzzyValue.NM, FuzzyValue.NM);
        plLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        plLine.put(FuzzyValue.PM, FuzzyValue.ZR);
        plLine.put(FuzzyValue.PL, FuzzyValue.NM);
        */

        plLine2.put(FuzzyValue.NL, FuzzyValue.PL);
        plLine2.put(FuzzyValue.NM, FuzzyValue.PL);
        plLine2.put(FuzzyValue.ZR, FuzzyValue.PL);
        plLine2.put(FuzzyValue.PM, FuzzyValue.PM);
        plLine2.put(FuzzyValue.PL, FuzzyValue.ZR);

        // returning FLRS table with two inputs and two outputs

        return new TwoXTwoTable(ruleTable1, ruleTable2);
    }

    public static void main(String[] args) {

        try {
            DecimalFormat decfor = new DecimalFormat("0.00");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(System.in));
            String inputLine="";
            System.out.println("Give weights w1 and w2: (separated by a space): ");
            inputLine = in.readLine();

            String[] weights = inputLine.split(" ");
            double w1 =Double.parseDouble(weights[0]) ;
            double w2 = Double.parseDouble(weights[1]);
            System.out.println("The weights are: w1="+w1+" | w2="+w2);
            // specifying the limits for fuzzyfication, defuzzyfication
            FuzzyDefuzzy fuzDefuz = new FuzzyDefuzzy(-1.0, -0.5, 0.0, 0.5, 1.0);

            // creating FLRS table for tow inputs and two outputs
            TwoXTwoTable inversor = createInversor();
            System.out.println("Give inputs x1 and  x2: (separated by a space): ");
            inputLine = in.readLine();
            // giving the two inputs
            String[] inputs = inputLine.split(" ");
            double x1 = Double.parseDouble(inputs[0]);
            double x2 = Double.parseDouble(inputs[1]);
            System.out.println("The inputs are: x1="+x1+" | x2="+x2);
            // multiplying the inputs with the amplification and fuzzyfication factors
            FuzzyToken inpToken1 = fuzDefuz.fuzzyfie(x1 * w1);
            FuzzyToken inpToken2 = fuzDefuz.fuzzyfie(x2 * w2);

            // executing the FLRS table
            FuzzyToken[] out = inversor.execute(inpToken1, inpToken2);

            // displaying the defuzzyfication results
            System.out.println("Outputs: x3 = " + decfor.format(fuzDefuz.defuzzify(out[0]))+
                    " | x4 = " + decfor.format(fuzDefuz.defuzzify(out[1])));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //read from console
    }

}
