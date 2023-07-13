package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRespository extends JpaRepository<Adress, Long> {
}
