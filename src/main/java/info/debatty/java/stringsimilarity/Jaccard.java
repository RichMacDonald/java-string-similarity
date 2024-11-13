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

import org.jspecify.annotations.Nullable;
import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.util.MetricStringCache;
import info.debatty.java.util.MetricStringUtils;
import net.jcip.annotations.Immutable;

/**
 * Each input string is converted into a set of n-grams, the Jaccard index is
 * then computed as |V1 inter V2| / |V1 union V2|.
 * Like Q-Gram distance, the input strings are first converted into sets of
 * n-grams (sequences of n characters, also called k-shingles), but this time
 * the cardinality of each n-gram is not taken into account.
 * Distance is computed as 1 - cosine similarity.
 * Jaccard index is a metric distance.
 * @author Thibault Debatty
 */
@Immutable
public class Jaccard extends ShingleBased implements
        MetricStringDistance, NormalizedStringDistance,
        NormalizedStringSimilarity {

	//Added for performance when doing large comparisons
	//This cache is optional. The client is also capable of holding onto the profiles
	private final @Nullable MetricStringCache<String[]> cache;

    /**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     *
     * @param k
     */
    public Jaccard(final int k) {
        this(k, null);
    }

    public Jaccard(final int k, @Nullable MetricStringCache<String[]> cache) {
      super(k);
      this.cache = cache;
  }

    /**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     */
    public Jaccard() {
        this(DEFAULT_K);
    }

    /**
     * Compute Jaccard index: |A inter B| / |A union B|.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The Jaccard index in the range [0, 1]
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
		public final double similarity(final String s1, final String s2) {
//        if (s1 == null) {
//            throw new NullPointerException("s1 must not be null");
//        }
//
//        if (s2 == null) {
//            throw new NullPointerException("s2 must not be null");
//        }

			if (s1.equals(s2)) {
		    return 1;
			}

       return _similarity(s1, s2, getProfileKeys(s1), getProfileKeys(s2), false);
    }

		@Override
		public String[] getProfileKeys(String string) {
			if (cache == null) {
				return _getProfileKeys(string);
			}
			return cache.computeIfAbsent(string, this::_getProfileKeys);
		}

		private String[] _getProfileKeys(String string) {
			return super.getProfileKeys(string);
		}

		public double similarity(final String s1, final String s2, String[] profile1, String[] profile2) {
			return _similarity(s1, s2, profile1, profile2, true);
		}

		private double _similarity(final String s1, final String s2, String[] profile1, String[] profile2, boolean checkEquality) {
			if (checkEquality && s1.equals(s2)) {
			    return 1;
			}
			int unionSetCount = MetricStringUtils.unionSetCount(profile1, profile2);
			int inter = profile1.length + profile2.length - unionSetCount;
			return 1.0 * inter / unionSetCount;
		}


    /**
     * Distance is computed as 1 - similarity.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return 1 - the Jaccard similarity.
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
		public final double distance(final String s1, final String s2) {
        return 1.0 - similarity(s1, s2);
    }
}
