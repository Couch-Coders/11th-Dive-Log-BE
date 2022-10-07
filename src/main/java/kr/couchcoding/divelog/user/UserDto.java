package kr.couchcoding.divelog.user;

public record UserDto(String id, String name, String email, String profileImage) {
    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getProfileImage());
    }
}
