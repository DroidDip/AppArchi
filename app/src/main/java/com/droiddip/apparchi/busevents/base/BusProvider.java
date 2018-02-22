package com.droiddip.apparchi.busevents.base;

/**
 * Created by Dipanjan Chakraborty on 21-02-2018.
 */

public final class BusProvider {

    private BusProvider() {
    }

    private static final UIBus BUS = new UIBus();

    public static UIBus getInstance() {
        return BUS;
    }

}
