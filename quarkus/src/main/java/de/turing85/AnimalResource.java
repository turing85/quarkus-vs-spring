package de.turing85;

import java.util.List;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
