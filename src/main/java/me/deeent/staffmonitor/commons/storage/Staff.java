package me.deeent.staffmonitor.commons.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Staff {

    private final UUID uuid;

    private final long join;
    @Setter private long leave;
    private final long allTime;

    public Staff(UUID uuid, long allTime) {
        this.uuid = uuid;

        this.join = System.currentTimeMillis();
        this.leave = 0;
        this.allTime = allTime;
    }

    public long getCurrentActivity() {
        return (System.currentTimeMillis() - join);
    }

    public long getAllActivity() {
        return (allTime + getCurrentActivity());
    }

}
