package io.crocker.jredis.server.command;

import io.crocker.jredis.server.data.DataStore;
import io.crocker.jredis.server.data.exception.MissingKeyException;

import java.util.Iterator;
import java.util.List;

public class CommandProcessor {
    private static final String INVALID_COMMAND = "Command is invalid.";

    private static final String UNIMPLEMENTED_COMMAND = "Command not implemented.";

    private static final String NO_KEY = "Key does not exist.";

    private static final String DATA_SET = "Data set successfully.";

    private static final String DATA_DELETED = "Data successfully deleted.";

    private static final String FLUSHED = " records deleted.";

    private DataStore store;

    private CommandProcessor() {
    }

    public CommandProcessor(DataStore store) {
        this.store = store;
    }

    public String process(String[] input) {
        CommandParser parser = new CommandParser(input);

        if (!parser.isValid()) {
            return CommandProcessor.INVALID_COMMAND;
        }

        switch (parser.getCommand()) {
            case Command.GET:
                return this.processGet(parser.getArguments());
            case Command.SET:
                return this.processSet(parser.getArguments());
            case Command.DELETE:
                return this.processDelete(parser.getArguments());
            case Command.FLUSH:
                return this.processFlush(parser.getArguments());
            case Command.LIST:
                return this.processList(parser.getArguments());
            case Command.INCREMENT:
                return this.processIncrement(parser.getArguments());
            case Command.DECREMENT:
                return this.processDecrement(parser.getArguments());
            case Command.ADD:
                return this.processAddition(parser.getArguments());
        }

        return CommandProcessor.UNIMPLEMENTED_COMMAND;
    }

    private String processAddition(String[] arguments) {
        int sum = 0;

        for (int i = 0; i < arguments.length; i++) {
            try {
                if (this.store.has(arguments[i])) {
                    sum += Integer.parseInt(
                            this.store.get(arguments[i]).toString()
                    );
                } else {
                    sum += Integer.parseInt(arguments[i]);
                }
            } catch (MissingKeyException e) {
                e.printStackTrace();
                return CommandProcessor.INVALID_COMMAND;
            }
        }

        return String.valueOf(sum);
    }

    private String processIncrement(String[] arguments) {
        try {
            String value = this.store.get(arguments[0]).toString();
            int number = Integer.parseInt(value);
            number = number + 1;
            this.store.set(arguments[0], String.valueOf(number));
            return CommandProcessor.DATA_SET;
        } catch (MissingKeyException e) {
            return CommandProcessor.NO_KEY;
        }
    }

    private String processDecrement(String[] arguments) {
        try {
            String value = this.store.get(arguments[0]).toString();
            int number = Integer.parseInt(value);
            number = number - 1;
            this.store.set(arguments[0], String.valueOf(number));
            return CommandProcessor.DATA_SET;
        } catch (MissingKeyException e) {
            return CommandProcessor.NO_KEY;
        }
    }

    private String processList(String[] arguments) {
        List<String> all = this.store.all();
        Iterator iterator = all.iterator();
        StringBuilder output = new StringBuilder();

        boolean endOfLine = false;

        while (iterator.hasNext()) {
            output.append(iterator.next());

            if (endOfLine) {
                output.append("\n");
            } else {
                output.append("\t\t");
            }

            endOfLine = !endOfLine;
        }

        return output.toString();
    }

    private String processFlush(String[] arguments) {
        int count = this.store.flush();
        return count + CommandProcessor.FLUSHED;
    }

    private String processDelete(String[] arguments) {
        this.store.delete(arguments[0]);
        return CommandProcessor.DATA_DELETED;
    }

    private String processSet(String[] arguments) {
        this.store.set(arguments[0], arguments[1]);
        return CommandProcessor.DATA_SET;
    }

    private String processGet(String[] arguments) {
        try {
            return this.store.get(arguments[0]).toString();
        } catch (MissingKeyException e) {
            e.printStackTrace();
            return CommandProcessor.NO_KEY;
        }
    }
}
