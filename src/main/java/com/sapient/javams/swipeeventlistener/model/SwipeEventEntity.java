package com.sapient.javams.swipeeventlistener.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "swipe_events")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SwipeEventEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String messageId;

    @Column(nullable = false)
    private String swipeID;

}
