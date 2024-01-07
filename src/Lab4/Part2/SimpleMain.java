package Lab4.Part2;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;

import java.util.List;

public class SimpleMain {
    private static final int SIM_PERIOD = 10;

    public static void main(String[] args) {

        Scenario scenario = Scenario.winterDay();

        Plant plant = new Plant(SIM_PERIOD, scenario);

        HeaterTankControllerComponent tankController = new HeaterTankControllerComponent(plant, SIM_PERIOD);

        RoomTemperatureControllerComponent roomController = new RoomTemperatureControllerComponent(plant, SIM_PERIOD);

        roomController.start();

        tankController.start();

        plant.start();

        double waterRefTemp = 28.0;

        double roomTemperature = 24.0;

        for (int i = 0; i < scenario.getScenarioLength(); i++) {

            tankController.setWaterRefTemp(waterRefTemp);

            tankController.setTankWaterTemp(plant.getTankWaterTemperature());

            roomController.setInput(roomTemperature, plant.getRoomTemperature());

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        tankController.stop();

        roomController.stop();

        // BEGIN: 1a2b3c4d5e6f
        MainView windowTankController = FuzzyPVizualzer.visualize(tankController.getNet(), tankController.getRecorder());

        MainView windowTermostat = FuzzyPVizualzer.visualize(roomController.getNet(), roomController.getRecorder());
        // END: 1a2b3c4d5e6f

        // BEGIN: 7g8h9i0j
        Plotter plotterTemperatureLog = new Plotter(plant.getTemperatureLogs());

        Plotter plotterCommandLog = new Plotter(plant.getCommandLogs());

        windowTankController.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());

        windowTermostat.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());

        windowTankController.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

        windowTermostat.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());
        // END: 7g8h9i0j

        double[] tankTempStats = SimpleMain.calcStatistics(plant.getTemperatureLogs().get("tankTemp"));

        double[] rommTempStsats = SimpleMain.calcStatistics(plant.getTemperatureLogs().get("roomTemp"));

        System.out.println("max tank temp :" + tankTempStats[0]);
        System.out.println("min tank temp :" + tankTempStats[1]);
        System.out.println("avg tank temp :" + tankTempStats[2]);
        System.out.println("max room temp :" + rommTempStsats[0]);
        System.out.println("min room temp :" + rommTempStsats[1]);
        System.out.println("avg room temp :" + rommTempStsats[2]);
        System.out.println("heater on ratio:" + plant.heatingOnRatio());
        System.out.println("max nr of mins continous heating on:" + plant.maxContiniousHeaterOn());
        System.out.println("all consunption ::" + plant.gasConsumption());
        System.out.println("avg consunption in a min ::" + plant.gasConsumption() / scenario.getScenarioLength());
    }

    public static double[] calcStatistics(List<Double> list) {
        double min = 1000.0;
        double max = 0.0;
        double sum = 0.0;

        for (Double d : list) {
            min = (min > d) ? d : min;
            max = (max < d) ? d : max;
            sum += d;
        }
        return new double[] { max, min, sum / list.size() };
    }
}
