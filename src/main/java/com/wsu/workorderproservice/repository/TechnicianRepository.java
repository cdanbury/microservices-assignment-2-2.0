package com.wsu.workorderproservice.repository;

import com.wsu.workorderproservice.model.Technician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TechnicianRepository extends JpaRepository<Technician, String> {
@Query(nativeQuery = true, value = "SELECT t.technician_code AS technicianCode, t.technician_first_name AS firstName, "
        + "t.technician_last_name AS lastName, wos.work_order_status_desc AS workOrderStatus, t.technician_type as type, GROUP_CONCAT(twp.state_code SEPARATOR ',') AS states "
        + "FROM technician t LEFT JOIN technician_work_permit twp on twp.technician_code = t.technician_code LEFT JOIN work_order wo ON wo.work_order_number = "
        + "(SELECT MAX(wo1.work_order_number) FROM work_order wo1 WHERE wo1.technician_code = t.technician_code) "
        + "LEFT JOIN work_order_status wos ON wo.work_order_status_code = wos.work_order_status_code "
        + "WHERE :search IS NULL OR (t.technician_code LIKE %:search% OR t.technician_first_name LIKE %:search% OR t.technician_type LIKE %:search% "
        + "OR t.technician_last_name LIKE %:search% OR wos.work_order_status_desc LIKE %:search% OR twp.state_code LIKE %:search%) group by t.technician_code")
    Page<Object[]> findBySearch(String search, Pageable pageable);
}
