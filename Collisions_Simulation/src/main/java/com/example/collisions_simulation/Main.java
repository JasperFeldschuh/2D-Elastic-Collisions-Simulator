package com.example.collisions_simulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GraphicsContext gc;

    long lastFrameTime;
    long currentFrameTime;
    double timeFromLastFrame;

    Image trump = new Image("donald-trump.jpg");
    Image poolTable = new Image("pool-table.jpeg");
    Image soccerField = new Image("soccer-field-2.png");

    Border poolTableBorders = new Border(74, 1206, 78, 643);

    ObjectManager objects = new ObjectManager(12); // default version
//    ObjectManager objects = new ObjectManager(poolTableBorders,10); // version with pool table borders

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(1280, 720);
        gc = canvas.getGraphicsContext2D();

//        objects.addObject(4,600, 200, 0, 200);
//        objects.addObject(5,600, 700, -0, -200);

//        objects.addObject(2,200, 360, 100, 0);
//        objects.addObject(3,800, 360, -300, 0);
//        objects.addObject(1,600, 360, 0, 0);
//        objects.addObject(1,400, 360, 300, 0);
        double finalBall = 730;
        objects.addObject(1,finalBall, 360, 0, 0);
        objects.addObject(1,finalBall - 60, 360, 0, 0);
        objects.addObject(1,finalBall - 120, 360, 0, 0);
        objects.addObject(1,finalBall - 180, 360, 0, 0);
        objects.addObject(1,finalBall - 240, 360, 0, 0);
        objects.addObject(1,100, 360, 500, 0);
//

        lastFrameTime = System.nanoTime();
        currentFrameTime = System.nanoTime();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                currentFrameTime = System.nanoTime();
                timeFromLastFrame = (currentFrameTime - lastFrameTime) / 1000000000.0;
                lastFrameTime = currentFrameTime;
                objects.keepObjectsInBorder();
                update();
                draw();
//                objects.calculateValues();
            }
        };
        timer.start();

        Pane root = new Pane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

//        calibrateImageDimensions(scene);

        calibrateBorder(scene, objects);

        stage.setScene(scene);
        stage.setTitle("Collisions Simulation");
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }

    void update() {
        objects.updateObjects(timeFromLastFrame);
    }

    void draw() {
        gc.clearRect(0, 0, 1280, 720);
        //  making black background
        gc.setFill(Color.web("#fffbf8"));
        gc.fillRect(0, 0, 1280, 720);

        // drawing border
        gc.setLineWidth(4);
        gc.setStroke(Color.web("#454645"));
        gc.strokeLine(20, 20, 1260, 20);
        gc.strokeLine(20, 700, 1260, 700);
        gc.strokeLine(20, 20 , 20, 700);
        gc.strokeLine(1260, 20, 1260, 700);
//        gc.drawImage(trump, 20, 20, 1240, 680);
//        gc.drawImage(poolTable, -12, -6, 1306, 728);

//        gc.drawImage(soccerField, -40, -10, 1361, 740);

        // drawing the circles
        objects.drawObjects(gc);
    }

    private void calibrateImageDimensions(Scene scene){
        final double[] x = {-8};
        final double[] y = {-6};
        final double[] width = {1292};
        final double[] height = {735};
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case LEFT: x[0]--;
                    break;
                case RIGHT: x[0]++;
                    break;
                case UP : y[0]--;
                    break;
                case DOWN: y[0]++;
                    break;
                case S: height[0]++;
                    break;
                case W: height[0]--;
                    break;
                case A: width[0]--;
                    break;
                case D: width[0]++;
                    break;
                case ENTER:
                    System.out.println("Left: " + x[0] + " Top: " + y[0] + " Right: " + width[0] + " Bottom: " + height[0]);
                    break;
            }
        });
    }

    private void calibrateBorder(Scene scene, ObjectManager objects){
        final double[] leftEdge = {21};
        final double[] rightEdge = {1259};
        final double[] topEdge = {21};
        final double[] bottomEdge = {699};
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case LEFT:
                    rightEdge[0]--;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case RIGHT:
                    rightEdge[0]++;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case UP :
                    topEdge[0]--;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case DOWN:
                    topEdge[0]++;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case A:
                    leftEdge[0]--;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case D:
                    leftEdge[0]++;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case W:
                    bottomEdge[0]--;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case S:
                    bottomEdge[0]++;
                    objects.updateBorder(topEdge[0], bottomEdge[0], leftEdge[0], rightEdge[0]);
                    break;
                case ENTER:
                    System.out.println("Left: " + leftEdge[0] + " Right: " + rightEdge[0] + " Top: " + topEdge[0] + " Bottom: " + bottomEdge[0]);
                    break;
            }
        });
    }
}
