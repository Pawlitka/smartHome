import smartDevices.*;

import java.util.*;

public class CommandLineInterface {
    private final Scanner scanner;
    private final HashMap<String, House> houses = new HashMap<>();
    private boolean isProgramRunning;
    private House currentHouse = null;
    private Room currentRoom = null;
    private Rules rules;

    public CommandLineInterface() {
        scanner = new Scanner(System.in);
        isProgramRunning = true;
    }

    public void start() {
        System.out.println("Welcome in SmartHome interface. Type 'help' for commands, 'exit' to stop and exit program.");
        while (isProgramRunning) {
            printCommandLine("Enter command");
            String input = scanner.nextLine();
            handleCommand(input.trim());
        }
    }

    private void handleCommand(String input) {
        if (input.isEmpty()) return;

        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                showHelp();
                break;
            case "exit":
                isProgramRunning = false;
                System.out.println("You left program.");
                break;
            case "create_house":
                createHouse();
                break;
            case "remove_house":
                removeHouse();
                break;
            case "list_houses":
                listHouses();
                break;
            case "enter_house":
                enter_house();
                break;
            case "exit_house":
                exit_house();
                break;
            case "create_room":
                createRoom();
                break;
            case "remove_room":
                removeRoom();
                break;
            case "enter_room":
                enterRoom();
                break;
            case "list_rooms":
                listRooms();
                break;
            case "list_room_types":
                listRoomTypes();
                break;
            case "exit_room":
                exit_room();
                break;
            case "create_device":
                createDevice();
                break;
            case "remove_device":
                removeDevice();
                break;
            case "list_devices":
                listDevices();
                break;
            case "manage_device":
                manageDevice();
                break;
            case "run_rules":
                rules.turnOnHeaterWhenCold.execute();
                rules.changeTvChannelIfOn.execute();
                rules.softLightTurnOn.execute();
                rules.turnOffUnusedOutlet.execute();
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println(" help              - Show this list of commands and their explanations");
        System.out.println(" create_house      - Create a new smart house");
        System.out.println(" remove_house      - Remove an existing house from the system");
        System.out.println(" list_houses       - Display all created houses");
        System.out.println(" enter_house       - Enter a selected house to manage its rooms and devices");
        System.out.println(" exit_house        - Exit the currently entered house");
        System.out.println(" create_room       - Create a new room in the currently entered house");
        System.out.println(" remove_room       - Remove a room from the currently entered house");
        System.out.println(" enter_room        - Enter a selected room to manage its devices");
        System.out.println(" list_rooms        - Show a list of all rooms in the current house");
        System.out.println(" list_room_types   - List available room types (e.g. Kitchen, Living Room)");
        System.out.println(" exit_room         - Exit the currently entered room");
        System.out.println(" create_device     - Add a new smart device to the currently entered room");
        System.out.println(" remove_device     - Remove a smart device from the current room");
        System.out.println(" list_devices      - Show all devices in the current room");
        System.out.println(" manage_device     - Interact with a specific smart device");
        System.out.println(" run rules         - Execute automation rules for the current setup");
        System.out.println(" exit              - Exit the application");
    }

    private void createHouse() {
        printCommandLine("Enter name for house");
        String name;
        do {
            name = scanner.nextLine();
            if (houses.containsKey(name)) {
                System.out.println("House with \"" + name + "\" already exists. Please use other name for the house.");
            }
        } while (houses.containsKey(name));


        printCommandLine("Enter area size (in m²)");
        Integer areaSize = null;
        while (areaSize == null) {
            try {
                areaSize = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number for area size.");
                printCommandLine("Enter area size (in m²)");
            }
        }

        printCommandLine("Enter address");
        String address = scanner.nextLine();

        House house = new House(name, areaSize, address);
        houses.put(house.getName(), house);


        System.out.println("House \"" + house.getName() + "\" has been created successfully!");
    }

    private void removeHouse() {
        if (houses.isEmpty()) {
            System.out.println("There is no any house yet. You cannot remove house.");
        } else {
            printCommandLine("Enter name for house that you want to delete");
            String name;
            do {
                name = scanner.nextLine();
                if (!houses.containsKey(name)) {
                    System.out.println("House with \"" + name + "\" does not exists. Please use valid name for the house.");
                }
            } while (!houses.containsKey(name));

            if (houses.containsKey(name)) {
                currentHouse = null;
                houses.remove(name);
            } else {
                houses.remove(name);
            }
            System.out.println("House \"" + name + "\" has been deleted successfully!");
        }
    }

