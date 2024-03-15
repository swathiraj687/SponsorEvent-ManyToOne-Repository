package com.example.eventmanagementsystem;

import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class EMSControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private EventJpaRepository eventJpaRepository;

        @Autowired
        private SponsorJpaRepository sponsorJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> eventsHashMap = new HashMap<>(); // Event
        {
                eventsHashMap.put(1, new Object[] { "TechCon", "2023-12-15", new Integer[] { 1, 2 } });
                eventsHashMap.put(2, new Object[] { "Fashion Fest", "2023-11-05", new Integer[] { 2 } });
                eventsHashMap.put(3, new Object[] { "MusicFest", "2024-01-25", new Integer[] { 3, 4 } });
                eventsHashMap.put(4,
                                new Object[] { "EcoAwareness Conclave", "2023-11-10", new Integer[] { 4 } });
                eventsHashMap.put(5, new Object[] { "Gaming Expo", "2024-03-10",
                                new Integer[] { 4 } }); // POST
                eventsHashMap.put(6, new Object[] { "Sci-fi Con", "2024-04-20", new Integer[] { 5 } }); // PUT
        }

        private HashMap<Integer, Object[]> sponsorsHashMap = new HashMap<>(); // Sponsor
        {
                sponsorsHashMap.put(1, new Object[] { "TechCorp", "Technology", new Integer[] { 1 } });
                sponsorsHashMap.put(2, new Object[] { "Glamour Inc.", "Fashion", new Integer[] { 1, 2 } });
                sponsorsHashMap.put(3,
                                new Object[] { "SoundWave Productions", "Music Production", new Integer[] { 3 } });
                sponsorsHashMap.put(4,
                                new Object[] { "EcoPlanet", "Environmental Conservation", new Integer[] { 3, 4 } });
                sponsorsHashMap.put(5, new Object[] { "GameOn Studios", "Video Gaming", new Integer[] { 4, 5 } }); // POST
                sponsorsHashMap.put(6, new Object[] { "FutureTech", "Science Fiction Gadgets", new Integer[] { 5 } }); // PUT
        }

        @Test
        @Order(1)
        public void testGetEvents() throws Exception {
                mockMvc.perform(get("/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].eventId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].eventName", Matchers.equalTo(eventsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].date", Matchers.equalTo(eventsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].eventId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].eventName", Matchers.equalTo(eventsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].date", Matchers.equalTo(eventsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].eventId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].eventName", Matchers.equalTo(eventsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].date", Matchers.equalTo(eventsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].eventId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].eventName", Matchers.equalTo(eventsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].date", Matchers.equalTo(eventsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(2)
        public void testGetSponsors() throws Exception {
                mockMvc.perform(get("/events/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].sponsorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].sponsorName", Matchers.equalTo(sponsorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].industry", Matchers.equalTo(sponsorsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].sponsorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].sponsorName", Matchers.equalTo(sponsorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].industry", Matchers.equalTo(sponsorsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].sponsorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].sponsorName", Matchers.equalTo(sponsorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].industry", Matchers.equalTo(sponsorsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].sponsorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].sponsorName", Matchers.equalTo(sponsorsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].industry", Matchers.equalTo(sponsorsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(3)
        public void testGetEventNotFound() throws Exception {
                mockMvc.perform(get("/events/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(4)
        public void testGetEventById() throws Exception {
                mockMvc.perform(get("/events/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/events/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/events/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/events/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(4)[2])[0])));

        }

        @Test
        @Order(5)
        public void testGetSponsorNotFound() throws Exception {
                mockMvc.perform(get("/events/sponsors/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testGetSponsorById() throws Exception {
                mockMvc.perform(get("/events/sponsors/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/events/sponsors/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/events/sponsors/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/events/sponsors/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(7)
        public void testPostEvent() throws Exception {
                String content = "{\n    \"eventName\": \"" + eventsHashMap.get(5)[0]
                                + "\",\n    \"date\": \""
                                + eventsHashMap.get(5)[1]
                                + "\",\n    \"sponsors\": [\n        {\n            \"sponsorId\": "
                                + ((Integer[]) eventsHashMap.get(5)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/events")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(8)
        public void testAfterPostEvent() throws Exception {
                mockMvc.perform(get("/events/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(9)
        @Transactional
        public void testDbAfterPostEvent() throws Exception {
                Event event = eventJpaRepository.findById(5).get();

                assertEquals(event.getEventId(), 5);
                assertEquals(event.getEventName(), eventsHashMap.get(5)[0]);
                assertEquals(event.getDate(), eventsHashMap.get(5)[1]);
                assertEquals(event.getSponsors().get(0).getSponsorId(), ((Integer[]) eventsHashMap.get(5)[2])[0]);

                Sponsor sponsor = sponsorJpaRepository.findById(((Integer[]) eventsHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < sponsor.getEvents().size(); i++) {
                        if (sponsor.getEvents().get(i).getEventId() == 5) {
                                break;
                        }
                }
                if (i == sponsor.getEvents().size()) {
                        throw new AssertionError("Assertion Error: Sponsor " + sponsor.getSponsorId()
                                        + " has no eventmanagementsystem with eventId 5");
                }
        }

        @Test
        @Order(10)
        public void testPostSponsorBadRequest() throws Exception {
                String content = "{\n    \"sponsorName\": \"" + sponsorsHashMap.get(5)[0] + "\",\n    \"industry\": \""
                                + sponsorsHashMap.get(5)[1]
                                + "\",\n    \"events\": [\n        {\n            \"eventId\": "
                                + ((Integer[]) sponsorsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"eventId\": " + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/events/sponsors")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(11)
        public void testPostSponsor() throws Exception {
                String content = "{\n    \"sponsorName\": \"" + sponsorsHashMap.get(5)[0] + "\",\n    \"industry\": \""
                                + sponsorsHashMap.get(5)[1]
                                + "\",\n    \"events\": [\n        {\n            \"eventId\": "
                                + ((Integer[]) sponsorsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"eventId\": "
                                + ((Integer[]) sponsorsHashMap.get(5)[2])[1]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/events/sponsors")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(12)
        public void testAfterPostSponsor() throws Exception {
                mockMvc.perform(get("/events/sponsors/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(13)
        @Transactional
        public void testDbAfterPostSponsor() throws Exception {
                Sponsor sponsor = sponsorJpaRepository.findById(5).get();

                assertEquals(sponsor.getSponsorId(), 5);
                assertEquals(sponsor.getSponsorName(), sponsorsHashMap.get(5)[0]);
                assertEquals(sponsor.getIndustry(), sponsorsHashMap.get(5)[1]);
                assertEquals(sponsor.getEvents().get(0).getEventId(), ((Integer[]) sponsorsHashMap.get(5)[2])[0]);
                assertEquals(sponsor.getEvents().get(1).getEventId(), ((Integer[]) sponsorsHashMap.get(5)[2])[1]);

                Event event = eventJpaRepository.findById(((Integer[]) sponsorsHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < event.getSponsors().size(); i++) {
                        if (event.getSponsors().get(i).getSponsorId() == 5) {
                                break;
                        }
                }
                if (i == event.getSponsors().size()) {
                        throw new AssertionError("Assertion Error: Event " + event.getEventId()
                                        + " has no sponsor with sponsorId 5");
                }

                event = eventJpaRepository.findById(((Integer[]) sponsorsHashMap.get(5)[2])[1]).get();
                for (i = 0; i < event.getSponsors().size(); i++) {
                        if (event.getSponsors().get(i).getSponsorId() == 5) {
                                break;
                        }
                }
                if (i == event.getSponsors().size()) {
                        throw new AssertionError("Assertion Error: Event " + event.getEventId()
                                        + " has no sponsor with sponsorId 5");
                }
        }

        @Test
        @Order(14)
        public void testPutEventNotFound() throws Exception {
                String content = "{\n    \"eventName\": \"" + eventsHashMap.get(6)[0]
                                + "\",\n    \"date\": \""
                                + eventsHashMap.get(6)[1]
                                + "\",\n    \"sponsors\": [\n        {\n            \"sponsorId\": "
                                + ((Integer[]) eventsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/events/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(15)
        public void testPutEvent() throws Exception {
                String content = "{\n    \"eventName\": \"" + eventsHashMap.get(6)[0]
                                + "\",\n    \"date\": \""
                                + eventsHashMap.get(6)[1]
                                + "\",\n    \"sponsors\": [\n        {\n            \"sponsorId\": "
                                + ((Integer[]) eventsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/events/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(16)
        public void testAfterPutEvent() throws Exception {
                mockMvc.perform(get("/events/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.eventId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.eventName", Matchers.equalTo(eventsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.date", Matchers.equalTo(eventsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(17)
        @Transactional
        public void testDbAfterPutEvent() throws Exception {
                Event event = eventJpaRepository.findById(5).get();

                assertEquals(event.getEventId(), 5);
                assertEquals(event.getEventName(), eventsHashMap.get(6)[0]);
                assertEquals(event.getDate(), eventsHashMap.get(6)[1]);
                assertEquals(event.getSponsors().get(0).getSponsorId(), ((Integer[]) eventsHashMap.get(6)[2])[0]);

                Sponsor sponsor = sponsorJpaRepository.findById(((Integer[]) eventsHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < sponsor.getEvents().size(); i++) {
                        if (sponsor.getEvents().get(i).getEventId() == 5) {
                                break;
                        }
                }
                if (i == sponsor.getEvents().size()) {
                        throw new AssertionError("Assertion Error: Sponsor " + sponsor.getSponsorId()
                                        + " has no eventmanagementsystem with eventId 5");
                }
        }

        @Test
        @Order(18)
        public void testPutSponsorNotFound() throws Exception {
                String content = "{\n    \"sponsorName\": \"" + sponsorsHashMap.get(6)[0] + "\",\n    \"industry\": \""
                                + sponsorsHashMap.get(6)[1]
                                + "\",\n    \"events\": [\n        {\n            \"eventId\": "
                                + ((Integer[]) sponsorsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/events/sponsors/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(19)
        public void testPutSponsorBadRequest() throws Exception {
                String content = "{\n    \"sponsorName\": \"" + sponsorsHashMap.get(6)[0] + "\",\n    \"industry\": \""
                                + sponsorsHashMap.get(6)[1]
                                + "\",\n    \"events\": [\n        {\n            \"eventId\": "
                                + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/events/sponsors/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(20)
        public void testPutSponsor() throws Exception {
                String content = "{\n    \"sponsorName\": \"" + sponsorsHashMap.get(6)[0] + "\",\n    \"industry\": \""
                                + sponsorsHashMap.get(6)[1]
                                + "\",\n    \"events\": [\n        {\n            \"eventId\": "
                                + ((Integer[]) sponsorsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/events/sponsors/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(21)
        public void testAfterPutSponsor() throws Exception {

                mockMvc.perform(get("/events/sponsors/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.sponsorId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.sponsorName", Matchers.equalTo(sponsorsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.industry", Matchers.equalTo(sponsorsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(22)
        @Transactional
        public void testDbAfterPutSponsor() throws Exception {
                Sponsor sponsor = sponsorJpaRepository.findById(5).get();

                assertEquals(sponsor.getSponsorId(), 5);
                assertEquals(sponsor.getSponsorName(), sponsorsHashMap.get(6)[0]);
                assertEquals(sponsor.getIndustry(), sponsorsHashMap.get(6)[1]);
                assertEquals(sponsor.getEvents().get(0).getEventId(), ((Integer[]) sponsorsHashMap.get(6)[2])[0]);

                Event event = eventJpaRepository.findById(((Integer[]) sponsorsHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < event.getSponsors().size(); i++) {
                        if (event.getSponsors().get(i).getSponsorId() == 5) {
                                break;
                        }
                }
                if (i == event.getSponsors().size()) {
                        throw new AssertionError("Assertion Error: Event " + event.getEventId()
                                        + " has no sponsor with sponsorId 5");
                }
        }

        @Test
        @Order(23)
        public void testDeleteSponsorNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/events/sponsors/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(24)
        public void testDeleteSponsor() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/events/sponsors/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(25)
        @Transactional
        @Rollback(false)
        public void testAfterDeleteSponsor() throws Exception {
                mockMvc.perform(get("/events/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].sponsorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].sponsorName", Matchers.equalTo(sponsorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].industry", Matchers.equalTo(sponsorsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].sponsorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].sponsorName", Matchers.equalTo(sponsorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].industry", Matchers.equalTo(sponsorsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].sponsorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].sponsorName", Matchers.equalTo(sponsorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].industry", Matchers.equalTo(sponsorsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].sponsorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].sponsorName", Matchers.equalTo(sponsorsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].industry", Matchers.equalTo(sponsorsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].events[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[1])));

                Event event = eventJpaRepository.findById(((Integer[]) sponsorsHashMap.get(6)[2])[0]).get();

                for (Sponsor sponsor : event.getSponsors()) {
                        if (sponsor.getSponsorId() == 5) {
                                throw new AssertionError("Assertion Error: Sponsor " + sponsor.getSponsorId()
                                        + " and Event 5 are still linked");
                        }
                }
        }

        @Test
        @Order(26)
        public void testDeleteEventNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/events/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(27)
        public void testDeleteEvent() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/events/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(28)
        public void testAfterDeleteEvent() throws Exception {
                mockMvc.perform(get("/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].eventId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].eventName", Matchers.equalTo(eventsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].date", Matchers.equalTo(eventsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].eventId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].eventName", Matchers.equalTo(eventsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].date", Matchers.equalTo(eventsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].eventId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].eventName", Matchers.equalTo(eventsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].date", Matchers.equalTo(eventsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].eventId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].eventName", Matchers.equalTo(eventsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].date", Matchers.equalTo(eventsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].sponsors[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(29)
        public void testGetEventBySponsorId() throws Exception {
                mockMvc.perform(get("/sponsors/1/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/sponsors/2/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/sponsors/3/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/sponsors/4/events")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[*].eventId",
                                                hasItem(((Integer[]) sponsorsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(30)
        public void testGetSponsorByEventId() throws Exception {
                mockMvc.perform(get("/events/1/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/events/2/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/events/3/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/events/4/sponsors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].sponsorId",
                                                hasItem(((Integer[]) eventsHashMap.get(4)[2])[0])));
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table event_sponsor");
                jdbcTemplate.execute("drop table sponsor");
                jdbcTemplate.execute("drop table event");
        }

}