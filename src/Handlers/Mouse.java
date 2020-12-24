package Handlers;

import java.awt.event.MouseEvent;

public class Mouse {

	public static final int NUM_KEYS = 5;
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	public static int mouseX, mouseY;
	public static int LEFT = 0;
	public static int RIGHT = 1;

	public static void keySet(int i, boolean b) {
		if (i == MouseEvent.BUTTON1)
			keyState[LEFT] = b;
		else if (i == MouseEvent.BUTTON3)
			keyState[RIGHT] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public static void setWhere(int x, int y) {
		mouseX = x;
		mouseY = y;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static void setMouseX(int mouseX) {
		Mouse.mouseX = mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static void setMouseY(int mouseY) {
		Mouse.mouseY = mouseY;
	}

}
