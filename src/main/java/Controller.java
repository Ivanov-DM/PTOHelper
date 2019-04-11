import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import ru.ivanov.pto_helper.model.AOSRContent;
import ru.ivanov.pto_helper.model.AOSRForUI;
import ru.ivanov.pto_helper.model.ExcelParser;
import ru.ivanov.pto_helper.model.WordProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    TextField templatePathField, excelPathField, savePathField, textFieldAOSRRange;

    @FXML
    TextField filterField;

    @FXML
    Button selectBtn;

    @FXML
    VBox majorVbox;

    @FXML
    TableView<AOSRForUI> tableView;

    @FXML
    Label tipLabel;

    @FXML
    TableColumn<AOSRForUI, Integer> numColumn;

    @FXML
    TableColumn<AOSRForUI, String> aosrNumColumn, rdNumColumn, workNameColumn;

    String saveDirectoryPath;
    String templateFilePath;
    String excelFilePath;
    ArrayList<AOSRContent> aosrContenList = new ArrayList<>();
    private ObservableList<AOSRForUI> masterTableData = FXCollections.observableArrayList();
    private ObservableList<AOSRForUI> filteredTableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Колонка порядковый номер акта в таблице
        numColumn.setCellValueFactory(new PropertyValueFactory<AOSRForUI, Integer>("num"));
        numColumn.setStyle("-fx-alignment: CENTER;");

        // Колонка номер акта
        aosrNumColumn.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("aosrNum"));
        aosrNumColumn.setStyle("-fx-alignment: CENTER;");

        // Колонка шифр РД
        rdNumColumn.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("rdNum"));
        rdNumColumn.setStyle("-fx-alignment: CENTER;");
        rdNumColumn.setCellFactory(new Callback<TableColumn<AOSRForUI, String>, TableCell<AOSRForUI, String>>() {
            @Override
            public TableCell<AOSRForUI, String> call(
                    TableColumn<AOSRForUI, String> param) {
                TableCell<AOSRForUI, String> cell = new TableCell<AOSRForUI, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Text text = new Text();
                        text.setFont(Font.font("Segoe UI Semibold",14));
                        text.setFill(Color.WHITE);
                        setGraphic(text);
                        setPrefHeight(Control.USE_COMPUTED_SIZE);
                        text.wrappingWidthProperty().bind(this.widthProperty());
                        text.textProperty().bind(this.itemProperty());
                        getStylesheets().clear();
                        setStyle("-fx-text-fill: white; -fx-alignment: center;");
                        setText(item);
                    }
                };
                return cell ;
            }
        });

        // Колонка наименование работ, указанных в акте.
        workNameColumn.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("workName"));
        workNameColumn.setCellFactory(new Callback<TableColumn<AOSRForUI, String>, TableCell<AOSRForUI, String>>() {
            @Override
            public TableCell<AOSRForUI, String> call(
                    TableColumn<AOSRForUI, String> param) {

                TableCell<AOSRForUI, String> cell = new TableCell<AOSRForUI, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Text text = new Text();
                        text.setFont(Font.font("Segoe UI Semibold",14));
                        text.setFill(Color.WHITE);
                        setGraphic(text);
                        setPrefHeight(Control.USE_COMPUTED_SIZE);
                        text.wrappingWidthProperty().bind(this.widthProperty());
                        text.textProperty().bind(this.itemProperty());
                        getStylesheets().clear();
                        setStyle("-fx-text-fill: white; -fx-alignment:");
                            setText(item);
                    }
                };
                return cell ;
            }
        });

        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        numColumn.setMaxWidth(1f * Integer.MAX_VALUE * 5); // 5% width
        aosrNumColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        rdNumColumn.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 25% width
        workNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width

