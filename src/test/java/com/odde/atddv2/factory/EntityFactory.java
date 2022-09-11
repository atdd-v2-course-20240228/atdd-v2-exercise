package com.odde.atddv2.factory;

import com.github.leeonky.jfactory.DataRepository;
import com.github.leeonky.jfactory.JFactory;
import com.github.leeonky.jfactory.Spec;
import com.github.leeonky.util.BeanClass;

import java.util.HashSet;
import java.util.Set;

public class EntityFactory extends JFactory {
    private static Set<Class> beanSpecsClasses = new HashSet<>();

    static {
        beanSpecsClasses.addAll(BeanClass.assignableTypesOf(Spec.class, "com.odde.atddv2.jfactory.factory"));
    }

    public EntityFactory() {
        configFactory();
    }

    public EntityFactory(DataRepository dataRepository) {
        super(dataRepository);
        configFactory();
    }

    private void configFactory() {
        ignoreDefaultValue(p -> p.getName().equals("id"));

        beanSpecsClasses.forEach(this::register);
    }
}
