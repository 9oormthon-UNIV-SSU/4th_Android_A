# 4th_Android_A

**구름톤 유니브 숭실대 4기 - Android 스터디 A조**

Jetpack Compose 기반의 Android 개발을 중심으로, 실습과 코드 리뷰를 통해 실력을 향상시키는 10주차 스터디입니다.

> 📚 각 주차별 워크북 및 상세한 과제 내용은 Notion에서 안내됩니다.  

---

## 👨‍🏫 브랜치 전략 (스터디 특화 Git Flow)

스터디 목적에 맞게, 각 스터디원이 **자신만의 develop 브랜치**를 만들어 실습을 병렬로 진행합니다.

### 📌 브랜치 구조 예시
	
	main                         ← 통합/결과용 (스터디장 관리)
	├── develop-kyojoon          ← Kyojoon 전용 develop 브랜치
	│   └── feature/week06       ← Kyojoon’s 실습 브랜치
	├── develop-sunghyuk         ← 팀원 Sunghyuk 전용 develop 브랜치
	└── feature/week06       ← Sunghyuk’s 실습 브랜치
	
	> 각자 develop 브랜치는 서로 충돌 없이 독립적으로 실습할 수 있는 공간입니다.

---

## ✅ 주차별 브랜치 생성 가이드

### 1. 본인의 develop 브랜치 생성 (최초 1회)

	```bash
	git checkout main
	git pull origin main
	git checkout -b develop-kyojoon
	git push origin develop-kyojoon
	
	2. 실습 브랜치 생성 (매주)
	
	git checkout develop-kyojoon
	git pull origin develop-kyojoon
	git checkout -b feature/week06
	
	※ 각자 develop 브랜치에서 주차별 feature 브랜치를 따로 생성해 실습합니다.
	
	⸻

🧑‍💻 과제 제출 가이드
		1.	클론 (최초 1회):
	
	git clone https://github.com/YOUR-ID/4th_Android_A.git
	cd 4th_Android_A
	
		2.	작업 디렉토리 생성:
	
	mkdir -p week06/kyojoon
	
		3.	코드 작성 후 커밋/푸시:
	
	git add .
	git commit -m "feat: 6주차 과제 완료"
	git push origin feature/week06
	
		4.	PR 작성 (대상 브랜치: develop-kyojoon)
	
		•	PR 제목 예시: [6주차] kyojoon - Navigation + Scaffold 구현

✅ 주의: PR은 develop 브랜치로! main 브랜치에는 파트장이 선택하여 통합합니다.

⸻

📂 폴더 및 네이밍 규칙

항목	예시
브랜치명	develop-kyojoon, feature/week06
디렉토리 경로	week06/kyojoon/
PR 제목	[6주차] kyojoon - Scaffold UI 과제
커밋 메시지	feat: 6주차 과제 완료


⸻

💻 개발 환경
	•	Android Studio Hedgehog 이상
	•	Kotlin
	•	Jetpack Compose (Material3)
	•	Git / GitHub
	•	Hilt, Retrofit (선택적 활용)

⸻

🙋‍♀️ 기타 안내
	•	질문은 GitHub Discussions 또는 Discord 채널에서 자유롭게!
	•	매주 스터디장이 과제를 GitHub 이슈로 공지합니다.
	•	개인/팀 프로젝트도 7주차 이후 별도로 진행될 예정입니다.

⸻

👨‍🏫 스터디장: @deephoon

궁금한 점은 언제든지 연락 주세요 🙂

---
