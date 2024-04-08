package com.emp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.emp.model.EmpModel;

public interface EmpService {

    String uploadEmployee(Long empId, String empName, String empDesignation, String empDepartment,
                          String email, LocalDate dateOfBirth, String filePath, MultipartFile file) throws IOException;

    String getImagePath(Long empId);

    List<EmpModel> getAllEmployee();

    boolean sentSampleMail(Long empId, String message);

    boolean sentSampleMail(String to, String message);
}
