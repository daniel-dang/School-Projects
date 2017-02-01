package cs3345_project3;

import java.util.Arrays;

//The linearProbingHashTable class
public class LinearProbingHashTable<K,V> {
    //variables
    private int size = 10;
    private int entryAdded = 0;
    //Generic array
    private Entry<K,V> table[] = new Entry[size];
    
    //default constructor for hash table
    public LinearProbingHashTable(){
    }
    
    //The entry class
    private static class Entry<K,V>{
        //variables
        private K key;
        private V value;
        private String status;
        
        //Entry constructor
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
            this.status = "";       //regular insert will always have empty status
        }
        
        //This method is to set status such as "delete" to an entry
        //and not having to delete the entire entry
        public void setStatus(String status){
            this.status = status;
        }
    }
    
    //The insert method.
    //This method will insert the key and value in an entry in the table 
    //if the key is not duplicate
    public boolean insert(K key, V value){
        //if they key is null tell the user key invalid 
        if (key == null){
            System.out.println("Null key, try enter value again.");
            System.exit(0);
        }
        //get hash code
        int hashCode;
        hashCode = key.hashCode() % table.length;
        
        //without collision
        //This will insert an entry in the table without collision
        if (table[hashCode] == null && hashCode < table.length){
            Entry newEntry = new Entry(key, value);             //make a new entry
            table[hashCode] = newEntry;                         //add the new entry to the array
            entryAdded++;                                       //increment the number of entries added
            if (entryAdded >= (table.length / 2))               //check if the table is half full
                rehash();
            return true;                                        //return true for successfully added an entry
        }
        //if the element is not empty but has been delete prevoius, we reuse
        else if(table[hashCode].status.equals("delete")){
            Entry newEntry = new Entry(key, value);             //these steps are the same as above steps
            table[hashCode] = newEntry;
            entryAdded++;
            if (entryAdded >= (table.length / 2 ))
                rehash();
        }
        //if collision occur try the next one if it is not a dupliate
        else if(table[hashCode] != null && !table[hashCode].key.equals(key)){
            int temp = hashCode + 1;                                //pick the next index in the array to check
            boolean reInserted = false;                             //set flag 
            //This array will continue to iterate unless the item has been reinserted
            do{                
                //try insert into the next one
                if (temp != table.length && table[temp] == null){
                    Entry newEntry = new Entry(key, value);
                    table[temp] = newEntry;
                    reInserted = true;                              //if we added a new entry, flag go true
                    
                    entryAdded++;
                    if (entryAdded >= (table.length / 2))           //check for table if half full
                        rehash();
                }
                else if(temp == table.length)                       //if reach the end of the table, go back to the top
                    temp = 0;
                else                                                //increment index
                    temp++;
            }while(!reInserted);
        }
        
        //if no case match return false;
        return false;
    }
    
    //The rehash function
    //This function will take the first table, make a second table, rehash everything from the 
    //first table with a new hash function and put all entries in the new hash table.
    private void rehash(){
        Entry<K,V> temp[] = new Entry[table.length * 2];                    //make a new table
        
        int hashCode;
        
        //This for loop will loop through the entire first table and rehash everything over to the
        //second table
        for (int i = 0; i < table.length; i++)
        {
            //if the index in the second table is empty
            if (table[i] != null){
                hashCode = table[i].key.hashCode() % table.length;          //new hash function
            
                //without collision
                if (temp[hashCode] == null && hashCode < temp.length){
                    temp[hashCode] = table[i];                              //set entry from first table to the second table
                    temp[hashCode].setStatus(table[i].status);              //carry the status from the first table over too
                }
                //if collision occur try the next one if it is not a dupliate
                else if(temp[hashCode] != null){                                    //these code are similair to the above
                    int tempCode = hashCode + 1;
                    boolean reInserted = false;
                    do{                
                        //try insert into the next one
                        if (tempCode != temp.length && temp[tempCode] == null){
                            temp[tempCode] = table[i];
                            temp[tempCode].setStatus(table[i].status);
                            reInserted = true;
                        }
                        else if(tempCode == temp.length)
                            tempCode = 0;
                        else
                            tempCode++;
                    }while(!reInserted);
                }
            }
        }
        
        table = temp;                       //set the new table to the pointer of the first table
    }
    
    //The find method
    //This method will find a key and return it value, null if no key found
    public V find(K key){
        //this is the hash value before probe
        int hashValue = key.hashCode() % table.length;
        int startingPoint = hashValue;
        int count = 0;
        
        boolean found = false;
        //loop until we find the key
        while(!found){
            //search throuh element that are not null
            if (table[hashValue] != null){
                //check if the element have the same key user looking for
                //and if it is the same key return the value, otherwise return null
                if ((hashValue == startingPoint) && count != 0)
                    return null;
                else if (table[hashValue].key.equals(key))
                    return table[hashValue].value;
            }
            //if the program go back to where we began to search for the key and does not find it
            //return null since no key existed
            else if ((hashValue == startingPoint) && count != 0)
                    return null;
            else{
                count++;
            }
            hashValue++;
            if (hashValue == table.length)
                hashValue = 0;
        }
        return null;
    }
    
    //The delete function
    //this funciton will mark an entry as delete but leave it there.
    //This function will search though and look for the entry that have 
    //the same key as the one that passed in, mark it as delete and return true
    public boolean delete(K key){
        //this is the hash value before probe
        int hashValue = key.hashCode() % table.length;
        int startingPoint = hashValue;
        int count = 0;
        
        boolean found = false;
        //loop until we find the key
        while(!found){
            if (table[hashValue] != null){
                if ((hashValue == startingPoint) && count != 0)
                    return false;
                else if (table[hashValue].key.equals(key)){
                    table[hashValue].setStatus("delete");           //mark as delete and return true
                    return true;
                }
            }
            else if ((hashValue == startingPoint) && count != 0)
                    return false;
            else{
                count++;
            }
            hashValue++;
            if (hashValue == table.length)
                hashValue = 0;
        }
        return false;
    }
    
    //The getHashValue function
    //This function will get the hash value of a key
    //This hash value is the hash value of the key before probing
    public int getHashValue(K key){
        
        //this is the hash value before probe
        int temp = key.hashCode() % table.length;
        int startingPoint = temp;
        int hashValue = temp;
        int count = 0;
        
        boolean found = false;
        //loop until we find the key
        while(!found){
            if (table[temp] != null){
                if ((temp == startingPoint) && count != 0)
                    return -1;
                else if (table[temp].key.equals(key))
                    return hashValue;                           //if the key found, return it hash value, else return -1 as not found
            }
            else if ((temp == startingPoint) && count != 0)
                    return -1;
            else{
                count++;
            }
            temp++;
            if (temp == table.length)
                temp = 0;
        }
        return -1;
    }
    
    //The getLocation function
    //This function will get the location to the key that were passed in
    //If found it return the index location of the key, else it return -1 as not found.
    public int getLocation(K key){
        //this is the hash value before probe
        int hashValue = key.hashCode() % table.length;
        int startingPoint = hashValue;
        int count = 0;
        
        boolean found = false;
        //loop until we find the key
        while(!found){
            if (table[hashValue] != null){
                if ((hashValue == startingPoint) && count != 0)
                    return -1;
                else if (table[hashValue].key.equals(key))
                    return hashValue;
            }
            else if ((hashValue == startingPoint) && count != 0)
                    return -1;
            else{
                count++;
            }
            hashValue++;
            if (hashValue == table.length)
                hashValue = 0;
        }
        return -1;
    }
    
    //This toSTring function will add the table as a long string and return to the caller (main)
    //For the entry that have sttaus delete, it add the status instead of the value. 
    @Override
    public String toString(){
        String hashString = "";
        
        for (int i = 0; i < table.length; i++){
            if (table[i] == null){
                hashString += i + " " + "null" + "\n";
            }
            else{
                if (table[i].status.equals("delete")){
                    hashString += i + " " + table[i].status + "\n";
                }
                else{
                    hashString += i + " " + table[i].value + "\n";
                }
            }
        }
        return hashString;
    }
    
    public static void main(String args[]){
        LinearProbingHashTable<Integer, String> testTable = new LinearProbingHashTable<>();
        
        //This is an empty hash table, initial size is 10
        System.out.println("Empty hash table.");
        System.out.print(testTable.toString());
        
        //insert 4 no rehash needed
        testTable.insert(80, "Item 1");
        testTable.insert(78, "Item 2");
        testTable.insert(65, "Item 3");
        testTable.insert(45, "Item 4");
        
        //Show hash table again after inserte 4 entries
        System.out.println("\n-------------------------------------------------\n");
        System.out.println("Insert 4 entries, no re-hash needed.");
        System.out.print(testTable.toString());
        
        //Insert the 5th item, which is half the table size will cause the program to rehash
        testTable.insert(44, "Item 5");
        
        //Show hash table again after rehash has occured
        System.out.println("\n-------------------------------------------------\n");
        System.out.println("Insert 5th item, table re-hashed.");
        System.out.print(testTable.toString());
        
        //find a key
        //this case is valid so the key was returned
        System.out.println("\nTest find function.");
        String value = testTable.find(65);
        System.out.println("Value for key 65 is: " + value);
        //this case is not valid so the return value is null
        String invalidValue = testTable.find(66);
        System.out.println("Value for key 66 is: " + invalidValue);
        
        //Delete a key
        System.out.println("\nTest delete function.");
        System.out.println("Delete a key: " + testTable.delete(45));
        
        //Show hash table again after delete a key has occured
        System.out.println("\n-------------------------------------------------\n");
        System.out.println("Entry is marked delete after delete operation.");
        System.out.print(testTable.toString());
        
        //get a hash value before probing occur
        //to do that we have to insert a value that will need linear probing
        testTable.insert(28, "Item 6");     //this value will end up with hash value of 8
                                            //and it will need to linear probing since index 8 already taken
        
        System.out.println("\n-------------------------------------------------\n");
        System.out.println("Hash table after linear probing occur.");
        System.out.print(testTable.toString());     //we see that item 6 is now in index 9
        
        //We can find the hash value to know if item 6 has been move to a different index
        System.out.println("\nTest getHashValue function.");
        int iniHashVal = testTable.getHashValue(28);
        System.out.println("The original hash value for key 28 is: " + iniHashVal);
        
        
        //Now we test for the function getLocation
        System.out.println("\nTest getLocation function.");
        int location = testTable.getLocation(80);
        System.out.println("The location for key 80 is: " + location);
    }
}
