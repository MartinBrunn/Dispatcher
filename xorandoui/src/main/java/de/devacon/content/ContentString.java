package de.devacon.content;

/**
 * Created by @Martin@ on 18.07.2015 23:14.
 */
public class ContentString implements StringContentProvider {
    private String string = null;

    ContentString(String string) {
        this.string = string;
    }
    @Override
    public String getContent() {
        return string;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }
}
