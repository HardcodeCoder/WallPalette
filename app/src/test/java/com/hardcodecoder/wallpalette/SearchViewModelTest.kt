package com.hardcodecoder.wallpalette

import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.usecase.SearchPhotos
import com.hardcodecoder.wallpalette.ui.randomPhoto
import com.hardcodecoder.wallpalette.ui.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchViewModelTest {

    private val searchPhotos = mockk<SearchPhotos>()
    private val data = MutableList(5) { randomPhoto() }
    private lateinit var viewModel: SearchViewModel

    @BeforeAll
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @BeforeEach
    fun initViewModel() {
        viewModel = SearchViewModel(searchPhotos)
    }

    @Test
    fun `When view model is init should not fetch any data`() = runTest {
        val flowResult = mutableListOf<List<Photo>>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchResult.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertTrue(flowResult[0].isEmpty())
    }

    @Test
    fun `Given a valid search query should return data`() = runTest {
        coEvery {
            searchPhotos.invoke(
                query = eq("cars"),
                page = any()
            )
        } returns Result.Success(data)

        val flowResult = mutableListOf<List<Photo>>()
        viewModel.searchPhotos("cars")

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchResult.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertEquals(5, flowResult[0].size)
    }

    @Test
    fun `Given an invalid search query should not return data`() = runTest {
        coEvery {
            searchPhotos.invoke(
                query = eq("invalid"),
                page = any()
            )
        } returns Result.Success(emptyList())

        val flowResult = mutableListOf<List<Photo>>()
        viewModel.searchPhotos("invalid")

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchResult.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertEquals(0, flowResult[0].size)
    }
}