package data;

import java.time.LocalDateTime;

class Registrations {
    private LocalDateTime date;
    private boolean userLeft;
    private String kickedBy;

    public Registrations(LocalDateTime date, boolean left, String kickedBy) {
        this.date = date;
        this.userLeft = userLeft;
        this.kickedBy = kickedBy;
    }

    /**
     * Returns the name of the user who kicked this user from the server.
     * <p>
     * If the return value is {@code null}, it means the user either joined
     * the server voluntarily or left on their own.
     * If a {@link String} is returned, it contains the name of the user who performed the kick.
     *
     * @return the name of the user who kicked this user, or {@code null} if the user was not kicked
     */
    public String getKickedBy() {
        if (userLeft)
            return kickedBy;
        return null;
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Indicates whether the user successfully joined the server.
     * <p>
     * If the return value is {@code true}, the user joined the server.
     * If the return value is {@code false}, the user either userLeft voluntarily or was kicked.
     *
     * @return {@code true} if the user joined the server; {@code false} otherwise
     */
    public boolean getUserLeft() {
        return userLeft;
    }

}
