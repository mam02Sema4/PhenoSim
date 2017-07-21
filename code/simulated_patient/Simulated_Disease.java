package org.hit.hanson.disease;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import org.hit.hanson.helper.FileHelper;

public class Simulated_Disease {

	public static void main(String[] args) {
		Simulated_Disease sd = new Simulated_Disease();
	    sd.getDiseaseInfo();
		sd.getSimulatedOMIMPatient();
        sd.getDisease_to_Phenotype();
        System.out.println("The Program is end...");
	}

	public HashMap<String, DiseaseInfo> getDiseaseInfo() {
		LinkedList<String> content = FileHelper.readFile("data/extracted_phenotype_annotation.txt");
		HashMap<String, DiseaseInfo> map = new HashMap<String, DiseaseInfo>();
		String[] tempString;
		int count = 0;
		DiseaseInfo diseaseInfo;
		PhenotypeInfo phenotypeInfo;
		double pentrance;// 外显率
		for (String line : content) {
			tempString = line.split("	");
			if (!tempString[0].equals("OMIM"))
				continue;
			switch (tempString[4]) {
			case "very rare":
				pentrance = 0.01;
				break;
			case "rare":
				pentrance = 0.05;
				break;
			case "occasional":
				pentrance = 0.075;
				break;
			case "frequent":
				pentrance = 0.33;
				break;
			case "typical":
				pentrance = 0.5;
				break;
			case "variable":
				pentrance = new Random().nextDouble() * 0.4 + 0.3;
				break;
			case "common":
				pentrance = 0.75;
				break;
			case "hallmark":
				pentrance = 0.90;
				break;
			case "obligate":
				pentrance = 1.0;
				break;
			default:
				pentrance = -1;
				break;
			}
			//System.out.println(tempString[4] + "    " + pentrance);
			phenotypeInfo = new PhenotypeInfo(tempString[3], "", pentrance);
			//System.out.println(phenotypeInfo.penetrance);
			if (map.containsKey(tempString[1])) {
				map.get(tempString[1]).phenotypeSet.add(phenotypeInfo);
			} else {
				diseaseInfo = new DiseaseInfo();
				diseaseInfo.name = tempString[2];
				diseaseInfo.phenotypeSet.add(phenotypeInfo);
				map.put(tempString[1], diseaseInfo);
			}
		}
		return map;
	}

	public void getSimulatedOMIMPatient() {
		HashMap<String, DiseaseInfo> map = getDiseaseInfo();
		LinkedList<String> writeList = new LinkedList<String>();
		Random random = new Random();
		double phenotypeSet;

		for (Entry<String, DiseaseInfo> entry : map.entrySet()) {
			if (entry.getValue().phenotypeSet.size() > 30) {
				for (int i = 0; i < 100; i++) {
					writeList.add("patientID:	" + entry.getKey() + "_" + i);
					writeList.add("causativeGene:	" + entry.getValue().name);
					writeList.add("patientGender:	");
					for (PhenotypeInfo pi : entry.getValue().phenotypeSet) {
						phenotypeSet = random.nextDouble();
						//System.out.println(pi.penetrance);
						if (phenotypeSet < pi.penetrance)
							writeList.add(pi.id);
					}
				}
			}
		}
		FileHelper.writeFile("result/simulated_patient_OMIM_optimal.txt", writeList);
	}
	
	public void getDisease_to_Phenotype(){
		LinkedList<String> writeList = new LinkedList<String>();
		for(Entry<String, DiseaseInfo> entry:getDiseaseInfo().entrySet()){
			for(PhenotypeInfo pi: entry.getValue().phenotypeSet){
				writeList.add(entry.getKey()+"	"+entry.getValue().name+"		"+pi.id);
			}
		}
		FileHelper.writeFile("data/OMIMDiseases_to_phenotype.txt", writeList);
	}

}
