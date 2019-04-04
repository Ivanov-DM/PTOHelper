import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.ivanov.pto_helper.model.AOSRForUI;


public class Main extends Application {
    public static Stage mainFX;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        final Controller controller = (Controller)loader.getController();
        primaryStage.setTitle("PTO-Helper");
        ChoiceBox choiceBoxFrom = controller.choiceBoxFrom;
        ChoiceBox choiceBoxTo = controller.choiceBoxTo;



        mainFX = primaryStage;
        controller.majorVbox.setPadding(new Insets(30, 40, 30, 40));
        controller.majorVbox.setSpacing(20);
        TableView<AOSRForUI> tableView = controller.tableView;

        // Create column UserName (Data type of String).
        TableColumn<AOSRForUI, Integer> numCol = new TableColumn<>("№");
        numCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, Integer>("num"));


        // Create column Email (Data type of String).
        TableColumn<AOSRForUI, String> aosrNumCol = new TableColumn<AOSRForUI, String>("АОСР №");
        aosrNumCol.setMinWidth(150);
        aosrNumCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("aosrNum"));

        // Create column FullName (Data type of String).
        TableColumn<AOSRForUI, String> workNameCol = new TableColumn<AOSRForUI, String>("Наименование работ");
        workNameCol.setMinWidth(500);
        workNameCol.setCellValueFactory(new PropertyValueFactory<AOSRForUI, String>("workName"));

//        ObservableList<AOSRForUI> list = getAOSRList();
//        tableView.setItems(list);

        tableView.getColumns().addAll(numCol, aosrNumCol, workNameCol);
        Scene scene = new Scene(root, 1000, 800);
//        scene.getStylesheets().add(getClass().getResource("myStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();




    }

    public static void main(String[] args) {
        launch(args);
    }
}
