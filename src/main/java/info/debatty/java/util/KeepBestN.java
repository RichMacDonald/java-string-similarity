package info.debatty.java.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class KeepBestN {

	final int keepBestN;
	public String exactMatched;
	public List<StringAndDistance> bestList = new ArrayList<>();

	KeepBestN(int keepBestN) {
		this.keepBestN = keepBestN;
	}

	public boolean isExact() {
		return exactMatched != null;
	}

	// answer true if completed
	public boolean accept(String item, double distance) {
		if (bestList == null) {
			// already exact matched
			return true;
		}
		if (distance == 0.0) {
			exactMatched = item;
			bestList = null;
			return true;
		} else {
			StringAndDistance pair = new StringAndDistance(item, distance);
			accept(pair);
			prune();
			return false;
		}
	}

	private void accept(StringAndDistance pair) {
		bestList.add(pair);
	}

	private void prune() {
		Collections.sort(bestList);
//		MriUtilsCollection.trimEnd(bestList, keepBestN);
		trimEnd(bestList, keepBestN);
	}

	private static void trimEnd(List<?> list, int maxSize) {
		while (list.size() > maxSize) {
			list.remove(list.size()-1);
		}
	}
	@Override
	public @NonNull String toString() {
		if (exactMatched != null) {
			return "exact=" + exactMatched;
		}
		StringBuilder buf = new StringBuilder().append("{");
		int pos = 0;
		for (StringAndDistance keepBestNPair : bestList) {
			buf.append(" str-").append(pos).append("=").append(keepBestNPair.string).append(" dist-" + pos).append("=").append(keepBestNPair.distance);
			pos++;
		}
		buf.append("}");
		return buf.toString();
	}

	public KeepBestN merge(@Nullable KeepBestN other, int keepBestN) {
		if (isExact()) {
			return this;
		}
		if (other.isExact()) {
			return other;
		}
		return merge(keepBestN, bestList, other.bestList);
	}

	private KeepBestN merge(int keepBestN, List<StringAndDistance> bestList1, List<StringAndDistance> bestList2) {
		Map<String, StringAndDistance> map2 = toMap(bestList2);
		List<StringAndDistance> merged = new ArrayList<>();
		for (StringAndDistance pair1 : bestList1) {
			StringAndDistance pair2 = map2.remove(pair1.string);
			double dist = pair1.distance;
			if (pair2 != null) {
				dist = Math.min(pair1.distance, pair2.distance);
			}
			merged.add(new StringAndDistance(pair1.string, dist));
		}
		merged.addAll(map2.values());
		KeepBestN answer = new KeepBestN(keepBestN);
		for (StringAndDistance pair : merged) {
			answer.accept(pair);
		}
		answer.prune();
		return answer;
	}
	private Map<String, StringAndDistance> toMap(List<StringAndDistance> bestList) {
		Map<String, StringAndDistance> map = new HashMap<>();
		for (StringAndDistance keepBestNPair : bestList) {
			map.put(keepBestNPair.string, keepBestNPair);
		}
		return map;
	}

	public KeepBestN mergeCommon(@Nullable KeepBestN other, int keepBestN) {
		if (isExact()) {
			return this;
		}
		if (other.isExact()) {
			return other;
		}
		return mergeCommon(keepBestN, bestList, other.bestList);
	}

	// keep only if they exist in both places
	private KeepBestN mergeCommon(int keepBestN, List<StringAndDistance> bestList1, List<StringAndDistance> bestList2) {
		Map<String, StringAndDistance> map2 = toMap(bestList2);
		List<StringAndDistance> mergedList = new ArrayList<>();
		for (StringAndDistance pair1 : bestList1) {
			StringAndDistance pair2 = map2.remove(pair1.string);
			if (pair2 != null) {
				mergedList.add(new StringAndDistance(pair1.string, Math.min(pair1.distance, pair2.distance)));
			}
		}
		KeepBestN answer = new KeepBestN(keepBestN);
		for (StringAndDistance pair : mergedList) {
			answer.accept(pair);
		}
		answer.prune(); // will sort but not remove any
		return answer;
	}
}