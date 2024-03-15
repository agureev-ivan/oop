package javaapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Operation {
    private String operationName;

    public Operation(String operationName) {
        this.operationName = operationName;
    }

    public void execute() {
        System.out.println("Executing operation: " + operationName);
    }

    public void undo() {
        System.out.println("Undoing operation: " + operationName);
    }
}

interface Command {
    void execute();
    void undo();
}

class MacroCommand implements Command {
    private List<Command> commands;

    public MacroCommand() {
        commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        System.out.println("Executing macro command:");
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        System.out.println("Undoing macro command:");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}

class CommandManager {
    private static CommandManager instance;
    private List<Command> commandHistory;

    private CommandManager() {
        commandHistory = new ArrayList<>();
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.add(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.remove(commandHistory.size() - 1);
            lastCommand.undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }
}

public class NewClass1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = CommandManager.getInstance();

        while (true) {
            System.out.println("Choose operation:");
            System.out.println("1. Execute Operation");
            System.out.println("2. Undo Last Operation");
            System.out.println("3. Execute Macro Command");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Enter operation name:");
                    String operationName = scanner.nextLine();
                    Operation operation = new Operation(operationName);
                    commandManager.executeCommand(new Command() {
                        @Override
                        public void execute() {
                            operation.execute();
                        }

                        @Override
                        public void undo() {
                            operation.undo();
                        }
                    });
                    break;
                case 2:
                    commandManager.undoLastCommand();
                    break;
                case 3:
                    MacroCommand macroCommand = new MacroCommand();
                    macroCommand.addCommand(new Command() {
                        @Override
                        public void execute() {
                            System.out.println("Executing operation 1");
                        }

                        @Override
                        public void undo() {
                            System.out.println("Undoing operation 1");
                        }
                    });
                    macroCommand.addCommand(new Command() {
                        @Override
                        public void execute() {
                            System.out.println("Executing operation 2");
                        }

                        @Override
                        public void undo() {
                            System.out.println("Undoing operation 2");
                        }
                    });
                    commandManager.executeCommand(macroCommand);
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
