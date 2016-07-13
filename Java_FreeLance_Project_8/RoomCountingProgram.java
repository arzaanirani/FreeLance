public class RoomCountingProgram {
    // Display the maze
    public static void displayMaze (byte[][] m) {
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[1].length; col++) {
                if (m[row][col] == 5)
                    System.out.print('@');
                else if (m[row][col] == 1)
                    System.out.print('*');
                else
                    System.out.print(' ');
            }
            System.out.println();
        }
    }

    // Reset the maze by restoring marked locations to 1 and 0
    public static void resetMaze (byte[][] m) {
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[1].length; col++) {
                if (m[row][col] != 1)
                    m[row][col] = 0;
            }
        }
    }

    // Count the number of rooms in the maze
    /**
     *  set count to 0
     *  for each r, c in m:
     *      recursively fill the space
     *      if the filled space has area greater than one
     *         increase count
     *  return count
     */
    public static int countRooms (byte[][] m) {
        int count = 0;
        for (int r = 0; r < m.length; r++)
            for (int c = 0; c < m[r].length; c++)
                if (roomSizeAt(m, r, c) != 0)
                    count++;
        return count;
    }

    // count the size of the room at row R, column C
    /**
     *  if the current cell is full:
     *      return an area of 0
     *  make the current cell full
     *  for each neighboring cell:
     *      add roomSizeAt(neighboring cell) to count
     *  return count
     */
    public static int roomSizeAt (byte[][] m, int r, int c) {
        if (m[r][c] > 0)
            return 0;
        m[r][c] = 2;
        int count = 1;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++)
                if (Math.abs(dr) != Math.abs(dc))
                    count += roomSizeAt(m, r + dr, c + dc);
        return count;
    }


    // Compute the size of the largest room in the maze
    /**
     *  set max to 0
     *  for each cell in the maze:
     *      set max to the greater element: max, or the room size at the current cell
     *  return max
     */
    public static int largestRoomSize (byte[][] m) {
        int max = 0;
        for (int r = 0; r < m.length; r++)
            for (int c = 0; c < m[r].length; c++)
                max = Math.max(max, roomSizeAt(m, r, c));
        return max;
    }

    public static void main (String[] args) {
        byte[][] maze1 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze1);
        System.out.println("Rooms in maze1:        " + countRooms(maze1));
        resetMaze(maze1);
        System.out.println("Largest room in maze1: " + largestRoomSize(maze1) + "\n");

        byte[][] maze2 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze2);
        System.out.println("Rooms in maze2:        " + countRooms(maze2));
        resetMaze(maze2);
        System.out.println("Largest room in maze2: " + largestRoomSize(maze2) + "\n");

        byte[][] maze3 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 1, 1, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze3);
        System.out.println("Rooms in maze3:        " + countRooms(maze3));
        resetMaze(maze3);
        System.out.println("Largest room in maze3: " + largestRoomSize(maze3) + "\n");

        byte[][] maze4 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze4);
        System.out.println("Rooms in maze4:        " + countRooms(maze4));
        resetMaze(maze4);
        System.out.println("Largest room in maze4: " + largestRoomSize(maze4) + "\n");

        byte[][] maze5 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze5);
        System.out.println("Rooms in maze5:        " + countRooms(maze5));
        resetMaze(maze5);
        System.out.println("Largest room in maze5: " + largestRoomSize(maze5) + "\n");

        byte[][] maze6 = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze6);
        System.out.println("Rooms in maze6:        " + countRooms(maze6));
        resetMaze(maze6);
        System.out.println("Largest room in maze6: " + largestRoomSize(maze6) + "\n");

        byte[][] maze7 = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze7);
        System.out.println("Rooms in maze7:        " + countRooms(maze7));
        resetMaze(maze7);
        System.out.println("Largest room in maze7: " + largestRoomSize(maze7) + "\n");

        byte[][] maze8 = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze8);
        System.out.println("Rooms in maze8:        " + countRooms(maze8));
        resetMaze(maze8);
        System.out.println("Largest room in maze8: " + largestRoomSize(maze8) + "\n");

        byte[][] maze9 = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};
        displayMaze(maze9);
        System.out.println("Rooms in maze9:        " + countRooms(maze9));
        resetMaze(maze9);
        System.out.println("Largest room in maze9: " + largestRoomSize(maze9) + "\n");
    }
}