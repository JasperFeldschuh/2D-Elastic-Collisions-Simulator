package com.example.collisions_simulation;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ObjectManager {
    private ArrayList<Object> objects = new ArrayList();
    private Border border = new Border();
    public ObjectManager(Border border, int numberOfObjects){
        this.border = border;
        for(int i = 0; i < numberOfObjects; i++){
            Object circle = new Object(border);
            objects.add(circle);
        }
    }

    public ObjectManager(int numberOfObjects){ // default constructor that makes a set number of random objects
        for(int i = 0; i < numberOfObjects; i++){
            Object circle = new Object(border);
            objects.add(circle);
        }
    }

    public void addObject(double mass, double centerX, double centerY, double xVelocity, double yVelocity){ // adds a custom object
        Object circle = new Object(mass, centerX, centerY, xVelocity, yVelocity, border);
        objects.add(circle);
    }

    public void removeObject(int index){ // an impractical way to remove objects that I will probably never bother using
        objects.remove(index);
    }

    public void updateObjects(double timeElapsed){
        for(Object circle: objects){
            circle.updateValues(timeElapsed);
        }

        for(int i = 0; i < objects.size() - 1; i++){
            for(int j = i + 1; j < objects.size(); j++){
                Object c1 = objects.get(i);
                Object c2 = objects.get(j);

                if(isTouching(c1, c2)){
                    // setting up preliminary values
                    double rise = c2.getCenterY() - c1.getCenterY();
                    double run = c2.getCenterX() - c1.getCenterX();
                    double theta = Math.atan2(rise, run);

                    double c1Mass = c1.getMass();
                    double c1xVelocity = c1.getxVelocity();
                    double c1yVelocity = c1.getyVelocity();

                    double c2Mass = c2.getMass();
                    double c2xVelocity = c2.getxVelocity();
                    double c2yVelocity = c2.getyVelocity();

                    double cos = Math.cos(theta);
                    double sin = Math.sin(theta);
                    // rotating to horizontal to make the y component irrelevant using fancy matrix shit I don't understand
                    double rotatedC1xVelocity = cos * c1xVelocity + sin * c1yVelocity;
                    double rotatedC1yVelocity = -sin * c1xVelocity + cos * c1yVelocity;
                    double rotatedC2xVelocity = cos * c2xVelocity + sin * c2yVelocity;
                    double rotatedC2yVelocity = -sin * c2xVelocity + cos * c2yVelocity;
                    // calculating the new velocity values after the collision
                    double rotatedXMomentum = c1Mass * rotatedC1xVelocity + c2Mass * rotatedC2xVelocity;
                    double newRotatedC1xVelocity = (rotatedXMomentum - c2Mass * (rotatedC1xVelocity - rotatedC2xVelocity)) / (c1Mass + c2Mass);
                    double newRotatedC2xVelocity = (rotatedXMomentum - c1Mass * (rotatedC2xVelocity - rotatedC1xVelocity)) / (c1Mass + c2Mass);
                    // rotating everything back with more matrix multiplication copied from an actually smart person
                    double newC1xVelocity = cos * newRotatedC1xVelocity - sin * rotatedC1yVelocity;
                    double newC1yVelocity = sin * newRotatedC1xVelocity + cos * rotatedC1yVelocity;
                    double newC2xVelocity = cos * newRotatedC2xVelocity - sin * rotatedC2yVelocity;
                    double newC2yVelocity = sin * newRotatedC2xVelocity + cos * rotatedC2yVelocity;
                    // setting the values
                    c1.setxVelocity(newC1xVelocity);
                    c1.setyVelocity(newC1yVelocity);
                    c2.setxVelocity(newC2xVelocity);
                    c2.setyVelocity(newC2yVelocity);
                }
            }
        }
    }

    private boolean isTouching(Object c1, Object c2){
        double c1CenterX = c1.getCenterX();
        double c1CenterY = c1.getCenterY();
        double c2CenterX = c2.getCenterX();
        double c2CenterY = c2.getCenterY();

        double horizontalDistance = c1CenterX - c2CenterX; // shouldn't matter if negative because it will get squared
        double verticalDistance = c1CenterY - c2CenterY;
        double distance = Math.hypot(horizontalDistance, verticalDistance);
        double c1Radius = c1.getRadius();
        double c2Radius = c2.getRadius();


        if(distance < c1Radius + c2Radius){
            separateObjects(c1, c1CenterX, c1CenterY, c1Radius, c2, c2CenterX, c2CenterY, c2Radius, verticalDistance, horizontalDistance, distance);
            return true;
        }

        return false;
    }

    private void separateObjects(Object c1, double c1CenterX, double c1CenterY, double c1Radius, Object c2, double c2CenterX, double c2CenterY, double c2Radius, double verticalDistance, double horizontalDistance, double distance) {
        //separating objects
        double overlap = c1Radius + c2Radius - distance;
        // ADD PROTECTION AGAINST DIVIDE BY ZERO ERROR!!!!!
        if (horizontalDistance == 0) { // protecting against divide by zero error
            double yShift = overlap / 2;
            if (c1CenterY > c2CenterY) {
                c1.setCenterY(c1CenterY + yShift);
                c1.setY(c1.getY() + yShift);
                c2.setCenterY(c2CenterY - yShift);
                c2.setY(c2.getY() - yShift);
            } else {
                c1.setCenterY(c1CenterY - yShift);
                c1.setY(c1.getY() - yShift);
                c2.setCenterY(c2CenterY + yShift);
                c2.setY(c2.getY() + yShift);
            }
        } else {

            double slope = verticalDistance / horizontalDistance;
            double xShift = Math.sqrt(overlap / (Math.pow(slope, 2) + 1)) / 2;
            double yShift = slope * xShift;

            if (c1CenterX > c2CenterX) {
                c1.setCenterX(c1CenterX + xShift);
                c1.setX(c1.getX() + xShift);
                c2.setCenterX(c2CenterX - xShift);
                c2.setX(c2.getX() - xShift);
//                System.out.println("C2 Separation Distance: " + (c2.getCenterX() - c2.getX()) + "\n C1 Separation Distance: " + (c1.getCenterX() - c1.getX()));
            } else {
                c1.setCenterX(c1CenterX - xShift);
                c1.setX(c1.getX() - xShift);
                c2.setCenterX(c2CenterX + xShift);
                c2.setX(c2.getX() + xShift);
//                System.out.println("C2 Separation Distance: " + (c2.getCenterX() - c2.getX()) + "\n C1 Separation Distance: " + (c1.getCenterX() - c1.getX()));
            }

            if (c1CenterY > c2CenterY) {
                c1.setCenterY(c1CenterY + yShift);
                c1.setY(c1.getY() + yShift);
                c2.setCenterY(c2CenterY - yShift);
                c2.setY(c2.getY() - yShift);
//                System.out.println("C2 Separation Distance: " + (c2.getCenterX() - c2.getX()) + "\n C1 Separation Distance: " + (c1.getCenterX() - c1.getX()));
            } else {
                c1.setCenterY(c1CenterY - yShift);
                c1.setY(c1.getY() - yShift);
                c2.setCenterY(c2CenterY + yShift);
                c2.setY(c2.getY() + yShift);
//                System.out.println("C2 Separation Distance: " + (c2.getCenterX() - c2.getX()) + "\n C1 Separation Distance: " + (c1.getCenterX() - c1.getX()));
            }
        }
    }

    public void keepObjectsInBorder(){
        for(Object circle : objects){
            circle.keepInBorder();
        }
    }
// method that was used for debugging and is probably not needed anymore
    public void calculateValues(){
        double xMomentum = 0;
        double yMomentum = 0;
        double xKineticEnergy = 0;
        double yKineticEnergy = 0;
        for(Object circle: objects){
            double mass = circle.getMass();
            double xVelocity = circle.getxVelocity();
            double yVelocity = circle.getyVelocity();
            xMomentum += mass * xVelocity;
            yMomentum += mass * yVelocity;
            xKineticEnergy += 0.5 * mass * Math.pow(xVelocity, 2);
            yKineticEnergy += 0.5 * mass * Math.pow(yVelocity, 2);
        }
        System.out.println("Momentum: " + Math.hypot(yMomentum, xMomentum)
                + "     Kinetic Energy: " + Math.hypot(yKineticEnergy, xKineticEnergy));
    }

    public void updateBorder(double topEdge, double bottomEdge, double leftEdge, double rightEdge){
        for(Object circle: objects){
            circle.setLeftEdge(leftEdge);
            circle.setRightEdge(rightEdge);
            circle.setTopEdge(topEdge);
            circle.setBottomEdge(bottomEdge);
        }
    }

    public void drawObjects(GraphicsContext gc){
        for(Object circle: objects){
            gc.setFill(circle.getColor()); // currently useless as they are all the same color
            gc.fillOval(circle.getX(), circle.getY(), circle.getRadius() * 2, circle.getRadius() * 2);
            // code to override whatever error I can't figure out
            circle.fixCoordinateDrift();
        }
    }
}
