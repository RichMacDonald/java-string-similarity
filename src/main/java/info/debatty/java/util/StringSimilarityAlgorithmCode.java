package info.debatty.java.util;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.MetricLCS;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.OptimalStringAlignment;
import info.debatty.java.stringsimilarity.QGram;
import info.debatty.java.stringsimilarity.RatcliffObershelp;
import info.debatty.java.stringsimilarity.SorensenDice;
import info.debatty.java.stringsimilarity.experimental.Sift4;
import info.debatty.java.stringsimilarity.extra.QwertyLevenshtein;
import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

public enum StringSimilarityAlgorithmCode {
	LongestCommonSubsequence(LongestCommonSubsequence.class),
	OptimalStringAlignment(OptimalStringAlignment.class),
	QGram(QGram.class),
	Sift4(Sift4.class),
	Damerau(Damerau.class),
	Jaccard(Jaccard.class),
	Levenshtein(Levenshtein.class),
	MetricLCS(MetricLCS.class),
	Cosine(Cosine.class),
	JaroWinkler(JaroWinkler.class),
	NGram(NGram.class),
	NormalizedLevenshtein(NormalizedLevenshtein.class),
	QwertyLevenshtein(QwertyLevenshtein.class),
	RatcliffObershelp(RatcliffObershelp.class),
	SorensenDice(SorensenDice.class)
	;

	StringSimilarityAlgorithmCode(Class<? extends StringDistance> klass) {
		this.klass = klass;
	}

	public final Class<? extends StringDistance> klass;

	public boolean isMetric() {
		return MetricStringDistance.class.isAssignableFrom(klass);
	}
	public boolean isNormalized() {
		return NormalizedStringDistance.class.isAssignableFrom(klass);
	}

	public StringDistance newMetric() {
		try {
			return klass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
