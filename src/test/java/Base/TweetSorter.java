package Base;

import java.util.ArrayList;
import java.util.Collections;

public class TweetSorter {

	ArrayList<MyTwitData> ListOFTweets = new ArrayList<>();       

	  public TweetSorter(ArrayList<MyTwitData> jobCandidate) {         
	    this.ListOFTweets = jobCandidate;     
	  }       

	  public ArrayList<MyTwitData> getSortedJobCandidateByRetweet() {         
	    Collections.sort(ListOFTweets, MyTwitData.RetweetComarator);         
	    return ListOFTweets;     
	  }       

	
	
	
}
