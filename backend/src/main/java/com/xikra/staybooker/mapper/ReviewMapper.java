package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Review;
import com.xikra.staybooker.model.ReviewDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewMapper {

    ReviewDTO reviewToReviewDTO(Review review);

    Review reviewDTOToReview(ReviewDTO reviewDTO);
}
