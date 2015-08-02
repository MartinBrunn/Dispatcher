package de.devacon.content;

/**
 * Created by @Martin@ on 18.07.2015 19:00.
 */
public interface StringContentProvider {
    String getContent();
    void attachContentSink(StringContentSink sink);
}
