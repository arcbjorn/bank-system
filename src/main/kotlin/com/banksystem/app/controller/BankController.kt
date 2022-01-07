package com.banksystem.app.controller

import com.banksystem.app.model.Bank
import com.banksystem.app.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/banks")
class BankController(private val service: BankService) {
    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()
}