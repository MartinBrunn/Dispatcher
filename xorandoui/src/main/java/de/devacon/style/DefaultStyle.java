package de.devacon.style;

/**
 * Created by @Martin@ on 18.07.2015 19:09.
 */
public class DefaultStyle implements StyleProvider {
    @Override
    public String getStyleSheet() {
        String style = "h1 { color: blue ; } \n bold { color: red } \n";
        return style;
    }
}
