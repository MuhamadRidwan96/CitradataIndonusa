package com.example.features.presentation.profile.screen.subscreen.membership

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core_ui.component.BenefitCard
import com.example.core_ui.component.CompareFeatures
import com.example.core_ui.component.SubscriptionCard
import com.example.core_ui.component.TopAppBarWithBack
import com.example.feature_login.R
import kotlinx.coroutines.launch

@Composable
fun MembershipScreen(navController: NavHostController) {
    val tabTitles = listOf("Plans", "Benefits", "Compare")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.membership),
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Choose Your Plan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Select the membership that works best for you",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .sizeIn(minHeight = 48.dp),
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    shadowElevation = 6.dp
                ) {
                    TabSection(
                        tabTitles = tabTitles,
                        selectedTabIndex = pagerState.currentPage,
                        onTabSelected = { index ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        pagerState = pagerState
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    when (page) {
                        0 -> PlansSection()
                        1 -> BenefitsContent()
                        2 -> CompareContent()
                    }
                }
            }
        }
    }
}


@Composable
private fun TabSection(
    tabTitles: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    pagerState: PagerState
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.onSecondary,
        divider = {},
        indicator = {
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(pagerState.currentPage),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun PlansSection() {
    val subscriptionPlans = rememberSubscriptionPlans()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(subscriptionPlans, key = {it.planName}) { plan ->
            SubscriptionCard(
                plan = plan,
                onSelectPlan = {}
            )
        }
    }
}


@Composable
fun BenefitsContent() {
    val benefitPlans = rememberBenefitPlans()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(benefitPlans) { benefit ->
            BenefitCard(
                benefit = benefit
            )
        }
    }
}

@Composable
fun CompareContent() {
    val features = rememberComparePlans()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) { item { CompareFeatures(features) } }
}
