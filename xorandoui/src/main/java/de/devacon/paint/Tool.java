package de.devacon.paint;

import android.graphics.ColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rasterizer;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;

import java.util.Locale;

/**
 * Created by @Martin@ on 13.07.2015 16:40.
 */
public class Tool {

    public enum ToolType {
        Selector,
        Line,
        Point,
        Path,
        RegularShape,
        ColorPicker,
        Filler,

    }
    public enum ShapeType {
        Oval,
        Triangle,
        Rectangle,
        Circle,
        RoundRect,
        Other,
    }
    public enum LineType {
        Simple,
        Arc,
        Multiline,
        Bezierline,
        Spline

    }
    ToolType type;
    ShapeType shape;
    LineType lineType;
    int lineStyle;

    public Drawable icon;
    public Paint pen;
    public Tool(ToolType type) {
        pen = new Paint();
        this.type = type;
    }
    public void setIcon(Drawable newIcon){
        icon = newIcon;
    }
    public Drawable getIcon() {
        return icon;
    }

    /**
     * Return the distance above (negative) the baseline (ascent) based on the
     * current typeface and text size.
     *
     * @return the distance above (negative) the baseline (ascent) based on the
     *         current typeface and text size.
     */
    public float ascent() {
        return pen.ascent();
    }

    /**
     * Set the paint's style, used for controlling how primitives'
     * geometries are interpreted (except for drawBitmap, which always assumes
     * Fill).
     *
     * @param style The new style to set in the paint
     */
    public void setStyle(Paint.Style style) {
        pen.setStyle(style);
    }

    /**
     * Helper for getFlags(), returning true if FAKE_BOLD_TEXT_FLAG bit is set
     *
     * @return true if the fakeBoldText bit is set in the paint's flags.
     */
    public boolean isFakeBoldText() {
        return pen.isFakeBoldText();
    }

    /**
     * Return the paint's style, used for controlling how primitives'
     * geometries are interpreted (except for drawBitmap, which always assumes
     * FILL_STYLE).
     *
     * @return the paint's style setting (Fill, Stroke, StrokeAndFill)
     */
    public Paint.Style getStyle() {
        return pen.getStyle();
    }

    /**
     * Get the paint's maskfilter object.
     *
     * @return the paint's maskfilter (or null)
     */
    public MaskFilter getMaskFilter() {
        return pen.getMaskFilter();
    }

    /**
     * Helper for getFlags(), returning true if ANTI_ALIAS_FLAG bit is set
     * AntiAliasing smooths out the edges of what is being drawn, but is has
     * no impact on the interior of the shape. See setDither() and
     * setFilterBitmap() to affect how colors are treated.
     *
     * @return true if the antialias bit is set in the paint's flags.
     */
    public boolean isAntiAlias() {
        return pen.isAntiAlias();
    }

    /**
     * Set the paint's Cap.
     *
     * @param cap set the paint's line cap style, used whenever the paint's
     *            style is Stroke or StrokeAndFill.
     */
    public void setStrokeCap(Paint.Cap cap) {
        pen.setStrokeCap(cap);
    }

    /**
     * Return the path (outline) for the specified text.
     * Note: just like Canvas.drawText, this will respect the Align setting
     * in the paint.
     *  @param text  The text to retrieve the path from
     * @param start The first character in the text
     * @param end   1 past the last charcter in the text
     * @param x     The x coordinate of the text's origin
     * @param y     The y coordinate of the text's origin
     * @param path  The path to receive the data describing the text. Must
     */
    public void getTextPath(String text, int start, int end, float x, float y, Path path) {
        pen.getTextPath(text, start, end, x, y, path);
    }

    /**
     * Allocates a new FontMetrics object, and then calls getFontMetrics(fm)
     * with it, returning the object.
     */
    public Paint.FontMetrics getFontMetrics() {
        return pen.getFontMetrics();
    }

