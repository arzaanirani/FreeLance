from game_data import World, Item, Location
from player import Player
import os.path


def check_saved_games(world, player):
    '''
    Loads a game if it exists.
    The save file is formatted so that each line contains a different variable of the game
    Each line is read in and parsed.

    :param world: The adventure world
    :param player: The player
    '''
    if os.path.isfile("saves.txt"):  # If there is a save
        file = open("saves.txt", 'r')  # Get the save
        lines = file.readlines()  # Read lines
        file.close()  # Close

        position = lines[0].replace("\n", "").split(" ")  # Holds player position
        score = int(lines[1].replace("\n", ""))
        movesLeft = int(lines[2].replace("\n", ""))
        inventory = lines[3].replace("\n", "").split(" ")  # Holds player inventory
        delivered_items = lines[4].replace("\n", "").split(" ")  # Holds the delivered items
        keys = lines[5].replace("\n", "").split(" ")

        player.x = int(position[0])  # Set x
        player.y = int(position[1])  # Set y
        player.score = score  # Set score
        player.movesLeft = movesLeft  # Set moves left

        for item_id in inventory:  # Go through each item
            if not (item_id == ""):  # If it's not blank
                item = world.get_item(item_id)  # Get the item
                for location in world.locations:  # Go through the locations and remove it
                    location.remove_item(item)
                player.add_item(item)  # Add it to the player inventory

        for item_id in delivered_items:  # Go through each item
            if not (item_id == ""):  # If it's not blank
                item = world.get_item(item_id)  # Get the item
                player.add_delivered_item()  # Add count to delivered items
                for location in world.locations:  # Go through the locations and remove it
                    location.remove_item(item)

        for key_id in keys:  # Go through each item
            if not (key_id == ""):  # If it's not blank
                key = world.get_item(key_id)  # Get the item
                player.used_keys.append(key)
                for location in world.locations:  # Go through the locations and remove it
                    if location.locNum == key.target:
                        location.door = False


def save_game(world, player):
    '''
    Saves the current game that the player is playing.
    The save file is formatted so that each line contains a different variable of the game.
    Specific variables within the game are written to the lines of the text file.

    :param world: The adventure world
    :param player: The player
    '''
    file = open("saves.txt", 'w')  # Create or overwrite the file
    file.write(str(player.x) + " " + str(player.y) + "\n")  # Write the x and y position
    file.write(str(player.score) + "\n")  # Write the player score
    file.write(str(player.movesLeft) + "\n")  # Write the moves left

    inventory = ""
    for item in player.inventory:
        inventory += item.item_id + " "  # Write all items within inventory
    file.write(inventory + "\n")

    delivered_items = ""
    for item in world.delivered_items:
        delivered_items += item.item_id + " "  # Write all items that were delivered
    file.write(delivered_items + "\n")

    keys = ""
    for key in player.used_keys:
        keys += key.item_id + " "  # Write all keys that were used
    file.write(keys + "\n")
    file.close()


if __name__ == "__main__":
    WORLD = World("map.txt", "locations.txt", "items.txt")
    PLAYER = Player(0, 0, 11) # set starting location of player; you may change the x, y coordinates here as appropriate

    load = input("Would you like to load a previously saved game (y/n)? ")
    if load == "y":  # If we should load
        check_saved_games(WORLD, PLAYER)  # Load from saved games if exists

    menu = ["go", "look", "inventory", "score", "save", "quit"]

    while not PLAYER.victory:

        if PLAYER.out_of_moves():  # If we're out of moves tell them
            print("You are all out of moves and you have lost! Thanks for playing!")
            break

        location = WORLD.get_location(PLAYER.x, PLAYER.y)  # Get the location

        location.look()  # Look
        location.deliver(WORLD, PLAYER)  # Deliver any items

        if WORLD.check_delivered_all_items(PLAYER):  # If we didn't deliver all items
            break

        print("What to do? \n")
        print("[menu]")
        for action in location.available_actions():
            print(action)
        choice = str(input("\nEnter action: "))

        if choice == "[menu]":
            print("Menu Options: \n")
            for option in menu:
                print(option)
            choice = input("\nChoose action: ")
            if choice.startswith("go"):  # If go
                dir = choice.split(" ")[1]  # get direction
                WORLD.move_player(PLAYER, dir)  # move if valid
            elif choice == "look":  # If look
                pass  # Do nothing, going to top of the while loop will 'look' automatically
            elif choice == "inventory":  # If inventory
                PLAYER.print_inventory()  # Print inventory
            elif choice == "score":  # If score
                print("Score: " + str(PLAYER.score))  # Print score
            elif choice == "save":  # If save
                save_game(WORLD, PLAYER)  # Save game
                print("Saved game.\n")
            elif choice == "quit":  # If quit
                print("I understand, you couldn't handle the adventure. Thanks for playing!")
                break;  # Break out
        elif choice.startswith("pickup"):  # If pickup
            itemName = choice.replace("pickup ", "")  # Get just the item name
            item = location.get_item(itemName)  # get the item
            if item is not None:  # If it is an actual item
                location.remove_item(item)  # Remove from location
                PLAYER.add_item(item)  # Add to inventory

    if PLAYER.victory:  # If won
        if os.path.isfile("saves.txt"):
            os.remove("saves.txt")  # Remove the saved game
            print("You have delivered all of the items and are victorious! Thanks for playing!")
