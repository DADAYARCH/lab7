package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import common.CommandType;
import common.models.Worker;
import common.transfer.Request;
import common.transfer.Response;
import server.database.UserData;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterContainsNameCommand extends CommandAbstract{

    String name;

    public FilterContainsNameCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler){
        super(outputHandler,transfer,userData,inputHandler);
    }

    public void setName(String command) {
        String[] c = command.trim().split("\\s+");
        if (c.length == 2){
            name =c[1];
        }
    }

    @Override
    public void execute() {
        if (name != null){
            Request request = new Request(userData, CommandType.GET_WORKERS);
            serverConnection.send(request);
            Response response = serverConnection.receive();
            if (response.getObject() == null){
                output.print(response.getMessage());
            }else {
                Set<Worker> workers = (Set<Worker>) response.getObject();
                Stream<Worker> stream = workers.stream().filter(
                        worker -> worker.getName().contains(name));
                prettyPrint.print(stream.collect(Collectors.toSet()));
            }
        }else {
            output.print("Не верно передан аргумент.\n");
        }
    }






















}
