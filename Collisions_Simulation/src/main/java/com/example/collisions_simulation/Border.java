package com.example.collisions_simulation;

public class Border {
    private double topEdge;
    private double bottomEdge;
    private double leftEdge;
    private double rightEdge;

    public Border(){
       topEdge = 21;
       bottomEdge = 699;
       leftEdge = 21;
       rightEdge = 1259;
    }

    public Border(double leftEdge, double rightEdge, double topEdge, double bottomEdge) {
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
    }

    public double getTopEdge() {
        return topEdge;
    }

    public void setTopEdge(double topEdge) {
        this.topEdge = topEdge;
    }

    public double getBottomEdge() {
        return bottomEdge;
    }

    public void setBottomEdge(double bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public double getLeftEdge() {
        return leftEdge;
    }

    public void setLeftEdge(double leftEdge) {
        this.leftEdge = leftEdge;
    }

    public double getRightEdge() {
        return rightEdge;
    }

    public void setRightEdge(double rightEdge) {
        this.rightEdge = rightEdge;
    }
}
