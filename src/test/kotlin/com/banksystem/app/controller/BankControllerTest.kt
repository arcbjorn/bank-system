package com.banksystem.app.controller

import com.banksystem.app.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {
    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("123")}
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should the bank with the given account number`() {
            val accountNumber = 123
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("7") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            val accountNumber = 124
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            val newBank = Bank("acc123", 12.1232, 7)

            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("acc123") }
                    jsonPath("$.trust") { value("12.1232") }
                    jsonPath("$.transactionFee") { value("7") }
                }
        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            val invalidBank = Bank("123", 1.1, 7)

            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }
}