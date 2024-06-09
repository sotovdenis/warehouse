package project.warehouse.controller;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    public TableView providers;
    public TableColumn providerID;
    public TableColumn rep;
    public TableColumn sur;
    public TableColumn providerName;
    public TableColumn fathersName;
    public TableColumn provider_phone;
    public Text message;
    @FXML
    private TextField fatherName;

    @FXML
    private Button godMode;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField reputation;

    @FXML
    private TextField surname;

    public void initialize() throws SQLException {
        createProviderList();
    }

    public List<ProviderEntity> getListProviders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProviderEntity> criteriaQuery = builder.createQuery(ProviderEntity.class);
        criteriaQuery.from(ProviderEntity.class);

        return session.createQuery(criteriaQuery).getResultList();
    }

//    public List<OrderEntity> getListOrders() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteriaQuery = builder.createQuery(OrderEntity.class);
//        criteriaQuery.from(ProviderEntity.class);
//
//        return session.createQuery(criteriaQuery).getResultList();
//    }

    void createProviderList() {
        providers.getColumns().clear();
        providers.getItems().clear();

        TableColumn<ProviderEntity, String> pID = providerID;
        TableColumn<ProviderEntity, String> reputation = rep;
        TableColumn<ProviderEntity, String> surname = sur;
        TableColumn<ProviderEntity, String> providersName = providerName;
        TableColumn<ProviderEntity, String> providersFather = fathersName;
        TableColumn<ProviderEntity, String> phoneNumber = provider_phone;


        pID.setCellValueFactory(new PropertyValueFactory<>("id"));
        reputation.setCellValueFactory(new PropertyValueFactory<>("reputation"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        providersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        providersFather.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));


        providers.getColumns().addAll(pID, reputation, surname, providersName, providersFather, phoneNumber);
        providers.getItems().addAll(getListProviders());
    }

//    void createOrderList() {
//        orders.getColumns().clear();
//        orders.getItems().clear();
//
//        TableColumn<ProviderEntity, String> oID = orderID;
//        TableColumn<ProviderEntity, String> paymentMethod = payment;
//        TableColumn<ProviderEntity, String> paymentStatus = status;
//        TableColumn<ProviderEntity, String> orderPriority = providerName;
//        TableColumn<ProviderEntity, String> information = info;
//        TableColumn<ProviderEntity, String> phoneNumber = customerPhone;
//
//
//        oID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        paymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
//        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
//        orderPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
//        information.setCellValueFactory(new PropertyValueFactory<>("information"));
//        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhonenumber"));
//
//
//        orders.getColumns().addAll(oID, paymentMethod, paymentStatus, orderPriority, information, phoneNumber);
//        orders.getItems().addAll(getListOrders());
//    }

    public void setNewProvider(ActionEvent actionEvent) {
        if (reputation.getText().trim().isEmpty() ||
                surname.getText().trim().isEmpty() ||
                providerName.getText().trim().isEmpty() ||
                fatherName.getText().trim().isEmpty() ||
                phone.getText().trim().isEmpty()
        ) {
            message.setText("Not enough data to create provider!");
        } else {
            ProviderEntity provider = new ProviderEntity();
            provider.setReputation(Integer.parseInt(reputation.getText()));
            provider.setSurname(surname.getText());
            provider.setName(providerName.getText());
            provider.setFatherName(fatherName.getText());
            provider.setPhone(phone.getText());

            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.persist(provider);
                transaction.commit();
                message.setText("Provider Added!");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    public void toOrdersList(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/project/warehouse/hello-view.fxml"));//TODO


        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ORDER");
        stage.showAndWait();
    }
}
