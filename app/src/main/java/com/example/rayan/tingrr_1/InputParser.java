package com.example.rayan.tingrr_1;

/**
 * Created by FredQ on 3/20/2018.
 */
import java.io.*;
import java.util.*;
public class InputParser {
    public static void main (String [] args) throws IOException {
        String dogFile = "C:\\Users\\FredQ\\eclipse-workspace\\Dog App\\src\\DogInfo.txt";
        String QFile = "C:\\Users\\FredQ\\eclipse-workspace\\Dog App\\src\\Questions.txt";
        Dog[] dogs = parseDogs(dogFile);
        Question[] questions = parseQuestions(QFile);
        Scanner reader = new Scanner(System.in);
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i].getQuestion());
            String[] options = questions[i].getOptions();
            for(String s:options) {
                System.out.println(s);
            }
            String in = reader.next();
            for(Dog dog: dogs) {
                dog.increment(in, questions[i].getKey());
                //System.out.println(dog);
            }
        }
        reader.close();
        SortDogs sortThem = new SortDogs();
        sortThem.sort(dogs, 0, dogs.length-1);
        System.out.println("Matches:");
        for (Dog dog: dogs) {
            System.out.println(dog);
        }
    }
    public static Dog[] parseDogs(String fileName) {
        String line = null;
        Dog[] allDogs = new Dog[29];
        int i = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader b = new BufferedReader(fileReader);
            while((line = b.readLine()) != null) {
                StringTokenizer inDog = new StringTokenizer(line);
                String name = inDog.nextToken();
                String size = inDog.nextToken();
                String space = inDog.nextToken();
                String ability = inDog.nextToken();
                String energyLevel = inDog.nextToken();
                String grooming = inDog.nextToken();
                String climate = inDog.nextToken();
                String allergenic = inDog.nextToken();
                String kids = inDog.nextToken();
                Dog newDog = new Dog(name, size, space, energyLevel, grooming, climate, allergenic, kids);
                allDogs[i] = newDog;
                //System.out.println(newDog);
                i++;
            }
            b.close();
        }catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return allDogs;
    }
    public static Question[] parseQuestions(String fileName) {
        String line = null;
        Question[] questions = new Question[0];;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader b = new BufferedReader(fileReader);
            line = b.readLine();
            StringTokenizer lineOne = new StringTokenizer(line);
            int numQuestions = Integer.parseInt(lineOne.nextToken());
            questions = new Question[numQuestions];
            for (int i = 0; i < questions.length; i++) {
                line = b.readLine();
                StringTokenizer input = new StringTokenizer(line);
                String question = "";
                String next = input.nextToken();
                while (!next.equals("?")) {
                    question += " " + next;
                    next = input.nextToken();
                }
                question += "?";
                int keyValue = Integer.parseInt(input.nextToken());
                int numChoices = Integer.parseInt(input.nextToken());
                String[] options = new String[numChoices];
                String[] values = new String[numChoices];
                for (int j = 0; j < numChoices; j++) {
                    line = b.readLine();
                    StringTokenizer choice = new StringTokenizer(line);
                    options[j] = choice.nextToken();
                    values[j] = choice.nextToken();
                }
                Question Q = new Question(question, keyValue, numChoices, options, values);
                questions[i] = Q;
                //System.out.println(Q);
            }
            b.close();
        }catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return questions;
    }
}
//ghg