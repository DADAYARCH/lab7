package client.io;

public interface OutputHandler {
    void printMessage(String messageCode);
    void print(Object object);
    void alwaysPrint(Object object);
    void println(Object object);
    void alwaysPrintln(Object object);
}
