package info.debatty.java.util;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Typical options:
 * If we find an exact match, stop.
 * Keep the best N matches.
 */

public class MetricStringVectorConsumer2 implements MetricStringVectorConsumer {

	public KeepBestN keepBestN;

	public MetricStringVectorConsumer2(int keepBestN) {
		this.keepBestN = new KeepBestN( keepBestN);
	}

	@Override
	public void accept(StringDistance metric, String[] row, String item, int rowPosition, double distance) {
		keepBestN.accept(item, distance);
	}

	@Override
	public boolean isCompleted() {
		return keepBestN.isExact();
	}

}
