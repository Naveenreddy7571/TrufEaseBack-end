package org.truf.naveentruf.ServiceInterface;
import org.truf.naveentruf.Dtos.ReviewDto;
import org.truf.naveentruf.Models.Review;
import java.util.List;

public interface ReviewServiceInterface {

    Review addReview(Long trufId, ReviewDto review);
    List<Review> getReviewsByTrufId(Long trufId);
}