package com.jsonTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {

	
		
	
		public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		@SuppressWarnings("deprecation")
		public static void main(String[] args) throws IOException {

			
			
			try {
				
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream filePath = classloader.getResourceAsStream("generated.json");
				
				if (filePath == null) {
				throw new NullPointerException("Cannot find resource file " + filePath);
				
			}
				



				JSONTokener tokener = new JSONTokener(filePath);
				JSONArray jsonArray = new JSONArray(tokener);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					String email = jsonObject.getString("email");
					
				if(validate(email)) {
					System.out.println("found "+email);
				}

				}
				

				JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(0);
				// Name
				String name = (String) jsonObject.getString("name");
				System.out.println("Name: " + name);

				
				String email = jsonObject.getString("email");
				System.out.println("Email: " + email);
				
				
				// latitude and longitude
				double lat = (Double) jsonObject.getDouble("latitude");
				double lot = (Double) jsonObject.getDouble("longitude");

				System.out.println("Latitude: " + lat + " Longitude" + lot);
				

				List<JSONObject> lista = new ArrayList<>();

				// Modify JSON
				jsonObject.put("index", "5");

				lista.add(jsonObject);

				JSONObject addedObj = (JSONObject) jsonObject.put("index", 0);
				System.out.println("Added is: " + addedObj);

				JSONObject newmemObject = (JSONObject) addedObj.put("index", 5);
				System.out.println("newObject is: " + newmemObject);

				int index = jsonObject.getInt("index");
				System.out.println("Index : " + index);
			    
				
					// New File 
					File newFile = new File("/Users/adnannmuratovic/eclipse-workspace/Practice/src/generatedNewFile.json");
					newFile.createNewFile();
				
					FileWriter fileWritter = new FileWriter(newFile);
					fileWritter.write(newmemObject.toString());
					fileWritter.close();
					
					System.out.println(newFile);
					
					System.out.print(jsonObject);
					

					// Create PDF Document
					PDDocument document = new PDDocument();
					PDPage page = new PDPage();
					
					document.addPage(page);
					
					
					PDFont font = PDType1Font.TIMES_BOLD_ITALIC;
					
					PDPageContentStream contentStream = new PDPageContentStream(document,page);
					
					// Text
					contentStream.beginText();
					contentStream.setFont(font, 15);
					contentStream.showText("Adnann Muratovic " + " ----> " + email);
					contentStream.setLeading(15);     
				    contentStream.newLineAtOffset(175, 670);
//					contentStream.newLineAtOffset(400, 200);
					contentStream.endText();
				
					contentStream.close();
					
					document.save("NewPDFFile.pdf");
					document.close();
					
					
			
			}

			catch (JSONException e) {
				e.printStackTrace();
			}   
		}
		
		public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
		
}
		

	


