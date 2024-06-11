package project.warehouse.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import project.warehouse.entity.*;
import project.warehouse.hibernate.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class AdminChoiceController {

    public TableColumn c1;
    public TableColumn c2;
    public TableColumn c3;
    public TableColumn c4;
    public TableView multiTable;
    public TableColumn c5;
    public TableColumn c6;
    @FXML
    private Button createList;

    @FXML
    private TextField setId;

    @FXML
    private ChoiceBox<String> tableChoice;

    public void initialize() throws SQLException {
        tableChoice.setItems(FXCollections.observableArrayList("Component",//DONE
                "Product",//DONE
                "Provider",//DONE
                "Order",//DONE
                "Shipment",//DONE
                "Warehouse"));//DONE
    }

    @FXML
    void createList() throws SQLException {
        box();
    }
    public void box() throws SQLException {//TODO продолжить!!!!!!!!!!!!!!!!!!!!!!!
        String choice = tableChoice.getValue();

        if (choice.equalsIgnoreCase("Provider")){
            createProviderList();
        } else if (choice.equalsIgnoreCase("Order")){
            createOrderList();
        } else  if (choice.equalsIgnoreCase("Product")){
            createProductList();
        } else if (choice.equalsIgnoreCase("Component")){
            createComponentList();
        } else if (choice.equalsIgnoreCase("Shipment")){
            createShipmentList();
        } else if (choice.equalsIgnoreCase("Warehouse")){
            createWarehouseList();
        }

    }

    public List<ProviderEntity> getListProviders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProviderEntity> criteriaQuery = builder.createQuery(ProviderEntity.class);
        Root<ProviderEntity> root = criteriaQuery.from(ProviderEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<ProviderEntity> providers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return providers;
    }

    public List<OrderEntity> getListOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = builder.createQuery(OrderEntity.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<OrderEntity> orders = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return orders;
    }

    public List<ProductEntity> getListProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> criteriaQuery = builder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<ProductEntity> orders = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return orders;
    }

    public List<ComponentEntity> getListComponents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ComponentEntity> criteriaQuery = builder.createQuery(ComponentEntity.class);
        Root<ComponentEntity> root = criteriaQuery.from(ComponentEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<ComponentEntity> providers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return providers;
    }

    public List<ShipmentEntity> getListShipments() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ShipmentEntity> criteriaQuery = builder.createQuery(ShipmentEntity.class);
        Root<ShipmentEntity> root = criteriaQuery.from(ShipmentEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<ShipmentEntity> providers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return providers;
    }

    public List<WarehouseEntity> getListSWarehouses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseEntity> criteriaQuery = builder.createQuery(WarehouseEntity.class);
        Root<WarehouseEntity> root = criteriaQuery.from(WarehouseEntity.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("id"), Integer.parseInt(setId.getText().trim().toString())));

        List<WarehouseEntity> providers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return providers;
    }

    void createOrderList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<OrderEntity, String> payment = c1;
        TableColumn<OrderEntity, String> paymentStatus = c2;
        TableColumn<OrderEntity, String> information = c3;
        TableColumn<OrderEntity, String> phoneNumber = c4;


        payment.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));


        multiTable.getColumns().addAll(payment, paymentStatus, information, phoneNumber);
        multiTable.getItems().addAll(getListOrders());
    }

    void createProviderList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<ProviderEntity, String> reputation = c1;
        TableColumn<ProviderEntity, String> surname = c2;
        TableColumn<ProviderEntity, String> providersName = c3;
        TableColumn<ProviderEntity, String> providersFather = c4;
        TableColumn<ProviderEntity, String> phoneNumber = c5;


        reputation.setCellValueFactory(new PropertyValueFactory<>("reputation"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        providersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        providersFather.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));


        multiTable.getColumns().addAll(reputation, surname, providersName, providersFather, phoneNumber);
        multiTable.getItems().addAll(getListProviders());
    }

    void createProductList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<ProductEntity, String> warehouse = c1;
        TableColumn<ProductEntity, String> idOrder= c2;
        TableColumn<ProductEntity, String> name = c3;
        TableColumn<ProductEntity, String> price = c4;
        TableColumn<ProductEntity, String> weight = c5;


        warehouse.setCellValueFactory(new PropertyValueFactory<>("idWarehouse"));
        idOrder.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));


        multiTable.getColumns().addAll(warehouse, idOrder, name, price, weight);
        multiTable.getItems().addAll(getListProducts());
    }

    void createComponentList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<ComponentEntity, String> idIn = c1;
        TableColumn<ComponentEntity, String> idWarehouse = c2;
        TableColumn<ComponentEntity, String> idShipment = c3;
        TableColumn<ComponentEntity, String> name = c4;
        TableColumn<ComponentEntity, String> price = c5;
        TableColumn<ComponentEntity, String> weight = c6;


        idIn.setCellValueFactory(new PropertyValueFactory<>("idProductIn"));
        idWarehouse.setCellValueFactory(new PropertyValueFactory<>("idWarehouse"));
        idShipment.setCellValueFactory(new PropertyValueFactory<>("idShipment"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));


        multiTable.getColumns().addAll(idIn, idWarehouse, idShipment, name, price, weight);
        multiTable.getItems().addAll(getListComponents());
    }

    void createShipmentList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<ShipmentEntity, String> idProvider = c1;
        TableColumn<ShipmentEntity, String> date = c2;
        TableColumn<ShipmentEntity, String> priority = c3;


        idProvider.setCellValueFactory(new PropertyValueFactory<>("idProvider"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));


        multiTable.getColumns().addAll(idProvider, date, priority);
        multiTable.getItems().addAll(getListShipments());
    }

    void createWarehouseList() {
        multiTable.getColumns().clear();
        multiTable.getItems().clear();

        TableColumn<WarehouseEntity, String> adress = c1;
        TableColumn<WarehouseEntity, String> phoneNumber = c2;
        TableColumn<WarehouseEntity, String> guard = c3;


        adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        guard.setCellValueFactory(new PropertyValueFactory<>("guard"));


        multiTable.getColumns().addAll(adress, phoneNumber, guard);
        multiTable.getItems().addAll(getListSWarehouses());
    }



}