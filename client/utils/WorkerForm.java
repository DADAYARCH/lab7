package client.utils;

import client.io.InputHandler;
import client.io.OutputHandler;
import common.models.*;

import java.util.Date;
import java.util.NoSuchElementException;

public class WorkerForm {
    private Worker worker;
    final InputHandler input;
    final OutputHandler output;


    public WorkerForm(InputHandler input, OutputHandler output) {
        this.input = input;
        this.output = output;
        this.worker = new Worker();
        this.worker.setCoordinates(new Coordinates());
    }

    public Worker getWorker() {
        try {
            fillWorker();
            return worker;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    protected void fillWorker(){
        fillWorkerName();
        fillCoordinateX();
        fillCoordinateY();
        fillSalary();
        fillStartDate();
        fillPosition();
        fillStatus();
        fillOrganization();


    }

    protected void fillWorkerName(){
        while (true) {
            try {
                String name = getUserInput("workerForm_nameField");
                WorkerValidator.validateName(name);
                worker.setName(name);
                break;
            } catch (ValidationError e) {
                output.printMessage("workerForm_invalidName");
            }
        }
    }

    protected void fillCoordinateX() {
        while (true) {
            try {
                Float x = Float.parseFloat(getUserInput("coordinatesForm_XField"));
                WorkerValidator.validateX(x);
                worker.getCoordinates().setX(x);
                break;
            } catch (ValidationError | NumberFormatException e) {
                output.printMessage("workerForm_invalidX");
            }
        }
    }

    protected void fillCoordinateY() {
        while (true) {
            try{
                Integer y = Integer.parseInt(getUserInput("coordinatesForm_YField"));
                worker.getCoordinates().setY(y);
                break;
            }catch (NumberFormatException e){
                Integer y1 = 0;
                worker.getCoordinates().setY(y1);
                break;
            }

        }
    }

    protected void fillSalary(){
        while (true){
            try {
                String input = getUserInput("workerForm_salaryField");
                int salary = 1;
                if (!input.isEmpty()){
                    salary = Integer.parseInt(input);
                }

                WorkerValidator.validateSalary(salary);
                worker.setSalary(salary);
                break;

            } catch (ValidationError | NumberFormatException e) {
                output.printMessage("workerForm_invalidSalary");
            }
        }
    }

    protected void fillStartDate(){
        while (true){
            try {
                String[] string = getUserInput("workerForm_startDateField").split("-");
                int year = Integer.parseInt(string[0]);
                int month = Integer.parseInt(string[1]);
                int day = Integer.parseInt(string[2]);
                Date startDate = new Date();
                startDate.setYear(year);
                startDate.setMonth(month);
                startDate.setDate(day);
                WorkerValidator.validateStartDate(startDate);
                worker.setStartDate(startDate);
                break;
            }catch (ValidationError | NumberFormatException | ArrayIndexOutOfBoundsException e){
                output.printMessage("workerForm_invalidStartDate");
            }
        }
    }

    protected void fillPosition(){
        while(true){
            try {
                String input = getUserInput("workerForm_positionField").toUpperCase();
                Position position = null;
                if (!input.isEmpty()){
                    position = Position.valueOf(input);
                }
                worker.setPosition(position);
                break;
            }catch (IllegalArgumentException e){
                output.printMessage("workerForm_invalidPosition");
            }
        }
    }

    protected void fillStatus(){
        while (true){
            try {
                String input = getUserInput("workerForm_statusField").toUpperCase();
                Status status = Status.valueOf(input);
                worker.setStatus(status);
                WorkerValidator.validateStatus(status);
                break;
            } catch (ValidationError | IllegalArgumentException e) {
                output.printMessage("workerForm_invalidStatus");
            }
        }
    }

    protected void fillOrganization(){
        while (true){
            try{

                Organization organization = new Organization();
                while (true){
                    try{
                        String orgType = getUserInput("workerForm_organizationTypeField").toUpperCase();
                        OrganizationType type = OrganizationType.valueOf(orgType);
                        organization.setType(type);
                        organization.validateType();
                        break;
                    }catch (ValidationError | IllegalArgumentException e){
                        output.printMessage("organization_invalidType");
                    }
                }
                while (true) {
                    try {
                        int orgEmCount = Integer.parseInt(getUserInput("workerForm_organizationCountField"));
                        organization.setEmployeesCount(orgEmCount);
                        organization.validateCount();
                        break;
                    } catch (ValidationError e) {
                        output.printMessage("organization_invalidCount");
                    }
                }

                Address address = new Address();
                Location location =new Location();

                String street = getUserInput("workerForm_addressStreetField");
                address.setStreet(street);

                String townName = getUserInput("workerForm_addressTownField");
                location.setName(townName);
                location.validateName();

                address.setTown(location);

                organization.setOfficialAddress(address);
                organization.validateOfficialAddress();
                worker.setOrganization(organization);
                break;
            }catch(ValidationError e){

            }
        }
    }

    private String getUserInput(String messageCode) {
        output.printMessage(messageCode);
        return input.nextLine();
    }
}
