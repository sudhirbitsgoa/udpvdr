/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpVdr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;

public class Main {
   public void multicast(
      String multicastMessage) throws IOException {
        InetAddress group = InetAddress.getByName("228.5.6.7");
        MulticastSocket s = new MulticastSocket();
        s.joinGroup(group);
        DatagramPacket hi = new DatagramPacket(multicastMessage.getBytes(), multicastMessage.getBytes().length,
                                    group, 7777);
        s.send(hi);
    }
   
   private static String MULTICAST_INTERFACE;
   private static int MULTICAST_PORT;
   private static String MULTICAST_IP;
   public final static char CR  = (char) 0x0D;
   public final static char LF  = (char) 0x0A; 
   public final static char NULL = (char) 0X00;
   
   public void sendMessage(String ip, String iface, int port,
         String message) throws IOException {
      DatagramChannel datagramChannel=DatagramChannel.open();
      datagramChannel.bind(null);
      NetworkInterface networkInterface=NetworkInterface
         .getByName(iface);
      datagramChannel.setOption(StandardSocketOptions
         .IP_MULTICAST_IF,networkInterface);
      // System.out.println(message.getBytes());
      message = message + CR + LF;
      // System.out.println(message.getBytes());
      ByteBuffer byteBuffer=ByteBuffer.wrap
         (message.getBytes());
      InetSocketAddress inetSocketAddress=new
         InetSocketAddress(ip,port);
      datagramChannel.send(byteBuffer,inetSocketAddress);
   }
   
   public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                System.out.println(iface.getName() + " supports multicast " + iface.supportsMulticast());
            }
            return;
        }
        if ("--help".equals(args[0])) {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                System.out.println(iface.getName() + " supports multicast " + iface.supportsMulticast());
            }
            return;
        }
       
        MULTICAST_INTERFACE = args[0];
        MULTICAST_PORT = Integer.parseInt(args[1]);
        MULTICAST_IP = args[2];
        Main mp = new Main();
        // String msg1 = "$GPRMC,132537,A,3753.2515,N,02429.2311,E,19956.8,19.4,130121,5,E,A*3A";
        String msg1 = args[3];
        if (args.length == 5 && "nullChar".equals(args[4])) {
            String[] arr = msg1.split("UdPbC");
            msg1 = "UdPbC" + NULL + arr[1];
        }
        // mp.multicast("$GPRMC,132537,A,3753.2515,N,02429.2311,E,19956.8,19.4,130121,5,E,A*3A");
        mp.sendMessage(MULTICAST_IP,MULTICAST_INTERFACE,
         MULTICAST_PORT, msg1);
        System.out.println(msg1 + " multicast to "+ args[2] +" on port " + args[1]);
   }
}