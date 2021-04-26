package com.example.punttilaskuri;

public class BmrCalculator {

    private double weightKg;
    private double heightCm;
    private double age;
    private boolean male;

    public BmrCalculator(double weightKg, double heightCm, double age, boolean male){
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.age = age;
        this.male = male;
    }

    public void setWeightKg(double weight){
        weightKg = weight;
    }
    public void setHeightCm(double height){
        heightCm = height;
    }
    public void setAge(double age) {
        this.age = age;
    }
    public void setMale(boolean male) {
        this.male = male;
    }

    public double calculateBmr(){
        if(male){
            double maleAgeMod = 6.755;
            double maleHeightMod = 5.003;
            double maleWeightMod = 13.7500;
            double maleMod = 66.5000;
            return maleMod + (maleWeightMod * weightKg) + (maleHeightMod * heightCm) - (maleAgeMod * age);
        }
        else{
            double femaleAgeMod = 4.676;
            double femaleHeightMod = 1.850;
            double femaleWeightMod = 9.563;
            double femaleMod = 655.0000;
            return femaleMod + (femaleWeightMod * weightKg) + (femaleHeightMod * heightCm) - (femaleAgeMod * age);
        }

    }
}
