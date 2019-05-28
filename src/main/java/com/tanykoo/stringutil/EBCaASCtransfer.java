package com.tanykoo.stringutil;

public class EBCaASCtransfer {
	private static byte SPACE = 32;
	private static int SNDLEN = 8192;
	private static byte[] T_ISPWD;
	public static byte EBC_CHINESE_FLAG_START_byte;
	public static byte EBC_CHINESE_FLAG_STOP_byte;
	public static byte EBC_PARTITION_AREA_FLAG_byte;
	public static byte ASC_CHINESE_FLAG_byte;
	public static byte ASC_PARTITION_AREA_FLAG_byte;
	private static byte[][] IBMtoGBTable;
	private static byte[][] IBMtoGBKTable;

	public EBCaASCtransfer() {
	}

	private static boolean EBC_CHINESE_FLAG_START(byte x) throws Exception {
		return x == EBC_CHINESE_FLAG_START_byte;
	}

	private static boolean EBC_CHINESE_FLAG_STOP(byte x) throws Exception {
		return x == EBC_CHINESE_FLAG_STOP_byte;
	}

	private static boolean EBC_PARTITION_AREA_FLAG(byte x) throws Exception {
		return x >= EBC_PARTITION_AREA_FLAG_byte;
	}

	private static boolean ASC_CHINESE_FLAG(byte x) throws Exception {
		return x >= ASC_CHINESE_FLAG_byte;
	}

	private static boolean ASC_PARTITION_AREA_FLAG(byte x) throws Exception {
		return x >= ASC_PARTITION_AREA_FLAG_byte;
	}

	public static int getEBDLen(String str) {
		try {
			byte[] ascByte = str.getBytes();
			return pub_base_ASCtoEBC(ascByte, ascByte.length).length;
		} catch (Exception var2) {
			return 0;
		}
	}

	public static byte[] pub_base_EBCtoASC(byte[] in_EBC_str, int EBC_str_len) throws Exception {
		byte[] out_ASC_str = new byte[EBC_str_len * 4];
		int out_ASC_str_position = 0;
		byte[] temp = new byte[2];
		int EBC_PRC_FLAG = 0;

		int temp0_int;
		int temp1_int;
		for(int in_EBC_str_position = 0; in_EBC_str_position < EBC_str_len; ++in_EBC_str_position) {
			byte EBC_byte_value = in_EBC_str[in_EBC_str_position];
			int EBC_byte_value_int = EBC_byte_value & 255;
			if (!EBC_CHINESE_FLAG_START(EBC_byte_value)) {
				out_ASC_str[out_ASC_str_position++] = SB_EBCDICtoASCII(EBC_byte_value_int);
			} else {
				++in_EBC_str_position;
				EBC_byte_value = in_EBC_str[in_EBC_str_position];

				for(EBC_byte_value_int = EBC_byte_value & 255; in_EBC_str_position < EBC_str_len && !EBC_CHINESE_FLAG_STOP(EBC_byte_value); EBC_byte_value_int = EBC_byte_value & 255) {
					temp[0] = EBC_byte_value;
					++in_EBC_str_position;
					temp[1] = in_EBC_str[in_EBC_str_position];
					temp0_int = temp[0] & 255;
					temp1_int = temp[1] & 255;
					if (temp0_int >= 64 && temp0_int <= 70 && temp1_int >= 64 && temp1_int <= 253) {
						EBC_PRC_FLAG = 1;
					}

					if (temp0_int == 72 && temp1_int >= 160 && temp1_int < 253 || temp0_int > 72 && temp0_int < 108 && temp1_int >= 64 && temp1_int <= 253 || temp0_int == 108 && temp1_int >= 64 && temp1_int <= 159) {
						EBC_PRC_FLAG = 2;
					}

					if (temp0_int >= 129 && temp0_int < 161 && temp1_int >= 64 && temp1_int <= 253 || temp0_int == 161 && temp1_int >= 64 && temp1_int <= 129) {
						EBC_PRC_FLAG = 3;
					}

					if (temp0_int == 161 && temp1_int >= 130 && temp1_int <= 253 || temp0_int > 161 && temp0_int < 204 && temp1_int >= 64 && temp1_int <= 253 || temp0_int == 204 && temp1_int >= 64 && temp1_int <= 103) {
						EBC_PRC_FLAG = 4;
					}

					if (temp0_int == 206 && temp1_int >= 64 && temp1_int < 166) {
						EBC_PRC_FLAG = 5;
					}

					if (temp0_int == 205 && temp1_int >= 64 && temp1_int <= 213) {
						EBC_PRC_FLAG = 6;
					}

					switch(EBC_PRC_FLAG) {
						case 1:
							temp = DB_EBCDICtoASCII_01(temp);
							if (temp[0] == 0 && temp[1] == 0) {
								temp[0] = EBC_byte_value;
								temp[1] = in_EBC_str[in_EBC_str_position];
								temp = DB_EBCDICtoASCII_06(temp);
							}
							break;
						case 2:
							temp = DB_EBCDICtoASCII_02(temp);
							break;
						case 3:
							temp = DB_EBCDICtoASCII_03(temp);
							break;
						case 4:
							temp = DB_EBCDICtoASCII_04(temp);
							break;
						case 5:
							temp = DB_EBCDICtoASCII_05(temp);
							break;
						case 6:
							temp = DB_EBCDICtoASCII_06(temp);
					}

					out_ASC_str[out_ASC_str_position++] = temp[0];
					out_ASC_str[out_ASC_str_position++] = temp[1];
					++in_EBC_str_position;
					EBC_byte_value = in_EBC_str[in_EBC_str_position];
				}

				out_ASC_str[out_ASC_str_position++] = 32;
				out_ASC_str[out_ASC_str_position++] = 32;
			}
		}

		temp0_int = 0;

		for(temp1_int = 0; temp1_int < out_ASC_str.length; ++temp1_int) {
			if (out_ASC_str[temp1_int] != 0) {
				++temp0_int;
			}
		}

		byte[] out = new byte[temp0_int];
		System.arraycopy(out_ASC_str, 0, out, 0, temp0_int);
		return out;
	}

