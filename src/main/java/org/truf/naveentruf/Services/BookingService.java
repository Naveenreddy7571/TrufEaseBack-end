package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.Dtos.BookingDto;
import org.truf.naveentruf.CustomExceptions.BookingException;
import org.truf.naveentruf.Models.Booking;
import org.truf.naveentruf.Models.Truf;
import org.truf.naveentruf.Models.TrufUser;
import org.truf.naveentruf.Repositories.BookingRepository;
import org.truf.naveentruf.Repositories.GroundRepository;
import org.truf.naveentruf.Repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GroundRepository groundRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking bookGround(BookingDto bookingDto) {
        Truf ground = groundRepository.findById(bookingDto.getGroundId())
                .orElseThrow(() -> new BookingException("Ground not found"));
        TrufUser user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new BookingException("User not found"));

        List<Booking> overlappingBookings = bookingRepository.findByGroundIdAndStartTimeBetween(
                bookingDto.getGroundId(), bookingDto.getStartTime(), bookingDto.getEndTime());

        if (!overlappingBookings.isEmpty()) {
            throw new BookingException("The selected time slot is already booked.");
        }

        Booking booking = new Booking();
        booking.setGround(ground);
        booking.setUser(user);
        booking.setStartTime(bookingDto.getStartTime());
        booking.setEndTime(bookingDto.getEndTime());

        return bookingRepository.save(booking);
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BookingDto convertToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setGroundId(booking.getGround().getId());
        dto.setUserId(booking.getUser().getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        return dto;
    }
}
