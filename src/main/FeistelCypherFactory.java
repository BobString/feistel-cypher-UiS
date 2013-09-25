package main;

import main.cyphers.TEA;
import main.cyphers.XTEA;

public class FeistelCypherFactory {

	public static FeistelCypher getFeistelCypher(FeistelCypherType type) {
		FeistelCypher cypher = null;
		switch (type) {
		case TEA:
			cypher = new TEA();
		case XTEA:
			cypher = new XTEA();
			break;
		case XXTEA:
			break;
		}
		return cypher;

	}

}
