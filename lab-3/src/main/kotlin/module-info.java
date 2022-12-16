module ru.nstu.lab3 {
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  opens ru.nstu.lab3 to javafx.fxml;
  exports ru.nstu.lab3;
}