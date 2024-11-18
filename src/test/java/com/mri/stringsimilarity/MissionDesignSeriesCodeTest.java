package com.mri.stringsimilarity;

import org.junit.Test;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import info.debatty.java.util.KeepBestN;
import info.debatty.java.util.MetricStringUtils;
import info.debatty.java.util.MetricStringVectorConsumer2;
import info.debatty.java.util.StringAndDistance;
import info.debatty.java.util.StringSimilarityAlgorithmCode;

/**
 * TLDR: String similarity cannot be used to lookup aircraft names reliably
 */

public class MissionDesignSeriesCodeTest {

	private static final String[] UdlMissionDesignSeriesCode_Name = new String[] {
			"A-10A",
			"A-10C",
			"Airbus 300",
			"Airbus 300-600",
			"Airbus 318",
			"Airbus 319",
			"Airbus 320",
			"Airbus 320-100",
			"Airbus 320-200",
			"Airbus 321",
			"Airbus 321-100",
			"Airbus 321-200",
			"Airbus 330",
			"Airbus 330-200",
			"Airbus 340",
			"Airbus A340-300",
			"Airbus A340-500",
			"Airbus A340-600",
			"Airbus 3501K",
			"Airbus 380",
			"Airbus 400M",
			"AC-130H",
			"AC-130J",
			"AC-130U",
			"AC-130W",
			"AC-208B",
			"AH-1W",
			"AH-1Z",
			"B-1B",
			"B-52H",
			"B-707M",
			"B-717",
			"Boeing 727",
			"Boeing 727-200",
			"Boeing 737",
			"Boeing 737-100",
			"Boeing 737-200",
			"Boeing 737-300",
			"Boeing 737-400",
			"Boeing 737-500",
			"Boeing 737-700",
			"Boeing 737-800",
			"Boeing 737 MAX 8",
			"Boeing 737-900",
			"Boeing 737 MAX 9",
			"Boeing 747",
			"Boeing 747-300",
			"Boeing 747-400",
			"Boeing 747-400E",
			"Boeing 747-400F",
			"Boeing 747-800",
			"Boeing 747-800F",
			"Boeing 757",
			"Boeing 757-200",
			"Boeing 757-300",
			"Boeing 767",
			"Boeing 767-300",
			"Boeing 767-300F",
			"Boeing 777",
			"Boeing 777-200",
			"Boeing 777-300",
			"Boeing 777-300ER",
			"Boeing 787",
			"C-2A",
			"C-5",
			"C-5A",
			"C-5B",
			"C-5M",
			"C-9A",
			"C-9B",
			"C-09C",
			"C-12F",
			"C-12J",
			"C-17A",
			"C-20A",
			"C-20B",
			"C-20C",
			"C-20D",
			"C-20E",
			"C-20F",
			"C-20G",
			"C-20H",
			"C-21",
			"C-21A",
			"C-32",
			"C-32A",
			"C-32B",
			"C-37",
			"C-37A",
			"C-37B",
			"C-40",
			"C-40B",
			"C-40C",
			"C-130",
			"C-130B",
			"C-130E",
			"C-130H",
			"C-130H30",
			"C-130J",
			"C-130J30",
			"C-130K",
			"C-130T",
			"C-137B",
			"C-143B",
			"C-145A",
			"C-146A",
			"C-160D",
			"C-160E",
			"C-160F",
			"C-160T",
			"C-208B",
			"CC-150",
			"CC-22B",
			"CH-124",
			"DC-006",
			"DC-10-30",
			"E-3C",
			"E-4B",
			"EA-6B",
			"F-16A",
			"F-16B",
			"F-16C",
			"F-16D",
			"F-18E",
			"F-18F",
			"F-22A",
			"F-35A",
			"F-35B",
			"F-35C",
			"Gulfstream IV",
			"HC-130J",
			"HC-130N",
			"KC-10A",
			"KC-46",
			"KC-46A",
			"KC-130J",
			"KC-135",
			"KC-135E",
			"KC-135R",
			"KC-135T",
			"LC-130H",
			"LR-036",
			"MC-130J",
			"MC-130P",
			"MD-011F",
			"MH-60S",
			"MH-60T",
			"MQ-8B",
			"MQ-9A",
			"MQ-9B",
			"Not Specified",
			"P-3",
			"P-3C",
			"RQ-7B",
			"T-6",
			"T-6A",
			"T-34C",
			"T-38C",
			"TGR4A",
			"UH-1N",
			"WC-130J"
	};

