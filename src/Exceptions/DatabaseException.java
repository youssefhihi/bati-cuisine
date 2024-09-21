package Exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
