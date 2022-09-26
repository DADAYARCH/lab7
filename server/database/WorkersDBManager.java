package server.database;

import common.models.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WorkersDBManager {
    final DatabaseHandler DBHandler;
    private UserDBManager userDBManager;

    public WorkersDBManager(DatabaseHandler DBHandler, UserDBManager userDBManager) {
        this.DBHandler = DBHandler;
        this.userDBManager = userDBManager;
        createTable();
    }

    private void createTable() {
        //language=SQL
        String CREATION_SQL = "CREATE TABLE IF NOT EXISTS WORKERS (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name TEXT NOT NULL CHECK (name <> '')," +
                "coordinateX FLOAT NOT NULL," +
                "coordinateY INTEGER NOT NULL," +
                "creationDate BIGINT NOT NULL," +
                "salary INTEGER CHECK(salary > 0)," +
                "startDate BIGINT NOT NULL,"+
                "position VARCHAR(50) CHECK ( position = 'MANAGER' or position = 'LABORER' or position = 'HEAD_OF_DIVISION' or position = 'HEAD_OF_DEPARTMENT' )," +
                "status VARCHAR(50) CHECK ( status = 'FIRED' or status = 'HIRED' or status = 'RECOMMENDED_FOR_PROMOTION' or status = 'REGULAR' or status = 'PROBATION')," +
                "employeesCount INTEGER CHECK(employeesCount >0),"+
                "organizationType VARCHAR(50) CHECK(organizationType = 'COMMERCIAL' or organizationType = 'PUBLIC' or organizationType = 'GOVERNMENT' or organizationType = 'TRUST' or organizationType = 'PRIVATE_LIMITED_COMPANY')," +
                "street TEXT,"+
                "town TEXT CHECK (name <> ''), "+
                "author VARCHAR(50) NOT NULL REFERENCES USERS(username)" +
                ");";
        try {
            Statement statement = DBHandler.getStatement();
            statement.execute(CREATION_SQL);
        } catch (DatabaseException | SQLException ignore) {}
    }

    public boolean insertWorker(Worker worker, UserData userData) {
        //language=SQL
        String INSERT_SQL = "INSERT INTO WORKERS " +
                "(name, coordinateX, coordinateY, creationDate, salary, startDate, position, status, employeesCount,organizationType, " +
                "street, town, author) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            DBHandler.setSavepoint();
            PreparedStatementWithNull preparedStatement = DBHandler.getPreparedStatementWithNull(INSERT_SQL);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setFloat(2, worker.getCoordinates().getX());
            preparedStatement.setInt(3, worker.getCoordinates().getY());
            preparedStatement.setLong(4, worker.getCreationDate() == null ? new Date().getTime() : worker.getCreationDate().getTime());
            preparedStatement.setInt(5, worker.getSalary());
            preparedStatement.setLong(6, worker.getStartDate()==null ? new Date().getTime() : worker.getStartDate().getTime());
            preparedStatement.setString(7, worker.getPosition() != null ? worker.getPosition().toString() : null);
            preparedStatement.setString(8, worker.getStatus() == null ? worker.getStatus().toString() : "RECOMMENDED_FOR_PROMOTION");
            preparedStatement.setInt(9, worker.getOrganization().getEmployeesCount());
            preparedStatement.setString(10,worker.getOrganization().getType() == null ? worker.getOrganization().getType().toString() : "PRIVATE_LIMITED_COMPANY");
            preparedStatement.setString(11, worker.getOrganization().getOfficialAddress().getStreet());
            preparedStatement.setString(12, worker.getOrganization().getOfficialAddress().getTown().getName());
            preparedStatement.setString(13, userData.username);
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (SQLException | DatabaseException e) {
            DBHandler.rollback();
            return false;
        }
    }

    public boolean updateWorker(Worker worker, UserData userData) {
        //language=SQL
        String UPDATE_SQL = "UPDATE WORKERS SET name = ?, " +
                "coordinateX = ?, coordinateY = ?, " +
                "creationDate = ?, salary = ?, " +
                "startDate = ?, position = ?, status = ?," +
                " employeesCount = ?, organizationType = ?," +
                "street = ?, town = ? WHERE id = ? and author = ?";
        try {
            DBHandler.setSavepoint();
            PreparedStatementWithNull preparedStatement = DBHandler.getPreparedStatementWithNull(UPDATE_SQL);
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setFloat(2, worker.getCoordinates().getX());
            preparedStatement.setInt(3, worker.getCoordinates().getY());
            preparedStatement.setLong(4, worker.getCreationDate() == null ? new Date().getTime() : worker.getCreationDate().getTime());
            preparedStatement.setInt(5, worker.getSalary());
            preparedStatement.setLong(6, worker.getStartDate()==null ? new Date().getTime() : worker.getStartDate().getTime());
            preparedStatement.setString(7, worker.getPosition() != null ? worker.getPosition().toString() : null);
            preparedStatement.setString(8, worker.getStatus() == null ? worker.getStatus().toString() : "RECOMMENDED_FOR_PROMOTION");
            preparedStatement.setInt(9, worker.getOrganization().getEmployeesCount());
            preparedStatement.setString(10,worker.getOrganization().getType() == null ? worker.getOrganization().getType().toString() : "PRIVATE_LIMITED_COMPANY");
            preparedStatement.setString(11, worker.getOrganization().getOfficialAddress().getStreet());
            preparedStatement.setString(12, worker.getOrganization().getOfficialAddress().getTown().getName());
            preparedStatement.setLong(13, worker.getId());
            preparedStatement.setString(14, userData.username);
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (SQLException | DatabaseException e) {
            DBHandler.rollback();
            return false;
        }
    }

    public boolean deleteWorker(Worker worker, UserData userData) {
        return deleteWorker(worker.getId(), userData);
    }

    public boolean deleteWorker(Long workerId, UserData userData) {
        //language=SQL
        String DELETE_SQL =  "DELETE FROM WORKERS WHERE id = ? and author = ?";
        try {
            DBHandler.setSavepoint();
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(DELETE_SQL);
            preparedStatement.setLong(1, workerId);
            preparedStatement.setString(2, userData.username);
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (SQLException | DatabaseException e) {
            DBHandler.rollback();
            return false;
        }
    }

    public boolean deleteAllWorkers(UserData userData) {
        //language=SQL
        String DELETE_SQL =  "DELETE FROM WORKERS WHERE author = ?";
        try {
            DBHandler.setSavepoint();
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(DELETE_SQL);
            preparedStatement.setString(1, userData.username);
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (SQLException | DatabaseException e) {
            DBHandler.rollback();
            return false;
        }
    }

    public Set<Worker> getWorkers() {
        //language=SQL
        String DELETE_SQL =  "SELECT * FROM WORKERS";
        try {
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(DELETE_SQL);
            preparedStatement.execute();
            return getWorkerSet(preparedStatement.getResultSet());
        } catch (SQLException | DatabaseException e) {
            return new HashSet<>();
        }
    }

    public Set<Worker> getWorkerSet(ResultSet resultSet) throws SQLException {
        Set<Worker> workerSet = new HashSet<>();
        while (resultSet.next()) {
            Worker worker = new Worker(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getFloat("coordinateX"),
                    resultSet.getInt("coordinateY"),
                    resultSet.getLong("creationDate"),
                    resultSet.getInt("salary"),
                    resultSet.getLong("startDate"),
                    resultSet.getString("position"),
                    resultSet.getString("status"),
                    resultSet.getInt("employeesCount"),
                    resultSet.getString("organizationType"),
                    resultSet.getString("street"),
                    resultSet.getString("town"),
                    resultSet.getString("author")
            );
            workerSet.add(worker);
        }
        return workerSet;
    }
}
