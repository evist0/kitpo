package com.evist0.labforwardlist;

import com.evist0.labforwardlist.factory.ObjectFactory;
import com.evist0.labforwardlist.factory.ObjectFactoryRegistry;
import com.evist0.labforwardlist.model.ForwardList;
import com.evist0.labforwardlist.model.ForwardListWithType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String DUMP_FILENAME = "save.bin";

    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField inputTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button insertButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button sortButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private ListView itemListView;

    private final ObservableList<String> typeObservableList;
    private final Property<String> selectedTypeProperty;
    private ForwardList forwardList;

    public MainController() {
        typeObservableList = FXCollections.observableList(ObjectFactoryRegistry.getAllFactories());
        selectedTypeProperty = new SimpleStringProperty(typeObservableList.get(0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoiceBox.setItems(typeObservableList);
        typeChoiceBox.valueProperty().bindBidirectional(selectedTypeProperty);

        selectedTypeProperty.addListener((observableValue, old, value) -> {
            forwardList = null;
            onItemCollectionChanged();
        });

        addButton.setOnAction(actionEvent -> {
            if (forwardList == null) {
                forwardList = new ForwardList();
            }

            forwardList.add(getObjectFactory().parse(inputTextField.getText()));
            onItemCollectionChanged();
        });

        insertButton.setOnAction(actionEvent -> {
            int at = itemListView.getSelectionModel().getSelectedIndex();

            forwardList.insert(at, getObjectFactory().parse(inputTextField.getText()));
            onItemCollectionChanged();
        });

        removeButton.setOnAction(actionEvent -> {
            int at = itemListView.getSelectionModel().getSelectedIndex();

            forwardList.remove(at);
            onItemCollectionChanged();
        });

        sortButton.setOnAction(actionEvent -> {
            forwardList.sort(getObjectFactory().getComparator());
            onItemCollectionChanged();
        });

        loadButton.setOnAction(actionEvent -> {
            try (FileInputStream fileInputStream = new FileInputStream(DUMP_FILENAME)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                ForwardListWithType forwardListWithType = (ForwardListWithType) objectInputStream.readObject();

                selectedTypeProperty.setValue(forwardListWithType.getTypeName());

                forwardList = forwardListWithType.getForwardList();
                onItemCollectionChanged();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        saveButton.setOnAction(actionEvent -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(DUMP_FILENAME)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(new ForwardListWithType(selectedTypeProperty.getValue(), forwardList));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObjectFactory getObjectFactory() {
        return ObjectFactoryRegistry.getFactory(selectedTypeProperty.getValue());
    }

    private void onItemCollectionChanged() {
        if (forwardList == null) {
            itemListView.setItems(FXCollections.observableList(Collections.emptyList()));
            return;
        }

        itemListView.setItems(FXCollections.observableList(forwardList.toList()));
    }
}