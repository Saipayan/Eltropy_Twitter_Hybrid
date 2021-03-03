package Base;

import java.util.Comparator;

public class FollowerData {

	
	
	public  String ScreenName;
	
	public  String FriendCount;
	
	public  String FollowerCount;
	
	
	

	public static Comparator<FollowerData> FollowerComparator = new Comparator<FollowerData>() {       
		
		
	    @Override         
	    public int compare(FollowerData jc1, FollowerData jc2) {             
	      return (Integer.parseInt(jc2.FollowerCount) < Integer.parseInt(jc1.FollowerCount) ? -1 :                     
	              (Integer.parseInt(jc2.FollowerCount) == Integer.parseInt(jc1.FollowerCount) ? 0 : 1));           
	    }     
	  };     
	  
}
