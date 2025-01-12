
/**
 * This class represents an item which may be put
 * in a room in the game of Zuul.
 * 
 * @author Hugo Ngo 
 * @version A2
 */
public class Item
{
    // description of the item
    private String description;
    
    private String name;
    // weight of the item in kilgrams 
    private double weight;

    /**
     * Constructor for objects of class Item.
     * 
     * @param name The name of item
     * @param description The description of the item
     * @param weight The weight of the item
     */
    public Item(String name, String description, double weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * Returns a description of the item, including its
     * description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return description + " that weighs " + weight + "kg.";
    }
    
    /**
     * Returns the short name for item.
     * 
     * @return  name of the item
     */
    public String getName() {
        return name;
    }
    
}
