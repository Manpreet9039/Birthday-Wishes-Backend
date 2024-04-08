//package com.emp.service;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import com.emp.email.EmailUtils;
//import com.emp.model.EmpModel;
//import com.emp.repository.EmpRepository;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.Base64;
//
//
//
//@Service
//public class EmpServiceImpl {
//
//    private final String storageDirectoryPath = "D:\\birthday";
//
//    @Autowired
//    private EmpRepository empRepository;
//    
//    /**
//     * 
//     * @param empId
//     * @param empName
//     * @param empDesignation
//     * @param empDepartment
//     * @param email
//     * @param dateOfBirth
//     * @param filePath
//     * @param file
//     * @return
//     * @throws IOException
//     */
//
//    public String uploadEmployee(Long empId, String empName, String empDesignation, String empDepartment,
//                                 String email, LocalDate dateOfBirth, String filePath, MultipartFile file) throws IOException {
//        // Set file name and path
//        String fileName = file.getOriginalFilename();
//
//        // Initialize savedEmp
//        EmpModel savedEmp = new EmpModel();
//
//        Path storageDirectory = Paths.get(storageDirectoryPath);
//        Path absolutePath = storageDirectory.toAbsolutePath();
//
//        if (!Files.exists(absolutePath)) {
//            //System.out.println("First time creating directory");
//            //System.out.println(absolutePath);
//            Files.createDirectories(storageDirectory); // Create the directory in the given storage directory path
//        }
//
//        Path destination = Paths.get(storageDirectory + "\\" + fileName);
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//
//        final int rootLength = absolutePath.toString().length();
//        final String absFileName = destination.toString();
//        final String relFileName = absFileName.substring(2);
//
//        // Set employee details
//        savedEmp.setEmpId(empId);
//        savedEmp.setEmpName(empName);
//        savedEmp.setEmpDesignation(empDesignation);
//        savedEmp.setEmpDepartment(empDepartment);
//        savedEmp.setEmail(email);
//        savedEmp.setDateOfBirth(dateOfBirth);
//        savedEmp.setImgName(fileName);
//        savedEmp.setFilePath(relFileName);
//
//        // Save employee details and image path
//        empRepository.save(savedEmp);
//
//        return "Employee details and image path uploaded successfully. Image Path: " + relFileName;
//    }
//    
//    
//    /**
//     * 
//     * @param empId
//     * @return
//     */
//    
//    public String getImagePath(Long empId) {
//        Optional<EmpModel> employee = empRepository.findByempId(empId);
//
//        if (employee.isPresent()) {
//            String imagePath = employee.get().getFilePath();
//            return imagePath;
//        } else {
//            return null; // or throw an exception, depending on your error handling strategy
//        }
//    }
//
//
//    /**
//     * 
//     * @return
//     */
//    
//    public List<EmpModel> getAllEmployee() {
//        List<EmpModel> work = empRepository.findAll();        
//        List<EmpModel> newWork = new ArrayList<>();
//        for (EmpModel i : work) {
//            try {
//            	EmpModel birthday = new EmpModel();
//                String img = i.getImgName();
//
//                // Construct the file path
//                Path filePath = Paths.get(storageDirectoryPath, img);
//
//                // Read the file content into a byte array
//                byte[] fileBytes = Files.readAllBytes(filePath);
//                
//                // Convert byte array to Base64-encoded string
//                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
//
//                // Set the properties in the new WorkAnniversaryModel
//                birthday.setId(i.getId());
//                birthday.setEmpId(i.getEmpId());
//                birthday.setEmpName(i.getEmpName());
//                birthday.setEmpDesignation(i.getEmpDesignation());
//                birthday.setEmpDepartment(i.getEmpDepartment());
//                birthday.setEmail(i.getEmail());
//                birthday.setDateOfBirth(i.getDateOfBirth());
//                birthday.setImgName(base64Image); // Set the Base64-encoded string
//                birthday.setFilePath(i.getFilePath());
//                
//                newWork.add(birthday);
//                // Do something with birthday or add it to a list if needed
//            } catch (IOException e) {
//                // Handle the exception
//                e.printStackTrace();
//            }
//        }
//        // Return the list or do something else with the results
//        return newWork;
//    }
//    
//		
//		@Autowired
//		private EmailUtils emailUtils;
//		
//		/**
//		 * 
//		 * @param empId
//		 * @param message
//		 * @return
//		 */
//		
//		public boolean sentSampleMail(Long empId, String message) {
//		    Optional<EmpModel> workOptional = empRepository.findByempId(empId);
//		    if (workOptional.isPresent()) {
//		        String to = workOptional.get().getEmail();
//		        return sentSampleMail(to, message);
//		    }
//		    return false;
//		}
//		
//		
//		/**
//		 * 
//		 * @param to
//		 * @param message
//		 * @return
//		 */
//		
//		public boolean sentSampleMail(String to, String message) {
//		    String subject = "Birthday Wish";
//		    String body = "Dear " + to + ",\n\n" + message;
//		    try {
//		        emailUtils.sendEmail(to, subject, body);
//		        return true;
//		    } catch (Exception e) {
//		        // Log the exception or handle it as needed
//		        return false;
//		    }
//		}
//
//
//
//
//
//	
//	}
//	
//	
//	
//


