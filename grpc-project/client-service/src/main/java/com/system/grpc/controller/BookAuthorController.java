package com.system.grpc.controller;

import com.system.grpc.service.BookAuthorClientService;

import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookAuthorController {
	final BookAuthorClientService bookAuthorClientService;

	@GetMapping("/author/{id}")
	public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable String id) {
		return bookAuthorClientService.getAuthor(Integer.parseInt(id));
	}

	@GetMapping("/book/{author_id}")
	public List<Map<Descriptors.FieldDescriptor, Object>> getBookByAuthor(@PathVariable String author_id)
			throws InterruptedException {
		return bookAuthorClientService.getBooksByAuthor(Integer.parseInt(author_id));
	}

	@GetMapping("/book")
	public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
		return bookAuthorClientService.getExpensiveBook();
	}

	@GetMapping("/book/author/{gender}")
	public List<Map<Descriptors.FieldDescriptor, Object>> getBookByGender(@PathVariable String gender)
			throws InterruptedException {
		return bookAuthorClientService.getBooksByGender(gender);
	}
}
