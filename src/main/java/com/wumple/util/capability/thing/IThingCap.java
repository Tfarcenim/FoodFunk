package com.wumple.util.capability.thing;

import com.wumple.util.adapter.IThing;

public interface IThingCap<T extends IThing>
{

    void checkInit(T ownerIn);
    <X> X getOwnerAs(Class<X> x);
    
    void forceUpdate();
    T getOwner();
    void setOwner(T ownerIn);
}