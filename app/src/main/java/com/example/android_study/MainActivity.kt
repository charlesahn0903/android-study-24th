package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

data class User(val name: String, val age: Int)

@Composable
fun MainScreen() {
    // TODO 1. 현재 선택된 탭 상태를 만들어보세요.
    // 힌트: remember, mutableStateOf 사용
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("홈") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    // TODO 2. 검색 탭을 누르면 selectedTab이 1이 되도록 작성하세요.
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("검색") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    // TODO 3. 마이페이지 탭을 누르면 selectedTab이 2가 되도록 작성하세요.
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("마이") }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // TODO 4. selectedTab 값에 따라 다른 화면을 보여주세요.
            when (selectedTab) {
                0 -> HomeFlowScreen()
                1 -> SearchScreen()
                2 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeFlowScreen() {
    // TODO 5. 선택된 사용자를 저장할 상태를 만들어보세요.
    // 처음에는 아무도 선택되지 않았으므로 null입니다.
    var selectedUser by remember { mutableStateOf<User?>(null) }

    if (selectedUser == null) {
        UserListScreen(
            onClick = { user ->
                // TODO 6. 클릭한 user를 selectedUser에 저장하세요.
                selectedUser = user
            }
        )
    } else {
        UserDetailScreen(
            user = selectedUser!!,
            onBack = {
                // TODO 7. 뒤로가기 버튼을 누르면 다시 목록으로 돌아가도록 null을 넣으세요.
                selectedUser = null
            }
        )
    }
}

@Composable
fun UserListScreen(onClick: (User) -> Unit) {
    val users = listOf(
        User("준이", 23),
        User("준삼", 30),
        User("준사", 20)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("홈 화면", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // TODO 8. 세로 리스트를 만들기 위한 컴포넌트를 작성하세요.
        LazyColumn {
            // TODO 9. users 리스트를 하나씩 꺼내 화면에 보여주세요.
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // TODO 10. 카드를 누르면 현재 user를 onClick으로 전달하세요.
                            onClick(user)
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = user.name)
                        Text(text = "${user.age}세")
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailScreen(user: User, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("상세 화면", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // TODO 11. 전달받은 user의 이름과 나이를 출력하세요.
        Text("이름: ${user.name}")
        Text("나이: ${user.age}세")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            // TODO 12. 버튼을 누르면 onBack 함수가 실행되게 하세요.
            onClick = onBack
        ) {
            Text("뒤로가기")
        }
    }
}

@Composable
fun SearchScreen() {
    // TODO 13. 검색어를 저장할 상태를 만들어보세요.
    var keyword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("검색 화면", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            // TODO 14. 현재 검색어 상태를 연결하세요.
            value = keyword,

            // TODO 15. 입력값이 바뀌면 keyword에 저장하세요.
            onValueChange = { keyword = it },

            label = { Text("검색어 입력") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("입력한 검색어: $keyword")
    }
}

@Composable
fun ProfileScreen() {
    // TODO 16. 좋아요 상태를 만들어보세요.
    var liked by remember { mutableStateOf(false) }

    // TODO 17. count 상태를 만들어보세요.
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("마이페이지", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("여기는 마이페이지 탭입니다.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // TODO 18. 버튼을 누르면 liked 값이 반대로 바뀌게 하세요.
                liked = !liked
            }
        ) {
            Text(if (liked) "좋아요 취소" else "좋아요")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // TODO 19. 버튼을 누르면 count가 1씩 증가하게 하세요.
                count ++
            }
        ) {
            Text("Count: $count")
        }
    }

}
@Preview()
@Composable
fun DefaultPreview() {
    MainScreen()
}