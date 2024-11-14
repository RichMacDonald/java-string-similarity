package info.debatty.java.util;

import info.debatty.java.stringsimilarity.CharacterSubstitutionInterface;

//https://kth.diva-portal.org/smash/get/diva2:1116701/FULLTEXT01.pdf

public class QwertyCharacterSubstitutionWeighting implements CharacterSubstitutionInterface{

	public static final QwertyCharacterSubstitutionWeighting INSTANCE = new QwertyCharacterSubstitutionWeighting();

	//Memory hog: This is a 16,384 array
	//Could do a lookup, but that will be slow

	private static final double[][] weights;
	private static int weightCount;
	static {
		int size = 128;
		weights = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				weights[i][j] = 1.0;
			}
		}

		addWeight('q', 'w', 'a');
		addWeight('w', 'e', 's', 'a', 'q');
		addWeight('e', 'r', 'd', 's', 'w');
		addWeight('r', 't', 'f', 'd', 'e');
		addWeight('t', 'y', 'g', 'f', 'r');
		addWeight('y', 'u', 'h', 'g', 't');
		addWeight('u', 'i', 'j', 'h', 'y');
		addWeight('i', 'o', 'k', 'j', 'u');
		addWeight('o', 'p', 'l', 'k', 'i');
		addWeight('p', 'l', 'o');
		addWeight('a', 'q', 'w', 's', 'z');
		addWeight('s', 'w', 'e', 'd', 'x', 'z', 'a');
		addWeight('d', 'e', 'r', 'f', 'c', 'x', 's');
		addWeight('f', 'r', 't', 'g', 'v', 'c', 'd');
		addWeight('g', 't', 'y', 'h', 'b', 'v', 'f');
		addWeight('h', 'y', 'u', 'j', 'n', 'b', 'g');
		addWeight('j', 'u', 'i', 'k', 'm', 'n', 'h');
		addWeight('k', 'i', 'o', 'l', 'm', 'j');
		addWeight('l', 'o', 'p', 'k');
		addWeight('z', 'a', 's', 'x');
		addWeight('x', 's', 'd', 'c', 'z');
		addWeight('c', 'd', 'f', 'v', 'x');
		addWeight('v', 'f', 'g', 'b', 'c');
		addWeight('b', 'g', 'h', 'n', 'v');
		addWeight('n', 'h', 'j', 'm', 'b');
		addWeight('m', 'j', 'k', 'n');

		//   Weighted 440 out of 16,384 for 2 percent
//		System.out.println("Weighted "+weightCount + " out of 16,384 for " + (weightCount*100/16384) + " percent");
	}
	private static void addWeight(char base, char ... neighbors) {
		for (char neighbor : neighbors) {
			addWeight2(base, neighbor);
		}
	}
	private static void addWeight2(char base, char neighbor) {
		char BASE = Character.toUpperCase(base);
		char NEIGHBOR = Character.toUpperCase(neighbor);

		weights[base][neighbor] = 0.7;
		weights[neighbor][base] = 0.7;
		weights[BASE][NEIGHBOR] = 0.7;
		weights[NEIGHBOR][BASE] = 0.7;

		weights[base][NEIGHBOR] = 0.6;
		weights[NEIGHBOR][base] = 0.6;
		weights[BASE][neighbor] = 0.6;
		weights[neighbor][BASE] = 0.6;

		weightCount += 4;
	}

	@Override
	public double cost(char c1, char c2) {
		if (c1 < 128 && c2 < 128) {
			return weights[c1][c2];
		}
		return 1.0;
	}
}


