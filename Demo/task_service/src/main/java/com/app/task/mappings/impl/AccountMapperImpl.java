package com.app.task.mappings.impl;

import com.app.task.dto.account.response.AccountDetailResponse;
import com.app.task.dto.account.response.AccountResponse;
import com.app.task.entity.Account;
import com.app.task.mappings.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountResponse toResponse(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountResponse()
                .setId(account.getId())
                .setUsername(account.getUsername())
                .setName(account.getName())
                .setEmail(account.getEmail())
                .setPhone(account.getPhone())
                .setRole(account.getRole())
                .setDescription(account.getDescription())
                .setUpdatedAt(account.getUpdatedAt())
                .setCreatedAt(account.getCreatedAt())
                .setAvatar(account.getAvatar());
    }

    @Override
    public AccountDetailResponse toDetailResponse(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDetailResponse()
                .setId(account.getId())
                .setUsername(account.getUsername())
                .setName(account.getName())
                .setEmail(account.getEmail())
                .setPhone(account.getPhone())
                .setRole(account.getRole())
                .setUpdatedAt(account.getUpdatedAt())
                .setCreatedAt(account.getCreatedAt())
                .setDescription(account.getDescription())
                .setAvatar(account.getAvatar());
    }


}
