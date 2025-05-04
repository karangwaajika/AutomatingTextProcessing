package org.example.automatictextprocessing;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.example.automatictextprocessing.exceptions.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

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
    public ComboBox dataTypeComboBox;
    @FXML
    public TextField regexField;
    @FXML
    public TextField separatorField;
    @FXML
    public TextArea textArea;
    @FXML
    public Button cleanButton;
    @FXML
    public Button cleaningButtonSubmit;
    @FXML
    public Button replaceButtonSubmit;
    @FXML
    public Button searchButtonSubmit;
    @FXML
    public TextField replaceByField;
    @FXML
    public TextField fileField;
    @FXML
    public HBox fileContents;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> maritalStatusComboBox;
    @FXML
    private TextField ageField;
    @FXML
    private TextField nationalIdField;
    @FXML
    private CheckBox isEmployedCheckBox;

    // table fields
    @FXML
    private TableView<Woman> womanTable;
    @FXML
    private TableColumn<Woman, String> nameColumn;
    @FXML
    private TableColumn<Woman, String> nationalIdColumn;
    @FXML
    private TableColumn<Woman, Integer> ageColumn;
    @FXML
    private TableColumn<Woman, Boolean> employedColumn;
    @FXML
    private TableColumn<Woman, String> maritalStatusColumn;
    @FXML
    private TableColumn<Single, String> relationshipColumn;
    @FXML
    private TableColumn<Woman, String> marriageDateColumn;
    @FXML
    private TableColumn<Woman, String> divorcedDateColumn;
    @FXML
    private TableColumn<Woman, String> spouseDeathDateColumn;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // db initialization
    private final Database db = new Database();
    private final ObservableList<Woman> womenList = FXCollections
            .observableArrayList(db.getAllWomen());

    @FXML
    private void handleSubmit() {
        try {
            String name = nameField.getText();
            if (!(name.matches("^[a-zA-Z\\s]+$"))) {
                throw new InvalidNameException("Name is invalid");
            }
            String maritalStatus = maritalStatusComboBox.getValue();
            if (!(Pattern.matches("\\d+", ageField.getText()))) {
                throw new InvalidAgeException("Age is invalid");
            }
            if (!(Pattern.matches("\\d+", nationalIdField.getText()))) {
                throw new InvalidNationaldException("National ID is invalid");
            }
            if (nationalIdField.getText().length() != 16) {
                throw new InvalidAgeException("National ID must be 16 digits long");
            }
            int age = Integer.parseInt(ageField.getText());
            String nationalId = nationalIdField.getText();
            boolean isEmployed = isEmployedCheckBox.isSelected();
            String dateDivorced = divorcedDateField.getValue() != null ? divorcedDateField.getValue().toString() : "-";
            String dateMarried = marriageDateField.getValue() != null ? marriageDateField.getValue().toString() : "-";
            String spouseDeathDate = spouseDateField.getValue() != null ? spouseDateField.getValue().toString() : "-";
            boolean isInRelationship = isInRelationshipCheckBox.isSelected();


            // add woman to Database(Hashmap)
            Woman woman = createWoman(nationalId, maritalStatus, name, age, isEmployed, isInRelationship,
                    dateMarried, dateDivorced, spouseDeathDate);
            db.addWoman(woman.getWomanId(), woman);
            womenList.setAll(db.getAllWomen()); // make table auto-refresh

            showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "✅ Woman added Successfully: " + woman.getName());
            System.out.println("✅ Woman added: " + woman.getName());
            clearForm();

        } catch (NotEmptyDateDivorcedException | NotEmptyDateMarriedException |
                 NotEmptyMaritalStatusException | NotEmptyNameException |
                 NotEmptySpouseDeathDateException | InvalidAgeException | UnderAgeException e) {
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
        // initialize field on table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        employedColumn.setCellValueFactory(new PropertyValueFactory<>("isEmployed"));
        maritalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
        relationshipColumn.setCellValueFactory(cellData -> {
            Woman w = cellData.getValue();
            if (w instanceof Single single) {
                return new SimpleStringProperty(Boolean.toString(single.getRelationship()));
            } else {
                return new SimpleStringProperty("-"); // blank for non-single
            }
        });
        marriageDateColumn.setCellValueFactory(cellData -> {
            Woman w = cellData.getValue();
            if (w instanceof Married married) {
                return new SimpleStringProperty(married.getDateMarried());
            } else {
                return new SimpleStringProperty("-"); // blank for non-marriage
            }
        });
        divorcedDateColumn.setCellValueFactory(cellData -> {
            Woman w = cellData.getValue();
            if (w instanceof Divorced divorced) {
                return new SimpleStringProperty(divorced.getDateDivorced());
            } else {
                return new SimpleStringProperty("-"); // blank for non-divorced
            }
        });
        spouseDeathDateColumn.setCellValueFactory(cellData -> {
            Woman w = cellData.getValue();
            if (w instanceof Widowed widowed) {
                return new SimpleStringProperty(widowed.getSpouseDeathDate());
            } else {
                return new SimpleStringProperty("-"); // blank for non-widowed
            }
        });

        womanTable.setItems(womenList); // refresh table for new record

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
    private Woman createWoman(String nationalId, String maritalStatus, String name, int age, boolean isEmployed,
                              boolean isInRelationship, String dateMarried, String dateDivorced,
                              String spouseDeathDate) {
        return switch (maritalStatus) {
            case "Single" -> new Single(nationalId, Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, isInRelationship);
            case "Married" -> new Married(nationalId, Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, dateMarried);
            case "Divorced" -> new Divorced(nationalId, Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, dateDivorced);
            default -> new Widowed(nationalId, Woman.womanNbr, name, age,
                    maritalStatus, isEmployed, spouseDeathDate);
        };
    }

    public void showCleanContent(ActionEvent actionEvent) {
        // enable common fields
        separatorField.setVisible(true);
        separatorField.setManaged(true);

        cleaningButtonSubmit.setVisible(true);
        cleaningButtonSubmit.setManaged(true);

        // disable other buttons' contents
        replaceByField.setVisible(false);
        replaceByField.setManaged(false);

        regexField.setVisible(false);
        regexField.setManaged(false);

        replaceButtonSubmit.setVisible(false);
        replaceButtonSubmit.setManaged(false);

        searchButtonSubmit.setVisible(false);
        searchButtonSubmit.setManaged(false);

        if (dataTypeComboBox.getValue().equals("Text")) {

            textArea.setVisible(true);
            textArea.setManaged(true);

            //disable file contents
            fileContents.setVisible(false);
            fileContents.setManaged(false);

        }else{

            fileContents.setVisible(true);
            fileContents.setManaged(true);

            // disable text contents
            textArea.setVisible(false);
            textArea.setManaged(false);

        }
    }

    public void showReplaceContent(ActionEvent actionEvent) {
        // enable common fields
        regexField.setVisible(true);
        regexField.setManaged(true);

        replaceByField.setVisible(true);
        replaceByField.setManaged(true);

        replaceButtonSubmit.setVisible(true);
        replaceButtonSubmit.setManaged(true);

        // disable other buttons' contents
        separatorField.setVisible(false);
        separatorField.setManaged(false);

        cleaningButtonSubmit.setVisible(false);
        cleaningButtonSubmit.setManaged(false);

        searchButtonSubmit.setVisible(false);
        searchButtonSubmit.setManaged(false);


        if (dataTypeComboBox.getValue().equals("Text")) {
            textArea.setVisible(true);
            textArea.setManaged(true);

            //disable file contents
            fileContents.setVisible(false);
            fileContents.setManaged(false);

        }else{
            fileContents.setVisible(true);
            fileContents.setManaged(true);

            // disable text contents
            textArea.setVisible(false);
            textArea.setManaged(false);

        }
    }

    public void showSearchContent(ActionEvent actionEvent) {
    }

    public void handleBrowseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Get the window from the event
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileField.setText(selectedFile.getAbsolutePath());
        }
    }
}