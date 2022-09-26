package client.io;

public class HiddenOutput implements OutputHandler {
    @Override
    public void printMessage(String messageCode) {}

    @Override
    public void print(Object object) {}

    @Override
    public void alwaysPrint(Object object) {
        System.out.print(object);
    }

    @Override
    public void println(Object object) {}

    @Override
    public void alwaysPrintln(Object object) {
        System.out.println(object);
    }
}
