package dataHandling;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
public class GameInfo {
	private JSONObject data;
	private String gametype;
	private String region;
	GameInfo(String source,String r) throws Exception {
		try{
			data = new JSONObject(source);
			region=r;
			gametype=data.getJSONObject("data").getJSONObject("game").getString("queueTypeName");
		}catch(JSONException e){
			throw new Exception("Servers are unavailable.");
		}catch(Exception e){
			System.out.println("erra"+data.getString("error"));
			throw new Exception(data.getString("error"));

		}
	}
	public String getGameType(){
		return gametype;
	}
	public String getRegion(){
		return region;
	}
	public List<Player> getTeamOnePlayers() {
		List<Player> players = new ArrayList<Player>();
		for (int x = 0; x < data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").length(); x++){
			players.add(new Player(
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").getJSONObject(x).getInt("summonerId"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").getJSONObject(x).getInt("accountId"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").getJSONObject(x).getString("summonerName"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").getJSONObject(x).getString("summonerInternalName"),
					getPickIDs(data.getJSONObject("data").getJSONObject("game").getJSONArray("teamOne").getJSONObject(x).getString("summonerInternalName")),region));
		}
		return players;
	}

	public List<Player> getTeamTwoPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (int x = 0; x < data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").length(); x++){
			players.add(new Player(
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").getJSONObject(x).getInt("summonerId"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").getJSONObject(x).getInt("accountId"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").getJSONObject(x).getString("summonerName"),
					data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").getJSONObject(x).getString("summonerInternalName"),
					getPickIDs(data.getJSONObject("data").getJSONObject("game").getJSONArray("teamTwo").getJSONObject(x).getString("summonerInternalName")),region));
		}
		return players;
	}
	private int[] getPickIDs(String name){
		int[] ids = null;
		for(int x=0;x<data.getJSONObject("data").getJSONObject("game").getJSONArray("playerChampionSelections").length();x++){
			if(data.getJSONObject("data").getJSONObject("game").getJSONArray("playerChampionSelections").getJSONObject(x).getString("summonerInternalName").equalsIgnoreCase(name)){
				ids=new int[]{data.getJSONObject("data").getJSONObject("game").getJSONArray("playerChampionSelections").getJSONObject(x).getInt("championId")
						,data.getJSONObject("data").getJSONObject("game").getJSONArray("playerChampionSelections").getJSONObject(x).getInt("spell1Id")
						,data.getJSONObject("data").getJSONObject("game").getJSONArray("playerChampionSelections").getJSONObject(x).getInt("spell2Id")};
			}
		}
		return ids;
	}
}
