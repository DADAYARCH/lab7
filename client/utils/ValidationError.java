package client.utils;

public class ValidationError extends RuntimeException {
    public ValidationError() {
        super();
    }
    public ValidationError(String message) {
        super(message);
    }
}
