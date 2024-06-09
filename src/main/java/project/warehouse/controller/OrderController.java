package project.warehouse.controller;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.Transaction;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.entity.ToOrderEntity;
import project.warehouse.hibernate.HibernateUtil;
import project.warehouse.sqlStaff.ToOrderConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;


public class OrderController {
    public TextField information;
    public Button paymentMethod;
    public TableColumn id;
    public TableColumn name;
    public TableColumn price;
    public TableView toOrderTable;
    public Text methodApproved;
    public TextField phonenumber;
    public TextField creditCardCVC;
    public TextField creditCardDate;
    public TextField creditCardNumber;
    public Button setInfo;

    public void initialize() throws SQLException {
        createListToOrder();
    }

    public void setOrder() {

        if (phonenumber.getText().trim().isEmpty()||
                information.getText().trim().isEmpty()
        ) {
            methodApproved.setText("Not enough data to create order!");
        } else {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setPaymentMethod(paymentMethod() ? "card" : "money");
            orderEntity.setPaymentStatus(paymentMethod() ? "payed" : "waiting");
            orderEntity.setPriority(paymentMethod() ? 10 : 5);
            orderEntity.setInformation(information.getText());
            orderEntity.setPhonenumber(phonenumber.getText());
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.persist(orderEntity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            if (paymentMethod()) {
                methodApproved.setText("Payed by card! Your priority is 10!");
            } else methodApproved.setText("You have to pay. Your order is getting ready!");
        }
    }

    public boolean paymentMethod() {
        if (!(creditCardNumber.getText().trim().isEmpty() &&
                creditCardDate.getText().trim().isEmpty() &&
                creditCardCVC.getText().trim().isEmpty())) {
            System.out.println("Payed by card");
            System.out.println(creditCardDate.toString());
            System.out.println(creditCardDate.toString().isEmpty());
            return true;
        } else {
            System.out.println("Did not payed");
            return false;
        }
    }


    @FXML
    void createListToOrder() throws SQLException {
        toOrderTable.getColumns().clear();
        toOrderTable.getItems().clear();
        ToOrderConnection toOrderConnection = ToOrderConnection.getDataAccessor();

        TableColumn<ToOrderEntity, String> fID = id;
        TableColumn<ToOrderEntity, String> name1 = name;
        TableColumn<ToOrderEntity, String> price1 = price;


        fID.setCellValueFactory(new PropertyValueFactory<>("id"));
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        price1.setCellValueFactory(new PropertyValueFactory<>("price"));

        toOrderTable.getColumns().addAll(fID, name1, price1);
        toOrderTable.getItems().addAll(toOrderConnection.getToOrderList());
    }
}