    private void listHouses() {
        if (houses.isEmpty()) {
            System.out.println("There are no created houses yet!\nUse \"create_house\" command to create one.");
        } else {
            System.out.println("HOUSE NAME | NUMBER OF ROOMS | ADDRESS");
            for (House house : houses.values()) {
                System.out.println(house.getName() + " | " + house.getNumberOfRooms() + " | " + house.getAddress());
            }
        }
    }

    private void enter_house() {
        if (houses.isEmpty()) {
            System.out.println("There are no created houses yet!\nUse \"create_house\" command to create one.");
        } else {
            listHouses();
            String name = null;
            while (name == null || !houses.containsKey(name)) {
                printCommandLine("Enter name for house");
                name = scanner.nextLine();
                if (!houses.containsKey(name)) {
                    System.out.println("The house with the name does not exist.");
                }
            }
            currentHouse = houses.get(name);
        }
    }

    private void exit_house() {
        if (currentRoom != null) {
            exit_room();
        }
        if (currentHouse == null) {
            System.out.println("You are not in any house now.");
        } else {
            System.out.println("You left \"" + currentHouse.getName() + "\" house.");
        }
        currentHouse = null;
    }

    private void createRoom() {
        if (currentHouse == null) {
            System.out.println("Enter the house where you want to create a room.");
        } else {
            String name;
            do {
                printCommandLine("Enter name for room");
                name = scanner.nextLine();
                if (currentHouse.getRooms().containsKey(name)) {
                    System.out.println("The room\"" + name + "\" already exists. Please choose other name.");
                }
            } while (currentHouse.getRooms().containsKey(name));

            listRoomTypes();

            printCommandLine("Enter type of room by its name (case-insensitive).");
            String roomTypeName = "";
            do {
                printCommandLine("Enter ordinal for room type");
                roomTypeName = scanner.nextLine();
                try {
                    RoomType type = RoomType.valueOf(roomTypeName);
                } catch (IllegalArgumentException _) {
                    System.out.println("The \"" + roomTypeName + "\" does not exist!");
                    roomTypeName = "";
                }
            } while (roomTypeName.isEmpty());

            Room room = new Room(name, RoomType.valueOf(roomTypeName));
            currentHouse.addRoom(room);
            System.out.println("Room has been successfully created.");
        }
    }

    private void removeRoom() {
        if (currentHouse == null) {
            System.out.println("Enter the house where you want to delete a room.");
        } else {
            if (currentHouse.getRooms().isEmpty()) {
                System.out.println("There is no any rooms yet in this house.");
            } else {
                printCommandLine("Enter name for room that you want to delete");
                String name;
                do {
                    name = scanner.nextLine();
                    if (!currentHouse.getRooms().containsKey(name)) {
                        System.out.println("Room with \"" + name + "\" does not exists. Please use valid name for the room.");
                    }
                } while (!currentHouse.getRooms().containsKey(name));

                currentHouse.deleteRoom(name);
                if (Objects.equals(currentRoom.getName(), name)) {
                    currentRoom = null;
                }
                System.out.println("Room \"" + name + "\" has been deleted successfully!");
            }
        }
    }

    private void enterRoom() {
        if (currentHouse == null) {
            System.out.println("Enter the house where you want to enter a room.");
        } else {
            listRooms();
            String name = null;
            while (name == null || !currentHouse.getRooms().containsKey(name)) {
                printCommandLine("Enter name for room");
                name = scanner.nextLine();
                if (!currentHouse.getRooms().containsKey(name)) {
                    System.out.println("The room with the name does not exist.");
                }
            }
            currentRoom = currentHouse.getRooms().get(name);
        }
    }

    private void listRooms() {
        if (currentHouse == null) {
            System.out.println("Enter the house where you want to list rooms.");
        } else {
            if (currentHouse.getRooms().isEmpty()) {
                System.out.println("There are no created rooms yet!\nUse \"create_room\" command to create one.");
            } else {
                System.out.println("ROOM NAME | ROOM TYPE | DEVICES");
                for (Room room : currentHouse.getRooms().values()) {
                    System.out.println(room.getName() + " | " + room.getRoomType() + " | " + room.getDevicesAsString());
                }
            }
        }
    }

    private void listRoomTypes() {
        System.out.println("Room types");
        for (RoomType type : RoomType.values()) {
            System.out.println(type.ordinal() + ". " + type.name());
        }
    }


    private void exit_room() {
        if (currentRoom == null) {
            System.out.println("You are not in any room now.");
        } else {
            System.out.println("You left \"" + currentRoom.getName() + "\" room.");
        }
        currentRoom = null;
    }

