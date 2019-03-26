package co.edu.uniquindio.gri.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import co.edu.uniquindio.gri.objects.TipoProduccion;
import co.edu.uniquindio.gri.cvlac.Produccion;
import co.edu.uniquindio.gri.cvlac.ProduccionBibliografica;

@Entity(name = "TIPOS")
@Table(name = "TIPOS", schema = "gri")
public class Tipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@Expose
	private long id;

	@Column(name = "NOMBRE", length = 100)
	@Expose
	private String nombre;

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Produccion> produccion = new ArrayList<Produccion>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProduccionBibliografica> produccionBibliografica = new ArrayList<ProduccionBibliografica>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<co.edu.uniquindio.gri.gruplac.Produccion> produccionG = new ArrayList<co.edu.uniquindio.gri.gruplac.Produccion>();

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> produccionBibliograficaG = new ArrayList<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica>();

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPOPRODUCCION_ID")
	@Expose
	private TipoProduccion tipoProduccion;
	
	public Tipo() {
	}

	public Tipo(long id, String nombre) {
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

	public List<Produccion> getProduccion() {
		return produccion;
	}

	public void setProduccion(List<Produccion> produccion) {
		this.produccion = produccion;
	}

	public List<co.edu.uniquindio.gri.gruplac.Produccion> getProduccionG() {
		return produccionG;
	}

	public void setProduccionG(List<co.edu.uniquindio.gri.gruplac.Produccion> produccionG) {
		this.produccionG = produccionG;
	}

	public List<ProduccionBibliografica> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	public void setProduccionBibliografica(List<ProduccionBibliografica> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	public List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> getProduccionBibliograficaG() {
		return produccionBibliograficaG;
	}

	public void setProduccionBibliograficaG(
			List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> produccionBibliograficaG) {
		this.produccionBibliograficaG = produccionBibliograficaG;
	}

	public TipoProduccion getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(TipoProduccion tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}
	
	

}
