package project.warehouse.controller.admin;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.Transaction;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.entity.ShipmentEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AdminShipmentController {

    public Text errorMessage;
    public Text message;
    @FXML
    private TextField priority;

    @FXML
    private Button createShipment;

    @FXML
    private TableColumn fathersNameColumn;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TextField idProvider;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn phoneColumn;

    @FXML
    private TableColumn repColumn;

    @FXML
    private TableColumn surnameColumn;

    @FXML
    private TableView tableProvider;

    public void initialize() {
        createShipmentList();
    }


        @FXML
    void createShipment() {
        if (idProvider.getText().trim().isEmpty() ||
                priority.getText().trim().isEmpty()
        ) {
            errorMessage.setText("Not enough data to create order!");
        } else {
            ShipmentEntity shipment = new ShipmentEntity();
            shipment.setDate(new Timestamp(System.currentTimeMillis()));
            shipment.setIdProvider(Integer.parseInt(idProvider.getText()));
            shipment.setPriority(Integer.parseInt(priority.getText()));


            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.persist(shipment);
                transaction.commit();
                message.setText("Shipment created!");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }

        initialize();

    }

    public List<ProviderEntity> getListProviders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProviderEntity> criteriaQuery = builder.createQuery(ProviderEntity.class);
        criteriaQuery.from(ProviderEntity.class);

        return session.createQuery(criteriaQuery).getResultList();
    }

    void createShipmentList() {
        tableProvider.getColumns().clear();
        tableProvider.getItems().clear();

        TableColumn<ProviderEntity, String> pID = idColumn;
        TableColumn<ProviderEntity, String> reputation = repColumn;
        TableColumn<ProviderEntity, String> surname = surnameColumn;
        TableColumn<ProviderEntity, String> providersName = nameColumn;
        TableColumn<ProviderEntity, String> providersFather = fathersNameColumn;
        TableColumn<ProviderEntity, String> phoneNumber = phoneColumn;


        pID.setCellValueFactory(new PropertyValueFactory<>("id"));
        reputation.setCellValueFactory(new PropertyValueFactory<>("reputation"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        providersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        providersFather.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));


        tableProvider.getColumns().addAll(pID, reputation, surname, providersName, providersFather, phoneNumber);
        tableProvider.getItems().addAll(getListProviders());
    }

}
