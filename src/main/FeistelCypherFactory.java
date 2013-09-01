package main;

public class FeistelCypherFactory {

	public static FeistelCypher getFeistelCypher(FeistelCypherType type) {
		FeistelCypher cypher = null;
		switch (type) {
		case XTEA:
			cypher = new XTEA();
			break;
		case XXTEA:
			break;
		}
		return cypher;

	}

}