    /**
     * Return the recommend line spacing based on the current typeface and
     * text size.
     *
     * @return recommend line spacing based on the current typeface and
     *          text size.
     */
    public float getFontSpacing() {
        return pen.getFontSpacing();
    }

    /**
     * Helper for setFlags(), setting or clearing the DITHER_FLAG bit
     * Dithering affects how colors that are higher precision than the device
     * are down-sampled. No dithering is generally faster, but higher precision
     * colors are just truncated down (e.g. 8888 -> 565). Dithering tries to
     * distribute the error inherent in this process, to reduce the visual
     * artifacts.
     *
     * @param dither true to set the dithering bit in flags, false to clear it
     */
    public void setDither(boolean dither) {
        pen.setDither(dither);
    }

    /**
     * Get the paint's colorfilter (maybe be null).
     *
     * @return the paint's colorfilter (maybe be null)
     */
    public ColorFilter getColorFilter() {
        return pen.getColorFilter();
    }

    /** Restores the paint to its default settings. */
    public void reset() {
        pen.reset();
    }

    /**
     * Return the paint's color. Note that the color is a 32bit value
     * containing alpha as well as r,g,b. This 32bit value is not premultiplied,
     * meaning that its alpha can be any value, regardless of the values of
     * r,g,b. See the Color class for more details.
     *
     * @return the paint's color (and alpha).
     */
    public int getColor() {
        return pen.getColor();
    }

    /**
     * Get the paint's patheffect object.
     *
     * @return the paint's patheffect (or null)
     */
    public PathEffect getPathEffect() {
        return pen.getPathEffect();
    }

    /**
     * Return in bounds (allocated by the caller) the smallest rectangle that
     * encloses all of the characters, with an implied origin at (0,0).
     *  @param text  Array of chars to measure and return their unioned bounds
     * @param index Index of the first char in the array to measure
     * @param count The number of chars, beginning at index, to measure
     * @param bounds Returns the unioned bounds of all the text. Must be
     */
    public void getTextBounds(char[] text, int index, int count, Rect bounds) {
        pen.getTextBounds(text, index, count, bounds);
    }

    /**
     * Helper for getFlags(), returning true if UNDERLINE_TEXT_FLAG bit is set
     *
     * @return true if the underlineText bit is set in the paint's flags.
     */
    public boolean isUnderlineText() {
        return pen.isUnderlineText();
    }

    /**
     * Set or clear the rasterizer object.
     * <p />
     * Pass null to clear any previous rasterizer.
     * As a convenience, the parameter passed is also returned.
     *
     * @param rasterizer May be null. The new rasterizer to be installed in
     *                   the paint.
     * @return rasterizer
     */
    public Rasterizer setRasterizer(Rasterizer rasterizer) {
        return pen.setRasterizer(rasterizer);
    }

    /**
     * Return the paint's flags. Use the Flag enum to test flag values.
     *
     * @return the paint's flags (see enums ending in _Flag for bit masks)
     */
    public int getFlags() {
        return pen.getFlags();
    }

    /**
     * Set the paint's stroke miter value. This is used to control the behavior
     * of miter joins when the joins angle is sharp. This value must be >= 0.
     *
     * @param miter set the miter limit on the paint, used whenever the paint's
     *              style is Stroke or StrokeAndFill.
     */
    public void setStrokeMiter(float miter) {
        pen.setStrokeMiter(miter);
    }

    /**
     * Return the width of the text.
     *
     * @param text  The text to measure. Cannot be null.
     * @param start The index of the first character to start measuring
     * @param end   1 beyond the index of the last character to measure
     * @return The width of the text
     */
    public float measureText(String text, int start, int end) {
        return pen.measureText(text, start, end);
    }

    /**
     * Helper for getFlags(), returning true if STRIKE_THRU_TEXT_FLAG bit is set
     *
     * @return true if the strikeThruText bit is set in the paint's flags.
     */
    public boolean isStrikeThruText() {
        return pen.isStrikeThruText();
    }

