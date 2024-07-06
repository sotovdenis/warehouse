package project.warehouse.controller.admin;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
import project.warehouse.entity.ComponentEntity;
import project.warehouse.entity.ProviderEntity;
import project.warehouse.entity.ShipmentEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.sql.Timestamp;
import java.util.List;

public class AdminComponentController {

    public Text errorMessage;
    public Text message;
    @FXML
    private TextField count;

    @FXML
    private Button createComponent;

    @FXML
    private TableColumn dateShipment;

    @FXML
    private TableColumn idComponent;

    @FXML
    private TableColumn idProductInComponent;

    @FXML
    private TableColumn idProviderShipment;

    @FXML
    private TableColumn idShipment;

    @FXML
    private TableColumn idShipmentComponent;

    @FXML
    private TableColumn idWarehouseComponent;

    @FXML
    private TableColumn nameComponent;

    @FXML
    private TableColumn priceComponent;

    @FXML
    private TableColumn priorityShipment;

    @FXML
    private TableView tableComponents;

    @FXML
    private TableView tableShipments;

    @FXML
    private TextField textIdShipment;

    @FXML
    private TextField textIdWarehouse;

    @FXML
    private TextField textNameComponent;

    @FXML
    private TextField textPriceComponent;

    @FXML
    private TextField textWeightComponent;

    @FXML
    private TableColumn weightComponent;

    public void initialize() {
        createShipmentList();
        createComponentList();
    }

    @FXML
    void createComponent() {
        if (textIdShipment.getText().trim().isEmpty() ||
                textNameComponent.getText().trim().isEmpty() ||
                textIdWarehouse.getText().trim().isEmpty() ||
                textPriceComponent.getText().trim().isEmpty() ||
                textWeightComponent.getText().trim().isEmpty() ||
                count.getText().trim().isEmpty()
        ) {
            errorMessage.setText("Not enough data to create Component!");
        } else {

            for (int i = 0; i<Integer.parseInt(count.getText());i++) {
                ComponentEntity component = new ComponentEntity();
                component.setName(textNameComponent.getText());
                component.setPrice(Double.parseDouble(textPriceComponent.getText()));
                component.setWeight(Double.parseDouble(textWeightComponent.getText()));
                component.setIdShipment(Integer.parseInt(textIdShipment.getText()));
                component.setIdWarehouse(Integer.parseInt(textIdWarehouse.getText()));

                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    session.persist(component);
                    transaction.commit();
                    message.setText("Components created!");
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }
        }

        initialize();


    }

    public List<ShipmentEntity> getListProviders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ShipmentEntity> criteriaQuery = builder.createQuery(ShipmentEntity.class);
        criteriaQuery.from(ShipmentEntity.class);

        return session.createQuery(criteriaQuery).getResultList();
    }

    public List<ComponentEntity> getListComponents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ComponentEntity> orders = session.createQuery(
                        "FROM ComponentEntity ORDER BY id ASC", ComponentEntity.class)
                .getResultList();
        session.close();
        return orders;
    }

    void createShipmentList() {
        tableShipments.getColumns().clear();
        tableShipments.getItems().clear();

        TableColumn<ProviderEntity, String> sID = idShipment;
        TableColumn<ProviderEntity, String> idProvider = idProviderShipment;
        TableColumn<ProviderEntity, String> date = dateShipment;
        TableColumn<ProviderEntity, String> priority = priorityShipment;


        sID.setCellValueFactory(new PropertyValueFactory<>("id"));
        idProvider.setCellValueFactory(new PropertyValueFactory<>("idProvider"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));


        tableShipments.getColumns().addAll(sID, idProvider, date, priority);
        tableShipments.getItems().addAll(getListProviders());
    }

    void createComponentList() {
        tableComponents.getColumns().clear();
        tableComponents.getItems().clear();

        TableColumn<ComponentEntity, String> idComponentColumn = idComponent;
        TableColumn<ComponentEntity, String> idProductInColumn = idProductInComponent;
        TableColumn<ComponentEntity, String> idWarehouseColumn = idWarehouseComponent;
        TableColumn<ComponentEntity, String> idShipmentColumn = idShipmentComponent;
        TableColumn<ComponentEntity, String> nameColumn = nameComponent;
        TableColumn<ComponentEntity, String> priceColumn = priceComponent;
        TableColumn<ComponentEntity, String> weightColumn = weightComponent;

        idComponentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idProductInColumn.setCellValueFactory(new PropertyValueFactory<>("idProductIn"));
        idWarehouseColumn.setCellValueFactory(new PropertyValueFactory<>("idWarehouse"));
        idShipmentColumn.setCellValueFactory(new PropertyValueFactory<>("idShipment"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tableComponents.getColumns().addAll(idComponentColumn,
                idProductInColumn,
                idWarehouseColumn,
                idShipmentColumn,
                nameColumn,
                priceColumn,
                weightColumn);
        tableComponents.getItems().addAll(getListComponents());
    }

}