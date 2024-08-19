package com.hardcodecoder.wallpalette.ui.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.hardcodecoder.wallpalette.R
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.ui.core.PaginatedLazyPhotoGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<SearchViewModel>()
    SearchView(
        modifier = modifier,
        searchResult = viewModel.searchResult.collectAsState(),
        scrollPositionChanged = { viewModel.updateScrollPosition(it) }
    ) {
        viewModel.searchPhotos(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    searchResult: State<List<Photo>>,
    scrollPositionChanged: (Int) -> Unit,
    search: (String) -> Unit
) {
    val queryText = rememberSaveable {
        mutableStateOf("")
    }

    SearchBar(
        query = queryText.value,
        tonalElevation = 0.dp,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_hint)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    queryText.value = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(id = R.string.clear)
                )
            }
        },
        onQueryChange = {
            queryText.value = it
        },
        onSearch = {
            search(it)
        },
        active = true,
        onActiveChange = {}
    ) {
        PaginatedLazyPhotoGrid(
            data = searchResult,
            scrollPositionChanged = { scrollPositionChanged(it) }
        ) {
        }
    }
}


@Preview(
    device = "id:pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}