    /**
     * This draws a shadow layer below the main layer, with the specified
     * offset and color, and blur radius. If radius is 0, then the shadow
     * layer is removed.
     * @param radius
     * @param dx
     * @param dy
     * @param color
     */
    public void setShadowLayer(float radius, float dx, float dy, int color) {
        pen.setShadowLayer(radius, dx, dy, color);
    }

    /**
     * Measure the text, stopping early if the measured width exceeds maxWidth.
     * Return the number of chars that were measured, and if measuredWidth is
     * not null, return in it the actual width measured.
     *
     * @param text  The text to measure. Cannot be null.
     * @param index The offset into text to begin measuring at
     * @param count The number of maximum number of entries to measure. If count
     *              is negative, then the characters are measured in reverse order.
     * @param maxWidth The maximum width to accumulate.
     * @param measuredWidth Optional. If not null, returns the actual width
     *                     measured.
     * @return The number of chars that were measured. Will always be <=
     *         abs(count).
     */
    public int breakText(char[] text, int index, int count, float maxWidth, float[] measuredWidth) {
        return pen.breakText(text, index, count, maxWidth, measuredWidth);
    }

    /**
     * Helper for setFlags(), setting or clearing the LINEAR_TEXT_FLAG bit
     *
     * @param linearText true to set the linearText bit in the paint's flags,
     *                   false to clear it.
     */
    @Deprecated
    public void setLinearText(boolean linearText) {
        pen.setLinearText(linearText);
    }

    /**
     * Set or clear the typeface object.
     * <p />
     * Pass null to clear any previous typeface.
     * As a convenience, the parameter passed is also returned.
     *
     * @param typeface May be null. The typeface to be installed in the paint
     * @return typeface
     */
    public Typeface setTypeface(Typeface typeface) {
        return pen.setTypeface(typeface);
    }

    /**
     * Set the text locale.
     *
     * The text locale affects how the text is drawn for some languages.
     *
     * For example, if the locale is {@link Locale#CHINESE} or {@link Locale#CHINA},
     * then the text renderer will prefer to draw text using a Chinese font. Likewise,
     * if the locale is {@link Locale#JAPANESE} or {@link Locale#JAPAN}, then the text
     * renderer will prefer to draw text using a Japanese font.
     *
     * This distinction is important because Chinese and Japanese text both use many
     * of the same Unicode code points but their appearance is subtly different for
     * each language.
     *
     * By default, the text locale is initialized to the system locale (as returned
     * by {@link Locale#getDefault}). This assumes that the text to be rendered will
     * most likely be in the user's preferred language.
     *
     * If the actual language of the text is known, then it can be provided to the
     * text renderer using this method. The text renderer may attempt to guess the
     * language script based on the contents of the text to be drawn independent of
     * the text locale here. Specifying the text locale just helps it do a better
     * job in certain ambiguous cases
     *
     * @param locale the paint's locale value for drawing text, must not be null.
     */
    public void setTextLocale(Locale locale) {
        //pen.setTextLocale(locale);
    }

    /*
     * Set the paint's hinting mode.  May be either
     * {@link #HINTING_OFF} or {@link #HINTING_ON}.
     * @param mode
     */
    public void setHinting(int mode) {
        pen.setHinting(mode);
    }

    /**
     * Set the paint's Join.
     *
     * @param join set the paint's Join, used whenever the paint's style is
     *             Stroke or StrokeAndFill.
     */
    public void setStrokeJoin(Paint.Join join) {
        pen.setStrokeJoin(join);
    }

    /**
     * Helper for getFlags(), returning true if DITHER_FLAG bit is set
     * Dithering affects how colors that are higher precision than the device
     * are down-sampled. No dithering is generally faster, but higher precision
     * colors are just truncated down (e.g. 8888 -> 565). Dithering tries to
     * distribute the error inherent in this process, to reduce the visual
     * artifacts.
     *
     * @return true if the dithering bit is set in the paint's flags.
     */
    public boolean isDither() {
        return pen.isDither();
    }

