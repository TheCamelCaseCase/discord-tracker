package activity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class UserActivity {
    private final String id;
    private boolean isOnline;
    private LocalDateTime offlineDateTime;
    private Period period;
    private Duration duration;
    /*
    Diese Funktionen werden später eingebaut:
    - Wie lange online, wenn im Voicechat?
    - Welcher Chat/Voicechat?
    - Was sie streamten?
    - Was sie spielen und wie viel?
    - Namensänderungen
     */
    public UserActivity(String id) {
        this.id = id;
    }
}
