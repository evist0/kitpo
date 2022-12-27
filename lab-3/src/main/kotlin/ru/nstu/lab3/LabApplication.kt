package ru.nstu.lab3

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException
import ru.nstu.lab3.ListPerformanceTest

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
    ListPerformanceTest.test(100000)
    ListPerformanceTest.test(150000)
    ListPerformanceTest.test(200000)
    ListPerformanceTest.test(250000)
    ListPerformanceTest.test(300000)
    ListPerformanceTest.test(350000)
    ListPerformanceTest.test(400000)
    ListPerformanceTest.test(450000)
    ListPerformanceTest.test(500000)

    //Application.launch(LabApplication::class.java)
}