	private static final String[] name_udlname = new String[] {
			"A-10A","A10A",
			"A-10C","A10C",
			"Airbus 300","A300",
			"Airbus 300-600","A30060",
			"Airbus 318","A318",
			"Airbus 319","A319",
			"Airbus 320","A320",
			"Airbus 320-100","A32010",
			"Airbus 320-200","A32020",
			"Airbus 321","A321",
			"Airbus 321-100","A32110",
			"Airbus 321-200","A32120",
			"Airbus 330","A330",
			"Airbus 330-200","A33020",
			"Airbus 340","A340",
			"Airbus A340-300","A34030",
			"Airbus A340-500","A34050",
			"Airbus A340-600","A34060",
			"Airbus 3501K","A3501K",
			"Airbus 380","A380",
			"Airbus 400M","A400M",
			"AC-130H","AC130H",
			"AC-130J","AC130J",
			"AC-130U","AC130U",
			"AC-130W","AC130W",
			"AC-208B","AC208B",
			"AH-1W","AH1W",
			"AH-1Z","AH1Z",
			"B-1B","B1B",
			"B-52H","B52H",
			"B-707M","B707M",
			"B-717","B717",
			"Boeing 727","B727",
			"Boeing 727-200","B72720",
			"Boeing 737","B737",
			"Boeing 737-100","B73710",
			"Boeing 737-200","B73720",
			"Boeing 737-300","B73730",
			"Boeing 737-400","B73740",
			"Boeing 737-500","B73750",
			"Boeing 737-700","B73770",
			"Boeing 737-800","B73780",
			"Boeing 737 MAX 8","B7378M",
			"Boeing 737-900","B73790",
			"Boeing 737 MAX 9","B7379M",
			"Boeing 747","B747",
			"Boeing 747-300","B74730",
			"Boeing 747-400","B74740",
			"Boeing 747-400E","B7474F",
			"Boeing 747-400F","B7474F",
			"Boeing 747-800","B74780",
			"Boeing 747-800F","B7478F",
			"Boeing 757","B757",
			"Boeing 757-200","B75720",
			"Boeing 757-300","B75730",
			"Boeing 767","B767",
			"Boeing 767-300","B76730",
			"Boeing 767-300F","B7673F",
			"Boeing 777","B777",
			"Boeing 777-200","B77720",
			"Boeing 777-300","B77730",
			"Boeing 777-300ER","B7773E",
			"Boeing 787","B787",
			"C-2A","C2A",
			"C-5","C5",
			"C-5A","C5A",
			"C-5B","C5B",
			"C-5M","C5M",
			"C-9A","C9A",
			"C-9B","C9B",
			"C-09C","C9C",
			"C-12F","C12F",
			"C-12J","C12J",
			"C-17A","C17A",
			"C-20A","C20A",
			"C-20B","C20B",
			"C-20C","C20C",
			"C-20D","C20D",
			"C-20E","C20E",
			"C-20F","C20F",
			"C-20G","C20G",
			"C-20H","C20H",
			"C-21","C21",
			"C-21A","C21A",
			"C-32","C32",
			"C-32A","C32A",
			"C-32B","C32B",
			"C-37","C37",
			"C-37A","C37A",
			"C-37B","C37B",
			"C-40","C40",
			"C-40B","C40B",
			"C-40C","C40C",
			"C-130","C130",
			"C-130B","C130B",
			"C-130E","C130E",
			"C-130H","C130H",
			"C-130H30","C130H30",
			"C-130J","C130J",
			"C-130J30","C130J30",
			"C-130K","C130K",
			"C-130T","C130T",
			"C-137B","C137B",
			"C-143B","C142B",
			"C-145A","C145A",
			"C-146A","C146A",
			"C-160D","C160D",
			"C-160E","C160E",
			"C-160F","C160F",
			"C-160T","C160T",
			"C-208B","C208B",
			"CC-150","CC150",
			"CC-22B","CC22B",
			"CH-124","CH124",
			"DC-006","DC006",
			"DC-10-30","DC103",
			"E-3C","E3C",
			"E-4B","E4B",
			"EA-6B","EA6B",
			"F-16A","F16A",
			"F-16B","F16B",
			"F-16C","F16C",
			"F-16D","F16D",
			"F-18E","FA18E",
			"F-18F","FA18F",
			"F-22A","F22A",
			"F-35A","F35A",
			"F-35B","F35B",
			"F-35C","F35C",
			"Gulfstream IV","GIV",
			"HC-130J","HC130J",
			"HC-130N","HC130N",
			"KC-10A","KC10A",
			"KC-46","KC46",
			"KC-46A","KC46A",
			"KC-130J","KC130J",
			"KC-135","KC135",
			"KC-135E","KC135E",
			"KC-135R","KC135R",
			"KC-135T","KC135T",
			"LC-130H","LC130H",
			"LR-036","LR036",
			"MC-130J","MC130J",
			"MC-130P","MC130P",
			"MD-011F","MD011F",
			"MH-60S","MH60S",
			"MH-60T","MH60T",
			"MQ-8B","MQ8B",
			"MQ-9A","MQ9A",
			"MQ-9B","MQ9B",
			"Not Specified","",
			"P-3","P3",
			"P-3C","P3C",
			"RQ-7B","RQ7B",
			"T-6","T6",
			"T-6A","T6A",
			"T-34C","T34C",
			"T-38C","T38C",
			"TGR4A","TGR4A",
			"UH-1N","UH1N",
			"WC-130J","WC130J"
	};

