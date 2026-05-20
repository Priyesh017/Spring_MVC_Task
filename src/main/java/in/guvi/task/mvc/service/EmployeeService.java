package in.guvi.task.mvc.service;

import in.guvi.task.mvc.dto.EmployeeRequestDto;
import in.guvi.task.mvc.exception.ResourceNotFoundException;
import in.guvi.task.mvc.model.Employee;
import in.guvi.task.mvc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling Employee business logic.
 * This layer sits between the Controller and the Repository. It is responsible for
 * data transformation (DTO to Entity), validation, and executing database operations.
 */
@Service // Marks this class as a Spring Service component for classpath scanning and dependency injection
@RequiredArgsConstructor // Automatically creates a constructor to inject the final EmployeeRepository
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Retrieves all employees from the database.
     * * @return A List of all Employee entities.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves a single employee by their ID.
     * * @param id The unique ID of the employee.
     * @return The Employee entity.
     * @throws ResourceNotFoundException if the employee ID does not exist in the database.
     */
    public Employee getOneEmployee(String id) {
        return employeeRepository.findById(id)
                // Use functional programming (Optional) to throw our custom exception if no record is found
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Transforms an EmployeeRequestDto into an Employee entity and saves it to the database.
     * * @param requestDto The data transfer object containing the new employee's details.
     * @return The unique ID of the newly saved employee.
     */
    public String saveEmployee(EmployeeRequestDto requestDto) {
        // Use the Builder pattern to map the DTO to the MongoDB Entity
        Employee employee = Employee.builder()
                .employeeId(requestDto.getEmployeeId())
                .employeeName(requestDto.getEmployeeName())
                .employeeEmail(requestDto.getEmployeeEmail())
                .location(requestDto.getLocation())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        return savedEmployee.getEmployeeId();
    }

    /**
     * Handles bulk insertion by transforming a list of DTOs into a list of Entities.
     * * @param requestDtos A list of DTOs containing employee data.
     * @return A list of the generated/saved Employee IDs.
     */
    public List<String> saveAllEmployees(List<EmployeeRequestDto> requestDtos) {
        // Map over the list of DTOs and convert each one into an Employee Entity using Java Streams
        List<Employee> employees = requestDtos.stream()
                .map(dto -> Employee.builder()
                        .employeeId(dto.getEmployeeId())
                        .employeeName(dto.getEmployeeName())
                        .employeeEmail(dto.getEmployeeEmail())
                        .location(dto.getLocation())
                        .build())
                .toList();

        // Perform a bulk insert using saveAll, which is much faster than saving in a loop
        List<Employee> savedEmployees = employeeRepository.saveAll(employees);

        // Extract and return only the IDs of the saved employees
        return savedEmployees.stream()
                .map(Employee::getEmployeeId)
                .toList();
    }

    /**
     * Updates an existing employee. This method performs a partial update, meaning
     * it will only update fields that are actually provided in the request body.
     * * @param id The ID of the employee to update.
     * @param requestDto The DTO containing the fields to be updated.
     * @return The fully updated Employee entity.
     * @throws ResourceNotFoundException if the employee ID does not exist.
     */
    public Employee updateEmployee(String id, EmployeeRequestDto requestDto) {
        // Fetch the existing record. This will throw a ResourceNotFoundException if not found.
        Employee fetchEmployee = getOneEmployee(id);

        // Check each field. If it is not null and not blank, update the fetched entity.
        if (requestDto.getEmployeeName() != null && !requestDto.getEmployeeName().isBlank()) {
            fetchEmployee.setEmployeeName(requestDto.getEmployeeName());
        }

        if (requestDto.getEmployeeEmail() != null && !requestDto.getEmployeeEmail().isBlank()) {
            fetchEmployee.setEmployeeEmail(requestDto.getEmployeeEmail());
        }

        if (requestDto.getLocation() != null && !requestDto.getLocation().isBlank()) {
            fetchEmployee.setLocation(requestDto.getLocation());
        }

        // Save the updated entity back to the database
        return employeeRepository.save(fetchEmployee);
    }

    /**
     * Deletes an employee from the database.
     * * @param id The ID of the employee to delete.
     * @throws ResourceNotFoundException if the employee ID does not exist.
     */
    public void deleteEmployee(String id) {
        // Explicitly check if the ID exists first so we can return a meaningful 404 error
        // instead of allowing the database to throw a generic runtime exception.
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
    }
}