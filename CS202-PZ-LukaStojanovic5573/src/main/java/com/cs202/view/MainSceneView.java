package com.cs203.view;

import com.cs203.context.LoggedInUser;

import javafx.scene.layout.StackPane;

public class MainSceneView extends StackPane {
    public MainSceneView() {
        super();

        this.getChildren().clear();
        switch (LoggedInUser.getInstance().getUser().getRole()) {
            case ADMIN:
                this.getChildren().add(new AdminView().getView());
                break;
            case WORKER:
                this.getChildren().add(new WorkerView().getView());
                break;
            case GUEST:
                this.getChildren().add(new GuestView().getView());
                break;
        }
    }
}
