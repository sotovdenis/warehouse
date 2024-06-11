package project.warehouse.controller;

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

    public void initialize() throws SQLException {
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

        TableColumn<ProviderEntity, String> oID = id;
        TableColumn<ProviderEntity, String> payment = paymentMethod;
        TableColumn<ProviderEntity, String> paymentStatus = status;
        TableColumn<ProviderEntity, String> orderPriority = priority;
        TableColumn<ProviderEntity, String> information = info;
        TableColumn<ProviderEntity, String> phoneNumber = phone;


        oID.setCellValueFactory(new PropertyValueFactory<>("id"));
        payment.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        orderPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));


        orders.getColumns().addAll(oID, paymentMethod, paymentStatus, orderPriority, information, phoneNumber);
        orders.getItems().addAll(getListOrders());
    }

    public void deleteBtn() {

        if (deletedID.getText().trim().isEmpty() || deletedID.getText().trim().toLowerCase().contains("drop")) {
            message.setText("Illegal string!");
        } else {
            int id = Integer.parseInt(deletedID.getText());
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete criteriaDelete = criteriaBuilder.createCriteriaDelete(ProductEntity.class);
            Root root = criteriaDelete.from(ProductEntity.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("idOrder"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        }
        deleteOrder();
        createOrderList();
        deletedID.clear();
    }

    public void deleteOrder() {
        if (deletedID.getText().trim().isEmpty() || deletedID.getText().trim().toLowerCase().contains("drop")) {
            message.setText("Illegal string!");
        } else {
            int id = Integer.parseInt(deletedID.getText());
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete criteriaDelete = criteriaBuilder.createCriteriaDelete(OrderEntity.class);
            Root root = criteriaDelete.from(OrderEntity.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        }
    }
}



