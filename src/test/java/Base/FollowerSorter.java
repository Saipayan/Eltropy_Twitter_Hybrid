package Base;

import java.util.ArrayList;
import java.util.Collections;

public class FollowerSorter {

	ArrayList<FollowerData> ListOFTweets = new ArrayList<>();       

	  public FollowerSorter(ArrayList<FollowerData> jobCandidate) {         
	    this.ListOFTweets = jobCandidate;     
	  }       

	  public ArrayList<FollowerData> getSortedJobCandidateByRetweet() {         
	    Collections.sort(ListOFTweets, FollowerData.FollowerComparator);         
	    return ListOFTweets;     
	  }       

	
	
	
	
	
}
