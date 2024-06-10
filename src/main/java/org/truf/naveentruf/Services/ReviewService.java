package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.Dtos.ReviewDto;
import org.truf.naveentruf.Models.Review;
import org.truf.naveentruf.Models.Truf;
import org.truf.naveentruf.Repositories.GroundRepository;
import org.truf.naveentruf.Repositories.ReviewRepository;
import org.truf.naveentruf.ServiceInterface.ReviewServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements ReviewServiceInterface {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GroundRepository groundRepository;

    @Override
    public Review addReview(Long trufId, ReviewDto reviewdto) {

        Optional<Truf> truf = groundRepository.findById(trufId);
        if(truf.isEmpty())
        {
            throw new RuntimeException("no truf found with the id");
        }
        Review review = new Review();
        review.setReviewerName(reviewdto.getReviewerName());
        review.setReviewText(reviewdto.getReviewerText());
        review.setRating(reviewdto.getRating());
        review.setTruf(truf.get());
        return reviewRepository.save(review);

    }

    @Override
    public List<Review> getReviewsByTrufId(Long trufId) {
        return reviewRepository.findByTrufId(trufId);
    }

}
