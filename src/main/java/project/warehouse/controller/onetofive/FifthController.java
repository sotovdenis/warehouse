package project.warehouse.controller.onetofive;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import project.warehouse.dto.FourthDTO;
import project.warehouse.hibernate.HibernateUtil;

import java.util.List;

public class FifthController {
    public TextField textComponentId;
    public TableView tableJoin;
    public TableColumn productNameColumn;
    public TableColumn priceColumn;


    public List<FourthDTO> getListSecond() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<FourthDTO> query = session.createQuery(
                "select c.name as component_name, ceiling(avg(c.price)) as components_price from ComponentEntity c join ProductEntity p on p.id = c.idProductIn and c.price > :price join OrderEntity r on p.idOrder = r.id group by component_name", FourthDTO.class);
        query.setParameter("price", Integer.parseInt(textComponentId.getText()));
        List<FourthDTO> results = query.getResultList();
        return results;
    }

    void createSecond() {
        tableJoin.getColumns().clear();
        tableJoin.getItems().clear();

        TableColumn<FourthDTO, String> name = productNameColumn;
        TableColumn<FourthDTO, String> price = priceColumn;


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        tableJoin.getColumns().addAll(name, price);
        tableJoin.getItems().addAll(getListSecond());
    }

    public void show() {
        createSecond();
    }
}
