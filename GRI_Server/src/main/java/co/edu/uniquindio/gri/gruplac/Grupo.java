package co.edu.uniquindio.gri.gruplac;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import co.edu.uniquindio.gri.objects.GruposInves;
import co.edu.uniquindio.gri.cvlac.Investigador;
import co.edu.uniquindio.gri.objects.Centro;
import co.edu.uniquindio.gri.objects.LineasInvestigacion;
import co.edu.uniquindio.gri.objects.Programa;

@Entity(name = "GRUPOS")
@Table(name = "GRUPOS", schema = "gri")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 300)
	@Expose
	private long id;

	@Column(name = "NOMBRE", length = 300)
	@Expose
	private String nombre;

	@Column(name = "AREACONOCIMIENTO", length = 300)
	@Expose
	private String areaConocimiento;

	@Column(name = "ANIOFUNDACION", length = 300)
	@Expose
	private String anioFundacion;

	@Column(name = "LIDER", length = 300)
	@Expose
	private String lider;

	@Column(name = "CATEGORIA", length = 300)
	@Expose
	private String categoria;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "GRUPOS_LINEAS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LINEASINVESTIGACION_ID") }, schema = "gri")
	private List<LineasInvestigacion> lineasInvestigacion = new ArrayList<LineasInvestigacion>();

	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Produccion> produccion = new ArrayList<Produccion>();

	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProduccionBibliografica> produccionBibliografica = new ArrayList<ProduccionBibliografica>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CENTROS_ID")
	@Expose
	private Centro centro;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PROGRAMAS_GRUPOS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROGRAMAS_ID") }, schema = "gri")
	@Expose
	private List<Programa> programas = new ArrayList<Programa>();

	@OneToMany(mappedBy = "grupo", cascade = CascadeType.MERGE)
	private List<GruposInves> investigadores = new ArrayList<GruposInves>();

	public Grupo(long id, String nombre, String areaConocimiento, String anioFundacion, String lider, String categoria,
			List<LineasInvestigacion> lineasInvestigacion, List<Produccion> produccion,
			List<ProduccionBibliografica> produccionBibliografica, Centro centro) {
		this.id = id;
		this.nombre = nombre;
		this.areaConocimiento = areaConocimiento;
		this.anioFundacion = anioFundacion;
		this.lider = lider;
		this.categoria = categoria;
		this.lineasInvestigacion = lineasInvestigacion;
		this.produccion = produccion;
		this.produccionBibliografica = produccionBibliografica;
		this.centro = centro;
	}
	
	

	public Grupo(long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}



	public Grupo() {

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

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getAnioFundacion() {
		return anioFundacion;
	}

	public void setAnioFundacion(String anioFundacion) {
		this.anioFundacion = anioFundacion;
	}

	public String getLider() {
		return lider;
	}

	public void setLider(String lider) {
		this.lider = lider;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<LineasInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(List<LineasInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}

	public List<Produccion> getProduccion() {
		return produccion;
	}

	public void setProduccion(List<Produccion> produccion) {
		this.produccion = produccion;
	}

	public List<ProduccionBibliografica> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	public void setProduccionBibliografica(List<ProduccionBibliografica> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}	

	public List<GruposInves> getInvestigadores() {
		return investigadores;
	}

	public void setInvestigadores(List<GruposInves> investigadores) {
		this.investigadores = investigadores;
	}

	public void removeLineasInvestigacion(LineasInvestigacion lineas) {
		lineasInvestigacion.remove(lineas);
		lineas.getGrupos().remove(this);
	}

	public void removeInvestigador(Investigador investigador) {
		investigadores.remove(investigador);
		investigador.getGrupos().remove(this);
	}

	public void removePrograma(Programa programa) {
		programas.remove(programa);
		programa.getGrupos().remove(this);
	}
}