	/**
	 * Which metric has the best approx match on a value close but not exact.
	 * Answer: JaroWinkler
	 */
  @Test
	public void test() {
  	test_Run("B52", "B-52H");
  	test_Run("B73730", "Boeing 737-300");
  	test_Run("B73740", "Boeing 737-400");
  	test_Run("B7378M", "Boeing 737 MAX 8");
	}

  @Test
	public void test_udl() {
  	for (int i = 0; i < name_udlname.length; i+=2) {
			String name = name_udlname[i];
			String udlname = name_udlname[i+1];
			test_Run(udlname, name);
		}
	}


	private void test_Run(String matchItem, String bestItem) {
  		StringDistance bestMetric = null;
  		double bestSelectivity = 0.0;

		for (StringSimilarityAlgorithmCode code : StringSimilarityAlgorithmCode.values()) {
			StringDistance metric = code.newMetric();
			MetricStringVectorConsumer2 cons = new MetricStringVectorConsumer2(2);
			MetricStringUtils.processVector(metric, UdlMissionDesignSeriesCode_Name, matchItem, cons);
			KeepBestN keepBestN = cons.keepBestN;
			if (keepBestN.isExact()) {
				boolean correct = keepBestN.exactMatched.equals(bestItem);
				System.out.println();
				if (correct) {
					System.out.println("matchexact="+matchItem + " best="+metric.getClass().getSimpleName() + " correct="+correct);
				} else {
					System.err.println("matchexact="+matchItem + " best="+metric.getClass().getSimpleName() + " correct="+correct);
				}
				System.out.println();
				return;
			}
			StringAndDistance first = keepBestN.bestList.get(0);
			StringAndDistance second = keepBestN.bestList.get(1);
			double selectivity = second.distance / first.distance;
			boolean correct = first.string.equals(bestItem);
			System.out.println("metric="+metric.getClass().getSimpleName() + " correct="+correct + " 1st="+first + " 2nd="+second + " selectivity="+selectivity);

			if (correct) {
				if (bestMetric == null) {
					bestMetric = metric;
				} else if (selectivity > bestSelectivity) {
					bestMetric = metric;
					bestSelectivity = selectivity;
				}
			}

		}
		System.out.println();
		System.out.println("match="+matchItem + " best="+bestMetric.getClass().getSimpleName() + " selectivity="+bestSelectivity);
		System.out.println();
	}
}
