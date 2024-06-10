package org.truf.naveentruf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.truf.naveentruf.Dtos.BookingDto;
import org.truf.naveentruf.CustomExceptions.BookingException;
import org.truf.naveentruf.Models.Booking;
import org.truf.naveentruf.Services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookground")
    public ResponseEntity<?> bookGround(@RequestBody BookingDto bookingDto) {
        try {
            Booking booking = bookingService.bookGround(bookingDto);
            return ResponseEntity.ok(booking);
        } catch (BookingException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserId(@PathVariable Long userId) {
        try {
            List<BookingDto> bookings = bookingService.getBookingsByUserId(userId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
