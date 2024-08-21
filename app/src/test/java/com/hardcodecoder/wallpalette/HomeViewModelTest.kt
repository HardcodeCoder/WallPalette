package com.hardcodecoder.wallpalette

import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import com.hardcodecoder.wallpalette.ui.home.HomeViewModel
import com.hardcodecoder.wallpalette.ui.randomPhoto
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
class HomeViewModelTest {

    private val repo = mockk<PhotoRepository>()
    private val data = MutableList(5) { randomPhoto() }
    private lateinit var viewModel: HomeViewModel

    @BeforeAll
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Default)
        coEvery {
            repo.getLatestPhotos(
                photosPerPage = any(),
                page = any()
            )
        } returns Result.Success(data)
    }

    @BeforeEach
    fun initViewModel() {
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun `When view model is init should automatically fetch initial data`() = runTest {
        val flowResult = mutableListOf<List<Photo>>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.photos.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertEquals(5, flowResult[0].size)
    }

    @Test
    fun `When scroll position is updated to end should fetch next page data`() = runTest {
        val flowResult = mutableListOf<List<Photo>>()
        viewModel.updateScrollPosition(4)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.photos.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertEquals(10, flowResult[0].size)
    }

    @Test
    fun `When scroll position is reset should not fetch next page data`() = runTest {
        val flowResult = mutableListOf<List<Photo>>()
        viewModel.updateScrollPosition(4)
        viewModel.updateScrollPosition(0)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.photos.toList(flowResult)
        }

        assertTrue(flowResult.isNotEmpty())
        assertEquals(10, flowResult[0].size)
    }
}