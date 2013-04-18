package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Block {
				
	private List<Tuple> tuples = new ArrayList<Tuple>();
	private int size;
	
	public Block(){}
	
	public Block(int size){
		this.size = size;
	}
	
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
	
	public void addAllTuples(Collection<Tuple> c){
		this.tuples.addAll(c);
	}
	
	public int getSize(){
		return size;
	}
	
	public String toString(){
		String str = "";
		
		for (Tuple tuple : tuples){
			str += tuple.toString() + "\n";			
		}
		return str;
	}
}
