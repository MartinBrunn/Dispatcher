package de.devacon.content;

/**
 * Created by @Martin@ on 18.07.2015 23:17.
 */
public class DummyHtmlContent implements StringContentProvider {
    private final String html;

    public DummyHtmlContent(int number) {
        html = "<h1>Title Dummy " + Integer.toString(number) + "</h1><p style=\"fontsize:40;\">" +
                Integer.toString(number) + "</p>" ;
    }
    @Override
    public String getContent() {
        return html ;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }
}
