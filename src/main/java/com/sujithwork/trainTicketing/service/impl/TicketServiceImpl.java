package com.sujithwork.trainTicketing.service.impl;

import com.sujithwork.trainTicketing.Excepion.*;
import com.sujithwork.trainTicketing.entity.Ticket;
import com.sujithwork.trainTicketing.repository.TicketRepository;
import com.sujithwork.trainTicketing.repository.UserRepository;
import com.sujithwork.trainTicketing.service.TicketService;
import com.sujithwork.trainTicketing.util.SectionUtil;
import com.sujithwork.trainTicketing.util.TicketingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketingUtil ticketingUtil;
    @Autowired
    private SectionUtil sectionUtil;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket saveTicket(Ticket ticket) {

        if (!userRepository.findById(ticket.getUserId()).isPresent()) {
            log.warn("User not found {}", ticket.getUserId());
            throw new UserNotFoundException("User with ID " + ticket.getUserId() + " not found");
        }
        if (sectionUtil.isSectionFull(ticket.getSection())) {
            log.warn("No more seats available in section {}", ticket.getSection());
            throw new SectionFullException("No more seats available in section " + ticket.getSection());
        }
        if (ticketingUtil.isSeatTaken(ticket.getSection(), ticket.getSeatNumber())) {
            log.warn("Seat {} in section {} is already taken", ticket.getSeatNumber(), ticket.getSection());
            throw new SeatAlreadyTakenException("Seat already taken in section " + ticket.getSection());
        }
        log.info("Saving ticket for user {} in section {}, seat {}",
                ticket.getUserId(), ticket.getSection(), ticket.getSeatNumber());
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketByUserId(Long userId) {
        log.info("Fetching ticket for user {}", userId);
        return ticketRepository.findAll().stream()
                .filter(ticket -> userId.equals(ticket.getUserId()))
                .findFirst()
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id " + userId));
    }

    public List<Ticket> getTicketBySection(String section) {
        log.info("Fetching tickets for section {}", section);
        return ticketRepository.findAll().stream()
                .filter(ticket -> section.equalsIgnoreCase(ticket.getSection()))
                .toList();
    }

    public Ticket updateTicket(Long ticketId, Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id " + ticketId));

        if (!existingTicket.getSection().equals(updatedTicket.getSection()) ||
                !existingTicket.getSeatNumber().equals(updatedTicket.getSeatNumber())) {
            if (ticketingUtil.isSeatTaken(updatedTicket.getSection(), updatedTicket.getSeatNumber())) {
                throw new SeatAlreadyTakenException("Seat already taken in section " + updatedTicket.getSection());
            }
            if (sectionUtil.isSectionFull(updatedTicket.getSection())) {
                throw new SectionFullException("No more seats available in section " + updatedTicket.getSection());
            }
        }

        existingTicket.setFromLocation(updatedTicket.getFromLocation());
        existingTicket.setToLocation(updatedTicket.getToLocation());
        existingTicket.setPricePaid(updatedTicket.getPricePaid());
        existingTicket.setSection(updatedTicket.getSection());
        existingTicket.setSeatNumber(updatedTicket.getSeatNumber());

        return ticketRepository.save(existingTicket);
    }

}
