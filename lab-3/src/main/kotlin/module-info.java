module ru.nstu.lab3 {
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  uses ru.nstu.lab3.factory.ObjectFactory;
  provides ru.nstu.lab3.factory.ObjectFactory with ru.nstu.lab3.factory.impl.IntegerFactory, ru.nstu.lab3.factory.impl.StringFactory;

  opens ru.nstu.lab3 to javafx.fxml;
  exports ru.nstu.lab3;
}