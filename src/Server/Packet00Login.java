package Server;

import java.net.InetAddress;

public class Packet00Login extends Packet {

    public Packet00Login(String data) {
        super("00"+data);
    }
    
}