package info.debatty.java.util;

import java.util.function.Function;

/**
 * A bridge for more powerful caches, such as Spring caching.
 * Allow the similarity calculators to cache their profiles and speed up many-to-many calculations
 */
public interface MetricStringCache<T> {

	/**
	 * Answer the value matching this key.
	 * If missing, calculate it with the mappingFunction.
	 * Method taken from ConcurrentHashMap, but we do not (yet) guarantee thread-safe
	 */
	T computeIfAbsent(String key, Function<String, T> mappingFunction);

	void clear();
}
