package com.cs203.view;

import java.text.SimpleDateFormat;

import com.cs203.controller.*;
import com.cs203.model.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminView implements RoleView {
    private final RoomController roomController = new RoomController();
    private final ReservationController reservationController = new ReservationController();
    private final HotelController hotelController = new HotelController();
    private final UserController userController = new UserController();

    private ObservableList<Hotel> hotelList = FXCollections.observableArrayList();
    private ObservableList<Room> roomList = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();
    private ObservableList<User> workersList = FXCollections.observableArrayList();

    private SimpleObjectProperty<Hotel> selectedHotelProperty = new SimpleObjectProperty<>(null);

    private TabPane tabPane;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Pane getView() {
        tabPane = new TabPane();
        tabPane.setStyle("-fx-tab-max-height: -1; -fx-tab-min-height: -1;"); // hide tabs header
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab hotelsTab = new Tab(); // 0 - hotels
        Tab hotelMenuTab = new Tab(); // 1 - hotel menu
        Tab hotelDetailTab = new Tab(); // 2 - hotel details
        Tab hotelRoomsTab = new Tab(); // 3 - rooms
        Tab hotelReservationsTab = new Tab(); // 4 - reservations
        Tab hotelWorkersTab = new Tab(); // 5 - workers

        // hotelsTab.setId("hotels");
        // hotelMenuTab.setId("hotel_menu");
        // hotelDetailTab.setId("hotel_details");
        // hotelRoomsTab.setId("hotel_rooms");
        // hotelReservationsTab.setId("hotel_reservations");
        // hotelWorkersTab.setId("hotel_workers");

        hotelsTab.setContent(hotelsTabContext());
        hotelMenuTab.setContent(hotelMenuTabContext());

        tabPane.getTabs().addAll(hotelsTab, hotelMenuTab, hotelDetailTab, hotelRoomsTab, hotelReservationsTab,
                hotelWorkersTab);

        // tabPane.getSelectionModel().selectedIndexProperty().addListener((observable,
        // oldValue, newValue) -> {
        // switch (newValue.intValue()) {
        // case 0:
        // hotelList =
        // FXCollections.observableArrayList(hotelController.getAllHotels());
        // break;
        // case 3:
        // roomList = FXCollections
        // .observableArrayList(roomController.getRoomsByHotel(selectedHotelProperty.get().getId()));
        // break;
        // case 4:
        // reservationsList = FXCollections
        // .observableArrayList(reservationController
        // .getAllReservationsByHotel(selectedHotelProperty.get().getId()));
        // break;
        // case 5:
        // workersList = FXCollections
        // .observableArrayList(userController.getWorkersByHotel(selectedHotelProperty.get().getId()));
        // break;

        // default:
        // break;
        // }
        // });

        selectedHotelProperty.addListener((observable, oldHotel, newHotel) -> {
            if (newHotel != null) {
                hotelDetailTab.setContent(hotelDetailsTabContext());
                hotelRoomsTab.setContent(hotelRoomsTabContext());
                hotelReservationsTab.setContent(hotelReservationsTabContext());
                hotelWorkersTab.setContent(hotelWorkersTabContext());

                tabPane.getSelectionModel().select(1);
            } else
                tabPane.getSelectionModel().select(0);
        });

        return new VBox(10, tabPane);
    }

    private Pane hotelsTabContext() {
        TableView<Hotel> hotelTable = new TableView<>();
        hotelTable.setPrefWidth(600);
        hotelTable.setEditable(false);

        TableColumn<Hotel, String> hotelNameColumn = new TableColumn<>("Hotel Name");
        hotelNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Hotel, String> hotelLocationColumn = new TableColumn<>("Address");
        hotelLocationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Hotel, String> hotelPhoneColumn = new TableColumn<>("Phone");
        hotelPhoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

        hotelTable.getColumns().add(hotelNameColumn);
        hotelTable.getColumns().add(hotelLocationColumn);
        hotelTable.getColumns().add(hotelPhoneColumn);

        hotelList = FXCollections.observableArrayList(hotelController.getAllHotels());
        hotelTable.setItems(hotelList);
        hotelTable.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY)
                selectedHotelProperty.setValue(hotelTable.getSelectionModel().getSelectedItem());
        });

        return new StackPane(hotelTable);
    }

    private Pane hotelMenuTabContext() {
        Button manageHotelButton = new Button("Manage Hotel Information");
        manageHotelButton.setOnAction(e -> tabPane.getSelectionModel().select(2));

        Button manageRoomsButton = new Button("Manage Rooms");
        manageRoomsButton.setOnAction(e -> tabPane.getSelectionModel().select(3));

        Button manageReservationsButton = new Button("Manage Reservations");
        manageReservationsButton.setOnAction(e -> tabPane.getSelectionModel().select(4));

        Button manageWorkersButton = new Button("Manage Workers");
        manageWorkersButton.setOnAction(e -> tabPane.getSelectionModel().select(5));

        VBox context = new VBox(manageHotelButton, manageRoomsButton, manageReservationsButton, manageWorkersButton);

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> selectedHotelProperty.set(null));

        HBox wrapper = new HBox(backButton, context);
        wrapper.setAlignment(Pos.TOP_LEFT);

        return wrapper;
    }

    private Pane hotelDetailsTabContext() {
        Label hotelNameLabel = new Label("Hotel Name:");
        Label hotelAddressLabel = new Label("Address:");

        TextField hotelNameField = new TextField();
        TextField hotelAddressField = new TextField();

        hotelNameField.setText("-");
        hotelAddressField.setText("-");

        hotelNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Hotel Name changed from " + oldValue + " to " + newValue);
        });

        hotelAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Hotel Address changed from " + oldValue + " to " + newValue);
        });

        VBox context = new VBox(10,
                hotelNameLabel, hotelNameField,
                hotelAddressLabel, hotelAddressField);
        context.setPrefWidth(600);

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> tabPane.getSelectionModel().select(1));

        HBox wrapper = new HBox(backButton, context);
        wrapper.setAlignment(Pos.TOP_LEFT);

        return wrapper;
    }

    private Pane hotelRoomsTabContext() {
        TableView<Room> roomsTable = new TableView<>();
        roomsTable.setPrefWidth(600);
        roomsTable.setEditable(false);

        TableColumn<Room, String> roomNumberColumn = new TableColumn<>("Room number");
        roomNumberColumn.setCellValueFactory(data -> {
            String val = String.valueOf(data.getValue().getRoomNumber());
            return new SimpleStringProperty(val);
        });

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room type");
        roomTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoomType()));

        TableColumn<Room, String> roomPriceColumn = new TableColumn<>("Room price");
        roomPriceColumn.setCellValueFactory(data -> {
            String val = String.valueOf(data.getValue().getPrice());
            return new SimpleStringProperty(val);
        });

        TableColumn<Room, String> roomAvailabilityColumn = new TableColumn<>("Room availability");
        roomAvailabilityColumn.setCellValueFactory(data -> {
            String val = data.getValue().isAvailabilityStatus() ? "Available" : "Not available";
            return new SimpleStringProperty(val);
        });

        TableColumn<Room, String> roomHotelColumn = new TableColumn<>("Hotel");
        roomHotelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHotel().getName()));

        roomsTable.getColumns().add(roomNumberColumn);
        roomsTable.getColumns().add(roomTypeColumn);
        roomsTable.getColumns().add(roomPriceColumn);
        roomsTable.getColumns().add(roomAvailabilityColumn);
        roomsTable.getColumns().add(roomHotelColumn);

        roomList = FXCollections
                .observableArrayList(roomController.getRoomsByHotel(selectedHotelProperty.get().getId()));
        roomsTable.setItems(roomList);
        roomsTable.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                ContextMenu contextMenu = new ContextMenu();

                MenuItem editMenuItem = new MenuItem("Edit");
                editMenuItem.setOnAction(event -> {
                    Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
                    System.out.println("Editing hotel: " + selectedRoom.getRoomNumber());
                });

                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(event -> {
                    Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
                    if (selectedRoom == null)
                        return;

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this room?");
                    alert.setContentText("This action cannot be undone.");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            roomList.remove(selectedRoom);
                            roomController.deleteRoom(selectedRoom.getId());
                        }
                    });
                });

                roomsTable.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
                contextMenu.show(roomsTable, e.getScreenX(), e.getScreenY());
            }
        });

        VBox context = new VBox(10, roomsTable);
        context.setPrefWidth(600);

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> tabPane.getSelectionModel().select(1));

        HBox wrapper = new HBox(backButton, context);
        wrapper.setAlignment(Pos.TOP_LEFT);

        return wrapper;
    }

    private Pane hotelReservationsTabContext() {
        TableView<Reservation> reservationsTable = new TableView<>();
        reservationsTable.setPrefWidth(600);
        reservationsTable.setEditable(false);

        TableColumn<Reservation, String> reservationRoomColumn = new TableColumn<>("Room number");
        reservationRoomColumn.setCellValueFactory(data -> {
            String val = String.valueOf(data.getValue().getRoom().getRoomNumber());
            return new SimpleStringProperty(val);
        });

        TableColumn<Reservation, String> reservationGuestColumn = new TableColumn<>("Guest");
        reservationGuestColumn
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser().getUsername()));

        TableColumn<Reservation, String> reservationDateColumn = new TableColumn<>("Reservation date");
        reservationDateColumn.setCellValueFactory(data -> {
            String val = formatter.format(data.getValue().getReservationDate());
            return new SimpleStringProperty(val);
        });

        TableColumn<Reservation, String> checkInDateColumn = new TableColumn<>("Check in date");
        checkInDateColumn.setCellValueFactory(data -> {
            String val = formatter.format(data.getValue().getCheckInDate());
            return new SimpleStringProperty(val);
        });

        TableColumn<Reservation, String> checkOutDateColumn = new TableColumn<>("Check out date");
        checkOutDateColumn.setCellValueFactory(data -> {
            String val = formatter.format(data.getValue().getCheckOutDate());
            return new SimpleStringProperty(val);
        });

        reservationsTable.getColumns().add(reservationRoomColumn);
        reservationsTable.getColumns().add(reservationGuestColumn);
        reservationsTable.getColumns().add(reservationDateColumn);
        reservationsTable.getColumns().add(checkInDateColumn);
        reservationsTable.getColumns().add(checkOutDateColumn);

        reservationsList = FXCollections
                .observableArrayList(
                        reservationController.getAllReservationsByHotel(selectedHotelProperty.get().getId()));
        reservationsTable.setItems(reservationsList);
        reservationsTable.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                ContextMenu contextMenu = new ContextMenu();

                MenuItem editMenuItem = new MenuItem("Edit");
                editMenuItem.setOnAction(event -> {
                    Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();
                    System.out.println("Editing hotel: " + selectedReservation.getHotel().getName());
                });

                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(event -> {
                    Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();
                    if (selectedReservation == null)
                        return;

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this reservation?");
                    alert.setContentText("This action cannot be undone.");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK)
                            reservationsList.remove(selectedReservation);
                    });
                });

                reservationsTable.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
                contextMenu.show(reservationsTable, e.getScreenX(), e.getScreenY());
            }
        });

        VBox context = new VBox(10, reservationsTable);
        context.setPrefWidth(600);

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> tabPane.getSelectionModel().select(1));

        HBox wrapper = new HBox(backButton, context);
        wrapper.setAlignment(Pos.TOP_LEFT);

        return wrapper;
    }

    private Pane hotelWorkersTabContext() {
        TableView<User> workersTable = new TableView<>();
        workersTable.setPrefWidth(600);
        workersTable.setEditable(false);

        TableColumn<User, String> workerIdColumn = new TableColumn<>("Worker ID");
        workerIdColumn.setCellValueFactory(data -> {
            String val = String.valueOf(data.getValue().getId());
            return new SimpleStringProperty(val);
        });

        TableColumn<User, String> workerNameColumn = new TableColumn<>("Name");
        workerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));

        workersTable.getColumns().add(workerIdColumn);
        workersTable.getColumns().add(workerNameColumn);

        workersList = FXCollections
                .observableArrayList(userController.getWorkersByHotel(selectedHotelProperty.get().getId()));
        workersTable.setItems(workersList);
        workersTable.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                ContextMenu contextMenu = new ContextMenu();

                MenuItem editMenuItem = new MenuItem("Edit");
                editMenuItem.setOnAction(event -> {
                    User selectedWorker = workersTable.getSelectionModel().getSelectedItem();
                    System.out.println("Editing hotel: " + selectedWorker.getUsername());
                });

                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(event -> {
                    User selectedWorker = workersTable.getSelectionModel().getSelectedItem();

                    if (selectedWorker == null)
                        return;

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this worker?");
                    alert.setContentText("This action cannot be undone.");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK)
                            workersList.remove(selectedWorker);
                    });
                });

                workersTable.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
                contextMenu.show(workersTable, e.getScreenX(), e.getScreenY());
            }
        });

        VBox context = new VBox(10, workersTable);
        context.setPrefWidth(600);

        Button backButton = new Button("<-");
        backButton.setOnAction(e -> tabPane.getSelectionModel().select(1));

        HBox wrapper = new HBox(backButton, context);
        wrapper.setAlignment(Pos.TOP_LEFT);

        return wrapper;
    }
}
