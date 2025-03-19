package org.lucas.ui.framework.views;

import org.lucas.ui.framework.Canvas;
import org.lucas.ui.framework.View;

/**
 * Null-object pattern to ensure the backstack never encounters a NullPointerException
 */
public class NullView extends View {
    public NullView(Canvas canvas) {
        super(canvas, "", "");
    }
}
