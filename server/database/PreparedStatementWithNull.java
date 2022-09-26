package server.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PreparedStatementWithNull {
    final PreparedStatement preparedStatement;

    public PreparedStatementWithNull(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public boolean isNull(Object object) {
        return object == null;
    }

    public void setLong(int parameterIndex, Long x) throws SQLException {
        if (isNull(x)) {
            preparedStatement.setNull(parameterIndex, Types.BIGINT);
        } else {
            preparedStatement.setLong(parameterIndex, x);
        }
    }

    public void setFloat(int parameterIndex, Float x) throws SQLException {
        if (isNull(x)) {
            preparedStatement.setNull(parameterIndex, Types.FLOAT);
        } else {
            preparedStatement.setFloat(parameterIndex, x);
        }
    }

    public void setInt(int parameterIndex, Integer x) throws SQLException {
        if (isNull(x)) {
            preparedStatement.setNull(parameterIndex, Types.INTEGER);
        } else {
            preparedStatement.setInt(parameterIndex, x);
        }
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        preparedStatement.setString(parameterIndex, x);
    }

    public void execute() throws SQLException {
        preparedStatement.execute();
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