	public static byte[] pub_base_ASCtoEBC(byte[] in_ASC_str, int ASC_str_len) throws Exception {
		byte[] out_EBC_str = new byte[ASC_str_len * 4];
		int num_of_space_for_chinese = 0;
		int out_EBC_str_position = 0;
		byte[] temp = new byte[2];
		int ASC_PRC_FLAG = 0;

		int temp0_int;
		int temp1_int;
		for(int in_ASC_str_position = 0; in_ASC_str_position < ASC_str_len; ++in_ASC_str_position) {
			byte ASC_byte_value = in_ASC_str[in_ASC_str_position];
			int ASC_byte_value_int = ASC_byte_value & 255;
			if (ASC_byte_value_int < 129) {
				if (ASC_byte_value == SPACE && num_of_space_for_chinese != 0) {
					--num_of_space_for_chinese;
				}

				out_EBC_str[out_EBC_str_position++] = SB_ASCIItoEBCDIC(ASC_byte_value_int);
			} else {
				out_EBC_str[out_EBC_str_position++] = EBC_CHINESE_FLAG_START_byte;

				while(in_ASC_str_position < ASC_str_len && ASC_byte_value_int >= 129) {
					temp[0] = ASC_byte_value;
					++in_ASC_str_position;
					temp[1] = in_ASC_str[in_ASC_str_position];
					temp0_int = temp[0] & 255;
					temp1_int = temp[1] & 255;
					if (temp0_int >= 161 && temp0_int <= 169 && temp1_int >= 160 && temp1_int <= 254) {
						ASC_PRC_FLAG = 1;
					}

					if (temp0_int >= 176 && temp0_int <= 247 && temp1_int >= 160 && temp1_int <= 254) {
						ASC_PRC_FLAG = 2;
					}

					if (temp0_int >= 129 && temp0_int <= 160 && temp1_int >= 64 && temp1_int <= 254) {
						ASC_PRC_FLAG = 3;
					}

					if (temp0_int >= 170 && temp0_int <= 252 && temp1_int >= 64 && temp1_int <= 160 || temp0_int == 253 && temp1_int >= 64 && temp1_int < 156) {
						ASC_PRC_FLAG = 4;
					}

					if (temp0_int == 253 && temp1_int >= 156 && temp1_int <= 160 || temp0_int == 254 && temp1_int >= 64 && temp1_int <= 160) {
						ASC_PRC_FLAG = 5;
					}

					if (temp0_int >= 168 && temp0_int <= 169 && temp1_int >= 64 && temp1_int <= 150) {
						ASC_PRC_FLAG = 6;
					}

					switch(ASC_PRC_FLAG) {
						case 1:
							temp = DB_ASCIItoEBCDIC_01(temp);
							break;
						case 2:
							temp = DB_ASCIItoEBCDIC_02(temp);
							break;
						case 3:
							temp = DB_ASCIItoEBCDIC_03(temp);
							break;
						case 4:
							temp = DB_ASCIItoEBCDIC_04(temp);
							break;
						case 5:
							temp = DB_ASCIItoEBCDIC_05(temp);
							break;
						case 6:
							temp = DB_ASCIItoEBCDIC_06(temp);
					}

					out_EBC_str[out_EBC_str_position++] = temp[0];
					out_EBC_str[out_EBC_str_position++] = temp[1];
					++in_ASC_str_position;
					if (in_ASC_str_position < ASC_str_len) {
						ASC_byte_value = in_ASC_str[in_ASC_str_position];
						ASC_byte_value_int = ASC_byte_value & 255;
					}
				}

				out_EBC_str[out_EBC_str_position++] = EBC_CHINESE_FLAG_STOP_byte;
				--in_ASC_str_position;
				num_of_space_for_chinese += 2;
			}
		}

		temp0_int = 0;

		for(temp1_int = 0; temp1_int < out_EBC_str.length; ++temp1_int) {
			if (out_EBC_str[temp1_int] != 0) {
				++temp0_int;
			}
		}

		byte[] out = new byte[temp0_int];
		System.arraycopy(out_EBC_str, 0, out, 0, temp0_int);
		return out;
	}

