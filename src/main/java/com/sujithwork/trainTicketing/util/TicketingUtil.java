package com.sujithwork.trainTicketing.util;

import com.sujithwork.trainTicketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketingUtil {

    @Autowired
    private TicketRepository ticketRepository;
    public boolean isSeatTaken(String section, String seatNumber) {
        return ticketRepository.findAll().stream()
                .anyMatch(ticket -> section.equals(ticket.getSection()) && seatNumber.equals(ticket.getSeatNumber()));
    }
}
