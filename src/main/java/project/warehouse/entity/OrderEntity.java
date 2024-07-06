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
@Table(name = "reservation", schema = "public", catalog = "database")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JoinColumn
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "payment_method", nullable = false, length = 255)
    private String paymentMethod;
    @Basic
    @Column(name = "payment_status", nullable = false, length = 255)
    private String paymentStatus;
    @Basic
    @Column(name = "priority", nullable = false)
    private Integer priority;
    @Basic
    @Column(name = "information", nullable = true, length = 255)
    private String information;
    @Basic
    @Column(name = "customer_phonenumber", nullable = false, length = 255)
    private String phonenumber;
    @Basic
    @Column(name = "status", nullable = false)
    private Integer status;

    public OrderEntity(String information, Integer priority){
        this.information = information;
        this.priority = priority;
    }

}
