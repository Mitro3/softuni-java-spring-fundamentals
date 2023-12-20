package com.likebookapp.init;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodType;
import com.likebookapp.repository.MoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoodSeeder implements CommandLineRunner {
    private final MoodRepository moodRepository;

    public MoodSeeder(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.moodRepository.count() == 0) {
            List<Mood> moods = Arrays.stream(MoodType.values())
                    .map(mood -> new Mood(mood))
                    .collect(Collectors.toList());

            this.moodRepository.saveAll(moods);
        }
    }
}
