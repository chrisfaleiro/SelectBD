package util;

import java.util.Comparator;

import entities.Attribute;
import entities.Tuple;

public class TupleComparator implements Comparator<Tuple>{

	private String nameAttribute;
	
	public TupleComparator(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}
	
	@Override
	public int compare(Tuple tuple1, Tuple tuple2) {

		Attribute attributeLeft = tuple1.getAttributeByName(nameAttribute);
		Attribute attributeRight = tuple2.getAttributeByName(nameAttribute);
		
		Long leftID = Long.valueOf((String) attributeLeft.getValue());
		Long rightID = Long.valueOf((String) attributeRight.getValue());

		return leftID.compareTo(rightID);
	}


}
