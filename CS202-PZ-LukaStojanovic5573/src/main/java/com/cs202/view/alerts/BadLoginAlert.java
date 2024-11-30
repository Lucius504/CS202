package com.cs203.view.alerts;

import javafx.scene.control.Alert;

public class BadLoginAlert extends Alert {
    public BadLoginAlert() {
        super(AlertType.ERROR);
        this.setTitle("Login Failed");
        this.setHeaderText(null);
        this.setContentText("Invalid username or password.");
    }
}
