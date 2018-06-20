package io.crocker.jredis.server.command;

/**
 * Command class.
 * <p>
 * Lists all commands available to the system.
 */
public class Command {

    /**
     * Terminates the connection.
     */
    public static final String EXIT = "EXIT";

    public static final String GET = "GET";

    public static final String SET = "SET";

    public static final String DELETE = "DELETE";

    public static final String FLUSH = "FLUSH";

    public static final String LIST = "LIST";

    public static final String INCREMENT = "INCR";

    public static final String DECREMENT = "DECR";

    public static final String ADD = "ADD";

    // TODO implement SUBTRACT
    // TODO implement MULTIPLY
    // TODO implement DIVIDE
}
