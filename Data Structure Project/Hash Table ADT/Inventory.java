public class Inventory { 

    private int size = 29;// table size kept global to change it easily and size equal to 29 as there are 26 aplphabets and 29 is the nearest prime number
    private HashNode[] table;// array of linked list
    
    private class HashNode{
        private HashNode next;
        private Fish value;

        public HashNode(Fish value){// Node class for chaining
            
            this.value = value;
            this.next = null;
        }
    }

    
    public int getHashCode(String key){
        int base = 13;
        int hash = 0;
        for (int i = 0; i < key.length(); i++){
            hash = (hash * base + (int) key.charAt(i)) % size;
        }
        return hash;
    }

    public Inventory() {
        table = new HashNode[size];
    }

    public void catchFish(Fish fish) {
        String data = fish.getSpecies();
        int code = getHashCode(data);
        HashNode newNode = new HashNode(fish);
        if(table[code] == null){
            table[code] = newNode;
        }else{
            HashNode temp = table[code];// get head
            while(temp.next != null){
                temp = temp.next;
            }

            temp.next = newNode;
        }
    }

    public void sell(String species, int count) {
        int mycount = 0;
        int fishCode = getHashCode(species);// got the index for the fish type
        if(count > 0 && table[fishCode] != null){
            
            HashNode temp = table[fishCode];
           
            while (temp != null) {
                if (temp.value.getSpecies().equalsIgnoreCase(species)) {
                mycount++;
                }
                temp = temp.next;
            }

            if(mycount >= count){
                HashNode curr = table[fishCode];
                HashNode prev = null;
                int deletion = 0;
                
                while(curr != null && deletion < count ){
                    if(curr.value.getSpecies().equalsIgnoreCase(species)){
                        if(prev == null){
                            // head deletion

                            table[fishCode] = curr.next;
                            curr = table[fishCode];
                        }else{
                            prev.next = curr.next;
                            curr = prev.next;
                        }
                        deletion++;
                    }else{
                        prev = curr;
                        curr = curr.next;
                    }
                }
            }
            
        
        }
        
    }

    public void sellAll(String species) {
        int count = 0;
        int code = getHashCode(species);
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                HashNode temp = table[i];
                while(temp != null){
                    
                    if(temp.value.getSpecies().equalsIgnoreCase(species)){
                        count ++;
                    }
                    temp = temp.next;
                }
                
            }
        }
        sell(species, count);
        
    }

    public boolean contains(String species, int count) {
        boolean check = false;
        int code = getHashCode(species);
        
        int mycount = 0;

        for(int i = 0; i < table.length; i++){
            HashNode temp = table[i];
            while(temp != null){
                if(temp.value.getSpecies().equalsIgnoreCase(species)){
                    mycount++;
                };
                temp = temp.next;
                
            }

            
        }
        if(mycount >= count){
                check = true;
            }
        

        return check;
    }

    public int count(String species) {
        int mycount = 0;
        for(int i = 0; i < table.length; i++){
            HashNode temp = table[i];
            while(temp != null){
                if(temp.value.getSpecies().equalsIgnoreCase(species)){
                    mycount++;
                    temp = temp.next;
                }else{
                    temp = temp.next;
                }
            }
        }

        return mycount;
    }

    public int countAll() {
        int mycount = 0;

        for(int i = 0; i < table.length; i++){
            HashNode mynode = table[i];
            while(mynode != null){
                mycount++;
                mynode = mynode.next;
            }
        }

        return mycount;
    }

    public void absorb(Inventory otherInventory) {
        for(int i = 0; i < otherInventory.table.length; i++){
            HashNode temp = otherInventory.table[i];
            while(temp != null){
                Fish myfish = temp.value;
                catchFish(myfish);
                temp = temp.next;
            }
            otherInventory.table[i] = null;
        }
    }
    
    public void clone(Inventory otherInventory) {
        for(int i = 0; i < otherInventory.table.length ; i++){
            HashNode temp = otherInventory.table[i];

            while(temp != null){
                Fish myfish = temp.value.clone();
                catchFish(myfish);
                temp = temp.next;
            }
        }
    }

    public Inventory duplicate() {
        Inventory result = new Inventory();
        for(int i = 0; i < table.length ; i++){
            HashNode temp = table[i];
            while(temp != null){
                Fish myfish = temp.value.clone();
                result.catchFish(myfish);
                temp = temp.next;
            }
        }
        return result;
    }

    public void print() {
        for(int i = 0; i < table.length ; i++){
            HashNode temp = table[i];
            while(temp != null){
                System.out.println(temp.value);
                temp = temp.next;
            }
        }

    }
}