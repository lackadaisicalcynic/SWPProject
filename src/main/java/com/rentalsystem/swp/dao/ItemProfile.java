package com.rentalsystem.swp.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ItemProfile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String timeSlots;
    private String category;
    private String owner;

    public ItemProfile(String name, String description, String timeSlots, String category, String owner) {
        this.name = name;
        this.description = description;
        this.timeSlots = timeSlots;
        this.category = category;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public String getCategory() {
        return category;
    }

    public String getOwner() {
        return owner;
    }
}
