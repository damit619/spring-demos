package org.javatigers.jpa.tests;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.javatigers.jpa.model.Users;
import org.junit.Test;

public class UsersTests extends AbstractTest {

	private EntityManager em;
	
	@Test
	public void testCreateUsers() {
		Users user = getUser();
		em = createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.flush();
		em.getTransaction().commit();
		em.close();
		System.out.println("User ID : " + user.getId());
		assertNotNull(user.getId());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testStoredProcederTest() {
		em = createEntityManager();
		em.getTransaction().begin();
		StoredProcedureQuery spQuery = em.createNamedStoredProcedureQuery("users");
		spQuery.setParameter(2, "User1");
		List<Users> users = spQuery.getResultList();
		for (Users user : users) {
			System.out.println("UserId : " + user.getId() + "  FirstName : " + user.getFirstName() + "  DOB : "
					+ user.getDob().toString());
		}
	}

	private Users getUser() {
		return new Users("User1", "user", LocalDate.of(2002, 03, 22));
	}
}
