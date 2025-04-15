package com.example.study02

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week_02.ui.theme.Week_02Theme
import com.example.week_02.R
import com.example.week_02.TeamMember

val sampleTeam = listOf(
    TeamMember("정재황", "팀장", "팀 전체를 리드합니다.", R.drawable.baseline_person_24),
    TeamMember("최지웅", "디자이너", "사용자 경험을 책임집니다.", R.drawable.baseline_person_24),
    TeamMember("김성혁", "프론트엔드 개발자", "프론트 기술 구현을 맡고 있습니다.", R.drawable.baseline_person_24),
    TeamMember("추교준", "백엔드 개발자", "백엔드 기술 구현을 맡고 있습니다.", R.drawable.baseline_person_24)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week_02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TeamMemberCardList(sampleTeam,innerPadding)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Week_02Theme {
        val sampleTeam = listOf(
            TeamMember("정재황", "팀장", "팀 전체를 리드합니다.", R.drawable.baseline_person_24),
            TeamMember("최지웅", "디자이너", "사용자 경험을 책임집니다.", R.drawable.baseline_person_24),
            TeamMember("김성혁", "프론트엔드 개발자", "프론트 기술 구현을 맡고 있습니다.", R.drawable.baseline_person_24),
            TeamMember("추교준", "백엔드 개발자", "백엔드 기술 구현을 맡고 있습니다.", R.drawable.baseline_person_24)
        )
        TeamMemberCardList(sampleTeam, PaddingValues(8.dp))
    }
}

@Composable
fun TeamMemberCardList(members: List<TeamMember>, innerpadding : PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        members.forEach { member ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = member.imgResId),
                        contentDescription = null,
                        modifier = Modifier
                            .height(80.dp)
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column {
                        Text(text = member.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = member.role, style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = member.intro, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        val context = LocalContext.current
                        Button(onClick = {
                            Toast.makeText(context, "${member.name} : 더 알아보기 클릭!", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("더 알아보기")
                        }
                    }
                }
            }
        }
    }
}