    /**
     * Get the paint's xfermode object.
     *
     * @return the paint's xfermode (or null)
     */
    public Xfermode getXfermode() {
        return pen.getXfermode();
    }

    /**
     * Return the paint's Cap, controlling how the start and end of stroked
     * lines and paths are treated.
     *
     * @return the line cap style for the paint, used whenever the paint's
     *         style is Stroke or StrokeAndFill.
     */
    public Paint.Cap getStrokeCap() {
        return pen.getStrokeCap();
    }

    /**
     * Helper for setFlags(), setting or clearing the FAKE_BOLD_TEXT_FLAG bit
     *
     * @param fakeBoldText true to set the fakeBoldText bit in the paint's
     *                     flags, false to clear it.
     */
    public void setFakeBoldText(boolean fakeBoldText) {
        pen.setFakeBoldText(fakeBoldText);
    }

    /**
     * Set or clear the shader object.
     * <p />
     * Pass null to clear any previous shader.
     * As a convenience, the parameter passed is also returned.
     *
     * @param shader May be null. the new shader to be installed in the paint
     * @return shader
     */
    public Shader setShader(Shader shader) {
        return pen.setShader(shader);
    }

    /**
     * Get the text Locale.
     *
     * @return the paint's Locale used for drawing text, never null.
     */
    public Locale getTextLocale() {
        return null;//pen.getTextLocale();
    }

    /**
     * Return in bounds (allocated by the caller) the smallest rectangle that
     * encloses all of the characters, with an implied origin at (0,0).
     *  @param text  String to measure and return its bounds
     * @param start Index of the first char in the string to measure
     * @param end   1 past the last char in the string measure
     * @param bounds Returns the unioned bounds of all the text. Must be
     */
    public void getTextBounds(String text, int start, int end, Rect bounds) {
        pen.getTextBounds(text, start, end, bounds);
    }

    /**
     * Helper for setFlags(), setting or clearing the STRIKE_THRU_TEXT_FLAG bit
     *
     * @param strikeThruText true to set the strikeThruText bit in the paint's
     *                       flags, false to clear it.
     */
    public void setStrikeThruText(boolean strikeThruText) {
        pen.setStrikeThruText(strikeThruText);
    }

    /**
     * Return the advance widths for the characters in the string.
     *
     * @param text     The text to measure. Cannot be null.
     * @param start    The index of the first char to to measure
     * @param end      The end of the text slice to measure
     * @param widths   array to receive the advance widths of the characters.
     *                 Must be at least a large as (end - start).
     * @return the actual number of widths returned.
     */
    public int getTextWidths(CharSequence text, int start, int end, float[] widths) {
        return pen.getTextWidths(text, start, end, widths);
    }

    /**
     * Set the paint's text size. This value must be > 0
     *
     * @param textSize set the paint's text size.
     */
    public void setTextSize(float textSize) {
        pen.setTextSize(textSize);
    }

    public Paint.FontMetricsInt getFontMetricsInt() {
        return pen.getFontMetricsInt();
    }

    /**
     * Set or clear the maskfilter object.
     * <p />
     * Pass null to clear any previous maskfilter.
     * As a convenience, the parameter passed is also returned.
     *
     * @param maskfilter May be null. The maskfilter to be installed in the
     *                   paint
     * @return maskfilter
     */
    public MaskFilter setMaskFilter(MaskFilter maskfilter) {
        return pen.setMaskFilter(maskfilter);
    }

    /**
     * Return the font's interline spacing, given the Paint's settings for
     * typeface, textSize, etc. If metrics is not null, return the fontmetric
     * values in it. Note: all values have been converted to integers from
     * floats, in such a way has to make the answers useful for both spacing
     * and clipping. If you want more control over the rounding, call
     * getFontMetrics().
     *
     * @return the font's interline spacing.
     * @param fmi
     */
    public int getFontMetricsInt(Paint.FontMetricsInt fmi) {
        return pen.getFontMetricsInt(fmi);
    }

