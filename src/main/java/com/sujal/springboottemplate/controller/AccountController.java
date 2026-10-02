package com.sujal.springboottemplate.controller;


import com.sujal.springboottemplate.dto.AccountRequest;
import com.sujal.springboottemplate.dto.AccountResponse;
import com.sujal.springboottemplate.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

     private final AccountService service;

    // /api/accounts
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {

        AccountResponse response = service.create(request);

        return ResponseEntity.status(201).body(response);
    }

    //  GET /api/accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById( @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }


    @GetMapping
    public ResponseEntity<Page<AccountResponse>> getAll(Pageable pageable) {

        return ResponseEntity.ok(service.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
