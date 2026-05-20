package in.guvi.task.mvc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequestDto {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String location;
}
