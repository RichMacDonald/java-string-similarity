package info.debatty.java.util;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Handles accepting results.
 * May stop if an exact match is found.
 * May generate the entire row.
 * May hold onto the top-X
 */
interface MetricStringVectorConsumer{

	void accept(StringDistance metric, String[] row, String item, int rowPosition, double distance);

	boolean isCompleted();

}