package dataHandling;
public class Player {
	private final String summonerName,summonerInternalName,threesLeague,soloLeague,fivesLeague,region;
	private final int summonerID,accountID,champID,spell1ID,spell2ID,summonerLevel;
	Player( int sID,int aID,String sName,String sIname,int[] id,String r){
		region=r;
		summonerID = sID;
		accountID = aID;
		champID = id[0];
		spell1ID = id[1];
		spell2ID = id[2];
		summonerName = sName;
		summonerInternalName = sIname;
		summonerLevel= DataGrabber.getSummonerLevel(summonerInternalName,region); 
		String[] l= DataGrabber.getLeagueRank(summonerID,region);
		threesLeague=l[0];
		soloLeague=l[1];
		fivesLeague=l[2];
	}
	public int getSummonerID(){
		return summonerID;
	}
	public int getaccountID(){
		return accountID;
	}
	public int getchampID(){
		return champID;
	}
	public int getspell1ID(){
		return spell1ID;
	}
	public int getspell2ID(){
		return spell2ID;
	}
	public int getsummonerLevel(){
		return summonerLevel;
	}
	public String getsummonerInternalName(){
		return summonerInternalName;
	}
	public String getthreesLeague(){
		return threesLeague;
	}
	public String getsoloLeague(){
		return soloLeague;
	}
	public String getfivesLeague(){
		return fivesLeague;
	}
	public String getsummonerName(){
		return summonerName;
	}
}
