package com.dickson.buzconnect.util;

/**
 * Created by dickson on 6/19/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
