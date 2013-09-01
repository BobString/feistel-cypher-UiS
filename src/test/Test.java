package test;

import main.FeistelCypher;
import main.FeistelCypherFactory;
import main.FeistelCypherType;

public class Test {
	/* Simple usage example */
	public static String quote = "OLA K ASE";

	public static void main(String[] args) {
		
		/* XTEA */
		FeistelCypher xtea = FeistelCypherFactory
				.getFeistelCypher(FeistelCypherType.XTEA);
		xtea.setKey("And is there honey still for tea?");

		/* Run it through the cipher and back */
		byte[] crypt = xtea.encrypt(quote);
		String result = xtea.decrypt(crypt);

		/* Lets see that all went well */
		String test = new String(result);
		if (!test.equals(quote)) {
			System.out.println("No son iguales:");
			System.out.println("Output: " + test);
		} else {
			System.out.println("YAY!!!");
		}

	}
}
