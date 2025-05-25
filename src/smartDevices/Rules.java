package smartDevices;

public class Rules {

    private SmartDevice temperatureSensor;
    private SmartDevice smartTv;
    private SmartDevice lightBulb;
    private SmartDevice outlet;
    public Rule turnOnHeaterWhenCold = new Rule(
            temperatureSensor,
            sensor -> ((TemperatureSensor) sensor).getTemperature() < 19,
            sensor -> {
                System.out.println("Temperature is low, turning on the heater.");
                temperatureSensor.simulate();
            }
    );

    public Rule changeTvChannelIfOn = new Rule(
            smartTv,
            device -> ((SmartTv) device).isOn(),
            (smartTv) -> smartTv.simulate()
    );

    public Rule softLightTurnOn = new Rule(
            lightBulb,
            device -> !((LightBulb) device).isOn(),
            device -> {
                LightBulb lb = (LightBulb) lightBulb;
                lb.setValue(0.3f); // ustaw niską jasność
                lb.turnOn();
            }
    );

    public Rule turnOffUnusedOutlet = new Rule(
            outlet,
            device -> {
                Outlet o = (Outlet) device;
                return o.isOn() && !o.isUse;
            },
            device -> {
                System.out.println("No device is using the outlet, turning it off.");
                ((Outlet) outlet).turnOff();
            }
    );
}
