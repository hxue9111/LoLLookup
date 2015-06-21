package dataHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Debug {
	public static void main(String args[]) {
		final String apiKey = "";
		String jsonString = JSONString("http://api.elophant.com/v2/na/in_progress_game_info/crsvoyboy?&key="+apiKey);
		System.out.println(jsonString);
		GameInfo data;
		try {
			data = new GameInfo(jsonString,DataGrabber.REGION_NA);

		for (Player a : data.getTeamOnePlayers())
			System.out.println(a.getsummonerName()+" "+a.getsummonerInternalName()+" "+a.getsoloLeague());
		for (Player a : data.getTeamTwoPlayers())
			System.out.println(a.getsummonerName()+" "+a.getsummonerInternalName()+" "+a.getsoloLeague());
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		//System.out.println(DataGrabber.getJSONString("na/","in_progress_game_info/", "idk what im doin"));
		for(Map.Entry a: DataGrabber.championKey.entrySet())
			System.out.println("ID: "+a.getKey()+" Name: "+a.getValue());
	}

	public static String JSONString(String URL) {
		StringBuilder data = new StringBuilder();
		int c;
		try {
			InputStream is = new URL(URL).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((c = br.read()) != -1)
				data.append((char) c);
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data.toString();
	}
}
