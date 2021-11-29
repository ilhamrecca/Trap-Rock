package Main;

import Handlers.AudioPlayer;

public class Main {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());

		GamePanel game = new GamePanel();
		game.start();
	}

}
