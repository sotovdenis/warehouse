package project.warehouse.controller.admin;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProductEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AdminOrderController {

    public TableColumn id;
    public TableColumn paymentMethod;
    public TableColumn status;
    public TableColumn priority;
    public TableColumn info;
    public TableColumn phone;
    public TableView orders;
    public TextField deletedID;
    public Button deleteBtn;
    public Text message;
    public TableColumn paymentstatus;

    public void initialize(){
        createOrderList();
    }

    public List<OrderEntity> getListOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = builder.createQuery(OrderEntity.class);
        criteriaQuery.from(OrderEntity.class);

        return session.createQuery(criteriaQuery).getResultList();
    }

    void createOrderList() {
        orders.getColumns().clear();
        orders.getItems().clear();

        TableColumn<OrderEntity, String> oID = id;
        TableColumn<OrderEntity, String> payment = paymentMethod;
        TableColumn<OrderEntity, String> paymentStatus = paymentstatus;
        TableColumn<OrderEntity, String> orderPriority = priority;
        TableColumn<OrderEntity, String> information = info;
        TableColumn<OrderEntity, String> phoneNumber = phone;
        TableColumn<OrderEntity, String> statusColumn = status;


        oID.setCellValueFactory(new PropertyValueFactory<>("id"));
        payment.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        orderPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        orders.getColumns().addAll(oID, payment, paymentStatus, orderPriority, information, phoneNumber, statusColumn);
        orders.getItems().addAll(getListOrders());
    }


    public void deleteBtn() {
        if (deletedID.getText().trim().isEmpty() || deletedID.getText().trim().toLowerCase().contains("drop")) {
            message.setText("Illegal string!");
        } else {
            int id = Integer.parseInt(deletedID.getText());
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            OrderEntity order = session.get(OrderEntity.class, Integer.parseInt(deletedID.getText()));

            order.setStatus(1);
            session.merge(order);
            session.getTransaction().commit();
            initialize();
        }
    }
}



