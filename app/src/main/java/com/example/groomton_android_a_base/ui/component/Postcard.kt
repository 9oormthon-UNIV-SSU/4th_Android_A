// 파일 경로: app/src/main/java/com.example/groomton_android_a_base/ui/component/PostCard.kt
package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.groomton_android_a_base.R
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField

import com.example.groomton_android_a_base.model.Post
import com.example.groomton_android_a_base.model.Comment
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import java.text.DecimalFormat
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


fun formatLikesCount(count: Int): String {
    return when {
        count < 10000 -> count.toString()
        else -> {
            val decimalFormat = DecimalFormat("#.#")
            val inTenThousands = count.toFloat() / 10000.0f
            "${decimalFormat.format(inTenThousands)}만"
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    var commentInput by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier.fillMaxWidth(),
        Arrangement.spacedBy(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 14.dp)
                .clickable { onUserClick(post.user) }
        ) {
            GlideImage(
                model = post.user.profileImageUrl,
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = post.user.name)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* TODO: 더보기 메뉴 또는 옵션 처리 */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = "More options"
                )
            }
        }

        GlideImage(
            model = post.postImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { onLikeClick() }
                    )
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onLikeClick) {
                    Icon(
                        painter = painterResource(
                            id = if (post.isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                        ),
                        contentDescription = "Like",
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 0.dp)
                            .size(20.dp)
                    )
                }
                Text(
                    text = "${formatLikesCount(post.likesCount)}",
                    color = if (post.isLiked) Color.Black else Color.Unspecified,
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)
                )
                IconButton(onClick = { showBottomSheet = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_icon),
                        contentDescription = "Comment",
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).size(20.dp)
                    )
                }
                IconButton(onClick = { showBottomSheet = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_message_icon),
                        contentDescription = "Message",
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp).size(20.dp)
                    )
                }
            }
            Text(
                text = "좋아요 ${formatLikesCount(post.likesCount)}개",
                color = if (post.isLiked) Color.Black else Color.Unspecified,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
            )

            if (post.comments.isNotEmpty()) {
                val lastComment = post.comments.last()
                Text(
                    text = "${lastComment.user.name} ${lastComment.text}",
                    modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 4.dp, bottom = 8.dp)
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {
                CommentBottomSheetContent(
                    initialComment = "",
                    onCommentSubmit = { newComment ->
                        showBottomSheet = false
                    },
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}

// ❗ PostCard 컴포저블이 끝나는 중괄호(}) 바로 뒤에 CommentBottomSheetContent 정의가 있어야 합니다. ❗
@Composable
fun CommentBottomSheetContent(
    initialComment: String,
    onCommentSubmit: (String) -> Unit, // 댓글 제출 시 호출될 콜백
    onDismiss: () -> Unit // Bottom Sheet를 닫을 때 호출될 콜백
) {
    var currentComment by remember { mutableStateOf(initialComment) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "댓글 작성", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currentComment,
            onValueChange = { currentComment = it },
            label = { Text("댓글을 입력하세요...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text("취소")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onCommentSubmit(currentComment) },
                enabled = currentComment.isNotBlank()
            ) {
                Text("게시")
            }
        }
    }
}