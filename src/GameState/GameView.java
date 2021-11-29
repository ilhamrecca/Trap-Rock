package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Entities.Piece;
import Handlers.AudioPlayer;
import Handlers.Handlers;
import Handlers.Mouse;
import Server.Packet00Login;
import Server.Packet01Move;
import Utils.ImageLoader;

public class GameView {
	private Handlers handler;
	private Font font;
	private int widthOffset = 265;
	private int heightOffset = 185;
	private int objSize;
	private int mapWidth = 470;
	private int mapheight = 470;
	private Piece[] pieces = { (new Piece(widthOffset, heightOffset, 1)),
			(new Piece(widthOffset + mapWidth, heightOffset, 1)), (new Piece(widthOffset, heightOffset + mapheight, 2)),
			(new Piece(widthOffset + mapWidth, heightOffset + mapheight, 2)), (new Piece(0, 0, 0)), };
	private int[][] originalPosition = { { widthOffset, heightOffset }, { widthOffset + mapWidth, heightOffset },
			{ widthOffset, heightOffset + mapheight }, { widthOffset + mapWidth, heightOffset + mapheight },
			{ widthOffset + mapWidth / 2, heightOffset + mapheight / 2 }, };

	private BufferedImage rock1, rock2;
	private boolean draggingObject = false;
	private int currentIndex = -2;
	private int numTurn = 1;

	public GameView(Handlers handler) {
		this.handler = handler;
		font = new Font("Arial", Font.PLAIN, 40);
//		numTurn = (handler.getPlayer().compareTo("1")== 0) ? 0 : 1; 
		numTurn = 0;
		objSize = 80;
		rock1 = ImageLoader.loadImage("Object/player1.png");
		rock2 = ImageLoader.loadImage("Object/player2.png");
		AudioPlayer.loop("gameplay", 600, AudioPlayer.getFrames("gameplay") - 44000);

	}

