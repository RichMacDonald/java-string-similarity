package info.debatty.java.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimpleMetricStringCache<T> implements MetricStringCache<T> {

	private final Map<String,T> map = new HashMap<>();

	@Override
	public T computeIfAbsent(String key, Function<String, T> mappingFunction) {
		T answer = map.get(key);
		if (answer == null) {
			answer = mappingFunction.apply(key);
			map.put(key,  answer);
		}
		return answer;
	}

	@Override
	public void clear() {
		map.clear();
	}

}
