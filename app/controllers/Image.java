package controllers;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import nav.dao.MedicationDAO;
import nav.dao.UserImageDAO;

import models.PatientMedicationDTO;
import models.UserImageDTO;

import play.Play;
import play.mvc.Controller;
import util.ImageUtil;

public class Image extends Controller {
	
	public static final File DEFAULT_IMAGE_FILE = Play.getFile("/public/images/top-p.png");
	public static final File DEFAULT_SOCIAL_IMAGE_FILE = Play.getFile("public/images/tahfblike.jpg");
	public static final File DEFAULT_IMAGE_MEDICINE_FILE = Play.getFile("/public/images/pill.png");
//	private static final int IMG_WIDTH = 202;
//	private static final int IMG_HEIGHT = 202;

	/**
	 * Renders image of given talker (or returns default image)
	 */
	public static void show(String userId) {
//		System.out.println("Image servlate : "+ userId);
		String contentType = "image/png";// + imageBean.getImageType();
		UserImageDTO imgdto =  UserImageDAO.getByUserId(userId);
		response.setHeader("Content-Type", contentType);
		response.setHeader("Cache-Control", "no-cache");
		if (imgdto != null && imgdto.getByteImage() == null) {
			//render default
			renderBinary(DEFAULT_IMAGE_FILE);
			
		} else {
			try{
				if(imgdto != null && imgdto.getByteImage() != null)
					renderBinary(new ByteArrayInputStream(imgdto.getByteImage()));
				else
					renderBinary(DEFAULT_IMAGE_FILE);
			 	/*
				String [] coords = imageBean.getCoords();
		        int xPos = 0;
				int yPos =  0;
				int width =  100;
				int height =  100;
				if(coords != null && coords.length == 4) {
			    	xPos = Integer.parseInt(coords[0]);
			    	yPos = Integer.parseInt(coords[1]);
			    	width = Integer.parseInt(coords[2]);
			    	height = Integer.parseInt(coords[3]);
			    }
				ByteArrayOutputStream baos = null;
				if(coords == null || (originalImage.getWidth() < xPos + width || originalImage.getHeight() < yPos + height)) {
			 		//baos = ImageUtil.createCropedThumbnail(0, 0, originalImage.getWidth(), originalImage.getHeight(), originalImage,imageBean.getImageType());
					//baos = new ByteArrayOutputStream(in);
					renderBinary(new ByteArrayInputStream(imageBean.getImageArray()));
			 	} else {
			 		width = width + xPos;
			 		height = height + yPos;
			 		baos = ImageUtil.crop(xPos, yPos, width, height, originalImage,imageBean.getImageType());
			 		renderBinary(new ByteArrayInputStream(baos.toByteArray()));
			 	}
			 	*/
			}catch ( Exception e ) {
				e.printStackTrace();
				renderBinary(DEFAULT_IMAGE_FILE);
			}
		}
		
	}
	
	public static void showMedicine(String medId) {
		String contentType = "image/png";
		Integer idField = new Integer(medId);
		PatientMedicationDTO imgdto =  MedicationDAO.getMedicineByField("id",idField);
		response.setHeader("Content-Type", contentType);
		response.setHeader("Cache-Control", "no-cache");
		if (imgdto != null && imgdto.getMedicine().getImage()  == null) {
			renderBinary(DEFAULT_IMAGE_MEDICINE_FILE);
		} else {
			try{
				if(imgdto != null && imgdto.getMedicine().getImage() != null)
					renderBinary(new ByteArrayInputStream(imgdto.getMedicine().getImage()));
				else
					renderText(imgdto.getMethod());
//					renderBinary(DEFAULT_IMAGE_MEDICINE_FILE);
			}catch ( Exception e ) {
				e.printStackTrace();
//				renderBinary(DEFAULT_IMAGE_MEDICINE_FILE);
				renderText(imgdto.getMethod());
			}
		}
	}
	
  /*
	 private static byte[] resizeImageWithHint(byte[] imageArray) {
		 try {
		 	InputStream in = new ByteArrayInputStream(imageArray);
		 	BufferedImage originalImage = ImageIO.read(in);
		 	
		 	int type = (originalImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		 	
		 	if(originalImage.getHeight() >= IMG_HEIGHT && originalImage.getWidth() >=  IMG_WIDTH) {
		 		return null;
		 	} else {
				BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
				Graphics2D g = resizedImage.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

				g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
				g.dispose();

				g.setComposite(AlphaComposite.Src);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( resizedImage, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();

				return imageInByte;
		 	}
		} catch (IOException e) {
//			System.out.println(e.getMessage());
		}
		return null;
	}
	*/
}