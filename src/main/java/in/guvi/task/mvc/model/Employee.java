package in.guvi.task.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "guvi_employee")
public class Employee {

    @Id
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String location;
}
