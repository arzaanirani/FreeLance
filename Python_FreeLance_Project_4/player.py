from game_data import Key

class Player:

    def __init__(self, x, y, movesLeft):
        '''
        Creates a new Player.
        :param x: x-coordinate of position on map
        :param y: y-coordinate of position on map
        :param movesLeft: The number of moves left
        :return:

        This is a suggested starter class for Player.
        You may add new parameters / attributes / methods to this class as you see fit.
        Consider every method in this Player class as a "suggested method":
                -- Suggested Method (You may remove/modify/rename these as you like) --
        '''
        self.x = x
        self.y = y
        self.inventory = []
        self.victory = False
        self.score = 0
        self.movesLeft = movesLeft
        self.itemsDelivered = 0
        self.used_keys = []  # Holds all the used keys. Used when saving game

    def move(self, dx, dy):
        '''
        Given integers dx and dy, move player to new location (self.x + dx, self.y + dy)
        :param dx: The change in x
        :param dy: The change in y
        '''
        self.x += dx
        self.y += dy
        self.movesLeft -= 1  # Subtract from moves left

    def move_to(self, x, y):
        ''' Move to a specific x and y coordinate

        :param x: The x coordinate
        :param y: The y coordinate
        '''
        self.x = x
        self.y = y

    def out_of_moves(self):
        '''
        :return: If they are no moves left
        '''
        return self.movesLeft <= 0

    def move_north(self):
        '''These integer directions are based on how the map must be stored
        in our nested list World.map'''
        self.move(0, -1)

    def move_south(self):
        '''
        Move south
        '''
        self.move(0, 1)

    def move_east(self):
        '''
        Move East
        '''
        self.move(1, 0)

    def move_west(self):
        '''
        Move West
        '''
        self.move(-1, 0)

    def add_item(self, item):
        ''' Adds an item to the player inventory
        :param item: The item to add
        '''
        self.inventory.append(item)

    def remove_item(self, item):
        ''' Removes an item from the player inventory
        :param item: The item to remove
        '''
        if item in self.inventory:  # If the item is in the inventory
            self.inventory.remove(item)  # Remove it

    def add_score(self, value):
        ''' Add a score to the player
        :param value: The value to add
        '''
        self.score += value

    def get_inventory(self):
        ''' :return: The inventory array '''
        return self.inventory

    def get_item(self, item_name):
        ''' Get an item from the inventory by it's name

        :param item_name: The item name
        :return: The item
        '''
        for item in self.inventory:
            if item.get_name() == item_name:
                return item

    def has_and_remove_key(self, location):
        ''' Checks if it has a key in a location. Removes the key from the inventory if it does.

        :param location: The location to check keys for
        :return: If a key was found
        '''
        for item in self.inventory:  # Go through items
            if isinstance(item, Key):  # If it's a key
                if item.target == location.locNum:  # If the key target is the location ID
                    self.inventory.remove(item)  # Remove from inventory
                    self.used_keys.append(item)  # Add to the used keys
                    return True

    def add_delivered_item(self):
        '''
        Add 1 to the counter of the items delivered
        '''
        self.itemsDelivered += 1

    def print_inventory(self):
        ''' Print the inventory in a readable format'''
        print("[Inventory]")
        for item in self.inventory:  # Go through each item
            print (item.get_name() + " - Deliver To: " + item.get_target_location() + " - Credits Awarded: " + item.get_target_points())
        print("[End Of Inventory]\n")
