/*
    Author: Tyler Wagner
    Date Created: 11/27/22
 */

import java.io.*;
import java.util.*;

public class HashingLab {

    String[] sArray;
    int[] probes = new int[257];
    int arraySize;

    public static String[] addItems = new String[257];

    public static void main(String[] args) {
        try
        {
            File file = new File("TableValues.txt");
            Scanner scanner = new Scanner(file);

            //populate the array
            while(scanner.hasNext())
            {
                for (int i = 0; i < 257; i++)
                {
                    String line = scanner.nextLine();
                    addItems[i] = line;
                    //System.out.println(i);
                }

            }

        }catch (Exception e)
        {
            System.out.println("Error: "+ e);
        }

        //System.out.println(Arrays.toString(addItems));



        //LINEAR
        //HashingLab halfTable = new HashingLab(257, "half", "linear");
        //HashingLab bHalfTable = new  HashingLab (257, "burris", "linear");
        //HashingLab eightyFive = new HashingLab(257, "85", "linear");
        //HashingLab bEightyFive = new HashingLab(257, "burris85", "linear");



        //RANDOM
        //HashingLab halfRandom = new HashingLab(257, "half", "random");
        //HashingLab bHalfRandom = new HashingLab(257, "burris", "random");
        //HashingLab eightyFiveRandom = new HashingLab(257, "85", "random");
        //HashingLab bEightyFiveRandom = new HashingLab(257, "burris85", "random");

        //use this to see if the items are actually in the list
        //System.out.println(Arrays.toString(addItems));

        //LINEAR
        //halfTable.display("linear");
        //bHalfTable.display("liner");
        //eightyFive.display("linear");
        //bEightyFive.display("linear");

        //RANDOM
        //halfRandom.display("random");
        //bHalfRandom.display("random");
        //eightyFiveRandom.display("random");
        //bEightyFiveRandom.display("random");

    }




    //BURRIS HASH
    public int burrisFunction(String w2h)
    {

        int keyValue = (w2h.charAt(1) + w2h.charAt(3)) / 256 + w2h.charAt(6) + 30;

        return keyValue;
    }




    //OUR HASH
    public int hashFunction(String w2h)
    {
        //System.out.println("in HashFunction Method");
        int keyValue = 0;

        //System.out.println(w2h.length());

        for(int j = 0; j < w2h.length(); j++) {
            int character = w2h.charAt(j) - 97;
            keyValue = Math.abs((keyValue * 28 + character) % 129);
            //System.out.println("Key Value: " + keyValue);
        }
        return keyValue;
    }




    HashingLab(int size, String method, String type)
    {
        //System.out.println("in HashingLab Method");
        arraySize = size;
        sArray = new String[size];

        //fill with empty strings
        for(int i = 0; i < 257; i++)
        {
            sArray[i] = "            ";
        }

        if(method.equalsIgnoreCase("half"))
        {
            if (type.equalsIgnoreCase("linear"))
            {
                half(addItems, "linear");
            }
            else {
                half(addItems, "random");
            }
        }
        else if (method.equalsIgnoreCase("85"))
        {
            if (type.equalsIgnoreCase("linear"))
            {
                eightyFive(addItems, "linear");
            }
            else
            {
                eightyFive(addItems, "random");
            }
        }

        else if (method.equalsIgnoreCase("burris"))
        {
            if (type.equalsIgnoreCase("linear"))
            {
                bHalf(addItems, "linear");

            }
            else
            {
                bHalf(addItems, "random");
            }
        }
        else if (method.equalsIgnoreCase("burris85"))
        {
            if (type.equalsIgnoreCase("linear"))
            {
                bEightyFive(addItems, "linear");
            }
            else
            {
                bEightyFive(addItems, "random");
            }

        }
        else
        {
            if (type.equalsIgnoreCase("linear"))
            {
                addArray(addItems, "linear");
            }
            else
            {
                addArray(addItems, "random");
            }
        }
    }




