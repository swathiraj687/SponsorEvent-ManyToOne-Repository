/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.eventmanagementsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventJpaRepository;

@Service
public class EventJpaService implements EventRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Event> getEvents() {
        try {
            List<Event> eventList = eventJpaRepository.findAll();
            ArrayList<Event> events = new ArrayList<>(eventList);
            return events;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            return eventJpaRepository.findById(eventId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        List<Integer> sponsorIds = new ArrayList<>();
        for (Sponsor sponsor : event.getSponsors()) {
            sponsorIds.add(sponsor.getSponsorId());
        }
        List<Sponsor> sponsors = sponsorJpaRepositary.findAllById(sponsorIds);
        event.setSponsors(sponsors);

        for (Sponsor sponsor : sponsors) {
            sponsor.getEvents().add(event);
        }
        Event savedEvent = eventJpaRepository.save(event);
        sponsorJpaRepositary.saveAll(sponsors);
        return savedEvents;
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event newEvent = eventJpaRepository.findById(eventId).get();
            if (event.getEventName() != null) {
                newEvent.setEventName(event.getEventName());
            }
            if (event.getSponsors() != null) {
                List<Sponsor> sponsors = newEvent.getSponsors();
                for (Sponsor sponsor : sponsors) {
                    sponsor.getEvents.remove(newEvent);
                }
                sponsorJpaRepository.saveAll(sponsors);

                List<Integer> newSponsorIds = new ArrayList();
                for (Sponsor sponsor : event.getSponsors()) {
                    newSponsorIds.add(sponsor.getSponsorId());
                }
                List<Sponsor> newSponsors = sponsorJpaRepository.findAllById(newSponsorIds);

                for (Sponsor sponsor : newSponsors) {
                    sponsor.getEvents().add(newEvent);
                }
                sponsorJpaRe(pository.saveAll(newSponsors));
                newEvent.setSponsors(newSponsors);
            }
            return eventJpaRepository.save(newEvent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEvent(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            List<Sponsor> sponsors = event.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(event);
            }
            sponsorJpaRepository.saveAll(sponsors);
            eventJpaRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStausException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Sposor> getEventSponsors(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            return event.getSponsors();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
