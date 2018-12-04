package zebra.crypto;

import java.util.Arrays;

public final class BlowfishCFB extends BlowfishECB {
	byte[] iv = new byte[8];
	int ivBytesLeft;

	public byte[] getIV() {
		return this.iv;
	}

	public void getIV(byte[] dest, int ofs) {
		System.arraycopy(this.iv, 0, dest, ofs, this.iv.length);
	}

	public void setIV(byte[] newIV, int ofs) {
		System.arraycopy(newIV, ofs, this.iv, 0, this.iv.length);
		this.ivBytesLeft = 0;
	}

	public BlowfishCFB(byte[] key, int ofs, int len) {
		super(key, ofs, len);
	}

	public BlowfishCFB(byte[] key, int ofs, int len, byte[] initIV, int ivOfs) {
		super(key, ofs, len);
		setIV(initIV, ivOfs);
	}

	public void cleanUp() {
		Arrays.fill(this.iv, (byte) 0);
		super.cleanUp();
	}

	@SuppressWarnings("unused")
	public int encrypt(byte[] inbuf, int inpos, byte[] outbuf, int outpos, int len) {
		int end = inpos + len;

		byte[] iv = this.iv;

		int ivBytesLeft = this.ivBytesLeft;
		int ivpos = iv.length - ivBytesLeft;

		if (ivBytesLeft >= len) {
			for (; inpos < end; ivpos++) {
				byte tmp53_52 = ((byte) (inbuf[inpos] ^ iv[ivpos]));
				outbuf[outpos] = tmp53_52;
				iv[ivpos] = tmp53_52;

				inpos++;
				outpos++;
			}

			this.ivBytesLeft = (iv.length - ivpos);
			return len;
		}
		for (int i = 0; ivpos < 8; ivpos++) {
			byte tmp107_106 = ((byte) (inbuf[inpos] ^ iv[ivpos]));
			outbuf[outpos] = tmp107_106;
			iv[ivpos] = tmp107_106;

			i++;
			inpos++;
			outpos++;
		}

		len -= ivBytesLeft;

		int[] sbox1 = this.sbox1;
		int[] sbox2 = this.sbox2;
		int[] sbox3 = this.sbox3;
		int[] sbox4 = this.sbox4;

		int[] pbox = this.pbox;
		int pbox00 = pbox[0];
		int pbox01 = pbox[1];
		int pbox02 = pbox[2];
		int pbox03 = pbox[3];
		int pbox04 = pbox[4];
		int pbox05 = pbox[5];
		int pbox06 = pbox[6];
		int pbox07 = pbox[7];
		int pbox08 = pbox[8];
		int pbox09 = pbox[9];
		int pbox10 = pbox[10];
		int pbox11 = pbox[11];
		int pbox12 = pbox[12];
		int pbox13 = pbox[13];
		int pbox14 = pbox[14];
		int pbox15 = pbox[15];
		int pbox16 = pbox[16];
		int pbox17 = pbox[17];

		int hi = iv[0] << 24 | iv[1] << 16 & 0xFF0000 | iv[2] << 8 & 0xFF00 | iv[3] & 0xFF;

		int lo = iv[4] << 24 | iv[5] << 16 & 0xFF0000 | iv[6] << 8 & 0xFF00 | iv[7] & 0xFF;

		int rest = len % 8;
		end -= rest;
		while (true) {
			hi ^= pbox00;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox01;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox02;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox03;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox04;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox05;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox06;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox07;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox08;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox09;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox10;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox11;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox12;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox13;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox14;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox15;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox16;

			int swap = lo ^ pbox17;
			lo = hi;
			hi = swap;

			if (inpos >= end) {
				break;
			}

			hi = hi ^ (inbuf[inpos] << 24 | inbuf[(inpos + 1)] << 16 & 0xFF0000 | inbuf[(inpos + 2)] << 8 & 0xFF00 | inbuf[(inpos + 3)] & 0xFF);

			lo = lo ^ (inbuf[(inpos + 4)] << 24 | inbuf[(inpos + 5)] << 16 & 0xFF0000 | inbuf[(inpos + 6)] << 8 & 0xFF00 | inbuf[(inpos + 7)] & 0xFF);

			inpos += 8;

			outbuf[outpos] = ((byte) (hi >>> 24));
			outbuf[(outpos + 1)] = ((byte) (hi >>> 16));
			outbuf[(outpos + 2)] = ((byte) (hi >>> 8));
			outbuf[(outpos + 3)] = ((byte) hi);

			outbuf[(outpos + 4)] = ((byte) (lo >>> 24));
			outbuf[(outpos + 5)] = ((byte) (lo >>> 16));
			outbuf[(outpos + 6)] = ((byte) (lo >>> 8));
			outbuf[(outpos + 7)] = ((byte) lo);

			outpos += 8;
		}

		iv[0] = ((byte) (hi >>> 24));
		iv[1] = ((byte) (hi >>> 16));
		iv[2] = ((byte) (hi >>> 8));
		iv[3] = ((byte) hi);
		iv[4] = ((byte) (lo >>> 24));
		iv[5] = ((byte) (lo >>> 16));
		iv[6] = ((byte) (lo >>> 8));
		iv[7] = ((byte) lo);

		for (int i = 0; i < rest; i++) {
			byte tmp1543_1542 = ((byte) (inbuf[(inpos + i)] ^ iv[i]));
			outbuf[(outpos + i)] = tmp1543_1542;
			iv[i] = tmp1543_1542;
		}

		this.ivBytesLeft = (iv.length - rest);

		return len;
	}

