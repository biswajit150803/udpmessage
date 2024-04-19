import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Client {
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;
    public Client(DatagramSocket datagramSocket,InetAddress inetAddress){
        this.datagramSocket=datagramSocket;
        this.inetAddress=inetAddress;
    }
    public void sendThenReceive(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            try{
                String message=scanner.nextLine();
                buffer=message.getBytes();
                DatagramPacket datagramPacket=new DatagramPacket(buffer,buffer.length,inetAddress,8080);
                datagramSocket.send(datagramPacket);
                buffer=new byte[256];
                datagramPacket=new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                message=new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("Message from server: "+message);
            }catch(IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        DatagramSocket datagramSocket=new DatagramSocket();
        InetAddress inetAddress=InetAddress.getByName("localhost");
        Client client=new Client(datagramSocket,inetAddress);
        System.out.println("Send datagram packets to a server");
        client.sendThenReceive();
    }
}