	public void draw(Graphics2D g) {

//		g.drawRect(widthOffset, heightOffset, 680, 550);
//		g.drawLine(widthOffset, heightOffset, 830, 650);
//		g.drawLine(widthOffset + 680, heightOffset, widthOffset, 650);
//		g.fillRect(Mouse.getMouseX() - 10, Mouse.getMouseY() - 10, 20, 20);
		BufferedImage player = null;
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i].getPlayer() != 0) {
				if (!draggingObject || i != currentIndex) {
					if (pieces[i].getPlayer() == 1) {
						player = rock1;
					} else if (pieces[i].getPlayer() == 2) {
						player = rock2;
					}

					g.drawImage(player, pieces[i].getxPos() - objSize / 2, pieces[i].getyPos() - objSize / 2, objSize,
							objSize, null);
//					g.setColor(Color.GREEN);
				} else {
					if (pieces[i].getPlayer() == 1) {
						player = rock1;
					} else if (pieces[i].getPlayer() == 2) {
						player = rock2;
					}
					g.drawImage(player, Mouse.getMouseX() - objSize / 2, Mouse.getMouseY() - objSize / 2, objSize,
							objSize, null);
				}

			}

		}

	}

	private Rectangle getCollisionBound(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height);
	}

	public void handleInput() {
		// ((numTurn)%2+1) == Integer.parseInt(handler.getPlayer())
		if (Mouse.isPressed(Mouse.LEFT) && !draggingObject) {
			System.out.println("Number of Turn : " + numTurn);
			currentIndex = checkMouseHover();
			if (currentIndex != -1) {
				if (pieces[currentIndex].getPlayer() != 0) {
					if (numTurn % 2 == 0 && handler.getPlayer().compareTo("1") == 0) {
						if (pieces[currentIndex].getPlayer() == 1) {
							draggingObject = true;
							System.out.println("Player 1 Turn Now Dragging : " + currentIndex);
						} else {
							System.out.println("it's Not Your turn");
						}
					} else if (numTurn % 2 == 1 && handler.getPlayer().compareTo("2") == 0) {

						if (pieces[currentIndex].getPlayer() == 2) {
							draggingObject = true;
							System.out.println("Player 2 Turn Now Dragging : " + currentIndex);
						} else {
							System.out.println("it's Not Your turn");
						}
					}

				} else {
					currentIndex = -2;
				}

			}
		} else if (Mouse.isPressed(Mouse.LEFT) && draggingObject) {

			draggingObject = false;
			int curPosition = checkMouseHover();
			System.out.println("dragged into : " + curPosition);
//			currentIndex = checkMouseHover();
			if (curPosition != -1) {
				if (pieces[curPosition].getPlayer() == 0) {
					boolean allowedMove = false;
					if (currentIndex == 0) {
						if (curPosition == 1 || curPosition == 4 || curPosition == 2) {
							allowedMove = true;
						}
					} else if (currentIndex == 1) {
						if (curPosition == 0 || curPosition == 4 || curPosition == 3) {
							allowedMove = true;
						}
					} else if (currentIndex == 2) {
						if (curPosition == 0 || curPosition == 4) {
							allowedMove = true;
						}
					} else if (currentIndex == 3) {
						if (curPosition == 1 || curPosition == 4) {
							allowedMove = true;
						}
					} else if (currentIndex == 4) {
						allowedMove = true;
					}
					if (allowedMove) {
						System.out.println("changing coordinate to current Index : " + currentIndex);
						move(curPosition, currentIndex);
						Packet01Move packet = new Packet01Move("" + curPosition + currentIndex);
						if (handler.getPlayer().compareTo("1") == 0) {
							try {
								packet.writeData(handler.getServer());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							try {
								packet.writeData(handler.getClient());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}
			}

		}
	}

	private int checkMouseHover() {
		for (int i = 0; i < originalPosition.length; i++) {
			if (getCollisionBound(originalPosition[i][0] - objSize / 2, originalPosition[i][1] - objSize / 2, objSize,
					objSize).contains(Mouse.getMouseX(), Mouse.getMouseY())) {
				return i;
			}
		}
		return -1;

	}

	public void move(int curPosition, int currentIndex) {
		pieces[curPosition].setxPos(originalPosition[curPosition][0]);
		pieces[curPosition].setyPos(originalPosition[curPosition][1]);
		pieces[curPosition].setPlayer(pieces[currentIndex].getPlayer());

		pieces[currentIndex].setxPos(0);
		pieces[currentIndex].setyPos(0);
		pieces[currentIndex].setPlayer(0);

		numTurn++;
	}

	private int checkMouseHoverMap() {
		for (int i = 0; i < originalPosition.length; i++) {
			if (getCollisionBound(originalPosition[i][0] - objSize / 2, originalPosition[i][1] - objSize / 2, objSize,
					objSize).contains(Mouse.getMouseX(), Mouse.getMouseY())) {
				return i;
			}
		}
		return -1;

	}

	public void update() {
//		if (draggingObject) {
//			map[currentIndex][0] = Mouse.getMouseX();
//			map[currentIndex][1] = Mouse.getMouseY();
//		}

	}

	private void playAgain(int player) {
		int restart;
		if (Integer.toString(player).compareTo(handler.getPlayer()) == 0) {
			restart = handler.getDisplay().playAgainConfirmationDialog("You Won ");
		} else {
			restart = handler.getDisplay().playAgainConfirmationDialog("You Lose ");
		}
		System.out.println("restart ? " + restart);
		if (restart == 0) {
			handler.getDisplay().waitingPlayer2();
			Packet00Login packet = new Packet00Login("");
			if (handler.getPlayer().compareTo("1") == 0) {
				try {
					packet.writeData(handler.getServer());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					packet.writeData(handler.getClient());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			if(handler.getPlayer().compareTo("1") == 0) {
				handler.getServer().close();
				handler.getServer().interrupt();
				
				
				handler.setServer(null);	
			}
			
			handler.getGsm().setState(GameStateManager.MENUSTATE);
			
		}
	}

	public void isFinished() {
		int player = ((numTurn + 1) % 2 + 1);
		if (pieces[1].getPlayer() == pieces[4].getPlayer() && pieces[0].getPlayer() == pieces[2].getPlayer()) {

//			handler.getGsm().setState(GameStateManager.SPLASHSCREEN);
			System.out.println("number of turn : " + numTurn);
			System.out.println("Player " + player + " Wins");
			playAgain(player);
		} else if (pieces[1].getPlayer() == pieces[3].getPlayer() && pieces[0].getPlayer() == pieces[4].getPlayer()) {
//			handler.getGsm().setState(GameStateManager.SPLASHSCREEN);
			System.out.println("number of turn : " + numTurn);
			System.out.println("Player " + player + " Wins");
			playAgain(player);
		}

	}

	public void addNumTurn() {
		numTurn += 1;
	}
}
