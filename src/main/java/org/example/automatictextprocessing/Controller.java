package org.example.automatictextprocessing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.example.automatictextprocessing.exceptions.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Controller {
    // input fields
    @FXML
    public DatePicker marriageDateField;
    @FXML
    public CheckBox isInRelationshipCheckBox;
    @FXML
    public DatePicker spouseDateField;
    @FXML
    public DatePicker divorcedDateField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> maritalStatusComboBox;
    @FXML
    private TextField ageField;
    @FXML
    private CheckBox isEmployedCheckBox;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // db initialization
    private final Database db = new Database();
    private final ObservableList<Woman> womenList = FXCollections
            .observableArrayList(db.getAllWomen());

    @FXML
    private void handleSubmit() {
        try {
            String name = nameField.getText();
            String maritalStatus = maritalStatusComboBox.getValue();
            int age = Integer.parseInt(ageField.getText());
            boolean isEmployed = isEmployedCheckBox.isSelected();
            String dateDivorced = divorcedDateField.toString();
            String dateMarried = marriageDateField.toString();
            String spouseDeathDate = spouseDateField.toString();
            boolean isInRelationship = isInRelationshipCheckBox.isSelected();


            // add woman to Database(Hashmap)
            Woman woman = createWoman(maritalStatus, name, age, isEmployed, isInRelationship,
                    dateMarried, dateDivorced, spouseDeathDate);
            db.addWoman(woman.getWomanId(), woman);

            womenList.setAll(db.getAllWomen()); // make table auto-refresh

            showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "✅ Woman added Successfully: " + woman.getName());
            System.out.println("✅ Woman added: " + woman.getName());
            clearForm();

        } catch (NotEmptyDateDivorcedException | NotEmptyDateMarriedException |
                 NotEmptyMaritalStatusException | NotEmptyNameException |
                 NotEmptySpouseDeathDateException | UnderAgeException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "⚠️ Error: " + e.getMessage());
            return;
        }
    }

    private void clearForm() {
        nameField.clear();
        ageField.clear();
        isEmployedCheckBox.setSelected(false);
        isInRelationshipCheckBox.setSelected(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void initialize() {
        // handle field visibility
        maritalStatusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            marriageDateField.setVisible("Married".equals(newVal));
            marriageDateField.setManaged("Married".equals(newVal));
        });
        marriageDateField.setDayCellFactory(getPastOnlyFactory());

        maritalStatusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            isInRelationshipCheckBox.setVisible("Single".equals(newVal));
            isInRelationshipCheckBox.setManaged("Single".equals(newVal));
        });

        maritalStatusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            spouseDateField.setVisible("Widowed".equals(newVal));
            spouseDateField.setManaged("Widowed".equals(newVal));
        });
        spouseDateField.setDayCellFactory(getPastOnlyFactory());

        maritalStatusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            divorcedDateField.setVisible("Divorced".equals(newVal));
            divorcedDateField.setManaged("Divorced".equals(newVal));
        });
        divorcedDateField.setDayCellFactory(getPastOnlyFactory());

        // date format
        marriageDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });
        marriageDateField.setPromptText("yyyy-MM-dd");

        divorcedDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });
        divorcedDateField.setPromptText("yyyy-MM-dd");

        spouseDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });
        spouseDateField.setPromptText("yyyy-MM-dd");
    }

    // disable future dates
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

    // create woman object
    private Woman createWoman(String maritalStatus, String name, int age, boolean isEmployed,
                              boolean isInRelationship, String dateMarried, String dateDivorced,
                              String spouseDeathDate) {
        return switch (maritalStatus) {
            case "Single" -> new Single(Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, isInRelationship);
            case "Married" -> new Married(Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, dateMarried);
            case "Divorced" -> new Divorced(Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, dateDivorced);
            default -> new Widowed(Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, spouseDeathDate);
        };
    }
}