package org.lf.utils;

import java.util.Comparator;

import org.springframework.web.multipart.MultipartFile;
/**
 * 根据文件名顺序实现的比较器
 * @author sunwill
 *
 */
public class Comparator4File implements Comparator<MultipartFile> {

	@Override
	public int compare(MultipartFile o1, MultipartFile o2) {
		String file1Name = o1.getOriginalFilename().split("\\.")[0];
		String file2Name = o2.getOriginalFilename().split("\\.")[0];
		return file1Name.compareToIgnoreCase(file2Name);
	}

}
