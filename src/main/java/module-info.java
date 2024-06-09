module project.warehouse {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires static lombok;
    requires jakarta.persistence;


    opens project.warehouse to javafx.fxml;
    exports project.warehouse;
    exports project.warehouse.controller;
    opens project.warehouse.controller to javafx.fxml;
    exports project.warehouse.entity;
    opens project.warehouse.entity to org.hibernate.orm.core;
}