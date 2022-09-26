package server.database;

public class PermissionDenied extends RuntimeException {
    public PermissionDenied() {
        super();
    }

    public PermissionDenied(String message) {
        super(message);
    }
}
