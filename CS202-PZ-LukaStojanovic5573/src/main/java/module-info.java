module com.cs203 {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires javafx.base;

    opens com.cs203.model to org.hibernate.orm.core;

    exports com.cs203;
}
