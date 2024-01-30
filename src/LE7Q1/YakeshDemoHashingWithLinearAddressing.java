package LE7Q1;

import java.util.Arrays;
import java.util.Scanner;
public class YakeshDemoHashingWithLinearAddressing{
    public static int items, tableCapacity;
    public static double lf;
    public static Scanner input = new Scanner(System.in);
    public static YakeshValueEntry[] hashTable;
    public static YakeshValueEntry[] workingHashTable;

    public static void addValueLinearProbe(int x) {//solve collisions using linear probing
        int hashIndex = hashFunction(x);//getting hashed value
        boolean spaceFound = false;
        //if nothing is in space put value there
        if (hashTable[hashIndex].getKey() == -111 || hashTable[hashIndex].getKey() == -1) {
            hashTable[hashIndex].setKey(x);
            spaceFound = true;
        }
        else{//if space is taken move through entire array until you find a spot, or you reach the original hashIndex
            int counter = 1;
            //using mod function to wrap around array
            for(int i = (hashIndex + 1) % hashTable.length; i != hashIndex; i = (hashIndex +counter) % hashTable.length){
                //spot found put value there and exit loop
                if (hashTable[i].getKey() == -111 || hashTable[i].getKey() == -1) {
                    hashTable[i].setKey(x);
                    spaceFound = true;
                    return;
                }
                else{//go to next index
                    counter = counter + 1;
                }
            }
        }
        if(!spaceFound){//if gone through entire array and no spot found then there is no space
            System.out.printf("%d cant be added. No more space ", x);
        }
    }

    public static void removeLinearProbing(int x) {//find and remove values using linear probing
        int hashIndex = hashFunction(x);
        boolean found = false;
        //if original index contains the value, set the value to -111(available)
        if(hashTable[hashIndex].getKey() == x){
            hashTable[hashIndex].setKey(-111);
            System.out.printf("%d is found and removed! ", x);
            found = true;
        }
        else{//go through entire array until -1(null) or you reach the original hashIndex
            int counter = 1;
            for(int i = (hashIndex + counter) % hashTable.length; i != hashIndex; i = (hashIndex + counter)%hashTable.length){
                //if value is found change spot to available and exit loop
                if(hashTable[i].getKey() == x){
                    hashTable[i].setKey(-111);
                    System.out.printf("%d is found and removed! ", x);
                    found = true;
                    return;
                }//if reached null exit loop and let user know the value is not in array
                else if(hashTable[i].getKey() == -1){
                    System.out.printf("%d is not found! ", x);
                    return;
                }
                else{//go to next index
                    counter = counter + 1;
                }
            }
        }
        if(!found){//if value is never found after going through entire array
            System.out.printf("%d is not found! ", x);
        }
    }

    public static void printHashTable() {//print current hashTable
        String[] array = new String[tableCapacity];
        //convert hashTable to String array, so I can put "null" and "available instead of -1 and -111
        for (int i = 0; i < tableCapacity; i++) {
            if (hashTable[i].getKey() == -1) {
                array[i] = "null";
            } else if (hashTable[i].getKey() == -111) {
                array[i] = "available";
            } else {
                array[i] = Integer.toString(hashTable[i].getKey());
            }
        }//print out string array using toString method
        System.out.printf("The Current Hash-Table: %s\n", Arrays.toString(array));
    }

    public static int checkPrime(int n) {//check prime from lab manual / returns next prime
        int m = n / 2;//we just need to check half of the n factors
        for (int i = 3; i <= m; i++) {
            if (n % i == 0) {//if n is not a prime number
                i = 2; //reset i to 2 so that it is incremented to 3 in the forheader
                //System.out.printf("i = %d\n",i);
                n++;//next n value
                m = n / 2;//we just need to check half of the n factors
            }
        }
        return n;
    }//end of checkPrime()

    public static int hashFunction(int x) {//converts value to hashvalue
        //mod value by table length
        //if modded value is negative add the table length to it
        int hashValue = x % hashTable.length;
        if (hashValue < 0) {
            hashValue = hashValue + hashTable.length;
        }
        return hashValue;
    }

