package common.models;


import client.utils.ValidationError;
import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Organization implements Comparable<Organization>, Serializable {
    private int employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null

    public Organization(){}
    public Organization(int employeesCount, OrganizationType type, Address officialAddress){
        this.employeesCount = employeesCount;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }
    public void setType(OrganizationType type) {
        this.type = type;
    }
    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }
    public OrganizationType getType() {
        return type;
    }
    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void validateCount() throws ValidationError{
        validateCount(this.employeesCount);
    }
    public void validateCount(int employeesCount) throws ValidationError {
        if (employeesCount <= 0){
            throw new ValidationError("Employees Count must be greater than \"0\"");
        }
    }
    public void validateType() throws ValidationError{
        validateType(this.type);
    }
    public void validateType(OrganizationType type) throws ValidationError {
        if (type == null){
            throw new ValidationError("Type can't be null");
        }
    }
    public void validateOfficialAddress() throws ValidationError{
        validateOfficialAddress(this.officialAddress);
    }
    public void validateOfficialAddress(Address officialAddress) throws ValidationError {
        if (officialAddress == null){
            throw new ValidationError("The street and the city couldn't be equal to NULL at the same time");
        }
    }

    public boolean isLowerThan(Organization other){
        return (this.employeesCount > 0 && other.employeesCount > 0 &&
                (this.employeesCount < other.employeesCount || this.officialAddress.isLowerThan(other.officialAddress))
        );
    }

    public boolean isValid(){
        try {
            validateCount();
            validateType();
            validateOfficialAddress();
            return true;
        }catch (ValidationError e){
            return false;
        }
    }

    @Override
    public int compareTo(@NotNull Organization o) {
        return this.getEmployeesCount() - o.getEmployeesCount();
    }
}
