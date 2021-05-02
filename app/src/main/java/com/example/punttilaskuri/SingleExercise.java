package com.example.punttilaskuri;

import org.jetbrains.annotations.NotNull;

/**
 * This Class holds and returns data that is stored within the variables.
 * This Class is intended for storing Activities and their MET values.
 * @author Henri Johansson
 */
public class SingleExercise {
    private final String activityName;
    private final double metabolicEquivTask;

    /**
     * The data within after the constructor is final.
     * Stores activity information.
     * @param activityName Name of the activity.
     * @param metabolicEquivTask the MET number of the activity.
     */
    public SingleExercise(String activityName, double metabolicEquivTask){
        this.activityName = activityName;
        this.metabolicEquivTask = metabolicEquivTask;
    }

    /**
     * returns the MET number.
     * @return MET number of the object
     */
    public double getMET() {
        return metabolicEquivTask;
    }

    /**
     * @return String that can be displayed when summary of the Object is needed.
     */
    @Override
    public @NotNull String toString() {
        return activityName;
    }
}