	public static String rmSpace(String str) {
		if (str != null && !"".equals(str)) {
			int in_ASC_str_position = 0;
			byte[] in_ASC_str = str.getBytes();
			int ASC_str_len = in_ASC_str.length;
			byte[] temp = new byte[ASC_str_len];
			int tempPos = 0;
			boolean end = true;


			for(in_ASC_str_position = 1; in_ASC_str_position < ASC_str_len; ++in_ASC_str_position) {
				byte pre_byte_value = in_ASC_str[in_ASC_str_position - 1];
				int pre_byte_value_int = pre_byte_value & 255;
				byte curr_byte_value = in_ASC_str[in_ASC_str_position];
				int curr_byte_value_int = curr_byte_value & 255;
				if (pre_byte_value_int >= 129 && curr_byte_value_int < 129) {
					temp[tempPos++] = in_ASC_str[in_ASC_str_position - 1];
					if (in_ASC_str_position == ASC_str_len - 2) {
						end = false;
					}

					in_ASC_str_position += 2;
				} else {
					temp[tempPos++] = in_ASC_str[in_ASC_str_position - 1];
				}
			}

			if (end) {
				temp[tempPos++] = in_ASC_str[in_ASC_str_position - 1];
			}

			byte[] result = new byte[tempPos];
			System.arraycopy(temp, 0, result, 0, tempPos);
			return new String(result);
		} else {
			return str;
		}
	}

	public static byte SB_ASCIItoEBCDIC(int in_ASC_byte_value_int) throws Exception {
		byte[] ASCIItoEBCDICTable = new byte[]{0, 16, 32, 48, 112, -128, -112, 1, 17, 33, 49, 65, 81, 113, -79, -31, 2, 18, 34, 50, 91, 82, 98, 114, -78, 3, 19, 35, 51, 67, 83, 99, 64, 90, 127, 123, -32, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111, 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42, -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, -70, -78, -69, -80, 109, 121, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106, -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, -64, 79, -48, -96, 115, 74, 79, -77, 4, 20, 36, 52, 68, 84, 100, 116, -76, 5, 21, 37, 53, 69, 85, 101, 117, -75, 6, 22, 38, 54, 70, 86, 102, 118, -74, 7, 23, 39, 55, 71, 87, 103, 119, -73, 8, 24, 40, 56, 72, 88, 104, 120, -72, 9, 25, 41, 57, 73, 89, 105, -71, 10, 26, 42, 58, -118, -102, -86, -70, -54, -38, -22, -6, 11, 27, 43, 59, -117, -101, -85, -69, -53, -37, -21, -5, 12, 28, 44, 60, -116, -100, -84, -68, -52, -36, -20, -4, 13, 29, 45, 61, -115, -99, -83, -67, -51, -35, -19, -3, 14, 30, 46, 62, -114, -98, -82, -66, -50, -34, -18, -2, 15, 31, 47, 63, -113, -97, -81, -65, -49, -33, -17, -1};
		return ASCIItoEBCDICTable[in_ASC_byte_value_int];
	}

