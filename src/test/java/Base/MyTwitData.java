package Base;

import java.util.Comparator;

public class MyTwitData {

	public String CreatedAt;
	public String id;
	public String name;
	public String text;
	public String favCount;
	public String retweetCount;
	
	
	
	public static Comparator<MyTwitData> RetweetComarator = new Comparator<MyTwitData>() {       
		
		
	    @Override         
	    public int compare(MyTwitData jc1, MyTwitData jc2) {             
	      return (Integer.parseInt(jc2.retweetCount) < Integer.parseInt(jc1.retweetCount) ? -1 :                     
	              (Integer.parseInt(jc2.retweetCount) == Integer.parseInt(jc1.retweetCount) ? 0 : 1));           
	    }     
	  };     
	  
	  
	
}
