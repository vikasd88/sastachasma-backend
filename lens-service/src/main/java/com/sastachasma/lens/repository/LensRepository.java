package com.sastachasma.lens.repository;

import com.sastachasma.lens.entity.Lens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LensRepository extends JpaRepository<Lens, Long> {
    List<Lens> findByType(String type);
    List<Lens> findByMaterial(String material);
    List<Lens> findByIsActiveTrue();
}
