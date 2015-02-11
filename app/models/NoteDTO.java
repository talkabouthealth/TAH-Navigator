package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.notes")
@javax.persistence.Entity
public class NoteDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="notes_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="notes_id_seq", sequenceName = "nav.notes_id_seq")
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "noteby")
	private UserDTO noteBy;

	@OneToOne
	@JoinColumn(name = "notefor")
	private UserDTO noteFor;

	@Column(name = "notetitle")
	private String noteTitle;

	@Column(name = "notedesc")
	private String noteDesc;

	@Column(name = "notedate")
	private Date noteDate;
	
	@Column(name = "editDate")
	private Date noteEditDate;

	@Column(name = "notesection")
	private String noteSection;
	
	public String getNoteSection() {
		return noteSection;
	}

	public void setNoteSection(String noteSection) {
		this.noteSection = noteSection;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getNoteBy() {
		return noteBy;
	}

	public void setNoteBy(UserDTO noteBy) {
		this.noteBy = noteBy;
	}

	public UserDTO getNoteFor() {
		return noteFor;
	}

	public void setNoteFor(UserDTO noteFor) {
		this.noteFor = noteFor;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteDesc() {
		return noteDesc;
	}

	public void setNoteDesc(String noteDesc) {
		this.noteDesc = noteDesc;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public Date getNoteEditDate() {
		return noteEditDate;
	}

	public void setNoteEditDate(Date noteEditDate) {
		this.noteEditDate = noteEditDate;
	}
}