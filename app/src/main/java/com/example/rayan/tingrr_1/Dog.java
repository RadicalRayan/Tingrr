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

    public String description() {
        String d = "";
        if (size.equals("S")) {
            d+="The " + name + " is a small dog which does not need a large yard. ";
        } else if (size.equals("M")) {
            d+="The " + name + " is a medium sized dog which will need a moderately large yard. ";
        } else if (size.equals("L")){
            d+="The " + name + " is a large dog which will need a large sized yard to be most comfortable. ";
        }

        if (energyLevel.equals("L")) {
            d+="The dog has a very high energy level. ";
        } else if(energyLevel.equals("M")) {
            d+="The dog has a medium energy level. ";
        } else if(energyLevel.equals("S")) {
            d+="The dog has a low energy level. ";
        }

        if (grooming.equals("L")) {
            d+="This dog will require a lot of grooming. ";
        } else if(grooming.equals("M")) {
            d+="This dog will require an average amount of grooming. ";
        } else if(grooming.equals("S")) {
            d+="This dog will require little grooming. ";
        }

        if (climate.equals("W")) {
            d+="This dog will do well in warmer climates. ";
        } else if (climate.equals("C")) {
            d+="This dog will do better in colder environments. ";
        } else if (climate.equals("N")) {
            d+="This dog will do well in both cold and warm environments. ";
        }

        if (kids.equals("Y")) {
            d+="This dog is fine with kids. ";
        } else if (kids.equals("N")) {
            d+="This dog would not be well behaved with kids. ";
        }

        if (allergenic.equals("N")) {
            d+="This dog is not hypoallergenic. If you have allergies, you may have an allergic reaction with the dog. ";
        } else if (allergenic.equals("Y")) {
            d+="This dog is hypoallergenic. This dog will not cause significant allergies compared to other dogs. ";
        }

        if (space.equals("A")) {
            d+="This dog would do best in an apartment. ";
        } else if (space.equals("H")) {
            d+="This dog would do best in a house. ";
        } else if (space.equals("N")) {
            d+="This dog would do well in either a house or an apartment. ";
        }

        return d;
    }

}