	public static byte SB_EBCDICtoASCII(int in_EBC_byte_value_int) throws Exception {
		byte[] EBCDICtoASCIITable = new byte[]{0, 7, 16, 25, -125, -116, -107, -98, -89, -80, -72, -60, -48, -36, -24, -12, 1, 8, 17, 26, -124, -115, -106, -97, -88, -79, -71, -59, -47, -35, -23, -11, 2, 9, 18, 27, -123, -114, -105, -96, -87, -78, -70, -58, -46, -34, -22, -10, 3, 10, 19, 28, -122, -113, -104, -95, -86, -77, -69, -57, -45, -33, -21, -9, 32, 11, 20, 29, -121, -112, -103, -94, -85, -76, -128, 46, 60, 40, 43, 124, 38, 12, 21, 30, -120, -111, -102, -93, -84, -75, 33, 20, 42, 41, 59, 94, 45, 47, 22, 31, -119, -110, -101, -92, -83, -74, 124, 44, 37, 95, 62, 63, 4, 13, 23, 127, -118, -109, -100, -91, -82, 96, 58, 35, 64, 39, 61, 34, 5, 97, 98, 99, 100, 101, 102, 103, 104, 105, -68, -56, -44, -32, -20, -8, 6, 106, 107, 108, 109, 110, 111, 112, 113, 114, -67, -55, -43, -31, -19, -7, 126, 126, 115, 116, 117, 118, 119, 120, 121, 122, -66, -54, -42, -30, -18, -6, 94, 14, 92, -126, -117, -108, -99, -90, -81, -73, 91, 93, -41, -29, -17, -5, 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, -64, -52, -40, -28, -16, -4, 125, 74, 75, 76, 77, 78, 79, 80, 81, 82, -63, -51, -39, -27, -15, -3, 36, 15, 83, 84, 85, 86, 87, 88, 89, 90, -62, -50, -38, -26, -14, -2, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -61, -49, -37, -25, -13, -1};
		return EBCDICtoASCIITable[in_EBC_byte_value_int];
	}

	public static byte[] DB_ASCIItoEBCDIC_01(byte[] asc_byte) throws Exception {
		byte[] temp = new byte[2];
		byte m_index = (byte)(asc_byte[0] - 161);
		byte s_index = (byte)(asc_byte[1] - 161);
		temp[0] = IBMtoGBTable[m_index & 255][(s_index & 255) * 2];
		temp[1] = IBMtoGBTable[m_index & 255][(s_index & 255) * 2 + 1];
		return temp;
	}

	public static byte[] DB_ASCIItoEBCDIC_02(byte[] asc_byte) throws Exception {
		byte firstbyte = (byte)(asc_byte[0] - ASC_PARTITION_AREA_FLAG_byte);
		byte[] temp = new byte[]{0, 0};
		if (firstbyte % 2 != 0) {
			++firstbyte;
		}

		firstbyte = (byte)(firstbyte / 2);
		temp[0] = (byte)(firstbyte + 72);
		if (asc_byte[0] % 2 != 0) {
			if ((asc_byte[1] & 255) < 224) {
				temp[1] = (byte)(asc_byte[1] - 96);
			} else {
				temp[1] = (byte)(asc_byte[1] - 95);
			}
		} else {
			temp[1] = (byte)(asc_byte[1] - 1);
		}

		return temp;
	}

