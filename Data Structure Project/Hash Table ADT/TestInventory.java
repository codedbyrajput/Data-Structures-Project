
public class TestInventory {
    private static boolean allTestsPass = true;

    public static void main(String args[]) {
        // Press G to meow
        boolean meow = args.length > 0 && args[0].equals("G");
        if (meow) {
            System.out.println("meow");
        }
        
        testAssertions();
        testEmptyInventory();
        testCatchingOneFishType();
        testCatchingMultipleFishTypes();
        testSell();
        testSellAll();
        testContains();
        testAbsorb();
        testClone();
        testDuplicate();
        testPrint();

        if (allTestsPass) {
            if (meow) {
                System.out.println("All tests pass :3");
            }
            else {
                System.out.println("All tests pass :)");
            }
        }
    }

    /**
     * Verifies that assertions are enabled, since they're required for the tests 
     * to work
     */
    private static void testAssertions() {
        try {
            assert false;
            allTestsPass = false;
            System.err.println("Assertions need to be enabled for the tests to work. Please run this program with assertions enabled (java -ea TestInventory).");
        } catch (AssertionError e) {
            // Yippee!
        }
    }

    /**
     * Tests a few use cases for an empty inventory
     */
    private static void testEmptyInventory() {
        try {
            Inventory inventory = new Inventory();

            assert inventory.countAll() == 0;
            assert inventory.contains("fish", 1) == false;
            assert inventory.contains("fish", 0) == true;
            assert inventory.count("fish") == 0;

            Fish fish = new Fish("Axolotl", 1, 37.5, 0.14);
            // Wait maybe I should change the name of this method
            assert inventory.contains(fish.getSpecies(), 1) == false;
            assert inventory.contains(fish.getSpecies(), 0) == true;
            assert inventory.count(fish.getSpecies()) == 0;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testEmptyInventory failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests behaviour of an inventory that contains multiple fish of the same
     * species
     */
    private static void testCatchingOneFishType() {
        try {
            Inventory inventory = new Inventory();

            Fish fish1 = new Fish("Rainbow Trout", 1, 75.0, 1.25);
            inventory.catchFish(fish1);
            assert inventory.countAll() == 1;
            assert inventory.count(fish1.getSpecies()) == 1;
            assert inventory.contains(fish1.getSpecies(), 1) == true;
            assert inventory.contains(fish1.getSpecies(), 2) == false;

            Fish fish2 = new Fish("Rainbow Trout", 3, 89.5, 1.25);
            inventory.catchFish(fish2);
            assert inventory.countAll() == 2;
            assert inventory.count(fish2.getSpecies()) == 2;
            assert inventory.contains(fish2.getSpecies(), 1) == true;
            assert inventory.contains(fish2.getSpecies(), 2) == true;

            // Since the second fish is the same species as the first, the counts 
            // should still be updated when we query the species of the first fish
            assert inventory.count(fish1.getSpecies()) == 2;
            assert inventory.contains(fish1.getSpecies(), 1) == true;
            assert inventory.contains(fish1.getSpecies(), 2) == true;
            assert inventory.contains(fish1.getSpecies(), 3) == false;

            // Inserting the same fish multiple times is, uh, undefined behaviour
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testCatchingOneFishType failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests behaviour of an inventory that contains fish of different species
     */
    private static void testCatchingMultipleFishTypes() {
        try {
            Inventory inventory = new Inventory();

            Fish fish1 = new Fish("Turtle", 2, 30.0, 0.35);
            inventory.catchFish(fish1);
            Fish fish2 = new Fish("Angelfish", 3, 25.0, 0.9);
            inventory.catchFish(fish2);

            assert inventory.countAll() == 2;
            assert inventory.count(fish1.getSpecies()) == 1;
            assert inventory.count(fish2.getSpecies()) == 1;
            assert inventory.contains(fish1.getSpecies(), 1) == true;
            assert inventory.contains(fish2.getSpecies(), 1) == true;
            assert inventory.contains(fish1.getSpecies(), 2) == false;
            assert inventory.contains(fish2.getSpecies(), 2) == false;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testCatchingMultipleFishType failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests selling fish
     */
    private static void testSell() {
        try {
            Inventory inventory = new Inventory();

            Fish toSell = new Fish("Clownfish", 1, 18.75, 1.0);
            Fish sameSpecies = new Fish("Clownfish", 1, 17.1, 1.0);
            Fish sameSpeciesAgain = new Fish("Clownfish", 1, 19.2, 1.0);
            Fish differentSpecies = new Fish("Dogfish", 2, 100.0, 0.3);
            inventory.catchFish(toSell);
            inventory.catchFish(sameSpecies);
            inventory.catchFish(sameSpeciesAgain);
            inventory.catchFish(differentSpecies);

            // Test pre-selling conditions
            assert inventory.countAll() == 4;
            assert inventory.count(toSell.getSpecies()) == 3;
            assert inventory.count(sameSpecies.getSpecies()) == 3;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 3;
            assert inventory.count(differentSpecies.getSpecies()) == 1;

            // Sell a fishy
            inventory.sell(toSell.getSpecies(), 1);
            assert inventory.countAll() == 3;
            assert inventory.count(toSell.getSpecies()) == 2;
            assert inventory.count(sameSpecies.getSpecies()) == 2;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 2;
            assert inventory.count(differentSpecies.getSpecies()) == 1;

            // Over-selling should do nothing
            inventory.sell(sameSpecies.getSpecies(), 3);
            assert inventory.countAll() == 3;
            assert inventory.count(toSell.getSpecies()) == 2;
            assert inventory.count(sameSpecies.getSpecies()) == 2;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 2;

            // Negative selling should do nothing
            inventory.sell(differentSpecies.getSpecies(), -1);
            assert inventory.countAll() == 3;
            assert inventory.count(differentSpecies.getSpecies()) == 1;

            // Selling a species that isn't in the inventory should do nothing
            inventory.sell("Soda Can", 1);
            assert inventory.countAll() == 3;
            assert inventory.count(toSell.getSpecies()) == 2;
            assert inventory.count(sameSpecies.getSpecies()) == 2;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 2;
            assert inventory.count(differentSpecies.getSpecies()) == 1;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testSell failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests selling all fish of a given species
     */
    private static void testSellAll() {
        try {
            Inventory inventory = new Inventory();

            Fish toSell = new Fish("Squid", 4, 287.5, 1.0);
            Fish sameSpecies = new Fish("Squid", 100, 1.0, 1.0);
            Fish sameSpeciesAgain = new Fish("Squid", 300, 2.718, 1.0);
            Fish differentSpecies = new Fish("Pupfish", 1, 8.75, 0.14);
            inventory.catchFish(toSell);
            inventory.catchFish(sameSpecies);
            inventory.catchFish(sameSpeciesAgain);
            inventory.catchFish(differentSpecies);

            // Test pre-selling conditions
            assert inventory.countAll() == 4;
            assert inventory.count(toSell.getSpecies()) == 3;
            assert inventory.count(sameSpecies.getSpecies()) == 3;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 3;
            assert inventory.count(differentSpecies.getSpecies()) == 1;

            // Sell all fish of a species
            inventory.sellAll(toSell.getSpecies());
            assert inventory.countAll() == 1;
            assert inventory.count(toSell.getSpecies()) == 0;
            assert inventory.count(sameSpecies.getSpecies()) == 0;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 0;
            assert inventory.count(differentSpecies.getSpecies()) == 1;

            // Selling all fish of a species that isn't in the inventory should do
            // nothing
            inventory.sellAll("Weed");
            assert inventory.countAll() == 1;
            assert inventory.count(toSell.getSpecies()) == 0;
            assert inventory.count(sameSpecies.getSpecies()) == 0;
            assert inventory.count(sameSpeciesAgain.getSpecies()) == 0;
            assert inventory.count(differentSpecies.getSpecies()) == 1;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testSellAll failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests the contains method
     */
    private static void testContains() {
        try {
            Inventory inventory = new Inventory();

            Fish fish = new Fish("Krill", 3, 6.25, 0.8);
            Fish sameSpecies = new Fish("Krill", 1, 5.0, 0.8);
            Fish sameSpeciesAgain = new Fish("Krill", 1, 6.2501, 0.8);
            inventory.catchFish(fish);
            inventory.catchFish(sameSpecies);
            inventory.catchFish(sameSpeciesAgain);
            assert inventory.contains("Krill", 1) == true;
            assert inventory.contains("Krill", 2) == true;
            assert inventory.contains("Krill", 3) == true;
            assert inventory.contains("Krill", 4) == false;

            // Nonpositive counts are always vacuously true
            assert inventory.contains("Unidentified Fish Object", 0) == true;
            assert inventory.contains("CREATURE", -1) == true;
            // (including when the species is in the inventory)
            assert inventory.contains("Krill", -1) == true;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testContains failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    // Not separately testing count or countAll since they're used in other 
    // tests

    /**
     * Tests the absorb method
     */
    private static void testAbsorb() {
        try {
            Inventory inventory = new Inventory();
            Inventory otherInventory = new Inventory();

            Fish fish = new Fish("Atlantic Salmon", 2, 43.75, 1.2);
            Fish sameSpecies = new Fish("Atlantic Salmon", 2, 43.75, 1.2);
            Fish sameSpeciesAgain = new Fish("Atlantic Salmon", 3, 43.07, 1.2);
            Fish differentSpecies = new Fish("Toad", 5, 12.5, 0.35);

            inventory.catchFish(fish);
            inventory.catchFish(sameSpecies);
            otherInventory.catchFish(sameSpeciesAgain);
            otherInventory.catchFish(differentSpecies);

            assert inventory.countAll() == 2;
            assert otherInventory.countAll() == 2;

            inventory.absorb(otherInventory);

            assert inventory.countAll() == 4;
            assert otherInventory.countAll() == 0;
            assert inventory.count(fish.getSpecies()) == 3;
            assert inventory.count(differentSpecies.getSpecies()) == 1;
            assert otherInventory.count(fish.getSpecies()) == 0;
            assert otherInventory.count(differentSpecies.getSpecies()) == 0;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testAbsorb failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests the clone method
     */
    private static void testClone() {
        try {
            // Same setup as testAbsorb
            Inventory inventory = new Inventory();
            Inventory otherInventory = new Inventory();

            Fish fish = new Fish("Atlantic Salmon", 2, 43.75, 1.2);
            Fish sameSpecies = new Fish("Atlantic Salmon", 2, 43.75, 1.2);
            Fish sameSpeciesAgain = new Fish("Atlantic Salmon", 3, 43.07, 1.2);
            Fish differentSpecies = new Fish("Toad", 5, 12.5, 0.35);

            inventory.catchFish(fish);
            inventory.catchFish(sameSpecies);
            otherInventory.catchFish(sameSpeciesAgain);
            otherInventory.catchFish(differentSpecies);

            assert inventory.countAll() == 2;
            assert otherInventory.countAll() == 2;

            inventory.clone(otherInventory);

            assert inventory.countAll() == 4;
            assert inventory.count(fish.getSpecies()) == 3;
            assert inventory.count(differentSpecies.getSpecies()) == 1;
            assert otherInventory.countAll() == 2;
            assert otherInventory.count(fish.getSpecies()) == 1;
            assert otherInventory.count(differentSpecies.getSpecies()) == 1;
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testClone failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * Tests the duplicate method
     */
    private static void testDuplicate() {
        try {
            Inventory inventory = new Inventory();

            Fish fish1 = new Fish("Gar", 4, 218.75, 0.18);
            Fish fish2 = new Fish("Manta Ray", 1, 750, 0.18);
            Fish fish3 = new Fish("Snail", 4, 5, 0.6);

            inventory.catchFish(fish1);
            inventory.catchFish(fish2);

            Inventory duplicate = inventory.duplicate();

            // The duplicate should have the same contents as the original
            assert duplicate.countAll() == inventory.countAll();
            assert duplicate.count(fish1.getSpecies()) 
                == inventory.count(fish1.getSpecies());
            assert duplicate.count(fish2.getSpecies()) 
                == inventory.count(fish2.getSpecies());
            assert duplicate.count(fish3.getSpecies()) 
                == inventory.count(fish3.getSpecies());

            // Modifying the duplicate should not affect the original, and vice 
            // versa
            inventory.sell(fish1.getSpecies(), 1);
            duplicate.catchFish(fish3);
            assert duplicate.countAll() != inventory.countAll();
            assert duplicate.count(fish1.getSpecies()) 
                != inventory.count(fish1.getSpecies());
            assert duplicate.count(fish2.getSpecies()) 
                == inventory.count(fish2.getSpecies());
            assert duplicate.count(fish3.getSpecies()) 
                != inventory.count(fish3.getSpecies());
        } catch (AssertionError e) {
            allTestsPass = false;
            System.err.println(
                "testDuplicate failed on line " 
                + e.getStackTrace()[0].getLineNumber()
            );
        }
    }

    /**
     * 'Tests' the print method
     */
    private static void testPrint() {
        Inventory inventory = new Inventory();
        inventory.catchFish(new Fish("Great White Shark", 3, 225, 0.12));
        inventory.catchFish(new Fish("Great White Shark", 2, 224, 0.12));
        inventory.catchFish(new Fish("Leedsichthys", 3, 337.5, 0.12));

        // Sell a fish so you can check that fish that used to be in the 
        // inventory aren't printed
        Fish toRemove = new Fish("Golden Bass", 5, 56.25, 0.03);
        inventory.sell(toRemove.getSpecies(), 1);
        
        // So yeah verify it yourself I guess lol
        System.err.println("Example printed inventory (verify manually that it looks correct):");
        inventory.print();
    }
}