    /**
     * Return the distance below (positive) the baseline (descent) based on the
     * current typeface and text size.
     *
     * @return the distance below (positive) the baseline (descent) based on
     *         the current typeface and text size.
     */
    public float descent() {
        return pen.descent();
    }

    /**
     * Copy the fields from src into this paint. This is equivalent to calling
     * get() on all of the src fields, and calling the corresponding set()
     * methods on this.
     * @param src
     */
    public void set(Paint src) {
        pen.set(src);
    }

    /**
     * Helper for setFlags(), setting or clearing the ANTI_ALIAS_FLAG bit
     * AntiAliasing smooths out the edges of what is being drawn, but is has
     * no impact on the interior of the shape. See setDither() and
     * setFilterBitmap() to affect how colors are treated.
     *
     * @param aa true to set the antialias bit in the flags, false to clear it
     */
    public void setAntiAlias(boolean aa) {
        pen.setAntiAlias(aa);
    }

    /**
     * Return the paint's stroke miter value. Used to control the behavior
     * of miter joins when the joins angle is sharp.
     *
     * @return the paint's miter limit, used whenever the paint's style is
     *         Stroke or StrokeAndFill.
     */
    public float getStrokeMiter() {
        return pen.getStrokeMiter();
    }

    /**
     * Get the paint's rasterizer (or null).
     * <p />
     * The raster controls/modifies how paths/text are turned into alpha masks.
     *
     * @return the paint's rasterizer (or null)
     */
    public Rasterizer getRasterizer() {
        return pen.getRasterizer();
    }

    /**
     * Return the path (outline) for the specified text.
     * Note: just like Canvas.drawText, this will respect the Align setting in
     * the paint.
     *  @param text     The text to retrieve the path from
     * @param index    The index of the first character in text
     * @param count    The number of characterss starting with index
     * @param x        The x coordinate of the text's origin
     * @param y        The y coordinate of the text's origin
     * @param path     The path to receive the data describing the text. Must
     */
    public void getTextPath(char[] text, int index, int count, float x, float y, Path path) {
        pen.getTextPath(text, index, count, x, y, path);
    }

    /**
     * Helper for setFlags(), setting or clearing the FILTER_BITMAP_FLAG bit.
     * Filtering affects the sampling of bitmaps when they are transformed.
     * Filtering does not affect how the colors in the bitmap are converted into
     * device pixels. That is dependent on dithering and xfermodes.
     *
     * @param filter true to set the FILTER_BITMAP_FLAG bit in the paint's
     *               flags, false to clear it.
     */
    public void setFilterBitmap(boolean filter) {
        pen.setFilterBitmap(filter);
    }

    /**
     * Get the paint's shader object.
     *
     * @return the paint's shader (or null)
     */
    public Shader getShader() {
        return pen.getShader();
    }

    /**
     * Set or clear the paint's colorfilter, returning the parameter.
     *
     * @param filter May be null. The new filter to be installed in the paint
     * @return filter
     */
    public ColorFilter setColorFilter(ColorFilter filter) {
        return pen.setColorFilter(filter);
    }

    /**
     * Set or clear the patheffect object.
     * <p />
     * Pass null to clear any previous patheffect.
     * As a convenience, the parameter passed is also returned.
     *
     * @param effect May be null. The patheffect to be installed in the paint
     * @return effect
     */
    public PathEffect setPathEffect(PathEffect effect) {
        return pen.setPathEffect(effect);
    }

    /**
     * Return the width for stroking.
     * <p />
     * A value of 0 strokes in hairline mode.
     * Hairlines always draws a single pixel independent of the canva's matrix.
     *
     * @return the paint's stroke width, used whenever the paint's style is
     *         Stroke or StrokeAndFill.
     */
    public float getStrokeWidth() {
        return pen.getStrokeWidth();
    }

