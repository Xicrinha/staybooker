package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
