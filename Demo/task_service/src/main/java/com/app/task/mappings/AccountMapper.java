package com.app.task.mappings;

import com.app.task.dto.account.response.AccountDetailResponse;
import com.app.task.dto.account.response.AccountResponse;
import com.app.task.entity.Account;

public interface AccountMapper {

    AccountResponse toResponse(Account account);

    AccountDetailResponse toDetailResponse(Account account);
}