    /**
     * Set the paint's text alignment. This controls how the
     * text is positioned relative to its origin. LEFT align means that all of
     * the text will be drawn to the right of its origin (i.e. the origin
     * specifieds the LEFT edge of the text) and so on.
     *
     * @param align set the paint's Align value for drawing text.
     */
    public void setTextAlign(Paint.Align align) {
        pen.setTextAlign(align);
    }

    /**
     * Return the width of the text.
     *
     * @param text  The text to measure. Cannot be null.
     * @param index The index of the first character to start measuring
     * @param count THe number of characters to measure, beginning with start
     * @return The width of the text
     */
    public float measureText(char[] text, int index, int count) {
        return pen.measureText(text, index, count);
    }

    /**
     * Measure the text, stopping early if the measured width exceeds maxWidth.
     * Return the number of chars that were measured, and if measuredWidth is
     * not null, return in it the actual width measured.
     *
     * @param text  The text to measure. Cannot be null.
     * @param measureForwards If true, measure forwards, starting with the
     *                        first character in the string. Otherwise,
     *                        measure backwards, starting with the
     *                        last character in the string.
     * @param maxWidth The maximum width to accumulate.
     * @param measuredWidth Optional. If not null, returns the actual width
     *                     measured.
     * @return The number of chars that were measured. Will always be <=
     *         abs(count).
     */
    public int breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth) {
        return pen.breakText(text, measureForwards, maxWidth, measuredWidth);
    }

    /**
     * Helper for getFlags(), returning true if SUBPIXEL_TEXT_FLAG bit is set
     *
     * @return true if the subpixel bit is set in the paint's flags
     */
    public boolean isSubpixelText() {
        return pen.isSubpixelText();
    }

    /**
     * Applies any/all effects (patheffect, stroking) to src, returning the
     * result in dst. The result is that drawing src with this paint will be
     * the same as drawing dst with a default paint (at least from the
     * geometric perspective).
     *
     * @param src input path
     * @param dst output path (may be the same as src)
     * @return true if the path should be filled, or false if it should be
     *                 drawn with a hairline (width == 0)
     */
    public boolean getFillPath(Path src, Path dst) {
        return pen.getFillPath(src, dst);
    }

    /**
     * Helper to setColor(), that takes a,r,g,b and constructs the color int
     *  @param a The new alpha component (0..255) of the paint's color.
     * @param r The new red component (0..255) of the paint's color.
     * @param g The new green component (0..255) of the paint's color.
     * @param b The new blue component (0..255) of the paint's color.
     */
    public void setARGB(int a, int r, int g, int b) {
        pen.setARGB(a, r, g, b);
    }

    /**
     * Set the width for stroking.
     * Pass 0 to stroke in hairline mode.
     * Hairlines always draws a single pixel independent of the canva's matrix.
     *
     * @param width set the paint's stroke width, used whenever the paint's
     *              style is Stroke or StrokeAndFill.
     */
    public void setStrokeWidth(float width) {
        pen.setStrokeWidth(width);
    }

    /**
     * Return the advance widths for the characters in the string.
     *
     * @param text   The text to measure. Cannot be null.
     * @param start  The index of the first char to to measure
     * @param end    The end of the text slice to measure
     * @param widths array to receive the advance widths of the characters.
     *               Must be at least a large as the text.
     * @return the number of unichars in the specified text.
     */
    public int getTextWidths(String text, int start, int end, float[] widths) {
        return pen.getTextWidths(text, start, end, widths);
    }

    /**
     * Whether or not the bitmap filter is activated.
     * Filtering affects the sampling of bitmaps when they are transformed.
     * Filtering does not affect how the colors in the bitmap are converted into
     * device pixels. That is dependent on dithering and xfermodes.
     *
     * @see #setFilterBitmap(boolean) setFilterBitmap()
     */
    public boolean isFilterBitmap() {
        return pen.isFilterBitmap();
    }

    /**
     * Get the paint's typeface object.
     * <p />
     * The typeface object identifies which font to use when drawing or
     * measuring text.
     *
     * @return the paint's typeface (or null)
     */
    public Typeface getTypeface() {
        return pen.getTypeface();
    }

    /**
     * Return the paint's text size.
     *
     * @return the paint's text size.
     */
    public float getTextSize() {
        return pen.getTextSize();
    }

    /**
     * Set the paint's color. Note that the color is an int containing alpha
     * as well as r,g,b. This 32bit value is not premultiplied, meaning that
     * its alpha can be any value, regardless of the values of r,g,b.
     * See the Color class for more details.
     *
     * @param color The new color (including alpha) to set in the paint.
     */
    public void setColor(int color) {
        pen.setColor(color);
    }

    /**
     * Set the paint's horizontal scale factor for text. The default value
     * is 1.0. Values > 1.0 will stretch the text wider. Values < 1.0 will
     * stretch the text narrower.
     *
     * @param scaleX set the paint's scale in X for drawing/measuring text.
     */
    public void setTextScaleX(float scaleX) {
        pen.setTextScaleX(scaleX);
    }

    /**
     * Set the paint's horizontal skew factor for text. The default value
     * is 0. For approximating oblique text, use values around -0.25.
     *
     * @param skewX set the paint's skew factor in X for drawing text.
     */
    public void setTextSkewX(float skewX) {
        pen.setTextSkewX(skewX);
    }

    /**
     * Helper to setColor(), that only assigns the color's alpha value,
     * leaving its r,g,b values unchanged. Results are undefined if the alpha
     * value is outside of the range [0..255]
     *
     * @param a set the alpha component [0..255] of the paint's color.
     */
    public void setAlpha(int a) {
        pen.setAlpha(a);
    }

    /**
     * Return the paint's horizontal skew factor for text. The default value
     * is 0.
     *
     * @return the paint's skew factor in X for drawing text.
     */
    public float getTextSkewX() {
        return pen.getTextSkewX();
    }

    /**
     * Helper for getFlags(), returning true if LINEAR_TEXT_FLAG bit is set
     *
     * @return true if the lineartext bit is set in the paint's flags
     */
    @Deprecated
    public boolean isLinearText() {
        return pen.isLinearText();
    }

    /**
     * Return the paint's stroke join type.
     *
     * @return the paint's Join.
     */
    public Paint.Join getStrokeJoin() {
        return pen.getStrokeJoin();
    }

    /**
     * Clear the shadow layer.
     */
    public void clearShadowLayer() {
        pen.clearShadowLayer();
    }

    /**
     * Return the advance widths for the characters in the string.
     *
     * @param text     The text to measure. Cannot be null.
     * @param index    The index of the first char to to measure
     * @param count    The number of chars starting with index to measure
     * @param widths   array to receive the advance widths of the characters.
     *                 Must be at least a large as count.
     * @return the actual number of widths returned.
     */
    public int getTextWidths(char[] text, int index, int count, float[] widths) {
        return pen.getTextWidths(text, index, count, widths);
    }

    /**
     * Helper for setFlags(), setting or clearing the SUBPIXEL_TEXT_FLAG bit
     *
     * @param subpixelText true to set the subpixelText bit in the paint's
     *                     flags, false to clear it.
     */
    public void setSubpixelText(boolean subpixelText) {
        pen.setSubpixelText(subpixelText);
    }

    /**
     * Set or clear the xfermode object.
     * <p />
     * Pass null to clear any previous xfermode.
     * As a convenience, the parameter passed is also returned.
     *
     * @param xfermode May be null. The xfermode to be installed in the paint
     * @return xfermode
     */
    public Xfermode setXfermode(Xfermode xfermode) {
        return pen.setXfermode(xfermode);
    }

    /**
     * Return the width of the text.
     *
     * @param text  The text to measure
     * @param start The index of the first character to start measuring
     * @param end   1 beyond the index of the last character to measure
     * @return The width of the text
     */
    public float measureText(CharSequence text, int start, int end) {
        return pen.measureText(text, start, end);
    }

    /**
     * Measure the text, stopping early if the measured width exceeds maxWidth.
     * Return the number of chars that were measured, and if measuredWidth is
     * not null, return in it the actual width measured.
     *
     * @param text  The text to measure. Cannot be null.
     * @param start The offset into text to begin measuring at
     * @param end   The end of the text slice to measure.
     * @param measureForwards If true, measure forwards, starting at start.
     *                        Otherwise, measure backwards, starting with end.
     * @param maxWidth The maximum width to accumulate.
     * @param measuredWidth Optional. If not null, returns the actual width
     *                     measured.
     * @return The number of chars that were measured. Will always be <=
     *         abs(end - start).
     */
    public int breakText(CharSequence text, int start, int end, boolean measureForwards, float maxWidth, float[] measuredWidth) {
        return pen.breakText(text, start, end, measureForwards, maxWidth, measuredWidth);
    }

    /**
     * Return the advance widths for the characters in the string.
     *
     * @param text   The text to measure
     * @param widths array to receive the advance widths of the characters.
     *               Must be at least a large as the text.
     * @return the number of unichars in the specified text.
     */
    public int getTextWidths(String text, float[] widths) {
        return pen.getTextWidths(text, widths);
    }

    /**
     * Helper for setFlags(), setting or clearing the UNDERLINE_TEXT_FLAG bit
     *
     * @param underlineText true to set the underlineText bit in the paint's
     *                      flags, false to clear it.
     */
    public void setUnderlineText(boolean underlineText) {
        pen.setUnderlineText(underlineText);
    }

    /**
     * Helper to getColor() that just returns the color's alpha value. This is
     * the same as calling getColor() >>> 24. It always returns a value between
     * 0 (completely transparent) and 255 (completely opaque).
     *
     * @return the alpha component of the paint's color.
     */
    public int getAlpha() {
        return pen.getAlpha();
    }

    /**
     * Set the paint's flags. Use the Flag enum to specific flag values.
     *
     * @param flags The new flag bits for the paint
     */
    public void setFlags(int flags) {
        pen.setFlags(flags);
    }

    /**
     * Return the paint's horizontal scale factor for text. The default value
     * is 1.0.
     *
     * @return the paint's scale factor in X for drawing/measuring text
     */
    public float getTextScaleX() {
        return pen.getTextScaleX();
    }

    /**
     * Return the font's recommended interline spacing, given the Paint's
     * settings for typeface, textSize, etc. If metrics is not null, return the
     * fontmetric values in it.
     *
     * @param metrics If this object is not null, its fields are filled with
     *                the appropriate values given the paint's text attributes.
     * @return the font's recommended interline spacing.
     */
    public float getFontMetrics(Paint.FontMetrics metrics) {
        return pen.getFontMetrics(metrics);
    }

    /*
     * Return the paint's hinting mode.  Returns either
     * {@link #HINTING_OFF} or {@link #HINTING_ON}.
     */
    public int getHinting() {
        return pen.getHinting();
    }

    /**
     * Return the width of the text.
     *
     * @param text  The text to measure. Cannot be null.
     * @return The width of the text
     */
    public float measureText(String text) {
        return pen.measureText(text);
    }

    /**
     * Return the paint's Align value for drawing text. This controls how the
     * text is positioned relative to its origin. LEFT align means that all of
     * the text will be drawn to the right of its origin (i.e. the origin
     * specifieds the LEFT edge of the text) and so on.
     *
     * @return the paint's Align value for drawing text.
     */
    public Paint.Align getTextAlign() {
        return pen.getTextAlign();
    }

}
