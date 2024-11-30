package com.cs203;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import com.cs203.context.LoggedInUser;
import com.cs203.controller.UserController;
import com.cs203.enums.Roles;
import com.cs203.exceptions.DatabaseException;
import com.cs203.exceptions.InvalidCredentialsException;
import com.cs203.exceptions.ValidationException;
import com.cs203.model.User;
import com.cs203.view.MainSceneView;
import com.cs203.view.alerts.BadLoginAlert;
import com.cs203.view.alerts.DatabaseErrorAlert;
import com.cs203.view.alerts.EmptyLoginFieldsAlert;

public class App extends Application {
    private Stage primaryStage;
    private StackPane loginScene;

    private UserController userController;

    private StackPane createLoginScreen() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            try {
                boolean isLoggedIn = userController.login(usernameField.getText(), passwordField.getText());

                if (isLoggedIn)
                    primaryStage.setScene(new Scene(new MainSceneView(), 800, 600));
                else
                    throw new InvalidCredentialsException();
            } catch (ValidationException ex) {
                new EmptyLoginFieldsAlert().showAndWait();
            } catch (InvalidCredentialsException ex) {
                new BadLoginAlert().showAndWait();
            }
        });

        Button signupButton = new Button("Signup");
        signupButton.setOnAction(e -> {
            try {
                User user = userController.createUser(usernameField.getText(), passwordField.getText(), Roles.GUEST);
                if (user != null) {
                    LoggedInUser.getInstance().setUser(user);
                    primaryStage.setScene(new Scene(new MainSceneView(), 800, 600));
                } else
                    throw new DatabaseException();
            } catch (ValidationException ex) {
                new EmptyLoginFieldsAlert().showAndWait();
            } catch (DatabaseException ex) {
                new DatabaseErrorAlert().showAndWait();
            }
        });

        VBox inputFields = new VBox(10);
        inputFields.getChildren().addAll(usernameField, passwordField);

        HBox loginLayout = new HBox(10);
        loginLayout.getChildren().addAll(loginButton, signupButton);

        return new StackPane(new VBox(10, inputFields, loginLayout));
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        loginScene = createLoginScreen();

        userController = new UserController();

        primaryStage.setScene(new Scene(loginScene, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}