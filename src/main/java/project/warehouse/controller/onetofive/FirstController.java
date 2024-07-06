package project.warehouse.controller.onetofive;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import project.warehouse.dto.FirstDTO;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProductEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.util.List;

public class FirstController {

    @FXML
    private TableColumn paymentStatusColumn;

    @FXML
    private TableColumn phonenumberColumn;

    @FXML
    private TableColumn productIdColumn;

    @FXML
    private TableView tableJoin;

    @FXML
    private TextField textStatusId;


    public List<FirstDTO> getListFirst() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<FirstDTO> query = session.createQuery(
                "SELECT p.id, r.paymentStatus, r.phonenumber from OrderEntity r join ProductEntity p on r.id = p.idOrder where r.status = :status order by p.id desc ", FirstDTO.class);
        query.setParameter("status", Integer.parseInt(textStatusId.getText()));
        List<FirstDTO> results = query.getResultList();
        return  results;
    }

    void createOrderToAssemblyList() {
        tableJoin.getColumns().clear();
        tableJoin.getItems().clear();

        TableColumn<FirstDTO, String> productId = productIdColumn;
        TableColumn<FirstDTO, String> paymentStatus = paymentStatusColumn;
        TableColumn<FirstDTO, String> phoneNumber = phonenumberColumn;

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));

        tableJoin.getColumns().addAll(productId, paymentStatus,phoneNumber);
        tableJoin.getItems().addAll(getListFirst());
    }

    public void show() {
        createOrderToAssemblyList();
    }
}