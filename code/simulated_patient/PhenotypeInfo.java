package org.hit.hanson.disease;

public class PhenotypeInfo {
	public String id;
	public String gender;
	public double penetrance;

	public PhenotypeInfo(String id, String gender, double penetrance) {
		this.id = id;
        this.gender = gender;
        this.penetrance = penetrance;
	}

	public PhenotypeInfo() {
		// TODO Auto-generated constructor stub
	}
}
