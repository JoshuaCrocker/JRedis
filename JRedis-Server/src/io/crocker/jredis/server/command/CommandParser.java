package io.crocker.jredis.server.command;

public class CommandParser {
    private String command;
    private String[] arguments;

    public CommandParser(String[] input) {
        this.command = input[0];

        this.arguments = new String[input.length - 1];

        for (int i = 1; i < input.length; i++) {
            this.arguments[i - 1] = input[i];
        }
    }

    public boolean isValid() {
        if (command.equals(Command.GET) && this.arguments.length == 1) {
            return true;
        }

        if (command.equals(Command.SET) && this.arguments.length == 2) {
            return true;
        }

        if (command.equals(Command.DELETE) && this.arguments.length == 1) {
            return true;
        }

        if (command.equals(Command.LIST) && this.arguments.length == 0) {
            return true;
        }

        if (command.equals(Command.FLUSH) && this.arguments.length == 0) {
            return true;
        }

        if (command.equals(Command.INCREMENT) && this.arguments.length == 1) {
            return true;
        }

        if (command.equals(Command.DECREMENT) && this.arguments.length == 1) {
            return true;
        }

        return false;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }

}
