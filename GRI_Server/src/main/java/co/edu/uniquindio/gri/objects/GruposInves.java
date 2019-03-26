package co.edu.uniquindio.gri.objects;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import co.edu.uniquindio.gri.cvlac.Investigador;
import co.edu.uniquindio.gri.gruplac.Grupo;
 
@Entity(name = "GRUPOS_INVES")
@Table(name = "GRUPOS_INVES", schema = "gri")
public class GruposInves implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "GRUPOS_ID")
	@Expose 
	private Grupo grupo;
 
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "INVESTIGADORES_ID")
    private Investigador investigador;
    
    @Column(name = "ESTADO", length = 50)
	private String estado;
 
	public GruposInves() {
		super();
	}

	public GruposInves(Grupo grupo, Investigador investigador, String estado) {
		this.grupo = grupo;
		this.investigador = investigador;
		this.estado = estado;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    	
}
