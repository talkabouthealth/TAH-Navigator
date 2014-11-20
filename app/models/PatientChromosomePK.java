package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class PatientChromosomePK  implements Serializable {
	
	private Integer patientid;

	private Integer chromosomeid;
}
