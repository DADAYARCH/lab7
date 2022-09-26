package common.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Worker implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer salary; //Значение поля должно быть больше 0
    private Date startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле не может быть null
    private Organization organization; //Поле не может быть null

    private String author;

    /**
     * Конструктор дает пустого рабочего
     */
    public Worker(){}

    public Worker(Long id, String name, Coordinates coordinates,
                  int salary, java.util.Date startDate, Position position,
                  Status status, Organization organization, String author){
        this(name,coordinates,salary,startDate,position,status,organization,author);
        this.id = id;
    }

    public Worker(String name, Coordinates coordinates,
                  int salary, java.util.Date startDate, Position position,
                  Status status, Organization organization, String author){
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
        this.author = author;
    }

    public Worker(Long id, String name, Coordinates coordinates,
                  int salary, java.util.Date startDate,
                  Status status, Organization organization, String author){
        this(name,coordinates,salary,startDate,status,organization,author);
        this.id = id;
    }

    public Worker(String name, Coordinates coordinates,
                  int salary, java.util.Date startDate,
                  Status status, Organization organization, String author){
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.status = status;
        this.organization = organization;
        this.author = author;
    }

    public Worker(Long id, String name, Float coordinateX, Integer coordinateY,
                  Long creationDate, Integer salary, Long startDate,
                  String position, String status, Integer employeesCount,
                  String organizationType, String street, String town, String author){
        this(name,coordinateX,coordinateY,creationDate,salary,startDate,position,status,employeesCount,organizationType,street,town,author);
        this.id = id;
    }
    public Worker(String name, Float coordinateX, Integer coordinateY,
                  Long creationDate, Integer salary, Long startDate,
                  String position, String status, Integer employeesCount,
                  String organizationType, String street, String town, String author){
        this.name = name;
        this.coordinates = new Coordinates(coordinateX,coordinateY);
        this.creationDate = new Date(creationDate);
        this.salary = salary;
        this.startDate = new Date(startDate);
        this.position = position == null ? null : Position.valueOf(position);
        this.status = Status.valueOf(status);
        this.organization = new Organization(employeesCount,OrganizationType.valueOf(organizationType), new Address(street,new Location(town)));
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isLowerThan(Worker other){
        return ((this.salary != 0 && other.salary != 0 && this.salary < other.salary));
    }
}
