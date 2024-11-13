package info.debatty.java.util;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Handles accepting results.
 * May stop if an exact match is found.
 * May generate the entire matric.
 * May hold onto the top-X
 */
interface MetricStringMatrixConsumer{

	void accept(StringDistance metric,
			String[] row, String[] col,
			String rowItem, String colItem,
			int rowIndex, int colIndex,
			double distance);

	default boolean isCompleted() {
		return false;
	}

	default boolean isCompletedRow(String[] row, String rowItem, int rowIndex) {
		return false;
	}

	default boolean isCompletedCol(String[] col, String colItem, int colIndex) {
		return false;
	}

}