package com.mey.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mey.newsapp.R
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.presentation.bookmark.BookmarkScreen
import com.mey.newsapp.presentation.bookmark.BookmarkViewModel
import com.mey.newsapp.presentation.details.DetailsEvent
import com.mey.newsapp.presentation.details.DetailsScreen
import com.mey.newsapp.presentation.details.DetailsViewModel
import com.mey.newsapp.presentation.home.HomeScreen
import com.mey.newsapp.presentation.home.HomeViewModel
import com.mey.newsapp.presentation.navgraph.Route
import com.mey.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.mey.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.mey.newsapp.presentation.search.SearchScreen
import com.mey.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, "Bookmark"),
        )
    }
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.BookmarkScreen.route
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, route = Route.HomeScreen.route)
                            1 -> navigateToTab(navController, route = Route.SearchScreen.route)
                            2 -> navigateToTab(navController, route = Route.BookmarkScreen.route)
                        }
                    }
                )
            }

        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(navController, route = Route.SearchScreen.route)
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(
                route = Route.SearchScreen.route
            ) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, articles = article)
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null){
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Articles>("article")?.let { article ->
                    DetailsScreen(
                        articles = article,
                        event = viewModel::onEvent,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            articles = article
                        )
                    }
                )

            }

        }
    }

}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, articles: Articles) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", articles)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}