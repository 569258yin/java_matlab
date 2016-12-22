package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.matlab.image.BinaryRGB;
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


public class Main extends Application {
	private MyGridPane root = new MyGridPane();
	private int valus1=0,valus2=0,valus3=0;
	private File file_new;
	private Image image2;
	private ImageView view2;
	private Button btnStart,btnGray,btnBinary;
	private TextField text;
	private ScrollBar s1,s2,s3;
	private FilePath filepath = FilePath.getInstance();
	int i = 0;

	private ExecutorService threadAgent = Executors.newSingleThreadExecutor();

	@Override
	public void start(Stage primaryStage) {
		try {
			//����ͼƬ��Դ
			//			File file = new  File("C:/004.png");
			if(!filepath.getFile().exists()){
				System.out.println("������FilePath�ļ�·����ȷ��");
				throw new FileNotFoundException();
			}
			Image image = new Image(filepath.getFile().toURI().toURL().toString());
			//			file_new = new  File("C:/004_new.png");
			ImageView view1 = new ImageView(image);
			image2 = new Image(filepath.getFile().toURI().toURL().toString());
			view2 = new ImageView(image2);
			file_new = filepath.getFile_new();
			GridPane gridpane = new GridPane();    //ͼƬ�Ĳ���
			gridpane.setHgap(20);
			gridpane.setVgap(20);
			gridpane.setPadding(new Insets(10, 10, 10, 10));
			gridpane.add(view1, 1, 1);
			gridpane.add(view2, 2, 1);

			GridPane textpane = new GridPane();


			Label uriLabel = new Label("��ѡ���ͼƬ·����");
			TextField uri = new TextField();
			Button serch = new Button("���ͼƬ");
			textpane.setHgap(5);
			textpane.setPadding(new Insets(10, 0, 0, 0));
			textpane.add(uriLabel, 2, 1);
			textpane.add(uri, 4, 1);
			textpane.add(serch, 6, 1);
			//��ť������
			GridPane btnPane = new GridPane();
			btnPane.setHgap(20);
			btnPane.setPadding(new Insets(10, 0, 0, 0));
			btnStart = new Button("�ָ�Ĭ��");
			btnGray = new Button("�ҶȻ�����");
			btnBinary = new Button("��ֵ������");
			text = new TextField();
			text.setText("  ��ִ������Ҫ�Ĳ���  ");

			text.setEditable(false);
			btnPane.add(btnStart, 2, 1);
			btnPane.add(btnGray, 4, 1);
			btnPane.add(btnBinary, 6, 1);
			btnPane.add(text, 8, 1);
			//���������ַŵ�һ����������
			VBox box = new VBox(textpane,btnPane,root.addPane(),gridpane);
			box.setPadding(new Insets(10));
			box.setSpacing(10);
			Scene scene = new Scene(box,1280,700);
			s1 = root.getS1();
			s2 = root.getS2();
			s3 = root.getS3();
			//��ʼ��ִ��һ�λָ�Ĭ��
			Restart();
			//��ɫͨ�������¼�
			s1.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					//					try {
					//						image2 = new Image(file_new.toURI().toURL().toString());
					//						view2.setImage(image2);
					//					} catch (MalformedURLException e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//					}
					//��ֹ������������������߳�
					if(!ImageUtil.isFastClick()){
						System.out.println("OnClick");
						valus1 = (int)s1.getValue();
						Future<Boolean> future = threadAgent.submit(new DealTask(valus1,valus2,valus3));
						try {
							while(true){
								if(future.isDone()){
									if(future.get()){
										image2 = new Image(file_new.toURI().toURL().toString());
										System.out.println("SetIamge");
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
			//��ɫͨ�������¼�
			s2.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					//					try {
					//						image2 = new Image(file_new.toURI().toURL().toString());
					//						view2.setImage(image2);
					//					} catch (MalformedURLException e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//					}
					//��ֹ������������������߳�
					if(ImageUtil.isFastClick()){
						return;
					}
					valus2 = (int)s2.getValue();
					Future<Boolean> future = threadAgent.submit(new DealTask(valus1,valus2,valus3));
					try {
						while(true){
							if(future.isDone()){
								if(future.get()){
									image2 = new Image(file_new.toURI().toURL().toString());
									System.out.println("SetIamge");
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
			//��ɫͨ�������¼�
			s3.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					//					try {
					//						image2 = new Image(file_new.toURI().toURL().toString());
					//					} catch (MalformedURLException e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//					}
					//��ֹ������������������߳�
					if(ImageUtil.isFastClick()){
						return;
					}
					view2.setImage(image2);
					valus3 = (int)s3.getValue();
					Future<Boolean> future = threadAgent.submit(new DealTask(valus1,valus2,valus3));
					try {
						while(true){
							if(future.isDone()){
								if(future.get()){
									image2 = new Image(file_new.toURI().toURL().toString());
									System.out.println("SetIamge");
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
			//�ָ�Ĭ������
			btnStart.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Restart();
				}
			});
			//�Ҷȱ仯
			btnGray.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					Rgb2Gray r = new Rgb2Gray();
					r.Myrun();
					try {
						image2 = new Image(file_new.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view2.setImage(image2);

				}
			});
			//��ֵ���仯
			btnBinary.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					BinaryRGB b = new BinaryRGB();
					b.Myrun();

					try {
						image2 = new Image(file_new.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view2.setImage(image2);
					primaryStage.show();

				}
			});
			serch.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					FileChooser choose = new FileChooser();
					choose.setTitle("��ѡ���ļ���");
					File values = choose.showOpenDialog(null);
					try {
						filepath.setFile(values);
						String path = values.toURI().toURL().toString();
						filepath.setUripath(path);
						uri.setText(values.toString());
						uri.setEditable(false);
						//						Thread thredMain = new Thread();
						//						thredMain.start();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//�ָ�Ĭ��ֵ�÷���
	public void Restart() {

		ReStart s = new ReStart();
		s.myRun();
		try {
			image2 = new Image(file_new.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
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
