package uz.ciasev.ubdd_service.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.config.base.CiasevDBConstraint;

import java.sql.*;
import java.util.function.Function;

@Component
public class DBHelper {

    private final String connectionString;
    private final String dbLogin;
    private final String dbPassword;

    public static boolean isConstraintViolation(DataIntegrityViolationException e, CiasevDBConstraint constraintName) {
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
            if (constraintViolationException.getConstraintName().equals(constraintName.getDbName())) {
                return true;
            }
        }

        return false;
    }

    @Autowired
    public DBHelper(@Value("${spring.datasource.url}") String connectionString,
                    @Value("${spring.datasource.username}") String dbLogin,
                    @Value("${spring.datasource.password}") String dbPassword) {
        this.connectionString = connectionString;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public <T> T doWithConnection(Function<Connection, T> consumer) throws Exception {
        Class.forName("org.postgresql.Driver");
        T res = null;
        Exception consumerEx = null;

        try (Connection conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);) {
            conn.setAutoCommit(false);

            try {
                res = consumer.apply(conn);
            } catch (Exception e) {
                consumerEx = e;
            }

            conn.commit();
        }

        if (consumerEx != null) throw consumerEx;
        return res;
    }

    public void execute(String sql) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(connectionString, dbLogin, dbPassword);) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                //statement.executeUpdate(sql);
                statement.executeUpdate();
            }
        }
    }
}
