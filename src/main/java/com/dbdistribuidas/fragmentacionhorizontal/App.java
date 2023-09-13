package com.dbdistribuidas.fragmentacionhorizontal;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private Hyperlink closeLink;
    private TableView tblData;
    private Button btnPredicate;
    private AppService appService;
    private ObservableList<Person> data;
    private String column;
    private String value;
    private String operator;
    
    @Override
    public void start(Stage stage) {
       
        closeLink = new Hyperlink("Salir");
        tblData = new TableView<Person>();
        btnPredicate = new Button("Predicado Simple");
        
        tblData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Person, Integer> noControlCol = new TableColumn("No. Control");
        noControlCol.setCellValueFactory(new PropertyValueFactory<>("noControl"));
        
        TableColumn<Person, String> nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<Person, String> domicilioCol = new TableColumn("Domicilio");
        domicilioCol.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        
        TableColumn<Person, String> ciudadCol = new TableColumn("Ciudad");
        ciudadCol.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        
        TableColumn<Person, Integer> edadCol = new TableColumn("Edad");
        edadCol.setCellValueFactory(new PropertyValueFactory<>("edad"));
        
        TableColumn<Person, String> oficioCol = new TableColumn("Oficio");
        oficioCol.setCellValueFactory(new PropertyValueFactory<>("oficio"));
        
        tblData.getColumns().addAll(
                noControlCol,
                nombreCol,
                domicilioCol,
                ciudadCol,
                edadCol,
                oficioCol
        );
        
        HBox topRightControl = new HBox();
        topRightControl.setAlignment(Pos.BOTTOM_RIGHT);
        topRightControl.getChildren().add(closeLink);
        
        HBox.setHgrow(topRightControl, Priority.ALWAYS);
        
        HBox topControls = new HBox();
        topControls.setAlignment(Pos.BOTTOM_LEFT);
        topControls.getChildren().addAll(topRightControl);
        
        HBox bottomControls = new HBox();
        bottomControls.setAlignment(Pos.BOTTOM_RIGHT);
        bottomControls.getChildren().add(btnPredicate);
        
        HBox middleControls = new HBox();
        middleControls.setAlignment(Pos.CENTER_LEFT);
        
        VBox columnsCheckBox = new VBox();
        middleControls.getChildren().add(columnsCheckBox);
        
        CheckBox cbNoControl = new CheckBox("No. Control");
        cbNoControl.setOnAction(e -> {
            if (cbNoControl.isSelected()) {
                setColumn("No_Control");
            }
                });
        VBox.setMargin(cbNoControl, new Insets(0, 10, 5, 0));
        
        CheckBox cbNombre = new CheckBox("Nombre");
        cbNombre.setOnAction(e -> {
            if (cbNombre.isSelected()) {
                setColumn("Nombre");
            }
                });
        VBox.setMargin(cbNombre, new Insets(0, 10, 5, 0));
        
        CheckBox cbDomicilio = new CheckBox("Domicilio");
        cbDomicilio.setOnAction(e -> {
            if (cbDomicilio.isSelected()) {
                setColumn("Domicilio");
            }
                });
        VBox.setMargin(cbDomicilio, new Insets(0, 10, 5, 0));
        
        CheckBox cbCiudad = new CheckBox("Ciudad");
        cbCiudad.setOnAction(e -> {
            if (cbCiudad.isSelected()) {
                setColumn("Ciudad");
            }
                });
        VBox.setMargin(cbCiudad, new Insets(0, 10, 5, 0));
        
        CheckBox cbEdad = new CheckBox("Edad");
        cbEdad.setOnAction(e -> {
            if(cbEdad.isSelected()) {
                setColumn("Edad");
            }
                });
        VBox.setMargin(cbEdad, new Insets(0, 10, 5, 0));
        
        CheckBox cbOficio = new CheckBox("Oficio");
        cbOficio.setOnAction(e -> {
            if (cbOficio.isSelected()) {
                setColumn("Oficio");
            }
                });
        VBox.setMargin(cbOficio, new Insets(0, 10, 5, 0));
        
        columnsCheckBox.getChildren().addAll(
                cbNoControl,
                cbNombre,
                cbDomicilio,
                cbCiudad,
                cbEdad,
                cbOficio
        );
        
        ToggleGroup tgOperators = new ToggleGroup();
        
        RadioButton rblessThan = new RadioButton("<");
        rblessThan.setOnAction(e -> setOperator("<"));
        rblessThan.setToggleGroup(tgOperators);
        HBox.setMargin(rblessThan, new Insets(0, 20, 0, 20));
        
        RadioButton rbgreaterThan = new RadioButton(">");
        rbgreaterThan.setOnAction(e -> setOperator(">"));
        rbgreaterThan.setToggleGroup(tgOperators);
        HBox.setMargin(rbgreaterThan, new Insets(0, 20, 0, 20));
        
        RadioButton rblessThanEqual = new RadioButton("<=");
        rblessThanEqual.setOnAction(e -> setOperator("<="));
        rblessThanEqual.setToggleGroup(tgOperators);
        HBox.setMargin(rblessThanEqual, new Insets(0, 20, 0, 20));
        
        RadioButton rbgreaterThanEqual = new RadioButton(">=");
        rbgreaterThanEqual.setOnAction(e -> setOperator(">="));
        rbgreaterThanEqual.setToggleGroup(tgOperators);
        HBox.setMargin(rbgreaterThanEqual, new Insets(0, 20, 0, 20));
        
        RadioButton rbEquals = new RadioButton("=");
        rbEquals.setOnAction(e -> setOperator("="));
        rbEquals.setToggleGroup(tgOperators);
        HBox.setMargin(rbEquals, new Insets(0, 20, 0, 20));
        
        RadioButton rbNotEqual = new RadioButton("<>");
        rbNotEqual.setOnAction(e -> setOperator("<>"));
        rbNotEqual.setToggleGroup(tgOperators);
        HBox.setMargin(rbNotEqual, new Insets(0, 20, 0, 20));
        
        TextField tfInput = new TextField();
        tfInput.textProperty().addListener((ov, oldV, newV) -> {
            if (newV.isEmpty()) {
                tblData.setItems(data);
                return;
            }
            
            value = newV;
        });

        middleControls.getChildren().addAll(
                rblessThan,
                rbgreaterThan,
                rblessThanEqual,
                rbgreaterThanEqual,
                rbEquals,
                rbNotEqual,
                tfInput
        );
        
        VBox vBox = new VBox(); //contains all the content in the view
        vBox.getChildren().addAll(
                topControls,
                tblData,
                middleControls,
                bottomControls
        );
        
        VBox.setVgrow(tblData, Priority.ALWAYS);
        
        VBox.setMargin(middleControls, new Insets(0, 15, 15, 15));
        VBox.setMargin(topControls, new Insets(15));
        VBox.setMargin(tblData, new Insets(0, 15, 15, 15));
        VBox.setMargin(bottomControls, new Insets(0, 15, 15, 15));
        
        initConn();
        
        btnPredicate.setOnAction((e) -> {
            setValue(tfInput.getText());
            updateTable();
        });
        
        closeLink.setOnAction(e -> System.exit(0));
        
        stage.setOnShown((e) -> {
            tblData.setItems(data);
        });
        
        Scene scene = new Scene(vBox,768,576);
        stage.setScene(scene);
        stage.show();
    }
    
    private void initConn() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica2", "root", "root");
            appService = new AppService(conn);
            data = FXCollections.observableArrayList(appService.where(""));
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTable() {
        if (value.isEmpty()) {
            return;
        }
        
        String condition = "where " + column + " " + operator + " " + "'" + value + "'";
        System.out.println(condition);
     
        tblData.setItems( FXCollections.observableArrayList(appService.where(condition)));
        
    }

    public void setColumn(String column) {
        this.column = column;
        System.out.println(column);
    }

    public void setValue(String value) {
        this.value = value;
        System.out.println(value);
    }

    public void setOperator(String operator) {
        this.operator = operator;
        System.out.println(operator);
    }
    
    
    
    public static void main(String[] args) {
        launch();
    }

}