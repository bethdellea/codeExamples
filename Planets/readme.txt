NBody Simulation
Beth Dellea

In my code for NBody.java, I have the entire universe existing in a never-ending While loop, with two for loops inside it.
My universe could have been created in one for loop, but it made more sense to me to contain the calculations of net force in one loop, 
and all of the subsequent acceleration, velocity, and position calculations in another.
At the end of my second for loop, once all new positions have been calculated, I have the program find the values associated with the "sun"
particle and return the assigned position to (0, 0) so it remains at the center of the universe where it belongs.

I created a new java file for my bonus work, but it is essentially the same code with different associated file names. There is a different
.txt file for the bonus, and though my bonus also has 5 bodies, they have different masses, positions, and initial velocities.