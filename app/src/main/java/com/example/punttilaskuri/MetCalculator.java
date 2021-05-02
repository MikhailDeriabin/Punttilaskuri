package com.example.punttilaskuri;

/**
 * @author Henri Johansson
 */
public class MetCalculator {
    private double weight;
    private double time;
    private double exerciseMet;

    public MetCalculator(double weight, double time, Double exerciseMet) {
        this.weight = weight;
        this.time = time;
        this.exerciseMet = exerciseMet;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setTime(double time){
        this.time = time;
    }
    public double getTime(){
        return time;
    }
    public void setExerciseMet(double exerciseMet){
        this.exerciseMet = exerciseMet;
    }

    public double calculateCalMet(){
        double weightMod = 3.5;
        double divider = 200;
        return (exerciseMet * weightMod * weight * time) / divider;
    }
}
