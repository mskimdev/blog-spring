-- User 테이블 데이터 (5명의 사용자)
INSERT INTO user_tb (username, password, email, created_at) VALUES
                                                                ('admin', '1234', 'admin@blog.com', NOW()),
                                                                ('ssar', '1234', 'ssar@nate.com', NOW()),
                                                                ('cos', '1234', 'cos@gmail.com', NOW()),
                                                                ('hong', '1234', 'hong@naver.com', NOW()),
                                                                ('kim', '1234', 'kim@daum.net', NOW());


-- admin 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
                                                               ('블로그 개설을 환영합니다!', '안녕하세요! 새로운 블로그가 오픈했습니다. 많은 관심과 참여 부탁드립니다.', 1, NOW()),
                                                               ('공지사항: 이용수칙 안내', '블로그 이용 시 지켜야 할 기본적인 수칙들을 안내드립니다. 건전한 소통 문화를 만들어가요.', 1, NOW()),
                                                               ('업데이트 소식', '새로운 기능들이 추가되었습니다. 댓글 기능과 좋아요 기능을 곧 만나보실 수 있습니다.', 1, NOW());



-- ssar 사용자가 작성한 게시글 (3개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
                                                               ('Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다. JPA가 정말 편리하네요!', 2, NOW()),
                                                               ('JPA 연관관계 정리노트', '오늘 배운 @ManyToOne, @OneToMany 연관관계에 대해 정리해봤습니다. 헷갈리는 부분이 많아요.', 2, NOW()),
                                                               ('코딩테스트 문제 추천', '백준과 프로그래머스에서 풀어볼 만한 문제들을 추천드립니다. 알고리즘 공부 화이팅!', 2, NOW());

-- cos 사용자가 작성한 게시글 (2개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
                                                               ('React vs Vue 비교', '프론트엔드 프레임워크 선택에 고민이 많았는데, 각각의 장단점을 비교해봤습니다.', 3, NOW()),
                                                               ('개발자 취업 팁 공유', '신입 개발자로 취업하면서 도움이 되었던 팁들을 공유합니다. 포트폴리오가 중요해요!', 3, NOW());

-- hong 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
    ('첫 번째 게시글입니다', '안녕하세요! 블로그에 처음 글을 올려봅니다. 앞으로 자주 소통해요~', 4, NOW());

-- kim 사용자가 작성한 게시글 (1개)
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
    ('맛집 추천 - 강남역 근처', '강남역 근처에서 점심 먹기 좋은 맛집들을 추천드립니다. 가성비도 좋아요!', 5, NOW());

INSERT INTO reply_tb (board_id, user_id, content, created_at) VALUES
    (1, 2, '블로그 오픈 축하드립니다!', NOW()),
    (1, 3, '좋은 공간이 될 것 같아요 :)', NOW()),
    (1, 4, '반갑습니다~ 자주 올게요!', NOW()),
    (2, 2, '이용수칙 잘 확인했습니다.', NOW()),
    (2, 5, '좋은 공지 감사합니다.', NOW()),
    (3, 3, '댓글 기능 빨리 보고 싶어요!', NOW()),
    (4, 1, 'Spring Boot 정말 편리하죠! 저도 애용하고 있어요.', NOW()),
    (4, 3, 'JPA 익숙해지면 진짜 개발이 즐거워집니다 ㅎㅎ', NOW()),
    (4, 5, '좋은 후기 감사해요. 저도 배우는 중입니다!', NOW()),
    (5, 1, '연관관계 매핑이 처음엔 어렵지만 금방 익숙해져요.', NOW()),
    (5, 4, '저도 이 부분에서 많이 헤맸어요. 정리 감사합니다!', NOW()),
    (6, 2, '알고리즘 공부 같이 해요!', NOW()),
    (6, 3, '백준 추천 문제 더 알려주세요~', NOW()),
    (7, 1, 'React 먼저 배우는 걸 추천드려요!', NOW()),
    (7, 2, '저는 Vue가 더 편하던데, 취향 차이인 것 같아요.', NOW()),
    (8, 2, '포트폴리오 어떻게 구성하셨나요?', NOW()),
    (8, 4, '유익한 글 감사합니다. 취준 중인데 도움이 많이 됐어요!', NOW()),
    (9, 1, '첫 글 환영해요!', NOW()),
    (9, 3, '앞으로 좋은 글 많이 올려주세요~', NOW()),
    (10, 1, '강남역 맛집 정보 감사해요!', NOW()),
    (10, 2, '어디어디인지 구체적인 상호명도 알려주세요 ㅎㅎ', NOW());