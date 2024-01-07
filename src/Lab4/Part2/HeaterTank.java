package Lab4.Part2;

//----- THIS ONE IS UNCHANGED----//
public class HeaterTank {
    private static final double pipeWaterTemerature = 7;

    private static final double maxWaterTemeprature = 75;

    private static final double startTemperature = 23;

    private static final double theoraticalRoomTemp = 23;

    double curentWaterTemepartaure;

    public HeaterTank() {

        curentWaterTemepartaure = startTemperature; }

    public void updateSystem(boolean heaterOn, double gasCmd){

        gasCmd = (gasCmd < 0.0) ? 0.0 : ((gasCmd > 1.0) ? 1.0 : gasCmd);

        curentWaterTemepartaure += -(curentWaterTemepartaure - pipeWaterTemerature) * 0.1 * ((heaterOn) ? 1.0 : 0.0) +

                (maxWaterTemeprature - curentWaterTemepartaure) * 0.4 * gasCmd

                - (curentWaterTemepartaure - theoraticalRoomTemp) * 0.005; }

    public double getHotWaterTemeprature() {

        return curentWaterTemepartaure; }
}
