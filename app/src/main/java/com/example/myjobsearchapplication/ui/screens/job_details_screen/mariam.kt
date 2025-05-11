package com.example.myjobsearchapplication.ui.screens.job_details_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.myjobsearchapplication.ui.theme.JobSearchApplicationTheme
import com.example.myjobsearchapplication.R





@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun JobDetailsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth() )
                    {Text("Job Detail", fontWeight = FontWeight.Bold ,
                        modifier = Modifier.padding(end = 48.dp), color= Color.White)} },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back action */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                            modifier = Modifier.size(28.dp), tint= Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Share action */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share",
                            modifier = Modifier.size(28.dp), tint= Color.White )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id= R.color.teal_700),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                JobDetailsContent()
            }
        }
    )
}

@Composable
fun JobDetailsContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Kotlin Programmer",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.teal_700),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "TestSoft",
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon",
                            tint = colorResource(id = R.color.orange),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Los Angeles, USA",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoCard(
                            "Salary (Monthly)",
                            "$8.000",
                            Icons.Default.Lock,
                            Modifier.height(50.dp)
                        )
                        InfoCard(
                            "Job Type", "Full-Time",
                            Icons.Default.ShoppingCart,
                            Modifier.fillMaxWidth(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoCard(
                            "Work Place",
                            "Remote",
                            Icons.Default.Home,
                            Modifier.fillMaxWidth(1f)
                        )
                        InfoCard(
                            "Level",
                            "Internship",
                            Icons.Default.Person,
                            Modifier.fillMaxWidth(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    TabSection()
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
        Button(
            onClick = { /* TODO: Apply Action */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF018786))
        )
        {
            Text("Apply for job", color = Color.White)
        }
    }
}



@Composable
fun InfoCard(title: String, value: String, icon: ImageVector, modifier: Modifier) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(100.dp)
            .padding(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorResource(id = R.color.orange),
                modifier = Modifier.size(14.dp)

            )
            Text(
                text = title,
                fontSize = 15.sp,
                color = colorResource(id = R.color.teal_700)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun TabSection() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("About", "Company", "Review")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Black,
            contentColor = colorResource(id = R.color.teal_700)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            title,
                            color = if (selectedTab == index) colorResource(id = R.color.teal_700) else Color.Gray
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTab) {
            0 -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "About the job",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "We are looking for a talented and motivated hire to join our team. As a developer on this program you'll work on new features and we'll be responsible for this job.",
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Job Description",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "We are looking for a talented and motivated hire to join our team.",
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}