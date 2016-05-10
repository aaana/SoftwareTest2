import client.ClientInfo;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by tina on 16/5/1.
 */
public class view extends Application{

    private Controller controller;


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


    //main scene
    private Label sysNameLabel;
    private Label clientTelLabel;
    private TextField clientTelField;

    //pay choice scene
    private Button alipayButton;
    private Button unionpayButton;
    private ImageView alipay;
    private ImageView unionpay;

    private TextField accountTextField;
    private PasswordField passwordTextFeild;
//    private TextField paymentTextField;

    private Pane root;


    //保存stage的值
    private void setPrimaryStage(Stage primaryStage) {
        view.primaryStage = primaryStage;
    }

    public void updateScene(Scene scene) {
        primaryStage.setScene(scene);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);


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

//        Button nextMonthBtn = new Button("下个月");
//        nextMonthBtn.setLayoutX(25);
//        nextMonthBtn.setLayoutY(170);
//        root.getChildren().add(nextMonthBtn);


        //输入手机号,按下回车后处理
        clientTelField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.ENTER) {
                    {
                        //获得手机号
                        controller = new Controller( clientTelField.getText() );
                        updateScene(scenePayChoice);
                    }
                }
            }
        });

//        nextMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent actionEvent) {
//
//                Controller.updateEveryMonth();
//            }
//        });

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

    }

    private void updatePayScene(){
        Pane pay = new Pane();

        Label expectedText = new Label("应付金额");
        expectedText.setLayoutX(50);
        expectedText.setLayoutY(45);
        pay.getChildren().add(expectedText);

        final double expectedCharge = controller.getExpectedCharge();
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

        passwordTextFeild = new PasswordField();
        passwordTextFeild.setId("password");
        passwordTextFeild.setLayoutX(180);
        passwordTextFeild.setLayoutY(125);
        passwordTextFeild.setPrefSize(140, 30);
        pay.getChildren().add(passwordTextFeild);


        Button payBtn = new Button("支付");
        payBtn.setLayoutX(20);
        payBtn.setLayoutY(170);
        pay.getChildren().add(payBtn);

        final Button printBtn = new Button("打印清单");
        printBtn.setLayoutX(190);
        printBtn.setLayoutY(170);
        pay.getChildren().add(printBtn);
        printBtn.setDisable( true );

        payBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {

                double aBalance = controller.verification( accountTextField.getText(), passwordTextFeild.getText(), expectedCharge );

                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型

                if ( aBalance >= 0 ) {
                    alert.setTitle( "支付成功" );
                    alert.setHeaderText( "支付成功" );
                    alert.setContentText( "您已支付成功，谢谢！\n余额: " + aBalance );

                    printBtn.setDisable( false );

                } else if ( aBalance == -2 ){
                    alert.setTitle("支付s失败");
                    alert.setHeaderText("支付失败");
                    alert.setContentText("余额不足");
                } else if ( aBalance == -1 ){
                    alert.setTitle( "支付s失败" );
                    alert.setHeaderText( "支付失败" );
                    alert.setContentText( "用户账户或密码错误" );
                }

                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
            }
        });

        //打印清单按钮的处理
        printBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {

                try {
                    controller.print();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        scenePay = new Scene(pay,500,240);
        scenePay.getStylesheets().add(prePath+"/style.css");


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
