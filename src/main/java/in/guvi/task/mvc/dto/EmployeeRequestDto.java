package in.guvi.task.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String location;
}