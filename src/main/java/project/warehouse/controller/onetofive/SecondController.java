package project.warehouse.controller.onetofive;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import project.warehouse.dto.FirstDTO;
import project.warehouse.dto.SecondDTO;
import project.warehouse.hibernate.HibernateUtil;

import java.util.List;

public class SecondController {
    public TableColumn productIdColumn;
    public TableColumn nameColumn;
    public TableColumn priceColumn;
    public TextField textStatusId;
    public TableView tableJoin;


    public List<SecondDTO> getListSecond() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<SecondDTO> query = session.createQuery(
                "select p.id, p.name, p.price from ProductEntity p where exists (select c from ComponentEntity c where exists (select s from ShipmentEntity s where c.idShipment = s.id and c.idProductIn = p.id and p.idWarehouse = :idWarehouse))", SecondDTO.class);
        query.setParameter("idWarehouse", Integer.parseInt(textStatusId.getText()));
        List<SecondDTO> results = query.getResultList();
        return  results;
    }

    void createSecond() {
        tableJoin.getColumns().clear();
        tableJoin.getItems().clear();

        TableColumn<SecondDTO, String> productId = productIdColumn;
        TableColumn<SecondDTO, String> name = nameColumn;
        TableColumn<SecondDTO, String> price = priceColumn;

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableJoin.getColumns().addAll(productId, name,price);
        tableJoin.getItems().addAll(getListSecond());
    }

    public void show() {
        createSecond();
    }
}
