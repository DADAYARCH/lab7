package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import client.utils.WorkerForm;
import common.CommandType;
import common.models.Worker;
import common.transfer.Request;
import common.transfer.Response;
import server.database.UserData;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveGreaterCommand extends CommandAbstract {
    Request request;

    public RemoveGreaterCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        super(outputHandler, transfer, userData, inputHandler);
    }


    @Override
    public void execute() {
        Worker worker = new WorkerForm(input,output).getWorker();
        if (worker != null) {
            Request request1 = new Request(userData, CommandType.GET_WORKERS);
            serverConnection.send(request1);
            Response response = serverConnection.receive();
            if (response.getObject() == null) {
                output.print(response.getMessage());
            } else {
                Set<Worker> workers = (Set<Worker>) response.getObject();
                Set<Worker> workerToRemove = workers.stream().filter(
                        worker1 -> worker1 != null && !(worker1.isLowerThan(worker))).collect(Collectors.toSet());
                for (Worker w : workerToRemove) {
                    Request r = new Request(userData, CommandType.DELETE, w);
                    serverConnection.send(r);
                    Response response1 = serverConnection.receive();
                    output.print(response1.getMessage());
                }
                output.print("Элементы удалены.\n");
            }
        }else{
            output.print("workerForm isInvalid\n");
        }
    }
}

