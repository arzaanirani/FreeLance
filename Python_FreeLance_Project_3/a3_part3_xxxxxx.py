import itertools

class Point:
    'class that represents a point in the plane'

    def __init__(self, xcoord=0, ycoord=0):
        ''' (Point,number, number) -> None
        initialize point coordinates to (xcoord, ycoord)'''
        self.x = xcoord
        self.y = ycoord

    def setx(self, xcoord):
        ''' (Point,number)->None
        Sets x coordinate of point to xcoord'''
        self.x = xcoord

    def sety(self, ycoord):
        ''' (Point,number)->None
        Sets y coordinate of point to ycoord'''
        self.y = ycoord

    def get(self):
        '''(Point)->tuple
        Returns a tuple with x and y coordinates of the point'''
        return (self.x, self.y)

    def move(self, dx, dy):
        '''(Point,number,number)->None
        changes the x and y coordinates by dx and dy'''
        self.x += dx
        self.y += dy

    def __eq__(self, other):
        '''(Point,Point)->bool
        Returns True if self and other have the same coordinates'''
        return self.x == other.x and self.y == other.y
    def __repr__(self):
        '''(Point)->str
        Returns canonical string representation Point(x, y)'''
        return 'Point('+str(self.x)+','+str(self.y)+')'
    def __str__(self):
        '''(Point)->str
        Returns nice string representation Point(x, y).
        In the case we chose the same representation as in __repr__'''
        return 'Point('+str(self.x)+','+str(self.y)+')'

class Rectangle:
    def __init__(self, point1, point2, color):
        '''point1 and point2 are Point objects, color is a String'''
        self.point1 = point1
        self.point2 = point2
        self.color = color

    def __str__(self):
        a = self.point1.get()
        b = self.point2.get()
        point1str = '(' + str(a[0]) + ', ' + str(a[1]) + ')'
        point2str = '(' + str(b[0]) + ', ' + str(b[1]) + ').'
        return('I am a ' + self.color + ' rectangle with bottom left corner at ' + point1str + ' and top right corner at ' + point2str)

    def __repr__(self):
        #return (str(self))
        return ('Rectangle(' + str(self.point1) + ',' + str(self.point2) + ',\'' + self.color + '\')')

    def __eq__(self, other):
        '''Other is a Rectangle object'''
        return (self.point1 == other.point1 and self.point2 == other.point2)

    def get_bottom_left(self):
        return self.point1

    def get_top_right(self):
        return self.point2

    def get_color(self):
        return self.color

    def reset_color(self, newcolor):
        '''newcolor is a String object'''
        self.color = newcolor

    def get_perimeter(self):
        a = self.point1.get()
        b = self.point2.get()
        return 2*((b[0]-a[0]) + (b[1]-a[1]))

    def get_area(self):
        a = self.point1.get()
        b = self.point2.get()
        return ((b[0]-a[0])*(b[1]-a[1]))

    def move(self, dx, dy):
        self.point1.move(dx,dy)
        self.point2.move(dx,dy)

    def intersects(self, otherRect):
        '''otherRect is a Rectangle object'''
        selfA = self.point1.get()
        selfB = self.point2.get()
        otherA = otherRect.point1.get()
        otherB = otherRect.point2.get()
        r1Left = selfA[0]
        r1Right = selfB[0]
        r1Bottom = selfA[1]
        r1Top = selfB[1]
        r2Left = otherA[0]
        r2Right = otherB[0]
        r2Bottom = otherA[1]
        r2Top = otherB[1]
        #if (selfA[0] > otherB[0] or selfB[0] < otherA[0] or selfB[1] < otherA[1] or selfA[1] > otherB[1]):
        #    return False
        #return True
        horizontal = (r1Left <= r2Right) and (r1Right >= r2Left)
        vertical = (r1Top >= r2Bottom) and (r1Bottom <= r2Top)
        return (horizontal and vertical)

    def contains(self, x, y):
        '''x and y are integers'''
        selfA = self.point1.get()
        selfB = self.point2.get()
        if (x >= selfA[0] and x <= selfB[0] and y >= selfA[1] and y <= selfB[1]):
            return True
        return False

class Canvas:
    def __init__(self):
        self.rectangles = []

    def __repr__(self):
        i = 0
        toRet = 'Canvas(['
        for rect in rectangles:
            if (i != 0):
                toRet += ','
            toRet += repr(rect)
            i += 1
        toRet += '])'
        return toRet

    def __str__(self):
        return repr()

    def add_one_rectangle(self, rect):
        '''rect is a Rectangle object'''
        self.rectangles.append(rect)

    def count_same_color(self, color):
        '''color is a String'''
        count = 0
        for rect in self.rectangles:
            if (rect.get_color == color):
                count = count + 1

    def total_perimeter(self):
        sum = 0
        for rect in self.rectangles:
            sum = sum + rect.get_perimeter()
        return sum

    def min_enclosing_rectangle(self):
        minx = maxx = miny = maxy = None
        for rect in self.rectangles:
            temp = rect.get_bottom_left().get()
            temp2 = rect.get_top_right().get()
            if (minx == None or temp[0] < minx):
                minx = temp[0]
            if (maxx == None or temp2[0] > maxx):
                maxx = temp2[0]
            if (miny == None or temp[1] < miny):
                miny = temp[1]
            if (maxy == None or temp2[1] > maxy):
                maxy = temp2[1]
        return(Rectangle(Point(minx,miny),Point(maxx,maxy),"White"))

    def common_point(self):
        for couple in itertools.combinations_with_replacement(self.rectangles,2):
            if not (couple[0].intersects(couple[1])):
                return False
        return True
