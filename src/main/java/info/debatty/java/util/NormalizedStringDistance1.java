package info.debatty.java.util;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Taken from NormalizedLevenshtein, but for any StringDistancwe
 */

public final class NormalizedStringDistance1  implements NormalizedStringDistance, NormalizedStringSimilarity{

	private final StringDistance delegate;

	public NormalizedStringDistance1(StringDistance delegate) {
		this.delegate = delegate;
	}

  public double distance(final String s1, final String s2) {

      if (s1.equals(s2)) {
          return 0;
      }

      int m_len = Math.max(s1.length(), s2.length());

      if (m_len == 0) {
          return 0;
      }

      return delegate.distance(s1, s2) / m_len;
  }

  public double similarity(final String s1, final String s2) {
      return 1.0 - distance(s1, s2);
  }

}
