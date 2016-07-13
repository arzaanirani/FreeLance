class Location:

    def __init__(self, locNum, credits, briefDesc, fullDesc):
        '''Creates a new location.          
        ADD NEW ATTRIBUTES TO THIS CLASS HERE TO STORE DATA FOR EACH LOCATION.
        
        Data that could be associated with each Location object:
        a position in the world map,
        a brief description,
        a long description,
        a list of available commands/directions to move,
        items that are available in the location,
        and whether or not the location has been visited before.
        Store these as you see fit.

        This is just a suggested starter class for Location.
        You may change/add parameters and the data available for each Location class as you see fit.
  
        The only thing you must NOT change is the name of this class: Location.
        All locations in your game MUST be represented as an instance of this class.
        '''
        self.locNum = locNum  # the location number
        self.credits = credits  # the credits
        self.briefDesc = briefDesc  # the brief description
        self.fullDesc = fullDesc  # the full description
        self.visited = False  # if it was visited
        self.items = []  # the items in the location (stored as an array)
        self.door = False

    def get_brief_description (self):
        '''Return str brief description of location.'''
        return self.briefDesc

    def get_full_description (self):
        '''Return str long description of location.'''
        return self.fullDesc

    def look(self):
        ''' Prints out the location details
        If the location wasn't visited it prints the full description otherwise it prints the brief description.
        '''
        if self.visited:  # Only show brief if it has been visited before
            print("Location " + self.locNum + "\n" + self.briefDesc)
        else:
            print("Location " + self.locNum + "\n" + self.fullDesc)
            self.visited = True

    def deliver(self, world, player):
        ''' Delivers items to location
        Checks all deliverable items that the player has to see if any have to be delivered to this location

        :param world: The adventure world
        :param player: The player
        :return:
        '''
        for item in player.get_inventory():  #Go through each item
            if not item.name == "Key":  # If it's not a key
                if item.get_target_location() == self.locNum:  # If the target location is this location
                    player.remove_item(item)  # Remove the item
                    player.add_score(int(item.get_target_points()))  # Reward points
                    player.add_delivered_item()  # Add to delivered item counter
                    world.delivered_item(item)  # Add to world array
                    print("You have delivered the " + item.get_name() + " to it's location here! You've been awarded " + item.get_target_points() + " credits!")

    def get_item(self, item_name):
        ''' Gets an item given the name

        :param item_name: The item's name to get
        :return: The item
        '''
        for item in self.items:  # Get each item
            if item.get_name() == item_name:  # If the name is the same
                return item  # Return the item

    def add_item(self, item):
        ''' Adds an item to the location

        :param item: The item to add
        '''
        self.items.append(item)

    def remove_item(self, item):
        ''' Removes an item from the location

        :param item: The item to remove
        '''
        if item in self.items:  # If the array contains it
            self.items.remove(item)  # Remove it

    def available_actions(self):
        '''
        -- Suggested Method (You may remove/modify/rename this as you like) --
        Return list of the available actions in this location.
        The list of actions should depend on the items available in the location
        and the x,y position of this location on the world map.'''
        actions = ["look"]
        if len(self.items) > 0:
            for item in self.items:
                actions.append("pickup " + item.get_name())
        return actions

class Item:

    def __init__ (self, item_id, name, start, target, target_points):
        '''Create item referred to by string name, with integer "start"
        being the integer identifying the item's starting location,
        the integer "target" being the item's target location, and
        integer target_points being the number of points player gets
        if item is deposited in target location.

        This is just a suggested starter class for Item.
        You may change these parameters and the data available for each Item class as you see fit.
        Consider every method in this Item class as a "suggested method":
                -- Suggested Method (You may remove/modify/rename these as you like) --

        The only thing you must NOT change is the name of this class: Item.
        All item objects in your game MUST be represented as an instance of this class.
        '''
        self.item_id = item_id
        self.name = name
        self.start = start
        self.target = target
        self.target_points = target_points

    def get_starting_location (self):
        '''Return int location where item is first found.'''
        return self.start

    def get_name(self):
        '''Return the str name of the item.'''
        return self.name

    def get_target_location (self):
        '''Return item's int target location where it should be deposited.'''
        return self.target

    def get_target_points (self):
        '''Return int points awarded for depositing the item in its target location.'''
        return self.target_points

class Key(Item):

    def __init__(self, item_id, start, target, target_points):
        ''' Key instance. Extends from the Item class
        A key is a special item which is used to unlock doors. This is part of the puzzle requirement

        :param item_id: The item id
        :param start: The initial location
        :param target:  The location the door is at
        :param target_points:  The credits awarded when
        '''
        super().__init__(item_id, "Key", start, target, target_points)  # Call the super constructor

