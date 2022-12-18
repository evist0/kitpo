package ru.nstu.lab3

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException

class LabApplication : Application() {
    @Throws(IOException::class)
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(LabApplication::class.java.getResource("main-view.fxml"))

        val scene = Scene(fxmlLoader.load(), 460.0, 440.0)

        stage.title = "Lab Forward List"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}

fun main() {
    Application.launch(LabApplication::class.java)
}