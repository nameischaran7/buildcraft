package com.buildcraft.inquiry.repository;

import com.buildcraft.inquiry.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends JpaRepository<CustomerInquiry,Long> {
}
