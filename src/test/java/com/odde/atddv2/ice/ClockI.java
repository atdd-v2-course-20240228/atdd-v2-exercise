package com.odde.atddv2.ice;

import Demo.TimeOfDay;
import com.zeroc.Ice.Current;

public class ClockI implements Demo.Clock {

    @Override
    public TimeOfDay getTime(Current current) {
        return null;
    }

    @Override
    public void setTime(TimeOfDay time, Current current) {

    }
}
