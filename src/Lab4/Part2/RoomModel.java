package Lab4.Part2;

//-- REMAINS UNCHANGED
public class RoomModel {
    private static final double StartingTemperature = 24.0;

    /* if the difference between the heated water and the room temperature

    Is 1C then the room temperature will increase by <heaterConstant> every

    Minute

    */

    private static final double heaterConstant = 0.01;

    /* if the difference between the outside temperature and the room

    Temperature is 1C the the room temperature will increase steadily

    With <wallConstant> every minute

    */

    private static final double wallConstant = 0.00055;

    /* if the difference between the outside temperature and the room

    Temperature is 1C then the window is open and the temperature will

    Decrease with <windowConstant> every minuite

    */

    private static final double windowConstant = 0.01;

    double currentTemaprature;

    public RoomModel() {

        currentTemaprature = StartingTemperature; }

    public void updateModel(boolean heatingOn, double heaterWaterTemp, boolean windowOpen, double outSideTemp) {

        double delatHeater = (heatingOn) ? (heaterWaterTemp - currentTemaprature) : 0.0;

        double outsideDelta = currentTemaprature - outSideTemp;

        currentTemaprature += delatHeater * heaterConstant - outsideDelta * wallConstant -

                ((windowOpen) ? (outsideDelta * windowConstant) : 0.0); }

    public double getCurrentTemperature() {

        return currentTemaprature; }
}
