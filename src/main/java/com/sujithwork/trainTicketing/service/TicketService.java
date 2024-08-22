package com.sujithwork.trainTicketing.service;

import com.sujithwork.trainTicketing.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    Ticket saveTicket(Ticket ticket);
    Ticket getTicketByUserId(Long userId);
    Ticket updateTicket(Long userId,Ticket ticket);
    List<Ticket> getTicketBySection(String section);
}
