package com.esprit.tn.rest;

import com.esprit.tn.clients.ProductsClients;
import com.esprit.tn.entity.User;
import com.esprit.tn.model.UserDTO;
import com.esprit.tn.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final UserService userService;
	private final ProductsClients productsClients;
	
	public UserController(final UserService userService ,final ProductsClients productsClients) {
		this.userService = userService;
		this.productsClients = productsClients;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/sellers")
	public ResponseEntity<List<UserDTO>> getAllSellers() {
		return ResponseEntity.ok(userService.findAllSellers());
	}
	
	@GetMapping("/buyers")
	public ResponseEntity<List<UserDTO>> getAllBuyers() {
		return ResponseEntity.ok(userService.findAllBuyers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable final Long id) {
		return ResponseEntity.ok(userService.get(id));
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid final UserDTO userDTO) {
		User user = userService.create(userDTO);
		userDTO.setId(user.getId());
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}

	@PutMapping("/updateProfile/{id}")
	@PreAuthorize("authentication.principal.claims['id']==#id")
	public ResponseEntity<Void> updateUser(@PathVariable final Long id, @RequestBody @Valid final UserDTO userDTO) {
		userService.update(id, userDTO);
		return ResponseEntity.ok().build();
	}

	@Transactional
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
		UserDTO user = userService.get(id);
		userService.delete(id);
		if (user.getRole().contains("seller"))
			productsClients.deleteSellerProducts(id);
		return ResponseEntity.noContent().build();
	}

}