    private String getPrefixCli() {
        String housePrefix = "";
        String roomPrefix = "";
        String devicePrefix = "";
        if (currentHouse != null) {
            housePrefix += "[" + currentHouse.getName() + "] ";
        }
        if (currentRoom != null) {
            roomPrefix += "(" + currentRoom.getName() + ") ";
        }

        return housePrefix + roomPrefix + devicePrefix;
    }

    private List<String> getdeviceCommands(SmartDevice device) {
        if (device instanceof TemperatureSensor) {
            return List.of(
                    "set_temperature",
                    "set_status",
                    "get_temperature"
            );
        }
        if (device instanceof Outlet) {
            return List.of(
                    "turn_on",
                    "turn_off",
                    "use_outlet",
                    "is_in_use"
            );
        }
        if (device instanceof LightBulb) {
            return List.of(
                    "turn_on",
                    "turn_off",
                    "is_on",
                    "set_hue",
                    "set_saturation",
                    "set_value",
                    "get_color"
            );
        }
        if (device instanceof SmartTv) {
            return List.of(
                    "turn_on",
                    "turn_off",
                    "is_on"
            );
        }
        if (device instanceof DevicesManager) {
            return List.of(
                    "turn_devices_off",
                    "turn_devices_on",
                    "set_status",
                    "update"
            );
        }
        if (device instanceof MotionSensor) {
            return List.of(
                    "set_status",
                    "is_motion_detected"
            );
        }
        return List.of();
    }

    private void createDevice() {
        if (currentHouse == null || currentRoom == null) {
            System.out.println("Enter the house and room where you want to add devices.");
        } else {
            String name;
            do {
                printCommandLine("Enter name for device");
                name = scanner.nextLine();
                if (currentRoom.getDevices().containsKey(name)) {
                    System.out.println("Device\"" + name + "\" already exists. Please choose other name.");
                }
            } while (currentRoom.getDevices().containsKey(name));

            printCommandLine("Enter type of device by its name (case-insensitive).");
            listDevicesTypes();
            String deviceTypeName = "";
            do {
                printCommandLine("Enter ordinal for device type");
                deviceTypeName = scanner.nextLine();
                try {
                    DeviceType type = DeviceType.valueOf(deviceTypeName);
                } catch (IllegalArgumentException _) {
                    System.out.println("The \"" + deviceTypeName + "\" does not exist!");
                    deviceTypeName = "";
                }
            } while (deviceTypeName.isEmpty());

            switch (deviceTypeName) {
                case "LIGHTBULB" -> {
                    LightBulb lightbulb = new LightBulb(name);
                    currentRoom.addDevice(lightbulb);
                    System.out.println("Lightbulb has been successfully created.");
                }
                case "OUTLET" -> {
                    Outlet outlet = new Outlet(name);
                    currentRoom.addDevice(outlet);
                    System.out.println("Outlet has been successfully created.");
                }
                case "TEMPERATURE_SENSOR" -> {
                    TemperatureSensor temperatureSensor = new TemperatureSensor(name);
                    currentRoom.addDevice(temperatureSensor);
                    System.out.println("Temperature sensor has been successfully created.");
                }
                case "SMART_TV" -> {
                    SmartTv smartTv = new SmartTv(name);
                    currentRoom.addDevice(smartTv);
                    System.out.println("Smart tv has been successfully created.");
                }
                case "MOTION_SENSOR" -> {
                    MotionSensor motionSensor = new MotionSensor(name);
                    currentRoom.addDevice(motionSensor);
                    System.out.println("Motion sensor has been successfully created.");
                }
            }
        }
    }

    private void removeDevice() {
        if (currentHouse == null && currentRoom == null) {
            System.out.println("Enter the house and room where you want to delete a room.");
        } else {
            if (currentRoom.getDevices().isEmpty()) {
                System.out.println("There is no any rooms and devices yet in this house.");
            } else {
                printCommandLine("Enter name for device that you want to delete");
                String name;
                do {
                    name = scanner.nextLine();
                    if (!currentRoom.getDevices().containsKey(name)) {
                        System.out.println("Device with \"" + name + "\" does not exists. Please use valid name for the device.");
                    }
                } while (!currentRoom.getDevices().containsKey(name));

                currentRoom.deleteDevice(name);
                System.out.println("Device \"" + name + "\" has been deleted successfully!");
            }
        }
    }

    private void listDevicesTypes() {
        System.out.println("Devices types");
        for (DeviceType type : DeviceType.values()) {
            System.out.println(type.ordinal() + ". " + type.name());
        }
    }

