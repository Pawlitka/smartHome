package smartDevices;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Rule {

    private final Predicate<SmartDevice> condition;
    private final Consumer<SmartDevice> action;
    private final SmartDevice device;

    public Rule(SmartDevice device, Predicate<SmartDevice> condition, Consumer<SmartDevice> action) {
        this.device = device;
        this.condition = condition;
        this.action = action;
    }

    public boolean check() {
        return condition.test(device);
    }

    public void execute() {
        if(check()) {
            action.accept(device);
        }
    }
}
