package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.entity.Subject;
import uz.pdp.appjparelationships.payload.StudentDTO;
import uz.pdp.appjparelationships.repository.AddressRepository;
import uz.pdp.appjparelationships.repository.GroupRepository;
import uz.pdp.appjparelationships.repository.StudentRepository;
import uz.pdp.appjparelationships.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

    //3. FACULTY DEKANAT
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_Id(facultyId, pageable);
        return studentPage;
    }


    //4. GROUP OWNER
    @GetMapping("/forGroup/{groupId}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer groupId,
                                                  @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Id(groupId, pageable);
        return studentPage;
    }



    @PostMapping("/add")
    public String addStudent(@RequestBody StudentDTO dto){
        Student student= new Student();


        Optional<Address> byId = addressRepository.findById(dto.getAddressId());
        if (!byId.isPresent()){
            return "Xatolik";
        }
        Address address = byId.get();
        Optional<Group> byId1 = groupRepository.findById(dto.getGroupId());
        if (!byId1.isPresent()){
            return "Xatolik";
        }
        Group group = byId1.get();

        List<Subject> allById = subjectRepository.findAllById(dto.getSubjectIds());
        if (allById.isEmpty()){
            return "Xatolik";
        }
        student.setAddress(address);
        student.setGroup(group);
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setSubjects(allById);

studentRepository.save(student);
return "Saqlandi";
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,@RequestBody StudentDTO dto){
        Optional<Student> byId2 = studentRepository.findById(id);
        if (!byId2.isPresent()){
            return "Xatolik";
        }
        Student student = byId2.get();

        Optional<Address> byId = addressRepository.findById(dto.getAddressId());
        if (!byId.isPresent()){
            return "Xatolik";
        }
        Address address = byId.get();
        Optional<Group> byId1 = groupRepository.findById(dto.getGroupId());
        if (!byId1.isPresent()){
            return "Xatolik";
        }
        Group group = byId1.get();

        List<Subject> allById = subjectRepository.findAllById(dto.getSubjectIds());
        if (allById.isEmpty()){
            return "Xatolik";
        }
        student.setAddress(address);
        student.setGroup(group);
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setSubjects(allById);

        studentRepository.save(student);
        return "O`zgartirildi";
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        studentRepository.deleteById(id);
        return "O`chirildi";
    }

}
