package com.invy.database.testdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestDataUtilTest {

	@Test
	public void testTestDataCleanse() {
		String putPrefix = "insert into itemref (name,unitclass,catalognumber,description,dummy) values ('";
		String putSuffix = "');\n";
		String fileName = "d:\\testcsv.csv";
		String line = null;
		List<String> lines = new ArrayList<>();
		File f1 = new File(fileName);
		BufferedReader br=null;
		BufferedWriter out=null;
		FileWriter fw=null;
		try {
			FileReader fr = new FileReader(f1);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				line = putPrefix+line.trim()+putSuffix;
				lines.add(line);
			}
			fw = new FileWriter(f1);
			out = new BufferedWriter(fw);
			for(String line2:lines){
				out.write(line2);
				out.flush();
			}
			out.close();
			fw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
