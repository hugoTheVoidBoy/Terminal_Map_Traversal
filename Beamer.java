    
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Hugo Ngo
 * @version A2
 */

public class Beamer extends Item
{
    // instance variables - replace the example below with your own
    private Boolean charged = false;
    private Room beamerRoom = null;
    /**
     * Constructor for objects of class Beamer
     */
    public Beamer(String name, String description, double weight)
    {
        // initialise instance variables
        super(name, description, weight);
        beamerRoom = null;
        charged = false;
    }

    /**
     * Returns a description of the Beamer, including its
     * description and weight.
     * 
     * @return  A description of the Beamer
     */
    public String getDescription()
    {
        return super.getDescription();
    }
    
    /**
     * Returns the short name for Beamer.
     * 
     * @return  name of the beamer
     */
    public String getName() {
        return super.getName();
    }
    
    /**
     * Charge beamer and memorize the room
     * 
     * @param current Room
     */
    public void chargeBeamer(Room currentRoom){
        if (!charged){
            charged = true;
            beamerRoom = currentRoom;
            System.out.println("Beamer is charged");
        }
        else{
            System.out.println("Beamer already charged.");
        }
    }
    
    /**
     * Check if the beamer is charge.
     */
    public Boolean checkBeamercharged(){
        return charged;
    }
    
    /**
     * Fire beamer and go back to memorized room.
     * 
     * @return the memorized room
     */
    public Room fireBeamer(){
        if (charged){
            charged = false;
            System.out.println("Beamer is fired");
            return beamerRoom;
        }
        else{
            System.out.println("Beamer not charged.");
            return null;
        }
    }
}
