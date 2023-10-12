package com.example.leadtoprospect.repository;

import com.example.leadtoprospect.model.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect,Long> {
}