    //OUR FUNCTION
    public void randomInsert(String word) {
        //System.out.println("in randomInsert Method");
        String w2h = ensure16(word);
        int numOfProbes = 0;
        int r = 1;

        //calc the key for the word

        int key = hashFunction(w2h);

        while (!sArray[key].equals("            ")) {
            key = genRandom(r) + numOfProbes;
            numOfProbes = numOfProbes + 1;
            //System.out.println(key);
        }

        sArray[key] = w2h;
        probes[key] = numOfProbes;
    }




    //BURRIS FUNCTION
    public void bRandomInsert(String word) {
        //System.out.println("in randomInsert Method");
        String w2h = ensure16(word);
        int numOfProbes = 0;
        int r = 1;

        //calc the key for the word

        int key = burrisFunction(w2h);

        while (!sArray[key].equals("            ")) {
            key = genRandom(r) + numOfProbes;
            numOfProbes = numOfProbes + 1;
            //System.out.println(key);
        }

        sArray[key] = w2h;
        probes[key] = numOfProbes;
    }


    public int genRandom(int r)
    {
        //System.out.println("in genRandom Method");
        int q = 0;
        int p = 0;
        while(p != 1)
        {
            r = r * 5;
            r = r % (2 ^ 257);
            q = r / 4;
            p = r;
        }
        return r;
    }



    //OUR HASH
    public void linearInsert(String word)
    {
        //System.out.println("in linearInsert Method");
        String w2h = ensure16(word);
        int numProbes = 0;

        int key = hashFunction(w2h);

        while (!sArray[key].equals("            ")) {
            if(key > 199)
            {
                key = 0;
            }
            key = key + 1;
            numProbes = numProbes + 1;
        }

        sArray[key] = w2h;
        probes[key] = numProbes;
    }



    //BURRIS HASH
    public void bLinearInsert(String word)
    {
        //System.out.println("in linearInsert Method");
        String w2h = ensure16(word);
        int numProbes = 0;

        int key = burrisFunction(w2h);

        while (!sArray[key].equals("            ")) {
            if(key > 199)
            {
                key = 0;
            }
            key = key + 1;
            numProbes = numProbes + 1;
        }

        sArray[key] = w2h;
        probes[key] = numProbes;
    }


    public String ensure16(String word)
    {
        //System.out.println("in ensure16 Method");
        if (word.length() < 20)
        {
            int remainder = 20 - word.length();

            for (int i = 0; i < remainder; i++)
            {
                word = word + "   ";
            }
        }
        else
        {
            word = word;
        }
        return word;
    }

    public void addArray(String[] addItems, String method)
    {
        //System.out.println("in addArray Method");
        for (int i = 0; i < addItems.length; i++)
        {
            String word = addItems[i];

            if (method.equalsIgnoreCase("linear"))
            {
                linearInsert(word);
            }
            else
            {
                randomInsert(word);
            }
        }
    }



    //OUR FUNCTION
    public void half(String[] addItems, String method)
    {
        //System.out.println("in half Method");
        for(int i = 0; i < 103; i++)
        {
            String word = addItems[i];

            if (method.equalsIgnoreCase("linear"))
            {
                linearInsert(word);
            }
            else
            {
                randomInsert(word);
            }
        }
    }






    //FOR BURRIS FUNCTION
    public void bHalf(String[] addItems, String method)
    {
        //System.out.println("in half Method");
        for(int i = 0; i < 103; i++)
        {
            String word = addItems[i];

            if (method.equalsIgnoreCase("linear"))
            {
                bLinearInsert(word);
            }
            else
            {
                bRandomInsert(word);
            }
        }
    }






    //OUR FUNCTION
    public void eightyFive(String[] addItems, String method)
    {
        //System.out.println("in eightyFive Method");
        for (int j = 0; j < 170; j++)
        {
            String word = addItems[j];

            if (method.equalsIgnoreCase("linear"))
            {
                linearInsert(word);
            }
            else
            {
                randomInsert(word);
            }
        }
    }



