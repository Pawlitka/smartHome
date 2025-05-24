import smartDevices.*;

import java.util.HashMap;
import java.util.Scanner;

public class CommandLineInterface {
    private final Scanner scanner;
    private boolean isProgramRunning;
    private final HashMap<String, House> houses = new HashMap<>();
    private House currentHouse = null;
    private Room currentRoom = null;
    private SmartDevice currentDevice = null;

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
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  help           - Show available commands");
        System.out.println("  create house         - Create house");
        System.out.println("  create room         - Create room");
        System.out.println("  create smart device         - To create device");
        System.out.println("  exit           - Exit the CommandLineInterface");
    }

    private void createHouse() {
        printCommandLine("Enter name for house");
        String name;
        do {
            name = scanner.nextLine();
            if(houses.containsKey(name)) {
                System.out.println("House with \"" + name + "\" already exists. Please use other name for the house.");
            }
        } while(houses.containsKey(name));


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
        if(houses.isEmpty()) {
            System.out.println("There is no any house yet. You cannot remove house.");
        } else {
            printCommandLine("Enter name for house that you want to delete: ");
            String name;
            do {
                name = scanner.nextLine();
                if(!houses.containsKey(name)) {
                    System.out.println("House with \"" + name + "\" does not exists. Please use valid name for the house.");
                }
            } while(houses.containsKey(name));
            currentHouse = null;
            houses.remove(name);
            System.out.println("House \"" + name + "\n has been deleted successfully!");
        }
    }

    private void listHouses() {
        if(houses.isEmpty()) {
            System.out.println("There are no created houses yet!\nUse \"create_house\" command to create one.");
        } else {
            System.out.println("HOUSE NAME | NUMBER OF ROOMS | ADDRESS");
            for(House house : houses.values()) {
                System.out.println(house.getName() + " | " + house.getNumberOfRooms() + " | " + house.getAddress());
            }
        }
    }

    private void enter_house() {
        if(houses.isEmpty()) {
            System.out.println("There are no created houses yet!\nUse \"create_house\" command to create one.");
        } else {
            listHouses();
            String name = null;
            while(name == null || !houses.containsKey(name)) {
                printCommandLine("Enter name for house");
                name = scanner.nextLine();
                if(!houses.containsKey(name)) {
                    System.out.println("The house with the name does not exist.");
                }
            }
            currentHouse = houses.get(name);
        }
    }

    private void exit_house() {
        if(currentRoom != null) {
            exit_room();
        }
        if(currentHouse == null) {
            System.out.println("You are not in any house now.");
        } else {
            System.out.println("You left \"" + currentHouse.getName() + "\" house.");
        }
        currentHouse = null;
    }

    private void createRoom() {
        if(currentHouse == null) {
            System.out.println("Enter the house where you want to create a room.");
        } else {
            String name;
            do {
                printCommandLine("Enter name for room");
                name = scanner.nextLine();
                if(currentHouse.getRooms().containsKey(name)) {
                    System.out.println("The room\"" + name +"\" already exists. Please choose other name.");
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
        if(currentHouse == null) {
            System.out.println("Enter the house where you want to delete a room.");
        } else {
            if(currentHouse.getRooms().isEmpty()) {
                System.out.println("There is no any rooms yet in this house.");
            } else {
                printCommandLine("Enter name for room that you want to delete");
                String name;
                do {
                    name = scanner.nextLine();
                    if(!currentHouse.getRooms().containsKey(name)) {
                        System.out.println("Room with \"" + name + "\" does not exists. Please use valid name for the room.");
                    }
                } while(!currentHouse.getRooms().containsKey(name));

                currentHouse.getRooms().remove(name);
                currentRoom = null;
                System.out.println("Room \"" + name + "\n has been deleted successfully!");
            }
        }
    }

    private void enterRoom() {
        if(currentHouse == null) {
            System.out.println("Enter the house where you want to enter a room.");
        } else {
            listRooms();
            String name = null;
            while(name == null || !currentHouse.getRooms().containsKey(name)) {
                printCommandLine("Enter name for room");
                name = scanner.nextLine();
                if(!currentHouse.getRooms().containsKey(name)) {
                    System.out.println("The room with the name does not exist.");
                }
            }
            currentRoom = currentHouse.getRooms().get(name);
        }
    }

    private void listRooms() {
        if(currentHouse == null) {
            System.out.println("Enter the house where you want to list rooms.");
        } else {
            if(currentHouse.getRooms().isEmpty()) {
                System.out.println("There are no created rooms yet!\nUse \"create_room\" command to create one.");
            } else {
                System.out.println("ROOM NAME | ROOM TYPE | DEVICES");
                for(Room room : currentHouse.getRooms().values()) {
                    System.out.println(room.getName() + " | " + room.getRoomType() + " | " + room.getDevicesAsString());
                }
            }
        }
    }

    private void listRoomTypes() {
        System.out.println("Room types");
        for(RoomType type : RoomType.values()) {
            System.out.println(type.ordinal() + ". " + type.name());
        }
    }


    private void exit_room() {
        if(currentRoom == null) {
            System.out.println("You are not in any room now.");
        } else {
            System.out.println("You left \"" + currentRoom.getName() + "\" room.");
        }
        currentRoom = null;
    }

    private String getPrefixCli() {
        String housePrefix = "";
        String roomPrefix = "";
        if(currentHouse != null) {
            housePrefix += "[" + currentHouse.getName() + "] ";
        }
        if(currentRoom != null) {
            roomPrefix += "(" + currentRoom.getName() + ") ";
        }

        return housePrefix + roomPrefix;
    }

    private void currentDeviceCommands() {
        if(currentDevice instanceof TemperatureSensor) {
            System.out.println("""
                    Commands that are available for temperature sensor:\s
                    simulate
                    set_temperature
                    set_status
                    get_temperature
                    """);
        } else if(currentDevice instanceof Outlet) {
            System.out.println("""
                    Commands that are available for outlet:\s
                    simulate
                    turn_on
                    turn_off
                    is_on
                    is_in_use
                    """);
        }  else if(currentDevice instanceof LightBulb) {
            System.out.println("""
                    Commands that are available for light bulb:\s
                    simulate
                    turn_on
                    turn_off
                    is_on
                    set_hue
                    set_saturation
                    set_value
                    get_color
                    """);
        }
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
                    DevicesTypes type = DevicesTypes.valueOf(deviceTypeName);
                } catch (IllegalArgumentException _) {
                    System.out.println("The \"" + deviceTypeName + "\" does not exist!");
                    deviceTypeName = "";
                }
            } while (deviceTypeName.isEmpty());

            switch (deviceTypeName) {
                case "lightbulb" -> {
                    LightBulb lightbulb = new LightBulb(name);
                    currentRoom.addDevice(lightbulb);
                    System.out.println("Lightbulb has been successfully created.");
                }
                case "outlet" -> {
                    Outlet outlet = new Outlet(name);
                    currentRoom.addDevice(outlet);
                    System.out.println("Outlet has been successfully created.");
                }
                case "temperature_sensor" -> {
                    TemperatureSensor temperatureSensor = new TemperatureSensor(name);
                    currentRoom.addDevice(temperatureSensor);
                    System.out.println("Temperature sensor has been successfully created.");
                }
            }
        }
    }

    private void removeDevice() {
        if(currentHouse == null && currentRoom == null) {
            System.out.println("Enter the house and room where you want to delete a room.");
        } else {
            if(currentRoom.getDevices().isEmpty()) {
                System.out.println("There is no any rooms and devices yet in this house.");
            } else {
                printCommandLine("Enter name for device that you want to delete");
                String name;
                do {
                    name = scanner.nextLine();
                    if(!currentRoom.getDevices().containsKey(name)) {
                        System.out.println("Device with \"" + name + "\" does not exists. Please use valid name for the device.");
                    }
                } while(!currentRoom.getDevices().containsKey(name));

                currentRoom.getDevices().remove(name);
                currentDevice = null;
                System.out.println("Device \"" + name + "\n has been deleted successfully!");
            }
        }
    }

    private void listDevicesTypes() {
        System.out.println("Devices types");
        for(DevicesTypes type : DevicesTypes.values()) {
            System.out.println(type.ordinal() + ". " + type.name());
        }
    }

    private void listDevices() {
        if(currentHouse == null || currentRoom == null) {
            System.out.println("Enter the house and room where you want to list devices.");
        } else {
            if(currentRoom.getDevices().isEmpty()) {
                System.out.println("There are no created devices yet!\nUse \"create_device\" command to create one.");
            } else {
                System.out.println("DEVICE NAME | DEVICE STATUS");
                for(SmartDevice device : currentRoom.getDevices().values()) {
                    System.out.println(device.getName() + " | " + device.getStatus());
                }
            }
        }
    }



private void printCommandLine(String phrase) {
System.out.print(getPrefixCli() + phrase + " > ");
}
}
