package application;

import com.matlab.image.BinaryRgbOperator;
import com.matlab.image.DealTask;
import com.matlab.image.FilePath;
import com.matlab.image.ImageUtil;
import com.matlab.image.ReStart;
import com.matlab.image.Rgb2Gray;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main extends Application {
    private MyGridPane root = new MyGridPane();
    private volatile int rValues = 0, bValues = 0, gValues = 0;
    private File fileNew;
    private Image image2;
    private ImageView view2;
    private Button btnStart, btnGray, btnBinary;
    private TextField text;
    private ScrollBar s1, s2, s3;
    private FilePath filepath = FilePath.getInstance();

    private ExecutorService threadAgent = Executors.newCachedThreadPool();

    @Override
    public void start(Stage primaryStage) {
        try {
            //加载图片资源
            if (!filepath.getFile().exists()) {
                System.out.println("请设置FilePath文件路径正确！");
                throw new FileNotFoundException();
            }
            Image image = new Image(filepath.getFile().toURI().toURL().toString());
            ImageView view1 = new ImageView(image);
            image2 = new Image(filepath.getFile().toURI().toURL().toString());
            view2 = new ImageView(image2);
            fileNew = filepath.getFile_new();
            //图片的布局
            GridPane gridpane = new GridPane();
            gridpane.setHgap(20);
            gridpane.setVgap(20);
            gridpane.setPadding(new Insets(10, 10, 10, 10));
            gridpane.add(view1, 1, 1);
            gridpane.add(view2, 2, 1);

            GridPane textPane = new GridPane();
            Label uriLabel = new Label("你选择的图片路径：");
            TextField uri = new TextField();
            Button search = new Button("浏览图片");
            textPane.setHgap(5);
            textPane.setPadding(new Insets(10, 0, 0, 0));
            textPane.add(uriLabel, 2, 1);
            textPane.add(uri, 4, 1);
            textPane.add(search, 6, 1);
            //按钮的设置
            GridPane btnPane = new GridPane();
            btnPane.setHgap(20);
            btnPane.setPadding(new Insets(10, 0, 0, 0));
            btnStart = new Button("恢复默认");
            btnGray = new Button("灰度化处理");
            btnBinary = new Button("二值化处理");
            text = new TextField();
            text.setText("  请执行你需要的操作  ");

            text.setEditable(false);
            btnPane.add(btnStart, 2, 1);
            btnPane.add(btnGray, 4, 1);
            btnPane.add(btnBinary, 6, 1);
            btnPane.add(text, 8, 1);
            //将三个布局放到一个主布局中
            VBox box = new VBox(textPane, btnPane, root.addPane(), gridpane);
            box.setPadding(new Insets(10));
            box.setSpacing(10);
            Scene scene = new Scene(box, 1280, 700);
            s1 = root.getS1();
            s2 = root.getS2();
            s3 = root.getS3();
            //初始化执行一次恢复默认
            Restart();
            //红色通道监听事件
            s1.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    //防止过快点击导致启动多次线程
                    if (!ImageUtil.isFastClick()) {
                        Future<Boolean> future = threadAgent.submit(new DealTask(newValue.intValue(), bValues, gValues));
                        try {
                            while (true) {
                                if (future.isDone()) {
                                    if (future.get()) {
                                        image2 = new Image(fileNew.toURI().toURL().toString());
                                        view2.setImage(image2);
                                        break;
                                    }
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //蓝色通道监听事件
            s2.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    //防止过快点击导致启动多次线程
                    if (ImageUtil.isFastClick()) {
                        return;
                    }
                    Future<Boolean> future = threadAgent.submit(new DealTask(rValues, newValue.intValue(), gValues));
                    try {
                        while (true) {
                            if (future.isDone()) {
                                if (future.get()) {
                                    image2 = new Image(fileNew.toURI().toURL().toString());
                                    view2.setImage(image2);
                                    break;
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            //绿色通道监听事件
            s3.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    //防止过快点击导致启动多次线程
                    if (ImageUtil.isFastClick()) {
                        return;
                    }
                    view2.setImage(image2);
                    Future<Boolean> future = threadAgent.submit(new DealTask(rValues, bValues, newValue.intValue()));
                    try {
                        while (true) {
                            if (future.isDone()) {
                                if (future.get()) {
                                    image2 = new Image(fileNew.toURI().toURL().toString());
                                    view2.setImage(image2);
                                    break;
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            //恢复默认设置
            btnStart.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Restart();
                }
            });
            //灰度变化
            btnGray.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    Rgb2Gray r = new Rgb2Gray();
                    r.run();
                    try {
                        image2 = new Image(fileNew.toURI().toURL().toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    view2.setImage(image2);

                }
            });
            //二值化变化
            btnBinary.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    BinaryRgbOperator b = new BinaryRgbOperator();
                    b.run();

                    try {
                        image2 = new Image(fileNew.toURI().toURL().toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    view2.setImage(image2);
                    primaryStage.show();

                }
            });
            search.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    FileChooser choose = new FileChooser();
                    choose.setTitle("请选择文件：");
                    File values = choose.showOpenDialog(null);
                    try {
                        filepath.setFile(values);
                        String path = values.toURI().toURL().toString();
                        filepath.setUripath(path);
                        uri.setText(values.toString());
                        uri.setEditable(false);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复默认值得方法
     */
    public void Restart() {

        ReStart s = new ReStart();
        s.myRun();
        try {
            image2 = new Image(fileNew.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        view2.setImage(image2);
        s1.setValue(0);
        s2.setValue(0);
        s3.setValue(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
