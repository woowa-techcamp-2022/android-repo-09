
# Git 사용법


## 목차

* [1. Git](#1-git)
   * [1.1. Rules](#11-rules)
      + [1.1.1. Git Flow](#111-git-flow)
      + [1.1.2. Etc.](#112-etc)
   + [1.2. Branch](#12-branch)
      - [1.2.1. Branch Naming Rule](#121-branch-naming-rule)
      - [1.2.2. Prefix](#122-prefix)
      - [1.2.3. 예시](#123-예시)
   + [1.3. Issue](#13-issue)
      - [1.3.1. Issue Naming Rule](#131-issue-naming-rule)
      - [1.3.2. 예시](#132-예시)
   + [1.4. Commit](#14-commit)
      - [1.4.1. Commit Message Convention](#141-commit-message-convention)
      - [1.4.2. 예시](#142-예시)
   + [1.5. Pull Request](#15-pull-request)
   + [1.6. Code Review](#16-code-review)
   + [1.7. Label](#17-label)
   + [1.8. Project](#18-project)
   + [1.9. WIKI](#19-wiki)



<br>

## 1. Git

### 1.1. Rules

#### 1.1.1. Git Flow

기본적으로 Git Flow 전략을 이용한다. 작업 시작 시 선행되어야 할 작업은 다음과 같다.

```
1. Issue를 생성한다.
2. feature Branch를 생성한다.
3. Add - Commit - Push - Pull Request 의 과정을 거친다.
4. Pull Request가 작성되면 여유가 있으면 Code Review를 한다.
5. Code Review가 완료되면 Pull Request 작성자가 develop Branch로 merge 한다.
6. merge된 작업이 있을 경우, 다른 브랜치에서 작업을 진행 중이던 개발자는 본인의 브랜치로 merge된 작업을 Pull 받아온다.
7. 종료된 Issue와 Pull Request의 Label과 Project를 관리한다.
```

### 1.1.2. Etc.

협업 시 준수해야 할 규칙은 다음과 같다.

```
1. develop에서의 작업은 원칙적으로 금지한다. 단, README 작성은 develop Branch에서 수행한다.
2. 자신이 담당한 부분 이외에 다른 팀원이 담당한 부분을 수정할 때에는 Slack을 통해 변경 사항을 전달한다.
3. 본인의 Pull Request는 본인이 Merge한다.
4. Commit, Push, Merge, Pull Request 등 모든 작업은 앱이 정상적으로 실행되는 지 확인 후 수행한다.
5. README 수정을 위한 Commit 도배는 금지한다. 미리보기는 Preview를 통해 확인한다.
```

<br>

### 1.2. Branch

Branch의 Naming Rule은 1.2.1을 따른다. Branch는 가능한 한 작업단위, 기능단위로 생성하며 이는 Issue를 기반으로 한다. 단, 같은 범위의 기능이라면 같은 브랜치를 사용한다. 예를 들면, 회원가입 기능 구현 시 *아이디 중복 체크, 비밀번호 확인, 아이디 유효성 확인* 등은 **회원가입** 하나로 구분한다.

#### 1.2.1. Branch Naming Rule

Branch를 생성하기 전 Issue를 먼저 작성한다. Issue 작성 후 생성되는 번호와 Issue의 간략한 설명 등을 조합하여 Branch의 이름을 결정한다. `<Prefix>/<Issue_Number>` 의 양식을 따른다.

#### 1.2.2. Prefix

- `main` : 개발이 완료된 산출물이 저장될 공간
- `develop` : feature 브랜치에서 구현된 기능들이 merge될 브랜치
- `feature` : 기능을 개발하는 브랜치, 이슈별/작업별로 브랜치를 생성하여 기능을 개발한다

#### 1.2.3. 예시

```
main
develop/v1.0.0
feature/10-sign-up
```

<br>

### 1.3. Issue

작업 시작 전 Issue 생성이 선행되어야 한다. Issue는 작업 단위, 기능 단위로 생성하며 생성 후 표시되는 Issue Number를 참조하여 Branch Name과 Commit Message를 작성한다.

이슈의 제목에는 기능의 대표적인 설명을 적고, 내용에는 세부적인 내용 및 작업 진행 상황을 작성한다. 

이슈 생성 시 Github 오른편의 Assignee, Label, Project, Linked Pull Requests 를 적용한다. Assignee는 해당 이슈의 담당자, Label에는 `담당자`, `TODO`, `우선순위` 등의 Label을 추가한다. 작업 대기중인 Issue에는 `TODO`, 작업 진행 중이면 `In Progress`를, 완료된 Issue에는 `complete` Label을 추가한다.

#### 1.3.1. Issue Naming Rule

`[<PREFIX>] <Description>` 의 양식을 준수하되, Prefix는 협업하며 맞춰가기로 한다. 또한 Prefix는 대문자를 사용한다.

#### 1.3.2. 예시

```
[FEAT] 회원가입 구현
[DEBUG] MainActivity 버그 수정
[STYLE] 폰트 변경
```

<br>

### 1.4. Commit

Commit Message는 영어로 작성하는 것을 원칙으로 하되, 의미 전달이 어려운 경우 한글을 사용한다. Commit Message Convention은 다음 <sup>[[1]](#footnote1)</sup> 을 따른다.

> <a name="footnote1">[1]</a> https://github.com/4z7l/Today-I-Learned/blob/master/Git/GitCommitConvention.md 참고.



#### 1.4.1. Commit Message Convention

`[<PREFIX>] <Issue_Number> - <Description>` 의 양식을 준수한다.

- **FIX** : 버그, 오류 해결
   `[FIX] #10 - callback error`
- **ADD** : Feat 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 View나 Activity 생성
   `[ADD] #11 - LoginActivity`
   `[ADD] #12 - CircleImageView Library`
- **FEAT** : 새로운 기능 구현
   `[FEAT] #11 - google login`
- **DEL** : 쓸모없는 코드 삭제
   `[DEL] #12 - unnecessary import package`
- **DOCS** : README나 WIKI 등의 문서 개정
   `[DOCS] update readme`
- **REFACTOR** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩토링 시
   `[REFACTOR] #15 - MVP architecture to MVVM`
- **CHORE** : 그 이외의 잡일/ 버전 코드 수정, 패키지 구조 변경, 파일 이동, 가독성이나 변수명, reformat 등
   `[CHORE] #20 - delete unnecessary import package`
   `[CHORE] #21 - reformat MainActivity`
- **MOD** : xml 파일만 수정한 경우
   `[MOD] #30 - use constraintlayout in activity_main.xml`
- **TEST** : 테스트 코드 추가



#### 1.4.2. 예시

```
[접두어] #이슈번호 - 내용<br>
세부내용<br>
각주<br>
```

```
[FEAT] #11 - google login

- Use Firebase Auth (http://example.com/reference)
- Need refactor code

See Also : #100, #120
```

<br>

### 1.5. Pull Request

Pull Request의 내용에는 변경된 사항에 대한 설명이 작성되어야 한다.
템플릿을 활용하여, 관련 이슈번호와 작업한 내용을 명시한다.

<br>

### 1.6. Code Review

Code Review는 변경 사항에 대해 궁금한 점, 코드 가독성(변수명, 함수명 등)에 대한 조언 등을 작성한다. Code Review는 존댓말로 하며, 일방적인 시비나 비난은 금지한다. Pull Request 작성자는 Code Review에 대해 성실히 답변한다.

<br>

### 1.7. Label

Issue, Pull Request를 한 눈에 파악하기 위해 Label을 추가한다. 필수적으로 추가되어야 할 Label은 `Assignee` 이다.

- `Assignee` : 담당자의 Github 아이디

<br>
