package com.sujithwork.trainTicketing.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketNumber;
    private Long userId;
    private String fromLocation;
    private String toLocation;
    private Double pricePaid;
    private String section;
    private String seatNumber;
}
