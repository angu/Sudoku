import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.asprise.util.ocr.OCR;

public class OCRParser {

	private static final int TEMP_DIR_ATTEMPTS = 10000;
	private String filePath;

	public OCRParser(String image) {
		filePath = image;
	}

	public String performParse()
	{
		
		
		// loads the image.
		String p="";
		try{
		 BufferedImage image = ImageIO.read(new File(filePath));
		 File tempDir=createTempDir();
		   System.out.println("Temp dir: "+tempDir.getAbsolutePath());
		image=getBufferedImage(image.getScaledInstance(812, 812,Image.SCALE_SMOOTH));
		 for(int r=0;r<9;r++)
			 for(int c=0;c<9;c++)
		 {
		 System.out.println("Controllo riga: "+r+" e colonna "+c);
		 BufferedImage imagec=image.getSubimage(11+89*c,11+89*r,80,80);
		 imagec=getBufferedImage(imagec.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		 
		 try {
			    // retrieve image
			  
			    File outputfile = new File(tempDir.getAbsolutePath()+"/saved"+r+c+".png");
			    ImageIO.write(imagec, "png", outputfile);
			} catch (IOException e) {

			}
		 // recognizes both characters and barcodes
		 String s = new OCR().recognizeCharacters(imagec);
		
		 // prints the results.
		
		 System.out.println("RESULTS: \n"+ s.charAt(s.length()-3));
		 char cr=s.charAt(s.length()-3);
		 if(Character.isDigit(cr))
		 {
			 p+=cr;
		 }
		 else
		 {
			 p+="0";
		 }
		 }
		}
		catch(Exception e)
		{
			System.out.println("Exception :"+e.getLocalizedMessage());
		}
		System.out.println("Repre: "+p);
		return p;
	}

	public static BufferedImage getBufferedImage(Image img) {
		if (img == null)
			return null;
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage bufimg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufimg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img, 0, 0, w, h, null);
		g2.dispose();
		return bufimg;
	}
	
	
	/*
	 * Metodo accessorio per creare una cartella temporanea.
	 * 
	 * */
	public static File createTempDir() {
		  File baseDir = new File(System.getProperty("java.io.tmpdir"));
		  String baseName = System.currentTimeMillis() + "-";

		  for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
		    File tempDir = new File(baseDir, baseName + counter);
		    if (tempDir.mkdir()) {
		      return tempDir;
		    }
		  }
		  throw new IllegalStateException("Failed to create directory within "
		      + TEMP_DIR_ATTEMPTS + " attempts (tried "
		      + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
		}
}
