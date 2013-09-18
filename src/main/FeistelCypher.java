package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public abstract class FeistelCypher {

	private FeistelCypherType type = null;

	public FeistelCypher(FeistelCypherType type) {
		this.setType(type);

	}

	public FeistelCypherType getType() {
		return type;
	}

	public void setType(FeistelCypherType type) {
		this.type = type;
	}

	protected int[] byte2int(byte[] source) {
		int rest = source.length % 4;
		byte[] fixArray;
		if (rest != 0) {
			fixArray = new byte[source.length + (4 - rest)];
			byte b1 = " ".getBytes()[0];
			int fixLength = fixArray.length;
			int sourceLength = source.length;
			for (int i = 0; i < fixLength; i++) {
				if (i < sourceLength) {
					fixArray[i] = source[i];
				} else {
					fixArray[i] = b1;
				}
			}

		} else {
			fixArray = source;
		}
		IntBuffer intBuf = ByteBuffer.wrap(fixArray)
				.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
		int[] array = new int[intBuf.remaining()];
		intBuf.get(array);
		return array;
	}

	protected byte[] int2byte(int[] source) {

		ByteBuffer byteBuffer = ByteBuffer.allocate(source.length * 4);
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(source);
		return byteBuffer.array();
	}

	public abstract byte[] encrypt(String plainText);

	public abstract String decrypt(byte[] cryptText);

	public abstract void setKey(String key);

}
