package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import Client.Client;

public class Packet {

    
    public String data;
    private InetAddress ipAddress;
    int port;

    public Packet(String data, InetAddress ipAddress, int port) {
        this.data = data;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public Packet(String data) {
    	this.data = data; 
        String packetData = new String(this.data);
    }

    public void writeData(Client client) throws IOException {
    	client.sendData(this.data);
    };

    public  void writeData(Server server) throws IOException {
    	server.sendData(this.data);
    };
   

    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }
    
    public String forWho(byte[] data) {
    	String message = new String(data).trim();
    	return message.substring(2,3);
    }


}
