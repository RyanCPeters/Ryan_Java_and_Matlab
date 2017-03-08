import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.*;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * @author Ryan Peters
 * @date 1/28/2017
 */
public class SampleUsingACanvasObj extends Application {
	Canvas canvas;


	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Drawing Operations Test");
		Group root = new Group();
		canvas = new Canvas(300, 250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//drawShapes(gc);
		//moveCanvas(150,150);
//        drawDShape(gc);
		drawRadialGradient(Color.ALICEBLUE, Color.BLACK, gc);
		drawLinearGradient(Color.AQUA, Color.BLANCHEDALMOND, gc);
		drawShapes(gc);
		drawDropShadow(Color.AQUA, Color.BLACK, Color.ALICEBLUE, Color.BLANCHEDALMOND, gc);
		root.getChildren().add(canvas);

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	// Example 1 Drawing Some Basic Shapes on a Canvas
	private void drawShapes(GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
		Polygon pgon = new Polygon();
		ArrayList<ArrayList<Double>> mutablePGonPoints = new ArrayList<>();
		ArrayList<Double> xList = new ArrayList<>();
		ArrayList<Double> yList = new ArrayList<>();
//
//        xList.add(5.0);
//        xList.add(5.0);
//        xList.add(5.0);
//        yList.add(2.0);
//        yList.add(2.0);
//        yList.add(2.0);
//        mutablePGonPoints.addAll(xList);
//        mutablePGonPoints.addAll();
//        pgon.getPoints().setAll(mutablePGonPoints);
		System.out.println("\nThe value of 3^2 is :" + (Math.pow(3, 0)));
		pgon.getPoints().addAll(new Double[]{
				0.0, 90.0,
				10.0, 95.0,
				20.0, 100.0
		});
		System.out.println(pgon.toString());
		pgon.getTransforms().add(new Rotate(30, 0, 0));
		pgon.getTransforms().add(new Translate(10, 10));
		ArrayList<Double> polyPoints = new ArrayList<>();
		polyPoints.addAll(pgon.getPoints());
		System.out.println(polyPoints.toString());
	}

	// Example 2 Moving the Canvas
	private void moveCanvas(int x, int y) {
		canvas.setTranslateX(x);
		canvas.setTranslateY(y);
	}


	// Example 3 Drawing a Bezier Curve (Capital "D") On Screen
	private void drawDShape(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(50, 50);
		gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
		gc.closePath();
	}

	// Example 4 Drawing a RadialGradient
	private void drawRadialGradient(Color firstColor, Color lastColor, GraphicsContext gc) {
		gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true,
				CycleMethod.REFLECT,
				new Stop(0.0, firstColor),
				new Stop(1.0, lastColor)));
		gc.fill();
	}

	//Example 5 Drawing a LinearGradient

	private void drawLinearGradient(Color firstColor, Color secondColor, GraphicsContext gc) {
		LinearGradient lg = new LinearGradient(0, 0, 1, 1, true,
				CycleMethod.REFLECT,
				new Stop(0.0, firstColor),
				new Stop(1.0, secondColor));
		gc.setStroke(lg);
		gc.setLineWidth(20);
		gc.stroke();
	}

	//Example 6 Adding a DropShadow

	private void drawDropShadow(Color firstColor, Color secondColor,
	                            Color thirdColor, Color fourthColor, GraphicsContext gc) {
		gc.applyEffect(new DropShadow(20, 20, 0, firstColor));
		gc.applyEffect(new DropShadow(20, 0, 20, secondColor));
		gc.applyEffect(new DropShadow(20, -20, 0, thirdColor));
		gc.applyEffect(new DropShadow(20, 0, -20, fourthColor));
	}

}
