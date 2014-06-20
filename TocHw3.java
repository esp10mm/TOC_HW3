import java.util.ArrayList;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import org.json.*;

public class TocHw3 {
	public static void main(String argv[]) throws Exception{
		// declaring variables
		String content;
		ArrayList<JSONObject> target = new ArrayList<JSONObject>();
		if(argv.length == 0)return;

		// sending GET request
		try{
			content = sendGet(argv[0]);
		}catch(Exception e){
			return;
		}

		// converting request result to JSON object
		JSONArray array = new JSONArray(content);

		// searching for targets
		JSONObject obj;
		for(int i=0;i<array.length();i++){
			obj = array.getJSONObject(i);
			if(argv[1].equals(obj.getString("鄉鎮市區"))){
				if(obj.getString("土地區段位置或建物區門牌").contains(argv[2])){
					int startYear = Integer.parseInt(argv[3]);
					if(obj.getInt("交易年月")>startYear*100)
						target.add(obj);
				}
			}
		}

		// output result
		int avg_price = 0;
		for(JSONObject r : target)
			avg_price += r.getInt("總價元");
		if(target.size() != 0)
			System.out.println(avg_price/target.size());
	}
	
	private static String sendGet(String url) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
		int responseCode = con.getResponseCode();
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),"UTF8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine+'\n');
		in.close();
 
		return response.toString();
	}
}

 
