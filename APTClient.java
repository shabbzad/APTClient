import java.net.*;
import java.io.*;
import java.util.*;

public class APTClient {
	public static void main(String [] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException
	{
//		String serverName = args[0];
//		int port = Integer.parseInt(args[1]);
		
		String serverName = "devdeb.legendaarne.eu";
		int port = 22800;
		String key = "n4sa";
		
		// initial connection
		Socket socket = new Socket(serverName, port);
		System.out.println("Connected to " + socket.getRemoteSocketAddress());

		// setting up the input and output streams
		PrintWriter out = 
				new PrintWriter(socket.getOutputStream(),true);
		out.println(key);

		BufferedReader in = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println(in.readLine());
		
		Scanner inputString = new Scanner(System.in);
		System.out.println("C: Please write command for the server: ");
		String comToServer = inputString.next();
		out.println(comToServer);
		System.out.println(in.readLine());
		
		socket.close();
	}
}
