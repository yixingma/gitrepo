/**
 * 
 */
package com.invy.commons.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ema
 * 
 */
public class CompressionUtils {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CompressionUtils.class);

	public static byte[] compress(byte[] data) throws IOException {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); 
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		LOGGER.info("Original: " + data.length / 1024 + " Kb");
		LOGGER.info("Compressed: " + output.length / 1024 + " Kb");
		return output;

	}

}
