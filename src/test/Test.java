package test;

import main.FeistelCypher;

public class Test {
	/* Simple usage example */
	public static String quote = "OLA K ASE";

	public static void main(String[] args) {
		/* Create a cipher using the first 16 bytes of the passphrase */
		FeistelCypher tea = new FeistelCypher(
				"And is there honey still for tea?");

		/* Run it through the cipher... and back */
		byte[] crypt = tea.encrypt(quote);
		String result = tea.decrypt(crypt);

		/* Ensure that all went well */
		String test = new String(result);
		if (!test.equals(quote)) {
			System.out.println("No son iguales:");
			System.out.println("Output: " + test);
		} else {
			System.out.println("YAY!!!");
		}

	}
}
