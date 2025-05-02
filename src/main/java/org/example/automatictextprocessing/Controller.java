package org.example.automatictextprocessing;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.time.LocalDate;

public class Controller {
    @FXML
    public DatePicker marriageDateField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> maritalStatusComboBox;
    @FXML
    private TextField ageField;
    @FXML
    private CheckBox isEmployedCheckBox;


    @FXML
    public void initialize() {
        maritalStatusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            marriageDateField.setVisible("Married".equals(newVal));
        });
        marriageDateField.setDayCellFactory(getPastOnlyFactory());
    }

    private Callback<DatePicker, DateCell> getPastOnlyFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disable all future dates
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }
}