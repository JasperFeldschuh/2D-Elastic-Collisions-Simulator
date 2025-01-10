package com.example.collisions_simulation;

import javafx.scene.paint.Color;

import java.util.List;

public class Object {
    private double mass, x, y, centerX, centerY, xVelocity, yVelocity, radius, leftEdge, rightEdge, topEdge, bottomEdge;
    private Color color;

    List<Color> acceptableColors = List.of(Color.DARKGOLDENROD, Color.BLACK, Color.AQUAMARINE, Color.AQUA, Color.DARKBLUE, Color.CHARTREUSE,
            Color.CHOCOLATE, Color.CORNFLOWERBLUE, Color.DARKCYAN, Color.BROWN, Color.CADETBLUE, Color.ORANGERED, Color.ORANGE, Color.TOMATO,
            Color.FIREBRICK, Color.FUCHSIA, Color.MEDIUMORCHID, Color.INDIGO, Color.SPRINGGREEN, Color.LIMEGREEN, Color.GOLD, Color.GREEN,
            Color.DARKSLATEGREY);

    public Object(Border border){
        mass = Math.random() * 4 + 0.5;
        x = Math.random() * 1220 + 30;
        y = Math.random() * 650 + 30;

        topEdge = border.getTopEdge();
        bottomEdge = border.getBottomEdge();
        leftEdge = border.getLeftEdge();
        rightEdge = border.getRightEdge();

        xVelocity = Math.random() * 300 + 20;
        yVelocity = Math.random() * 300 + 20;
        radius = Math.sqrt(mass) * 15;
        centerX = x + radius; // javafx fillOval coordinates are in the top left corner
        centerY = y + radius;
        color = acceptableColors.get((int)(Math.random() * 23));
    }

    public Object(double mass, double centerX, double centerY, double xVelocity, double yVelocity, Border border){
        this.mass = mass;
        this.centerX = centerX;
        this.centerY = centerY;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;

        topEdge = border.getTopEdge();
        bottomEdge = border.getBottomEdge();
        leftEdge = border.getLeftEdge();
        rightEdge = border.getRightEdge();

        radius = Math.sqrt(mass) * 20;
        x = centerX - radius; // javaFX fillOval coordinates are in the top left corner
        y = centerY - radius;

        color = acceptableColors.get((int)(Math.random() * 12));
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCenterX(){
        return centerX;
    }

    public double getCenterY(){
        return centerY;
    }

    public void setCenterX(double centerX){
        this.centerX = centerX;
    }

    public void setCenterY(double centerY){
        this.centerY = centerY;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getRadius(){
        return radius;
    }

   public Color getColor(){
        return color;
   }
    
    public void updateValues(double timeElapsed){
        double xShift = xVelocity * timeElapsed;
        double yShift = yVelocity * timeElapsed;
        x += xShift;
        y += yShift;
        centerX += xShift;
        centerY += yShift;
    }

    public void keepInBorder(){
        if(centerX - radius < leftEdge){
            xVelocity *= -1;
            double xShift = leftEdge - centerX + radius;
            x += xShift;
            centerX += xShift;
        } else if(centerX + radius > rightEdge){
            xVelocity *= -1;
            double xShift = rightEdge - centerX - radius;
            x += xShift;
            centerX += xShift;
        }

        if(centerY - radius < topEdge){
            yVelocity *= -1;
            double yShift = topEdge - centerY + radius;
            y += yShift;
            centerY += yShift;
        } else if(centerY + radius > bottomEdge){
            yVelocity *= -1;
            double yShift = bottomEdge - centerY - radius;
            y += yShift;
            centerY += yShift;
        }
    }

    public void setLeftEdge(double leftEdge) {
        this.leftEdge = leftEdge;
    }

    public void setRightEdge(double rightEdge) {
        this.rightEdge = rightEdge;
    }

    public void setTopEdge(double topEdge) {
        this.topEdge = topEdge;
//        System.out.println("topEdge " + topEdge);
    }

    public void setBottomEdge(double bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public void fixCoordinateDrift(){
        centerX = x + radius;
        centerY = y + radius;
    }
}