	public int decrypt(byte[] inbuf, int inpos, byte[] outbuf, int outpos, int len) {
		int end = inpos + len;

		byte[] iv = this.iv;

		int ivBytesLeft = this.ivBytesLeft;
		int ivpos = iv.length - ivBytesLeft;

		if (ivBytesLeft >= len) {
			for (; inpos < end; ivpos++) {
				int b = inbuf[inpos];
				outbuf[outpos] = ((byte) (b ^ iv[ivpos]));
				inbuf[inpos] = ((byte) b);

				inpos++;
				outpos++;
			}

			this.ivBytesLeft = (iv.length - ivpos);
			return len;
		}
		for (int i = 0; ivpos < 8; inpos++) {
			byte tmp117_116 = ((byte) (inbuf[(inpos + i)] ^ iv[ivpos]));
			outbuf[(outpos + i)] = tmp117_116;
			iv[ivpos] = tmp117_116;

			i++;
		}

		len -= ivBytesLeft;

		int[] sbox1 = this.sbox1;
		int[] sbox2 = this.sbox2;
		int[] sbox3 = this.sbox3;
		int[] sbox4 = this.sbox4;

		int[] pbox = this.pbox;
		int pbox00 = pbox[0];
		int pbox01 = pbox[1];
		int pbox02 = pbox[2];
		int pbox03 = pbox[3];
		int pbox04 = pbox[4];
		int pbox05 = pbox[5];
		int pbox06 = pbox[6];
		int pbox07 = pbox[7];
		int pbox08 = pbox[8];
		int pbox09 = pbox[9];
		int pbox10 = pbox[10];
		int pbox11 = pbox[11];
		int pbox12 = pbox[12];
		int pbox13 = pbox[13];
		int pbox14 = pbox[14];
		int pbox15 = pbox[15];
		int pbox16 = pbox[16];
		int pbox17 = pbox[17];

		int hi = iv[0] << 24 | iv[1] << 16 & 0xFF0000 | iv[2] << 8 & 0xFF00 | iv[3] & 0xFF;

		int lo = iv[4] << 24 | iv[5] << 16 & 0xFF0000 | iv[6] << 8 & 0xFF00 | iv[7] & 0xFF;

		int rest = len % 8;
		end -= rest;
		while (true) {
			hi ^= pbox00;

			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox01;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox02;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox03;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox04;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox05;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox06;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox07;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox08;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox09;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox10;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox11;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox12;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox13;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox14;
			lo ^= (sbox1[(hi >>> 24)] + sbox2[(hi >>> 16 & 0xFF)] ^ sbox3[(hi >>> 8 & 0xFF)]) + sbox4[(hi & 0xFF)] ^ pbox15;
			hi ^= (sbox1[(lo >>> 24)] + sbox2[(lo >>> 16 & 0xFF)] ^ sbox3[(lo >>> 8 & 0xFF)]) + sbox4[(lo & 0xFF)] ^ pbox16;

			int swap = lo ^ pbox17;
			lo = hi;
			hi = swap;

			if (inpos >= end) {
				break;
			}

			int chi = inbuf[inpos] << 24 | inbuf[(inpos + 1)] << 16 & 0xFF0000 | inbuf[(inpos + 2)] << 8 & 0xFF00 | inbuf[(inpos + 3)] & 0xFF;

			int clo = inbuf[(inpos + 4)] << 24 | inbuf[(inpos + 5)] << 16 & 0xFF0000 | inbuf[(inpos + 6)] << 8 & 0xFF00 | inbuf[(inpos + 7)] & 0xFF;

			inpos += 8;

			hi ^= chi;
			lo ^= clo;

			outbuf[outpos] = ((byte) (hi >>> 24));
			outbuf[(outpos + 1)] = ((byte) (hi >>> 16));
			outbuf[(outpos + 2)] = ((byte) (hi >>> 8));
			outbuf[(outpos + 3)] = ((byte) hi);

			outbuf[(outpos + 4)] = ((byte) (lo >>> 24));
			outbuf[(outpos + 5)] = ((byte) (lo >>> 16));
			outbuf[(outpos + 6)] = ((byte) (lo >>> 8));
			outbuf[(outpos + 7)] = ((byte) lo);

			outpos += 8;

			hi = chi;
			lo = clo;
		}

		iv[0] = ((byte) (hi >>> 24));
		iv[1] = ((byte) (hi >>> 16));
		iv[2] = ((byte) (hi >>> 8));
		iv[3] = ((byte) hi);
		iv[4] = ((byte) (lo >>> 24));
		iv[5] = ((byte) (lo >>> 16));
		iv[6] = ((byte) (lo >>> 8));
		iv[7] = ((byte) lo);

		for (int i = 0; i < rest; i++) {
			int b = inbuf[(inpos + i)];
			outbuf[(outpos + i)] = ((byte) (b ^ iv[i]));
			iv[i] = ((byte) b);
		}

		this.ivBytesLeft = (iv.length - rest);

		return len;
	}
}