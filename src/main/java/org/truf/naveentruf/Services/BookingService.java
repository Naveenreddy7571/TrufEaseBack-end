package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.CustomExceptions.InsufficientBalanceException;
import org.truf.naveentruf.Dtos.AllBookingsGround;
import org.truf.naveentruf.Dtos.BookingDto;
import org.truf.naveentruf.CustomExceptions.BookingException;
import org.truf.naveentruf.Dtos.UserDto;
import org.truf.naveentruf.Models.Booking;
import org.truf.naveentruf.Models.Truf;
import org.truf.naveentruf.Models.TrufUser;
import org.truf.naveentruf.Repositories.BookingRepository;
import org.truf.naveentruf.Repositories.GroundRepository;
import org.truf.naveentruf.Repositories.UserRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GroundRepository groundRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    public Booking bookGround(BookingDto bookingDto) throws InsufficientBalanceException {
        Truf ground = groundRepository.findById(bookingDto.getGroundId())
                .orElseThrow(() -> new BookingException("Ground not found"));
        TrufUser user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new BookingException("User not found"));

        List<Booking> overlappingBookings = bookingRepository.findByGroundIdAndStartTimeBetween(
                bookingDto.getGroundId(), bookingDto.getStartTime(), bookingDto.getEndTime());

        if (!overlappingBookings.isEmpty()) {
            throw new BookingException("The selected time slot is already booked.");
        }

        Double userAmount = walletService.fetchBalance(user.getId());
        if(userAmount < ground.getPrice())
        {
            throw new InsufficientBalanceException("No Enough Money to Book the Ground");
        }
        walletService.removeMoneyFromWallet(user.getId(), ground.getPrice());
        Booking booking = new Booking();
        booking.setGround(ground);
        booking.setUser(user);
        booking.setStartTime(bookingDto.getStartTime());
        booking.setEndTime(bookingDto.getEndTime());
        booking.setBookedPrice(ground.getPrice());
        booking.setStatus(Boolean.TRUE);
        return bookingRepository.save(booking);
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<BookingDto> getAllBookings()
    {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void canclebooking(Long bookingId) throws InsufficientBalanceException {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if(bookingOptional.isPresent())
        {
            Booking booking = bookingOptional.get();
            bookingRepository.delete(booking);
            walletService.addMoneyToWallet(booking.getUser().getId() , booking.getBookedPrice());
        }
        else
        {
            throw new BookingException("Cancellation Failed");
        }
    }

    public List<UserDto> getAllCustomers()
    {
        List<TrufUser> trufUserList =bookingRepository.findAll().stream().map(Booking::getUser).distinct().toList();
        return convertTrufUsersToUserDtos(trufUserList);
    }


    private BookingDto convertToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setGroundId(booking.getGround().getId());
        dto.setUserId(booking.getUser().getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        return dto;
    }

    private List<UserDto> convertTrufUsersToUserDtos(List<TrufUser> trufUserList)
    {
        return  trufUserList.stream().map(user -> new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getNumber(),
                user.getAddress())).collect(Collectors.toList());

    }

    public List<AllBookingsGround> bookedGroundInfo()
    {
        List<Booking> bookingList = bookingRepository.findAll();
        List<AllBookingsGround> dto = new ArrayList<>();
        if(bookingList.isEmpty())
        {
            return null;
        }
        for(Booking bookininfo : bookingList)
        {
            AllBookingsGround bookingsGroundDto = new AllBookingsGround();
            bookingsGroundDto.setGroundName(bookininfo.getGround().getGroundName());
            bookingsGroundDto.setBookingID(bookininfo.getId());
            bookingsGroundDto.setCustomerName(bookininfo.getUser().getFirstName());
            bookingsGroundDto.setCustomerNumber(bookininfo.getUser().getNumber());
            String bookingSlot = formatBookingSlot(bookininfo.getStartTime(),bookininfo.getEndTime());
            bookingsGroundDto.setBookingSlot(bookingSlot);
            bookingsGroundDto.setPaidAmount(bookininfo.getBookedPrice());
            bookingsGroundDto.setStatus(bookininfo.getStatus());
            bookingsGroundDto.setBookingDate(bookininfo.getBookedDate());
            dto.add(bookingsGroundDto);
        }
        return dto;
    }


    private String formatBookingSlot(LocalDateTime startTime, LocalDateTime endTime) {
        String formattedStartTime = formatTime(startTime);
        String formattedEndTime = formatTime(endTime);
        return formattedStartTime + " - " + formattedEndTime;
    }

    private static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return dateTime.format(formatter);
    }

}
