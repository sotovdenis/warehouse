package project.warehouse.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import project.warehouse.entity.ComponentEntity;
import project.warehouse.entity.OrderEntity;
import project.warehouse.entity.ProductEntity;
import project.warehouse.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PlumberController {

    @FXML
    private Text addMessage;

    @FXML
    private Button addNewProduct;

    @FXML
    private TableColumn cComponentName;

    @FXML
    private TableColumn cComponentPrice;

    @FXML
    private TableColumn cComponentWeight;

    @FXML
    private TableColumn cCustomerPhone;

    @FXML
    private TableColumn cIdOrder;

    @FXML
    private TableColumn cIdProduct;

    @FXML
    private TableColumn cIdShipment;

    @FXML
    private TableColumn cIdWarehouse;

    @FXML
    private TableColumn cIdcomponent;

    @FXML
    private TableColumn cInfo;

    @FXML
    private TableColumn cInstruction;

    @FXML
    private TableColumn cPaymentMethod;

    @FXML
    private TableColumn cPaymentStatus;

    @FXML
    private TableColumn cPriority;

    @FXML
    private TableColumn cProductIn;

    @FXML
    private TableColumn cProductName;

    @FXML
    private TableColumn cProductPrice;

    @FXML
    private TableColumn cProductWarehouse;

    @FXML
    private TableColumn cProductWeight;

    @FXML
    private TableView componentsTable;

    @FXML
    private TableColumn cprOrderId;

    @FXML
    private Text errorMessage;

    @FXML
    private TableView ordersTable;

    @FXML
    private TableView productsTable;

    @FXML
    private TableView tableTodo;

    @FXML
    private TextField textClosedOrder;

    @FXML
    private TextField textInstruction;

    @FXML
    private TextField textProductName;

    @FXML
    private TextField textWarehouse;

    @FXML
    private TextField textComponentToDelete1;

    @FXML
    private TextField textComponentToDelete2;

    @FXML
    private TextField textComponentToDelete3;

    @FXML
    private TableColumn todoName;

    @FXML
    private TableColumn todoPriority;

    public void initialize() {
        createOrderList();
        createComponentList();
        createProductList();
        createOrderToAssemblyList();
    }

    @FXML
    void addNewProduct() {
        if (textProductName.getText().trim().isEmpty() ||
                textInstruction.getText().trim().isEmpty() ||
                textWarehouse.getText().trim().isEmpty() ||
                textClosedOrder.getText().trim().isEmpty() ||
                calculateWeight() == null
        ) {
            errorMessage.setText("Not enough data to create product!");
        } else if (isNull()) {
            ProductEntity product = new ProductEntity();

            product.setName(textProductName.getText());
            product.setIdWarehouse(Integer.valueOf(textWarehouse.getText()));
            product.setIdOrder(Integer.valueOf(textClosedOrder.getText()));
            product.setInsruction(textInstruction.getText());
            product.setPrice(calculateWeight().get(0) * 1.5);
            product.setWeight(calculateWeight().get(1) * 1.2);

            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.persist(product);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }

            addMessage.setText("You`ve added new Product!");
            if (isNull()) {
                setComponentIdInProduct();
            } else errorMessage.setText("Already used component!!!");

            initialize();
        }
    }

    public Integer getProductCreatedId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Integer id = null;

        try {
            Integer closedOrder = Integer.parseInt(textClosedOrder.getText());

            String hql = "SELECT p.id FROM ProductEntity p WHERE p.idOrder = :closedOrder";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("closedOrder", closedOrder);

            List<Integer> result = query.getResultList();

            transaction.commit();

            if (!result.isEmpty()) {
                id = result.get(0);
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    void setComponentIdInProduct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (isNull()) {

            ComponentEntity component1 = session.get(ComponentEntity.class, Integer.parseInt(textComponentToDelete1.getText()));
            component1.setIdProductIn(getProductCreatedId());
            session.merge(component1);

            ComponentEntity component2 = session.get(ComponentEntity.class, Integer.parseInt(textComponentToDelete2.getText()));
            component2.setIdProductIn(getProductCreatedId());
            session.merge(component2);

            ComponentEntity component3 = session.get(ComponentEntity.class, Integer.parseInt(textComponentToDelete3.getText()));
            component3.setIdProductIn(getProductCreatedId());
            session.merge(component3);

            transaction.commit();
            session.close();
        }
    }

    public boolean isNull() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<ComponentEntity> root = criteriaQuery.from(ComponentEntity.class);

        boolean resultIsNull = true;

        criteriaQuery.select(root.get("id"))
                .where(criteriaBuilder.isNull(root.get("idProductIn")));

        List<Integer> result = session.createQuery(criteriaQuery).getResultList();

        if (result.contains(Integer.parseInt(textComponentToDelete1.getText()))) {
            if (result.contains(Integer.parseInt(textComponentToDelete2.getText()))) {
                if (result.contains(Integer.parseInt(textComponentToDelete3.getText()))) {
                    resultIsNull = true;
                }
            }
        } else {
            errorMessage.setText("Already used component!");
            resultIsNull = false;
        }
        return resultIsNull;
    }

    public List<Double> calculateWeight() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        ComponentEntity component1 = session
                .get(ComponentEntity.class, Integer.parseInt(textComponentToDelete1.getText()));
        Double weight = component1.getWeight();
        Double price = component1.getPrice();

        ComponentEntity component2 = session
                .get(ComponentEntity.class, Integer.parseInt(textComponentToDelete2.getText()));
        weight += component2.getWeight();
        price += component2.getPrice();

        ComponentEntity component3 = session
                .get(ComponentEntity.class, Integer.parseInt(textComponentToDelete3.getText()));
        weight += component3.getWeight();
        price += component3.getPrice();

        transaction.commit();
        session.close();

        List<Double> weightAndPrice = new ArrayList<>();
        weightAndPrice.add(price);
        weightAndPrice.add(weight);

        return weightAndPrice;
    }

    public List<OrderEntity> getListOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orders = session.createQuery(
                        "FROM OrderEntity ORDER BY priority DESC", OrderEntity.class)
                .getResultList();
        session.close();
        return orders;
    }

    public List<OrderEntity> getListOrdersToAssembly() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);

        criteriaQuery.select(criteriaBuilder.construct(OrderEntity.class, root.get("information"), root.get("priority")));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("priority")));

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

    public List<ProductEntity> getListProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductEntity> orders = session.createQuery(
                        "FROM ProductEntity ORDER BY id ASC", ProductEntity.class)
                .getResultList();
        session.close();
        return orders;
    }

    void createOrderList() {
        ordersTable.getColumns().clear();
        ordersTable.getItems().clear();

        TableColumn<OrderEntity, String> idOrderColumn = cIdOrder;
        TableColumn<OrderEntity, String> paymentMethodColumn = cPaymentMethod;
        TableColumn<OrderEntity, String> paymentStatusColumn = cPaymentStatus;
        TableColumn<OrderEntity, String> priorityColumn = cPriority;
        TableColumn<OrderEntity, String> infoColumn = cInfo;
        TableColumn<OrderEntity, String> phoneNumberColumn = cCustomerPhone;

        idOrderColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("information"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));


        ordersTable.getColumns().addAll(idOrderColumn,
                paymentMethodColumn,
                paymentStatusColumn,
                priorityColumn,
                infoColumn,
                phoneNumberColumn);
        ordersTable.getItems().addAll(getListOrders());
    }

    void createComponentList() {
        componentsTable.getColumns().clear();
        componentsTable.getItems().clear();

        TableColumn<ComponentEntity, String> idComponentColumn = cIdcomponent;
        TableColumn<ComponentEntity, String> idProductInColumn = cProductIn;
        TableColumn<ComponentEntity, String> idWarehouseColumn = cIdWarehouse;
        TableColumn<ComponentEntity, String> idShipmentColumn = cIdShipment;
        TableColumn<ComponentEntity, String> nameColumn = cComponentName;
        TableColumn<ComponentEntity, String> priceColumn = cComponentPrice;
        TableColumn<ComponentEntity, String> weightColumn = cComponentWeight;

        idComponentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idProductInColumn.setCellValueFactory(new PropertyValueFactory<>("idProductIn"));
        idWarehouseColumn.setCellValueFactory(new PropertyValueFactory<>("idWarehouse"));
        idShipmentColumn.setCellValueFactory(new PropertyValueFactory<>("idShipment"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        componentsTable.getColumns().addAll(idComponentColumn,
                idProductInColumn,
                idWarehouseColumn,
                idShipmentColumn,
                nameColumn,
                priceColumn,
                weightColumn);
        componentsTable.getItems().addAll(getListComponents());
    }

    void createProductList() {
        productsTable.getColumns().clear();
        productsTable.getItems().clear();

        TableColumn<ProductEntity, String> idProductColumn = cIdProduct;
        TableColumn<ProductEntity, String> idWarehouseColumn = cProductWarehouse;
        TableColumn<ProductEntity, String> idOrderColumn = cprOrderId;
        TableColumn<ProductEntity, String> instructionColumn = cInstruction;
        TableColumn<ProductEntity, String> nameColumn = cProductName;
        TableColumn<ProductEntity, String> priceColumn = cProductPrice;
        TableColumn<ProductEntity, String> weightColumn = cProductWeight;

        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idWarehouseColumn.setCellValueFactory(new PropertyValueFactory<>("idWarehouse"));
        idOrderColumn.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        instructionColumn.setCellValueFactory(new PropertyValueFactory<>("insruction"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        productsTable.getColumns().addAll(idProductColumn,
                idWarehouseColumn,
                idOrderColumn,
                instructionColumn,
                nameColumn,
                priceColumn,
                weightColumn);
        productsTable.getItems().addAll(getListProducts());
    }

    void createOrderToAssemblyList() {
        tableTodo.getColumns().clear();
        tableTodo.getItems().clear();

        TableColumn<OrderEntity, String> todoNameColumn = todoName;
        TableColumn<OrderEntity, String> todoPriorityColumn = todoPriority;

        todoNameColumn.setCellValueFactory(new PropertyValueFactory<>("information"));
        todoPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        tableTodo.getColumns().addAll(todoNameColumn, todoPriorityColumn);
        tableTodo.getItems().addAll(getListOrdersToAssembly());
    }

}
