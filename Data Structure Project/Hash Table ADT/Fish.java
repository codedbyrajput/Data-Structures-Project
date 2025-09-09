public class Fish implements Comparable<Fish>{
    private String species;
    private int quality;
    private double size;
    private double weight;

    public Fish(String species, int quality, double size, double weight){
        this.species = species;
        this.quality = quality;
        this.size = size;
        this.weight = weight;
    }

    public String getSpecies(){
        return species;
    }

    public String toString(){
        return "Species: " + this.species + " of quality: " + this.quality + " size: " + this.size + " and weight: " + this.weight;
    }

    public Fish clone(){
        return new Fish(this.species, this.quality, this.size, this.weight);
    }

    public int compareTo(Fish obj){
        return this.species.compareToIgnoreCase(obj.getSpecies());
    }
}