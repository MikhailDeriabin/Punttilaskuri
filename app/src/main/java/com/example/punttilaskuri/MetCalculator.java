package com.example.punttilaskuri;

/**
 * class for counting used calories based on MET 3 parameters and 2 modifiers.
 * Reference link below
 * {@link} http://media.hypersites.com/clients/1235/filemanager/MHC/METs.pdf
 * @author Henri Johansson
 */
public class MetCalculator {
    private double weight;
    private double time;
    private double exerciseMet;

    /**
     * Initializes Calculator with some values given.
     * @param weight initial weight
     * @param time initial time
     * @param exerciseMet initial met
     */
    public MetCalculator(double weight, double time, Double exerciseMet) {
        this.weight = weight;
        this.time = time;
        this.exerciseMet = exerciseMet;
    }

    /**
     * setter for weight
     * @param weight weight for the calculator as type Double.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Setter for time
     * @param time sets time for the activity with type Double.
     */
    public void setTime(double time){
        this.time = time;
    }

    /**
     * Getter for time
     * @return time in type Double of the calculator.
     */
    public double getTime(){
        return time;
    }

    /**
     * Setter for MET
     * @param exerciseMet set MET value inside the calculator as type double.
     */
    public void setExerciseMet(double exerciseMet){
        this.exerciseMet = exerciseMet;
    }

    /**
     * Counts the calories used estimate and returns it
     * @return type double calculation estimate of calories consumed.
     */
    public double calculateCalMet(){
        double weightMod = 3.5;
        double divider = 200;
        return (exerciseMet * weightMod * weight * time) / divider;
    }
}
