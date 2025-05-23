package smart.devices;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Rule {

    private Predicate<SmartDevice> condition;
    private Consumer<SmartDevice> action;
    private SmartDevice device;

    public void check() {
    }
}
