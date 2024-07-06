module project.warehouse {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires static lombok;
    requires jakarta.persistence;
    requires criteria.api;

    opens project.warehouse to javafx.fxml;
    exports project.warehouse;
    exports project.warehouse.controller;
    opens project.warehouse.controller to javafx.fxml;
    exports project.warehouse.entity;
    opens project.warehouse.entity to org.hibernate.orm.core;
    exports project.warehouse.controller.admin;
    opens project.warehouse.controller.admin to javafx.fxml;
    exports project.warehouse.controller.onetofive;
    opens project.warehouse.controller.onetofive to javafx.fxml;
    exports project.warehouse.dto;
    opens project.warehouse.dto to org.hibernate.orm.core;
}