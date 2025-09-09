public class Inventory {

    private Node head;


    private class Node{
        private Fish data;
        private Node next;
        private Node prev;

        public Node(Fish data){
            this.data = data;
            next = null;
            prev = null;
        }
    }

    public Node getHead(){
        return this.head;
    }




    public Inventory() {
        //adding a dummy node
        Fish myfish = new Fish("noName", 10000, 10000, 10000);
        head = new Node(myfish);
        

    }

    public void catchFish(Fish fish) {
        Node newNode = new Node(fish);
        //add the new node to the previous one
        Node temp = head;
        while(temp.next != null){
            temp = temp.next;

        }
        temp.next = newNode;
        newNode.prev = temp;
        
        sortList(head);
        
    }

    public void sortList(Node modifiedNode) {
    Node dummy = new Node(null); 
    Node current = modifiedNode.next;     

    while (current != null) {
        Node nextNode = current.next;

        Node position = dummy;
        while (position.next != null && position.next.data.compareTo(current.data) < 0) {
            position = position.next;
        }

        current.next = position.next;
        if (position.next != null) position.next.prev = current;

        position.next = current;
        current.prev = position;

        current = nextNode;
    }

    modifiedNode.next = dummy.next;
    if (dummy.next != null) {
        dummy.next.prev = modifiedNode;
    }
}



    public void sell(String species, int count) {
        Node temp = head.next;//dummy head skipped
        int available = 0;

        while(temp != null){
            if(temp.data.getSpecies().equalsIgnoreCase(species)){
                available++;
            }
            temp = temp.next;
        }
        temp = head.next;
        if(available >= count && count > 0){
            int removed = 0;
                    
                        while(temp != null && removed < count){
                            
                            if(temp.data.getSpecies().equalsIgnoreCase(species)){
                                //save reference to the next before removing
                                Node nextNode = temp.next;
                                if (temp.prev != null) temp.prev.next = temp.next;
                                if (temp.next != null) temp.next.prev = temp.prev;
                                removed++;
                                temp = nextNode;
                            }else{
                                temp = temp.next;
                            }
                        }
                        
                    
                    }
        
        
        }
        
    

    public void sellAll(String species) {
        Node temp = head.next;
        while(temp != null){
            if(temp.data.getSpecies().equalsIgnoreCase(species)){
                Node nextNode = temp.next;
                if(temp.next != null){ temp.next.prev = temp.prev;}
                if(temp.prev != null){ temp.prev.next = temp.next;}
                temp = nextNode;
            }else{
                temp = temp.next;
            }
            
        }
    }

    public boolean contains(String species, int count) {
    boolean result = false;
    int mycount = 0;

    if (count <= 0) {
        result = true;
    } else {
        Node temp = head.next;
        while (temp != null) {
            if (temp.data != null && temp.data.getSpecies().equalsIgnoreCase(species)) {
                mycount++;
                if (mycount >= count) {
                    result = true;
                    break;
                }
            }
            temp = temp.next;
        }
    }

    return result;
}


    public int count(String species) {
        int mycount = 0;

        Node temp = head.next;
        while(temp != null){
            if(temp.data.getSpecies().equalsIgnoreCase(species)){
                mycount++;
                temp = temp.next;
            }else{
                temp = temp.next;
            }
        }
        return mycount;
    }

    public int countAll() {
        int mycount = 0;
        Node temp = head.next;
        while(temp != null){
            mycount++;
            temp = temp.next;
        }
        return mycount;
    }

    public void absorb(Inventory otherInventory) {
        Node otherFirst = otherInventory.getHead().next;

        if(otherFirst != null){
            Node temp = head;
            while(temp.next != null){
                temp = temp.next;
            }

            temp.next = otherFirst;//not adding the dummy node
            otherFirst.prev = temp;

            
            sortList(head);
        }

        otherInventory.getHead().next = null;
        
        
        
        
    }
    
    public void clone(Inventory otherInventory) {
        Node temp = head;
        while(temp.next != null){
            temp = temp.next;

        }

        Node othertemp = otherInventory.getHead().next;//not including dummy node
        

        while(othertemp != null){
            Fish clonedFish = othertemp.data.clone();
            Node myNode = new Node(clonedFish);
            temp.next = myNode;
            myNode.prev = temp;
            temp = myNode;

            othertemp = othertemp.next;

        }

    }

    public Inventory duplicate() {
        Inventory result = new Inventory();
        Node temp = head.next;
        Node otherTemp = result.getHead();
        while(temp != null){
            Node myNode = new Node(temp.data.clone());
            otherTemp.next = myNode;
            myNode.prev = otherTemp;
            otherTemp = otherTemp.next;
            temp = temp.next;
        }

        return result;
    }

    public void print() {
        Node temp = head.next;//skip dummy
        while(temp != null){
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }
}