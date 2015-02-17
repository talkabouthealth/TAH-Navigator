package controllers;

import javax.persistence.EntityManager;

import play.mvc.Controller;
import util.JPAUtil;

public class Static extends Controller {

	public static void success() {
		render();
	}
	
	public static void about() {
		render();
	}
	
	public static void contact() {
		render();
	}
	
	public static void distRessThanks() {
		render();
	}
	
	public static void pageNotFound() {
		render();
	}
	public static void tos() {
		render();
	}
	public static void privacy() {
		render();
	}
	public static void status() {
		String status ="OK";
		try {
			EntityManager em = JPAUtil.getEntityManager();
			if(!em.isOpen())
				status ="ERROR";
		} catch(Exception e ) {
			e.printStackTrace();
			status ="ERROR";	
		}
		renderText(status);
	}
}
