package co.edu.uniquindio.gri.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity(name = "TIPOPRODUCCION")
@Table(name = "TIPOPRODUCCION", schema = "gri")
public class TipoProduccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@Expose
	private long id;

	@Column(name = "NOMBRE", length = 100)
	@Expose
	private String nombre;

	@OneToMany(mappedBy = "tipoProduccion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tipo> tipos = new ArrayList<Tipo>();

	public TipoProduccion() {
	}

	public TipoProduccion(long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}
	

}
