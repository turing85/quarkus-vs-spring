package de.turing85;

import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Path("animals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnimalResource {
  private final AnimalRepository repository;

  @GET
  public List<Animal> listAll() {
    return repository.listAll();
  }

  @GET
  @Path("{id}")
  public Animal getById(@PathParam("id") long id) {
    return repository.findById(id);
  }

  @POST
  @Transactional
  public Animal create(Animal animal) {
    repository.persist(animal);
    return animal;
  }
}
