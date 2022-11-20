package com.hamaddo.labforwardlist

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.io.IOException

object LabApplication extends JFXApp3 {
  override def start(): Unit = {

    val resource = getClass.getResource("main-view.fxml")
    if (resource == null) {
      throw new IOException("Cannot load resource: main-view.fxml")
    }

    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp3.PrimaryStage() {
      title = "Lab Forward List"
      scene = new Scene(root)
      resizable = false
    }
  }
}