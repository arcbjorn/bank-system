package com.banksystem.app.datasource.network.dto

import com.banksystem.app.model.Bank

data class BankList (
    val results: Collection<Bank>
)