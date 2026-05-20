package in.guvi.task.mvc.service;

import in.guvi.task.mvc.dto.EmployeeRequestDto;
import in.guvi.task.mvc.model.Employee;
import in.guvi.task.mvc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getOneEmployee(String id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public String saveEmployee(EmployeeRequestDto requestDto) {
        Employee employee = Employee.builder()
                .employeeId(requestDto.getEmployeeId())
                .employeeName(requestDto.getEmployeeName())
                .employeeEmail(requestDto.getEmployeeEmail())
                .location(requestDto.getLocation())
                .build();

        Employee saveEmployee = employeeRepository.save(employee);

        return saveEmployee.getEmployeeId();
    }

    public List<Employee> updateEmployee(String id, EmployeeRequestDto requestDto) {
        Employee fetchEmployee = getOneEmployee(id);

        if (requestDto.getEmployeeName() != null && !requestDto.getEmployeeName().isBlank()) {
            fetchEmployee.setEmployeeName(requestDto.getEmployeeName());
        }

        if (requestDto.getEmployeeEmail() != null && !requestDto.getEmployeeEmail().isBlank()) {
            fetchEmployee.setEmployeeEmail(requestDto.getEmployeeEmail());
        }

        if (requestDto.getLocation() != null && !requestDto.getLocation().isBlank()) {
            fetchEmployee.setLocation(requestDto.getLocation());
        }

        employeeRepository.save(fetchEmployee);

        return employeeRepository.findAll();
    }

    public List<Employee> deleteEmployee(String id) {

        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);

        return employeeRepository.findAll();
    }
}
