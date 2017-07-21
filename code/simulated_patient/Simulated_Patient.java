package org.hit.hanson.disease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hit.hanson.helper.FileHelper;
import org.hit.hanson.jung.Methods;
import org.hit.hanson.jung.PatientInfo;
import org.hit.hanson.jung.Vertex;

public class Simulated_Patient {
	public static int sum = 0;
	public static int patientNum = 0;
	public static HashMap<String, Collection<Vertex>> genePhenotype = new HashMap<String, Collection<Vertex>>();
	public static HashMap<String, Collection<Vertex>> diseasePhenotype = new HashMap<String, Collection<Vertex>>();

	//getAnnotation
	public static HashMap<String, Collection<Vertex>> getAnnotation(HashMap<String, Collection<Vertex>> annotation,
			String sourceFile) {
		LinkedList<String> list = FileHelper.readFile(sourceFile);
		Iterator<String> iterator = list.iterator();
		String line = iterator.next();
		String[] temp;
		Vertex vertex;

		while (iterator.hasNext()) {
			line = iterator.next();
			temp = line.split("	");
			if (Methods.altID.containsKey(temp[3])) {
				vertex = Methods.vertexID.get(temp[3]);
			} else if (Methods.altID.containsKey(temp[3])) {
				vertex = Methods.vertexID.get(Methods.altID.get(temp[3]));
			} else {
				vertex = new Vertex();
				System.out.println("-------------------------");
			}
			if (annotation.containsKey(temp[1])) {
				annotation.get(temp[1]).add(vertex);
			} else {
				Collection<Vertex> phenotype = new HashSet<Vertex>();
				phenotype.add(vertex);
				annotation.put(temp[1], phenotype);
			}
		}
		return annotation;
	}

	//reGetAnnotation
	public static void reGetAnnotation(HashMap<String, Collection<Vertex>> annotation) {
		Collection<Vertex> tempSet = new HashSet<Vertex>();
		for (Iterator<String> iterator = annotation.keySet().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			for (Iterator<Vertex> iterator2 = annotation.get(type).iterator(); iterator2.hasNext();) {
				Vertex vertex = (Vertex) iterator2.next();
				if (vertex.id.equals("")) {
					System.out.println(-1);
					continue;
				}
				tempSet.addAll(Methods.HPO.getAncestor(vertex));
			}
			annotation.get(type).addAll(tempSet);
			tempSet.clear();
		}
	}

