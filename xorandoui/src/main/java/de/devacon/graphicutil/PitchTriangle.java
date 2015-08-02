package de.devacon.graphicutil;



/**
 * Created by @Martin@ on 21.07.2015 12:49.
 */


public class PitchTriangle extends Triangle {
    protected float angle = 0;
    protected float radius = 0;
    protected float len = 0;
    protected float widthFactor = 0.01f;
    protected float xcenter = 0;
    protected float ycenter = 0 ;
    private PitchTriangle(float radius,float angle, float len,float widthFactor ) {
        super();
        this.angle = angle;
        this.len = len;
        this.radius = radius;
        this.widthFactor = widthFactor;

        this.xcenter = xcenter;
        this.ycenter = ycenter;
    }

    /**
     * @param xCenter
     * @param yCenter
     * @param radius
     * @param angle
     * @param len
     * @param widthFactor
     */
    public PitchTriangle(float xCenter,float yCenter,float radius,float angle,float len,float widthFactor) {
        this(radius,angle,len,widthFactor);
        this.xcenter = xCenter;
        this.ycenter = yCenter;
        calc();
    }

    private void calc() {
        float red = radius - len;
        double adjust = Math.PI * 2 *widthFactor;
        setPoint(0,xcenter + (float)Math.cos(angle)*red,ycenter + (float)Math.sin(angle)*red);
        setPoint(1,xcenter + (float)Math.cos(angle + adjust)*radius,
                ycenter + (float)Math.sin(angle + adjust)*radius);
        setPoint(2,xcenter + (float)Math.cos(angle - adjust)*radius,
                ycenter + (float)Math.sin(angle - adjust)*radius);

    }
}
