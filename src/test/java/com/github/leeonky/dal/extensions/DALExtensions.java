package com.github.leeonky.dal.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leeonky.dal.DAL;
import com.github.leeonky.dal.runtime.Extension;
import com.github.leeonky.dal.runtime.RuntimeContextBuilder;
import lombok.SneakyThrows;

import java.util.List;

public class DALExtensions implements Extension {

    @Override
    public void extend(DAL dal) {
        RuntimeContextBuilder runtimeContextBuilder = dal.getRuntimeContextBuilder();
        runtimeContextBuilder.registerStaticMethodExtension(StaticMethods.class);
    }

    public static class StaticMethods {

        @SneakyThrows
        public static Object json(byte[] body) {
            return new ObjectMapper().readValue("[" + new String(body) + "]", List.class).get(0);
        }
    }
}
