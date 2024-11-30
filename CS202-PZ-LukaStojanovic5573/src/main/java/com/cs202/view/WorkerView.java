package com.cs203.view;

import com.cs203.controller.HotelController;
import com.cs203.controller.ReservationController;
import com.cs203.controller.RoomController;
import com.cs203.model.WorkerHotelMapping;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WorkerView implements RoleView {
    private final RoomController roomController = new RoomController();
    private final ReservationController reservationController = new ReservationController();
    private final HotelController hotelController = new HotelController();

    @Override
    public Pane getView() {
        VBox layout = new VBox(10);

        layout.getChildren().add(new Label("Worker Dashboard"));

        Button viewReservationsButton = new Button("View Reservations");
        // viewReservationsButton.setOnAction(e -> {
        // Integer hotelId = WorkerHotelMapping.getHotelForWorker(workerId);
        // if (hotelId != null) {
        // System.out.println("Viewing Reservations for Hotel ID: " + hotelId);
        // } else {
        // System.out.println("No hotel assigned to this worker.");
        // }
        // });
        layout.getChildren().add(viewReservationsButton);

        return layout;
    }

}
