// 파일 경로: app/src/main/java/com.example/groomton_android_a_base/ui/component/PostCard.kt
package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.Image
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
// ❗ onLikeClick과 onUserClick 파라미터 수정 ❗
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit, // ❗ 파라미터가 없는 함수로 수정 ❗
    onUserClick: (User) -> Unit, // ❗ onUserClick 콜백 추가 ❗
    modifier: Modifier = Modifier
) {
    var commentInput by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier.fillMaxWidth(),
        Arrangement.spacedBy(0.dp)
    ) {
        // 프로필 이미지 및 사용자 이름 Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 14.dp)
                // ❗ onUserClick 콜백을 호출하도록 수정 ❗
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

        // 게시물 이미지
        GlideImage(
            model = post.postImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
                .pointerInput(Unit) {
                    detectTapGestures(
                        // ❗ onDoubleTap에서 onLikeClick() 호출 ❗
                        onDoubleTap = { onLikeClick() }
                    )
                },
            contentScale = ContentScale.Crop
        )

        // 좋아요/댓글 아이콘 섹션
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // ❗ IconButton에서 onLikeClick() 호출 ❗
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
                        // PostCard가 아닌 ViewModel에서 상태를 관리해야 합니다.
                        showBottomSheet = false
                    },
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}
// ... (나머지 코드 유지) ...