package project.warehouse.controller.onetofive;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import project.warehouse.dto.FourthDTO;
import project.warehouse.dto.ThirdDTO;
import project.warehouse.hibernate.HibernateUtil;

import java.util.List;

public class FourthController {
    public TextField textWarehouseId;
    public TableView tableJoin;
    public TableColumn productNameColumn;
    public TableColumn priceColumn;
    public TextField textPriceMin;

    public List<FourthDTO> getListSecond() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<FourthDTO> query = session.createQuery(
                "select p.name, sum(p.price) as price from ProductEntity p join WarehouseEntity w on p.idWarehouse = w.id where w.id = :idWarehouse and p.price > :price group by p.name", FourthDTO.class);
        query.setParameter("idWarehouse", Integer.parseInt(textWarehouseId.getText()));
        query.setParameter("price",Double.parseDouble(textPriceMin.getText()));
        List<FourthDTO> results = query.getResultList();
        return results;
    }

    void createSecond() {
        tableJoin.getColumns().clear();
        tableJoin.getItems().clear();

        TableColumn<FourthDTO, String> name = productNameColumn;
        TableColumn<FourthDTO, String> count = priceColumn;


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        count.setCellValueFactory(new PropertyValueFactory<>("price"));


        tableJoin.getColumns().addAll(name, count);
        tableJoin.getItems().addAll(getListSecond());
    }

    public void show() {
        createSecond();
    }
}
