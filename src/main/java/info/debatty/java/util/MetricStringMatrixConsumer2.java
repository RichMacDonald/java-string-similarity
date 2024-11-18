package info.debatty.java.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Typical options:
 * If we find an exact match, stop that row and column.
 * Keep the best N matches for the other rows and columns.
 *
 * Impl note: We may have one pair where it is the 2nd best match on the row but the 5th best on the col. What to do?
 * We can't just keep track of the best rows and the best cols. This would be storage N + M.
 * We have to keep track of N x M values and only select at the end.
 */

public class MetricStringMatrixConsumer2 implements MetricStringMatrixConsumer {

	final int keepBestN;
	Map<String, KeepBestN> rowMap;
	Map<String, KeepBestN> colMap;
	Set<Integer> completedRows = new HashSet<>();
	Set<Integer> completedCols = new HashSet<>();

	public MetricStringMatrixConsumer2(int keepBestN) {
		this.keepBestN = keepBestN;
	}

	@Override
	public boolean isCompletedRow(String[] row, String rowItem, int rowIndex) {
		return completedRows.contains(rowIndex);
	}
	@Override
	public boolean isCompletedCol(String[] col, String colItem, int colIndex) {
		return completedCols.contains(colIndex);
	}
	@Override
		public boolean isCompleted() {
			return completedRows.size() == rowMap.size() || completedCols.size() == colMap.size();
		}

	@Override
	public void accept(StringDistance metric, String[] rows, String[] cols, String rowItem, String colItem, int rowIndex, int colIndex, double distance) {
		firstTime(rows, cols);
		if (rowMap.get(rowItem).accept(colItem, distance)) {
			completedRows.add(rowIndex);
		}
		if (colMap.get(colItem).accept(rowItem, distance)) {
			completedCols.add(colIndex);
		}
	}

	private void firstTime(String[] row, String[] col) {
		if (rowMap != null) {
			return;
		}
		rowMap = new HashMap<>();
		for (String string : row) {
			rowMap.put(string, new KeepBestN(keepBestN));
		}
		colMap = new HashMap<>();
		for (String string : col) {
			colMap.put(string, new KeepBestN(keepBestN));
		}
	}

	// -----------------------------------------------------------------------------------

	public MetricStringMatrixResult2 pickBest() {
		return new MetricStringMatrixResult2(keepBestN, rowMap, colMap);
	}

	// -----------------------------------------------------------------------------------

	public static class MetricStringMatrixResult2 {

		final int keepBestN;
		Map<String, KeepBestN> rowMap;
		Map<String, KeepBestN> colMap;

		public MetricStringMatrixResult2(int keepBestN, Map<String, KeepBestN> rowMap, Map<String, KeepBestN> colMap) {
			this.keepBestN = keepBestN;
			this.rowMap = rowMap;
			this.colMap = colMap;
		}

		public void printSummary() {
			for (Entry<String, KeepBestN> entry : rowMap.entrySet()) {
				String rowStr = entry.getKey();
				KeepBestN best = entry.getValue();
				System.out.println("row=" + rowStr + " best=" + best.toString());
			}
			for (Entry<String, KeepBestN> entry : colMap.entrySet()) {
				String rowStr = entry.getKey();
				KeepBestN best = entry.getValue();
				System.out.println("col=" + rowStr + " best=" + best.toString());
			}
		}
		private MetricStringMatrixResult2 _merge(MetricStringMatrixResult2 other, int keepBestN2) {
			Map<String, KeepBestN> rowMapMerge = new HashMap<String, KeepBestN>();
			Map<String, KeepBestN> colMapMerge = new HashMap<String, KeepBestN>();

			for (Entry<String, KeepBestN> entry : rowMap.entrySet()) {
				KeepBestN merged = entry.getValue().merge(other.rowMap.get(entry.getKey()), keepBestN2);
				rowMapMerge.put(entry.getKey(), merged);
			}

			for (Entry<String, KeepBestN> entry : colMap.entrySet()) {
				KeepBestN merged = entry.getValue().merge(other.colMap.get(entry.getKey()), keepBestN2);
				colMapMerge.put(entry.getKey(), merged);
			}

			return new MetricStringMatrixResult2(keepBestN2, rowMapMerge, colMapMerge);
		}

		/**
		 * Argument calculated by a different metric. Take the best of this and that.
		 * Only works if both metrics were normalized and have comparable values.
		 * Which they probably don't
		 * Takes the union, which means: values found in either.
		 * Does not drop any.
		 */
		public MetricStringMatrixResult2 mergeUnion(MetricStringMatrixResult2 other) {
			final int keepBestN2 = keepBestN + other.keepBestN;
			return _merge(other, keepBestN2);
		}

		/**
		 * Argument calculated by a different metric. Take the values that show up in both
		 * Is sorted. But sorting may not make much sense because we're comparing two different distances.
		 */
		public MetricStringMatrixResult2 mergeCommon(MetricStringMatrixResult2 other) {
			final int keepBestN2 = keepBestN + other.keepBestN; // so nothing will prune

			Map<String, KeepBestN> rowMapMerge = new HashMap<String, KeepBestN>();
			Map<String, KeepBestN> colMapMerge = new HashMap<String, KeepBestN>();

			for (Entry<String, KeepBestN> entry : rowMap.entrySet()) {
				KeepBestN merged = entry.getValue().mergeCommon(other.rowMap.get(entry.getKey()), keepBestN2);
				rowMapMerge.put(entry.getKey(), merged);
			}

			for (Entry<String, KeepBestN> entry : colMap.entrySet()) {
				KeepBestN merged = entry.getValue().mergeCommon(other.colMap.get(entry.getKey()), keepBestN2);
				colMapMerge.put(entry.getKey(), merged);
			}

			return new MetricStringMatrixResult2(keepBestN2, rowMapMerge, colMapMerge);

		}
	}
}
