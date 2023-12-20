package com.likebookapp.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moods")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private MoodType name;

    public Mood() {
    }

    public Mood(MoodType mood) {
        this.name = mood;
    }

    @Column
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoodType getName() {
        return name;
    }

    public void setName(MoodType moodName) {
        this.name = moodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
