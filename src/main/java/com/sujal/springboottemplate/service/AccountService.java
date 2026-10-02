package com.sujal.springboottemplate.service;


import com.sujal.springboottemplate.dto.AccountRequest;
import com.sujal.springboottemplate.dto.AccountResponse;
import com.sujal.springboottemplate.entity.Account;
import com.sujal.springboottemplate.exception.ResourceNotFoundException;
import com.sujal.springboottemplate.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor

public class AccountService {
    private static final String RESOURCE = "Account";

    private final AccountRepository repository;

    @Transactional
    public AccountResponse create(AccountRequest request) {
        Account account = new Account();
        apply(account, request);
        return AccountResponse.from(repository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(Long id) {
        return AccountResponse.from(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<AccountResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(AccountResponse::from);
    }

    @Transactional
    public AccountResponse update(Long id, AccountRequest request) {
        Account account = findOrThrow(id);
        apply(account, request);
        return AccountResponse.from(repository.save(account));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Account findOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    private void apply(Account account, AccountRequest request) {
        account.setHolderName(request.holderName());
        account.setBalance(request.balance());
        account.setCurrency(request.currency());
    }
}
