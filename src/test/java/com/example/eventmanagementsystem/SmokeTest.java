package com.example.eventmanagementsystem;

import com.example.eventmanagementsystem.controller.EventController;
import com.example.eventmanagementsystem.controller.SponsorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private EventController eventController;

    @Autowired
    private SponsorController sponsorController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(eventController).isNotNull();
        assertThat(sponsorController).isNotNull();
    }
}
