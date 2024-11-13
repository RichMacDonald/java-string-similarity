package info.debatty.java.util;

import java.util.HashSet;
import java.util.Set;
import org.jspecify.annotations.NonNull;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

public class MetricStringUtils {

	/**
	 * Construct a Set of both arguments and return the set size
	 */
	public static int unionSetCount(String[] arr1, String[] arr2) {
    Set<String> union = new HashSet<String>();
    addTo(arr1, union);
    addTo(arr2, union);
    return union.size();
	}

	private static void addTo(String[] arr, Set<String> union) {
		for (String str : arr) {
			union.add(str);
		}
	}

	public static void processVector(StringDistance metric, String @NonNull [] row, String item, MetricStringVectorConsumer consumer) {
		for (int i = 0; i < row.length; i++) {
			double distance = metric.distance(item, row[i]);
			consumer.accept(metric, row, item, i, distance);
			if (consumer.isCompleted()) {
				return;
			}
		}
	}

	public static void processMatrix(StringDistance metric, String @NonNull [] row, String @NonNull [] col, MetricStringMatrixConsumer consumer) {
		for (int i = 0; i < row.length; i++) {
			String rowItem = row[i];
			if (!consumer.isCompletedRow(row, rowItem, i)) {

				for (int j = 0; j < col.length; j++) {

					//ask twice because we can complete a row while in that row
					if (!consumer.isCompletedRow(row, rowItem, i)) {

						String colItem = col[j];
						if (!consumer.isCompletedCol(col, colItem, j)) {

							double distance = metric.distance(rowItem, colItem);

							consumer.accept(metric, row, col, rowItem, colItem, i, j, distance);
							if (consumer.isCompleted()) {
								return;
							}
						}
					}
				}
			}
		}
	}
}
