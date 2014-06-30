package models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.userimage")
@javax.persistence.Entity
public class UserImageDTO {
	
	@Id
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "id")
	private UserDTO user;

	@Column(name = "image")
	private byte [] byteImage;
	
	@Column(name = "imagetype")
	private String imagetype;
	
	@Column(name = "imagename")
	private String imagename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public byte[] getByteImage() {
		return byteImage;
	}

	public void setByteImage(byte[] byteImage) {
		this.byteImage = byteImage;
	}

	public String getImagetype() {
		return imagetype;
	}

	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
}