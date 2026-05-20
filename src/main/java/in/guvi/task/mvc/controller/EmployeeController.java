package in.guvi.task.mvc.controller;

import in.guvi.task.mvc.dto.EmployeeRequestDto;
import in.guvi.task.mvc.model.Employee;
import in.guvi.task.mvc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/displayAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getOneEmployee(id));
    }

    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(requestDto));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<String>> saveAllEmployees(@RequestBody List<EmployeeRequestDto> requestDtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveAllEmployees(requestDtos));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with ID " + id + " was successfully deleted.");
    }
}