package de.devacon.graphicutil;

import de.devacon.graphics.Vector3D;

/**
 * Created by @Martin@ on 21.07.2015 11:40.
 */
public class ClockFace extends  Dial {
    public ClockFace() {
    }

    public ClockFace(Vector3D position, float radius) {
        super(position, radius);
        pitchLineStyle = LINE;
        pitchLineCount = 12;
        withPitchLines = true;
    }

    public ClockFace(Vector3D position, float radius, int pitchLineStyle,
                     int pitchLineCount, int lineColor, int pitchLineColor,
                     float lineWidth,
                     boolean withPitchLines, float pitchLineLengthFactor, float pitchLineWidthFactor,
                     boolean withCircle) {
        super(position, radius, pitchLineStyle, pitchLineCount, lineColor,
                pitchLineColor, lineWidth, withPitchLines, pitchLineLengthFactor,
                pitchLineWidthFactor, withCircle);
    }
}
