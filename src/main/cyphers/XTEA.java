package main.cyphers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import main.FeistelCypher;
import main.FeistelCypherType;

/**
 * Feistel cypher based XTEA
 * 
 * @author roberto
 * 
 */
public class XTEA extends FeistelCypher {
	/**
	 * 32 cycles = 64 feistel rounds
	 */
	private int numCycles = 32;

	/**
	 * 128 bits key, 4 integers
	 */
	private int[] key = new int[4];
	/**
	 * Used in each round
	 */
	private int delta = 0x9E3779B9;

	public XTEA() {
		super(FeistelCypherType.XTEA);
	}

	/**
	 * Set the key
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		byte[] keybyte = key.getBytes();
		if (keybyte == null) {
			throw new RuntimeException("No key");
		} else if (keybyte.length < 16) {
			byte[] newkey = new byte[16];
			for (int i = 0; i < 16; i++) {
				newkey[i] = keybyte[i % keybyte.length];
			}
			keybyte = newkey;
		}

		// Change bytes for ints
		IntBuffer intBuf = ByteBuffer.wrap(keybyte).order(ByteOrder.BIG_ENDIAN)
				.asIntBuffer();
		int[] array = new int[intBuf.remaining()];
		intBuf.get(array);
		for (int i = 0; i < 4; i++) {
			this.key[i] = array[i];
		}

	}

	public byte[] encrypt(String plainText) {
		byte[] text = plainText.getBytes();
		int[] intText = byte2int(text);

		int i, v0, v1, sum, n;
		i = 0;
		while (i < intText.length - 1) {
			n = numCycles;
			v0 = intText[i];
			v1 = intText[i + 1];
			sum = 0;
			for (int ind = 0; ind < n; ind++) {
				v0 += (((v1 << 4) ^ (v1 >> 5)) + v1) ^ (sum + key[sum & 3]);
				sum += delta;
				v1 += (((v0 << 4) ^ (v0 >> 5)) + v0)
						^ (sum + key[(sum >> 11) & 3]);
			}
			intText[i] = v0;
			intText[i + 1] = v1;
			i += 2;
		}
		return int2byte(intText);
	}

	public String decrypt(byte[] encondeMessage) {
		int[] intText = byte2int(encondeMessage);

		int i, v0, v1, sum, n;
		i = 0;
		while (i < intText.length - 1) {
			n = numCycles;
			v0 = intText[i];
			v1 = intText[i + 1];
			sum = delta * numCycles;
			for (int ind = 0; ind < n; ind++) {
				v1 -= (((v0 << 4) ^ (v0 >> 5)) + v0)
						^ (sum + key[(sum >> 11) & 3]);
				sum -= delta;
				v0 -= (((v1 << 4) ^ (v1 >> 5)) + v1) ^ (sum + key[sum & 3]);
			}
			intText[i] = v0;
			intText[i + 1] = v1;
			i += 2;
		}
		return new String(int2byte(intText));
	}

}
