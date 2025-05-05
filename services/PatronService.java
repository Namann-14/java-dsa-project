package services;

import datastructures.LinkedList;
import models.Patron;

public class PatronService {
    private LinkedList<Patron> patrons;
    private int nextId;

    public PatronService() {
        patrons = new LinkedList<>();
        nextId = 1;
    }

    public Patron addPatron(String name, String contactInfo, String address, String membershipDate) {
        String id = "P" + String.format("%04d", nextId++);
        Patron patron = new Patron(id, name, contactInfo, address, membershipDate);
        patrons.add(patron);
        return patron;
    }

    public boolean removePatron(String id) {
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            if (patron.getId().equals(id)) {
                patrons.remove(patron);
                return true;
            }
        }
        return false;
    }

    public Patron findPatronById(String id) {
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            if (patron.getId().equals(id)) {
                return patron;
            }
        }
        return null;
    }

    public LinkedList<Patron> findPatronsByName(String name) {
        LinkedList<Patron> result = new LinkedList<>();
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            if (patron.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(patron);
            }
        }
        return result;
    }

    public LinkedList<Patron> getAllPatrons() {
        return patrons;
    }

    public void updatePatron(Patron updatedPatron) {
        Patron existingPatron = findPatronById(updatedPatron.getId());
        if (existingPatron != null) {
            existingPatron.setName(updatedPatron.getName());
            existingPatron.setContactInfo(updatedPatron.getContactInfo());
            existingPatron.setAddress(updatedPatron.getAddress());
            existingPatron.setMembershipDate(updatedPatron.getMembershipDate());
        }
    }
} 