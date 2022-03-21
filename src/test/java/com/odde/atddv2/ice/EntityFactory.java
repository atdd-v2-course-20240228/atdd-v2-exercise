package com.odde.atddv2.ice;

import com.github.leeonky.jfactory.JFactory;
import com.github.leeonky.jfactory.Spec;
import com.github.leeonky.util.BeanClass;

public class EntityFactory extends JFactory {

    public EntityFactory() {
        super();
        configFactory();
    }

    private void configFactory() {
        BeanClass.subTypesOf(Spec.class, "com.odde.atddv2.ice.spec").forEach(c -> register((Class) c));
    }
}
