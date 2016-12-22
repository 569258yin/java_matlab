package application;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;

public class MyGridPane{
	private Label laber1,laber2,laber3;
	private ScrollBar s1,s2,s3;
	public MyGridPane() {

	}
	public GridPane addPane(){	
		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));
		//�ı�
		laber1= new Label("��ͨ��");
		laber2 = new Label("��ͨ��");
		laber3 = new Label("��ͨ��");
		//������
		s1= new ScrollBar();
		s1.setMax(255);
		s1.setMin(-255);
		s1.blockIncrementProperty();
		s1.setValue(0);
		s2= new ScrollBar();
		s2.setMax(255);
		s2.setMin(-255);
		s2.blockIncrementProperty();
		s2.setValue(0);
		s3= new ScrollBar();
		s3.setMax(255);
		s3.setMin(-255);
		s3.blockIncrementProperty();
		s3.setValue(0);
		
		pane.add(laber1, 1, 2);
		pane.add(s1, 2, 2);
		pane.add(laber2, 4, 2);
		pane.add(s2, 5, 2);
		pane.add(laber3, 7, 2);
		pane.add(s3, 8, 2);
		
		return pane;

	}
	public Label getLaber1() {
		return laber1;
	}
	public Label getLaber2() {
		return laber2;
	}
	public Label getLaber3() {
		return laber3;
	}

	public ScrollBar getS1() {
		return s1;
	}
	public ScrollBar getS2() {
		return s2;
	}
	public ScrollBar getS3() {
		return s3;
	}
	
	
	
}
