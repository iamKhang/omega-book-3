package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Test {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_MSSQL_OMEGABOOK3");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			tr.commit();
			System.out.println("Da tao database");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
//		Supplier_DAO supplier_DAO = new Supplier_DAO();
//		System.out.println(supplier_DAO.getAll());
	}

}
