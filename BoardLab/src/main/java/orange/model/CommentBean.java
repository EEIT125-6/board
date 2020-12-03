package orange.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Board")
public class CommentBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String name;
	private Integer star;
	private Date date;
	private String context;
	private String photo;
	
	
	public CommentBean(){
		
	}
	
	public CommentBean(String pname,Integer pstars,Date pdate,String pcontext,String pphoto) {
		
		this.name = pname;
		this.star = pstars;
		this.date = pdate;
		this.context = pcontext;
		this.photo=pphoto;
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStars() {
		return star;
	}
	public void setStars(Integer stars) {
		this.star = stars;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
}
