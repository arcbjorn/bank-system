package com.banksystem.app.datasource.mock

import com.banksystem.app.datasource.BankDataSource
import com.banksystem.app.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    val banks = listOf(
        Bank("123", 3.14, 7),
        Bank("1010", 2.03, 0),
        Bank("234", 0.0, 97),
    )
    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
    }
}