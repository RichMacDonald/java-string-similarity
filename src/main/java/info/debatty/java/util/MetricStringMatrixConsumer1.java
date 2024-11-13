package info.debatty.java.util;

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

public class MetricStringMatrixConsumer1 implements MetricStringMatrixConsumer{
	double[][] distanceMatrix;
	String[] row;
	String[] col;

	//Choose the best single match for each case.
	public MetricStringMatrixResult1 pickBest() {
		return new MetricStringMatrixResult1(row, col, distanceMatrix);
	}

	@Override
	public void accept(StringDistance metric, String[] row, String[] col, String rowItem, String colItem, int rowIndex, int colIndex, double similarity) {
		ensureMatrix(row, col);
		distanceMatrix[rowIndex][colIndex] = similarity;
	}

	private void ensureMatrix(String[] row, String[] col) {
		if (distanceMatrix == null) {
			this.row = row;
			this.col = col;
			distanceMatrix = new double[row.length][col.length];
		}
	}

	public static class MetricStringMatrixResult1{
		final String[] matchingRows;
		final String[] matchingCols;

		public MetricStringMatrixResult1(String[] row, String[] col, double[][] similarityMatrix) {
			matchingRows = new String[similarityMatrix.length];
			matchingCols = new String[similarityMatrix[0].length];
			double[][] distanceMatrixCopy = new double[row.length][col.length];
			for (int i = 0; i < distanceMatrixCopy.length; i++) {
				for (int j = 0; j < distanceMatrixCopy.length; j++) {
					distanceMatrixCopy[i][j] = similarityMatrix[i][j];
				}
			}
			int[] minRowCol = new int[2];
			double minValue = 0.0;
			while (minValue != Double.MAX_VALUE) {
				minValue = findMin(distanceMatrixCopy, minRowCol);
				int colIndex = minRowCol[1];
				String colMatch = col[colIndex];
				int rowIndex = minRowCol[0];
				matchingRows[rowIndex] = colMatch;
				String rowMatch = row[rowIndex];
				matchingCols[colIndex] = rowMatch;
				clear(distanceMatrixCopy, minRowCol);
//				System.out.println("Match distance=" + maxValue + " i="+rowIndex + " row-value="+rowMatch + " j="+colIndex + " col-value="+colMatch);
				System.out.println("row=" + rowMatch + " col="+colMatch + " dist=" + minValue);

			}
		}

		private void clear(double[][] distanceMatrixCopy, int[] maxRowCol) {
			for (int i = 0; i < matchingRows.length; i++) {
				distanceMatrixCopy[i][maxRowCol[1]] = Double.MAX_VALUE;
			}
			for (int j = 0; j < matchingCols.length; j++) {
				distanceMatrixCopy[maxRowCol[0]][j] = Double.MAX_VALUE;
			}
		}

		private double findMin(double[][] distanceMatrix, int[] minRowCol) {
			double minValue = Double.MAX_VALUE;
			for (int i = 0; i < distanceMatrix.length; i++) {
				for (int j = 0; j < distanceMatrix.length; j++) {
					if (distanceMatrix[i][j] < minValue) {
						minRowCol[0] = i;
						minRowCol[1] = j;
						minValue = distanceMatrix[i][j];
						if (minValue == 0.0) {
							return minValue;
						}
					}
				}
			}
			return minValue;
		}
	}

}
