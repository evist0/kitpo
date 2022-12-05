package com.hamaddo.labforwardlist

import com.hamaddo.labforwardlist.factory.{ObjectFactory, ObjectFactoryRegistry}
import com.hamaddo.labforwardlist.model.ForwardList
import javafx.beans.property.SimpleStringProperty
import javafx.collections.{FXCollections, ObservableList}
import scalafx.scene.control.{Button, ChoiceBox, ListView, TextField}
import scala.jdk.CollectionConverters._
import scalafxml.core.macros.sfxml
import java.io.{EOFException, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
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
  private val DUMP_FILENAME = "save.bin"

  private val typeObservableList: ObservableList[String] = FXCollections.observableList(ObjectFactoryRegistry.getAllFactories)
  private val selectedTypeProperty = new SimpleStringProperty(typeObservableList.get(0))

  private var forwardList = new ForwardList

  typeChoiceBox.setItems(typeObservableList)
  typeChoiceBox.valueProperty().bindBidirectional(selectedTypeProperty)

  selectedTypeProperty.addListener((_, _, _) => {
    forwardList = null
  })

  addButton.onAction = _ => {
    if (forwardList == null) {
      forwardList = new ForwardList
    }

    val value = inputTextField.getText
    val parsedValue = getObjectFactory.parse(value)

    forwardList.add(parsedValue)

    onItemCollectionChanged()
  }

  insertButton.onAction = _ => {
    val at = itemListView.getSelectionModel.getSelectedIndex

    forwardList.insert(at, getObjectFactory.parse(inputTextField.getText))
    onItemCollectionChanged()
  }

  removeButton.onAction = _ => {
    val at = itemListView.getSelectionModel.getSelectedIndex

    forwardList.remove(at)
    onItemCollectionChanged()
  }

  sortButton.onAction = _ => {
    forwardList.sort(getObjectFactory.getComparator)
    onItemCollectionChanged()
  }

  loadButton.onAction = _ => {
    try {
      val fileInputStream = new FileInputStream(DUMP_FILENAME)

      try {
        val objectInputStream = new ObjectInputStream(fileInputStream)

        val factoryName = objectInputStream.readObject.asInstanceOf[String]
        selectedTypeProperty.setValue(factoryName)

        forwardList = new ForwardList

        var eof = false
        while (!eof) try {
          val readObject = objectInputStream.readObject
          forwardList.add(readObject)
        } catch {
          case _: EOFException =>
            eof = true
        }

        onItemCollectionChanged()
      } catch {
        case e@(_: IOException | _: ClassNotFoundException) =>
          throw new RuntimeException(e)
      } finally {
        if (fileInputStream != null) {
          fileInputStream.close()
        }
      }
    }
  }

  saveButton.onAction = _ => {
    try {
      val fileOutputStream = new FileOutputStream(DUMP_FILENAME)

      try {
        val objectOutputStream = new ObjectOutputStream(fileOutputStream)

        val factoryName = getObjectFactory.getObjectName
        objectOutputStream.writeObject(factoryName)

        for (i <- forwardList) {
          objectOutputStream.writeObject(i)
        }
      } catch {
        case e: IOException =>
          throw new RuntimeException(e)
      } finally {
        if (fileOutputStream != null) {
          fileOutputStream.close()
        }
      }
    }
  }

  private def getObjectFactory: ObjectFactory = {
    ObjectFactoryRegistry.getFactory(selectedTypeProperty.getValue)
  }

  private def onItemCollectionChanged(): Unit = {
    if (forwardList == null) {
      itemListView.setItems(FXCollections.observableList(Collections.emptyList[Object]))
      return
    }

    itemListView.setItems(FXCollections.observableList(forwardList.toList.asJava))
  }
}
