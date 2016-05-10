package sample;

import client.ClientInfo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.*;
import java.text.NumberFormat;



public class Main extends Application {


    enum PAYCHOICE{
        ALIPAY,
        UNIONPAY;
    }
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";

    static ClientInfo clientInfo = new ClientInfo();
    private PAYCHOICE paychoice;

    private static Stage primaryStage;
    //scenes
    private Scene sceneMain;
    private Scene scenePayChoice;
    private Scene scenePay;


    //view scene
    private Label sysNameLabel;
    private Label clientTelLabel;
    private TextField clientTelField;

    //pay choice scene
    private Button alipayButton;
    private Button unionpayButton;
    private ImageView alipay;
    private ImageView unionpay;

    private TextField accountTextField;
    private TextField passwordTextFeild;
    private TextField paymentTextField;

    private Pane root;

    private String pnoneNumber;

    //保存stage的值
    private void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    public void updateScene(Scene scene) {
        primaryStage.setScene(scene);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        //文件路径prefix



        root = new Pane();
        root.setId("root");

        //顶部信息
        sysNameLabel = new Label("电信收费系统");
        sysNameLabel.setId("sysName");
        sysNameLabel.setLayoutX(110);
        sysNameLabel.setLayoutY(20);
        root.getChildren().add(sysNameLabel);

        clientTelLabel = new Label("客户手机号：");
        clientTelLabel.setLayoutX(35);
        clientTelLabel.setLayoutY(100);
        root.getChildren().add(clientTelLabel);

        clientTelField = new TextField();
        clientTelField.setId("clientTel");
        clientTelField.setLayoutX(165);
        clientTelField.setLayoutY(100);
        root.getChildren().add(clientTelField);


        //输入手机号,按下回车后处理
        clientTelField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.ENTER) {
                    {
                        //获得手机号
                        String tel = clientTelField.getText();
                        pnoneNumber = clientTelField.getText();
                        int clientId = Integer.parseInt(tel);
                        double ownedBeforeYear=20;
                        double ownedThisYear=0;
                        double timeoutTime=2;
                        double chatTime = 50;
//                        clientInfo = new ClientInfo(clientId,ownedBeforeYear,ownedThisYear,timeoutTime);
                        clientInfo.setClientId(clientId);
                        clientInfo.setOwedBeforeYear(ownedBeforeYear);
                        clientInfo.setOwedThisYear(ownedThisYear);
                        clientInfo.setTimeOutCount(timeoutTime);
                        clientInfo.updateInfo(chatTime);


                        updateScene(scenePayChoice);
                    }
                }
            }
        });

        sceneMain = new Scene(root,400,250);
        primaryStage.setScene(sceneMain);
        sceneMain.getStylesheets().add(prePath + "/style.css");
        primaryStage.show();


        final Pane payChoice = new Pane();
        payChoice.setId("payChoice");

        Label payChoiceLabel = new Label("请选择支付方式");
        payChoiceLabel.getLayoutX();
        payChoiceLabel.setLayoutX(80);
        payChoiceLabel.setLayoutY(15);
        payChoice.getChildren().add(payChoiceLabel);



        alipay = new ImageView(prePath+"/alipay.png");
        alipay.setFitHeight(70);
        alipay.setFitWidth(73);
        alipayButton = new Button("",alipay);
        alipayButton.setLayoutX(35);
        alipayButton.setLayoutY(40);
        payChoice.getChildren().add(alipayButton);

        alipayButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
//                updateScene(sceneMain);
                updatePayScene();
                updateScene(scenePay);

                paychoice = PAYCHOICE.ALIPAY;
            }
        });

        unionpay = new ImageView(prePath+"/unionpay.png");

        unionpay.setFitHeight(60);
        unionpay.setFitWidth(90);

        unionpayButton = new Button("",unionpay);
        unionpayButton.setLayoutX(145);
        unionpayButton.setLayoutY(45);

        payChoice.getChildren().add(unionpayButton);

        unionpayButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                //
                updatePayScene();
                updateScene(scenePay);
                paychoice = PAYCHOICE.UNIONPAY;
            }
        });

        scenePayChoice = new Scene(payChoice,300,150);
        scenePayChoice.getStylesheets().add(prePath+"/style.css");
//        updateScene(scenePayChoice);


