package com.example.punttilaskuri;

public class MetCalculator {
    private double weight;
    private double time;
    private Double exerciseMet;

    public MetCalculator(double weight, double time, Double exerciseMet) {
        this.weight = weight;
        this.time = time;
        this.exerciseMet = exerciseMet;
    }

    public double calculateCalMet(){
        double weightMod = 3.5;
        double divider = 200;
        return (exerciseMet * weightMod * weight * time) / divider;
    }
}
