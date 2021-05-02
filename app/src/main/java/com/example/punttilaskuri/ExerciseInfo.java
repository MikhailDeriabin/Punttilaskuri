package com.example.punttilaskuri;

import java.util.ArrayList;

/**
 * This Class is a Singelton that holds information about MET values of various activities.
 * @author Henri Johansson
 */
public class ExerciseInfo {
    private static final ExerciseInfo infoInstance = new ExerciseInfo();
    private final ArrayList<SingleExercise> exercises;

    /**
     * ArrayList contains multiple SingleExercise objects with names and corresponding MET double numbers
     * @return ArrayList<SingleExercise> gets returned.
     *
     * Reference: American College of Sports Medicine. The Compendium of Physical Activities. ACSM Resource Manual 5th Edition, 2006
     * {@link} http://media.hypersites.com/clients/1235/filemanager/MHC/METs.pdf
     */
    public static ExerciseInfo getInstance(){
        return infoInstance;
    }
    //Dataset for the ExerciseInfo calculator
    private ExerciseInfo(){
        exercises = new ArrayList<>();
        //Walking, Jogging, Running
        exercises.add(new SingleExercise("Walking, slowly (stroll)",2.0));
        exercises.add(new SingleExercise("Walking, 3.2 Kmh",2.5));
        exercises.add(new SingleExercise("Walking, 4.8 Kmh (12.4 min/Km)",3.3));
        exercises.add(new SingleExercise("Walking, 10.6 min/Km",3.8));
        exercises.add(new SingleExercise("Walking, 9.3 min/Km",5.0));
        exercises.add(new SingleExercise("Race walking, moderate pace",6.5));
        exercises.add(new SingleExercise("Hiking up hills",6.9));
        exercises.add(new SingleExercise("Hiking hills, 5,4 Kg pack",7.5));
        exercises.add(new SingleExercise("Jogging, 7.5 min/Km",8.0));
        exercises.add(new SingleExercise("Running, 6.2 min/Km",10.0));
        exercises.add(new SingleExercise("Running, 5.6 min/Km",11.0));
        exercises.add(new SingleExercise("Running, 5 min/Km",12.5));
        exercises.add(new SingleExercise("Running, 4.3 min/Km",14.0));
        exercises.add(new SingleExercise("Running, 3.7 min/Km",16.0));
        //Biking
        exercises.add(new SingleExercise("Stationary cycling, 50 watts ",3.0));
        exercises.add(new SingleExercise("Bicycling, leisurely",3.5));
        exercises.add(new SingleExercise("Stationary cycling, 100 watts",5.5));
        exercises.add(new SingleExercise("Bicycling, 7.5-8.1 Kmh",8.0));
        exercises.add(new SingleExercise("Bicycling, 8.7-9.3 Kmh",10.0));
        exercises.add(new SingleExercise("Bicycling, 10-11.8 Kmh",12.0));
        exercises.add(new SingleExercise("Bicycling, 12.4+ Kmh",16.0));
        //Light activities (<3 METs)*
        exercises.add(new SingleExercise("Canoeing leisurely",2.5));
        exercises.add(new SingleExercise("Croquet",2.5));
        exercises.add(new SingleExercise("Dancing, ballroom, slow",2.9));
        exercises.add(new SingleExercise("Fishing, standing",2.5));
        exercises.add(new SingleExercise("Golf with a cart",2.5));
        exercises.add(new SingleExercise("Housework, light",2.5));
        exercises.add(new SingleExercise("Playing catch",2.5));
        exercises.add(new SingleExercise("Playing a piano",2.5));
        exercises.add(new SingleExercise("Sitting quietly",1.0));
        exercises.add(new SingleExercise("Stretching exercises, yoga",2.5));
        //Moderate activities (3-6 METs)*
        exercises.add(new SingleExercise("Aerobic dance, low impact",5.0));
        exercises.add(new SingleExercise("Archery ",3.5));
        exercises.add(new SingleExercise("Badminton",4.5));
        exercises.add(new SingleExercise("Baseball or softball",5.0));
        exercises.add(new SingleExercise("Basketball, shooting baskets",3.5));
        exercises.add(new SingleExercise("Bowling",3.0));
        exercises.add(new SingleExercise("Calisthenics, light to moderate",3.5));
        exercises.add(new SingleExercise("Canoeing, 4.8 Kmh",3.0));
        exercises.add(new SingleExercise("Chopping wood",6.0));
        exercises.add(new SingleExercise("Dancing, aerobic or ballet",6.0));
        exercises.add(new SingleExercise("Dancing, modern, fast",4.8));
        exercises.add(new SingleExercise("Fencing",6.0));
        exercises.add(new SingleExercise("Fishing, walking and standing",3.5));
        exercises.add(new SingleExercise("Foot bag, hacky sack",4.0));
        exercises.add(new SingleExercise("Gardening, active",4.0));
        exercises.add(new SingleExercise("Golf, walking",4.4));
        exercises.add(new SingleExercise("Gymnastics",4.0));
        exercises.add(new SingleExercise("Hiking cross country",6.0));
        exercises.add(new SingleExercise("Horseback riding",4.0));
        exercises.add(new SingleExercise("Ice skating ",5.5));
        exercises.add(new SingleExercise("Jumping on mini tramp",4.5));
        exercises.add(new SingleExercise("Kayaking",5.0));
        exercises.add(new SingleExercise("Mowing lawn, walking",5.5));
        exercises.add(new SingleExercise("Raking the lawn",4.0));
        exercises.add(new SingleExercise("Shoveling snow",6.0));
        exercises.add(new SingleExercise("Skateboarding",5.0));
        exercises.add(new SingleExercise("Skiing downhill, moderate",6.0));
        exercises.add(new SingleExercise("Snorkeling",5.0));
        exercises.add(new SingleExercise("Snowmobiling",3.5));
        exercises.add(new SingleExercise("Surfing",6.0));
        exercises.add(new SingleExercise("Swimming, moderate pace",4.5));
        exercises.add(new SingleExercise("Table tennis",4.0));
        exercises.add(new SingleExercise("Tai chi",4.0));
        exercises.add(new SingleExercise("Tennis, doubles",5.0));
        exercises.add(new SingleExercise("Trampoline ",3.5));
        exercises.add(new SingleExercise("Volleyball, noncompetitive",3.0));
        exercises.add(new SingleExercise("Walking, brisk up hills",6.0));
        exercises.add(new SingleExercise("Water skiing",6.0));
        exercises.add(new SingleExercise("Weight lifting, heavy workout",6.0));
        exercises.add(new SingleExercise("Wrestling",6.0));
        //Vigorous activities (>6 METs)*
        exercises.add(new SingleExercise("Aerobic dance",6.5));
        exercises.add(new SingleExercise("Aerobic dance, high impact",7.0));
        exercises.add(new SingleExercise("Aerobic stepping, 15.2-20.3 cm",8.5));
        exercises.add(new SingleExercise("Backpacking",7.0));
        exercises.add(new SingleExercise("Basketball game",8.0));
        exercises.add(new SingleExercise("Calisthenics, heavy, vigorous",8.0));
        exercises.add(new SingleExercise("Canoeing, 8 Kmh or portaging",7.0));
        exercises.add(new SingleExercise("Fishing in stream with waders",6.5));
        exercises.add(new SingleExercise("Football, competitive",9.0));
        exercises.add(new SingleExercise("Football, touch/flag",8.0));
        exercises.add(new SingleExercise("Frisbee, ultimate",8.0));
        exercises.add(new SingleExercise("Hockey, field or ice",8.0));
        exercises.add(new SingleExercise("Ice skating, social",7.0));
        exercises.add(new SingleExercise("Judo/karate/tae kwan do",10.0));
        exercises.add(new SingleExercise("Lacrosse",8.0));
        exercises.add(new SingleExercise("Logging/felling trees",8.0));
        exercises.add(new SingleExercise("Mountain climbing",8.0));
        exercises.add(new SingleExercise("Racquetball",10.0));
        exercises.add(new SingleExercise("Racquetball, team",8.0));
        exercises.add(new SingleExercise("Roller skating",7.0));
        exercises.add(new SingleExercise("Rollerblading, fast",12.0));
        exercises.add(new SingleExercise("Rope skipping, slow",8.0));
        exercises.add(new SingleExercise("Rope skipping, fast",12.0));
        exercises.add(new SingleExercise("Skiing cross country, slow",7.0));
        exercises.add(new SingleExercise("Skiing cross country, moderate",8.0));
        exercises.add(new SingleExercise("Skiing cross country, racing uphill ",16.5));
        exercises.add(new SingleExercise("Skiing cross country, vigorous",9.0));
        exercises.add(new SingleExercise("Skiing down hill, vigorous",8.0));
        exercises.add(new SingleExercise("Skin diving",12.5));
        exercises.add(new SingleExercise("Snow shoeing",8.0));
        exercises.add(new SingleExercise("Soccer, casual",7.0));
        exercises.add(new SingleExercise("Soccer, competitive",10.0));
        exercises.add(new SingleExercise("Swimming laps, fast",10.0));
        exercises.add(new SingleExercise("Swimming laps, moderate pace",7.0));
        exercises.add(new SingleExercise("Swimming laps, sidestroke",8.0));
        exercises.add(new SingleExercise("Swimming recreational",6.0));
        exercises.add(new SingleExercise("Tennis",7.0));
        exercises.add(new SingleExercise("Volleyball, competitive/beach",8.0));
        exercises.add(new SingleExercise("Walking, 6.8 min/Km",11.0));
        exercises.add(new SingleExercise("Walking up stairs",8.0));
        exercises.add(new SingleExercise("Water jogging",8.0));
        exercises.add(new SingleExercise("Water polo",10.0));
    }
    /**
     * This function returns the whole arrayList of all the exercises saved inside this class
     * @return ArrayList of type SingleExercise
     */
    public ArrayList<SingleExercise> getExercises(){
        return exercises;
    }

    /**
     * This function will return a Single exercise if its found within the exercises
     * ArrayList if nothing matches in the ListArray
     * it will return SingleExercise with name of Unknown and MetabolicEquivalent of Task with value of 0
     * @param exerciseName is the name of the Exercise that you wish to return
     * @return returns SingleExercise that holds the same name as ExerciseName or SingleExercise called Unknown
     */
    public SingleExercise getExerciseWithString(String exerciseName){
        for (SingleExercise exercise : exercises) {
            if(exerciseName.equals(exercise.toString())){
                return exercise;
            }
        }
        return new SingleExercise("Unknown", 0);
    }

}