	//get_Simulated_Patient
	public static void get_Simulated_Patient(String file) {
		LinkedList<String> list = FileHelper.readFile(file);
		LinkedList<String> tempList = new LinkedList<String>();
		Collection<DiseaseInfo> diseaseSet = new HashSet<DiseaseInfo>();
		DiseaseInfo disease = new DiseaseInfo();
		PhenotypeInfo phenotype = new PhenotypeInfo();
		Iterator<String> iterator = list.iterator();
		iterator.next();
		iterator.next();

		for (; iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.contains("OMIM:")) {
				diseaseSet.add(disease);
				disease = new DiseaseInfo();
			}
			if (string.contains("causative	gene:")) {
				disease.causativeGene = string.split(":")[1].trim();
			}
			if (string.contains("HP:")) {
				phenotype.id = string.split("	")[0].trim();
				phenotype.gender = string.split("	")[1].trim();
				phenotype.penetrance = Double.valueOf(string.split("	")[2].trim());
				//phenotype.penetrance = 0.0;
				disease.phenotypeSet.add(phenotype);
				phenotype = new PhenotypeInfo();
			}
		}
		diseaseSet.add(disease);
		int diseaseId = 0;
		Random random = new Random();
		boolean flag;
		for (Iterator<DiseaseInfo> iterator1 = diseaseSet.iterator(); iterator1.hasNext(); diseaseId++) {
			DiseaseInfo diseaseInfo = (DiseaseInfo) iterator1.next();
			for (int i = 0; i < 100; i++) {
				tempList.add("patientID:	" + String.valueOf(diseaseId) + "_" + i);
				tempList.add("causativeGene:	" + diseaseInfo.causativeGene);
				flag = true;
				if (random.nextBoolean()) {
					tempList.add("patientGender:	M");
					while (flag) {
						for (Iterator<PhenotypeInfo> iterator2 = diseaseInfo.phenotypeSet.iterator(); iterator2
								.hasNext();) {
							PhenotypeInfo pi = (PhenotypeInfo) iterator2.next();
							if (random.nextDouble() < pi.penetrance && !pi.gender.contains("F")) {
								tempList.add(pi.id);
								flag = false;
							}
						}
					}
				} else {
					tempList.add("patientGender:	F");
					while (flag) {
						for (Iterator<PhenotypeInfo> iterator2 = diseaseInfo.phenotypeSet.iterator(); iterator2
								.hasNext();) {
							PhenotypeInfo pi = (PhenotypeInfo) iterator2.next();
							if (random.nextDouble() < pi.penetrance && !pi.gender.contains("M")) {
								tempList.add(pi.id);
								flag = false;
							}
						}
					}
				}
			}
		}
		FileHelper.writeFile("result/train/simulated_patient_train_optimal.txt", tempList);
	}

	// get_Simulated_Patient_Noise
	public static void get_Simulated_Patient_Noise(String sourceFile, String destFile) {
		List<Vertex> set = new ArrayList<Vertex>();
		set.addAll(Methods.vertexID.values());
		Random random = new Random();
		LinkedList<String> list = FileHelper.readFile(sourceFile);
		LinkedList<String> tempList = new LinkedList<String>();
		Collection<String> phenotypeSet = new HashSet<String>();
		int index;

		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.contains("patientID:")) {
				for (Iterator<String> iterator2 = phenotypeSet.iterator(); iterator2.hasNext();) {
					String temp = (String) iterator2.next();
					tempList.add(temp);
				}
				for (int i = 0; i < phenotypeSet.size() / 2; i++) {
					index = random.nextInt(set.size());
					while (phenotypeSet.contains(set.get(index).id) || set.get(index).id.equals("HP:0000001")) {
						index = random.nextInt(set.size());
					}
					tempList.add(set.get(index).id);
				}
				tempList.add(string);
				phenotypeSet = new HashSet<String>();
			}
			if (string.contains("causativeGene:")) {
				tempList.add(string);
			}
			if (string.contains("patientGneder:")) {
				tempList.add(string);
			}
			if (string.contains("HP:")) {
				phenotypeSet.add(string);
			}
		}
		for (Iterator<String> iterator2 = phenotypeSet.iterator(); iterator2.hasNext();) {
			String temp = (String) iterator2.next();
			tempList.add(temp);
		}
		for (int i = 0; i < phenotypeSet.size() / 2; i++) {
			index = random.nextInt(set.size());
			while (phenotypeSet.contains(set.get(index).id) || set.get(index).id.equals("HP:0000001")) {
				index = random.nextInt(set.size());
			}
			tempList.add(set.get(index).id);
		}
		FileHelper.writeFile(destFile, tempList);
	}

	// patient_imprecision
	public static void getImprecision(String sourceFile, String destFile) {
//		getAnnotation(diseasePhenotype, "data/OMIMDiseases_to_phenotype.txt");
		getAnnotation(diseasePhenotype,"data/genes_to_phenotype.txt");
		Collection<PatientInfo> patients = Methods.getPatient(sourceFile);
		Collection<Vertex> phenotypeSet = new HashSet<Vertex>();
		LinkedList<Vertex> allowImpreSet = new LinkedList<Vertex>();
		Random random = new Random();
		LinkedList<String> imprecision = new LinkedList<String>();
		Vertex v = null;

		for (Iterator<PatientInfo> iterator = patients.iterator(); iterator.hasNext();) {
			PatientInfo patientInfo = (PatientInfo) iterator.next();
			phenotypeSet.clear();
			imprecision.add("patientID:	" + patientInfo.id);
			imprecision.add("causativeGene:	" + patientInfo.causativeGene);
			imprecision.add("patientGender:	");
			
			int totalNum = patientInfo.phenotypeSet.size();
			int tempNum = 0;
			for (Vertex vertex : patientInfo.phenotypeSet) {
				allowImpreSet.clear();
				allowImpreSet.addAll(Methods.HPO.getAncestor(vertex));
				allowImpreSet.remove(Methods.vertexID.get("HP:0000001"));
				allowImpreSet.removeAll(diseasePhenotype.get(patientInfo.causativeGene));
				allowImpreSet.remove(phenotypeSet);
				if (allowImpreSet.size() == 0)
					System.out.println(v.id);
				if(tempNum < totalNum/2){
				v = allowImpreSet.get(random.nextInt(allowImpreSet.size()));
				imprecision.add(v.id);
				phenotypeSet.add(v);
				tempNum = tempNum + 1;
				}else{
					imprecision.add(vertex.id);
				}
			}
		}
		FileHelper.writeFile(destFile, imprecision);
	}

	// patient_imprnoise
	public static void getImprnoise(String sourceFile, String destFile) {
//		getAnnotation(diseasePhenotype, "data/OMIMDiseases_to_phenotype.txt");
		getAnnotation(diseasePhenotype,"data/genes_to_phenotype.txt");
		reGetAnnotation(diseasePhenotype);
		Collection<PatientInfo> patients = Methods.getPatient(sourceFile);
		LinkedList<Vertex> allowNoiseSet = new LinkedList<Vertex>();
		Random random = new Random();
		LinkedList<String> noise = new LinkedList<String>();
		for (Iterator<PatientInfo> iterator = patients.iterator(); iterator.hasNext();) {
			PatientInfo patientInfo = (PatientInfo) iterator.next();
			noise.add("patientID:	" + patientInfo.id);
			noise.add("causativeGene:	" + patientInfo.causativeGene);
			noise.add("patientGender:	");
			for (Vertex vertex : patientInfo.phenotypeSet) {
				noise.add(vertex.id);
			}
			allowNoiseSet.clear();
			allowNoiseSet.addAll(Methods.vertexID.values());
			allowNoiseSet.remove(Methods.vertexID.get("HP:0000001"));
			if (!patientInfo.causativeGene.equals("null"))
				System.out.println(allowNoiseSet.removeAll(diseasePhenotype.get(patientInfo.causativeGene)));
			for (int i = 0; i < patientInfo.phenotypeSet.size() / 2; i++)
				noise.add(allowNoiseSet.get(random.nextInt(allowNoiseSet.size())).id);
		}
		FileHelper.writeFile(destFile, noise);
	}

	public static void main(String[] args) {
		System.out.println("----------------------");
		//产生模拟预测疾病
//		getImprnoise("result/simulated_patient_OMIM_imprecision.txt", "result/simulated_patient_OMIM_imprnoise.txt");
//		getImprecision("result/simulated_patient_OMIM_optimal.txt", "result/simulated_patient_OMIM_imprecision.txt");
//		get_Simulated_Patient_Noise("result/simulated_patient_OMIM_optimal.txt", "result/simulated_patient_OMIM_noise.txt");

		//产生模拟预测基因
//		get_Simulated_Patient("data/simulated_disease.txt");
//		get_Simulated_Patient_Noise("result/train/simulated_patient_train_optimal.txt","result/train/simulated_patient_train_noise.txt");
//		getImprecision("result/simulated_patient_optimal.txt","result/simulated_patient_imprecision_half.txt");
		getImprnoise("result/simulated_patient_imprecision_half.txt","result/simulated_patient_imprnoise_half.txt");
		
		System.out.println("The Program is end...");
	}
}
