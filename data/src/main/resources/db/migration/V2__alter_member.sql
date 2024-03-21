-- delete_at 컬럼 삭제
ALTER TABLE member
    DROP COLUMN deleted_at;

-- delted_at과 묶여있던 nickname, email 멀티 유니크 인덱스 삭제
ALTER TABLE member
    DROP INDEX member_unique_idx_nickname_and_deleted_at;
ALTER TABLE member
    DROP INDEX member_unique_idx_email_and_deleted_at;

-- nickname 컬럼 타입 변경
ALTER TABLE member
    MODIFY nickname VARCHAR (50) NULL;

-- nickname, email 컬럼에 유니크 인덱스 추가
ALTER TABLE member
    ADD UNIQUE INDEX member_unique_idx_nickname (nickname);
ALTER TABLE member
    ADD UNIQUE INDEX member_unique_idx_email (email);
