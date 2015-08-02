package de.devacon.content;

/**
 * Created by @Martin@ on 19.07.2015 01:06.
 */
public interface StringContentSink {
    void setContentProvider(StringContentProvider provider);
    void onContentChange() ;
}
