package com.banksystem.app.datasource

import com.banksystem.app.model.Bank

abstract class BankDataSource {

    abstract fun retrieveBanks(): Collection<Bank>
}