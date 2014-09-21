import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class APTClient {
	public static void main(String [] args) throws UnknownHostException, IOException, 
	ClassNotFoundException, InterruptedException, JSONException
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

		InputStream inFromServer = socket.getInputStream();
		
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(inFromServer));
		System.out.println(in.readLine());
//		in.close();
		
		// pull command -- *** to be internalised!
		Scanner inputString = new Scanner(System.in);
		System.out.println("please write command: ");
		String comToServer = inputString.next();
		out.println(comToServer);
				
		// read JSON from the InputStream
		StringBuilder builder = new StringBuilder();
		String line;
		line = in.readLine();
		builder.append(line.substring(3));
		
		String jsonIn = builder.toString();
		
		System.out.println(jsonIn);
		
		JSONObject json = new JSONObject(jsonIn);
		String output = json.toString();
		
		System.out.println(output);
		
		out.close();
		in.close();
		socket.close();
	}
}
