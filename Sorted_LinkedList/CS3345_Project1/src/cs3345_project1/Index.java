package cs3345_project1;

public class Index {
    private Link head;
    
    //constructor for an empty LinkedList
    public Index(){
        head = new Link("", null);
    }
    
    //This method insert a new name into the LinkedList by create
    //a new link and link to the one before that.
    public void insert(String name){
        
        //if list empty, create new node with letter of the name
        if (head.next == null){ 
            Link newAlpha = new Link(String.valueOf(name.charAt(0)), null);
            head.next = newAlpha;
            
            //Then make the new link with the name
            Link newL = new Link(name, null);
            newAlpha.next = newL;
        }
        //If head is not null, then the list already have items.
        else{
            boolean changed = false;    //Set a flag to check for if a link is added
            Link current = head.next;   //set the current to the head.next
            
            //The while loop check for name value and set the name in between
            while (current != null){
                //If the name is less than the name we want to give to the new link then execute
                if (current.name.compareTo(name) < 1){
                    //if the name is more than the name we want to give to the link then execute,
                    //because of this, the name is in between alphabetically ordered.
                    if ((current.next == null || current.next.name.compareTo(name) > 1) &&
                        current.name.charAt(0) != name.charAt(0)){
                        Link newAlpha = new Link(String.valueOf(name.charAt(0)), null);
                        newAlpha.next = current.next;   //Set new alphabet letter
                        current.next = newAlpha;        //Point to the new link from the head location
                        
                        Link newL = new Link(name, null);   //After we add alpha letter, we add new link with the name
                        newL.next = newAlpha.next;          //Point the new link to the next of alpha letter
                        newAlpha.next = newL;               //piont the alpha letter next to the new link
                        
                        changed = true;                     //Set the flag to true 
                    }
                    //This case is for when there is already a letter, then we add the link of the name only
                    else if((current.next == null || current.next.name.compareTo(name) > 1) &&
                            current.name.charAt(0) == name.charAt(0)){
                        Link newL = new Link(name, null);       //Create new link with name
                        newL.next = current.next;               //Point the new link to the current's next link
                        current.next = newL;                    //Point the current's next link to the new link
                        
                        changed = true;                         //Set change to true
                    }
                }
                else
                    System.out.println("Something wrong!");     //If none of the case work, print out error message
                
                if (changed)                                    //if detected change, break out of the loop
                    break;
                current = current.next;                         //increment node
            }
        }
    }
    
    //this method will remove the name of the person, 
    //It will also remove the letter and all the name that start with that letter
    //Or it will also remove the letter if the name happen to be the last in the list beside the letter
    public void remove(String name){
        char alpha = name.charAt(0);        //Get name's first initial
        Link loc = head;                    //Get the location of head
        Link firstLetter = head.next;       //Get the location of the first letter
        Link temp = head.next;              //Create a temp link at the head.next location

        //This while will step through the entire list
        while (temp.next != null){
            //When encounter the letter that belong to the first initial of the name get the location
            if (temp.next.name.charAt(0) != alpha){
               loc = temp.next;
            }
            
            //If the argument is 1 letter, then that mean we want to remove the letter and it entire 
            //name that start with that letter.
            //This case will find the name have one letter and remove it
            if ((name.length() == 1 && name.equals(temp.next.name))){
                //this while loop traverse the list until the next name that doesn't have 
                //the same letter initial
                while (temp.next != null && (temp.next.name.charAt(0) == alpha)){
                    temp = temp.next;
                }
                Link loc2 = temp.next;  //create another location where the name have different initial
                temp.next = loc;        //Set the current location before the letter we want to delete
                temp.next.next = loc2;  //now set the current.next location to the location of the name that
                                        //have different initial, then this will remove the letter and all of the names
            }
            
            //This case will remove the letter at the beginning of the list, this is a special case.
            //This case deteced the letter and remove it at the beginning case only. all other case
            //work for the case above.
            else if(firstLetter.name.equals(name)){
                while (temp.next != null && (temp.next.name.charAt(0) == alpha)){
                    temp = temp.next;
                }
                head.next = temp.next;
                break;
            }
            //This case remove only the name
            else if (temp.next.name.equals(name)){
                //Remove just the name in the front of the list
               if (temp.next == head.next.next && (temp.next.next.name.charAt(0) != alpha)){
                   head = head.next.next;
               }
               //this case will remove the name regularly, between the node.
               else if(temp.next.next == null){
                   //if only one letter left remove the letter
                   if (temp.name.length() == 1){
                       temp = loc;
                   }
                   //else we can just remove regularly
                    temp.next = null;
                    break;
               }
               //This case handle the end of the list, if the list reach the end, it will remove the letter and all it component 
               //at the end of the list.
               else if (temp.next.next != null && (temp.next.next.name.charAt(0) != alpha)){
                   loc.next = temp.next.next;
               }
               temp.next = temp.next.next; //this case is when the name is not the only one left in the alphabet
            }     
            temp = temp.next;
        } 
    }
    
    //This method will step through all the node and find the name
    //return true if the name is in the list, false otherwise.
    public boolean find(String name)
    {
      Link temp = head;
        while (temp.next != null){
            if ((temp.name).equals(name)){
            return true;
            }
            temp = temp.next;
        }
        
        return false;
    }
    
    //This method will travese the entire list and print them out.
    public void printList(){
        Link temp = head.next;
        while (temp != null){
            System.out.print(temp.name + " : ");
            temp = temp.next;
        }
        
        System.out.println();
    }
    
    //This class is the individual node
    class Link{
        protected String name;
        protected Link next;
        
        //constructor for a new link
        private Link(String name, Link next){
            this.name = name;
            this.next = next;
        }
    }
    
    //Main method
    public static void main(String[] args){
        //Make an empty LinkedList
        Index list = new Index();
        list.insert("Alex");
        list.insert("Allen");
        list.insert("Bianca");
        list.insert("Brandon");
        list.insert("Cathy");
        list.insert("Ben");
        list.insert("X-man");
        list.insert("Yami");
        list.insert("Timmy");
        list.insert("Danny");
        
        //Print the list using printList method
        list.printList();
        
        //Find a person using the method find
        boolean found = list.find("Alex");
        found = list.find("B");
        
        System.out.println("Found: " + found);
        
        list.remove("A"); //remove a letter
        list.printList();
        
        list.remove("Ben"); //remove a person
        list.printList();
        
        list.remove("Cathy"); //remove a person and a letter behind it
        list.printList();
    }
}
