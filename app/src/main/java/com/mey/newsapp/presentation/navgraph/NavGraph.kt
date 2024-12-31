package com.mey.newsapp.presentation.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mey.newsapp.presentation.bookmark.BookmarkScreen
import com.mey.newsapp.presentation.bookmark.BookmarkViewModel
import com.mey.newsapp.presentation.home.HomeScreen
import com.mey.newsapp.presentation.home.HomeViewModel
import com.mey.newsapp.presentation.onboarding.OnBoardingScreen
import com.mey.newsapp.presentation.onboarding.OnBoardingViewModel
import com.mey.newsapp.presentation.search.SearchScreen
import com.mey.newsapp.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ){
            composable(route = Route.NewsNavigatorScreen.route){
                val viewModel : BookmarkViewModel = hiltViewModel()
                BookmarkScreen(
                    state = viewModel.state.value,
                    navigate = {}
                )

                /*val viewModel : SearchViewModel = hiltViewModel()
                SearchScreen(state = viewModel.state.value, event = viewModel::onEvent, navigate = {})*/
                /*val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigate = {}
                )*/
            }
        }
    }
}