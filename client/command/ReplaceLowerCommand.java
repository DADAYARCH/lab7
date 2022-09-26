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


public class ReplaceLowerCommand extends CommandAbstract {
    Request request;
    Long id;

    public ReplaceLowerCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        super(outputHandler, transfer, userData, inputHandler);
    }

    public void setId(String command) {
        String[] c = command.trim().split("\\s+");
        if (c.length == 2) {
            try {
                id = Long.parseLong(c[1]);
            } catch (NumberFormatException ignore) {}
        }
    }


    @Override
    public void execute() {
        if (id != null){
            Worker worker = new WorkerForm(input,output).getWorker();
            if (worker != null){
                Request request1 = new Request(userData, CommandType.GET_WORKERS);
                serverConnection.send(request1);
                Response response = serverConnection.receive();
                if (response.getObject() == null) {
                    output.print(response.getMessage());
                } else {
                    Set<Worker> workers = (Set<Worker>) response.getObject();
                    Set<Worker> workerToReplace = workers.stream().filter(worker1 -> worker1.getId() == id).filter(
                            worker1 -> worker1 != null && worker1.isLowerThan(worker)).collect(Collectors.toSet() );
                    if (!workerToReplace.isEmpty()){
                        for (Worker w : workerToReplace){
                            Request request2 = new Request(userData,CommandType.UPDATE, w);
                            serverConnection.send(request2);
                            Response response1 = serverConnection.receive();
                            output.print(response1.getMessage());
                        }

                    }else {
                        output.print("Новый элемент не меньше старого, замена не произошла.\n");
                    }
                }
            }
        }else {
            output.print("Неверно передан аргумент:(\n");
        }

    }
}
