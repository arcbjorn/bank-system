package com.banksystem.app.model

data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)