package in.guvi.task.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class representing an Employee document in the MongoDB database.
 * This class serves as the primary data model for the application's persistence layer.
 */
@Data // Generates getters, setters, toString(), equals(), and hashCode() methods automatically
@Builder // Enables the Builder design pattern for fluent and readable object instantiation
@NoArgsConstructor // Generates a no-argument constructor, required by Spring Data to instantiate the object from the database
@AllArgsConstructor // Generates an all-arguments constructor, required by the @Builder annotation
@Document(collection = "guvi_employee") // Maps this Java class to the "guvi_employee" collection in MongoDB
public class Employee {

    /**
     * The unique identifier for the employee document.
     * The @Id annotation tells Spring Data MongoDB to map this field to the document's primary key ("_id").
     */
    @Id
    private String employeeId;

    /**
     * The full name of the employee.
     */
    private String employeeName;

    /**
     * The official contact email of the employee.
     */
    private String employeeEmail;

    /**
     * The physical location, branch, or city where the employee is stationed.
     */
    private String location;
}