package com.emp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.emp.email.EmailUtils;
import com.emp.model.EmpModel;
import com.emp.repository.EmpRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class EmpServiceImpl implements EmpService {

    private final String storageDirectoryPath = "D:\\birthday";

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EmailUtils emailUtils;

    /**
     * Uploads employee details and image to the storage directory.
     * 
     * @param empId          - The ID of the employee
     * @param empName        - The name of the employee
     * @param empDesignation - The designation of the employee
     * @param empDepartment  - The department of the employee
     * @param email          - The email address of the employee
     * @param dateOfBirth    - The date of birth of the employee
     * @param filePath       - The file path where the image is stored
     * @param file           - The image file
     * @return               - A success message indicating the uploaded details and image path
     * @throws IOException   - If an I/O error occurs while copying the file
     */
    @Override
    public String uploadEmployee(Long empId, String empName, String empDesignation, String empDepartment,
            String email, LocalDate dateOfBirth, String filePath, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        EmpModel savedEmp = new EmpModel();
        Path storageDirectory = Paths.get(storageDirectoryPath);
        Path absolutePath = storageDirectory.toAbsolutePath();

        if (!Files.exists(absolutePath)) {
            Files.createDirectories(storageDirectory);
        }

        Path destination = Paths.get(storageDirectory + "\\" + fileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        final int rootLength = absolutePath.toString().length();
        final String absFileName = destination.toString();
        final String relFileName = absFileName.substring(rootLength + 1);

        savedEmp.setEmpId(empId);
        savedEmp.setEmpName(empName);
        savedEmp.setEmpDesignation(empDesignation);
        savedEmp.setEmpDepartment(empDepartment);
        savedEmp.setEmail(email);
        savedEmp.setDateOfBirth(dateOfBirth);
        savedEmp.setImgName(fileName);
        savedEmp.setFilePath(relFileName);

        empRepository.save(savedEmp);

        return "Employee details and image path uploaded successfully. Image Path: " + relFileName;
    }

    /**
     * Retrieves the image path for the given employee ID.
     * 
     * @param empId - The ID of the employee
     * @return      - The image path of the employee if found, otherwise null
     */
    @Override
    public String getImagePath(Long empId) {
        Optional<EmpModel> employee = empRepository.findByEmpId(empId);

        if (employee.isPresent()) {
            return employee.get().getFilePath();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the details of all employees along with their encoded images.
     * 
     * @return - A list of employee details with encoded images
     */
    @Override
    public List<EmpModel> getAllEmployee() {
        List<EmpModel> work = empRepository.findAll();
        List<EmpModel> newWork = new ArrayList<>();

        for (EmpModel i : work) {
            try {
                EmpModel birthday = new EmpModel();
                String img = i.getImgName();
                Path filePath = Paths.get(storageDirectoryPath, img);
                byte[] fileBytes = Files.readAllBytes(filePath);
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);

                birthday.setId(i.getId());
                birthday.setEmpId(i.getEmpId());
                birthday.setEmpName(i.getEmpName());
                birthday.setEmpDesignation(i.getEmpDesignation());
                birthday.setEmpDepartment(i.getEmpDepartment());
                birthday.setEmail(i.getEmail());
                birthday.setDateOfBirth(i.getDateOfBirth());
                birthday.setImgName(base64Image);
                birthday.setFilePath(i.getFilePath());

                newWork.add(birthday);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newWork;
    }

    /**
     * Sends a sample birthday wish email to the employee with the specified ID.
     * 
     * @param empId   - The ID of the employee
     * @param message - The message to be included in the email
     * @return        - True if the email is sent successfully, otherwise false
     */
    @Override
    public boolean sentSampleMail(Long empId, String message) {
        Optional<EmpModel> workOptional = empRepository.findByEmpId(empId);
        if (workOptional.isPresent()) {
            String to = workOptional.get().getEmail();
            return sentSampleMail(to, message);
        }
        return false;
    }

    /**
     * Sends a sample birthday wish email to the specified email address.
     * 
     * @param to      - The email address of the recipient
     * @param message - The message to be included in the email
     * @return        - True if the email is sent successfully, otherwise false
     */
    @Override
    public boolean sentSampleMail(String to, String message) {
        String subject = "Birthday Wish";
        String body = "Dear " + to + ",\n\n" + message;
        try {
            emailUtils.sendEmail(to, subject, body);
            return true;
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return false;
        }
    }
}
