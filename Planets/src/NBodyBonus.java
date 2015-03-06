	/*******************************
	 * NBody Simulation - visually represent solar system using Newtonian Physics
	 * @author Beth Dellea
	 * September 24, 2014
	 * 
	 */

import java.io.FileInputStream;

public class NBodyBonus {
	


		public static void main(String[] args) throws Exception {
			// read in planet information
			System.setIn(new FileInputStream("bonusPlanets.txt"));
			// number of planets
			int N = StdIn.readInt();
			// radius of universe
			double R = StdIn.readDouble();
			StdDraw.setXscale(-R, R);
			StdDraw.setYscale(-R, R);

			// Gravitational Constant
			double G = 6.67e-11;
			// time step
			double dt = 23000; // time delta

			// data structures to store information related to the planents
			double[] x = new double[N];
			double[] y = new double[N];
			double[] vx = new double[N];
			double[] vy = new double[N];
			double[] m = new double[N];
			String[] img = new String[N];

			/* YOUR CODE HERE */

			StdAudio.play("frontier.wav"); //starts the audio playing
			/* Step 1: Read in planet data from file and store in data structures */

			// fill arrays with relevant data
			for (int a = 0; a < N; a++) {
				x[a] = Double.parseDouble(StdIn.readString());
				y[a] = Double.parseDouble(StdIn.readString());
				vx[a] = Double.parseDouble(StdIn.readString());
				vy[a] = Double.parseDouble(StdIn.readString());
				m[a] = Double.parseDouble(StdIn.readString());
				img[a] = StdIn.readString();
			}
			Boolean running = true;     //keeps it running forever
			while (running) {
				//set up images and music and all that

				StdDraw.clear();
				StdDraw.picture(0, 0, "bonusStarfield.gif");
				
				StdDraw.picture(x[0], y[0], img[0]);
				StdDraw.picture(x[1], y[1], img[1]);
				StdDraw.picture(x[2], y[2], img[2]);
				StdDraw.picture(x[3], y[3], img[3]);
				StdDraw.picture(x[4], y[4], img[4]);
				StdDraw.show(28);
				
				//actual universe movement calculations
				// Net force (fx, fy)
				double[] fx = new double[N];
				double[] fy = new double[N];
				// acceleration
				double[] ax = new double[N];
				double[] ay = new double[N];

				for (int b = 0; b < N; b++) {
					double netGravX = 0; //net force in x accumulator
					double netGravY = 0; //net force in y accumulator
					for (int c = 0; c < N - 1; c++) {
						double gravForce = 0; //overall force between 2 bodies
						double r = Math.sqrt(Math.pow((x[c] - x[b]), 2)
								+ Math.pow((y[c] + y[b]), 2));
						if (r != 0) {
							gravForce = G*(m[b] * m[c]) /(r * r);
							netGravX += (gravForce * (x[c] - x[b]) / r);
							netGravY += (gravForce * (y[c] - y[b]) / r);
						}

					}
					fx[b] = netGravX;
					fy[b] = netGravY;
				}

				// calculate acceleration

				for (int d = 0; d < N; d++) {
					ax[d] = (fx[d] / m[d]);
					ay[d] = (fy[d] / m[d]);
					// calculate new velocity
					
					vx[d] = (vx[d] + dt * ax[d]);
					vy[d] = (vy[d] + dt * ay[d]);


					// calculate new position
					x[d] = (x[d] + dt * vx[d]);
					y[d] = (y[d] +dt * vy[d]);
					
					if (img[d] == "ship"){  //keep the sun at the origin of the universe
						x[d] = 0;
						y[d] = 0;
					}
				}

			}
		}

	}
