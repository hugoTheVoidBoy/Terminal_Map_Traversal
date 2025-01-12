import java.util.Stack;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes 
 * @version 2006.03.30
 * 
 * @author Hugo Ngo
 * @version A2
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRoomStack;
    private Item  playerItem; 
    private Boolean eaten = false;
    private ArrayList<Item> carrying = new ArrayList<Item>(5);
    private Beamer beamer1, beamer2, beamer;
    private Room transporter;
    
    /**
     * Constructor for Game class. 
     * 
     * Preset parser and stack of previous rooms.
     *
     */
    public Game() {
        {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        }

    }

    /**
     * Create all the rooms, setting items and link their exits together.
     */
    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        // Create items
        Item chair1, chair2, chair3, chair4, bar, computer1, computer2, computer3, tree1, tree2;
        Item cookie1, cookie2;
        
        chair1 = new Item("chair","a wooden chair", 5);
        chair2 = new Item("chair","a wooden chair", 5);
        chair3 = new Item("chair","a wooden chair", 5);
        chair4 = new Item("chair","a wooden chair", 5);
        bar = new Item("bar","a long bar with stools", 95.67);
        computer1 = new Item("PC","a PC", 10);
        computer2 = new Item("Mac", "a Mac", 5);
        computer3 = new Item("PC","a PC", 10);
        tree1 = new Item("tree", "a fir tree", 500.5);
        tree2 = new Item("tree","a fir tree", 500.5);
        cookie1 = new Item("cookie","a cookie", 0.1); // New item - cookie
        cookie2 = new Item("cookie","a cookie", 0.1);
        beamer1= new Beamer("beamer","a beamer-ator", 888);
        beamer2= new Beamer("beamer","a beamer-ator", 888);
        beamer= new Beamer("beamer","a beamer-ator", 888);
        
        // Create rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter = new TransporterRoom("in a mysterious transporter room");
        //Add room list
        TransporterRoom.addRoom(outside);
        TransporterRoom.addRoom(theatre);
        TransporterRoom.addRoom(pub);
        TransporterRoom.addRoom(lab);
        TransporterRoom.addRoom(office);
        
        
        // Add items to rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        theatre.addItem(chair1);
        pub.addItem(bar);
        lab.addItem(chair2);
        lab.addItem(computer1);
        lab.addItem(chair3);
        lab.addItem(computer2);
        office.addItem(chair4);
        office.addItem(computer3);
        pub.addItem(cookie1); // Add a cookie to the room
        theatre.addItem(cookie2);
        pub.addItem(beamer1); // Add a beamer to the room
        theatre.addItem(beamer2);
        
        // Initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("neverland", transporter);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);


        currentRoom = outside;  // Start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("eat")) {
            eat(command);
        } else if (commandWord.equals("back")) {
            back(command);
        } else if (commandWord.equals("stackBack")) {
            stackBack(command);
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("charge")) {
            charge(command);
        }
        else if (commandWord.equals("fire")) {
            fire(command);
        }
        return wantToQuit;
        }
    

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * If we are in transporter room, then go to random room.
     * @param command The command to be processed
     */
    private void goRoom(Command command) {
        if (currentRoom != transporter){
            if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Go where?");
                return;
            }

            String direction = command.getSecondWord();

            // Try to leave current room.
            Room nextRoom = currentRoom.getExit(direction);

            if (nextRoom == null) {
                System.out.println("There is no door!");
            }
            else {
                previousRoom = currentRoom; // store the previous room
                previousRoomStack.push(currentRoom); // and add to previous room stack
                currentRoom = nextRoom;
                look(command);
            }
        }
        
        else{
            currentRoom = TransporterRoom.getExit();
        }
    }   

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    /** 
     * "Look" was entered. Print current room, then what we are holding.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) {
        if (eaten == false){
            System.out.println(currentRoom.getLongDescription());
            if (playerItem != null) {
                System.out.println("You are carrying: " + playerItem.getDescription());
            } else {
                System.out.println("You are not carrying anything.");
            }
        }
        else{
            System.out.println(currentRoom.getLongDescription());
            System.out.println("You are holding ");
            if (carrying.size() == 0){
                System.out.println("         nothing");
            }
            else{
                for (Item i: carrying){
                System.out.println("        "+i.getDescription());
            }
            }
        }
    }

    /** 
     * "Eat" was entered. If holding cookie, then eat the cookie and get strong
     * Else print error message.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) {
        if (playerItem != null && playerItem.getDescription().contains("cookie")) {
            System.out.println("You have eaten the cookie.");
            eaten = true;
            playerItem = null; // Remove the eaten cookie from player's inventory
        } else {
            System.out.println("You have no food to eat.");
        }
    }

    /** 
     * "Back" was entered. Go back to the previous room.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) {
    
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                look(command);
            }
        }
    }
    

    /** 
     * "StackBack" was entered. Go back rooms in timely order.
     * @param command The command to be processed
     */
    private void stackBack(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                look(command);
            }
        }
    }

    /**
     * "take" is enterd as 2 words command. Only pick 1 item.
     * Pick 5 if just eaten a cookie.
     * 
     * @param command The command to be processed
     */
    private void take(Command command) {
        
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

            String itemName = command.getSecondWord();
            Item item = currentRoom.getItem(itemName);
        if (eaten == false){
            if (item == null) {
                System.out.println("That item is not in the room.");
            } else if (playerItem != null) {
                System.out.println("You are already holding something.");
            } else {
                playerItem = item;
                currentRoom.removeItem(itemName);
                System.out.println("You picked up " + item.getName() + ".");
            }
        }
        else{
            if (carrying.size() < 5){
                carrying.add(item);
                currentRoom.removeItem(itemName);
                System.out.println("You picked up " + item.getName() + ".");
            }
            else{
                System.out.println("You already carried 5 items");
            }
        }
    }
    
    /**
     * "Drop" everything that is being held by the player.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command) {
        if (eaten == false){
            if (playerItem == null) {
                System.out.println("You have nothing to drop.");
            } else {
                currentRoom.addItem(playerItem);
                System.out.println("You have dropped " + playerItem.getName() + ".");
                playerItem = null;
            }
        }
        else{
            for (Item i: carrying){
                currentRoom.addItem(i);
            }
            System.out.println("You dropped all items");
            eaten = false;
        }
    }
    
    /**
     * "Charge" beamer and remember current room.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command) {
        if (playerItem == beamer1 || playerItem == beamer2){
            beamer.chargeBeamer(currentRoom);
        }
        else{
            System.out.println("You are not holding a beamer!");
        }
        
    }
    /**
     * "Fire" beamer and go to the room where beamer was charged.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command) {
        if (playerItem == beamer1 || playerItem == beamer2){
            Room x = currentRoom;
            currentRoom = beamer.fireBeamer();
            if (currentRoom == null){
                currentRoom = x;
            }
        }
        else{
            System.out.println("You are not holding a beamer!");
        }        
    }
}

