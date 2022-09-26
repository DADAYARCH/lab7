package client;

import client.command.*;
import client.io.*;
import client.transfer.Transfer;
import common.transfer.ConnectionError;
import server.database.UserData;

import java.io.IOException;
import java.util.ArrayList;

public class Client {
    private InputHandler input;
    private OutputHandler output;
    private Transfer serverConnection;
    private UserData userData = new UserData("-", "-");
    private boolean active = true;
    private static ArrayList<String> paths = new ArrayList<>();

    public Client() {
        input = new ConsoleInput();
        output = new ConsoleOutput();
        serverConnection = new Transfer();
    }

    public Client(int port) {
        input = new ConsoleInput();
        output = new ConsoleOutput();
        serverConnection = new Transfer(port);
    }

    public Client(Client client, String filename) {
        try {
            input = new FileInput(filename);
            output = new HiddenOutput();
            serverConnection = client.serverConnection;
        } catch (IOException e) {
            active = false;
        }
    }

    public void executeCommand(String string) {
        if (string.equalsIgnoreCase("exit")) {
            stop();
        } else if (string.equalsIgnoreCase("info")) {
            new InfoCommand(output, serverConnection, userData, input).execute();
        } else if (string.equalsIgnoreCase("help")) {
            new HelpCommand(output, serverConnection, userData, input).execute();
        } else if (string.equalsIgnoreCase("show")) {
            new ShowCommand(output, serverConnection, userData, input).execute();
        } else if (string.equalsIgnoreCase("register")) {
            new RegisterCommand(output, serverConnection, userData, input).execute();
        } else if (string.equalsIgnoreCase("login")) {
            new LoginCommand(output, serverConnection, userData, input).execute();
        } else if (string.equalsIgnoreCase("insert")) {
            new InsertCommand(output, serverConnection, userData, input).execute();
        } else if (string.startsWith("filter_contains_name")) {
            FilterContainsNameCommand f = new FilterContainsNameCommand(output,serverConnection,userData, input);
            f.setName(string);
            f.execute();
        } else if (string.equalsIgnoreCase("clear")) {
            new ClearCommand(output, serverConnection, userData, input).execute();
        } else if (string.startsWith("remove_id")) {
            DeleteCommand c = new DeleteCommand(output, serverConnection, userData, input);
            c.setId(string);
            c.execute();
        } else if (string.startsWith("update")) {
            UpdateCommand c = new UpdateCommand(output, serverConnection, userData, input);
            c.setId(string);
            c.execute();
        } else if (string.startsWith("remove_greater")) {
            new RemoveGreaterCommand(output, serverConnection, userData, input).execute();

        }else if (string.startsWith("remove_lower")) {
            new RemoveLowerCommand(output, serverConnection, userData, input).execute();
        }else if (string.startsWith("replace_if_lower")) {
            ReplaceLowerCommand c = new ReplaceLowerCommand(output, serverConnection, userData, input);
            c.setId(string);
            c.execute();
        }
    }

    public void stop() {
        active = false;
    }

    public void run() {
        while (active) {
            output.printMessage("console_prefix");
            String com = input.nextLine();
            try {
                executeCommand(com.trim());
            } catch (ConnectionError e) {
                output.print("crushedServer");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new Client().run();
        } else  {
            new Client(Integer.parseInt(args[0])).run();
        }
    }
}
