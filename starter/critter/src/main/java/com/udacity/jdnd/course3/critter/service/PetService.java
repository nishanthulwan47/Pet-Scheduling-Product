package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet findPetById(Long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> findPetsByOwner(Long customerid) {
        return petRepository.getAllPetsByCustomerId(customerid);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
