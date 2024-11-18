package info.debatty.java.util;

public final class StringAndDistance implements Comparable<StringAndDistance> {

	public final String string;
	public final double distance;

	public StringAndDistance(String string, double distance) {
		this.string = string;
		this.distance = distance;
	}
	@Override
	public int compareTo(StringAndDistance o) {
		return Double.compare(distance, o.distance);
	}
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder().append("{");
		buf.append("string=").append(string).append(" dist=").append(distance);
		buf.append("}");
		return buf.toString();
	}
}