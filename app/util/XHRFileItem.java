package util;

import org.apache.commons.fileupload.FileItem;

import java.io.*;

import static play.mvc.Http.Request.current;

import org.apache.commons.fileupload.FileItem;

public class XHRFileItem implements FileItem{

    private String fieldName;

    public XHRFileItem(String fieldName) {
        this.fieldName = fieldName;
    }

    public InputStream getInputStream() throws IOException {
        return current().body;
    }

    public String getContentType() {
        return current().contentType;
    }

    public String getName() {
        String fileName = current().params.get(fieldName);
        if (fileName == null) {
            fileName = current().headers.get("x-file-name").value();
        }
        return fileName;
    }

    public boolean isInMemory() {
        return false;
    }

    public long getSize() {
        return 0;
    }

    public byte[] get() {
        return new byte[0];
    }

    public String getString(String s) throws UnsupportedEncodingException {
        return s;
    }

    public String getString() {
        return "";
    }

    public void delete() {

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isFormField() {
        return false;
    }

    public void setFormField(boolean b) {

    }

	@Override
	public void write(File arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
