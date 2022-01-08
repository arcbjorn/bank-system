package com.banksystem.app.datasource.network

import com.banksystem.app.datasource.BankDataSource
import com.banksystem.app.datasource.network.dto.BankList
import com.banksystem.app.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkBankDataSource(
    @Autowired private val restTemplate: RestTemplate
): BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> =
            restTemplate.getForEntity<BankList>("http://54.193.31.159/banks")

        return  response.body?.results
            ?: throw IOException("Could not fetch banks from network")
    }


    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}