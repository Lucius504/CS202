package com.cs203.view.alerts;

import javafx.scene.control.Alert;

public class DatabaseErrorAlert extends Alert {
    public DatabaseErrorAlert() {
        super(AlertType.ERROR);
        this.setTitle("Login Failed");
        this.setHeaderText(null);
        this.setContentText("Database error occurred.");
    }
}
