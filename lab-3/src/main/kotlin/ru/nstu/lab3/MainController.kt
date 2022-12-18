package ru.nstu.lab3

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import ru.nstu.lab3.factory.ObjectFactory
import ru.nstu.lab3.factory.ObjectFactoryRegistry
import ru.nstu.lab3.model.ForwardList
import java.io.*
import java.net.URL
import java.util.*

class MainController : Initializable {
    @FXML
    private lateinit var typeChoiceBox: ChoiceBox<String>

    @FXML
    private lateinit var inputTextField: TextField

    @FXML
    private lateinit var itemListView: ListView<Any>

    private val typeObservableList: ObservableList<String> = FXCollections.observableList(ObjectFactoryRegistry.allFactories)

    private lateinit var selectedTypeProperty: Property<String>
    private var forwardList: ForwardList<Any>? = null

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        selectedTypeProperty = SimpleStringProperty(typeObservableList[0])

        typeChoiceBox.items = typeObservableList
        typeChoiceBox.valueProperty().bindBidirectional(selectedTypeProperty)

        selectedTypeProperty.addListener { _: ObservableValue<out String>?, _: String?, _: String? ->
            forwardList = null
            onItemCollectionChanged()
        }
    }

    @FXML
    private fun onAddButtonClick() {
        if (forwardList == null) {
            forwardList = ForwardList()
        }

        forwardList!!.add(objectFactory.parse(inputTextField.text))
        onItemCollectionChanged()
    }

    @FXML
    private fun onInsertButtonClick() {
        val at = itemListView.selectionModel.selectedIndex

        forwardList!!.insert(at, objectFactory.parse(inputTextField.text))
        onItemCollectionChanged()
    }

    @FXML
    private fun onRemoveButtonClick() {
        val at = itemListView.selectionModel.selectedIndex

        forwardList!!.remove(at)
        onItemCollectionChanged()
    }

    @FXML
    private fun onSortButtonClick() {
        forwardList = forwardList!!.mergeSort(objectFactory.comparator)
        onItemCollectionChanged()
    }

    @FXML
    private fun onLoadButtonClick() {
        try {
            FileInputStream(DUMP_FILENAME).use { fileInputStream ->
                val objectInputStream = ObjectInputStream(fileInputStream)
                val factoryName = objectInputStream.readObject() as String
                selectedTypeProperty.value = factoryName

                forwardList = ForwardList()

                var eof = false
                while (!eof) {
                    try {
                        val readObject = objectInputStream.readObject()
                        (forwardList as ForwardList).add(readObject)
                    } catch (exception: EOFException) {
                        eof = true
                    }
                }
                onItemCollectionChanged()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
    }

    @FXML
    private fun onSaveButtonClick() {
        try {
            FileOutputStream(DUMP_FILENAME).use { fileOutputStream ->
                val objectOutputStream = ObjectOutputStream(fileOutputStream)
                val factoryName = objectFactory.objectName
                objectOutputStream.writeObject(factoryName)

                for (i in forwardList!!) {
                    objectOutputStream.writeObject(i)
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private val objectFactory: ObjectFactory<Any>
        get() = ObjectFactoryRegistry.getFactory(selectedTypeProperty.value)

    private fun onItemCollectionChanged() {
        if (forwardList == null) {
            itemListView.items = FXCollections.observableList(emptyList<Any>())
            return
        }

        itemListView.items = FXCollections.observableList(forwardList!!.toList())
    }

    companion object {
        private const val DUMP_FILENAME = "save.bin"
    }
}