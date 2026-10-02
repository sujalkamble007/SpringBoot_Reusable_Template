package com.sujal.springboottemplate.service;


import com.sujal.springboottemplate.dto.AccountRequest;
import com.sujal.springboottemplate.dto.AccountResponse;
import com.sujal.springboottemplate.entity.Account;
import com.sujal.springboottemplate.exception.ResourceNotFoundException;
import com.sujal.springboottemplate.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;


    @Test
    void create_shouldSaveAccountAndReturnResponse() {

        System.out.println("Executing create_shouldSaveAccountAndReturnResponse test case");

        AccountRequest request = new AccountRequest("Sujal Kamble", new BigDecimal("5000.00"),
                "INR");

        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setHolderName("Sujal Kamble");
        savedAccount.setBalance(new BigDecimal("5000.00"));
        savedAccount.setCurrency("INR");

        when(repository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = service.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.holderName()).isEqualTo("Sujal Kamble");
        assertThat(response.balance()).isEqualByComparingTo("5000.00");
        assertThat(response.currency()).isEqualTo("INR");

        verify(repository).save(any(Account.class));
        System.out.println("Finished create_shouldSaveAccountAndReturnResponse test case");
    }

    @Test
    void getById_shouldReturnAccountWhenExists() {

        System.out.println("Executing getById_shouldReturnAccountWhenExists test case");

        Account account = new Account();
        account.setId(1L);
        account.setHolderName("Sujal Kamble");
        account.setBalance(new BigDecimal("5000.00"));
        account.setCurrency("INR");

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(account));

        AccountResponse response = service.getById(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.holderName()).isEqualTo("Sujal Kamble");
        assertThat(response.balance()).isEqualByComparingTo("5000.00");
        assertThat(response.currency()).isEqualTo("INR");

        verify(repository).findById(1L);

        System.out.println("Finished getById_shouldReturnAccountWhenExists test case");
    }

    @Test
    void getById_shouldThrowExceptionWhenNotFound() {

        when(repository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> service.getById(999L)).isInstanceOf(ResourceNotFoundException.class);

        verify(repository).findById(999L);
    }

    @Test
    void getAll_shouldReturnPagedAccountResponses() {

        Account account1 = new Account();
        account1.setId(1L);
        account1.setHolderName("Sujal Kamble");
        account1.setBalance(new BigDecimal("5000.00"));
        account1.setCurrency("INR");

        Account account2 = new Account();
        account2.setId(2L);
        account2.setHolderName("Rahul Sharma");
        account2.setBalance(new BigDecimal("7500.00"));
        account2.setCurrency("INR");

        Page<Account> accountPage = new PageImpl<>(
                List.of(account1, account2)
        );

        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(accountPage);

        Page<AccountResponse> response = service.getAll(pageable);

//        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).holderName()).isEqualTo("Sujal Kamble");
        assertThat(response.getContent().get(1).holderName()).isEqualTo("Rahul Sharma");

        verify(repository).findAll(pageable);
    }

    @Test
    void update_shouldModifyExistingAccount() {

        Account account = new Account();
        account.setId(1L);
        account.setHolderName("Old Name");
        account.setBalance(new BigDecimal("5000.00"));
        account.setCurrency("INR");

        AccountRequest request = new AccountRequest(
                "Updated Name",
                new BigDecimal("9000.00"),
                "USD"
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(account));

        when(repository.save(any(Account.class)))
                .thenReturn(account);

        AccountResponse response = service.update(1L, request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.holderName()).isEqualTo("Updated Name");
        assertThat(response.balance()).isEqualByComparingTo("9000.00");
        assertThat(response.currency()).isEqualTo("USD");

        verify(repository).findById(1L);
        verify(repository).save(account);
    }

    @Test
    void update_shouldThrowExceptionWhenAccountDoesNotExist() {

        AccountRequest request = new AccountRequest(
                "Updated Name",
                new BigDecimal("9000.00"),
                "INR"
        );

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository).findById(999L);
        verify(repository, never()).save(any(Account.class));
    }

    @Test
    void delete_shouldDeleteExistingAccount() {

        Account account = new Account();
        account.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(account));

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).delete(account);
    }

    @Test
    void delete_shouldThrowExceptionWhenAccountDoesNotExist() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository).findById(999L);
        verify(repository, never()).delete(any(Account.class));
    }



}
