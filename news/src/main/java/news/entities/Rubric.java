package news.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rubric", schema = "public")
public class Rubric implements java.io.Serializable {

	@Id
	@Column(name = "id_rubric", unique = true, nullable = false)
	private int idRubric;
	
	@Column(name = "name", length = 150)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rubric")
	private List<News> newsList = new ArrayList<News>();

	public Rubric() {
	}

	public int getIdRubric() {
		return this.idRubric;
	}

	public void setIdRubric(int idRubric) {
		this.idRubric = idRubric;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

}
