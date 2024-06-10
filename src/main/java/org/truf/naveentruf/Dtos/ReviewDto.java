package org.truf.naveentruf.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.truf.naveentruf.Models.Review;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private String reviewerName;
    private String reviewerText;
    private int rating;

    public List<ReviewDto> convertToDto(List<Review> reviews) {
        return reviews.stream()
                .map(review -> new ReviewDto(
                        review.getReviewerName(),
                        review.getReviewText(),
                        review.getRating()))
                .collect(Collectors.toList());
    }
}
