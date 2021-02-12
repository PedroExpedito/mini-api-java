package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dog;
import com.example.demo.repository.DogRepository;


@RestController
@RequestMapping("/dogs")
public class DogController {

	@Autowired
	private DogRepository dogRepository;

	@GetMapping
	public List<Dog> listar() {
		return dogRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Dog adicionar(@RequestBody Dog dog) {
		return dogRepository.save(dog);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.FOUND)
	public String delete(@RequestBody Dog dog) {
		dogRepository.deleteById(dog.getId());
		return "apagado!!";
	}

	@PutMapping
	public Dog put(@RequestBody Dog newDog) {
		return dogRepository.findById(newDog.getId()).map(dog -> {
			dog.setNome(newDog.getNome());
			dog.setBreed(newDog.getBreed());
			return dogRepository.save(dog);
		}).orElseGet(() -> {
			return dogRepository.save(newDog);
		});
	}
}
