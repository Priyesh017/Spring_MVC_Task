package in.guvi.task.mvc.controller;

import in.guvi.task.mvc.dto.EmployeeRequestDto;
import in.guvi.task.mvc.model.Employee;
import in.guvi.task.mvc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
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

    @PostMapping()
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.saveEmployee(requestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<Employee>> updateEmployee(@PathVariable String id, EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Employee>> deleteEmployee(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
