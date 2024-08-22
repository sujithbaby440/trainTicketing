package com.sujithwork.trainTicketing.service.impl;

import com.sujithwork.trainTicketing.Excepion.SeatAlreadyTakenException;
import com.sujithwork.trainTicketing.Excepion.SectionFullException;
import com.sujithwork.trainTicketing.Excepion.UserNotFoundException;
import com.sujithwork.trainTicketing.entity.Ticket;
import com.sujithwork.trainTicketing.entity.Users;
import com.sujithwork.trainTicketing.repository.TicketRepository;
import com.sujithwork.trainTicketing.repository.UserRepository;
import com.sujithwork.trainTicketing.util.SectionUtil;
import com.sujithwork.trainTicketing.util.TicketingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketingUtil ticketingUtil;

    @Mock
    private SectionUtil sectionUtil;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    private Ticket ticket;

    private Users users;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setTicketNumber(1L);
        ticket.setUserId(1L);
        ticket.setSection("A");
        ticket.setSeatNumber("1");

        users  = new Users();
        users.setUserId(1L);
        users.setEmail("abc@bee.com");
    }


    @Test
    void saveTicket_userNotFound_throwsException() {
        when(userRepository.findById(ticket.getUserId())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            ticketService.saveTicket(ticket);
        });

        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    void saveTicket_sectionFull_throwsException() {
        when(userRepository.findById(ticket.getUserId())).thenReturn(Optional.of(new Users()));
        when(sectionUtil.isSectionFull(ticket.getSection())).thenReturn(true);

        SectionFullException exception = assertThrows(SectionFullException.class, () -> {
            ticketService.saveTicket(ticket);
        });

        assertEquals("No more seats available in section A", exception.getMessage());
    }

    @Test
    void saveTicket_seatTaken_throwsException() {
        when(userRepository.findById(ticket.getUserId())).thenReturn(Optional.of(new Users()));
        when(sectionUtil.isSectionFull(ticket.getSection())).thenReturn(false);
        when(ticketingUtil.isSeatTaken(ticket.getSection(), ticket.getSeatNumber())).thenReturn(true);

        SeatAlreadyTakenException exception = assertThrows(SeatAlreadyTakenException.class, () -> {
            ticketService.saveTicket(ticket);
        });

        assertEquals("Seat already taken in section A", exception.getMessage());
    }

    @Test
    void saveTicket_success() {
        when(userRepository.findById(ticket.getUserId())).thenReturn(Optional.of(new Users()));
        when(sectionUtil.isSectionFull(ticket.getSection())).thenReturn(false);
        when(ticketingUtil.isSeatTaken(ticket.getSection(), ticket.getSeatNumber())).thenReturn(false);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket savedTicket = ticketService.saveTicket(ticket);

        assertEquals(ticket, savedTicket);
    }

    @Test
    void getTicketByUserId_ticketFound() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        Ticket foundTicket = ticketService.getTicketByUserId(ticket.getUserId());

        assertEquals(ticket, foundTicket);
    }

    @Test
    void getTicketBySection_ticketsFound() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> foundTickets = ticketService.getTicketBySection(ticket.getSection());

        assertEquals(1, foundTickets.size());
        assertEquals(ticket, foundTickets.get(0));
    }

    @Test
    void getTicketBySection_noTicketsFound() {
        when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        List<Ticket> foundTickets = ticketService.getTicketBySection(ticket.getSection());

        assertTrue(foundTickets.isEmpty());
    }


    @Test
    void updateTicket_success() {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setSection("B");
        updatedTicket.setSeatNumber("2");

        when(ticketRepository.findById(ticket.getTicketNumber())).thenReturn(Optional.of(ticket));
        when(ticketingUtil.isSeatTaken(updatedTicket.getSection(), updatedTicket.getSeatNumber())).thenReturn(false);
        when(sectionUtil.isSectionFull(updatedTicket.getSection())).thenReturn(false);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(updatedTicket);

        Ticket result = ticketService.updateTicket(ticket.getTicketNumber(), updatedTicket);

        assertEquals(updatedTicket, result);
    }
}