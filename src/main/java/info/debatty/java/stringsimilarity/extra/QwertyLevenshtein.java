package info.debatty.java.stringsimilarity.extra;

import info.debatty.java.stringsimilarity.WeightedLevenshtein;
import info.debatty.java.util.QwertyCharacterSubstitutionWeighting;

public final class QwertyLevenshtein extends NormalizedStringDistance1{

	public QwertyLevenshtein() {
		super(new WeightedLevenshtein(QwertyCharacterSubstitutionWeighting.INSTANCE));
	}
}
