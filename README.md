# Terminal_Map_Traversal
 Traversing through the map with your terminal!
The project is a game of traversing through a map with terminal commands. 

You should have a Java compiler on your laptop to run this game.
Clone this repo by this bash:
```
git clone https://github.com/hugoTheVoidBoy/Terminal_Map_Traversal
cd Terminal_Map_Traversal

```
Start the game by running **Game.java**

>Technical code features in my project:

-	In CommandWords.java, all the available command words are listed. A function “GetCommandList” is made to print out all command words when called.
-	Parser.java reads the input in the terminal.
-	Command.java checks the input syntax. 
-	Item.java constructs a description of an object in the map.
-	Room.java constructs a combination of hash maps (rooms and available exits) and an array list of available items in the room and related functions to print/add/get items.
-	Beamers.java is a subclass of Item. There are 2 random beamers in random rooms. When picked up, “charge” is required to memorize the current room and “Fire” is used to instantly go back to that room.
-	TransportationRoom.java used a list of rooms and Random objects to transport to a random room.
-	Game.java initializes the game with rooms, items and exits. Stack was used to push and pop to return to rooms in historical order. Functions from modules above are called in here to process the game. Array list is also used to hold/drop multiple items.
