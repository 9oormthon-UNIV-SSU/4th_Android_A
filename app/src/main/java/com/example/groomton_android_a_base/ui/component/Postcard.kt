import androidx.compose.foundation.Image
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

import com.example.groomton_android_a_base.model.Post // 이 import는 Post 데이터 클래스가 다른 파일에 정의되어 있을 때 필요합니다.
//상태관리
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button // 댓글 게시 버튼용
import androidx.compose.material3.ButtonDefaults // 버튼 색상용
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color

// 만약 Post 데이터 클래스가 이 파일에 정의되어 있다면 위의 import는 필요 없고,
// 이 파일 내에 정의된 Post 클래스를 사용하면 됩니다.
// 현재 코드에서는 파일 내에 다시 정의되어 있으므로, 둘 중 하나를 선택해야 합니다.
// 보통은 model/Post.kt와 같이 별도 파일에 정의하고 import하여 사용합니다.
// 여기서는 코드의 다른 부분과 충돌을 피하기 위해 임시로 주석 처리하거나,
// 실제 프로젝트 구조에 맞게 하나만 사용하세요.
/*
data class Post(
    val profileImage: Int,
    val username: String,
    val postImage: Int,
    val isLiked: Boolean,
    val comment: String
)
*/
@OptIn(ExperimentalMaterial3Api::class) // ModalBottomSheet 사용 시 필요
@Composable
fun PostCard(post: Post, onLikeClick: () -> Unit) {
    var commentInput by remember { mutableStateOf(post.comment) }
    var showBottomSheet by remember { mutableStateOf(false) }
    // Bottom Sheet의 상태를 제어하기 위한 state (애니메이션 등)
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // 부분적으로 확장되는 상태 건너뛰고 완전히 확장되도록
    )


    Column(
        Modifier.fillMaxWidth(), Arrangement.spacedBy(0.dp)
        // Column 내부 요소 간 기본 간격 제거
    ) {
        // 프로필 이미지 및 사용자 이름 Row - 세로 패딩 최소화
        Row(
            verticalAlignment = Alignment.CenterVertically,
            // 세로 패딩을 0.dp 또는 1.dp로 거의 제거
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = post.profileImage),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            // Spacer의 가로 간격만 유지, 세로 간격은 없음
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = post.username)
        }

        Image(
            painter = painterResource(id = post.postImage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
        )

        // 좋아요 버튼 Row - 세로 패딩 최소화
        Row(
            // 세로 패딩을 0.dp 또는 1.dp로 거의 제거
            modifier = Modifier.padding(vertical = 0.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically // <<<<< 이 부분을 추가합니다!

        ) {
            // IconButton 자체에 기본 패딩이 있을 수 있으나, 외부 Row에서 제어
            IconButton(onClick = onLikeClick) {
                Icon(
                    painter = painterResource(
                        id = if (post.isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                    ),
                    contentDescription = "Like"
                )
            }
            IconButton(onClick = { showBottomSheet = true }) { // 클릭 시 showBottomSheet를 true로 설정
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_icon), // <<<<<<< 채팅 아이콘 리소스
                    contentDescription = "Comment"
                )
            }
            Text(
                text = "좋아요 ${if (post.isLiked) 1 else 0}개",
                // 세로 패딩을 0.dp 또는 1.dp로 거의 제거
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
            )
            if (post.comment.isNotBlank()) {
                Text(
                    text = "${post.username} ${post.comment}", // 실제 댓글은 누가 달았는지에 따라 달라짐
                    modifier = Modifier.padding( top = 4.dp, bottom = 8.dp)
                )}
        }

        // 좋아요 개수 텍스트 - 세로 패딩 최소화


        // OutlinedTextField - 외부 패딩 최소화 및 내부 패딩 고려
//        OutlinedTextField(
//            value = commentInput, // <<<<< `commentInput` 상태 변수를 `value`로 사용
//            onValueChange = { newValue ->
//                // <<<<< `onValueChange`에서 `commentInput` 상태를 `newValue`로 업데이트
//                commentInput = newValue
//                // (선택 사항) 만약 Post 객체의 `comment` 필드도 즉시 업데이트하고 싶다면:
//                // post.comment = newValue
//                // 하지만 일반적으로 댓글 게시는 별도의 버튼 클릭 시 처리됩니다.
//            },
//            placeholder = { Text("댓글 달기…") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 0.dp, bottom = 13.dp) // bottom 패딩 유지
//        )
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }, // 뒷 배경 클릭 또는 스와이프 다운 시 닫기
                sheetState = sheetState,
                // windowInsets 파라미터 제거 (Material3 버전에 따라 없을 수 있음)
                // 대신, CommentBottomSheetContent 내부에서 navigationBarsPadding()을 사용합니다.
            ) {
                // Bottom Sheet 내부의 내용 (댓글 화면 UI)
                CommentBottomSheetContent(
                    initialComment = post.comment, // 현재 게시물의 댓글을 초기값으로 전달
                    onCommentSubmit = { newComment ->
                        // 여기에서 실제로 댓글을 게시하는 로직 (예: 서버에 전송)
                        post.comment = newComment // <<<<< Post 객체의 comment 업데이트
                        showBottomSheet = false // 댓글 게시 후 Bottom Sheet 닫기
                    },
                    onDismiss = { showBottomSheet = false } // 닫기 버튼 클릭 시 닫기
                )
            }
        }
    }
}

// CommentBottomSheetContent Composable 정의
// 이 함수는 PostCard Composable 함수가 끝나는 중괄호(}) 바로 뒤에 위치해야 합니다.
@Composable
fun CommentBottomSheetContent(
    initialComment: String,
    onCommentSubmit: (String) -> Unit, // 댓글 제출 시 호출될 콜백
    onDismiss: () -> Unit // Bottom Sheet를 닫을 때 호출될 콜백
) {
    // 댓글 입력 필드의 상태 (Bottom Sheet 내에서만 관리)
    var currentComment by remember { mutableStateOf(initialComment) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // 시스템 내비게이션 바와 겹치지 않도록 패딩 추가
            .padding(16.dp), // Bottom Sheet 내부의 전체 패딩
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
            singleLine = false, // 여러 줄 입력 가능
            maxLines = 5 // 최대 5줄
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors() // Outline 스타일 버튼
            ) {
                Text("취소")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onCommentSubmit(currentComment) },
                enabled = currentComment.isNotBlank() // 댓글 내용이 있어야 활성화
            ) {
                Text("게시")
            }
        }
    }
}