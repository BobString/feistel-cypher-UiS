package main.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import main.FeistelCypher;
import main.FeistelCypherFactory;
import main.FeistelCypherType;

public class UI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/* Cypher typer */

		System.out.println("Please enter number of cypher:");
		System.out.println("  1.TEA");
		System.out.println("  2.XTEA");
		System.out.println("  3.XXTEA");

		String alg = null;
		try {
			alg = (new BufferedReader(new InputStreamReader(System.in)))
					.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (alg == null) {
			alg = "1";
		}

		FeistelCypher feistel = null;

		switch (alg) {
		case "1":
			feistel = FeistelCypherFactory
					.getFeistelCypher(FeistelCypherType.TEA);
			break;
		case "2":
			feistel = FeistelCypherFactory
					.getFeistelCypher(FeistelCypherType.XTEA);
			break;
		case "3":
			feistel = FeistelCypherFactory
					.getFeistelCypher(FeistelCypherType.XXTEA);
			break;
		default:
			feistel = FeistelCypherFactory
			.getFeistelCypher(FeistelCypherType.XTEA);
			break;
			
		}

		/* Key */
		System.out.println("Please enter a key:");
		String key = null;
		try {
			key = (new BufferedReader(new InputStreamReader(System.in)))
					.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (key == null) {
			key = "Don't put the key null";
			System.exit(0);
		}
		feistel.setKey(key);

		/* ENCRYPTION */
		System.out.println("Enter text to encrypt:");
		String text = null;
		while (text == null) {
			try {
				text = (new BufferedReader(new InputStreamReader(System.in)))
						.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		byte[] cypherText = feistel.encrypt(text);

		System.out.println("Text encrypted: " + Arrays.toString(cypherText));

		/* DECRYPTION */

		System.out.println("Press enter to decrypt");
		try {
			(new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Text decrypted: " + feistel.decrypt(cypherText));

	}
}
