package Lab3;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.*;
import core.FuzzyPetriLogic.Executor.*;
import core.FuzzyPetriLogic.PetriNet.*;
import core.FuzzyPetriLogic.PetriNet.Recorders.*;
import core.FuzzyPetriLogic.Tables.*;
import core.TableParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.io.File.separator;

public class Ex2 {
    // FLRS table for T0 that implements P0-P1

    String differentiator = "" + //

            "{[<ZR><NM><NL><NL><NL>]" + //

            " [<PM><ZR><NM><NL><NL>]" + //

            " [<PL><PM><ZR><NM><NL>]" + //

            " [<PL><PL><PM><ZR><NM>]" + //

            " [<PL><PL><PL><PM><ZR>]}";

    // FLRS table for T1 that makes the selection according to the result P0-P1
    // (positive or negative)

    //String separator = "{[<NL,FF><NL,FF><FF,FF><FF,PL><FF,PL>]}";
    //Modified for c)
    String separator = "{[<FF,NL><FF,NL><FF,FF><PL,FF><PL,FF>]}";

    public Ex2() {
        // the Petri network is being constructed
        TableParser parser = new TableParser();
        FuzzyPetriNet petriNet = new FuzzyPetriNet();

        // adding the input places
        int p0Inp = petriNet.addInputPlace();
        int p1Inp = petriNet.addInputPlace();

        // attaching to the transition t0 the corresponding FLRS table
        TwoXOneTable diffTable = parser.parseTwoXOneTable(differentiator);
        int t0 = petriNet.addTransition(0, diffTable);

        // add the arcs and the weights corresponding to the Petri Net
        petriNet.addArcFromPlaceToTransition(p0Inp, t0, 1.0);
        petriNet.addArcFromPlaceToTransition(p1Inp, t0, 1.0);

        // add the places and arc corresponding to the Petri Net
        int p2 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t0, p2);

        int t1 = petriNet.addTransition(0,
                parser.parseOneXTwoTable(separator));
        petriNet.addArcFromPlaceToTransition(p2, t1, 1.0);

        int p3 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p3);

        int p4 = petriNet.addPlace();
        petriNet.addArcFromTransitionToPlace(t1, p4);

        int t2Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p3, t2Out, 1.0);

        // associating an action of the output transition t2
        Consumer cons = new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println("Output From Transition 2: " + t.toString());
            }
        };
        petriNet.addActionForOuputTransition(t2Out, cons);

        int t3Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
        petriNet.addArcFromPlaceToTransition(p4, t3Out, 1.0);

        // associating an action of the output transition t3
        Consumer consumer = new Consumer<FuzzyToken>() {
            @Override
            public void accept(FuzzyToken t) {
                System.out.println("Output From Transition 3: " + t.toString());
            }
        };
        petriNet.addActionForOuputTransition(t3Out, consumer);

        // creating the date Petri Net executor and specifying the period in
        // milliseconds

        AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);

        // creating an object for visualizing the behavior of the Petri net

        FullRecorder recorder = new FullRecorder();

        executor.setRecorder(recorder);

        FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

        // launching the execution of the thread that contains the executor

        (new Thread(executor)).start();

        for (int i = 0; i < 1080; i++) {

            // constructing the dictionary collection (map) for inputs

            Map<Integer, FuzzyToken> inps = new HashMap<>();

            //if (i % 10 < 5) {

                /* Original Code
				inps.put(p0Inp, driver.fuzzifie(i/100.0));
				inps.put(p1Inp, driver.fuzzifie(i/-100.0));
			    } else {
				inps.put(p1Inp, driver.fuzzifie(i/100.0));
				inps.put(p0Inp, driver.fuzzifie(i/-100.0)); }

				*/

                // placing the fuzzyficated token

                inps.put(p0Inp, driver.fuzzifie(Math.sin(Math.toRadians(i))));
                inps.put(p1Inp, driver.fuzzifie(Math.cos(Math.toRadians(i))));

            //} else {

              //  inps.put(p1Inp, driver.fuzzifie(Math.sin(i)));
               // inps.put(p0Inp, driver.fuzzifie(Math.cos(i)));

           // }
            // placing the input tokens for the executer

            executor.putTokenInInputPlace(inps);

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.stop();
        // visualizing the Petri Net and its behavoir.
        FuzzyPVizualzer.visualize(petriNet, recorder);
    }

    public static void main(String[] main) {
        new Ex2();
    }
}
