Minesweeper Lab
Beth Dellea

In this program, when "Minesweeper.java" is run, a guide to the controls is printed out and users are asked to select a level. Easy, medium, and hard are
all levels, as is "Custom" wherein players may submit any positive integer for height and width, and some number that is less than (h*w-2). This ensures that
players don't use negative values and that they do not attempt to place more bombs than there are squares for, which would cause problems in the function
that handles bomb placement.

Once the level is chosen with valid input, the Minesweeper class creates an instance of the Grid class in which the height and width fit the settings. The
Grid class is where the actual grid gets created and displayed, and the bombs are placed once the user makes their first click.
Grid has a countdown of the number of Flags left to be placed, which can go negative, and climbs back up as flags are removed, as in the real version
 of the game. Unfortunately, for some reason I haven't figured out, when the game is started from within Minesweeper.java, the block of code that handles toggling flags is not used. Flags can be toggled
if you start the game using the main function in Grid.java, though.

If a player clicks on an empty square, all other empty squares touching it will be clicked recursively, stopping at squares that have been flagged, revealed,
or are not empty (will click individual squares with values, but not squares touching those).

The gameplay ends when all non-bomb squares have been revealed or a bomb has been clicked. If a bomb is clicked, the bomb in question turns one shade of red,
and the rest of the grid is revealed, with all other bombs a darker shade of red. Losing and winning each play a sound, as well.

