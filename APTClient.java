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
		
		// initiate connection
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
		
		// read in the main content of database
		out.println("pull");
		String jsonMn = in.readLine().substring(3);
		JSONObject jsonMain = new JSONObject(jsonMn);
		System.out.println(jsonMain.toString());
		
		//read the change snippets and implement
		while(true){
			String jsonCh = in.readLine().substring(3);
			JSONObject json = new JSONObject(jsonCh);
			
			System.out.println(json.toString());
			//implement the new changes to the Main JSON object
			//I assume it is one change per entry from the server!
			String collection = json.getString("collection");
			int id = json.getInt("id");
			JSONObject change = (JSONObject) json.get("change");
			if (jsonMain.has(collection)){
				JSONArray tempJSON = (JSONArray) jsonMain.getJSONArray(collection);
				boolean hasID = false;
				for (int i = 0; i < tempJSON.length(); i++) {
					JSONObject temptempJSON = tempJSON.getJSONObject(i);
					if(temptempJSON.has("id") == true){
						if (temptempJSON.getInt("id") == id){
							
							JSONObject oJSON = new JSONObject();
							hasID = true;
							Iterator i1 = temptempJSON.keys();
							Iterator i2 = change.keys();
							String tmp_key;
							while(i1.hasNext()) {
							    tmp_key = (String) i1.next();
							    oJSON.put(tmp_key, temptempJSON.get(tmp_key));
							}
							while(i2.hasNext()) {
							    tmp_key = (String) i2.next();
							    oJSON.put(tmp_key, change.get(tmp_key));
							}
							tempJSON.put(oJSON);						
						}
					}
					
				}
				if(hasID == false){
				JSONObject nJSON = change.append("id", id);
				tempJSON.put(nJSON);
				}
			}
			else{
				JSONObject mJSON = change.append("id", id);
				jsonMain.put(collection, mJSON);
			}

			System.out.println(jsonMain.toString());
		}
		

//		out.close();
//		in.close();
//		socket.close();
	}
}
