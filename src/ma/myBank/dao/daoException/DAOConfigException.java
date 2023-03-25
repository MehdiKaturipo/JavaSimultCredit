package ma.myBank.dao.daoException;

import lombok.*;

public class DAOConfigException extends RuntimeException {

    public DAOConfigException(String message) {
        super(message);
    }

    public DAOConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
