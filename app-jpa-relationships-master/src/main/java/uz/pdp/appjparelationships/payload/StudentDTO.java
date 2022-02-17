package uz.pdp.appjparelationships.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Sanjarbek Allayev, чт 21:59. 17.02.2022
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private String firstName;
    private String lastName;
    private Integer addressId;
    private Integer groupId;
    private List<Integer> subjectIds;
}
