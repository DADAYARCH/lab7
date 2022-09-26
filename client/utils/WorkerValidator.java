package client.utils;

import common.models.Organization;
import common.models.Status;

public class WorkerValidator {
    public static void validateName(String name) throws ValidationError {
        if (name == null || name.isEmpty()) {
            throw new ValidationError("Name must be not null or empty.");
        }
    }

    public static void validateX(Float x) throws ValidationError {
        if (x == null) {
            throw new ValidationError("X must be not null");
        }
    }

    public static void validateSalary(int salary) throws ValidationError{
        if (salary <= 0){
            throw new ValidationError("The salary should be more than zero");
        }
    }

    public static void validateStartDate(java.util.Date startDate) throws ValidationError{
        if (startDate == null){
            throw new ValidationError("Start_Date can't be null");
        }
    }

    public static void validateStatus(Status status) throws ValidationError{
        if (status == null){
            throw new ValidationError("Status can't be null");
        }
    }

    public static void validateOrganization(Organization organization) throws ValidationError{
        if (organization == null ){
            throw new ValidationError("Organization can't be null");
        }
    }





}
