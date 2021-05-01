package com.example.punttilaskuri;

/**
 * The function of this Class is to calculate the BMR value for any given person. The base for this
 * is the (Revised) Harris Benedict Equation.
 * @author Henri Johansson
 */

public class BmrCalculator {

    private double weightKg;
    private double heightCm;
    private double age;
    private boolean male;

    /**
     *Constructs a calculator that can calculate
     * @param weightKg Sets the initial weight
     * @param heightCm Sets the initial height
     * @param age Sets the initial age
     * @param male Sets the initial gender
     */
    public BmrCalculator(double weightKg, double heightCm, double age, boolean male){
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.age = age;
        this.male = male;
    }

    /**
     * Sets Weight for the class instance
     * @param weight current weight
     */
    public void setWeightKg(double weight){
        weightKg = weight;
    }

    /**
     * Sets Height for the class instance
     * @param height curent height
     */
    public void setHeightCm(double height){
        heightCm = height;
    }

    /**
     * Sets Age for the class instance
     * @param age current age
     */
    public void setAge(double age) {
        this.age = age;
    }

    /**
     * Sets Gender for the class instance
     * @param male current gender
     */
    public void setMale(boolean male) {
        this.male = male;
    }

    /**
     * Uses (Revised) Harris Benedict equation to calculate calories needed for sustaining current
     * weight.
     * @return The result of Calories per day
     * Check out Basal Metabolic rate and the link below.
     * {@link} https://globalrph.com/medcalcs/harris-benedict-equation-updated-basal-metabolic-rate/
     */
    public double calculateBmr(){
        if(male){
            double maleAgeMod = 5.6770;
            double maleHeightMod = 4.7990;
            double maleWeightMod = 13.3970;
            double maleMod = 88.3620;
            return maleMod + (maleWeightMod * weightKg) + (maleHeightMod * heightCm) - (maleAgeMod * age);
        }
        else{
            double femaleAgeMod = 4.330;
            double femaleHeightMod = 3.098;
            double femaleWeightMod = 9.247;
            double femaleMod = 447.593;
            return femaleMod + (femaleWeightMod * weightKg) + (femaleHeightMod * heightCm) - (femaleAgeMod * age);
        }

    }
}
