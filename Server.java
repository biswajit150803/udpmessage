// operates on top of IP to transmit data over a network
// Connectionless meaning no end-to-end connection is established before data is sent
// Fast making it useful for query ressponse protocols such as DNS
// Lack of verification makes it unreliable leading to denial of service attacks
// DatagramSocket class is used to send and receive UDP packets
import java.net.*;
import java.io.*;
public class Server {
    private DatagramSocket datagramSocket;
    private byte[] buffer=new byte[256];
    public Server(DatagramSocket datagramSocket){
        this.datagramSocket=datagramSocket;
    }
    public void receiveThenSend(){
        while(true){
            try{
                DatagramPacket datagramPacket=new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress=datagramPacket.getAddress();
                int port=datagramPacket.getPort();
                String message=new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("Message from client: "+message);
                datagramPacket=new DatagramPacket(buffer,buffer.length,inetAddress,port);
                datagramSocket.send(datagramPacket);
            }catch(IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args) throws SocketException{
        try{
            DatagramSocket datagramSocket=new DatagramSocket(8080);
            Server server=new Server(datagramSocket);
            server.receiveThenSend();
        }catch(SocketException e){
            e.printStackTrace();
        }
    }
}