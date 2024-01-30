package LE7Q2;
import LE7Q1.*;
import static LE7Q1.YakeshDemoHashingWithLinearAddressing.*;

public class YakeshDemoHashingWIthAllOpenAddressingTechniques {
    public static void addValueQuadraticProbe(int x){//solve collisions using quadratic probing
        boolean added = false;
        int hashValue = hashFunction(x);
        int counter = 0;
        //start from original hashIndex and move to hashIndex + counter^2
        //if no spot is found after visiting half the locations stop
        for (int i = (hashValue + (int) Math.pow(counter, 2)) % hashTable.length; counter <= (hashTable.length)/2; i = ((hashValue + (int) Math.pow(counter, 2)) % hashTable.length)) {
            //if spot found add value and leave loop
            if(hashTable[i].getKey() == -1 || hashTable[i].getKey() == -111 ){
                hashTable[i].setKey(x);
                added = true;
                return;
            }else{//increase counter by 1
                counter = counter + 1;}
        }
        if(!added){//if not able to find spot after going through array then the load factor needs to be reduced
            System.out.print("Probing failed! Use a load factor of 0.5 or less\n");
        }
    }

    public static void addValueDoubleHashing(int x){//solve collisions using doublehashing
        int hashValue = hashFunction(x);
        boolean added = false;
        int counter = 0;
        //finding second hash value using formula q - q mod k
        int hashVal2 = thePrimeNumberForSecondHashFunction(hashTable.length) - (x%thePrimeNumberForSecondHashFunction(hashTable.length) );
        //start from original hashIndex and move to the original hashValue + the counter * second hashvalue
        for(int i = hashValue % hashTable.length; counter < hashTable.length; i = (hashValue + (counter * hashVal2)) % hashTable.length){
            //if spot found add value and exit loop
            if(hashTable[i].getKey() == -1 || hashTable[i].getKey() == -111 ){
                hashTable[i].setKey(x);
                added = true;
                return;
            }else{//increase counter
                counter = counter + 1;}
        }
        if(!added){//if no spots found after going through entire array, array is full
            System.out.print("Array Full");
        }
    }

    public static void emptyHashTable(){
        //go through entire array and set each key to -1(null)
        for (int i = 0; i< hashTable.length; i++){
            hashTable[i].setKey(-1);
        }
    }


    public static int thePrimeNumberForSecondHashFunction(int q){//finding next lowest prime
        q=q-1;//subtracting one because q is already prime
        int m = q / 2;//we just need to check half of the n factors
        for (int i = 3; i <= m; i++) {
            if (q % i == 0) {//if n is not a prime number
                i = 2; //reset i to 2 so that it is incremented to 3 in the forheader
                //System.out.printf("i = %d\n",i);
                q--;//changed to -- to go down
                m = q / 2;//we just need to check half of the n factors
            }
        }
        return q;
    }

    public static void printArray(Integer[] x){
        //print each element of array
        System.out.printf("The given data set is: [");
        for(int i = 0; i<x.length-1;i++){
            System.out.printf("%d, ", x[i]);
        }
        System.out.printf("%d]\n", x[x.length-1]);
    }

    public static void main(String[] args){
        myHeader(7,1);
        //finding appropriate table capacity and creating hashtable accordingly
        items = inputCheck("Let's demonstrate our understanding on all the open addressing techniques...\n" +
                "How many data items: ",1);
        lf = inputCheck("What is the load factor (Recommended: <= 0.5): ",0.001,1.00);
        tableCapacity = checkPrime((int)(Math.ceil(items/lf)));
        System.out.printf("The minimum required table capacity would be: %d\n", tableCapacity);
        hashTable = new YakeshValueEntry[tableCapacity];
        for(int i = 0; i< tableCapacity; i++){
            hashTable[i] = new YakeshValueEntry();
        }
        Integer[] values = {7,14,-21,-28,35};
        System.out.print("The given dataset is: ");
        printArray(values);
        System.out.print("Let's enter each data item from the above to the hash table:\n");
//adding each item in values array to hashtable using linear probing to resolve collisions
        System.out.print("\nAdding data - Linear probing resolves collision\n");
        for(int i = 0; i < values.length; i++){
            addValueLinearProbe(values[i]);
        }
        printHashTable();
        //emptying array so elements can be added using another way to resolve collisions
        emptyHashTable();
//adding each item in values array to hashtable using quadratic probing to resolve collisions
        System.out.print("\nAdding data - Quadratic probing resolves collision\n");
        for(int i = 0; i < values.length; i++){
            addValueQuadraticProbe(values[i]);
        }
        printHashTable();
        emptyHashTable();
//adding each item in values array to hashtable using double hashing to resolve collisions
        System.out.printf("\nAdding data - Double hashing resolves collision\n" +
                "The q value for double hashing is: %d\n", thePrimeNumberForSecondHashFunction(hashTable.length));
        for(int i = 0; i < values.length; i++){
            addValueDoubleHashing(values[i]);
        }
        printHashTable();
        emptyHashTable();
        myFooter(7,1);

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
