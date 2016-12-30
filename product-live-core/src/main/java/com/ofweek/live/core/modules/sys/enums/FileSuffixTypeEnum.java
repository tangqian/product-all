package com.ofweek.live.core.modules.sys.enums;

import java.io.IOException;
import java.io.InputStream;

import com.ofweek.live.core.common.utils.StringUtils;

public enum FileSuffixTypeEnum {

	/**
	 * JEPG.
	 */
	JPEG("FFD8FF"),

	/**
	 * PNG.
	 */
	PNG("89504E47"),

	/**
	 * GIF.
	 */
	GIF("47494638"),

	/**
	 * TIFF.
	 */
	TIFF("49492A00"),

	/**
	 * Windows Bitmap.
	 */
	BMP("424D"),

	/**
	 * CAD.
	 */
	DWG("41433130"),

	/**
	 * Adobe Photoshop.
	 */
	PSD("38425053"),

	/**
	 * Rich Text Format.
	 */
	RTF("7B5C727466"),

	/**
	 * XML.
	 */
	XML("3C3F786D6C"),

	/**
	 * HTML.
	 */
	HTML("68746D6C3E"),

	/**
	 * Email [thorough only].
	 */
	EML("44656C69766572792D646174653A"),

	/**
	 * Outlook Express.
	 */
	DBX("CFAD12FEC5FD746F"),

	/**
	 * Outlook (pst).
	 */
	PST("2142444E"),

	/**
	 * MS Word/Excel.
	 */
	XLS_DOC("D0CF11E0"),

	/**
	 * MS Access.
	 */
	MDB("5374616E64617264204A"),

	/**
	 * WordPerfect.
	 */
	WPD("FF575043"),

	/**
	 * Postscript.
	 */
	EPS("252150532D41646F6265"),

	/**
	 * Adobe Acrobat.
	 */
	PDF("255044462D312E"),

	/**
	 * Quicken.
	 */
	QDF("AC9EBD8F"),

	/**
	 * Windows Password.
	 */
	PWL("E3828596"),

	/**
	 * ZIP Archive.
	 */
	ZIP("504B0304"),

	/**
	 * RAR Archive.
	 */
	RAR("52617221"),

	/**
	 * Wave.
	 */
	WAV("57415645"),

	/**
	 * AVI.
	 */
	AVI("41564920"),

	/**
	 * Real Audio.
	 */
	RAM("2E7261FD"),

	/**
	 * Real Media.
	 */
	RM("2E524D46"),

	/**
	 * MPEG (mpg).
	 */
	MPG("000001BA"),

	/**
	 * Quicktime.
	 */
	MOV("6D6F6F76"),

	/**
	 * Windows Media.
	 */
	ASF("3026B2758E66CF11"),

	/**
	 * MIDI.
	 */
	MID("4D546864");

	private String value = "";

	public static FileSuffixTypeEnum getType(InputStream inputStream)
			throws IOException {

		byte[] b = new byte[28];
		if (inputStream.markSupported()) {
			inputStream.mark(28);
		}
		try {
			inputStream.read(b, 0, 28);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inputStream.markSupported()) {
				inputStream.reset();
			}
		}
		String fileHeader = StringUtils.toHex(b);
		if (StringUtils.isBlank(fileHeader)) {
			return null;
		}
		fileHeader = fileHeader.toUpperCase();
		FileSuffixTypeEnum[] fileTypes = FileSuffixTypeEnum.values();
		for (FileSuffixTypeEnum type : fileTypes) {
			if (fileHeader.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}

	public static boolean isAnyType(InputStream inputStream,
			FileSuffixTypeEnum... types) throws IOException {
		if (types == null || types.length == 0) {
			return false;
		}
		FileSuffixTypeEnum type = getType(inputStream);
		if (type == null) {
			return false;
		}
		for (FileSuffixTypeEnum validateType : types) {
			if (type.equals(validateType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 */
	private FileSuffixTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
