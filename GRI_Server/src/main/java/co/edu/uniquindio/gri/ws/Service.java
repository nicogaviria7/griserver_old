package co.edu.uniquindio.gri.ws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.edu.uniquindio.gri.control.Recurso;
import co.edu.uniquindio.gri.cvlac.Investigador;
import co.edu.uniquindio.gri.gruplac.Grupo;
import co.edu.uniquindio.gri.gruplac.Produccion;
import co.edu.uniquindio.gri.gruplac.ProduccionBibliografica;
import co.edu.uniquindio.gri.objects.Centro;
import co.edu.uniquindio.gri.objects.Facultad;
import co.edu.uniquindio.gri.objects.Programa;
import co.edu.uniquindio.gri.objects.Tipo;

@Path("/service")
public class Service {

	@Inject
	private EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response getStatus() {
		return Response.ok("{\"status\":\"El servicio está corriendo\"}").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("investigadores")
	public Response getInvestigadores() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Investigador> facs = em
				.createQuery("select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) FROM co.edu.uniquindio.gri.cvlac.Investigador i join i.grupos g where g.estado = 'ACTUAL'").getResultList();
	
		respuesta = ObjetToJSON(facs);
		em.close();

		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("investigadores/{id}")
	public Response getInvestigador(@PathParam("id") long idInvestigador) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Investigador investigador = em.find(Investigador.class, idInvestigador);
			if (investigador == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"El investigador con el id " + idInvestigador + " no fue encontrado."
						+ "}";
				return Response.status(401).entity(respuesta).build();
			}
			Hibernate.initialize(investigador.getIdiomas());
			Hibernate.initialize(investigador.getGrupos());
			em.close();
			respuesta = ObjetToJSON(investigador);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("integrantes/{type}/{id}")
	public Response getIntegrantes(@PathParam("type") String type ,@PathParam("id") long idEntidad) {
		String respuesta = null;
		em = Recurso.getEntityManager();
		
		if(type.equals("g")){ 
		List<Investigador> integrantes = em.createQuery(
				"select NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.gruplac.Grupo g join g.investigadores gi join gi.investigador i where g.id ="
						+ idEntidad + " and gi.estado ='ACTUAL'")
				.getResultList();

		respuesta = ObjetToJSON(integrantes);
		}else if(type.equals("p")){
			List<Investigador> integrantes = em.createQuery(
					"select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.objects.Programa p join p.grupos g join g.investigadores gi join gi.investigador i where p.id ="
							+ idEntidad + " and gi.estado ='ACTUAL'")
					.getResultList();

			respuesta = ObjetToJSON(integrantes);
		}else if(type.equals("c")) {
			List<Investigador> integrantes = em.createQuery(
					"select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.objects.Centro c join c.grupo g join g.investigadores gi join gi.investigador i where c.id ="
							+ idEntidad + " and gi.estado ='ACTUAL'")
					.getResultList();

			respuesta = ObjetToJSON(integrantes);
	
		}else if (type.equals("f")){
			List<Investigador> integrantes = em.createQuery(
					"select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.objects.Facultad f join f.programas p join p.grupos g join g.investigadores gi join gi.investigador i where f.id ="
							+ idEntidad + " and gi.estado ='ACTUAL'")
					.getResultList();
			
			List<Investigador> integrantesc = em.createQuery(
					"select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.objects.Facultad f join f.centros c join c.grupo g join g.investigadores gi join gi.investigador i where f.id ="
							+ idEntidad + " and gi.estado ='ACTUAL'")
					.getResultList();
			
			for(Investigador integrante: integrantesc){
				if(!integrantes.contains(integrante))
					integrantes.add(integrante);
			}
			
			respuesta = ObjetToJSON(integrantes);
			
		} else if(type.equals("u")){
			List<Investigador> integrantes = em.createQuery(
					"select distinct NEW co.edu.uniquindio.gri.cvlac.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) FROM co.edu.uniquindio.gri.cvlac.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
					.getResultList();

			respuesta = ObjetToJSON(integrantes);
		}
		
		em.close();
		return Response.ok(respuesta).build();

	}

	//TODO
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("grupos")
	public Response getGrupos() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Grupo> grps = em.createQuery("FROM co.edu.uniquindio.gri.gruplac.Grupo").getResultList();
		respuesta = ObjetToJSON(grps);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("grupo/{id}")
	public Response getGrupo(@PathParam("id") long idGrupo) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Grupo grupo = em.find(Grupo.class, idGrupo);
			if (grupo == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"El grupo con el id " + idGrupo + " no fue encontrado." + "}";
				return Response.status(401).entity(respuesta).build();
			}
			em.close();
			respuesta = ObjetToJSON(grupo);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("grupos/{id}")
	public Response getGruposPrograma(@PathParam("id") long idFacultad) {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Grupo> grps = em.createQuery("FROM co.edu.uniquindio.gri.gruplac.Grupo g join g.programas p where p.id ="+idFacultad).getResultList();
		respuesta = ObjetToJSON(grps);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("facultades")
	public Response getFacultades() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Facultad> facs = em.createQuery("FROM co.edu.uniquindio.gri.objects.Facultad  WHERE id<>0")
				.getResultList();

		respuesta = ObjetToJSON(facs);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("facultad/{id}")
	public Response getFacultad(@PathParam("id") long idFacultad) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Facultad facultad = em.find(Facultad.class, idFacultad);
			if (facultad == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"La facultad con el id " + idFacultad + " no fue encontrada." + "}";
				return Response.status(401).entity(respuesta).build();
			}
			em.close();
			respuesta = ObjetToJSON(facultad);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("programas")
	public Response getProgramas() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Programa> progr = em.createQuery("FROM co.edu.uniquindio.gri.objects.Programa WHERE id<>0")
				.getResultList();

		respuesta = ObjetToJSON(progr);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("programa/{id}")
	public Response getPrograma(@PathParam("id") long idPrograma) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Programa programa = em.find(Programa.class, idPrograma);
			if (programa == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"El programa con el id " + idPrograma + " no fue encontrado." + "}";
				return Response.status(401).entity(respuesta).build();
			}
			em.close();
			respuesta = ObjetToJSON(programa);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("programas/{id}")
	public Response getProgramasFacultad(@PathParam("id") long idFacultad) {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Programa> progr = em.createQuery("FROM co.edu.uniquindio.gri.objects.Programa WHERE id<>0 and facultad ="+idFacultad)
				.getResultList();

		respuesta = ObjetToJSON(progr);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("centros")
	public Response getCentros() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Centro> centros = em.createQuery("FROM co.edu.uniquindio.gri.objects.Centro WHERE id<>0").getResultList();

		respuesta = ObjetToJSON(centros);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("centro/{id}")
	public Response getCentro(@PathParam("id") long idCentros) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Centro centro = em.find(Centro.class, idCentros);
			if (centro == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"El centro con el id " + idCentros + " no fue encontrado." + "}";
				return Response.status(401).entity(respuesta).build();
			}
			em.close();
			respuesta = ObjetToJSON(centro);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("centros/{id}")
	public Response getGruposCentros(@PathParam("id") long idCentro) {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Grupo> centros = em.createQuery("FROM co.edu.uniquindio.gri.gruplac.Grupo g WHERE g.centro = "+idCentro).getResultList();

		respuesta = ObjetToJSON(centros);
		em.close();
		return Response.ok(respuesta).build();

	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stats")
	public Response getStats() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		long cantInves = (long) em
				.createQuery("select count(distinct i.id) from co.edu.uniquindio.gri.cvlac.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
				.getSingleResult();
		long cantGrupos = (long) em.createQuery("select count(id) from co.edu.uniquindio.gri.gruplac.Grupo ")
				.getSingleResult();
		long cantCentros = (long) em
				.createQuery("select count(id) from co.edu.uniquindio.gri.objects.Centro  where id <> 0")
				.getSingleResult();

		List<Long> stats = new ArrayList<Long>();
		stats.add(cantInves);
		stats.add(cantGrupos);
		stats.add(cantCentros);

		respuesta = ObjetToJSON(stats);
		em.close();
		return Response.ok(respuesta).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("tipos")
	public Response getTipos() {
		String respuesta = null;
		em = Recurso.getEntityManager();
		List<Tipo> tipos = em.createQuery("FROM co.edu.uniquindio.gri.objects.Tipo").getResultList();
		respuesta = ObjetToJSON(tipos);
		em.close();
		return Response.ok(respuesta).build();

	}

	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("tipos/{id}")
	public Response getTipo(@PathParam("id") long idTipo) {
		String respuesta = null;
		try {
			em = Recurso.getEntityManager();
			Tipo tipo = em.find(Tipo.class, idTipo);
			if (tipo == null) {
				respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"El centro con el id " + idTipo + " no fue encontrado." + "}";
				return Response.status(401).entity(respuesta).build();
			}
			em.close();
			respuesta = ObjetToJSON(tipo);
		} catch (Exception err) {
			respuesta = "{\"status\":\"401\"," + "\"message\":\"No content found \"" + "\"developerMessage\":\""
					+ err.getMessage() + "\"" + "}";
			return Response.status(401).entity(respuesta).build();
		}
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();

	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("producciones/{type}/{id}/{tipo}")
	public Response getProducciones(@PathParam("type") String type, @PathParam("id") long idEntity,
			@PathParam("tipo") long idTipo) {
		String respuesta = null;
		em = Recurso.getEntityManager();
		Tipo tipo = em.find(Tipo.class, idTipo);
		long idTipoProd = tipo.getTipoProduccion().getId();

		if (type.equals("i")) {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.cvlac.ProduccionBibliografica> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.cvlac.ProduccionBibliografica where investigador ="
								+ idEntity + "and tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.cvlac.Produccion> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.cvlac.Produccion where investigador =" + idEntity
								+ "and (tipo = 1 or tipo = 41 or tipo = 42 or tipo = 43)")
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.cvlac.Produccion> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.cvlac.Produccion where investigador =" + idEntity
								+ "and tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			}
		} else if (type.equals("g")) {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.gruplac.ProduccionBibliografica where grupo ="
								+ idEntity + "and tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.gruplac.Produccion where grupo =" + idEntity
								+ "and (tipo = 1 or tipo = 41 or tipo = 42 or tipo = 43)")
						.getResultList();
			
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em
						.createQuery("FROM co.edu.uniquindio.gri.gruplac.Produccion where grupo =" + idEntity
								+ "and tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			}
		} else if (type.equals("f")) {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> producciones = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.ProduccionBibliografica(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.gruplac.ProduccionBibliografica pb join pb.grupo g join g.programas p join p.facultad f where f.id = "
										+ idEntity + " and pb.tipo = " + idTipo)
						.getResultList();
				
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> produccionesc = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.ProduccionBibliografica(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.gruplac.ProduccionBibliografica pb join pb.grupo g join g.centro c join c.facultad f where f.id = "
										+ idEntity + " and pb.tipo = " + idTipo)
						.getResultList();
				
				for(ProduccionBibliografica produccion : produccionesc){
					if(!producciones.contains(produccion)){					
						producciones.add(produccion);
					}
				}
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.programas p join p.facultad f where f.id = "
										+ idEntity + " and (pr.tipo =  1 or pr.tipo = 41 or pr.tipo = 42 or pr.tipo = 43)")
						.getResultList();
				
				List<co.edu.uniquindio.gri.gruplac.Produccion> produccionesc = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.centro c join c.facultad f where f.id = "
										+ idEntity + " and (pr.tipo =  1 or pr.tipo = 41 or pr.tipo = 42 or pr.tipo = 43)")
						.getResultList();
				
				for(Produccion produccion : produccionesc){
					if(!producciones.contains(produccion)){
						producciones.add(produccion);
					}
				}
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.programas p join p.facultad f where f.id = "
										+ idEntity+ " and pr.tipo = " + idTipo)
						.getResultList();
				
				List<co.edu.uniquindio.gri.gruplac.Produccion> produccionesc = em
						.createQuery(
								"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.centro c join c.facultad f where f.id = "
										+ idEntity + " and pr.tipo = " + idTipo)
						.getResultList();
				
				for(Produccion produccion : produccionesc){
					if(!producciones.contains(produccion)){
						producciones.add(produccion);
					}
				}
				respuesta = ObjetToJSON(producciones);
			}
		} else if (type.equals("p")) {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.ProduccionBibliografica(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.gruplac.ProduccionBibliografica pb join pb.grupo g join g.programas p  where p.id = "
								+ idEntity + " and pb.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.programas p where p.id = "
								+ idEntity + " and (pr.tipo = 1 or pr.tipo = 41 or pr.tipo = 42 or pr.tipo = 43)")
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.programas p where p.id = "
								+ idEntity + " and pr.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			}
		} else if (type.equals("c")) {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.ProduccionBibliografica(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.gruplac.ProduccionBibliografica pb join pb.grupo g join g.centro c where c.id = "
								+ idEntity + "  and pb.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.centro c where c.id = "
								+ idEntity + " and (pr.tipo = 1 or pr.tipo = 41 or pr.tipo = 42 or pr.tipo = 43)")
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr join pr.grupo g join g.centro c where c.id = "
								+ idEntity + " and pr.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			}
		} else {
			if (idTipoProd == 3) {
				List<co.edu.uniquindio.gri.gruplac.ProduccionBibliografica> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.ProduccionBibliografica(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.gruplac.ProduccionBibliografica pb where pb.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else if (idTipo == 1) {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr where pr.tipo = 1 or pr.tipo = 41 or pr.tipo = 42 or pr.tipo = 43")
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			} else {
				List<co.edu.uniquindio.gri.gruplac.Produccion> producciones = em.createQuery(
						"SELECT NEW co.edu.uniquindio.gri.gruplac.Produccion(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.gruplac.Produccion pr where pr.tipo = " + idTipo)
						.getResultList();
				respuesta = ObjetToJSON(producciones);
			}
		}

		em.close();
		return Response.ok(respuesta).build();

	}

	public String ObjetToJSON(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(object);
	}

}