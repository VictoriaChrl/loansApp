package com.example.empty_project.shared.loan.core.repository

import com.example.empty_project.shared.loan.core.data.SharPrefManagerImpl
import com.example.empty_project.shared.loan.core.data.api.LoansApi
import com.example.empty_project.shared.loan.core.data.converter.LoanConverter
import com.example.empty_project.shared.loan.core.data.model.LoanConditionsModel
import com.example.empty_project.shared.loan.core.data.model.LoanModel
import com.example.empty_project.shared.loan.core.data.model.NewLoanModel
import com.example.empty_project.shared.loan.core.data.repository.LoanRepositoryImpl
import com.example.empty_project.shared.loan.core.domain.entity.Loan
import com.example.empty_project.shared.loan.core.domain.entity.LoanConditions
import com.example.empty_project.shared.loan.core.domain.entity.NewLoan
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class LoanRepositoryImplTest {

    private val loansApi: LoansApi = mock()
    private val converterLoan: LoanConverter = mock()
    private val sharPrefManager: SharPrefManagerImpl = mock()
    private val loanModel: LoanModel = mock()
    private val loan: Loan = mock()
    private val loanConditions: LoanConditions = mock()
    private val loanConditionsModel: LoanConditionsModel = mock()
    private val newLoan: NewLoan = mock()
    private val newLoanModel: NewLoanModel = mock()
    private val loanRepository = LoanRepositoryImpl(loansApi, converterLoan, sharPrefManager)

    @BeforeEach
    fun setup() {
        val token = "token"

        whenever(sharPrefManager.getToken()).thenReturn(token)
        whenever(converterLoan.convertLoan(loanModel)).thenReturn(loan)
        whenever(converterLoan.convertLoanConditions(loanConditionsModel)).thenReturn(loanConditions)
        whenever(converterLoan.convertNewLoan(newLoan)).thenReturn(newLoanModel)
    }

    @Test
    fun `get all EXPECT loans`() = runTest {

        val expectedLoans = listOf(loan)

        whenever(loansApi.getAllLoans(any())) doReturn arrayOf(loanModel)

        val result = loanRepository.getAllLoans()

        assertEquals(expectedLoans, result)
    }

    @Test
    fun `get EXPECT loan by id`() = runTest {

        val loanId: Long = 1
        val expectedLoans = loan

        whenever(loansApi.getLoanById(any(), any())) doReturn loanModel

        val result = loanRepository.getLoanById(loanId)

        assertEquals(expectedLoans, result)
    }

    @Test
    fun `get EXPECT loan condition`() = runTest {

        val expectedLoanCondition = loanConditions

        whenever(loansApi.getLoanConditions(any())) doReturn loanConditionsModel

        val result = loanRepository.getLoanConditions()

        assertEquals(expectedLoanCondition, result)
    }

    @Test
    fun `create loan EXPECT call loansApi createLoan`() = runTest {

        loanRepository.createLoan(newLoan)

        verify(loansApi).createLoan(any(), any())
    }

}
