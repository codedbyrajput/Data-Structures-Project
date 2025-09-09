# Inventory ADT – Linked List & Hash Table Implementation
This project was developed as part of COMP 2140 – Data Structures (University of Manitoba). It focuses on designing and analyzing an Inventory Abstract Data Type (ADT) with multiple data structure implementations and a study of probabilistic techniques for efficient data processing.  

# Project Highlights
Sorted Linked List Implementation: Designed with advanced features (dummy nodes, doubly linked, circular) to efficiently manage sorted inventory.  
Hash Table Implementation: Built from scratch using polynomial hashing with Horner’s method and separate chaining for collision resolution.  
Practical Context: Modeled as a fishing game inventory system where players can catch, sell, clone, duplicate, or absorb inventories.   
Probabilistic Data Structures: Explored an Estimator ADT (similar to Count-Min Sketch) to demonstrate space–time tradeoffs between accuracy and memory.  

# Tech Stack
Java – Core implementation of ADTs  
Custom Data Structures – No reliance on Java’s built-in collections (ArrayList, HashMap, etc.)  
Asymptotic Analysis – Runtime and memory efficiency comparison  
Features & Operations  
The Inventory ADT supports advanced operations beyond standard insert/delete:  
catchFish(Fish fish) - Add fish to inventory  
sell(species, count) - Remove a number of fish of a given species  
sellAll(species) - Remove all fish of a species  
contains(species, count) - Check if enough fish exist  
count(species) & countAll() - Count fish by species or in total  
absorb(otherInventory) - Merge inventories, leaving one empty  
clone(otherInventory) - Copy inventory without modifying source  
duplicate() - Return a deep copy of the inventory  

# Learning Outcomes  
Mastery of linked lists and hash tables through full ADT implementation.  
Practical application of polynomial hashing and separate chaining.  
Exposure to probabilistic algorithms and their accuracy/memory tradeoffs.  
Strengthened skills in algorithm design, complexity analysis, and clean coding.  

# License
This project is provided without a license. That means all rights are reserved, others may view the code, but reuse, modification, and distribution are not permitted.  