	public static byte[] DB_ASCIItoEBCDIC_03(byte[] asc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = asc_byte[0] - 129;
		int intsecond = asc_byte[1] - 64;
		int intsum;
		if (asc_byte[1] < 127) {
			intsum = intfirst * 190 + intsecond;
		} else {
			intsum = intfirst * 190 + intsecond - 1;
		}

		temp[0] = (byte)(129 + intsum / 188);
		temp[1] = (byte)(intsum - (temp[0] - 129) * 188 + 65);
		if ((temp[1] & 255) >= 128) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_ASCIItoEBCDIC_04(byte[] asc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = asc_byte[0] - 170;
		int intsecond = asc_byte[1] - 64;
		int intsum;
		if ((asc_byte[1] & 255) < 127) {
			intsum = intfirst * 96 + intsecond;
		} else {
			intsum = intfirst * 96 + intsecond - 1;
		}

		temp[0] = (byte)(161 + intsum / 188);
		if ((intsum - intsum / 188 * 188 & 255) > 123) {
			temp[1] = (byte)(intsum - (temp[0] - 161) * 188 - 123 + 64);
			++temp[0];
		} else {
			temp[1] = (byte)(intsum - (temp[0] - 161) * 188 + 129);
		}

		if ((temp[1] & 255) >= 128) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_ASCIItoEBCDIC_05(byte[] asc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = asc_byte[0] - 170;
		int intsecond = asc_byte[1] - 64;
		int intsum;
		if ((asc_byte[1] & 255) < 127) {
			intsum = intfirst * 96 + intsecond + 149 + 188;
		} else {
			intsum = intfirst * 96 + intsecond - 1 + 149 + 188;
		}

		temp[0] = (byte)(161 + intsum / 188);
		if ((intsum - intsum / 188 * 188 & 255) > 123) {
			temp[1] = (byte)(intsum - (temp[0] - 161) * 188 - 123 + 64);
			++temp[0];
		} else {
			temp[1] = (byte)(intsum - (temp[0] - 161) * 188 + 129);
		}

		if ((temp[1] & 255) >= 128) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_ASCIItoEBCDIC_06(byte[] asc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		byte m_index = (byte)(asc_byte[0] - 168);
		byte s_index = (byte)(asc_byte[1] - 64);
		temp[0] = IBMtoGBKTable[m_index & 255][(s_index & 255) * 2];
		temp[1] = IBMtoGBKTable[m_index & 255][(s_index & 255) * 2 + 1];
		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_01(byte[] ebc_byte) throws Exception {
		byte index1 = 0;
		byte index2 = 0;
		int index1_int = index1 & 255;
		int index2_int = index2 & 255;
		byte[] temp = new byte[]{0, 0};

		for(index1_int = 0; index1_int < 9; ++index1_int) {
			for(index2_int = 0; index2_int < 94; ++index2_int) {
				if (IBMtoGBTable[index1_int][index2_int * 2] == ebc_byte[0] && IBMtoGBTable[index1_int][index2_int * 2 + 1] == ebc_byte[1]) {
					temp[0] = (byte)(index1_int + 161);
					temp[1] = (byte)(index2_int + 161);
					return temp;
				}
			}
		}

		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_02(byte[] ebc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		if (ebc_byte[0] != 72) {
			byte firstbyte = (byte)(ebc_byte[0] - 72);
			firstbyte = (byte)(firstbyte * 2);
			if ((ebc_byte[1] & 255) <= 159) {
				--firstbyte;
			}

			temp[0] = (byte)(firstbyte + 176);
			if ((ebc_byte[1] & 255) <= 159) {
				if ((ebc_byte[1] & 255) < 129) {
					temp[1] = (byte)(ebc_byte[1] + 96);
				} else {
					temp[1] = (byte)(ebc_byte[1] + 95);
				}
			} else {
				temp[1] = (byte)(ebc_byte[1] + 1);
			}
		} else {
			temp[0] = -80;
			temp[1] = (byte)(ebc_byte[1] + 1);
		}

		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_03(byte[] ebc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = ebc_byte[0] - 129;
		int intsecond = ebc_byte[1] - 65;
		int intsum;
		if ((ebc_byte[1] & 255) < 128) {
			intsum = intfirst * 188 + intsecond;
		} else {
			intsum = intfirst * 188 + intsecond - 1;
		}

		temp[0] = (byte)(129 + intsum / 190);
		temp[1] = (byte)(intsum - (temp[0] - 129) * 190 + 64);
		if ((temp[1] & 255) >= 127) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_04(byte[] ebc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = ebc_byte[0] - 161;
		int intsecond = ebc_byte[1] - 65;
		int intsum;
		if ((ebc_byte[1] & 255) < 128) {
			intsum = intfirst * 188 + intsecond;
		} else {
			intsum = intfirst * 188 + intsecond - 1;
		}

		intsum -= 64;
		temp[0] = (byte)(170 + intsum / 96);
		temp[1] = (byte)(intsum - (temp[0] - 170) * 96 + 64);
		if ((temp[1] & 255) >= 127) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_05(byte[] ebc_byte) throws Exception {
		byte[] temp = new byte[]{0, 0};
		int intfirst = ebc_byte[0] - 161 - 1;
		int intsecond = ebc_byte[1] - 65;
		int intsum;
		if ((ebc_byte[1] & 255) < 128) {
			intsum = intfirst * 188 + intsecond;
		} else {
			intsum = intfirst * 188 + intsecond - 1;
		}

		intsum = intsum - 149 - 64;
		temp[0] = (byte)(170 + intsum / 96);
		temp[1] = (byte)(intsum - (temp[0] - 170) * 96 + 64);
		if ((temp[1] & 255) >= 127) {
			++temp[1];
		}

		return temp;
	}

	public static byte[] DB_EBCDICtoASCII_06(byte[] ebc_byte) throws Exception {
		byte index1 = 0;
		byte index2 = 0;
		int index1_int = index1 & 255;
		int index2_int = index2 & 255;
		byte[] temp = new byte[]{0, 0};

		for(index1_int = 0; index1_int < 2; index1_int = index1_int + 1) {
			for(index1_int = 0; index2_int < 94; ++index2_int) {
				if (IBMtoGBKTable[index1_int][index2_int * 2] == ebc_byte[0] && IBMtoGBKTable[index1_int][index2_int * 2 + 1] == ebc_byte[1]) {
					temp[0] = (byte)(index1_int + 168);
					temp[1] = (byte)(index2_int + 64);
					return temp;
				}
			}
		}

		return temp;
	}

	public static void main(String[] args) throws Exception {
		String f = "sss��s����sss";
		byte[] d = f.getBytes();
		byte[] h = pub_base_ASCtoEBC(d, d.length);
		System.out.println("sss��s����ssss= " + h.length);
		byte[] g = pub_base_EBCtoASC(h, h.length);
		System.out.println(g.length + ":" + (new String(g)).trim());
	}

	static {
		T_ISPWD = new byte[SNDLEN + 1];
		EBC_CHINESE_FLAG_START_byte = 14;
		EBC_CHINESE_FLAG_STOP_byte = 15;
		EBC_PARTITION_AREA_FLAG_byte = 72;
		ASC_CHINESE_FLAG_byte = -96;
		ASC_PARTITION_AREA_FLAG_byte = -80;
		IBMtoGBTable = new byte[][]{{64, 64, 67, 68, 67, 65, 67, 69, 69, 69, 69, 70, 68, 96, 68, 91, 68, 93, 68, 74, 67, -95, 68, 124, 68, 127, 68, 97, 68, 113, 68, 98, 68, 114, 68, 99, 68, 115, 68, 100, 68, 116, 68, 101, 68, 117, 67, 66, 67, 67, 68, 66, 68, 67, 69, 91, 69, 92, 68, 102, 68, 118, 68, 75, 68, 122, 68, 123, 69, 98, 69, 99, 69, 100, 69, 101, 69, 102, 69, 103, 69, 104, 69, 105, 69, 106, 69, 107, 69, 108, 69, 109, 69, 110, 69, 111, 69, 112, 69, 113, 69, 114, 69, 115, 69, 116, 69, 117, 69, 118, 69, 119, 68, 76, 69, 121, 69, 122, 68, 103, 68, 119, 68, 77, 68, 120, 68, 104, 68, 105, 68, 121, 68, -19, 68, -18, 68, -17, 68, 78, 66, -32, 69, -120, 67, 74, 66, 74, 69, -117, 68, 106, 68, 110, 68, -27, 68, -26, 68, -32, 68, -31, 68, -28, 68, -25, 68, -24, 68, -23, 68, -22, 68, -30, 68, -29, 68, 107, 68, -16, 68, -15, 68, -14, 68, -13, 68, 125}, {64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 69, -79, 69, -78, 69, -77, 69, -76, 69, -75, 69, -74, 69, -73, 69, -72, 69, -71, 69, -70, 69, -69, 69, -84, 69, -67, 69, -66, 69, -65, 69, -64, 69, -63, 69, -62, 69, -61, 69, -60, 69, -59, 69, -58, 69, -57, 69, -56, 69, -55, 69, -54, 69, -53, 69, -52, 69, -51, 69, -50, 69, -49, 69, -48, 69, -47, 69, -46, 69, -45, 69, -44, 69, -43, 69, -42, 69, -41, 69, -40, 69, -31, 69, -30, 69, -29, 69, -28, 69, -27, 69, -26, 69, -25, 69, -24, 69, -23, 69, -22, 64, 64, 64, 64, 69, -15, 69, -14, 69, -13, 69, -12, 69, -11, 69, -10, 69, -9, 69, -8, 69, -7, 69, -6, 64, 64, 64, 64, 65, -15, 65, -14, 65, -13, 65, -12, 65, -11, 65, -10, 65, -9, 65, -8, 65, -7, 65, -6, 65, -5, 65, -4, 64, 64, 64, 64}, {66, 90, 66, 127, 66, 123, 66, 91, 66, 108, 66, 80, 68, 80, 66, 77, 66, 93, 66, 92, 66, 78, 66, 107, 66, 96, 66, 75, 66, 97, 66, -16, 66, -15, 66, -14, 66, -13, 66, -12, 66, -11, 66, -10, 66, -9, 66, -8, 66, -7, 66, 122, 66, 94, 66, 76, 66, 126, 66, 110, 66, 111, 66, 124, 66, -63, 66, -62, 66, -61, 66, -60, 66, -59, 66, -58, 66, -57, 66, -56, 66, -55, 66, -47, 66, -46, 66, -45, 66, -44, 66, -43, 66, -42, 66, -41, 66, -40, 66, -39, 66, -30, 66, -29, 66, -28, 66, -27, 66, -26, 66, -25, 66, -24, 66, -23, 68, 68, 67, -32, 68, 69, 68, 112, 66, 109, 66, 121, 66, -127, 66, -126, 66, -125, 66, -124, 66, -123, 66, -122, 66, -121, 66, -120, 66, -119, 66, -111, 66, -110, 66, -109, 66, -108, 66, -107, 66, -106, 66, -105, 66, -104, 66, -103, 66, -94, 66, -93, 66, -92, 66, -91, 66, -90, 66, -89, 66, -88, 66, -87, 66, -64, 66, 79, 66, -48, 66, -95}, {68, 71, 68, -127, 68, 72, 68, -126, 68, 73, 68, -125, 68, 81, 68, -124, 68, 82, 68, -123, 68, -122, 68, -64, 68, -121, 68, -63, 68, -120, 68, -62, 68, -119, 68, -61, 68, -118, 68, -60, 68, -116, 68, -59, 68, -115, 68, -58, 68, -114, 68, -57, 68, -113, 68, -56, 68, -112, 68, -55, 68, -111, 68, -54, 68, -110, 68, -53, 68, 86, 68, -109, 68, -52, 68, -108, 68, -51, 68, -107, 68, -50, 68, -106, 68, -105, 68, -104, 68, -103, 68, -102, 68, -99, 68, -49, 68, -43, 68, -98, 68, -48, 68, -42, 68, -97, 68, -47, 68, -41, 68, -94, 68, -46, 68, -40, 68, -93, 68, -45, 68, -39, 68, -92, 68, -91, 68, -90, 68, -89, 68, -88, 68, 83, 68, -87, 68, 84, 68, -86, 68, 85, 68, -84, 68, -83, 68, -82, 68, -81, 68, -70, 68, -69, 68, 87, 68, -68, 68, -38, 68, -37, 68, 70, 68, -67, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}, {67, 71, 67, -127, 67, 72, 67, -126, 67, 73, 67, -125, 67, 81, 67, -124, 67, 82, 67, -123, 67, -122, 67, -64, 67, -121, 67, -63, 67, -120, 67, -62, 67, -119, 67, -61, 67, -118, 67, -60, 67, -116, 67, -59, 67, -115, 67, -58, 67, -114, 67, -57, 67, -113, 67, -56, 67, -112, 67, -55, 67, -111, 67, -54, 67, -110, 67, -53, 67, 86, 67, -109, 67, -52, 67, -108, 67, -51, 67, -107, 67, -50, 67, -106, 67, -105, 67, -104, 67, -103, 67, -102, 67, -99, 67, -49, 67, -43, 67, -98, 67, -48, 67, -42, 67, -97, 67, -47, 67, -41, 67, -94, 67, -46, 67, -40, 67, -93, 67, -45, 67, -39, 67, -92, 67, -91, 67, -90, 67, -89, 67, -88, 67, 83, 67, -87, 67, 84, 67, -86, 67, 85, 67, -84, 67, -83, 67, -82, 67, -81, 67, -70, 67, -69, 67, 87, 67, -68, 67, -38, 67, -37, 67, 70, 67, -67, 67, -44, 67, 89, 67, 90, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}, {65, 97, 65, 98, 65, 99, 65, 100, 65, 101, 65, 102, 65, 103, 65, 104, 65, 105, 65, 106, 65, 107, 65, 108, 65, 109, 65, 110, 65, 111, 65, 112, 65, 113, 65, 114, 65, 115, 65, 116, 65, 117, 65, 118, 65, 119, 65, 120, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 65, 65, 65, 66, 65, 67, 65, 68, 65, 69, 65, 70, 65, 71, 65, 72, 65, 73, 65, 74, 65, 75, 65, 76, 65, 77, 65, 78, 65, 79, 65, 80, 65, 81, 65, 82, 65, 83, 65, 84, 65, 85, 65, 86, 65, 87, 65, 88, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}, {65, -64, 65, -63, 65, -62, 65, -61, 65, -60, 65, -59, 65, -58, 65, -57, 65, -56, 65, -55, 65, -54, 65, -53, 65, -52, 65, -51, 65, -50, 65, -49, 65, -48, 65, -47, 65, -46, 65, -45, 65, -44, 65, -43, 65, -42, 65, -41, 65, -40, 65, -39, 65, -38, 65, -37, 65, -36, 65, -35, 65, -34, 65, -33, 65, -32, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 65, -128, 65, -127, 65, -126, 65, -125, 65, -124, 65, -123, 65, -122, 65, -121, 65, -120, 65, -119, 65, -118, 65, -117, 65, -116, 65, -115, 65, -114, 65, -113, 65, -112, 65, -111, 65, -110, 65, -109, 65, -108, 65, -107, 65, -106, 65, -105, 65, -104, 65, -103, 65, -102, 65, -101, 65, -100, 65, -99, 65, -114, 65, -97, 65, -96, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}, {70, 65, 70, 66, 70, 67, 70, 68, 70, 69, 70, 70, 70, 71, 70, 72, 70, 73, 70, 74, 70, 75, 70, 76, 70, 77, 70, 78, 70, 79, 70, 80, 70, 81, 70, 82, 70, 83, 70, 84, 70, 85, 70, 86, 70, 87, 70, 88, 70, 89, 70, 90, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 70, 101, 70, 102, 70, 103, 70, 104, 70, 105, 70, 106, 70, 107, 70, 108, 70, 109, 70, 110, 70, 111, 70, 112, 70, 113, 70, 114, 70, 115, 70, 116, 70, 117, 70, 118, 70, 119, 70, 120, 70, 121, 70, 122, 70, 123, 70, 124, 70, 125, 70, 126, 70, 127, 70, -128, 70, -127, 70, -126, 70, -125, 70, -124, 70, -123, 70, -122, 70, -121, 70, -120, 70, -119, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}, {64, 64, 64, 64, 64, 64, 70, -92, 70, -91, 70, -90, 70, -89, 70, -88, 70, -87, 70, -86, 70, -85, 70, -84, 70, -83, 70, -82, 70, -81, 70, -80, 70, -79, 70, -78, 70, -77, 70, -76, 70, -75, 70, -74, 70, -73, 70, -72, 70, -71, 70, -70, 70, -69, 70, -68, 70, -67, 70, -66, 70, -65, 70, -64, 70, -63, 70, -62, 70, -61, 70, -60, 70, -59, 70, -58, 70, -57, 70, -56, 70, -55, 70, -54, 70, -53, 70, -52, 70, -51, 70, -50, 70, -49, 70, -48, 70, -47, 70, -46, 70, -45, 70, -44, 70, -43, 70, -42, 70, -41, 70, -40, 70, -39, 70, -38, 70, -37, 70, -36, 70, -35, 70, -34, 70, -33, 70, -32, 70, -31, 70, -30, 70, -29, 70, -28, 70, -27, 70, -26, 70, -25, 70, -24, 70, -23, 70, -22, 70, -21, 70, -20, 70, -19, 70, -18, 70, -17, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64}};
		IBMtoGBKTable = new byte[][]{{-51, 65, -51, 66, -51, 67, -51, 68, -51, 69, 68, 126, -51, 70, -51, 71, -51, 72, -51, 73, -51, 74, -51, 75, -51, 76, -51, 77, -51, 78, -51, 79, -51, 80, -51, 81, -51, 82, -51, 83, -51, 84, -51, 85, -51, 86, -51, 87, -51, 88, -51, 89, -51, 90, -51, 91, -51, 92, -51, 93, -51, 94, -51, 95, -51, 96, -51, 97, -51, 98, -51, 99, -51, 100, -51, 101, -51, 102, -51, 103, -51, 104, -51, 105, -51, 106, -51, 107, -51, 108, -51, 109, -51, 110, -51, 111, -51, 112, -51, 113, -51, 114, -51, 115, -51, 116, -51, 117, -51, 118, -51, 119, -51, 120, -51, 121, -51, 122, -51, 123, -51, 124, -51, 125, -51, 126, -51, 127, -51, -127, -51, -126, -51, -125, -51, -124, -51, -123, -51, -122, -51, -121, -51, -120, -51, -119, -51, -118, 68, -20, 68, -21, -51, -117, -51, -116, -51, -115, -51, -114, -51, -113, -51, -112, 68, 108, -51, -111, -51, -110, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}, {-51, -109, -51, -108, -51, -107, -51, -106, -51, -105, -51, -104, -51, -103, -51, -102, -51, -101, -51, -100, -51, -99, -51, -98, -51, -97, -51, -96, -51, -95, -51, -94, -51, -93, -51, -92, -51, -91, -51, -90, -51, -89, -51, -88, 66, 95, 66, 106, -2, -2, 68, 111, 68, 109, -2, -2, 68, 90, -2, -2, -2, -2, -2, -2, 67, 88, 67, -66, 67, -65, 67, -36, 67, -35, 68, 94, 68, -36, 68, -35, -51, -87, -51, -86, -51, -85, -51, -84, -51, -83, -51, -82, -51, -81, -51, -80, -51, -79, -51, -78, -51, -77, -51, -76, -51, -75, -51, -74, -51, -73, -51, -72, -51, -71, -51, -70, -51, -69, -51, -68, -51, -67, -51, -66, -51, -65, -51, -64, -51, -63, -51, -62, -51, -61, -49, -60, -51, -59, -51, -58, -51, -57, -51, -56, -49, -55, -51, -54, -51, -53, -51, -52, -51, -51, -49, -50, -51, -49, -51, -48, -51, -47, -51, -46, -49, -45, -51, -44, -51, -43, 68, 95, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}};
	}
}