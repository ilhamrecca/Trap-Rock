package Main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import Handlers.Handlers;

public class Display {

	private javax.swing.JScrollPane tabel;
	private javax.swing.table.DefaultTableModel defTableModel;
	private javax.swing.JTable kampret;
	private javax.swing.JFrame frame;
	private Canvas canvas;
	private Handlers handler;
	SwingWorker<Void, Integer> timer;
	private String title;
	private int width, height;
	private String player1;
	private String player2;
	private int second;
	private int minute;
	private boolean pause;
	private JOptionPane optionPaneWaiting;
	private JDialog dialogWaiting;
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		createDisplay();
	}

	private void createDisplay() {
		frame = new javax.swing.JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.requestFocus();

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public javax.swing.JFrame getFrame() {
		return frame;
	}

	public void setHandler(Handlers handler) {
		this.handler = handler;
	}
	
	public String endGame() {
		return javax.swing.JOptionPane.showInputDialog("MASUKAN NAMA PLAYER "+ handler.getPlayer());
	}

	

	@SuppressWarnings("serial")
	public void highscore() {
		javax.swing.JFrame frame = new javax.swing.JFrame("Highscores");
		String[] columnNames = { "Player 1", "Player 2", "Waktu", "Kecocokan" };
		defTableModel = new javax.swing.table.DefaultTableModel() {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			};
		};
		defTableModel.setColumnIdentifiers(columnNames);

		kampret = new javax.swing.JTable();
		kampret.setModel(defTableModel);

		tabel = new javax.swing.JScrollPane();
		handler.getDatabase().getData();
		tabel.setViewportView(kampret);

		frame.add(tabel);
		frame.setPreferredSize(new Dimension(450, 250));
		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(450, 250));
		frame.setVisible(true);

	}

	public int startServer() {
		return JOptionPane.showConfirmDialog(frame, "Do you wanna start a server?");

	}

	public void waitingPlayer2() {
		this.optionPaneWaiting = new JOptionPane("Waiting for player 2 to connect", JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);

		this.dialogWaiting = new JDialog();
		dialogWaiting.setTitle("Message");
		dialogWaiting.setModal(true);

		dialogWaiting.setContentPane(optionPaneWaiting);

		dialogWaiting.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialogWaiting.pack();

		dialogWaiting.setVisible(true);
	}
	
	public void player2Connected() {
		dialogWaiting.setVisible(false);
		optionPaneWaiting.removeAll();
		dialogWaiting.pack();
	}

	public void time() {

		timer = new SwingWorker<Void, Integer>() {
			int seconds = 0;
			int minutes = 0;

//			int now = 0;
			@Override
			protected Void doInBackground() throws Exception {
//				int now = 0;
//				seconds = 0;
//				minutes = 0;
				// seconds = 0;
//				long lastTime;
//				long currentTime;
				long targetTime;

				targetTime = 999;

				while (!pause) {

					Thread.sleep(targetTime);
					seconds++;
					publish(seconds);
				}
				seconds = 0;
				minutes = 0;

				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				if (seconds >= 60) {
					seconds = 0;
					minutes++;
				}

				setSecond(seconds);
				setMinute(minutes);
			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				System.out.println("timer stopped");
			}

		};

		timer.execute();

	}
	public String selectServer() {
		 return JOptionPane.showInputDialog("Enter Server Address");
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public SwingWorker<Void, Integer> getTimer() {
		return timer;
	}

	public void setTimer(SwingWorker<Void, Integer> timer) {
		this.timer = timer;
	}

	public javax.swing.table.DefaultTableModel getDefTableModel() {
		return defTableModel;
	}

	public void setDefTableModel(javax.swing.table.DefaultTableModel defTableModel) {
		this.defTableModel = defTableModel;
	}

}
