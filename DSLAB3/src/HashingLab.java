import java.io.*;
import java.util.*;

/*
    Author: Tyler Wagner
    Date Created: 11/6/22
    Last Modified: 11/6/22
 */

public class HashingLab {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Enter the name of the file: ");
        Scanner kb = new Scanner(System.in);
        String answer = kb.nextLine();

        File file = new File(answer);
        Scanner input = new Scanner(file);



        addElementstoTable(input);

    }

    public static void addElementstoTable(Scanner input)
    {
        int i = 1;


        //        <Type of Key, Type of mapped value>
        // in my case I want to have an int, so I can see what number the items are
        // and I want to store strings
        // the size will be 128 and the initial load factor will be 40
        Hashtable<Integer, String> table1 = new Hashtable<>(128, 0.40f);

        //this is where I will loop to store the values of the text file

        while(input.hasNextLine())
        {
            //Stores the values of the text file in the table
            //with the initial value of 1, and it will move up each time
            String text = input.nextLine();
            table1.put(i,text);
            i++;

        }
        System.out.println("Initial Map" + table1);

    }



    public static int Searching()
    {
        // YOU WILL NEED TO CHANGE THIS VALUE GENIUS
        return 0; //LOOK HERE IF YOU THINK YOU ARE GETTING WEIRD NUMBERS
        //LOOK ABOVE THIS PLEASE
    }

}