    public static void rehashingWithLinearProbing() {
        workingHashTable = hashTable;//moving to temp array
        tableCapacity = checkPrime(tableCapacity *2);
        //creating new array with double the space and prime
        //setting hashtable to new table with more space
        YakeshValueEntry[] temp = new YakeshValueEntry[tableCapacity];
        for(int i = 0; i < temp.length; i++){
            temp[i] = new YakeshValueEntry();
        }
        hashTable = temp;
        //putting all the elements backs into the rehashed table
        for (int i = 0; i < workingHashTable.length; i++) {
            if (workingHashTable[i].getKey() != -1 && workingHashTable[i].getKey() != -111) {
                addValueLinearProbe(workingHashTable[i].getKey());
            }

        }
    }

    public static void main(String[] args){
        myHeader(7,1);
        //getting user inputs and finding appropriate table capacity
        items = inputCheck("Let's decide on the initial table capacity based on the load factor and dataset size\n" +
                "How many data items: ",1);
        lf = inputCheck("What is the load factor (Recommended: <= 0.5): ",0.001,1.00);
        tableCapacity = checkPrime((int)(Math.ceil(items/lf)));
        System.out.printf("The minimum required table capacity would be: %d\n", tableCapacity);
        //creating array of YakeshValueEntry and creating new instances without parameters
        hashTable = new YakeshValueEntry[tableCapacity];
        for(int i = 0; i< tableCapacity; i++){
            hashTable[i] = new YakeshValueEntry();
        }
        //getting value and adding to hashtable then printing hashtable
        for(int i = 0; i < items; i++){
            addValueLinearProbe(inputCheck(String.format("Enter Item %d: ",i+1)));
        }
        printHashTable();
        //removing 2 elements and adding 1 element
        System.out.print("\nLet's remove two values from the table and then add one.....\n\n");
        removeLinearProbing(inputCheck("Enter a value you want to remove: "));
        printHashTable();
        removeLinearProbing(inputCheck("Enter a value you want to remove: "));
        printHashTable();
        addValueLinearProbe(inputCheck("Enter a value to add to the table: "));
        printHashTable();
        System.out.print("\n\nRehashing the table...\n");
        rehashingWithLinearProbing();//rehash and print table out
        System.out.printf("The rehashed table capacity is: %d\n", hashTable.length);
        printHashTable();
        myFooter(7,1);
    }

    public static int inputCheck(String message, int min){//input check with min value
        int val;
        while(true){
            try{
                System.out.print(message);
                val = Integer.parseInt(input.nextLine());//this way no buffer issues
                if(val < min){
                    throw new RuntimeException("Invalid Input\n");
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.print("Invalid Input\n");
            }
            catch (RuntimeException e){
                System.out.print(e.getMessage());
            }
        }

        return val;
    }

    public static int inputCheck(String message){//input check without min and max
        int val;
        while(true){
            try{
                System.out.print(message);
                val = Integer.parseInt(input.nextLine());//this way no buffer issues
                break;
            }
            catch (NumberFormatException e){
                System.out.print("Invalid Input\n");
            }
            catch (RuntimeException e){
                System.out.print(e.getMessage());
            }
        }
        return val;
    }

    public static double inputCheck(String message, double min, double max){//input check with min and max
        double val;
        while(true){
            try{
                System.out.print(message);
                val = Double.parseDouble(input.nextLine());//this way no buffer issues
                if(val < min || val > max){
                    throw new RuntimeException("Invalid Input\n");
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.print("Invalid Input\n");
            }
            catch (RuntimeException e){
                System.out.print(e.getMessage());
            }
        }

        return val;
    }
    public static void myHeader(int lab_number, int q_number){//Header method
        System.out.printf("=======================================================\n" +
                "Lab Exercise %d-Q%d\n" +
                "Name: Yakesh Umachandran\n" +
                "Student Number: 251303571\n" +
                "Goal of this Exercise: Timing different sorting algorithms\n" +
                "=======================================================\n",lab_number, q_number);
    }
    public static void myFooter(int lab_number, int q_number){//Footer Method
        System.out.printf("=======================================================\n" +
                "Completion of Lab Exercise %d-Q%d is successful!\n" +
                "Signing off - Yakesh\n" +
                "=======================================================", lab_number,q_number);
    }
}