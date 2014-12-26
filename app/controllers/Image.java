package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;

import models.CareTeamMasterDTO;
import models.PatientMedicationDTO;
import models.UserImageDTO;
import nav.dao.CareTeamDAO;
import nav.dao.MedicationDAO;
import nav.dao.UserDAO;
import nav.dao.UserImageDAO;
import nav.dto.UserBean;
import play.Play;
import play.mvc.Controller;
import util.CommonUtil;

public class Image extends Controller {
	
	public static final File DEFAULT_IMAGE_FILE = Play.getFile("/public/images/top-p.png");
	public static final File DEFAULT_SOCIAL_IMAGE_FILE = Play.getFile("public/images/tahfblike.jpg");
	public static final File DEFAULT_IMAGE_MEDICINE_FILE = Play.getFile("/public/images/pill.png");
//	private static final int IMG_WIDTH = 202;
//	private static final int IMG_HEIGHT = 202;

	/**
	 * Renders image of given talker (or returns default image)
	 */
	public static void show(int userId) {
//		System.out.println("Image servlate : "+ userId);
		String contentType = "image/png";// + imageBean.getImageType();
		
		UserBean user = CommonUtil.loadCachedUser(session);
		boolean loadImage = true;
		if(user.getId() != userId) {
			if(session.get("usertype").equals("user")) {
				UserBean patientDto = UserDAO.getUserByField("id", new Integer(userId));
				if(patientDto != null && patientDto.getUserType() == 'p') {
					loadImage = false;
				}
			}
		}
		UserImageDTO imgdto =  UserImageDAO.getByUserId(userId);
		response.setHeader("Content-Type", contentType);
		response.setHeader("Cache-Control", "no-cache");
		if(loadImage) {
			if (imgdto != null && imgdto.getByteImage() == null) {
				//render default
				renderBinary(DEFAULT_IMAGE_FILE);
			} else {
				try{
					if(imgdto != null && imgdto.getByteImage() != null)
						renderBinary(new ByteArrayInputStream(imgdto.getByteImage()));
					else
						renderBinary(DEFAULT_IMAGE_FILE);
				 
				}catch ( Exception e ) {
					e.printStackTrace();
					renderBinary(DEFAULT_IMAGE_FILE);
				}
			}
		} else {
			renderBinary(DEFAULT_IMAGE_FILE);
		}
	}
	
	public static void showClinic(int careTeamId) {
		CareTeamMasterDTO imgdto = CareTeamDAO.getCareTeamByField("id", careTeamId);
		response.setHeader("Content-Type", "image/png");
		response.setHeader("Cache-Control", "no-cache");
		if (imgdto != null && imgdto.getLogo()  == null) {
			renderBinary(DEFAULT_IMAGE_MEDICINE_FILE);
		} else {
			try{
				if(imgdto != null && imgdto.getLogo() != null)
					renderBinary(new ByteArrayInputStream(imgdto.getLogo()));
			}catch ( Exception e ) {
				e.printStackTrace();
				renderText(imgdto.getLogo());
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