package com.sujithwork.trainTicketing.controller;

import com.sujithwork.trainTicketing.entity.Ticket;
import com.sujithwork.trainTicketing.entity.Users;
import com.sujithwork.trainTicketing.service.TicketService;
import com.sujithwork.trainTicketing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/train-ticket")
public class TrainTicketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    @GetMapping("/receipt/{userId}")
    public Ticket getReceipt(@PathVariable Long userId) {
        return ticketService.getTicketByUserId(userId);
    }

    @PutMapping("/modify-seat/{ticketId}")
    public Ticket updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(ticketId, ticket);
    }
}
