package ru.sea.people.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.sea.people.dao.JPAEntityDAO;
import ru.sea.people.model.Person;

@Path("users")
public class PersonService {
	
	private JPAEntityDAO dao = new JPAEntityDAO();

    @GET
    @Path("list.json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> list() {
        return dao.getListPeople();
    }

    @PUT
    @Path("save.json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(List<Person> listPeople) {
    	for (Person person : listPeople) {
    		if (dao.validLogin(person)) {
    			dao.save(person);
    		};
    	}
    	return Response.status(200).entity(listPeople).build();
    }

    @POST
    @Path("delete.json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(Person person) {
    	dao.remove(person);
    	return Response.status(200).entity(person).build();
    }
	
    @POST
    @Path("add.json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Person person) {
		if (dao.validLogin(person)) {
			dao.insert(person);
		}
    	return Response.status(200).entity(person).build();
    }

}
