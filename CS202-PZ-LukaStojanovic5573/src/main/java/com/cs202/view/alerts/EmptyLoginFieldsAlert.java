package com.cs203.view.alerts;

import javafx.scene.control.Alert;

public class EmptyLoginFieldsAlert extends Alert {
    public EmptyLoginFieldsAlert() {
        super(AlertType.ERROR);
        this.setTitle("Login Failed");
        this.setHeaderText(null);
        this.setContentText("Login or password field is empty.");
    }
}