//        Tooltip tooltip = new Tooltip();
//        tooltip.setText("Введите номера или диапазоны актов, разделенные запятыми");
//        tipLabel.setTooltip(tooltip);

        majorVbox.setPadding(new Insets(30, 40, 30, 40));
        majorVbox.setSpacing(20);

        filterField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateFilteredTableData();
            }
        });
    }

    @FXML
    private void prepareTableDataset(){
        for (int i = 0; i < aosrContenList.size(); i++) {
            AOSRContent ac = aosrContenList.get(i);
            masterTableData.add(new AOSRForUI(i + 1, ac.getAOSRNum(), ac.getRDNum(), ac.getWorkName()));
        }
        filteredTableData.addAll(masterTableData);
        tableView.setItems(filteredTableData);
    }

    private void updateFilteredTableData() {
        filteredTableData.clear();
        for (AOSRForUI o : masterTableData) {
            if (matchesFilter(o)) {
                filteredTableData.add(o);
            }
        }
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(AOSRForUI aosr) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.length() == 0) {
            return true;
        }
        String lowerCaseFilterString = filterString.toLowerCase();
        if (aosr.getRDNum().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<AOSRForUI, ?>> sortOrder = new ArrayList<>(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
    }

    //получаем путь к excel файлу
    public void selectExcelFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл ОЖР");
        File file = fileChooser.showOpenDialog(Main.mainFX);
        excelFilePath = file.getAbsolutePath();
        excelPathField.setText(file.getAbsolutePath());
        try {
            ExcelParser excelParser = new ExcelParser(excelFilePath);
            aosrContenList = excelParser.getAOSRContentListNew();
            if (aosrContenList.isEmpty()) {
                Alert aosrContentAlert = new Alert(Alert.AlertType.INFORMATION);
                aosrContentAlert.setHeaderText("Ошибка");
                aosrContentAlert.setContentText("PTO Helper не удается распознать файл " + excelFilePath + '\n'
                                                + "Выберите правильный файл!");
                aosrContentAlert.showAndWait();
            } else {
                prepareTableDataset();
            }
        } catch (IOException e) {
            System.out.println("Wrong excel file");
            e.printStackTrace();
        }
    }

    //получаем путь к папке для сохранения актов
    public void selectSaveDirectory(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбрать папку для сохранения");
        File file = directoryChooser.showDialog(Main.mainFX);
        saveDirectoryPath = file.getAbsolutePath();
        savePathField.setText(file.getAbsolutePath());
    }

    //получаем путь к файлу шаблона АОСР
    public void selectTemplateFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл шаболна АОСР");
        File file = fileChooser.showOpenDialog(Main.mainFX);
        templateFilePath = file.getAbsolutePath();
        templatePathField.setText(file.getAbsolutePath());
    }

    public void createAOSRFile(ActionEvent actionEvent) {
        WordProcessor wordProcessor = null;
        try {
            if (aosrContenList.isEmpty()) {
                Alert aosrContenAlert = new Alert(Alert.AlertType.INFORMATION);
                aosrContenAlert.setHeaderText("Ошибка");
                aosrContenAlert.setContentText("Для формирования АСОР недостаточно данных," + '\n'
                        + "начните с выбора файла ОЖР!");
                aosrContenAlert.showAndWait();
            } else {
                if (templateFilePath == null) {
                    Alert templateFileAlert = new Alert(Alert.AlertType.INFORMATION);
                    templateFileAlert.setHeaderText("Ошибка");
                    templateFileAlert.setContentText("Выберите файл шаблона АОСР");
                    templateFileAlert.showAndWait();
                } else {
                    wordProcessor = new WordProcessor(templateFilePath, aosrContenList);
                }
                if (saveDirectoryPath == null) {
                    Alert saveDirectoryAlert = new Alert(Alert.AlertType.INFORMATION);
                    saveDirectoryAlert.setHeaderText("Ошибка");
                    saveDirectoryAlert.setContentText("Выберите папку для сохранения актов");
                    saveDirectoryAlert.showAndWait();
                } else {
                    wordProcessor.createAOSR(getAosrRange(), saveDirectoryPath);
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getAosrRange() {
        ArrayList<Integer> arr = new ArrayList<>();
        if (textFieldAOSRRange.getText() == null || textFieldAOSRRange.getText().isEmpty()) {
            Alert aosrRangeAlert = new Alert(Alert.AlertType.INFORMATION);
            aosrRangeAlert.setHeaderText("Ошибка");
            aosrRangeAlert.setContentText("Укажите номера актов!");
            aosrRangeAlert.showAndWait();
        } else {
            String str = textFieldAOSRRange.getText().replaceAll(" ", "");
            String[] strSplit = str.split(",");
            for (String o : strSplit) {
                if (!o.contains("-")) {
                    arr.add(Integer.parseInt(o));
                } else {
                    String[] rangeSplit = o.split("-");
                    int count = Integer.parseInt(rangeSplit[0]);
                    do {
                        arr.add(count);
                        count++;
                    } while (count <= Integer.parseInt(rangeSplit[1]));
                }
            }
        }
        return arr;
    }
}
