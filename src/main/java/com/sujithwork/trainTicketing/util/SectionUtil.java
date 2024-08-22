package com.sujithwork.trainTicketing.util;

import com.sujithwork.trainTicketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SectionUtil {
    @Autowired
    private TicketRepository ticketRepository;

    private static final int MAX_SEATS_PER_SECTION = 10;
    public boolean isSectionFull(String section) {
        long count = ticketRepository.findAll().stream()
                .filter(ticket -> section.equals(ticket.getSection()))
                .count();
        return count >= MAX_SEATS_PER_SECTION;
    }
}
