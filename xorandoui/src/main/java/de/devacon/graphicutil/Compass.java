package de.devacon.graphicutil;

import android.graphics.Canvas;
import android.graphics.Path;

import de.devacon.graphics.Vector3D;

/**
 * Created by @Martin@ on 21.07.2015 11:41.
 */
public class Compass extends Dial {
    Needle needle = new Needle();
    float angle;
    public Compass() {
    }

    public Compass(Vector3D position, float radius) {
        super(position, radius);
        pitchLineCount = 16;
        pitchLineStyle = TRIANGLE;
        placement = TextPlacement.OUTSIDE;
        text = new String[]{
          "O","OSO","SO","SSO",
                "S","SSW","SW","WSW",
                "W","WNW","NW","NNW",
                "N","NNO","NO","ONO"
        };
    }

    public Compass(Vector3D position, float radius, int pitchLineStyle, int pitchLineCount,
                   int lineColor, int pitchLineColor, float lineWidth, boolean withPitchLines,
                   float pitchLineLengthFactor, float pitchLineWidthFactor, boolean withCircle) {
        super(position, radius, pitchLineStyle, pitchLineCount, lineColor, pitchLineColor, lineWidth,
                withPitchLines, pitchLineLengthFactor, pitchLineWidthFactor, withCircle);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Needle getNeedle() {
        return needle;
    }

    public void setNeedle(Needle needle) {
        this.needle = needle;
    }

    @Override
    protected void drawPitchLines(Path path) {
        for(int index = 0 ; index < getPitchLineCount() ; ++index){
            if((index%4) == 0)
                drawPitchLine(path,index,2f);
            else if((index%2) == 0)
                drawPitchLine(path,index,1.5f);
            else
                drawPitchLine(path,index,1);
        }
    }
    @Override
    protected void drawText(Canvas canvas, float textRadius) {
        for(int index = 0 ; index < pitchLineCount ; index++ ) {
            if((index%4) == 0) {
                drawText(canvas, index, textRadius, 2);
            }
            else if((index%2) == 0) {
                drawText(canvas, index, textRadius, 1.4f);
            }
            else {
                drawText(canvas, index, textRadius, 1);
            }
        }
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        needle.draw(canvas,position,radius,angle);
    }
}
