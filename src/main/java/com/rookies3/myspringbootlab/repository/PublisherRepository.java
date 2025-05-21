package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    Optional<Publisher> findByName(String name);
    Optional<Publisher> findByWithBooks(Long id);
    boolean existsByName(String name);
}
