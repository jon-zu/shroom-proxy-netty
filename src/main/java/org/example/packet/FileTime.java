package org.example.packet;

public class FileTime {
    private final long secs;
    private final long nanos;

    private static final long FT_EPOCH = 116444736000000000L;
    private static final long FT_TICKS_PER_NANOSECOND = 10000000L;

    private static final FileTime FT_MIN = new FileTime(94354848000000000L);
    private static final FileTime FT_MAX = new FileTime(150842304000000000L);


    public FileTime(long secs, long nanos) {
        this.secs = secs;
        this.nanos = nanos;
    }

    public FileTime(long fileTime) {
        if (fileTime < 0)
            throw new IllegalArgumentException("Filetime must be positive");

        this.secs = fileTime / FT_TICKS_PER_NANOSECOND;
        this.nanos = fileTime % FT_TICKS_PER_NANOSECOND;
    }

    public FileTime() {
        this(0, 0);
    }

    public static FileTime fromMillis(long millis) {
        return new FileTime(millis * 10000 + FT_EPOCH);
    }

    public static FileTime fromNanos(long nanos) {
        return new FileTime(nanos + FT_EPOCH);
    }

    public static FileTime now() {
        return fromNanos(System.nanoTime());
    }

    public long getFileTime() {
        return secs * FT_TICKS_PER_NANOSECOND + nanos;
    }

    public boolean isMax() {
        return this == FT_MAX;
    }

    public boolean isMin() {
        return this == FT_MIN;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FileTime ft) {
            return ft.secs == secs && ft.nanos == nanos;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(secs) ^ Long.hashCode(nanos);
    }
}