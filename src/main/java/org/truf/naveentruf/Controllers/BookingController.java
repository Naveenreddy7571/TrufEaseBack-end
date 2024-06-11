package org.truf.naveentruf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.truf.naveentruf.CustomExceptions.InsufficientBalanceException;
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
            return new ResponseEntity<>("Booked SuccesFully",HttpStatus.OK);
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

    @GetMapping("/fetch/all")
    public ResponseEntity<List<BookingDto>> getAllBookedGrounds()
    {
        try {
            List<BookingDto> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/cancle-booking/{bookingId}")
    public ResponseEntity<String> cancleBooking(@PathVariable Long bookingId)
    {
        try {
            bookingService.canclebooking(bookingId);
            return new ResponseEntity<>("Cancelled SuccessFully", HttpStatus.OK);
        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
