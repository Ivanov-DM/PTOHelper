import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.ivanov.pto_helper.model.AOSRForUI;


public class Main extends Application {
    public static Stage mainFX;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        final Controller controller = (Controller)loader.getController();
        primaryStage.setTitle("PTO-Helper");
        ChoiceBox choiceBoxFrom = controller.choiceBoxFrom;
        ChoiceBox choiceBoxTo = controller.choiceBoxTo;



        mainFX = primaryStage;
        controller.majorVbox.setPadding(new Insets(30, 40, 30, 40));
        controller.majorVbox.setSpacing(20);
        final TableView<AOSRForUI> tableView = controller.tableView;

        // Create column UserName (Data type of String).
        TableColumn<AOSRForUI, Integer> numCol = new TableColumn<>("№");
        numCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, Integer>("num"));
        numCol.setStyle("-fx-alignment: CENTER;");


        // Create column Email (Data type of String).
        TableColumn<AOSRForUI, String> aosrNumCol = new TableColumn<AOSRForUI, String>("АОСР №");
//        aosrNumCol.setMinWidth(150);
        aosrNumCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("aosrNum"));
        aosrNumCol.setStyle("-fx-alignment: CENTER;");
        aosrNumCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AOSRForUI, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AOSRForUI, String> data) {
                return data.getValue().aosrNumProperty();
            }
        });
//        aosrNumCol.setCellFactory(new Callback<TableColumn<AOSRForUI, String>, TableCell<AOSRForUI, String>>() {
//            @Override
//            public TableCell<AOSRForUI, String> call(
//                    TableColumn<AOSRForUI, String> param) {
//
//                TableCell<AOSRForUI, String> cell = new TableCell<AOSRForUI, String>() {
//
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        Text text = new Text();
//                        setGraphic(text);
//                        text.textProperty().bind(this.itemProperty());
////                        setStyle("-fx-text-fill: red;");
//                        setText(item);
//                    }
//                };
//                return cell ;
//            }
//        });







        // Create column FullName (Data type of String).
        TableColumn<AOSRForUI, String> workNameCol = new TableColumn<AOSRForUI, String>("Наименование работ");
//        workNameCol.setMinWidth(500);
        workNameCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("workName"));
        workNameCol.setCellFactory(new Callback<TableColumn<AOSRForUI, String>, TableCell<AOSRForUI, String>>() {
            @Override
            public TableCell<AOSRForUI, String> call(
                    TableColumn<AOSRForUI, String> param) {

                TableCell<AOSRForUI, String> cell = new TableCell<AOSRForUI, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);



                        Text text = new Text();
                        setGraphic(text);
                        setPrefHeight(Control.USE_COMPUTED_SIZE);
                        text.wrappingWidthProperty().bind(this.widthProperty());
                        text.textProperty().bind(this.itemProperty());
                        getStylesheets().clear();
                        setId("cellWork");
                        setStyle("-fx-text-fill: white;");
                            setText(item);
                    }
                };


//                Text text = new Text();
//                cell.setGraphic(text);
//                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
//                text.wrappingWidthProperty().bind(cell.widthProperty());
//                text.textProperty().bind(cell.itemProperty());
//
//                cell.setStyle("-fx-text-fill: white;");
                return cell ;
            }
        });






//        numCol.prefWidthProperty().bind(tableView.widthProperty().divide(100/10));
//        aosrNumCol.prefWidthProperty().bind(tableView.widthProperty().divide(100/25));
//        workNameCol.prefWidthProperty().bind(tableView.widthProperty().divide(100/65));
        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        numCol.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 50% width
        aosrNumCol.setMaxWidth( 1f * Integer.MAX_VALUE * 25 ); // 30% width
        workNameCol.setMaxWidth( 1f * Integer.MAX_VALUE * 65 ); // 20% width

//        ObservableList<AOSRForUI> list = getAOSRList();
//        tableView.setItems(list);

        tableView.getColumns().addAll(numCol, aosrNumCol, workNameCol);
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("myStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();





    }

    public static void main(String[] args) {
        launch(args);
    }
}
