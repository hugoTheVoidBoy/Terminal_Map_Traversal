import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Class TransporterRoom - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "TransporterRoom" represents the room that can transport to a randomm room.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Hugo Ngo
 * @version A2
 */
public class TransporterRoom extends Room {
    private static Random random = new Random();
    private static List<Room> rooms = new ArrayList<>();
    
    /**
     * Constructor based on the super class Room.
     */
    public TransporterRoom(String description) {
        super(description);
    }

    
    /**
     * Get the random room to go to.
     * 
     * @return the random room.
     */
    public static Room getExit() {
        return findRandomRoom();
    }

    /**
     * Get random room in the room list based on the random index.
     * 
     * @return room index.
     */
    private static Room findRandomRoom() {
        int index = random.nextInt(getNumRooms());
        return getRoom(index);
    }
    

    /**
     * Add room to the room list
     */
    public static void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Get the amount of room in the room list.
     * 
     * @return list size.
     */
    public static int getNumRooms() {
        return rooms.size();
    }

    /**
     * Get the room in the list based on the random index.
     * 
     * @param index that is random
     * @return room.
     */
    public static Room getRoom(int index) {
        if (index >= 0 && index < rooms.size()) {
            return rooms.get(index);
        } else {
            return null;
        }
    }
}
