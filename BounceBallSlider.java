import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.beans.property.DoubleProperty;
public class BounceBallSlider extends Application{
    @Override
    public void start(Stage primaryStage){
    	MultipleBallPane ballPane = new MultipleBallPane();
    	
    	Slider slSpeed = new Slider();
        slSpeed.setMax(20);
        slSpeed.setValue(10);
        ballPane.rateProperty().bind(slSpeed.valueProperty());
        
        BorderPane pane=new BorderPane();
        
        ballPane.setOnMousePressed(e->ballPane.pause());
        ballPane.setOnMouseReleased(e->ballPane.play());
        slSpeed.setOnKeyPressed(e->{
            if(e.getCode()==KeyCode.UP){
                slSpeed.setValue(slSpeed.getValue()+1);
            }
            else if(e.getCode()==KeyCode.DOWN){
                slSpeed.setValue(slSpeed.getValue()-1);
            }
        });
        Button btAdd = new Button("+");
        Button btSubtract = new Button("-");
        HBox hBox = new HBox(2);
        btAdd.setOnAction(e -> ballPane.add());
        btSubtract.setOnAction(e -> ballPane.subtract());
        hBox.getChildren().addAll(btAdd, btSubtract);
        hBox.setAlignment(Pos.CENTER);
        pane.setCenter(ballPane);
        pane.setBottom(slSpeed);
        pane.setTop(hBox);
        Scene scene=new Scene(pane,250,150);
        primaryStage.setTitle("BounceBallControl");
        primaryStage.setScene(scene);
        primaryStage.show();
        slSpeed.requestFocus();
    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
class MultipleBallPane extends Pane {
	private Timeline animation;
    public MultipleBallPane() {
    	animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveBall()));
    	animation.setCycleCount(Timeline.INDEFINITE);
    	animation.play();
    }
    public void add() {
		Color color = new Color(Math.random(), 
		Math.random(), Math.random(), 0.5);
		getChildren().add(new Ball(30, 30, 20, color)); 
    }
	public void subtract() {
		if (getChildren().size() > 0) {
			getChildren().remove(0); 
		}
	}
	public void play() {
		animation.play();
	}
	public void pause() {
		animation.pause();
	}
	public DoubleProperty rateProperty() {
		return animation.rateProperty();
	}
	protected void moveBall() {
		for (Node node: this.getChildren()) {
			Ball ball = (Ball)node;
			if (ball.getCenterX() < ball.getRadius() || ball.getCenterX() > getWidth() - ball.getRadius()) {
				ball.dx *= -1;
			}
			if (ball.getCenterY() < ball.getRadius() || ball.getCenterY() > getHeight() - ball.getRadius()) {
				ball.dy *= -1;
			}
			ball.setCenterX(ball.dx + ball.getCenterX());
			ball.setCenterY(ball.dy + ball.getCenterY());
		}
	}
}
class Ball extends Circle {
	double dx = 1, dy = 1;
	Ball(double x, double y, double radius, Color color) {
		super(x, y, radius);
		setFill(color);
    }
}