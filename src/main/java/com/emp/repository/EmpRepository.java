
package com.emp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.model.EmpModel;

public interface EmpRepository extends JpaRepository<EmpModel,Integer> {
	Optional<EmpModel>findByimgName(String imgName);
	Optional<EmpModel>findByEmpId(Long empId);
	
}
