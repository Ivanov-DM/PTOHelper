import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
    TextField templatePathField, excelPathField, savePathField;

    @FXML
    Button selectBtn;

    @FXML
    VBox majorVbox;

    @FXML
    TableView<AOSRForUI> tableView;

    @FXML
    ChoiceBox choiceBoxFrom, choiceBoxTo;

    String saveDirectoryPath;
    String templateFilePath;
    String excelFilePath;
    int aosrNumFrom;
    int aosrNumTo;
    ArrayList<AOSRContent> aosrContenList;
    private ObservableList<AOSRForUI> aosrTableViewData = FXCollections.observableArrayList();
    private ObservableList<Integer> aosrChoiceData = FXCollections.observableArrayList();

    //получаем путь к excel файлу
    public void selectExcelFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл ОЖР");
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*.xlsx");
//        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(Main.mainFX);
        excelFilePath = file.getAbsolutePath();
        excelPathField.setText(file.getAbsolutePath());
        try {
            ExcelParser excelParser = new ExcelParser(excelFilePath);
            aosrContenList = excelParser.getAOSRContentListNew();
            aosrTableViewData.clear();
            for (int i = 0; i < aosrContenList.size(); i++) {
                aosrTableViewData.add(new AOSRForUI(i + 1, aosrContenList.get(i).getAOSRNum(), aosrContenList.get(i).getWorkName()));
            }
            tableView.setItems(aosrTableViewData);
            getAOSRNumList();
            choiceBoxFrom.setItems(aosrChoiceData);
            choiceBoxTo.setItems(aosrChoiceData);
            Main.mainFX.show();

        } catch (IOException e) {
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
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*.xlsx");
//        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(Main.mainFX);
        templateFilePath = file.getAbsolutePath();
        templatePathField.setText(file.getAbsolutePath());
    }

    public void createAOSRFile(ActionEvent actionEvent) {
        WordProcessor wordProcessor = null;
        try {
            wordProcessor = new WordProcessor(templateFilePath, aosrContenList);
            wordProcessor.createAOSR(aosrNumFrom, aosrNumTo, saveDirectoryPath);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAOSRNumFrom(ActionEvent actionEvent) {
        aosrNumFrom = (Integer)choiceBoxFrom.getValue() - 1;
    }

    public void getAOSRNumTo(ActionEvent actionEvent) {
        aosrNumTo = (Integer)choiceBoxTo.getValue() - 1;
    }

    private void getAOSRNumList() {
        int aosrNumFrom = 0;
        int aosrNumTo = aosrContenList.size();
        for (int i = aosrNumFrom; i < aosrNumTo  ; i++) {
            aosrChoiceData.add(i + 1);
        }
    }
}
