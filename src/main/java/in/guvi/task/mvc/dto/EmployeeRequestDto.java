package in.guvi.task.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Employee requests.
 * Used to transfer data between the client (frontend/Postman) and the controller layer.
 * This separates the API representation of an Employee from the internal MongoDB Entity.
 */
@Data // Generates getters, setters, toString(), equals(), and hashCode() methods automatically
@Builder // Implements the Builder design pattern for clean and flexible object creation
@NoArgsConstructor // Required by Jackson library to deserialize incoming JSON payloads into this Java object
@AllArgsConstructor // Required by @Builder when @NoArgsConstructor is explicitly declared
public class EmployeeRequestDto {

    /**
     * The unique identifier for the employee.
     * Can be provided by the client or generated dynamically.
     */
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
     * The current branch or city where the employee is based.
     */
    private String location;
}