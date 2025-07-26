package com.example.groomton_android_a_base.ui.component.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.groomton_android_a_base.model.Comment
import com.example.groomton_android_a_base.model.Feed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(feed: Feed?, sheetState: SheetState, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    if (feed != null){
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            windowInsets = WindowInsets.navigationBars, //네비게이션 바 영역 침범하지 않도록 설정
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(WindowInsets.statusBars.asPaddingValues()) // 상태바 영역만큼 추가 패딩
            ) {
                Text(
                    text = "${feed.user.name}님의 게시물 댓글",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                CommentsSection(comments = feed.comments)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}

@Composable
fun CommentsSection(comments: List<Comment>, modifier: Modifier = Modifier){
    if(comments.isEmpty()){
        Column {
            Text(
                text = "아직 댓글이 없습니다.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
        }
        return
    }
    LazyColumn(modifier = modifier) {
        items(comments){ comment ->
            CommentItem(comment = comment)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun CommentItem(comment: Comment, modifier: Modifier = Modifier){
    ListItem(
        headlineContent = {
            Text(
                text = comment.user.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall
            )
        },
        supportingContent = {
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingContent = {
            ProfileIcon(user = comment.user, storyBorderWidth = 2.5.dp, iconSize = 48.dp)
        },
        modifier = modifier.padding(vertical = 8.dp)
    )
}