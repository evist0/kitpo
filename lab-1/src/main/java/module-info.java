module com.evist0.labforwardlist {
    requires javafx.controls;
    requires javafx.fxml;

    uses com.evist0.labforwardlist.factory.ObjectFactory;

    provides com.evist0.labforwardlist.factory.ObjectFactory with com.evist0.labforwardlist.factory.impl.StringFactory, com.evist0.labforwardlist.factory.impl.IntegerFactory;

    opens com.evist0.labforwardlist to javafx.fxml;
    exports com.evist0.labforwardlist;
}