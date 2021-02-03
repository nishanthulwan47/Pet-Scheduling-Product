package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = null;

        if ((Long) petDTO.getOwnerId() != null) {
            customer = customerService.findCustomerById(petDTO.getOwnerId());
        }

        Pet pet = convertPetDTOToPet(petDTO);
        pet.setCustomer(customer);
        Pet savePet = petService.savePet(pet);

        if (customer != null) {
            customer.addPet(savePet);
        }
        return convertPetToPetDTO(savePet);
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        if (pet != null) {
            return convertPetToPetDTO(pet);
        }
        return null;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> retrievedPets = petService.getAllPets();
        List<PetDTO> petDTOS = new ArrayList<>();

        for (Pet pet : retrievedPets) {
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findPetsByOwner(ownerId);
        List<PetDTO> petDTOS = new ArrayList<PetDTO>();

        for (Pet pet : pets) {
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }
}
