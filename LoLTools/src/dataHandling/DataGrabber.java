/*Powered by Elophant.com
 *Developer key=********
 *HTTP Data call: api.elophant.com/v2/<region>/<resource>?key=********
 *API: http://elophant.com/developers/docs
 *<Recource>					<Parameter>
 *champions
 *summoner					String summonerName
 *mastery_pages				int    summonerID
 *rune_pages				int    summonerID
 *recent_games				int	   accountID
 *leagues					int    summonerID
 *ranked_stats				int    accountID,String season
 *in_progress_game_info		String summonerName
 */
package dataHandling;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.*;

public class DataGrabber {
	private final static String key = "UdMVjnHOfK22quVzeWA4";
	public final static String REGION_NA = "NA";
	public final static String REGION_EUW = "EUW";
	public final static String REGION_EUNE = "EUNE";
	public final static String REGION_BR = "BR";
	private final static String httpCall = "http://api.elophant.com/v2/<region>/<resource>/<parameter>?key=" + key;
	public final static HashMap<Integer, String> championKey = initializeChampionKey();

	public static GameInfo getInProgressGameInfo(String summonerName,String region) throws Exception {
		return new GameInfo(getJSONString(region, "/in_progress_game_info/", summonerName),region);
	}

	public static String[] getLeagueRank(int summonerID,String region) {
		String[] leagues={"Unranked","Unranked","Unranked"};
		List<Integer> threesLeague=new ArrayList<Integer>();
		List<Integer> fivesLeague=new ArrayList<Integer>();
		try {
			JSONObject json = new JSONObject(getJSONString(region, "/leagues/", Integer.toString(summonerID)));
			for (int x = 0; x < json.getJSONObject("data").getJSONArray("summonerLeagues").length(); x++) {
				switch (json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("queue")) {
					case "RANKED_TEAM_3x3":
						threesLeague.add(leagueToInteger(json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("tier")+" "+json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("requestorsRank")));
						break;
					case "RANKED_TEAM_5x5":
						fivesLeague.add(leagueToInteger(json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("tier")+" "+json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("requestorsRank")));
						break;
					case "RANKED_SOLO_5x5":
						leagues[1]=json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("tier")+" "+json.getJSONObject("data").getJSONArray("summonerLeagues").getJSONObject(x).getString("requestorsRank");
						break;
				}
			}

		} catch (Exception e) {
		}
		try {
			leagues[0] = integerToLeague(Collections.max(threesLeague));
		} catch (Exception e) {
		}
		try {
			leagues[2] = integerToLeague(Collections.max(fivesLeague));
		} catch (Exception e) {
		}
		return leagues;
	}

	public static int getSummonerLevel(String summonerName,String region) {
		try {
			return new JSONObject(getJSONString(region, "/summoner/", summonerName)).getJSONObject("data").getInt("summonerLevel");
		} catch (JSONException e) {
			return 0;
		}
	}

	private static HashMap<Integer, String> initializeChampionKey() {
		try {
			JSONObject json = new JSONObject(getJSONString("", "champions", ""));
			HashMap<Integer, String> championKey = new HashMap<Integer, String>(json.getJSONArray("data").length());
			for (int x = 0; x < json.getJSONArray("data").length(); x++) {
				championKey.put(json.getJSONArray("data").getJSONObject(x).getInt("id"), json.getJSONArray("data").getJSONObject(x).getString("name"));
			}
			return championKey;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getJSONString(String region, String resource, String parameter) {
		String url=httpCall.replaceFirst("<region>/", region).replaceFirst("<resource>/", resource).replaceFirst("<parameter>", parameter).replaceAll(" ", "");

		StringBuilder jsonstring = new StringBuilder();
		int c;
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((c = br.read()) != -1)
				jsonstring.append((char) c);
			is.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		
		return jsonstring.toString();
	}
	private static int leagueToInteger(String s){
		int value = 0;
		switch(s.substring(0, s.indexOf(" "))){
			case "CHALLENGER":
				value+=50;
				break;
			case "DIAMOND":
				value+=40;
				break;
			case "PLATNIUM":
				value+=30;
				break;
			case "GOLD":
				value+=20;
				break;
			case "SILVER":
				value+=10;
		}
		switch(s.substring(s.indexOf(" ")+1, s.length())){
			case "I":
				value+=5;
				break;
			case "II":
				value+=4;
				break;
			case "III":
				value+=3;
				break;
			case "IV":
				value+=2;
				break;
			case "V":
				value+=1;
		}
		return value;
	}
	private static String integerToLeague(int i){
		StringBuilder s=new StringBuilder();
		switch((int)i/10){
			case 5:
				return "Challenger";
			case 4:
				s.append("Diamond");
				break;
			case 3:
				s.append("Platnium");
				break;
			case 2:
				s.append("Gold");
				break;
			case 1:
				s.append("Silver");
				break;
			case 0:
				s.append("Bronze");
		}
		s.append(" Division ");
		switch((int)i%10){
			case 5:
				s.append("I");
				break;
			case 4:
				s.append("II");
				break;
			case 3:
				s.append("III");
				break;
			case 2:
				s.append("IV");
				break;
			case 1:
				s.append("V");
		}
		return s.toString();
	}
}
