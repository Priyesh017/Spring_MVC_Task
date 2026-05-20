package in.guvi.task.mvc.repository;

import in.guvi.task.mvc.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Layer (Repository) for the Employee entity.
 * This interface acts as a bridge between the application and the MongoDB database.
 *
 * By extending MongoRepository, Spring Data automatically provides the implementation
 * for standard database operations (CRUD, pagination, and sorting) at runtime,
 * eliminating the need to write boilerplate database queries.
 */
@Repository // Marks this interface as a Spring bean and enables database exception translation
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    // No code needs to be written here!
    // MongoRepository<Employee, String> tells Spring Data that this repository
    // manages the "Employee" document, and its primary key (@Id) is of type "String".
    //
    // Built-in methods like save(), findById(), findAll(), and deleteById()
    // are automatically inherited and ready to use in the Service layer.
}