    //BURRIS FUNCTION
    public void bEightyFive(String[] addItems, String method)
    {
        //System.out.println("in eightyFive Method");
        for (int j = 0; j < 170; j++)
        {
            String word = addItems[j];

            if (method.equalsIgnoreCase("linear"))
            {
                bLinearInsert(word);
            }
            else
            {
                bRandomInsert(word);
            }
        }
    }

    public void display(String type)
    {
        //System.out.println("in display Method");
        for (int i = 0; i < 7; i = i + 3)
        {
            System.out.println("|  " + i + "  |  " + i + 1 + "  |  " + i + 2 + "  |" );
            System.out.println("| " + sArray[i] + probes[i] +" | "+ sArray[i + 1] + probes [i + 1] + " | " + sArray[i + 2] + probes[i + 2] + " |");
        }

        for (int i  = 10; i < 97; i = i + 3)
        {
            System.out.println("|  " + i + "  |  " + i + 1 + "  |  " + i + 2 + "  |" );
            System.out.println("| " + sArray[i] + probes[i] +" | "+ sArray[i + 1] + probes [i + 1] + " | " + sArray[i + 2] + probes[i + 2] + " |");
        }

        for (int i = 100; i < 197; i = i + 3)
        {
            System.out.println("|  " + i + "  |  " + i + 1 + "  |  " + i + 2 + "  |" );
            System.out.println("| " + sArray[i] + probes[i] +" | "+ sArray[i + 1] + probes [i + 1] + " | " + sArray[i + 2] + probes[i + 2] + " |");
        }

        System.out.println("First 30: ");
        displayStats(probes, 0 , 29);
        System.out.println("\n");
        System.out.println("Last 30: ");
        displayStats(probes, 169, 199);
        System.out.println("");

        if (type.equalsIgnoreCase("linear"))
        {
            double linearTheroretical = calculateTheoretical(30, 128, "linear");
            System.out.println("Theoretical expected number of probes using linear: " + linearTheroretical);

        }
        else
        {
            double theoriticalNumofProbes = calculateTheoretical(30, 128, "random");

            System.out.println("Theoretical number of probes using random: " + theoriticalNumofProbes);
        }
    }

    public double avgProbes(int probes[], int start, int finals)
    {
        //System.out.println("in avgProbe Method");
        int sum = 0;
        int records = finals - start;
        for(int i = 0; i <= records; i++)
        {
            sum = sum + probes[start + i];
        }

        double avg = 1.0d * sum / records;
        return  avg;
    }

    public void displayStats(int[] probes, int min, int max)
    {
        //System.out.println("in displayStats Method");
        System.out.println("Average probes: "+ avgProbes(probes, min, max));
        System.out.println("Minimum probes: "+minProbes(probes, min, max));
        System.out.println("Max probes: "+ maxProbes(probes, min, max));

    }

    public double calculateTheoretical(double keys, double tSize, String type)
    {
        //System.out.println("in calcTheoretical Method");
        if(type.equalsIgnoreCase("linear"))
        {
            double load = (keys/tSize);
            double theoreticalProbes = ((1 - load) / 2)/(1 - load);
            return theoreticalProbes;
        }
        else
        {
            double load = (keys / tSize);
            double theoreticalProbes = -((1/ load) / 2) * Math.log(1-load);
            return theoreticalProbes;
        }
    }

    public int minProbes(int[] array, int start, int finals)
    {
        //System.out.println("in minProbes Method");
        int minValue = array[start];
        int d = finals - start;
        for(int i = 0; i <= d; i++)
        {
            minValue = array[start + i];
        }
        return minValue;
    }

    public static int maxProbes(int[] array, int start, int finals)
    {
        //System.out.println("in maxProbes Method");
        int max = array[start];
        int d = finals - start;
        for(int i = 0; i <= d; i++)
        {
            if(array[start + i] > max)
            {
                max = array[start + i];
            }
        }

        return max;
    }


}
