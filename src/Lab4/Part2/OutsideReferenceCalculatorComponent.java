package Lab4.Part2;

import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.TableParser;

import java.util.HashMap;
import java.util.Map;

public class OutsideReferenceCalculatorComponent {
    static String reader = "" +
            "{[<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
            " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
            " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
            " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]" +
            " [<ZR,NL><ZR,NM><ZR,ZR><ZR,PM><ZR,PL>]}";

    static String t2Table = "{[<PL><PM><ZR><NM><NL>]}";

    /* So when the temperature is NL> the command should be PL
    NM> pm
    ZR stays the same
    PM > it should be cooler so NM
    PL> NL */

    private FuzzyPetriNet net;

    private int p1OustideTemInp;

    private int t2Out;

    private FuzzyDriver outsideTempDriver;

    private FuzzyDriver tankWaterTemeDriver;

    private FullRecorder rec;

    private AsyncronRunnableExecutor execcutor;


    public OutsideReferenceCalculatorComponent(Plant plant, HeaterTankControllerComponent comp, long simPeriod) {

        TableParser parser = new TableParser();

        net = new FuzzyPetriNet();

        // Build the petri net here // your homework
        int p0 = net.addPlace();
        net.setInitialMarkingForPlace(p0, FuzzyToken.zeroToken());

        p1OustideTemInp = net.addInputPlace();

        int t0 = net.addTransition(0, parser.parseTwoXTwoTable(reader));
        net.addArcFromPlaceToTransition(p0, t0, 1.0);
        net.addArcFromPlaceToTransition(p1OustideTemInp, t0, 1.0);

        int p2 = net.addPlace();
        net.addArcFromTransitionToPlace(t0, p2);

        int p3 = net.addPlace();
        net.addArcFromTransitionToPlace(t0, p3);

        int t1Delay = net.addTransition(1, OneXOneTable.defaultTable());
        net.addArcFromPlaceToTransition(p2, t1Delay, 1.0);
        net.addArcFromTransitionToPlace(t1Delay, p0);

        t2Out = net.addOuputTransition(parser.parseOneXOneTable(t2Table));
        net.addArcFromPlaceToTransition(p3, t2Out, 1.0);

        // ------
        outsideTempDriver = FuzzyDriver.createDriverFromMinMax(-30, 10);

        tankWaterTemeDriver = FuzzyDriver.createDriverFromMinMax(45, 68);

        net.addActionForOuputTransition(t2Out, tk -> { //<<<<<<<<<< this is the t2 action
            comp.setWaterRefTemp(tankWaterTemeDriver.defuzzify(tk));//<<< that is connected to
            //<<< The water tank
        });

        rec = new FullRecorder();

        execcutor = new AsyncronRunnableExecutor(net, simPeriod);

        execcutor.setRecorder(rec);

    }


    public void start() {
        (new Thread(execcutor)).start();
    }

    public void stop() {
        execcutor.stop();
    }

    public void setOutsideTemp(double waterRefTemp) {
        Map<Integer, FuzzyToken> inps = new HashMap<Integer, FuzzyToken>();
        inps.put(p1OustideTemInp, outsideTempDriver.fuzzifie(waterRefTemp));
        execcutor.putTokenInInputPlace(inps);
    }

    public FuzzyPetriNet getNet() {
        return net;
    }

    public FullRecorder getRecorder() {
        return rec;
    }
}
