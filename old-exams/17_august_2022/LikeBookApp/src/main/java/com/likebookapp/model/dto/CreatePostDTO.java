package com.likebookapp.model.dto;

import com.likebookapp.model.entity.MoodType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostDTO {

    @NotNull
    @Size(min = 2, max = 150)
    private String content;

    @NotNull
    private MoodType mood;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MoodType getMood() {
        return mood;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
    }
}
