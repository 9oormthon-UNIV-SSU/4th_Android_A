package com.foo.composecoffee2_1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foo.composecoffee2_1.ui.theme.Composecoffee2_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composecoffee2_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        val members = listOf(
                            TeamMember("팀장님", "카카오 CEO", "I am the best.", R.drawable.bg),
                            TeamMember("추교준", "GOOGLE CEO", "Chatgpt is GOD.", R.drawable.bg),
                            TeamMember("김성혁", "AMAZON CEO", "Go get some book", R.drawable.bg)
                        )

                        members.forEach { member ->
                            MemberCard(member)
                        }
                    }
                }
            }
        }
    }
}

data class TeamMember(
    val name: String,
    val role: String,
    val intro: String,
    val imgResId: Int
)

@Composable
fun MemberCard(member: TeamMember) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = member.imgResId),
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = member.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = member.role)
                Text(text = member.intro)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                Toast.makeText(context, "Hello ${member.name}", Toast.LENGTH_SHORT).show()
            }) {
                Text("Greet")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Composecoffee2_1Theme {
        MemberCard(
            TeamMember(
                name = "John Smith",
                role = "Developer",
                intro = "I am learning Compose.",
                imgResId = R.drawable.bg
            )
        )
    }
}
