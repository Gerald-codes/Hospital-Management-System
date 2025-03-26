package org.groupJ.ui.framework.views;

import org.groupJ.ui.framework.Canvas;
import org.groupJ.ui.framework.View;

/**
 * Null-object pattern to ensure the backstack never encounters a NullPointerException
 */
public class NullView extends View {
    public NullView(Canvas canvas) {
        super(canvas, "", "");
    }
}
