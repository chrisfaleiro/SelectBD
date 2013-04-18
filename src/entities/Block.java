package entities;

import java.util.ArrayList;
import java.util.List;

public class Block {
				
	private List<Tuple> tuples = new ArrayList<Tuple>();
	
	public Block addTuple(Tuple tuple){
		this.tuples.add(tuple);
		return this;
	}
	
	public List<Tuple> getTuples(){
		return tuples;
	}
	
	public void removeLastTuple(){
		this.tuples.remove(this.tuples.size()-1);
	}
	
	public String toString(){
		String str = "";
		
		for (Tuple tuple : tuples){
			str += tuple.toString() + "\n";			
		}
		return str;
	}
}
