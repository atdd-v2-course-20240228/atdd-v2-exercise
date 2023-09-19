package com.odde.atddv2.spec;

import com.github.leeonky.jfactory.Global;
import com.github.leeonky.jfactory.Spec;
import com.odde.atddv2.entity.relations.Clazz;
import com.odde.atddv2.entity.relations.School;
import com.odde.atddv2.entity.relations.Student;
import com.odde.atddv2.entity.relations.Teacher;

public class Relations {
    @Global
    public static class 学校 extends Spec<School> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }

    @Global
    public static class 老师 extends Spec<Teacher> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }

    @Global
    public static class 班级 extends Spec<Clazz> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }

    @Global
    public static class 学生 extends Spec<Student> {
        @Override
        public void main() {
            property("id").ignore();
        }
    }
}
