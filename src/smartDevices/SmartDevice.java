package smartDevices;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class SmartDevice {
    protected final UUID uuid;
    protected String name;
    protected DeviceStatus status = DeviceStatus.OFF;
    protected final HashSet<DeviceStatus> allowedStatuses;

    SmartDevice(HashSet<DeviceStatus> allowedStatuses) {
        this.allowedStatuses = allowedStatuses;
        this.uuid = UUID.randomUUID();
    }

    public  abstract void simulate();

    public List<String> getAllowedStatuses() {
        return allowedStatuses.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public void setStatus(DeviceStatus status) {
        checkIfStatusIsAllowed(status);
        this.status = status;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return name + " " + status;
    }

    private void checkIfStatusIsAllowed(DeviceStatus status) {
        if(!this.allowedStatuses.contains(status)) {
            throw new IllegalArgumentException("The \"" + status + "\" status is not allowed");
        }
    }

    public String getName() {
        return name;
    }
}