    private void listDevices() {
        if (currentHouse == null || currentRoom == null) {
            System.out.println("Enter the house and room where you want to list devices.");
        } else {
            if (currentRoom.getDevices().isEmpty()) {
                System.out.println("There are no created devices yet!\nUse \"create_device\" command to create one.");
            } else {
                System.out.println("DEVICE NAME | DEVICE STATUS");
                for (SmartDevice device : currentRoom.getDevices().values()) {
                    System.out.println(device.getName() + " | " + device.getStatus());
                }
            }
        }
    }

    private void manageDevice() {
        listDevices();
        String name;

        do {
            printCommandLine("Choose the device");
            name = scanner.nextLine();
            if (!currentRoom.getDevices().containsKey(name)) {
                System.out.println("Device with name " + name + " does not exist. Enter valid name.");
            }
        } while (!currentRoom.getDevices().containsKey(name));

        SmartDevice device = currentRoom.getDevice(name);
        List<String> commands = getdeviceCommands(device);
        printAllowedCommandsOfDevice(device);
        String command;

        do {
            printCommandLine("Enter command from the list");
            command = scanner.nextLine();
            if(!commands.contains(command)) {
                System.out.println("That command does not exist. Enter valid one.");
            }
        } while (!commands.contains(command));
        Object input = null;

        if(command.equals("set_temperature")) {
            do {
                printCommandLine("Enter valid value for temperature");
                 input = scanner.nextDouble();
            } while (input == null);
        }

        if(command.equals("set_status")) {
            do {
                listDeviceStatuses(device);
                printCommandLine("Enter valid status for device");
                input = scanner.nextLine();
                if (!device.getAllowedStatuses().contains(input)) {
                    System.out.println("The status " + input + " does not exist. Enter valid status.");
                }
            } while (!device.getAllowedStatuses().contains(input));
        }

        if(command.equals("set_hue")) {
            do {
                printCommandLine("Enter float value for hue");
                input = scanner.nextFloat();
                float inputAsFloat = Float.parseFloat(input.toString());
                if(0 <= inputAsFloat && inputAsFloat < 360) {
                    input = inputAsFloat;
                }
            } while (input == null);
        }

        if(command.equals("set_saturation")) {
            do {
                printCommandLine("Enter float value for saturation");
                input = scanner.nextFloat();
                float inputAsFloat = Float.parseFloat(input.toString());
                if(0 <= inputAsFloat && inputAsFloat <= 1) {
                    input = inputAsFloat;
                }
            } while (input == null);
        }

        if(command.equals("set_value")) {
            do {
                printCommandLine("Enter float value for value");
                input = scanner.nextFloat();
                float inputAsFloat = Float.parseFloat(input.toString());
                if(0 <= inputAsFloat && inputAsFloat <= 1) {
                    input = inputAsFloat;
                }
            } while (input == null);
        }

        if(command.equals("add_device")) {
            do {
                printCommandLine("Enter device name that you want to add to manager");
                input = scanner.nextLine();
                if(input != currentRoom.getDevice(name)) {
                    System.out.println("Device with name " + name + " does not exist. Enter valid name.");
                }
            } while (input != currentRoom.getDevice(name));
            input = currentRoom.getDevice(name);
        }

        executeDeviceCommand(device, command, input);

    }

    private void executeDeviceCommand(SmartDevice device, String command, Object input) {
        if(device instanceof TemperatureSensor temperatureSensor) {
            executeTemperatureSensorCommand(temperatureSensor, command, input);
        }
        if(device instanceof Outlet outlet) {
            executeOutletCommand(outlet,command,input);
        }
        if(device instanceof LightBulb lightBulb) {
            executeLightBulbCommand(lightBulb,command,input);
        }
        if(device instanceof  MotionSensor motionSensor) {
            executeMotionSensorCommands(motionSensor,command,input);
        }
        if(device instanceof SmartTv smartTv) {
            executeSmartTvCommands(smartTv,command,input);
        }
        if(device instanceof DevicesManager devicesManager) {
            executeDevicesManagerCommands(devicesManager,command,input);
        }
    }

    private void executeTemperatureSensorCommand(TemperatureSensor temperatureSensor, String command, Object input) {
        switch (command) {
            case "get_temperature" -> {
                System.out.println(temperatureSensor.getTemperature());
                Logger.log("Get temperature", temperatureSensor, currentRoom.getName(), "get_temperature");
            }
            case "set_temperature" -> {
                temperatureSensor.setTemperature(Double.valueOf(input.toString()));
                Logger.log("Set temperature", temperatureSensor, currentRoom.getName(), "set_temperature");
            }
            case "set_status" -> {
                temperatureSensor.setStatus(DeviceStatus.valueOf(input.toString()));
                Logger.log("Set status", temperatureSensor, currentRoom.getName(), "set_status");
            }
        }
    }

