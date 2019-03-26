package co.edu.uniquindio.gri.cvlac;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import co.edu.uniquindio.gri.objects.GruposInves;
import co.edu.uniquindio.gri.gruplac.Grupo;
import co.edu.uniquindio.gri.objects.LineasInvestigacion;

@Entity(name = "INVESTIGADORES")
@Table(name = "INVESTIGADORES", schema = "gri")
public class Investigador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@Expose
	private long id;

	@Column(name = "NOMBRE", length = 200)
	@Expose
	private String nombre;

	@Column(name = "CATEGORIA", length = 200)
	@Expose
	private String categoria;

	@Column(name = "NIVELACADEMICO", length = 200)
	@Expose
	private String nivelAcademico;
	

	@Column(name = "PERTENENCIA", length = 50)
	@Expose
	private String pertenencia;

	@OneToMany( mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	@Expose
	private List<Idiomas> idiomas = new ArrayList<Idiomas>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "INVEST_LINEAS", joinColumns = { @JoinColumn(name = "INVESTIGADORES_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LINEASINVESTIGACION_ID") }, schema = "gri")
	private List<LineasInvestigacion> lineasInvestigacion = new ArrayList<LineasInvestigacion>();

	@OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Produccion> producciones = new ArrayList<Produccion>();

	@OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProduccionBibliografica> produccionesBibliograficas = new ArrayList<ProduccionBibliografica>();

	@OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL)
	@Expose
	private List<GruposInves> grupos = new ArrayList<GruposInves>();

	public Investigador(long id, String nombre, String categoria, String nivelAcademico, List<Idiomas> idiomas,
			List<LineasInvestigacion> lineasInvestigacion, List<Produccion> producciones,
			List<ProduccionBibliografica> produccionesBibliograficas) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelAcademico = nivelAcademico;
		this.idiomas = idiomas;
		this.lineasInvestigacion = lineasInvestigacion;
		this.produccionesBibliograficas = produccionesBibliograficas;

	}
	
	

	



	public Investigador(long id, String nombre, String categoria, String nivelAcademico, String pertenencia) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelAcademico = nivelAcademico;
		this.pertenencia = pertenencia;
	}







	public Investigador() {
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNivelAcademico() {
		return nivelAcademico;
	}

	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public List<Idiomas> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Idiomas> idiomas) {
		this.idiomas = idiomas;
	}

	public List<Produccion> getProducciones() {
		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	public List<ProduccionBibliografica> getProduccionesBibliograficas() {
		return produccionesBibliograficas;
	}

	public void setProduccionesBibliograficas(List<ProduccionBibliografica> produccionesBibliograficas) {
		this.produccionesBibliograficas = produccionesBibliograficas;
	}

	public List<LineasInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(List<LineasInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}	

	public List<GruposInves> getGrupos() {
		return grupos;
	}


	public void setGrupos(List<GruposInves> grupos) {
		this.grupos = grupos;
	}


	public void removeLineasInvestigacion(LineasInvestigacion lineas) {
		lineasInvestigacion.remove(lineas);
		lineas.getInvestigadores().remove(this);
	}







	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigador other = (Investigador) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
