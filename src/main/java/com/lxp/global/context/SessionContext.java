package com.lxp.global.context;

import static com.lxp.support.StringUtils.isNull;

/**
 * 애플리케이션의 현재 로그인된 사용자 상태(세션)를 관리하는 싱글톤 클래스입니다.
 * <p>
 * 이 클래스는 현재 세션 사용자 ID를 저장 및 조회하며,
 * 애플리케이션의 전역적인 상태(Context) 관리를 담당합니다.
 *
 * <h3>세션 사용하기</h3>
 * <pre>
 *     //세션에 있는 사용자의 아이디가 일치하는지 확인
 *     boolean flag = SessionContext.getInstance().isSameId(request.userId());
 * </pre>
 */
public class SessionContext {

    private static final SessionContext INSTANCE = new SessionContext();

    private Long currentUserId;

    private SessionContext() {
    }

    public static SessionContext getInstance() {
        return INSTANCE;
    }

    /**
     * 사용자 ID를 설정하여 로그인 상태로 만듭니다.
     */
    public void setUserId(Long userId) {
        this.currentUserId = userId;
    }

    /**
     * 현재 로그인된 사용자 ID를 반환합니다.
     */
    public Long getUserId() {
        return currentUserId;
    }

    /**
     * 현재 로그인된 사용자와 주어진 ID가 동일한지 확인합니다.
     *
     * @param userId 비교할 사용자 ID
     * @return boolean 현재 로그인된 사용자와 ID가 같으면 true
     */
    public boolean isSameId(Long userId) {
        if (checkId(userId)) {
            return false;
        }

        // Long 타입 비교 시 NullPointerException 방지 및 값 비교를 위해 equals 사용
        return this.currentUserId.equals(userId);
    }

    /**
     * 사용자 ID를 null로 설정하여 로그아웃합니다.
     */
    public void clear() {
        this.currentUserId = null;
    }

    /**
     * 현재 로그인 상태인지 확인합니다.
     */
    public boolean isLoggedIn() {
        return currentUserId != null;
    }

    private boolean checkId(Long userId) {
        return isNull(this.currentUserId) || isNull(userId);
    }

}
