package com.example.rayan.tingrr_1;

/**
 * Created by FredQ on 3/20/2018.
 */

public class Dog {
    private String name;
    private String size;
    private String space;
    private String energyLevel;
    private String grooming;
    private String climate;
    private String allergenic;
    private String kids;
    private int points;

    public Dog(String newName, String newSize, String newSpace, String newEnergyLevel, String newGrooming, String newClimate, String newAllergenic, String newKids){
        name = newName;
        size = newSize;
        space = newSpace;
        energyLevel = newEnergyLevel;
        grooming = newGrooming;
        climate = newClimate;
        allergenic = newAllergenic;
        kids = newKids;
    }
    public void addPoints(int add) {
        points += add;
    }
    public int getPoints() {
        return points;
    }
    public void increment(String in, int keyValue) {
        if (keyValue == 0) {
            if (in.equals(allergenic)) {
                addPoints(10);
            }
        }
        if (keyValue == 1) {
            if (in.equals(kids)) {
                addPoints(10);
            }
        }
        if (keyValue == 2) {
            if (in.equals(climate)) {
                addPoints(10);
            }
        }
        if (keyValue == 3) {
            if (in.equals(space) || space.equals('N')) {
                addPoints(10);
            }
        }
        if (keyValue == 4) {
            if (in.equals(size)) {
                addPoints(10);
            }
        }
        if (keyValue == 5) {
            if (in.equals(energyLevel)) {
                addPoints(10);
            }
        }
        if (keyValue == 6) {
            if (in.equals(grooming)) {
                addPoints(10);
            }
        }
    }
    public String toString() {
        //return (name + size + energyLevel + grooming + climate + allergenic);
        return name + points;
    }

}
