package in.guvi.task.mvc.controller;

import in.guvi.task.mvc.dto.EmployeeRequestDto;
import in.guvi.task.mvc.model.Employee;
import in.guvi.task.mvc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Employee operations.
 * Exposes endpoints for CRUD operations and bulk insertions.
 */
@RestController // Combines @Controller and @ResponseBody, ensuring responses are serialized directly to JSON
@RequiredArgsConstructor // Automatically generates a constructor for dependency injection of final fields
@RequestMapping("/employees") // Base URI for all endpoints in this controller
@CrossOrigin(origins = "*") // Allows cross-origin requests from the frontend (CORS bypass)
public class EmployeeController {

    // Injected via constructor by Lombok's @RequiredArgsConstructor
    private final EmployeeService employeeService;

    /**
     * Retrieves a list of all employees in the database.
     *
     * @return ResponseEntity containing a List of Employees and an HTTP 200 (OK) status.
     */
    @GetMapping("/displayAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Retrieves a single employee by their unique ID.
     *
     * @param id The unique identifier of the employee.
     * @return ResponseEntity containing the Employee object and an HTTP 200 (OK) status.
     */
    @GetMapping("/display/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getOneEmployee(id));
    }

    /**
     * Creates a new employee record in the database.
     *
     * @param requestDto The DTO containing the employee details from the request body.
     * @return ResponseEntity containing the saved Employee's ID and an HTTP 201 (CREATED) status.
     */
    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(requestDto));
    }

    /**
     * Handles bulk insertion of multiple employees at once.
     *
     * @param requestDtos A List of Employee DTOs to be saved.
     * @return ResponseEntity containing a List of the newly generated Employee IDs and an HTTP 201 (CREATED) status.
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<String>> saveAllEmployees(@RequestBody List<EmployeeRequestDto> requestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveAllEmployees(requestDtos));
    }

    /**
     * Updates an existing employee's details.
     *
     * @param id The ID of the employee to update.
     * @param requestDto The DTO containing the updated fields.
     * @return ResponseEntity containing the updated Employee object and an HTTP 200 (OK) status.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    /**
     * Deletes an employee from the database by their ID.
     *
     * @param id The ID of the employee to delete.
     * @return ResponseEntity containing a success message and an HTTP 200 (OK) status.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with ID " + id + " was successfully deleted.");
    }
}