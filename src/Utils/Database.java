package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingWorker;

import Handlers.Handlers;

public class Database {
	private Connection conn;
	private Handlers handler;
	public Database(Handlers handler) {
		this.handler = handler;
		connect();
		
	}
	
	
	private void connect() {

		SwingWorker<Void, Void> konak = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pbo?serverTimezone=UTC", "root",
							"");
					if (conn != null) {
						System.out.println("Connected to Database");
					}

				} catch (SQLException e) {
					System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

		};

		konak.execute();

	}


	public Connection getConn() {
		return conn;
	}
	public void addData(String player1, String player2, String hasil, String comp) {
		SwingWorker<Void, Void> konak = new SwingWorker<Void, Void>() {

			@SuppressWarnings("resource")
			@Override
			protected Void doInBackground() throws Exception {
				String getId = "SELECT ID FROM USER WHERE player_1 = '" + player1 + "' AND player_2 = '" + player2 + "';";
				
				String sql = "INSERT INTO user (player_1, player_2) VALUES ('" + player1 + "', '" + player2 + "')";
				
				
				try {
					
					PreparedStatement preparedStatement = conn.prepareStatement(getId);
					
					ResultSet result = preparedStatement.executeQuery();
					
					if(!result.next()) {
						System.out.println("first time played");
						ResultSet result2;
						
						preparedStatement = conn.prepareStatement(sql);
						preparedStatement.execute();
						
						preparedStatement = conn.prepareStatement(getId);
						result2 = preparedStatement.executeQuery();
						result2.next();
						String id = result2.getString("id");
						
						String sql2 = "INSERT INTO time (id_player, time, compatibility) VALUES ('" + id + "', '" + hasil + "', '" +comp+ "')";
						preparedStatement = conn.prepareStatement(sql2);
						preparedStatement.execute();
						
						
					}
					else {
						System.out.println("second time playerd");
						String id = result.getString("id");
						System.out.println(id);
						String sql2 = "INSERT INTO time (id_player, time, compatibility) VALUES (" + id + ", '" + hasil + "', '" + "bagus')";
						preparedStatement = conn.prepareStatement(sql2);
						preparedStatement.execute();
						
					}
					
//					preparedStatement = conn.prepareStatement(sql);
//					preparedStatement.execute();
					System.out.println("berhasil");
				} catch (SQLException e) {
					System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		};

		konak.execute();

	}
	
	
	public void getData() {
		
		SwingWorker<Void, Void> konak = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
			
				String sql = "SELECT PLAYER_1, PLAYER_2, time, compatibility FROM TIME LEFT JOIN USER ON TIME.id_player = USER.id ORDER BY TIME ASC LIMIT 10";
				
				
				try {
					
					PreparedStatement preparedStatement = conn.prepareStatement(sql);
					
					ResultSet result = preparedStatement.executeQuery();
					
					while(result.next()) {
						
						String player1 = result.getString("Player_1");
						String player2 = result.getString("Player_2");
						String time = result.getString("Time");
						String comp = result.getString("compatibility");
						
						handler.getDisplay().getDefTableModel().addRow(new Object[] { player1, player2, time, comp });
						
					}
					System.out.println("berhasil");
				} catch (SQLException e) {
					System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		};

		konak.execute();
		
	}
	
}
