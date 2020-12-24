package Server;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import Client.Client;

public class Packet {

    
    public byte[] data;
    private InetAddress ipAddress;
    int port;

    public Packet(String data, InetAddress ipAddress, int port) {
        this.data = data.getBytes();
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public Packet(String data) {
        this.data = data.getBytes();
        String packetData = new String(this.data, StandardCharsets.UTF_8);
    }

    public void writeData(Client client) {
    	client.sendData(this.data);
    };

    public  void writeData(Server server) {
    	server.sendData(this.data, this.ipAddress, this.port);
    };
    
    public void writeDataToAll(Server server) {
    	
    }

    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }
    
    public String forWho(byte[] data) {
    	String message = new String(data).trim();
    	return message.substring(2,3);
    }


}
