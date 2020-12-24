package Main;




public class Main {

	public static void main(String[] args) {
//		JFrame window = new JFrame("Artifact");
//		window.add(new GamePanel());
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setResizable(false);
//		window.pack();
//		window.setLocationRelativeTo(null);
//		window.setVisible(true);
		System.out.println(Thread.currentThread().getName());
		GamePanel game = new GamePanel();
		game.start();
	}

}
