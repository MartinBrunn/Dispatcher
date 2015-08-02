package de.devacon.content;

/**
 * Created by @Martin@ on 22.07.2015 14:10.
 */
public interface GageSource {
    Object getValue(Object which);
    void attachSink(GageSink sink);
}
