package Main;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import Assets.Assets;
import Client.Client;
import GameState.GameStateManager;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Keys;
import Handlers.Mouse;
import Server.Server;

public class GamePanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	public static final int WIDTH = 980;
	public static final int HEIGHT = 720;
	
	//game Thread;
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long timePerTick = 1000/FPS;
	
	//Buffered image
	//private BufferedImage image1;
	//private BufferedImage image;
	
	//commented
	//private Graphics2D g;
	
	//testing
	private Graphics2D g;
	
	//Buffered Strategy
	private BufferStrategy bs;
	
	//testing
	private Display display;
	
	//GameState Manager;
	private GameStateManager gsm;
	
	//database
	
	
	//handler
	Handlers handler;
	
	public GamePanel() {
//		super();
//		
//		setPreferredSize(new Dimension(WIDTH, HEIGHT));
//		setFocusable(true);
//		requestFocus();
		
		display = new Display("entahlah", GamePanel.WIDTH,GamePanel.HEIGHT);
	}
//	
//	public void addNotify() {
//		super.addNotify();
//		if(thread == null) {
//			thread = new Thread(this);
//			addKeyListener(this);
//			thread.start();
//		}
//	}
//	
	private void init() {
		display.getFrame().addKeyListener(this);
		display.getFrame().addMouseListener(this);
		display.getFrame().addMouseMotionListener(this);
		display.getCanvas().addMouseListener(this);
		display.getCanvas().addMouseMotionListener(this);
		AudioPlayer.init();
		AudioPlayer.load("Music/menu.mp3", "menu");
		AudioPlayer.load("Music/gameplay.mp3", "gameplay");

		
		
		this.handler = new Handlers(display);
		handler.getDisplay().time();
		display.setHandler(handler);
		running = true;
		
		Assets.init();
		
		gsm = new GameStateManager(handler);	
		

	}
	
	@Override
	public void run() {
		init();
		
		long start;
		long wasted;
		long wait = 0;
		
		while(running) {
			start = System.nanoTime();
			
			update();
			drawBuffer();
			//draw();	
			wasted = System.nanoTime() - start;
			wait = timePerTick - wasted / 1000000;
			
			if(wait<0) { 
				wait = 0;
				System.out.println("Slow");
			}
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}

	}
	
	private void update() {
		gsm.update();
		Keys.update();
		Mouse.update();
	}
	
	private void drawBuffer() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		//System.out.println(Thread.currentThread().getName() );
		
		g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		gsm.draw(g);
		
		bs.show();
		
		g.dispose();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	private void draw() {
//		Graphics g2 = getGraphics();
//		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
//		g2.dispose();
//	}

	@Override
	public void keyPressed(KeyEvent e) {	
		Keys.keySet(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.setWhere(e.getX(), e.getY());
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {

		
	}
	@Override
	public void mouseExited(MouseEvent e) {

		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.keySet(e.getButton(), true);
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.keySet(e.getButton(), false);
	}

	

}
