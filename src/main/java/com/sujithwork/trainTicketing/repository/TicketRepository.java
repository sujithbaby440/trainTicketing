package com.sujithwork.trainTicketing.repository;

import com.sujithwork.trainTicketing.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
