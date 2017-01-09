package org.lf.utils;

import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

import org.junit.Test;

public class StringUtilsTest {

	 @Test
	public void testToPinYin() {
//		List<String> result = StringUtils.toPinYin("湖北省高级人民法院", HanyuPinyinCaseType.UPPERCASE);

		System.out.println(StringUtils.toShortPinYin("湖北省m高级人民法院", 5));
	}

	// @Test
	public void test1() {
		for (int i = 0; i < 30; i++) {
			System.out.println(Math.random());
		}
	}

	@Test
	public void testCrc32() {
		CRC32 crc32 = new CRC32();
		for (int i = 0; i < 10; i++) {
			String uuid = StringUtils.getUUID();
			System.out.println("UUID:" + uuid);
			crc32.update(uuid.getBytes());
			System.out.println("CRC32:" + crc32.getValue());
		}
	}

}
