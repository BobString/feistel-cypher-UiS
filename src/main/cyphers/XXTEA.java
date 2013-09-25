package main.cyphers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import main.FeistelCypher;
import main.FeistelCypherType;

public class XXTEA extends FeistelCypher {

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

	public XXTEA() {
		super(FeistelCypherType.XXTEA);
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
		if(intText.length < 2){
			int[] aux = {intText[0],0};
			intText = aux;
		}
		int sum, n, rounds, z, e, y, ind;
		n = 2;
		rounds = 6 + 52 / n;
		z = intText[n - 1];
		// v0 = intText[i];
		// v1 = intText[i + 1];
		sum = 0;
		while (rounds > 0) {
			sum += delta;
			e = (sum >> 2) & 3;
			for (ind = 0; ind < n - 1; ind++) {
				y = intText[ind + 1];
				z = intText[ind] += (((z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)) ^ ((sum ^ y) + (key[(ind & 3)
						^ e] ^ z)));
			}
			y = intText[0];
			z = intText[n - 1] += (((z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)) ^ ((sum ^ y) + (key[(ind & 3)
					^ e] ^ z)));
			rounds--;
		}
		return int2byte(intText);
	}

	public String decrypt(byte[] encondeMessage) {
		int[] intText = byte2int(encondeMessage);
		int sum, n, rounds, z, e, y, ind;
		n = 2;
		rounds = 6 + 52 / n;
		sum = rounds * delta;
		y = intText[0];
		while (sum != 0) {
			e = (sum >> 2) & 3;
			for (ind = n - 1; ind > 0; ind--) {
				z = intText[ind - 1];
				y = intText[ind] -= (((z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)) ^ ((sum ^ y) + (key[(ind & 3)
						^ e] ^ z)));
			}
			z = intText[n - 1];
			y = intText[0] -= (((z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)) ^ ((sum ^ y) + (key[(ind & 3)
					^ e] ^ z)));
			sum -= delta;
		}
		return new String(int2byte(intText));
	}

}
