/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.debatty.java.stringsimilarity;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import info.debatty.java.util.MetricStringCache;
import net.jcip.annotations.Immutable;

/**
 * Shingle based where the code uses the keys of the Map, not the values.
 * Optional caching of the keys
 *
 * @author rjm
 */
@Immutable
public abstract class ShingleKeyBasedA extends ShingleBasedA {

	// Added for performance when doing large comparisons
	// This cache is optional. The client is also capable of holding onto the profiles
	private final @Nullable MetricStringCache<String[]> cache;

	public ShingleKeyBasedA(final int k) {
		this(k, null);
	}

	public ShingleKeyBasedA(final int k, @Nullable MetricStringCache<String[]> cache) {
		super(k);
		this.cache = cache;
	}

	@Override
	protected final Map<String, Integer> getProfile(String string) {
		throw new RuntimeException("Subclasses should not call this method. Use getProfileKeys");
	}

	protected final String[] getProfileKeys(String string) {
		if (cache == null) {
			return _getProfileKeys(string);
		}
		return cache.computeIfAbsent(string, this::_getProfileKeys);
	}


	private String[] _getProfileKeys(String string) {
		Map<String, Integer> shingles = super.getProfile(string);
		String[] keys = new String[shingles.size()];
		int pos = 0;
		for (String key : shingles.keySet()) {
			keys[pos++] = key;
		}
		return keys;
	}
}
