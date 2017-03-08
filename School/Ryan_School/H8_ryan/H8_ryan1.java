import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * this class will instantiate a window and a drawing Canvas object upon which it will draw a polygon with (x,y) points
 * derived from a recursive call to recursive snowflake.
 * The Canvas object will have a fixed size set to 325 pixels square and will handle the process of calculating then
 * plotting the corner points of an N'th level Koch Snowflake.
 *
 * @author Ryan Peters
 *         1/25/2017
 */
public class H8_ryan1 extends Application {

	// the variable imgEL should be read as the image edge length, the image isn't going to scale with the window so
	// this can be a constant
	private final int IMG_EL = 350;

	// MAXN denotes the highest level of fractal this program can attempt before hitting a stack overflow
	private final int MAXN = 20;

	// The angle 60 degrees expressed in radians is Pi over three ie. Pi/3.
	private final double SIXTY = Math.PI / 3;

	// Now to establish a reference point in the center of the drawing surface
	// IMG_C reads as image center
	private final double IMG_C = IMG_EL / 2; // if IMG_EL is 350, this should be 175
	private final double BRDR_E = IMG_EL / 14;// if IMG_EL is 350, this should be 25
	private double topWidth = IMG_EL - 2 * BRDR_E;// this variable corresponds to the width of the top level fractal, ie. the base triangle.
	private double height = topWidth * Math.sin(SIXTY);
	private boolean[] pastTwoTurns = new boolean[]{false, false};
	// the drawing area for which we have a graphics component
	private Canvas canvas;

	// starting at the lowest level of the recursion we'll begin saving the plotted points to this map object.
	private Map<Integer, ArrayList<double[]>> map;


