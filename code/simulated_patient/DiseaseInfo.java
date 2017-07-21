package org.hit.hanson.disease;

import java.util.Collection;
import java.util.HashSet;

public class DiseaseInfo {
	public String causativeGene = "";
	public String name = "";
	public Collection<PhenotypeInfo> phenotypeSet = new HashSet<PhenotypeInfo>();
}
