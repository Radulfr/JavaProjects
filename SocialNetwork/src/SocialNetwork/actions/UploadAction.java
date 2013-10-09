package SocialNetwork.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private String filename;

	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}

	public String execute() {
		
		File serverfile = new File(this.filename);
		Map<String, Object> session = ActionContext.getContext().getSession();  // Get session
		try{
			InputStream in = new FileInputStream(this.file);
			OutputStream out = new FileOutputStream(serverfile);
                
			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
			}

			in.close();
			out.close();
		} catch (IOException e){
			System.out.println(e.toString());
			return ERROR;
		}
		
		return SUCCESS;
	}
}