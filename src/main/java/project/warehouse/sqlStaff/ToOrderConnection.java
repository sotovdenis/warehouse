package project.warehouse.sqlStaff;

import lombok.Getter;
import project.warehouse.entity.ToOrderEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToOrderConnection {


    private Connection connection;
    @Getter
    private static ToOrderConnection dataAccessor = new ToOrderConnection("jdbc:postgresql://localhost:5432/database", "postgres", "Ugegam16");

    private ToOrderConnection(String dbUrl, String userID, String password) {
        if (dataAccessor == null) {
            try {
                connection = DriverManager.getConnection(dbUrl, userID, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public  List<ToOrderEntity> getToOrderList() throws SQLException {
        List<ToOrderEntity> order = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM to_order;");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");

                ToOrderEntity orderEntity = new ToOrderEntity(id, name, price);
                order.add(orderEntity);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return order;
    }


    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }
}

