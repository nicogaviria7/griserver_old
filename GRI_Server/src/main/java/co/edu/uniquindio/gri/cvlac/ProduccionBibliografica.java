package co.edu.uniquindio.gri.cvlac;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import co.edu.uniquindio.gri.cvlac.Investigador;
import co.edu.uniquindio.gri.objects.Tipo;

@Entity(name = "PRODUCCIONBIBLIOGRAFICA")
@Table(name = "BIBLIOGRAFICAS", schema = "gri")
public class ProduccionBibliografica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Expose
	private long id;

	@Column(name = "IDENTIFICADOR", length = 400)
	@Expose
	private String identificador;

	@Column(name = "AUTORES", length = 2000)
	@Expose
	private String autores;

	@Column(name = "ANIO", length = 10)
	@Expose
	private String anio;

	@Column(name = "REFERENCIA", length = 4000)
	@Expose
	private String referencia;

	@Column(name = "REPETIDO", length = 10)
	@Expose
	private String repetido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_ID")
	private Tipo tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INVESTIGADORES_ID")
	private Investigador investigador;

	public ProduccionBibliografica(long id, String identificador, String autores, String anio, String referencia,
			Tipo tipo, String repetido, Investigador investigador) {
		this.id = id;
		this.identificador = identificador;
		this.autores = autores;
		this.anio = anio;
		this.referencia = referencia;
		this.tipo = tipo;
		this.repetido = repetido;
		this.investigador = investigador;
	}

	public ProduccionBibliografica() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getRepetido() {
		return repetido;
	}

	public void setRepetido(String repetido) {
		this.repetido = repetido;
	}

	public Investigador getInvestigador() {
		return investigador;
	}

	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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
		ProduccionBibliografica other = (ProduccionBibliografica) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
