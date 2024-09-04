package AdvancedFeatures.Chapter4;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientSocketTest {
    private static void learning() throws IOException {
        // Socket套接字 //
        // Socket s = new Socket(ip, port);  建议使用try-with-resource
        // 为了防止堵塞，可设置超时值
        // Socket s = new Socket();
        // s.connect(new InetSocketAddress(ip, port), TimeOut_ms);
        // s.setSoTimeout(TimeOut_ms);
        // 状态判断：isConnected() isClosed()
        // 半关闭：shutdownOutput() 设置输出流结束
        // SocketChannel：非阻塞的Socket，使用SocketChannel.open方法

        // 因特网地址
        InetSocketAddress socketAddress = new InetSocketAddress("www.baidu.com", 80); // 使用主机名解析
        if (!socketAddress.isUnresolved()) {
            socketAddress.getAddress();
        }
        InetAddress localhost = InetAddress.getLocalHost();     // 本地主机地址
        System.out.println(localhost);

        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");  // 多ip的域名
        for (InetAddress address : addresses) {
            byte[] addressBytes = address.getAddress();         // 获取ip的字节序列
            String addressString = address.getHostAddress();    // 获取显示的ip字符串
            String HostName = address.getHostName();            // 获取主机名

            System.out.println(addressString);
            System.out.println(HostName);
        }
    }

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket()) {
            clientSocket.setSoTimeout(3000);
            clientSocket.connect(new InetSocketAddress("localhost", 8189), 5000);
            System.out.println("Connect successfully!");

            try (var in = new Scanner(clientSocket.getInputStream(), StandardCharsets.UTF_8);
                 var out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
                         StandardCharsets.UTF_8), true)) {
                while (clientSocket.isConnected() && in.hasNextLine()) {
                    System.out.println("Client Receive: " + in.nextLine());
                    var systemInput = new Scanner(System.in, StandardCharsets.UTF_8);
                    String line = systemInput.nextLine();
                    out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Disconnect. Bye!");
    }
}
