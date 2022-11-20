package com.hamaddo.labforwardlist

import com.hamaddo.labforwardlist.factory.{ObjectFactory, ObjectFactoryRegistry}
import javafx.beans.property.SimpleStringProperty
import javafx.collections.{FXCollections, ObservableList}
import scalafx.scene.control.{Button, ChoiceBox, ListView, TextField}
import scalafxml.core.macros.sfxml

import java.util.Collections

@sfxml
class MainController(
                      private val typeChoiceBox: ChoiceBox[String],
                      private val inputTextField: TextField,
                      private val addButton: Button,
                      private val insertButton: Button,
                      private val removeButton: Button,
                      private val sortButton: Button,
                      private val loadButton: Button,
                      private val saveButton: Button,
                      private val itemListView: ListView[Object]) {
  private val typeObservableList: ObservableList[String] = FXCollections.observableList(ObjectFactoryRegistry.getAllFactories)
  private val selectedTypeProperty = new SimpleStringProperty(typeObservableList.get(0))

  typeChoiceBox.setItems(typeObservableList)
  typeChoiceBox.valueProperty().bindBidirectional(selectedTypeProperty)

  selectedTypeProperty.addListener((observableValue, old, value) => {
  })

  addButton.onAction = (actionEvent) => {
    println("Add Button")
  }

  insertButton.onAction = (actionEvent) => {
    println("Insert Button")
  }

  removeButton.onAction = (actionEvent) => {
    println("Insert Button")
  }

  sortButton.onAction = (actionEvent) => {
    println("Insert Button")
  }

  loadButton.onAction = (actionEvent) => {
    println("Insert Button")
  }

  saveButton.onAction = (actionEvent) => {
    println("Insert Button")
  }

  def getObjectFactory: ObjectFactory = {
    return ObjectFactoryRegistry.getFactory(selectedTypeProperty.getValue)
  }

  def onItemCollectionChanged: Unit = {
    itemListView.setItems(FXCollections.observableList(Collections.emptyList[Object]))
  }
}