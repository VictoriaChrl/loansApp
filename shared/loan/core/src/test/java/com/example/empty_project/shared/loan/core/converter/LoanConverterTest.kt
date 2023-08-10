package com.example.empty_project.shared.loan.core.converter


import com.example.empty_project.shared.loan.core.data.converter.LoanConverter
import com.example.empty_project.shared.loan.core.data.model.LoanConditionsModel
import com.example.empty_project.shared.loan.core.data.model.LoanModel
import com.example.empty_project.shared.loan.core.data.model.NewLoanModel
import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.entity.LoanStatus
import com.example.empty_project.shared.loan.core.domain.entity.NewLoan
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class LoanConverterTest {

    private val loanConverter = LoanConverter()

    @Test
    fun `convertLoan EXPECT correct conversion from LoanModel to Loan`() {
        val loanModel = LoanModel(
            amount = 1000,
            firstName = "John",
            lastName = "Doe",
            percent = 5.0,
            period = 12,
            phoneNumber = "123456789",
            id = 1,
            date = "2022-01-01T00:00:00.000Z",
            status = LoanStatus.APPROVED
        )

        val expectedLoan = Loan(
            amount = 1000,
            firstName = "John",
            lastName = "Doe",
            percent = 5.0,
            period = 12,
            phoneNumber = "123456789",
            id = 1,
            date = "01.01.2022 00:00",
            status = LoanStatus.APPROVED
        )

        val result = loanConverter.convertLoan(loanModel)

        assertEquals(expectedLoan, result)
    }

    @Test
    fun `convertNewLoan EXPECT correct conversion from NewLoan to NewLoanModel`() {
        val newLoan = NewLoan(
            amount = 2000,
            firstName = "Jane",
            lastName = "Smith",
            percent = 3.5,
            period = 6,
            phoneNumber = "987654321"
        )

        val expectedNewLoanModel = NewLoanModel(
            amount = 2000,
            firstName = "Jane",
            lastName = "Smith",
            percent = 3.5,
            period = 6,
            phoneNumber = "987654321"
        )

        val result = loanConverter.convertNewLoan(newLoan)

        assertEquals(expectedNewLoanModel, result)
    }

    @Test
    fun `convertLoanConditions EXPECT correct conversion from LoanConditionsModel to LoanConditions`() {
        val loanConditionsModel = LoanConditionsModel(
            maxAmount = 5000,
            percent = 4.0,
            period = 24
        )

        val expectedLoanConditions = LoanConditions(
            maxAmount = 5000,
            percent = 4.0,
            period = 24
        )

        val result = loanConverter.convertLoanConditions(loanConditionsModel)

        assertEquals(expectedLoanConditions, result)
    }

    @Test
    fun `toDate EXPECT correct conversion from string date to formatted date`() {
        val stringDate = "2022-01-01T00:00:00.000Z"
        val expectedFormattedDate = "01.01.2022 00:00"

        val result = loanConverter.toDate(stringDate)

        assertEquals(expectedFormattedDate, result)
    }
}