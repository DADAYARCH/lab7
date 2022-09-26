package common;


import java.io.Serializable;

public enum CommandType implements Serializable {
    INSERT,
    UPDATE,
    DELETE,
    CLEAN,
    GET_WORKERS,
    REGISTER,
}