//        Pane pay = new Pane();
//
//        Label expectedText = new Label("应付金额");
//        expectedText.setLayoutX(50);
//        expectedText.setLayoutY(45);
//        pay.getChildren().add(expectedText);
//
//        double expectedCharge = clientInfo.getOwedBeforeYear()+clientInfo.getOwedThisYear();
//        String expected = String.valueOf(expectedCharge);
//        Label expectedLabel = new Label(expected);
//        expectedLabel.setLayoutX(180);
//        expectedLabel.setLayoutY(45);
//        pay.getChildren().add(expectedLabel);
//
//        Label accountLabel = new Label("帐号：");
//        accountLabel.setLayoutX(50);
//        accountLabel.setLayoutY(85);
//        pay.getChildren().add(accountLabel);
//
//        accountTextField = new TextField();
//        accountTextField.setId("account");
//        accountTextField.setLayoutX(180);
//        accountTextField.setLayoutY(85);
//        accountTextField.setPrefSize(140, 30);
//        pay.getChildren().add(accountTextField);
//
//
//
//        Label pswLabel = new Label("密码：");
//        pswLabel.setLayoutX(50);
//        pswLabel.setLayoutY(125);
//        pay.getChildren().add(pswLabel);
//
//        passwordTextFeild = new TextField();
//        passwordTextFeild.setId("password");
//        passwordTextFeild.setLayoutX(180);
//        passwordTextFeild.setLayoutY(125);
//        passwordTextFeild.setPrefSize(140, 30);
//        pay.getChildren().add(passwordTextFeild);
//
//
//        Button payBtn = new Button("支付");
//        payBtn.setLayoutX(20);
//        payBtn.setLayoutY(170);
//        pay.getChildren().add(payBtn);
////支付成功的响应时间
//        payBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent actionEvent) {
////                updateScene(sceneMain);
//                //获得账 账号和密码
//                accountTextField.getText();
//                passwordTextFeild.getText();
//                //支付成功时弹出警示框
//                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
//                alert.setTitle("支付成功"); //設定對話框視窗的標題列文字
//                alert.setHeaderText("支付成功"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
//                alert.setContentText("您已支付成功，谢谢！"); //設定對話框的訊息文字
//                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
//            }
//        });
//
//
//        Button printBtn = new Button("打印清单");
//        printBtn.setLayoutX(190);
//        printBtn.setLayoutY(170);
//        pay.getChildren().add(printBtn);
//
//        //打印清单按钮的处理
//        printBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent actionEvent) {
////                updateScene(sceneMain);
//            }
//        });
//
//        scenePay = new Scene(pay,500,240);
//        scenePay.getStylesheets().add(prePath+"/style.css");
////        updateScene(scenePay);
//
//
//        Button backBtn = new Button("返回主界面");
//        backBtn.setLayoutX(350);
//        backBtn.setLayoutY(170);
//        pay.getChildren().add(backBtn);
//
//        backBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent actionEvent) {
//                updateScene(sceneMain);
//            }
//        });

    }

    private void updatePayScene(){
        Pane pay = new Pane();

        Label expectedText = new Label("应付金额");
        expectedText.setLayoutX(50);
        expectedText.setLayoutY(45);
        pay.getChildren().add(expectedText);

        double expectedCharge = clientInfo.getOwedBeforeYear()+clientInfo.getOwedThisYear();
        String expected = String.valueOf(expectedCharge);
        Label expectedLabel = new Label(expected);
        expectedLabel.setLayoutX(180);
        expectedLabel.setLayoutY(45);
        pay.getChildren().add(expectedLabel);

        Label accountLabel = new Label("帐号：");
        accountLabel.setLayoutX(50);
        accountLabel.setLayoutY(85);
        pay.getChildren().add(accountLabel);

        accountTextField = new TextField();
        accountTextField.setId("account");
        accountTextField.setLayoutX(180);
        accountTextField.setLayoutY(85);
        accountTextField.setPrefSize(140, 30);
        pay.getChildren().add(accountTextField);



        Label pswLabel = new Label("密码：");
        pswLabel.setLayoutX(50);
        pswLabel.setLayoutY(125);
        pay.getChildren().add(pswLabel);

        passwordTextFeild = new TextField();
        passwordTextFeild.setId("password");
        passwordTextFeild.setLayoutX(180);
        passwordTextFeild.setLayoutY(125);
        passwordTextFeild.setPrefSize(140, 30);
        pay.getChildren().add(passwordTextFeild);


        Button payBtn = new Button("支付");
        payBtn.setLayoutX(20);
        payBtn.setLayoutY(170);
        pay.getChildren().add(payBtn);
//支付成功的响应时间
        payBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
//                updateScene(sceneMain);
                //获得账 账号和密码
                accountTextField.getText();
                passwordTextFeild.getText();
                //支付成功时弹出警示框

                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型



                if ( pnoneNumber.equals( "100" ) ) {
                    alert.setTitle("支付成功"); //設定對話框視窗的標題列文字
                    alert.setHeaderText("支付成功"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                    alert.setContentText("您已支付成功，谢谢！"); //設定對話框的訊息文字

                } else if ( pnoneNumber.equals( "101" ) ) {
                    alert.setTitle("支付s失败"); //設定對話框視窗的標題列文字
                    alert.setHeaderText("支付失败"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                    alert.setContentText("余额不足"); //設定對話框的訊息文字

                }


                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
            }
        });


        Button printBtn = new Button("打印清单");
        printBtn.setLayoutX(190);
        printBtn.setLayoutY(170);
        pay.getChildren().add(printBtn);

        //打印清单按钮的处理
        printBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
//                updateScene(sceneMain);
            }
        });

        scenePay = new Scene(pay,500,240);
        scenePay.getStylesheets().add(prePath+"/style.css");
//        updateScene(scenePay);


        Button backBtn = new Button("返回主界面");
        backBtn.setLayoutX(350);
        backBtn.setLayoutY(170);
        pay.getChildren().add(backBtn);

        backBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                updateScene(sceneMain);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
