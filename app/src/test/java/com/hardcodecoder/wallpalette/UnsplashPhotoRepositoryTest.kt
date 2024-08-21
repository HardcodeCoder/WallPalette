package com.hardcodecoder.wallpalette

import android.graphics.Color
import com.hardcodecoder.wallpalette.data.model.UnsplashPhoto
import com.hardcodecoder.wallpalette.data.model.UnsplashPhotoUrl
import com.hardcodecoder.wallpalette.data.model.UnsplashSearchResult
import com.hardcodecoder.wallpalette.data.remote.UnsplashPhotoService
import com.hardcodecoder.wallpalette.data.repo.UnsplashPhotoRepository
import com.hardcodecoder.wallpalette.domain.model.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.HttpException
import retrofit2.Response

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnsplashPhotoRepositoryTest {

    private val service = mockk<UnsplashPhotoService>()
    private val repo = UnsplashPhotoRepository(service)
    private val unsplashPhotos = MutableList(5) {
        UnsplashPhoto(
            id = "id",
            description = null,
            altDescription = "Something",
            width = 300,
            height = 200,
            blurHash = "blue hash",
            color = "#FF00FF",
            url = UnsplashPhotoUrl(
                thumbUrl = "",
                fullUrl = ""
            ),
            likes = 10
        )
    }

    init {
        mockkStatic(Color::class)
        coEvery { Color.parseColor(any()) } returns 0
    }

    @Test
    fun `When requesting latest photos should return data`() = runTest {
        coEvery {
            service.getLatestUnsplashPhotos(
                photosPerPage = any(),
                page = any()
            )
        } returns unsplashPhotos

        val result = repo.getLatestPhotos()

        assertTrue(result is Result.Success)
        assertEquals(5, (result as Result.Success).data.size)
    }

    @Test
    fun `When searching photos with valid query should return data`() = runTest {
        coEvery {
            service.searchUnsplashPhotos(
                query = eq("Cars"),
                resultPerPage = any(),
                page = any()
            )
        } returns UnsplashSearchResult(unsplashPhotos)

        val result = repo.searchPhotos(query = "Cars")

        assertTrue(result is Result.Success)
        assertEquals(5, (result as Result.Success).data.size)
    }

    @Test
    fun `When searching photos with empty query should return empty result`() = runTest {
        coEvery {
            service.searchUnsplashPhotos(
                query = eq(""),
                resultPerPage = any(),
                page = any()
            )
        } returns UnsplashSearchResult(emptyList())

        val result = repo.searchPhotos(query = "")

        assertTrue(result is Result.Success)
        assertEquals(0, (result as Result.Success).data.size)
    }

    @Test
    fun `When api call throws http exception should handle and pass error`() = runTest {
        val response = mockk<Response<*>>(relaxed = true)

        every { response.code() } returns 404

        coEvery {
            service.getLatestUnsplashPhotos(photosPerPage = any(), page = any())
        }.throws(HttpException(response))

        val result = repo.getLatestPhotos()

        assertTrue(result is Result.Error)
        assertEquals(404, (result as Result.Error).code)
    }

    @Test
    fun `When api call throws exception should handle and pass error`() = runTest {
        coEvery {
            service.getLatestUnsplashPhotos(photosPerPage = any(), page = any())
        }.throws(RuntimeException("Something unexpected happened"))

        val result = repo.getLatestPhotos()

        assertTrue(result is Result.Error)
        assertEquals(-1, (result as Result.Error).code)
    }

    @AfterAll
    fun tearDown() {
        unmockkStatic(Color::class)
    }
}