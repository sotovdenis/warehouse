package project.warehouse.controller.onetofive;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import project.warehouse.dto.SecondDTO;
import project.warehouse.dto.ThirdDTO;
import project.warehouse.hibernate.HibernateUtil;

import java.util.List;

public class ThirdController {
    public TextField textStatusId;
    public TableView tableJoin;
    public TableColumn productIdColumn;
    public TableColumn nameColumn;

    public List<ThirdDTO> getListSecond() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<ThirdDTO> query = session.createQuery(
                "select c.name, count(c.price) > :count from ComponentEntity c join ProductEntity p on c.idProductIn = p.id group by c.name", ThirdDTO.class);
        query.setParameter("count", Integer.parseInt(textStatusId.getText()));
        List<ThirdDTO> results = query.getResultList();
        return results;
    }

    void createSecond() {
        tableJoin.getColumns().clear();
        tableJoin.getItems().clear();

        TableColumn<ThirdDTO, String> name = productIdColumn;
        TableColumn<ThirdDTO, String> count = nameColumn;


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        count.setCellValueFactory(new PropertyValueFactory<>("componentCount"));


        tableJoin.getColumns().addAll(name, count);
        tableJoin.getItems().addAll(getListSecond());
    }

    public void show() {
        createSecond();
    }
}
