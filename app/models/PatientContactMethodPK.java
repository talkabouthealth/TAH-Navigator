package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class PatientContactMethodPK  implements Serializable {
	
	private Integer userid;

	private Integer contactmethod;
}
