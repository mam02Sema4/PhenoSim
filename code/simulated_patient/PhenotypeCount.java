package org.hit.hanson.disease;

import org.hit.hanson.jung.Vertex;

public class PhenotypeCount implements Comparable<PhenotypeCount>{
	int count = 0;
	Vertex phenotype;
	@Override
	public int compareTo(PhenotypeCount another) {
		if(another.count>this.count)
			return 1;
		else if(another.count<this.count)
			return -1;
		else
			return -1;
	}
	

}
