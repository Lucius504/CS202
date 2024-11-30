package com.cs203.view;

import com.cs203.controller.HotelController;
import com.cs203.controller.ReservationController;
import com.cs203.controller.RoomController;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GuestView implements RoleView {
    private final RoomController roomController = new RoomController();
    private final ReservationController reservationController = new ReservationController();
    private final HotelController hotelController = new HotelController();

    @Override
    public Pane getView() {
        VBox layout = new VBox(10);

        layout.getChildren().add(new Label("Guest Dashboard"));

        Button viewHotelsButton = new Button("View Hotels");
        viewHotelsButton.setOnAction(e -> System.out.println("Viewing Hotels"));
        layout.getChildren().add(viewHotelsButton);

        Button viewRoomsButton = new Button("View Rooms");
        viewRoomsButton.setOnAction(e -> System.out.println("Viewing Rooms"));
        layout.getChildren().add(viewRoomsButton);

        Button addReservationButton = new Button("Add Reservation");
        addReservationButton.setOnAction(e -> System.out.println("Adding a Reservation"));
        layout.getChildren().add(addReservationButton);

        return layout;
    }
}