//from typing import Callable, Union
//import numpy as np
//from weighted_levenshtein import dam_lev, lev
//
//# Source for NEIGHBORS_OF: A. Samuellson Master's Thesis
//# https://kth.diva-portal.org/smash/get/diva2:1116701/FULLTEXT01.pdf
//NEIGHBORS_OF = {}
//NEIGHBORS_OF["q"] = ["w", "a"]
//NEIGHBORS_OF["w"] = ["e", "s", "a", "q"]
//NEIGHBORS_OF["e"] = ["r", "d", "s", "w"]
//NEIGHBORS_OF["r"] = ["t", "f", "d", "e"]
//NEIGHBORS_OF["t"] = ["y", "g", "f", "r"]
//NEIGHBORS_OF["y"] = ["u", "h", "g", "t"]
//NEIGHBORS_OF["u"] = ["i", "j", "h", "y"]
//NEIGHBORS_OF["i"] = ["o", "k", "j", "u"]
//NEIGHBORS_OF["o"] = ["p", "l", "k", "i"]
//NEIGHBORS_OF["p"] = ["l", "o"]
//NEIGHBORS_OF["a"] = ["q", "w", "s", "z"]
//NEIGHBORS_OF["s"] = ["w", "e", "d", "x", "z", "a"]
//NEIGHBORS_OF["d"] = ["e", "r", "f", "c", "x", "s"]
//NEIGHBORS_OF["f"] = ["r", "t", "g", "v", "c", "d"]
//NEIGHBORS_OF["g"] = ["t", "y", "h", "b", "v", "f"]
//NEIGHBORS_OF["h"] = ["y", "u", "j", "n", "b", "g"]
//NEIGHBORS_OF["j"] = ["u", "i", "k", "m", "n", "h"]
//NEIGHBORS_OF["k"] = ["i", "o", "l", "m", "j"]
//NEIGHBORS_OF["l"] = ["o", "p", "k"]
//NEIGHBORS_OF["z"] = ["a", "s", "x"]
//NEIGHBORS_OF["x"] = ["s", "d", "c", "z"]
//NEIGHBORS_OF["c"] = ["d", "f", "v", "x"]
//NEIGHBORS_OF["v"] = ["f", "g", "b", "c"]
//NEIGHBORS_OF["b"] = ["g", "h", "n", "v"]
//NEIGHBORS_OF["n"] = ["h", "j", "m", "b"]
//NEIGHBORS_OF["m"] = ["j", "k", "n"]
//
//QWERTY_COSTS = np.ones((128, 128), dtype=np.float64)
//
//for ch in NEIGHBORS_OF:
//    QWERTY_COSTS[ord(ch), ord(ch.upper())] = 0.6
//    QWERTY_COSTS[ord(ch.upper()), ord(ch)] = 0.6
//    for neighbor in NEIGHBORS_OF[ch]:
//        QWERTY_COSTS[ord(ch), ord(neighbor)] = 0.7
//        QWERTY_COSTS[ord(neighbor), ord(ch)] = 0.7
//
//        QWERTY_COSTS[ord(ch.upper()), ord(neighbor.upper())] = 0.7
//        QWERTY_COSTS[ord(neighbor.upper()), ord(ch.upper())] = 0.7
//
//
//def _base_distance_similarity(
//    distance_fn: Callable, text_1: str, text_2: str, similarity: bool
//) -> Union[int, float]:
//    if text_1 == text_2:
//        return 1.0 if similarity else 0.0
//
//    text_1 = text_1.encode("ascii", errors="ignore").decode()
//    text_2 = text_2.encode("ascii", errors="ignore").decode()
//
//    distance = distance_fn(text_1, text_2, substitute_costs=QWERTY_COSTS)
//    if not similarity:
//        return distance
//    len1, len2 = len(text_1), len(text_2)
//
//    max_distance = min(len1, len2) + (max(len1, len2) - min(len1, len2))
//    similarity = (max_distance - distance) / max_distance
//    return similarity
//
//
//def qwerty_weighted_levenshtein(
//    text_1: str, text_2: str, similarity: bool = False
//) -> Union[int, float]:
//    return _base_distance_similarity(lev, text_1, text_2, similarity)
//
//
//def qwerty_weighted_damerau_levenshtein(
//    text_1: str, text_2: str, similarity: bool = False
//) -> Union[int, float]:
//    return _base_distance_similarity(dam_lev, text_1, text_2, similarity)
