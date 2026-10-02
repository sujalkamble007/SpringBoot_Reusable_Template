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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}