class World:

    def __init__(self, mapdata, locdata, itemdata):
        '''
        Creates a new World object, with a map, and data about every location and item in this game world.

        You may ADD parameters/attributes/methods to this class as you see fit.
        BUT DO NOT RENAME OR REMOVE ANY EXISTING METHODS/ATTRIBUTES.

        :param mapdata: name of text file containing map data in grid format (integers represent each location, separated by space)
                        map text file MUST be in this format.
                        E.g.
                        1 -1 3
                        4 5 6
                        Where each number represents a different location, and -1 represents an invalid, inaccessible space.
        :param locdata: name of text file containing location data (format left up to you)
        :param itemdata: name of text file containing item data (format left up to you)
        :return:
        '''
        self.locations = []  # Holds all locations
        self.items = []  # Used as a reference when loading a saved game
        self.delivered_items = []  # Holds items that were delivered. Used when saving game
        self.totalItems = 0  # Total items in the game
        self.map = self.load_map(mapdata)  # The map MUST be stored in a nested list as described in the docstring for load_map() below
        # self.locations ... You may choose how to store location and item data.
        self.load_locations(locdata)  # This data must be stored somewhere. Up to you how you choose to do it...
        self.load_items(itemdata)  # This data must be stored somewhere. Up to you how you choose to do it...

    def check_delivered_all_items(self, player):
        ''' Determines whether all of the items in the adventure were delivered

        :param player: The player
        :return: bool all items from delivered
        '''
        if player.itemsDelivered == self.totalItems:  # Get each delivered item
            player.victory = True  # Set victory to true
            return True  # Return true
        return False  # Return false

    def delivered_item(self, item):
        ''' Add to the delivered items

        :param item: The item to add
        '''
        self.delivered_items.append(item)

    def move_player(self, player, dir):
        ''' Move player command
        Moves the player if the player isn't trying to move into a location that doesn't exist or is off the map

        :param player: The player
        :param dir: The direction to move
        :return:
        '''
        last_x = player.x  # Save in case there's a door
        last_y = player.y  # Save in case there's a door

        if dir == "south":  # moving south
            y = player.y + 1  # get desired y
            if y < len(self.map):  # Not outside the bounds
                loc_id = self.map[y][player.x]  # Get the ID
                if loc_id != "-1":  # If it's a location
                    player.move_south()  # move south
                    self.check_for_door(player, last_x, last_y)
        elif dir == "north":  # moving north
            y = player.y - 1  # get desired y
            if y >= 0:  # Not outside the bounds
                loc_id = self.map[player.y - 1][player.x]  # Get the ID
                if loc_id != "-1":  # If it's a location
                    player.move_north()  # move north
                    self.check_for_door(player, last_x, last_y)
        elif dir == "east":  # moving east
            x = player.x + 1  # get desired x
            if x < len(self.map[0]):  # Not outside the bounds
                loc_id = self.map[player.y][x]  # Get the ID
                if loc_id != "-1":  # If it's a location
                    player.move_east()  # Move east
                    self.check_for_door(player, last_x, last_y)
        elif dir == "west":  # moving west
            x = player.x - 1  # get desired x
            if x >= 0:  # Not outside the bounds
                loc_id = self.map[player.y][x]  # Get the ID
                if loc_id != "-1":  # If it is a location
                    player.move_west()
                    self.check_for_door(player, last_x, last_y)

    def check_for_door(self, player, last_x, last_y):
        ''' Checks to see if there is a door when trying to move to a specific location
        If there is a door it will alert the player.
        If there is a door but the player has the key, it will remove the door and key and alert the player.

        :param player: The player
        :param last_x: The last player x position
        :param last_y: The last player y position
        '''
        location = self.get_location(player.x, player.y)  # get the player location
        if location.door:  # If the location is a door
            if player.has_and_remove_key(location):  # If the player has the key for the door
                location.door = False  # Remove the door
                print("\n*** You have unlocked the door with the key!!! ***\n")
            else:
                print("\n*** STOP *** You need to find the key to go to this area!!! *** STOP ***\n")
                player.move_to(last_x, last_y)  # Move back

    def load_map(self, filename):
        '''
        THIS FUNCTION MUST NOT BE RENAMED OR REMOVED.
        Store map from filename (map.txt) in the variable "self.map" as a nested list of strings OR integers like so:
            1 2 5 map
            3 -1 4
        becomes [['1','2','5'], ['3','-1','4']] OR [[1,2,5], [3,-1,4]]
        RETURN THIS NEW NESTED LIST.
        :param filename: string that gives name of text file in which map data is located
        :return: return nested list of strings/integers representing map of game world as specified above
        '''
        file = open(filename, 'r')  # Open file
        map = []
        for line in file.readlines():  # Get each line
            map.append(line.replace("\n", "").split(" ")) # Split line by spaces and append the returned array to the map array
        file.close()
        return map

    def load_locations(self, filename):
        '''
        Store all locations from filename (locations.txt) into the variable "self.locations"
        however you think is best.
        Remember to keep track of the integer number representing each location.
        Make sure the Location class is used to represent each location.
        :param filename:
        '''
        file = open(filename, 'r')  # Open the file
        lines = file.readlines()  # Read the lines
        file.close()

        location_id = ""  # ID
        location_credits = 0  # Credits
        location_brief_desc = ""  # Brief desc
        location_full_desc = ""  # Full desc
        found = False  # Found a location within the file

        i = 0  # Counter
        while i < len(lines): # Go through each line
            line = lines[i].replace("\n", "")  # Get the line we're on
            if line.startswith("LOCATION") and not found: # We found a new location
                location_id = line.split(" ")[1]  # Get the ID from the line
                i += 1  # Go to next line to get credits
                line = lines[i].replace("\n", "")
                location_credits = int(line)
                i += 1  # Go to next line to get brief desc
                line = lines[i].replace("\n", "")
                location_brief_desc = line
                i += 1  # Go to next line to start reading full desc
                line = lines[i].replace("\n", "")
                found = True

            if found:  # We are reading the full description until END
                if not line.startswith("END"): # We're reading the full description
                    location_full_desc += line + "\n"  # Append to the full description
                else:
                    self.locations.append(Location(location_id, location_credits, location_brief_desc, location_full_desc))  # Add the new locatin
                    location_full_desc = ""  # Reset the description
                    found = False  # Reset found flag
            i += 1  # Increment i

    def load_items(self, filename):
        '''
        Store all items from filename (items.txt) into ... whatever you think is best.
        Make sure the Item class is used to represent each item.
        Change this docstring accordingly.
        :param filename:
        :return:
        '''
        file = open(filename, 'r')  # Open file

        for line in file:  # Go through each line
            split = line.replace("\n", "").split(" ")  # Split values
            item_id = split[0]  # Get the item ID
            init_location = split[1]  # Get initial location
            dest_location = split[2]  # Get the item's destination
            credits_awarded = split[3]  # Get the credits awarded when delivered
            name = split[4].replace("_", " ")  # The name of the item
            item = None  # Create null variable
            if name == "Key":  # Check if it's a key
                item = Key(item_id, init_location, dest_location, credits_awarded)  # Create Key
                location = self.get_location_from_id(dest_location)
                location.door = True
            else:  # It's a normal item
                item = Item(item_id, name, init_location, dest_location, credits_awarded)  # Create Item
                self.totalItems += 1  # Add to count
            self.add_item_to_location(item)  # Add to corresponding location
            self.items.append(item)  # Add to items array

        file.close()

    def get_item(self, item_id):
        ''' Gets an item by ID

        :param item_id: The item ID
        :return: The item
        '''
        for item in self.items:  # Get each item
            if item.item_id == item_id:  # If the item ID equals the ID
                return item  # Return the item

    def add_item_to_location(self, item):
        ''' Add an item to it's starting location

        :param item: The item to add
        '''
        for location in self.locations:  # Go through each location
            if location.locNum == item.get_starting_location():  # If the ID is where the item starts
                location.add_item(item)  # Add the item
                break

    def get_location_from_id(self, location_id):
        ''' Get a location by it's ID

        :param location_id: The location ID
        :return: The location
        '''
        for location in self.locations:  # Go through each location
            if location.locNum == location_id:  # If the ID is location ID
                return location  # Return the ID

    def get_location(self, x, y):
        '''Check if location exists at location (x,y) in world map.
        Return Location object associated with this location if it does. Else, return None.
        Remember, locations represented by the number -1 on the map should return None.
        :param x: integer x representing x-coordinate of world map
        :param y: integer y representing y-coordinate of world map
        :return: Return Location object associated with this location if it does. Else, return None.
        '''
        location_id = self.map[y][x]  # Get the ID
        return self.get_location_from_id(location_id)  # Call and return the location via it's ID
