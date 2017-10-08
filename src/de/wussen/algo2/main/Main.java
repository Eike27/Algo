package de.wussen.algo2.main;

import de.wussen.algo2.gui.View;

public class Main {

	public static void main(String[] args) {
		Log.setFileName("Algo_Test_2");
		Log.setDev(true);
		Log.checkDirectory();
		Log.info("Starting");
		View view1 = new View("");
		view1.asd();
//		view1.rndNew();
//		view1.rnd();
	}
 
}
