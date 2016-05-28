package ru.sea.people.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import ru.sea.people.model.Person;

public class JPAEntityDAO {
	
	private EntityManager entityManager = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

	public Person getById(Long id) {
		return entityManager.find(Person.class, id);
	}

	public List<Person> getListPeople() {
		return entityManager.createQuery("select p from Person p", Person.class).getResultList();
	}

	public void insert(Person person) {
		entityManager.getTransaction().begin();
		entityManager.persist(person);
        entityManager.getTransaction().commit();
	}

	public void save(Person person) {
		entityManager.getTransaction().begin();
		entityManager.merge(person);
		entityManager.flush();
        entityManager.getTransaction().commit();
	}

	public void remove(Person person) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.contains(person) ? person : entityManager.merge(person));
        entityManager.getTransaction().commit();
	}

	public boolean validLogin(Person person) {
		Long count = entityManager.createQuery("select count(p) from Person p where p.login = :login and p.id <> :id", Long.class).setParameter("login", person.getLogin()).setParameter("id", person.getId()).getSingleResult();
		return count.equals(0L);
	}
}
