package com.github.leeonky.github.leeonky.dal.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leeonky.dal.DAL;
import com.github.leeonky.dal.runtime.Extension;
import lombok.SneakyThrows;

import java.util.List;

public class JsonExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder().registerStaticMethodExtension(StaticMethods.class);
    }


    public static class StaticMethods {

        @SneakyThrows
        public static Object json(byte[] data) {
            return new ObjectMapper().readValue("[" + new String(data) + "]", List.class).get(0);
        }
    }
}