    private void executeOutletCommand(Outlet outlet, String command, Object input) {
        switch (command) {
            case "turn_on" -> {
                outlet.turnOn();
                Logger.log("turn on", outlet, currentRoom.getName(), "turn_on");
            }
            case "turn_off" -> {
                outlet.turnOff();
                Logger.log("turn off", outlet, currentRoom.getName(), "turn_off");

            }
            case "is_in_use" -> {
                outlet.isInUse();
                Logger.log("is in use", outlet, currentRoom.getName(), "is_in_use");

            }
            case "use_outlet" -> {
                outlet.useOutlet();
                Logger.log("use outlet", outlet, currentRoom.getName(), "use_outlet");

            }
        }
    }

    private void executeLightBulbCommand(LightBulb lightBulb, String command, Object input) {
        switch (command) {
            case "turn_on" -> {
                lightBulb.turnOn();
                Logger.log("turn on", lightBulb, currentRoom.getName(), "turn_on");

            }
            case "turn_off" -> {
                lightBulb.turnOff();
                Logger.log("turn off", lightBulb, currentRoom.getName(), "turn_off");

            }
            case "is_on" -> {
                System.out.println(lightBulb.isOn());
                Logger.log("is on", lightBulb, currentRoom.getName(), "is_on");

            }
            case "set_hue" -> {
                lightBulb.setHue(Float.valueOf(input.toString()));
                Logger.log("set_hue", lightBulb, currentRoom.getName(), "set_hue");

            }
            case "set_saturation" -> {
                lightBulb.setSaturation(Float.valueOf(input.toString()));
                Logger.log("set saturation", lightBulb, currentRoom.getName(), "set_saturation");

            }
            case "set_value" -> {
                lightBulb.setValue(Float.valueOf(input.toString()));
                Logger.log("set value", lightBulb, currentRoom.getName(), "set_value");

            }
            case "get_color" -> {
                System.out.println(lightBulb.getRGBColor());
                Logger.log("get color", lightBulb, currentRoom.getName(), "get_color");

            }
        }
    }

    private void executeMotionSensorCommands(MotionSensor motionSensor, String command, Object input) {
        switch(command) {
            case "set_status" -> {
                motionSensor.setStatus(DeviceStatus.valueOf(input.toString()));
                Logger.log("set status", motionSensor, currentRoom.getName(), "set_status");

            }
            case "is_motion_detected" -> {
                System.out.println(motionSensor.isMotionDetected());
                Logger.log("is motion detected", motionSensor, currentRoom.getName(), "is_motion_detected");


            }
        }
    }

    private void executeSmartTvCommands(SmartTv smartTv, String command, Object input) {
        switch(command) {
            case "turn_on" -> {
                smartTv.turnOn();
                Logger.log("turn on", smartTv, currentRoom.getName(), "turn_on");

            }
            case "turn_off" -> {
                smartTv.turnOff();
                Logger.log("turn off", smartTv, currentRoom.getName(), "turn_off");

            }
            case "is_on" -> {
                System.out.println(smartTv.isOn());
                Logger.log("is on", smartTv, currentRoom.getName(), "is_on");

            }

        }
    }

    private void executeDevicesManagerCommands(DevicesManager devicesManager, String command, Object input) {
        switch(command) {
            case "turn_devices_off" -> {
                devicesManager.turnAllOff();
                Logger.log("turn devices off", devicesManager, currentRoom.getName(), "turn_devices_off");

            }
            case "turn_devices_on" -> {
                devicesManager.turnAllOn();
                Logger.log("turn devices on", devicesManager, currentRoom.getName(), "turn_devices_on");

            }
            case "set_status" -> {
                devicesManager.setStatus(DeviceStatus.valueOf(input.toString()));
                Logger.log("set_status", devicesManager, currentRoom.getName(), "set_status");

            }
            case "update" -> {
                devicesManager.notifyObservers();
                Logger.log("update", devicesManager, currentRoom.getName(), "update");

            }
        }
    }

    private void printAllowedCommandsOfDevice(SmartDevice device) {
        List<String> commands = getdeviceCommands(device);
        System.out.println("Allowed commands are:");
        for(String command : commands) {
            System.out.println(command);
        }
    }

    private void listDeviceStatuses(SmartDevice device) {
        List<String> statuses = device.getAllowedStatuses();
        for(String status : statuses) {
            System.out.println(status);
        }
    }

    private void printCommandLine(String phrase) {
        System.out.print(getPrefixCli() + phrase + " > ");
    }
}
