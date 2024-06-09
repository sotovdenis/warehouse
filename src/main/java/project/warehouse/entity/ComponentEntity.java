package project.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "component", schema = "public", catalog = "database")
public class ComponentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "id_product_in", nullable = true)
    private Integer idProductIn;
    @Basic
    @Column(name = "id_warehouse", nullable = false)
    private Integer idWarehouse;
    @Basic
    @Column(name = "id_shipment", nullable = false)
    private Integer idShipment;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private Double price;
    @Basic
    @Column(name = "weight", nullable = false, precision = 0)
    private Double weight;

}
