package de.devacon.content;

/**
 * Created by @Martin@ on 22.07.2015 14:09.
 */
public interface GageSink {
    void onChanged() ;
    void setGageSource(GageSource gageSource);
}