	public static void main(String[] args) {


		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {
		Scanner scanMAn = new Scanner(System.in);
		System.out.println("\nPlease enter an integer value between 0 and " + MAXN + "\n");
		int n = -1;
		if (scanMAn.hasNextInt()) {
			n = scanMAn.nextInt();
		}
		int loopCount = 0;
		while ((n < 0 || n > MAXN) && loopCount < 10) {
			loopCount++;
			System.out.println("\nThe value you entered was not between 0 and " + MAXN + " please try again\n");
			if (scanMAn.hasNextInt()) {
				n = scanMAn.nextInt();
			} else if (scanMAn.hasNext()) {
				scanMAn.next();
			}
		}
		if (loopCount >= 10) {
			System.out.println("There appears to be a problem in the data input system. The user should seek aid in " +
					"debugging, program closing now.");
			System.exit(0);
		}

		primaryStage.setTitle("Koch SnowFlake of level " + n);
		Group root = new Group();
		canvas = new Canvas(IMG_EL + 150, IMG_EL + BRDR_E + 150);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		gc.setLineWidth((n > 0) ? (4 / n) : 4);
//        gc.setStroke(new RadialGradient(0, 0, 0.5, 0.5, 0.1, true,CycleMethod.REFLECT,new Stop(0.0, Color.BLUE),new Stop(1.0, Color.color(.8,.3,.5))));
		Polyline theLine = new Polyline();
		map = new HashMap<Integer, ArrayList<double[]>>();
		System.out.println("topWidth = " + topWidth + "\nheight = " + height);
		if (n == 0) {
			double[] x = {BRDR_E, BRDR_E + topWidth / 2, BRDR_E + topWidth};
			double[] y = {BRDR_E + height, BRDR_E, BRDR_E + height};
			gc.strokePolygon(x, y, 3);
		} else {
			double[] endDimens = {
					topWidth / (Math.pow(3, n)),
					height / (Math.pow(3, n)
					)};
			recursiveSnowFlake(n, theLine, endDimens);
			ArrayList<double[]> bucket = new ArrayList<>();
			double[] xValsQ1;
			double[] yValsQ1;

			double[] xValsQ2;
			double[] yValsQ2;

			double[] xValsQ3;
			double[] yValsQ3;

			double[] xValsQ4;
			double[] yValsQ4;
			bucket = map.getOrDefault(n, bucket);
			xValsQ1 = bucket.get(0);
			int Q1len = xValsQ1.length;
			xValsQ2 = new double[Q1len];
			xValsQ3 = new double[Q1len];
			xValsQ4 = new double[Q1len];

			yValsQ1 = bucket.get(1);
			yValsQ2 = new double[Q1len];
			yValsQ3 = new double[Q1len];
			yValsQ4 = new double[Q1len];

			for (int i = 0, j = xValsQ1.length - 1; i < xValsQ1.length; j--, i++) {

				xValsQ2[j] = xValsQ1[i] * -1 + BRDR_E + topWidth / 2;
				xValsQ3[j] = xValsQ1[i] * -1 + BRDR_E + topWidth / 2;
				xValsQ1[i] = xValsQ1[i] + BRDR_E + topWidth / 2;
				xValsQ4[j] = xValsQ1[i];

				yValsQ1[i] = (yValsQ1[i] + BRDR_E);
				yValsQ2[j] = yValsQ1[i];
				yValsQ3[i] = yValsQ1[i] + yValsQ1[Q1len - 1];
				yValsQ4[i] = yValsQ1[i] + yValsQ1[Q1len - 1];
			}
			StringBuilder strB = new StringBuilder("\n\nxValsQ1 =\n{ ");
			for (double xq1 : xValsQ1) {
				strB.append(", " + xq1);

			}
			strB.append(" }\n");

			strB = new StringBuilder("yValsQ1 =\n{ ");
			for (double xq1 : xValsQ1) {
				strB.append(", " + xq1);

			}
			strB.append(" }\n");

			strB.append("xValsQ2 =\n{ ");
			for (double xq2 : xValsQ2) {
				strB.append(", " + xq2);

			}
			strB.append(" }\n");

			strB.append("xValsQ3 =\n{ ");
			for (double xq3 : xValsQ3) {
				strB.append(", " + xq3);
			}
			strB.append(" }\n");
			strB.append("xValsQ4 =\n{ ");
			for (double xq4 : xValsQ4) {
				strB.append(", " + xq4);
			}
			strB.append(" }\n");
			System.out.println(strB.toString());


			gc.strokePolyline(xValsQ1, yValsQ1, Q1len);
//            gc.strokePolyline(xValsQ2,yValsQ2,Q1len);
//            gc.strokePolyline(xValsQ3,yValsQ3,Q1len);
//            gc.strokePolyline(xValsQ4,yValsQ4,Q1len);
		}

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}// end of start method


	/*
	* It should be noted that for all levels n of a Koch snowflake, there will always be a corner which sits at the
	* top center of the drawing. At the time of writing this comment, with IMG_EL = 350 the top center point should be
	*  at AXS_C = (x,y) = (IMG_C, BRDR_E) = (175,25)
	*/
	private double recursiveSnowFlake(int n, Polyline theLine, double[] endDimens) {

		double[] xGivens;
		double[] yGivens;
		ArrayList<double[]> bucket = new ArrayList<>();

		///////////////////////////////////////////////////////////////////////////////////////
		if (n == 0) {
			System.out.println("\n\nEntered recursion for n = " + n);
			System.out.println("endDimens = { " + endDimens[0] + ", " + endDimens[1] + " }");
			// for xGivens, the order here is top, bottom left, then bottom right
			xGivens = new double[]{0.0, endDimens[0] * Math.cos(SIXTY)};// I could create the double as I add it to bucket, but then I can't test it's values so easily
			// same for the y order, first is top, then bottom left, then bottom right.
			yGivens = new double[]{0.0, endDimens[0] * Math.sin(SIXTY)};
			bucket.add(0, xGivens);
			bucket.add(1, yGivens);
			for (int i = 0; i < 2; i++) {
				theLine.getPoints().add(xGivens[i]);
				theLine.getPoints().add(yGivens[i]);
			}
			map.put(n, bucket);
			// the subsequent system.out calls are for debugging.
			StringBuilder baseCase = new StringBuilder("First up is xGivens\n{");
			for (double d : xGivens) {
				baseCase.append(", " + d);
			}
			baseCase.append(" }\n Next is yGivens\n{");
			for (double d : yGivens) {
				baseCase.append(", " + d);
			}
			baseCase.append(" }\n");
			System.out.println(baseCase.toString());
			pastTwoTurns = new boolean[]{false, false};
			System.out.println("angle = " + (-SIXTY));
			return -SIXTY;// idx 0 says if we turned a 60 and idx 1 says if we turned a 120
		}// end of base case conditional
		//////////////////////////////////////////////////////////////////////////////////////////

		// these arrays will take the given point data from n-1 and scale it to 3x size but keep it in proper order, then
		// add the inverted mirrors of those points in the same order at the end.
		double[] xvalsCombine;
		double[] yvalsCombine;
		double[] angle = {0, 0};

		double thisTurnCos;
		double thisTurnSin;
		double nextTurnCos;
		double nextTurnSin;
		angle[0] += recursiveSnowFlake(n - 1, theLine, endDimens);
		if (!pastTwoTurns[0] && !pastTwoTurns[1]) {// had the first turn in a new hump, time for the 120!
			angle[1] -= (SIXTY + SIXTY);
			angle[0] += SIXTY;
			System.out.println("Is angle the low? " + angle[0]);
			pastTwoTurns[0] = false;//
			pastTwoTurns[1] = true;

		} else if (pastTwoTurns[1]) {
			angle[0] += SIXTY;
			angle[1] = SIXTY;
			pastTwoTurns[1] = pastTwoTurns[0] = false;
		} else {
			angle[0] += angle[1] = SIXTY;
			pastTwoTurns[0] = true;
		}

		thisTurnCos = Math.cos(angle[0]);
		thisTurnSin = Math.sin(angle[0]);
		nextTurnCos = Math.cos(angle[0] + angle[1]);
		nextTurnSin = Math.sin(angle[0] + angle[1]);

		bucket = map.getOrDefault(n - 1, bucket);
		System.out.println("\n\nEntered recursion for n = " + n);
		System.out.println("endDimens = { " + endDimens[0] + ", " + endDimens[1] + " }");
		System.out.println("the angle at this point is :" + angle[0] + " and " + angle[1]);
	    /*
        * in order to rotate and flip the points calculated in n-1 we need to swap x and y values, then set the y values
        * to be negative, then add 2*|y|
        */
		int givenArrayLen = bucket.get(0).length;
		System.out.println("the variable givenArraylen = " + givenArrayLen);
		xvalsCombine = new double[givenArrayLen * 3];
		yvalsCombine = new double[givenArrayLen * 3];
		for (int i = 0, j = givenArrayLen - 1, k = givenArrayLen * 2 - 1; i < givenArrayLen; k++, j--, i++) {
			// assigning the given x values to the array to be passed at 3x size
			xvalsCombine[i] = bucket.get(0)[i];
			// assigning the inverted mirrors of given x vals to a later section of array at the same 3x size
			xvalsCombine[i + givenArrayLen] = (bucket.get(1)[j] + endDimens[0]) * thisTurnCos;// here is where we do the y to x conversion

			// assigning the given y values to the array to be passed at 3x size
			yvalsCombine[i] = bucket.get(1)[i];
			// assigning the inverted mirrors of given y vals to a later section of array at the same 3x size
			yvalsCombine[i + givenArrayLen] = ((bucket.get(0)[j]) + endDimens[0]) * thisTurnSin;// here is where we do the x to y conversion

			xvalsCombine[k] = (bucket.get(1)[i] + endDimens[0]) * nextTurnCos;
			yvalsCombine[k] = (bucket.get(0)[i] + (endDimens[1] + endDimens[0])) * nextTurnSin;
		}

		// the subsequent system.out calls are for debugging.
		StringBuilder sb = new StringBuilder("First up is xvalsCombine\n{");
		for (double d : xvalsCombine) {
			sb.append(", " + d);
		}
		sb.append(" }\n Next is yvalsCombine\n{");
		for (double d : yvalsCombine) {
			sb.append(", " + d);
		}
		sb.append(" }\n");
		System.out.println(sb.toString());

		bucket = new ArrayList<>(2);
		bucket.add(xvalsCombine);
		bucket.add(yvalsCombine);
		map.put(n, bucket);
		return angle[0] + angle[1];
	}// end of recursiveSnowFlake method

}
