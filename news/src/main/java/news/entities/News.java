package news.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "news", schema = "public")
public class News {

	@Id
	@Column(name = "id_news", unique = true, nullable = false)
	private int idNews;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dat", length = 13)
	private Date dat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rubric")
	private Rubric rubric;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "header")
	private String header;

	public News() {
	}

	public int getIdNews() {
		return this.idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}

	public Date getDat() {
		return this.dat;
	}

	public void setDat(Date dat) {
		this.dat = dat;
	}

	public Rubric getRubric() {
		return this.rubric;
	}

	public void setRubric(Rubric rubric) {
		this.rubric = rubric;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
