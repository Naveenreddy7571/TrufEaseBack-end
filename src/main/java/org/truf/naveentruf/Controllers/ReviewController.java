package org.truf.naveentruf.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.truf.naveentruf.Dtos.ReviewDto;
import org.truf.naveentruf.Services.ReviewService;
import org.truf.naveentruf.Models.Review;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private  ReviewService reviewService;

    @PostMapping("addreview/{trufId}")
    public ResponseEntity<?> addReview(@PathVariable Long trufId, @RequestBody ReviewDto review) {
        try {
            Review createdReview = reviewService.addReview(trufId, review);
            return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully: " + createdReview.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add review: " + e.getMessage());
        }
    }

    @GetMapping("getgroundreview/{trufId}")
    public ResponseEntity<?> getReviewsByTrufId(@PathVariable Long trufId) {
        ReviewDto dto = new ReviewDto();
        try {
            List<Review> reviews = reviewService.getReviewsByTrufId(trufId);
            if (reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for Truf with ID: " + trufId);
            }
            return ResponseEntity.ok(dto.convertToDto(reviews));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve reviews: " + e.getMessage());
        }
    }


}
