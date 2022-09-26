package client.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileInput implements InputHandler {
    Scanner scanner;

    public FileInput(String fileName) throws IOException {
        scanner = new Scanner(new FileInputStream(fileName));
    }
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }
}
