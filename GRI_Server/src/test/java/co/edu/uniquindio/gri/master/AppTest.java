package co.edu.uniquindio.gri.master;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.edu.uniquindio.gri.cvlac.Produccion;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private static EntityManager manager;

	private static EntityManagerFactory emf;

	public static void main(String[] args) {

		emf = Persistence.createEntityManagerFactory("Persistencia");
		manager = emf.createEntityManager();

//		@SuppressWarnings("unchecked")
//		List<Grupo> test = (ArrayList<Grupo>) manager.createQuery("FROM GRUPOS").getResultList();

//		ProduccionBibliografica p= new ProduccionBibliografica();
//		p.setIdentificador("test1");
//		p.setAutores("test1");
//		p.setAnio("test1");
//		p.setReferencia("test1");
//		p.setTipo("test1");
//		p.setRepetido("test1");
//		p.setGrupo(test.get(1));
		
		// Empresa e = new Empresa();
		// e.setId(1L);
		// e.setNombre("Fulano");
		//
//		 manager.getTransaction().begin();
//		
//		 manager.persist(p);
//		
//		 manager.getTransaction().commit();
		//
		imprimir();
	}

	private static void imprimir() {
		@SuppressWarnings("unchecked")
		List<Produccion> test = (ArrayList<Produccion>) manager.createQuery("FROM co.edu.uniquindio.gri.cvlac.Produccion").getResultList();
		for (int i = 0; i < test.size(); i++) {
			Produccion aux = test.get(i);
			System.out.println(i + 1 + ") " + aux.getReferencia());
		}
	}
}
